/*
 *  Copyright 2006-2013 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.csv;

import com.trazere.core.collection.Lists;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.ImperativePredicates;
import com.trazere.core.imperative.IntCounter;
import com.trazere.core.imperative.LookAheadIterator;
import com.trazere.core.lang.InternalException;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.record.Field;
import com.trazere.core.record.Fields;
import com.trazere.core.record.Record;
import com.trazere.core.record.RecordBuilder;
import com.trazere.core.record.SimpleRecordBuilder;
import com.trazere.core.text.CharPredicates;
import com.trazere.core.text.Scanner;
import com.trazere.core.text.TextUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Serializers;
import com.trazere.core.util.Tuple2;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The {@link CSVReader} class implements readers of a CSV formatted table.
 * <p>
 * This class wraps a reader, decodes the CSV lines, and behaves like an iterator over them.
 * <p>
 * Each CSV line is represented as a record of fields identified by their corresponding column header. Missing and empty values are excluded from the records.
 * Available values are deserialized as defined by the column header.
 */
public class CSVReader
implements Iterator<Record<CSVHeader<?>>>, Closeable {
	/**
	 * Builds a new CSV reader using the first line as the list of headers starting at line 1.
	 *
	 * @param reader Reader providing the CSV input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param options Options.
	 * @return The built reader.
	 * @throws CSVException When the headers cannot be computed.
	 */
	public static CSVReader build(final Reader reader, final String delimiter, final Set<CSVOption> options)
	throws CSVException {
		return build(reader, delimiter, Functions.constant(Maybe.none()), options);
	}
	
	/**
	 * Builds a new CSV reader using the first line as the list of headers starting at line 1.
	 *
	 * @param reader Reader providing the CSV input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param userHeaders Function that provides the used defined headers according to the header name.
	 * @param options Options.
	 * @return The built reader.
	 * @throws CSVException When the headers cannot be computed.
	 */
	public static CSVReader build(final Reader reader, final String delimiter, final Function<? super String, ? extends Maybe<? extends CSVHeader<?>>> userHeaders, final Set<CSVOption> options)
	throws CSVException {
		return build(reader, 1, delimiter, userHeaders, options);
	}
	
	/**
	 * Builds a new CSV reader using the first line as the list of headers.
	 *
	 * @param reader Reader providing the CSV input.
	 * @param lineNumber Line number of the text input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param userHeaders Function that provides the used defined headers according to the header name.
	 * @param options Options.
	 * @return The built reader.
	 * @throws CSVException When the headers cannot be computed.
	 */
	@SuppressWarnings("resource")
	public static CSVReader build(final Reader reader, final int lineNumber, final String delimiter, final Function<? super String, ? extends Maybe<? extends CSVHeader<?>>> userHeaders, final Set<CSVOption> options)
	throws CSVException {
		// Build the field reader.
		final FieldReader fieldReader = new FieldReader(reader, lineNumber, delimiter);
		
		// Read the header line.
		if (fieldReader.isEof()) {
			throw new CSVException("Missing header line");
		}
		final List<Tuple2<String, Integer>> headerFields;
		try {
			headerFields = fieldReader.readFields().get1();
		} catch (final CSVException exception) {
			throw new CSVException("Failed reading the header line", exception);
		}
		
		// Build the headers.
		final Predicate<String> headerNames = ImperativePredicates.normalizer();
		final List<CSVHeader<?>> headers = Lists.fromIterable(IterableUtils.map(headerFields, (final Tuple2<String, Integer> headerField) -> {
			// Check for conflict.
			final String headerName = headerField.get1();
			if (!headerNames.evaluate(headerName)) {
				throw new CSVException("Conflicting headers \"" + headerName + "\"");
			}
			
			// Build the header.
			final Maybe<? extends CSVHeader<?>> userHeader = userHeaders.evaluate(headerName);
			if (userHeader.isSome()) {
				return userHeader.asSome().getValue();
			} else {
				// Build a basic header.
				return new CSVHeader<>(headerName, String.class, Serializers.identity());
			}
		}));
		
		// Build the reader.
		return new CSVReader(fieldReader, headers, options);
	}
	
	/**
	 * Instantiates a new CSV reader starting at line 1.
	 *
	 * @param reader Reader providing the CSV input.
	 * @param headers Headers of the CSV fields.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param options Options.
	 */
	public CSVReader(final Reader reader, final String delimiter, final List<? extends CSVHeader<?>> headers, final Set<CSVOption> options) {
		this(reader, 1, delimiter, headers, options);
	}
	
	/**
	 * Instantiates a new CSV reader.
	 *
	 * @param reader Reader providing the CSV input.
	 * @param lineNumber Line number of the text input.
	 * @param headers Headers of the CSV fields.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param options Options.
	 */
	public CSVReader(final Reader reader, final int lineNumber, final String delimiter, final List<? extends CSVHeader<?>> headers, final Set<CSVOption> options) {
		this(new FieldReader(reader, lineNumber, delimiter), headers, options);
	}
	
	/**
	 * Instantiates a new CSV reader.
	 *
	 * @param reader Reader providing the CSV fields.
	 * @param headers Headers of the CSV fields.
	 * @param options Options.
	 */
	protected CSVReader(final FieldReader reader, final List<? extends CSVHeader<?>> headers, final Set<CSVOption> options) {
		assert null != reader;
		assert null != headers;
		assert null != options;
		
		// Checks.
		if (headers.isEmpty()) {
			throw new IllegalArgumentException("Lines should have at least one header");
		}
		
		// Initialization.
		_reader = reader;
		_headers = Collections.unmodifiableList(new ArrayList<>(headers));
		_options = Collections.unmodifiableSet(EnumSet.copyOf(options));
	}
	
	// Fields.
	
	protected static class FieldReader
	implements Closeable {
		/**
		 * Instantiates a new CSV reader.
		 *
		 * @param reader Reader providing the CSV input.
		 * @param lineNumber Line number of the text input.
		 * @param delimiter Delimiter of the CSV fields.
		 */
		public FieldReader(final Reader reader, final int lineNumber, final String delimiter) {
			assert null != delimiter;
			
			// Checks.
			if (delimiter.isEmpty()) {
				throw new IllegalArgumentException("Delimiter should not be empty");
			}
			
			// Initialization.
			_input = new Scanner(new Reader() {
				@Override
				public int read(final char[] cbuf, final int off, final int len)
				throws IOException {
					// Read.
					final int n = reader.read(cbuf, off, len);
					
					// Count the EOL.
					if (n > 0) {
						for (int i = off; i < n; i += 1) {
							if (cbuf[i] == '\n') {
								_lineNumber.inc();
							}
						}
					}
					
					return n;
				}
				
				@Override
				public void close()
				throws IOException {
					reader.close();
				}
			});
			_lineNumber = new IntCounter(lineNumber);
			
			_delimiter = delimiter;
			_delimiterHead = delimiter.charAt(0);
		}
		
		// Input.
		
		/** CSV input. */
		protected final Scanner _input;
		
		/** Line number of the text input. */
		protected final IntCounter _lineNumber;
		
		/**
		 * Indicates whether the input has been exhausted or not.
		 * 
		 * @return <code>true</code> when the input has been exhausted, <code>false</code> otherwise.
		 */
		public boolean isEof() {
			return _input.isEof();
		}
		
		/**
		 * Closes the underlying reader providing the input to this reader.
		 */
		@Override
		public void close() {
			_input.close();
		}
		
		// Delimiter.
		
		/** Delimiter of the CSV fields. */
		protected final String _delimiter;
		
		/**
		 * Gets the CSV field delimiter of this reader.
		 * 
		 * @return The delimiter.
		 */
		public String getDelimiter() {
			return _delimiter;
		}
		
		/** Head character of the delimiter. */
		protected final char _delimiterHead;
		
		// Fields.
		
		/**
		 * Reads the fields until the end of the line.
		 * 
		 * @return The read field and a flag indicating whether the line has ended or not.
		 */
		protected Tuple2<List<Tuple2<String, Integer>>, Integer> readFields() {
			// Get the line number.
			// Note: must be got before reading the field because it might change
			final int baseLineNumber = _lineNumber.get();
			
			final List<Tuple2<String, Integer>> fields = new ArrayList<>();
			while (true) {
				// Get the line number.
				// Note: must be got before reading the field because it might change
				final int lineNumber = _lineNumber.get();
				
				// Read the next field.
				final Tuple2<String, Boolean> field = readField();
				fields.add(new Tuple2<>(field.get1(), lineNumber));
				
				// Stop when done.
				if (field.get2().booleanValue()) {
					return new Tuple2<>(fields, baseLineNumber);
				}
			}
		}
		
		/**
		 * Reads the next field.
		 * 
		 * @return The read field and a flag indicating whether the line has ended or not.
		 */
		protected Tuple2<String, Boolean> readField() {
			if (_input.scanChar('"')) {
				// Quoted field.
				final StringBuilder buffer = new StringBuilder();
				while (true) {
					// Read some data.
					buffer.append(_input.scanToChar('"'));
					
					if (_input.scanChar('"')) {
						// Quote
						if (_input.scanChar('"')) {
							// Escaped double quote.
							buffer.append('"');
						} else if (_input.scanSeq(_delimiter)) {
							// Delimiter.
							return new Tuple2<>(buffer.toString(), false);
						} else if (readEndOfLine()) {
							// EOL.
							return new Tuple2<>(buffer.toString(), true);
						} else {
							// Data.
							final int lineNumber = _lineNumber.get();
							skipEndOfLine();
							throw new CSVException("Invalid input at line " + lineNumber);
						}
					} else if (_input.isEof()) {
						// EOF
						throw new CSVException("Invalid input at line " + _lineNumber.get());
					} else {
						throw new InternalException("Invalid input");
					}
				}
			} else {
				// Unquoted field.
				final StringBuilder buffer = new StringBuilder();
				while (true) {
					// Read some data.
					buffer.append(_input.scanToChar(c -> _delimiterHead == c || '"' == c || '\n' == c || '\r' == c));
					
					if (_input.scanChar('"')) {
						// Quote.
						final int lineNumber = _lineNumber.get();
						skipEndOfLine();
						throw new CSVException("Invalid input at line " + lineNumber);
					} else if (_input.scanSeq(_delimiter)) {
						// Delimiter.
						return new Tuple2<>(buffer.toString(), false);
					} else if (_input.scanChar(_delimiterHead)) {
						// False delimiter.
						buffer.append(_delimiterHead);
					} else if (readEndOfLine()) {
						// EOL.
						return new Tuple2<>(buffer.toString(), true);
					} else {
						throw new InternalException("Unexcepted input at line " + _lineNumber.get());
					}
				}
			}
		}
		
		protected boolean readEndOfLine() {
			if (_input.scanChar('\r')) {
				// EOL (Windows/Mac).
				_input.scanChar('\n');
				return true;
			} else if (_input.scanChar('\n')) {
				// EOL (Unix).
				return true;
			} else if (_input.isEof()) {
				// EOF.
				return true;
			} else {
				return false;
			}
		}
		
		protected void skipEndOfLine() {
			_input.scanToChar(c -> '\r' == c || '\n' == c);
			_input.scanChar('\r');
			_input.scanChar('\n');
		}
	}
	
	/** Field reader. */
	protected final FieldReader _reader;
	
	/**
	 * Closes the underlying reader providing the input to this reader.
	 */
	@Override
	public void close() {
		_reader.close();
	}
	
	// Delimiter.
	
	/**
	 * Gets the CSV field delimiter of this reader.
	 *
	 * @return The delimiter.
	 */
	public String getDelimiter() {
		return _reader.getDelimiter();
	}
	
	// Headers.
	
	/** Headers of the CSV fields. */
	protected final List<? extends CSVHeader<?>> _headers;
	
	/**
	 * Gets the headers of this reader.
	 * 
	 * @return An unmodifiable collection of the headers.
	 */
	public List<? extends CSVHeader<?>> getHeaders() {
		return _headers;
	}
	
	// Options.
	
	/** Options. */
	protected final Set<CSVOption> _options;
	
	/**
	 * Gets the options of this reader.
	 * 
	 * @return An unmodifiable set of the options.
	 */
	public Set<CSVOption> getOptions() {
		return _options;
	}
	
	// Lines.
	
	/** Lines of the CSV table. */
	protected final Iterator<Record<CSVHeader<?>>> _lines = new LookAheadIterator<Record<CSVHeader<?>>>() {
		@Override
		protected Maybe<? extends Record<CSVHeader<?>>> pull() {
			return readNextLine();
		}
	};
	
	/**
	 * Indicates whether another CSV line is available or not
	 *
	 * @return <code>true</code> when another line is available, <code>false</code> otherwise.
	 */
	@Override
	public boolean hasNext() {
		return _lines.hasNext();
	}
	
	/**
	 * Gets the next CSV line.
	 *
	 * @return The record of the field composing the CSV line.
	 * @throws NoSuchElementException When no lines are available.
	 */
	@Override
	public Record<CSVHeader<?>> next() {
		return _lines.next();
	}
	
	/**
	 * Reads the next CSV line.
	 * <p>
	 * This methods ignores invalid lines
	 * 
	 * @return The next valid line, or nothing the input has ended.
	 */
	protected Maybe<Record<CSVHeader<?>>> readNextLine() {
		while (true) {
			// Check EOF.
			if (_reader.isEof()) {
				return Maybe.none();
			} else {
				try {
					// Read a line.
					return Maybe.some(readLine());
				} catch (final CSVException exception) {
					// Ignore the invalid line.
					if (_options.contains(CSVOption.IGNORE_INVALID_LINES)) {
						// Log.
						if (CSVUtils.LOGGER.isWarnEnabled()) {
							CSVUtils.LOGGER.warn("Ignoring invalid line", exception);
						}
					} else {
						throw exception;
					}
				}
			}
		}
	}
	
	/**
	 * Reads the next line.
	 * 
	 * @return The read line, or nothing the input has ended.
	 */
	protected Record<CSVHeader<?>> readLine() {
		// Read the fields.
		// Note: eagerly read the fields before deserializing them in order to consume the whole line in case of error.
		final Tuple2<List<Tuple2<String, Integer>>, Integer> fieldsResult = _reader.readFields();
		final Iterator<Tuple2<String, Integer>> fields = fieldsResult.get1().iterator();
		final int lineNumber = fieldsResult.get2().intValue();
		
		// Build the line.
		final RecordBuilder<CSVHeader<?>, Record<CSVHeader<?>>> line = new SimpleRecordBuilder<>();
		final Iterator<? extends CSVHeader<?>> headers = _headers.iterator();
		while (true) {
			if (headers.hasNext() && fields.hasNext()) {
				// Deserialize and add the field.
				final CSVHeader<?> header = headers.next();
				final Tuple2<String, Integer> field = fields.next();
				line.addAll(deserializeField(header, field.get1(), field.get2().intValue()));
			} else if (_options.contains(CSVOption.CHECK_CARDINALITY) && (headers.hasNext() || fields.hasNext())) {
				// Mismatching cardinalities.
				throw new CSVException("Invalid cardinality at line	" + lineNumber);
			} else {
				return line.build();
			}
		}
	}
	
	protected <V> Maybe<Field<CSVHeader<V>, V>> deserializeField(final CSVHeader<V> header, final String representation, final int lineNumber) {
		// Trim.
		final String trimmedRepresentation;
		if (_options.contains(CSVOption.TRIM_FIELDS)) {
			trimmedRepresentation = TextUtils.trim(representation, CharPredicates.whitespace()).toString();
		} else {
			trimmedRepresentation = representation;
		}
		
		// Strip.
		if (trimmedRepresentation.isEmpty()) {
			return Maybe.none();
		} else {
			// Deserialize.
			try {
				return Maybe.some(Fields.fromKeyAndValue(header, header.getSerializer().deserialize(trimmedRepresentation)));
			} catch (final Exception exception) {
				throw new CSVException("Invalid representation \"" + trimmedRepresentation + "\" for field \"" + header + "\" at line " + lineNumber, exception);
			}
		}
	}
}
