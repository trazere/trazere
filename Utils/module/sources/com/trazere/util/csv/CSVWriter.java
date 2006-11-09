package com.trazere.util.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.trazere.util.Assert;

/**
 * The <code>CSVWriter</code> class provides a tool to write CSV files.
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
	 * Instanciate a new CSV writer with the given writer, headers and options.
	 * 
	 * @param writer Writer receiving the CSV output.
	 * @param delimiter Delimiter of the CSV fields.
	 * @param headers Headers of the CSV output.
	 * @param options Options.
	 */
	public CSVWriter(final Writer writer, final String delimiter, final List headers, final EnumSet<CSVWriterOption> options) {
		Assert.notNull(writer);
		Assert.notNull(delimiter);
		Assert.expression(delimiter.length() > 0, "Empty delimiter");
		Assert.notNull(headers);

		// Initialization.
		_writer = writer;
		_delimiter = delimiter;
		_headers = Collections.unmodifiableList(new ArrayList(headers));
		_options = Collections.unmodifiableSet(EnumSet.copyOf(options));
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
	 * @throws IOException
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
	 * @throws IOException
	 */
	public void writeLine(final CSVLine line)
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
			final String value = line.getField(header);
			if (null != value) {
				_writer.write(escapeValue(value));
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
		Assert.notNull(value);

		if (value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0 || value.indexOf('"') >= 0 || value.indexOf(_delimiter) >= 0) {
			// Escape.
			final StringBuilder builder = new StringBuilder();
			builder.append('"');
			builder.append(value.replaceAll("[\"]", "\"\""));
			builder.append('"');
			return builder.toString();
		} else {
			// Identity.
			return value;
		}
	}

	/**
	 * Close the underlying writer of the receiver CSV writer.
	 *
	 * @throws IOException
	 */
	public void close()
	throws IOException {
		_writer.close();
	}
}
