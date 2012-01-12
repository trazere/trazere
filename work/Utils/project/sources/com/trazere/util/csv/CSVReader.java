/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.csv;

import com.trazere.util.lang.InternalException;
import com.trazere.util.record.DuplicateFieldException;
import com.trazere.util.text.Scanner;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CSVReader} class provides support for reading CSV files.
 * <p>
 * This class behaves like an iterator over the CSV lines. It takes an arbitrar reader as input, parse it, and produces maps of the values of the CSV fields
 * identified by the corresponding headers.
 * <p>
 * This reader is able to check the cardinality the data lines against the cardinality of the headers. It may use the invalid entries, ignore them or hang on
 * the first one.
 * <p>
 * This reader also supports the self-describing CSV inputs. When no headers are provided, the first CSV line is read and used as header list.
 * 
 * @see CSVLine
 */
public class CSVReader {
	private static final Logger _LOGGER = LoggerFactory.getLogger(CSVReader.class);
	
	/** Scanner of the CSV input. */
	protected final Scanner _scanner;
	
	/** Delimiter of the CSV fields. */
	protected final String _delimiter;
	
	/** Headers of the CSV input. */
	protected final List<String> _headers;
	
	/** Read options. */
	protected final Set<CSVReaderOption> _options;
	
	/** Flag indicating whether the input reader has been exhausted or not. */
	protected boolean _eof = false;
	
	/** Flag indicating whether the next entry has been fetched or not. */
	protected boolean _lookAhead = false;
	
	/** Next CSV line. */
	protected CSVLine _nextEntry = null;
	
	/** Line number of the next CSV entry in the input. */
	protected int _nextEntryLine = 0;
	
	/** Line number of the current CSV entry. */
	protected int _line = -1;
	
	protected final char _delimiterChar;
	protected final String _specialChars;
	
	/**
	 * Instantiate a new CSV reader using the given reader, delimiter and options. The CSV input is considered as self-describing. Its first line is read in
	 * order to determine the headers.
	 * 
	 * @param reader Reader providing the CSV input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param options Options.
	 * @throws IOException
	 */
	public CSVReader(final Reader reader, final String delimiter, final EnumSet<CSVReaderOption> options)
	throws IOException {
		assert null != reader;
		assert null != delimiter;
		assert delimiter.length() > 0;
		assert null != options;
		
		// Initialization.
		_scanner = new Scanner(reader);
		_delimiter = delimiter;
		_delimiterChar = delimiter.charAt(0);
		_specialChars = _delimiterChar + "\"\n\r";
		_options = Collections.unmodifiableSet(EnumSet.copyOf(options));
		
		// Read the headers from the input.
		final List<String> headers = readLine();
		if (null != headers) {
			_nextEntryLine += 1;
			_headers = Collections.unmodifiableList(new ArrayList<String>(headers));
		} else {
			throw new EOFException("Missing header line");
		}
	}
	
	/**
	 * Instantiate a new CSV reader using the given reader, delimiter, headers, and options.
	 * 
	 * @param reader Reader providing the CSV input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param headers Headers of the CSV input.
	 * @param options Options.
	 */
	public CSVReader(final Reader reader, final String delimiter, final List<String> headers, final Set<CSVReaderOption> options) {
		assert null != reader;
		assert null != delimiter;
		assert delimiter.length() > 0;
		assert null != headers;
		assert null != options;
		
		// Initialize the instance.
		_scanner = new Scanner(reader);
		_delimiter = delimiter;
		_delimiterChar = delimiter.charAt(0);
		_specialChars = _delimiterChar + "\"\n\r";
		_headers = Collections.unmodifiableList(new ArrayList<String>(headers));
		_options = Collections.unmodifiableSet(EnumSet.copyOf(options));
	}
	
	/**
	 * Get the delimiter of the CSV fields of the receiver reader.
	 * 
	 * @return The delimiter string.
	 */
	public String getDelimiter() {
		return _delimiter;
	}
	
	/**
	 * Get the headers of the CSV input of the receiver reader.
	 * 
	 * @return An unmodifiable list of the headers.
	 */
	public List<String> getHeaders() {
		return _headers;
	}
	
	/**
	 * Get the options of the receiver reader.
	 * 
	 * @return An unmodifiable set of the options.
	 */
	public Set<CSVReaderOption> getOptions() {
		return _options;
	}
	
	/**
	 * Test whether another CSV entry is available from the input.
	 * <p>
	 * This methods does not garantee that the entry will be valid, only that it is available so that the {@link #next()} method can be called.
	 * 
	 * @return <code>true</code> if another entry is available, <code>false</code> if the input has been exausted or if the reader hangs because of an invalid
	 *         line.
	 * @throws IOException
	 */
	public boolean hasNext()
	throws IOException {
		readEntry();
		return !_eof;
	}
	
