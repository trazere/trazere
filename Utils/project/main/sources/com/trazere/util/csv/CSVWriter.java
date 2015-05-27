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
package com.trazere.util.csv;

import com.trazere.util.lang.MutableBoolean;
import com.trazere.util.type.Maybe;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * The {@link CSVWriter} class provides support for writing CSV files.
 */
public class CSVWriter {
	/** Writer receiving the CSV output. */
	protected final Writer _writer;
	
	/** Delimiter of the CSV fields. */
	protected final String _delimiter;
	
	/** Headers of the CSV output. */
	protected final List<String> _headers;
	
	/** Options. */
	protected final Set<CSVWriterOption> _options;
	
	/**
	 * Instantiate a new CSV writer with the given writer, headers and options.
	 * 
	 * @param writer Writer receiving the CSV output.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param headers Headers of the CSV output.
	 * @param options Options.
	 */
	public CSVWriter(final Writer writer, final String delimiter, final List<String> headers, final EnumSet<CSVWriterOption> options) {
		assert null != writer;
		assert null != delimiter;
		assert delimiter.length() > 0;
		assert null != headers;
		
		// Initialization.
		_writer = writer;
		_delimiter = delimiter;
		_headers = Collections.unmodifiableList(new ArrayList<String>(headers));
		_options = Collections.unmodifiableSet(EnumSet.copyOf((Collection<CSVWriterOption>) options)); // FIXME: useless cast, eclipse bug workaround
	}
	
	/**
	 * Get the delimiter of the CSV fields of the receiver writer.
	 * 
	 * @return The delimiter string.
	 */
	public String getDelimiter() {
		return _delimiter;
	}
	
	/**
	 * Get the headers of the CSV output of receiver writer.
	 * 
	 * @return The headers.
	 */
	public List<String> getHeaders() {
		return _headers;
	}
	
	/**
	 * Get the options of the receiver writer.
	 * 
	 * @return An unmodifiable set of the options.
	 */
	public Set<CSVWriterOption> getOptions() {
		return _options;
	}
	
	/**
	 * Write a line containing the headers of the CSV output.
	 * 
	 * @throws IOException On failure.
	 */
	public void writeHeaders()
	throws IOException {
		boolean first = true;
		for (final String header : _headers) {
			// Delimiter.
			if (!first) {
				_writer.write(_delimiter);
			} else {
				first = false;
			}
			
			// Value.
			_writer.write(escapeValue(header));
		}
		
		// New line.
		_writer.write("\n");
	}
	
	/**
	 * Write the given CSV line.
	 * 
	 * @param line Values of the CSV fields of the line identified by their header.
	 * @throws IOException On failure.
	 */
	public void writeLine(final CSVLine line)
	throws IOException {
		final MutableBoolean first = new MutableBoolean(true);
		for (final String header : _headers) {
			// Delimiter.
			if (!first.get()) {
				_writer.write(_delimiter);
			} else {
				first.set(false);
			}
			
			// Value.
			final Maybe<String> maybeValue = line.getMaybe(header);
			if (maybeValue.isSome()) {
				final String value = maybeValue.asSome().getValue();
				if (null != value) {
					_writer.write(escapeValue(value));
				}
			}
		}
		
		// New line.
		_writer.write("\n");
	}
	
	/**
	 * Escape the given CSV field value if needed.
	 * 
	 * @param value Value to escape.
	 * @return The escapted value.
	 */
	public final String escapeValue(final String value) {
		assert null != value;
		
		if (value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0 || value.indexOf('"') >= 0 || value.indexOf(_delimiter) >= 0) {
			// Escape.
			final StringBuilder builder = new StringBuilder();
			builder.append('"').append(value.replaceAll("[\"]", "\"\"")).append('"');
			return builder.toString();
		} else {
			// Identity.
			return value;
		}
	}
	
	/**
	 * Flush the underlying writer of the receiver CSV writer.
	 * 
	 * @throws IOException On failure.
	 */
	public void flush()
	throws IOException {
		_writer.flush();
	}
	
	/**
	 * Close the underlying writer of the receiver CSV writer.
	 * 
	 * @throws IOException On failure.
	 */
	public void close()
	throws IOException {
		_writer.close();
	}
}
