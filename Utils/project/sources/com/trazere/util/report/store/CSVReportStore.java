/*
 *  Copyright 2006-2011 Julien Dufour
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
package com.trazere.util.report.store;

import com.trazere.util.csv.CSVLine;
import com.trazere.util.csv.CSVLineBuilder;
import com.trazere.util.csv.CSVReader;
import com.trazere.util.csv.CSVReaderOption;
import com.trazere.util.csv.CSVWriter;
import com.trazere.util.csv.CSVWriterOption;
import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.InternalException;
import com.trazere.util.record.DuplicateFieldException;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportLevel;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link CSVReportStore} abstract class represents report stores relying on CSV files.
 * 
 * @param <Entry> Type of the report entries.
 */
public abstract class CSVReportStore<Entry extends ReportEntry<?, ?>>
implements ReportStore<Entry> {
	private static final Logger _LOGGER = LoggerFactory.getLogger(CSVReportStore.class);
	
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
	 * Instantiate a new store using the given path.
	 * 
	 * @param path Path of the backing CSV file.
	 */
	public CSVReportStore(final File path) {
		assert null != path;
		
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
		assert null != level;
		assert null != entry;
		
		final Date date = new Date();
		
		// Build the line.
		final CSVLineBuilder builder = new CSVLineBuilder();
		try {
			builder.add(getDateHeader(), TextUtils.formatDate(getDateFormat(), date));
			builder.add(getLevelHeader(), level.toString());
		} catch (final DuplicateFieldException exception) {
			throw new InternalException(exception);
		}
		serializeEntry(entry, builder);
		
		// Write the line.
		try {
			open();
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
	 * @param builder Build of the CSV line.
	 * @throws ReportException
	 */
	protected abstract void serializeEntry(final Entry entry, final CSVLineBuilder builder)
	throws ReportException;
	
	public void sleep()
	throws ReportException {
		close();
	}
	
	/**
	 * Test whether the CSV file of the receiver store is currently opened for writing.
	 * 
	 * @return <code>true</code> when the file of opened for writing, <code>false</code> otherwise.
	 */
	public boolean isOpen() {
		return null != _writer;
	}
	
	protected void open()
	throws IOException {
		if (null == _writer) {
			// Open the report.
			final List<String> headers = buildHeaders();
			final Maybe<CSVReader> reader_ = getReader(headers);
			if (reader_.isSome()) {
				// Get the current headers.
				final CSVReader reader = reader_.asSome().getValue();
				final List<String> currentHeaders = reader.getHeaders();
				reader.close();
				
				// Build the writer for the current report.
				_writer = getWriter(currentHeaders, true);
			} else {
				// Build the writer for a new report.
				_LOGGER.info("Creating report file at path " + _path);
				_writer = getWriter(headers, false);
				_entries = new ArrayList<ReportStoreEntry<Entry>>();
			}
		}
	}
	
	/**
	 * Write any buffered data in the CSV file.
	 * 
	 * @throws ReportException
	 */
	public void flush()
	throws ReportException {
		// Flush the writer if needed.
		if (null != _writer) {
			try {
				_writer.flush();
			} catch (final IOException exception) {
				throw new ReportException(exception);
			}
		}
	}
	
	/**
	 * Write any buffered data and close the CSV file.
	 * 
	 * @throws ReportException
	 */
	public void close()
	throws ReportException {
		// Close and release the writer if needed.
		if (null != _writer) {
			try {
				_writer.close();
				_writer = null;
			} catch (final IOException exception) {
				throw new ReportException(exception);
			}
		}
	}
	
	public int countEntries(final Predicate1<? super ReportStoreEntry<Entry>, ReportException> filter)
	throws ReportException {
		// Load the entries.
		load();
		
		// Count.
		return _entries.size();
	}
	
	public List<ReportStoreEntry<Entry>> getEntries(final Predicate1<? super ReportStoreEntry<Entry>, ReportException> filter, final int limit, final boolean fromEnd)
	throws ReportException {
		// Load the entries.
		load();
		
		// Get the entries.
		return ReportStoreUtils.filterEntries(_entries, filter, limit, fromEnd);
	}
	
	/**
	 * Test whether the file of the receiver store has been loaded (cached).
	 * 
	 * @return <code>true</code> when the file is loaded, <code>false</code> otherwise.
	 */
	public boolean isLoaded() {
		return null != _entries;
	}
	
	/**
	 * Load the report from the CSV file.
	 * <p>
	 * Calling this method is not required because data is automatically when needed. It may however useful to preload the data in order to control time
	 * allocation.
	 * 
	 * @throws ReportException
	 */
	public void load()
	throws ReportException {
		if (null == _entries) {
			try {
				// First, ensure that the writer is closed.
				close();
				
				// Reset the entries.
				_entries = new ArrayList<ReportStoreEntry<Entry>>();
				
				// Open the report.
				final Maybe<CSVReader> reader_ = getReader(buildHeaders());
				if (reader_.isSome()) {
					final CSVReader reader = reader_.asSome().getValue();
					try {
						// Read the entries.
						while (reader.hasNext()) {
							final CSVLine line = reader.next();
							
							// Read the date.
							final String dateField = line.get(getDateHeader(), null);
							if (null == dateField) {
								_LOGGER.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (missing date)");
								continue;
							}
							
							final Maybe<Date> maybeDate = TextUtils.parseDate(getDateFormat(), dateField);
							if (maybeDate.isNone()) {
								_LOGGER.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (invalid date)");
								continue;
							}
							final Date date = maybeDate.asSome().getValue();
							
							// Read the level.
							final String levelField = line.get(getLevelHeader(), null);
							if (null == levelField) {
								_LOGGER.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (missing level)");
								continue;
							}
							
							final ReportLevel level;
							try {
								level = ReportLevel.valueOf(levelField);
							} catch (final IllegalArgumentException exception) {
								_LOGGER.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (invalid level)");
								continue;
							}
							
							// Read the entry.
							final Maybe<Entry> entry = buildEntry(line);
							if (entry.isNone()) {
								_LOGGER.warn("Ignoring invalid entry at line " + reader.getLine() + " from report file at path " + _path + " (invalid entry)");
								continue;
							}
							
							// Add the entry.
							_entries.add(new ReportStoreEntry<Entry>(date, level, entry.asSome().getValue()));
						}
					} finally {
						reader.close();
					}
				}
			} catch (final IOException exception) {
				throw new ReportException(exception);
			}
		}
	}
	
	/**
	 * Build the entry corresponding to the given serialized CSV line.
	 * 
	 * @param line CSV line to read.
	 * @return The built entry.
	 * @throws ReportException
	 */
	protected abstract Maybe<Entry> buildEntry(final CSVLine line)
	throws ReportException;
	
	protected List<String> buildHeaders() {
		final List<String> headers = new ArrayList<String>();
		headers.add(getDateHeader());
		headers.add(getLevelHeader());
		headers.addAll(getEntryHeaders());
		return headers;
	}
	
	protected Maybe<CSVReader> getReader(final List<String> headers)
	throws IOException {
		if (_path.exists()) {
			// Open the file.
			final FileReader reader = new FileReader(_path);
			try {
				// Build the CSV reader.
				final CSVReader csvReader = new CSVReader(reader, DELIMITER, EnumSet.of(CSVReaderOption.CHECK_CARDINALITY, CSVReaderOption.SKIP_INVALID_LINES, CSVReaderOption.STRIP_EMPTY_FIELDS));
				
				// Check the headers.
				final List<String> currentHeaders = csvReader.getHeaders();
				if (!currentHeaders.containsAll(headers)) {
					// Invalid headers.
					_LOGGER.warn("Ignoring report at path " + _path + " with incompatible headers " + currentHeaders);
					
					csvReader.close();
					return Maybe.none();
				}
				
				return Maybe.some(csvReader);
			} catch (final EOFException exception) {
				// Invalid report.
				_LOGGER.warn("Ignoring invalid report at path " + _path);
				
				reader.close();
				return Maybe.none();
			} catch (final IOException exception) {
				reader.close();
				throw exception;
			}
		} else {
			// Missing report.
			_LOGGER.warn("Ignoring missing report at path " + _path);
			
			return Maybe.none();
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