	protected void readEntry()
	throws IOException {
		// Test that input is not exhausted and that the next line has not already been read ahead.
		if (!_eof && !_lookAhead) {
			_lookAhead = true;
			_nextEntry = null;
			
			do {
				// Read the next line.
				_LOGGER.debug("Fetching line (" + (_nextEntryLine + 1) + ")");
				final List<String> line = readLine();
				if (null != line) {
					_nextEntryLine += 1;
					
					// Test the cardinality.
					if (_options.contains(CSVReaderOption.CHECK_CARDINALITY) && _headers.size() != line.size()) {
						_LOGGER.warn("Cardinality check failed for line: " + line);
					} else {
						// Build the entry.
						final CSVLineBuilder builder = new CSVLineBuilder();
						final Iterator<String> headers = _headers.iterator();
						final Iterator<String> values = line.iterator();
						while (headers.hasNext() && values.hasNext()) {
							final String header = headers.next();
							if (header.length() > 0 && !builder.contains(header)) {
								final String value = values.next();
								final String trimmedValue = _options.contains(CSVReaderOption.TRIM_FIELDS) ? value.trim() : value;
								final String stripedValue = trimmedValue.length() > 0 || !_options.contains(CSVReaderOption.STRIP_EMPTY_FIELDS) ? trimmedValue : null;
								try {
									builder.add(header, stripedValue);
								} catch (final DuplicateFieldException exception) {
									throw new InternalException(exception);
								}
							}
						}
						_nextEntry = builder.build();
					}
				} else {
					_eof = true;
				}
			} while (!_eof && _options.contains(CSVReaderOption.SKIP_INVALID_LINES) && null == _nextEntry);
		}
	}
	
	protected List<String> readLine()
	throws IOException {
		// Test for EOF.
		if (_scanner.eof()) {
			return null;
		}
		
		// Read the line.
		final List<String> line = new ArrayList<String>();
		readLine(line);
		return line;
	}
	
	protected void readLine(final List<String> line)
	throws IOException {
		while (true) {
			// Test for EOF.
			if (_scanner.eof()) {
				break;
			}
			
			// Read the cell.
			final StringBuilder buffer = new StringBuilder();
			final boolean delimiter = readCell(buffer);
			line.add(buffer.toString());
			
			// Test for line end.
			if (!delimiter) {
				break;
			}
		}
	}
	
	protected boolean readCell(final StringBuilder buffer)
	throws IOException {
		while (true) {
			// Read the data.
			final String part = _scanner.scanUpToAnyChar(_specialChars);
			buffer.append(part);
			
			if (_scanner.scanChar('"')) {
				// Quoted content.
				readQuotedCell(buffer);
			} else if (_scanner.scanString(_delimiter)) {
				// Delimiter.
				return true;
			} else if (_scanner.scanChar(_delimiterChar)) {
				// False delimiter.
				buffer.append(_delimiterChar);
			} else if (_scanner.scanChar('\r')) {
				// Line end (Windows/Mac).
				_scanner.scanChar('\n');
				return false;
			} else if (_scanner.scanChar('\n')) {
				// Line end (Unix).
				return false;
			} else if (_scanner.eof()) {
				// EOF.
				return false;
			} else {
				throw new RuntimeException("Internal error");
			}
		}
	}
	
	protected void readQuotedCell(final StringBuilder buffer)
	throws IOException {
		while (true) {
			// Read the data.
			final String part = _scanner.scanUpToChar('"');
			buffer.append(part);
			
			if (_scanner.scanChar('"')) {
				// Double quote
				if (_scanner.scanChar('"')) {
					// Escaped double quote.
					buffer.append('"');
				} else {
					return;
				}
			} else if (_scanner.eof()) {
				// EOF
				return;
			} else {
				throw new RuntimeException("Internal error");
			}
		}
	}
	
	/**
	 * Read the next CSV entry. This method should not be called if <code>hasNext</code> returns <code>false</code>.
	 * 
	 * @return A map of the values of the CSV fields identified by their corresponding headers, or <code>null</code> if the entry is invalid.
	 * @throws IOException
	 */
	public CSVLine next()
	throws IOException {
		// Read one line ahead.
		readEntry();
		
		// Consume the entry.
		final CSVLine entry = _nextEntry;
		_lookAhead = false;
		_nextEntry = null;
		_line = _nextEntryLine;
		
		return entry;
	}
	
	/**
	 * Get the line number of the current CSV entry (the entry returned by the last call to {@link #next()}.
	 * 
	 * @return The line number, or <code>-1</code> if {@link #next()} has never been called.
	 */
	public int getLine() {
		return _line;
	}
	
	/**
	 * Close the underlying reader.
	 * 
	 * @throws IOException
	 */
	public void close()
	throws IOException {
		_scanner.close();
	}
}
