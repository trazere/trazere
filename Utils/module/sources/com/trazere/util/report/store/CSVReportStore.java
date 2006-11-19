package com.trazere.util.report.store;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trazere.util.Assert;
import com.trazere.util.csv.CSVLine;
import com.trazere.util.csv.CSVReader;
import com.trazere.util.csv.CSVReaderOption;
import com.trazere.util.csv.CSVWriter;
import com.trazere.util.csv.CSVWriterOption;
import com.trazere.util.function.Filter;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportLevel;

/**
 * The <code>CSVReportStore</code> abstract class represents report stores relying on CSV files.
 * 
 * @param <Entry> Type of the report entries.
 */
public abstract class CSVReportStore<Entry extends ReportEntry<?, ?>>
implements ReportStore<Entry> {
	private static final Log LOG = LogFactory.getLog(CSVReportStore.class);

	/** Default delimiter of the CSV fields. */
	public static final String DELIMITER = ";";

	/** Default header of the report entry level fields in the CSV files. */
	public static final String LEVEL_HEADER = "Level";

	/** Default header of the report entry date fields in the CSV files. */
	public static final String DATE_HEADER = "Date";

	/** Default format of the report entry date fields in the CSV files. */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	/** Path of the backing CSV file. */
	protected final File _path;

	/** Report entries of the CSV file. Many be <code>null</code> when the entries have not been loaded. */
	protected List<ReportStoreEntry<Entry>> _entries = null;

	/** Open writer to the CSV file. May be <code>null</code>. */
	protected CSVWriter _writer = null;

	/**
	 * Instanciate a new store using the given path.
	 * 
	 * @param path Path of the backing CSV file.
	 */
	public CSVReportStore(final File path) {
		Assert.notNull(path);

		// Initializtion.
		_path = path;
	}

	/**
	 * Get the path of the backing CSV file of the receiver store.
	 * 
	 * @return The path.
	 */
	public File getPath() {
		return _path;
	}

	/**
	 * Get the header of the report entry level fields in the CSV file of the receiver store.
	 * 
	 * @return The header.
	 */
	public String getLevelHeader() {
		return LEVEL_HEADER;
	}

	/**
	 * Get the header of the report entry date fields in the CSV file of the receiver store.
	 * 
	 * @return The header.
	 */
	public String getDateHeader() {
		return DATE_HEADER;
	}

	/**
	 * Get the format of the report entry dates in the CSV file of the receiver store.
	 * 
	 * @return The date format.
	 */
	public DateFormat getDateFormat() {
		return DATE_FORMAT;
	}

	/**
	 * Get the field headers for the serialized report entries.
	 * <p>
	 * The headers must not conflict with the date and level headers.
	 * 
	 * @return The headers.
	 * @see #getDateHeader()
	 * @see #getLevelHeader()
	 */
	protected abstract List<String> getEntryHeaders();

	public void report(final ReportLevel level, final Entry entry)
	throws ReportException {
		Assert.notNull(level);
		Assert.notNull(entry);

		final Date date = new Date();

		// Build the line.
		final CSVLine.Builder builder = new CSVLine.Builder(serializeEntry(entry));
		builder.setField(getDateHeader(), getDateFormat().format(date));
		builder.setField(getLevelHeader(), level.toString());

		// Write the line.
		try {
			openWriter();
			_writer.writeLine(builder.build());
		} catch (final IOException exception) {
			throw new ReportException("Failed writing line for entry " + entry, exception);
		}

		// Fill the cache.
		if (null != _entries) {
			_entries.add(new ReportStoreEntry<Entry>(date, level, entry));
		}
	}

	/**
	 * Serialize the given report entry to a set of CSV fields.
	 * 
	 * @param entry The entry to serialize.
	 * @return The values of the CSV fields identified by their headers.
	 * @throws ReportException
	 */
	protected abstract Map<String, String> serializeEntry(final Entry entry)
	throws ReportException;

	protected void openWriter()
	throws IOException {
		if (null == _writer) {
			// Build the headers.
			final List<String> headers = buildHeaders();

			if (_path.exists()) {
				// Get the current headers.
				final CSVReader reader = getReader();
				final List<String> currentHeaders = reader.getHeaders();
				reader.close();

				// Build the writer.
				if (currentHeaders.containsAll(headers)) {
					_writer = getWriter(currentHeaders, true);
				} else {
					LOG.warn("Reseting report file at path " + _path + " with incompatilble headers.");
					_writer = getWriter(headers, false);
					_entries = new ArrayList<ReportStoreEntry<Entry>>();
				}
			} else {
				// Build the writer for a new file.
				LOG.info("Creating report file at path " + _path);
				_writer = getWriter(headers, false);
				_entries = new ArrayList<ReportStoreEntry<Entry>>();
			}
		}
	}

	/**
	 * Test wether the CSV file of the receiver store is currently opened for writing.
	 * 
	 * @return <code>true</code> when the file of opened for writing, <code>false</code> otherwise.
	 */
	public boolean isWritting() {
		return null != _writer;
	}

	/**
	 * Write any buffered data and close the CSV file.
	 * 
	 * @throws IOException
	 */
	public void closeWriter()
	throws IOException {
		// Close and release the writer if needed.
		if (null != _writer) {
			_writer.close();
			_writer = null;
		}
	}

	public int countEntries(final Filter<ReportStoreEntry<Entry>> filter)
	throws ReportException {
		// Load the entries.
		try {
			load();
		} catch (final IOException exception) {
			throw new ReportException(exception);
		}

		// Count.
		return _entries.size();
	}

	public List<ReportStoreEntry<Entry>> getEntries(final Filter<ReportStoreEntry<Entry>> filter, final int limit, final boolean fromEnd)
	throws ReportException {
		// Load the entries.
		try {
			load();
		} catch (final IOException exception) {
			throw new ReportException(exception);
		}

		// Get the entries.
		return ReportStoreUtils.filterEntries(_entries, filter, limit, fromEnd);
	}

	protected void load()
	throws IOException, ReportException {
		if (null != _entries) {
			// Stop writing first if needed.
			closeWriter();

			// Read the ile.
			_entries = new ArrayList<ReportStoreEntry<Entry>>();

			if (_path.exists()) {
				// Open the file.
				final CSVReader reader = getReader();
				try {
					// Check the current headers.
					final List<String> currentHeaders = reader.getHeaders();
					if (currentHeaders.containsAll(buildHeaders())) {
						// Read the entries.
						while (reader.hasNext()) {
							final CSVLine line = reader.next();

							// Read the date.
							final String dateField = line.getField(getDateHeader());
							if (null == dateField) {
								LOG.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (missing date)");
								continue;
							}

							final Date date;
							try {
								date = getDateFormat().parse(dateField);
							} catch (final ParseException exception) {
								LOG.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (invalid date)");
								continue;
							}

							// Read the level.
							final String levelField = line.getField(getLevelHeader());
							if (null == levelField) {
								LOG.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (missing level)");
								continue;
							}

							final ReportLevel level;
							try {
								level = ReportLevel.valueOf(levelField);
							} catch (final IllegalArgumentException exception) {
								LOG.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (invalid level)");
								continue;
							}

							// Read the entry.
							final Entry entry = buildEntry(line);
							if (null == entry) {
								LOG.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (invalid entry)");
								continue;
							}

							// Add the entry.
							_entries.add(new ReportStoreEntry<Entry>(date, level, entry));
						}
					} else {
						LOG.warn("Ignoring content of report file at path " + _path + " with incompatilble headers.");
					}
				} finally {
					reader.close();
				}
			} else {
				// Build the writer for a new file.
				LOG.info("No report file at path " + _path);
			}
		}
	}

	/**
	 * Build the entry corresponding to the given serialized CSV line.
	 * 
	 * @param line CSV line to read.
	 * @return The built entry, or <code>null</code> if the line is not valid.
	 * @throws ReportException
	 */
	protected abstract Entry buildEntry(final CSVLine line)
	throws ReportException;

	/**
	 * Test wether the file of the receiver store has been loaded (cached).
	 * 
	 * @return <code>true</code> when the file is loaded, <code>false</code> otherwise.
	 */
	public boolean isLoaded() {
		return null != _entries;
	}

	protected List<String> buildHeaders() {
		final List<String> headers = new ArrayList<String>();
		headers.add(getDateHeader());
		headers.add(getLevelHeader());
		headers.addAll(getEntryHeaders());
		return headers;
	}

	protected CSVReader getReader()
	throws IOException {
		// Open the file.
		final FileReader reader = new FileReader(_path);
		try {
			// Build the reader.
			return new CSVReader(reader, DELIMITER, EnumSet.of(CSVReaderOption.CHECK_CARDINALITY, CSVReaderOption.SKIP_INVALID_LINES, CSVReaderOption.STRIP_EMPTY_FIELDS));
		} catch (final IOException exception) {
			reader.close();
			throw exception;
		}
	}

	protected CSVWriter getWriter(final List<String> headers, final boolean append)
	throws IOException {
		// Open the file.
		final FileWriter writer = new FileWriter(_path, append);
		try {
			// Build the writer.
			final CSVWriter csvWriter = new CSVWriter(writer, DELIMITER, headers, EnumSet.noneOf(CSVWriterOption.class));

			// Write the headers.
			if (!append) {
				csvWriter.writeHeaders();
			}

			return csvWriter;
		} catch (final IOException exception) {
			writer.close();
			throw exception;
		}
	}
}
