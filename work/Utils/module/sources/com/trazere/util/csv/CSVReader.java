/*
 *  Copyright 2006 Julien Dufour
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

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trazere.util.Assert;
import com.trazere.util.text.Scanner;

/**
 * The <code>CSVReader</code> class provides a tool to read CSV files.
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
	private static final Log LOG = LogFactory.getLog(CSVReader.class);

	/** Scanner of the CSV input. */
	protected final Scanner _scanner;

	/** Delimiter of the CSV fields. */
	protected final String _delimiter;

	/** Headers of the CSV input. */
	protected final List<String> _headers;

	/** Read options. */
	protected final Set<CSVReaderOption> _options;

	/** Flag indicating wether the input reader has been exhausted or not. */
	protected boolean _eof = false;

	/** Flag indicating wether the next entry has been fetched or not. */
	protected boolean _lookAhead = false;

	/** Next CSV line. */
	protected CSVLine _nextEntry = null;

	/** Line number of the next CSV entry in the input. */
	protected int _nextEntryLine = 0;

	/** Line number of the current CSV entry. */
	protected int _line = -1;

	/**
	 * Instanciate a new CSV reader using the given reader, delimiter and options. The CSV input is considered as self-describing. Its first line is read in
	 * order to determine the headers.
	 * 
	 * @param reader Reader providing the CSV input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param options Options.
	 * @throws IOException
	 */
	public CSVReader(final Reader reader, final String delimiter, final EnumSet<CSVReaderOption> options)
	throws IOException {
		Assert.notNull(reader);
		Assert.notNull(delimiter);
		Assert.expression(delimiter.length() > 0, "empty delimiter");
		Assert.notNull(options);

		// Initialization.
		_scanner = new Scanner(reader);
		_delimiter = delimiter;
		_options = Collections.unmodifiableSet(EnumSet.copyOf((Collection<CSVReaderOption>) options)); // FIXME: useless cast, eclipse bug workaround

		final List<String> headers = new ArrayList<String>();
		_headers = Collections.unmodifiableList(headers);

		// Read the headers from the input.
		final List<String> headers_ = readLine();
		if (null != headers_) {
			_nextEntryLine += 1;
			headers.addAll(headers);
		} else {
			throw new EOFException("Missing header line");
		}
	}

	/**
	 * Instanciate a new CSV reader using the given reader, delimiter, headers, and options.
	 * 
	 * @param reader Reader providing the CSV input.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param headers Headers of the CSV input.
	 * @param options Options.
	 */
	public CSVReader(final Reader reader, final String delimiter, final List<String> headers, final Set<CSVReaderOption> options) {
		Assert.notNull(reader);
		Assert.notNull(delimiter);
		Assert.expression(delimiter.length() > 0, "empty delimiter");
		Assert.notNull(headers);
		Assert.notNull(options);

		// Initialize the instance.
		_scanner = new Scanner(reader);
		_delimiter = delimiter;
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
	 * Test wether another CSV entry is available from the input.
	 * <p>
	 * This methods does not garantee that the entry will be valid, only that it is available so that the {@link #next()} method can be called.
	 * 
	 * @return <code>true</code> if another entry is available, <code>false</code> if the input has been exausted or if the reader hangs because of an
	 *         invalid line.
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
				LOG.debug("Fetching line (" + (_nextEntryLine + 1) + ")");
				final List<String> line = readLine();
				if (null != line) {
					_nextEntryLine += 1;

					// Test the cardinality.
					if (_options.contains(CSVReaderOption.CHECK_CARDINALITY) && (_headers.size() != line.size())) {
						LOG.warn("Cardinality check failed for line: " + line);
					} else {
						// Build the entry.
						final CSVLine.Builder builder = new CSVLine.Builder();
						final Iterator<String> headers = _headers.iterator();
						final Iterator<String> values = line.iterator();
						while (headers.hasNext() && values.hasNext()) {
							final String header = headers.next();
							final String value_ = values.next();
							final String value = _options.contains(CSVReaderOption.TRIM_FIELDS) ? value_.trim() : value_;
							if (value.length() > 0 || !_options.contains(CSVReaderOption.STRIP_EMPTY_FIELDS)) {
								builder.setField(header, value);
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
		final char delimiter = _delimiter.charAt(0);
		final String upto = delimiter + "\"\n\r";

		while (true) {
			// Read the data.
			final String part = _scanner.scanUpToAnyChar(upto);
			buffer.append(part);

			if (_scanner.scanChar('"')) {
				// Quoted content.
				readQuotedCell(buffer);
			} else if (_scanner.scanString(_delimiter)) {
				// Delimiter.
				return true;
			} else if (_scanner.scanChar(delimiter)) {
				// False delimiter.
				buffer.append(delimiter);
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
