/*
 *  Copyright 2006-2015 Julien Dufour
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

import com.trazere.core.functional.Function;
import com.trazere.core.record.Record;
import com.trazere.core.text.CharPredicates;
import com.trazere.core.text.Joiner;
import com.trazere.core.text.Joiners;
import com.trazere.core.text.TextUtils;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link CSVWriter} class implements writers of a CSV formatted table.
 * 
 * @since 2.0
 */
public class CSVWriter
implements Closeable {
	/**
	 * Instantiates a new CSV writer.
	 * 
	 * @param writer Writer receiving the CSV output.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param headers Headers of the CSV fields.
	 * @param options Options.
	 * @since 2.0
	 */
	public CSVWriter(final Writer writer, final String delimiter, final List<? extends CSVHeader<?>> headers, final Set<CSVOption> options) {
		assert null != writer;
		assert null != delimiter;
		assert null != headers;
		assert null != options;
		
		// Checks.
		if (headers.isEmpty()) {
			throw new IllegalArgumentException("Lines should have at least one header");
		}
		
		// Initialization.
		_writer = writer;
		_delimiter = delimiter;
		_headers = Collections.unmodifiableList(new ArrayList<>(headers));
		_options = Collections.unmodifiableSet(EnumSet.copyOf(options));
	}
	
	// Output.
	
	/**
	 * CSV output writer.
	 * 
	 * @since 2.0
	 */
	protected final Writer _writer;
	
	/**
	 * Flushes the pending output of this writer.
	 * <p>
	 * This methods simply flushes the underlying writer.
	 * 
	 * @since 2.0
	 */
	public void flush() {
		try {
			_writer.flush();
		} catch (final IOException exception) {
			throw new CSVException(exception);
		}
	}
	
	/**
	 * Closes this writer.
	 * <p>
	 * This methods simply closes the underlying writer.
	 * 
	 * @since 2.0
	 */
	@Override
	public void close() {
		try {
			_writer.close();
		} catch (final IOException exception) {
			throw new CSVException(exception);
		}
	}
	
	// Delimiter.
	
	/**
	 * Delimiter of the CSV fields.
	 * 
	 * @since 2.0
	 */
	protected final String _delimiter;
	
	/**
	 * Gets the CSV field delimiter of this writer.
	 * 
	 * @return The delimiter.
	 * @since 2.0
	 */
	public String getDelimiter() {
		return _delimiter;
	}
	
	// Headers.
	
	/**
	 * Headers of the CSV fields.
	 * 
	 * @since 2.0
	 */
	protected final List<? extends CSVHeader<?>> _headers;
	
	/**
	 * Gets the headers of this writer.
	 * 
	 * @return An unmodifiable collection of the headers.
	 * @since 2.0
	 */
	public List<? extends CSVHeader<?>> getHeaders() {
		return _headers;
	}
	
	// Options.
	
	/**
	 * Options.
	 * 
	 * @since 2.0
	 */
	protected final Set<CSVOption> _options;
	
	/**
	 * Gets the options of this writer.
	 * 
	 * @return An unmodifiable set of the options.
	 * @since 2.0
	 */
	public Set<CSVOption> getOptions() {
		return _options;
	}
	
	// Lines.
	
	/**
	 * Writes a CSV line containing the headers.
	 * 
	 * @since 2.0
	 */
	public void writeHeaders() {
		try {
			writeLine(header -> header.getLabel());
		} catch (final Exception exception) {
			throw new CSVException("Failed writing headers " + _headers, exception);
		}
	}
	
	/**
	 * Writes the given CSV line.
	 * 
	 * @param line Line to write.
	 * @since 2.0
	 */
	public void writeLine(final Record<CSVHeader<?>> line) {
		try {
			writeLine(header -> serializeField(line, header));
		} catch (final Exception exception) {
			throw new CSVException("Failed writing line " + line, exception);
		}
	}
	
	private static <V> String serializeField(final Record<CSVHeader<?>> line, final CSVHeader<V> header) {
		return line.get(header).map(value -> header.getSerializer().serialize(value)).get("");
	}
	
	/**
	 * Writes the given CSV line.
	 * 
	 * @param representations Representations of the fields.
	 * @since 2.0
	 */
	protected void writeLine(final Function<? super CSVHeader<?>, String> representations) {
		try {
			// Write the fields.
			final Joiner<CSVHeader<?>> joiner = Joiners.joiner(header -> renderField(representations.evaluate(header)), false, _delimiter);
			joiner.join(_headers, _writer);
			
			// Write the new line.
			_writer.write("\n");
		} catch (final Exception exception) {
			throw new CSVException("Failed writing line " + representations, exception);
		}
	}
	
	/**
	 * Render the given CSV field.
	 * 
	 * @param representation Representation of the field.
	 * @return The renderered representation of the field.
	 * @since 2.0
	 */
	protected final String renderField(final String representation) {
		// Trim.
		final String trimmedRepresentation;
		if (_options.contains(CSVOption.TRIM_FIELDS)) {
			trimmedRepresentation = TextUtils.trim(representation, CharPredicates.whitespace()).toString();
		} else {
			trimmedRepresentation = representation;
		}
		
		// Escape.
		if (TextUtils.contains(trimmedRepresentation, c -> '"' == c || '\n' == c || '\r' == c) || trimmedRepresentation.contains(_delimiter)) {
			return "\"" + trimmedRepresentation.replaceAll("[\"]", "\"\"") + "\"";
		} else {
			return trimmedRepresentation;
		}
	}
}
