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
package com.trazere.util.task;

import com.trazere.util.csv.CSVLine;
import com.trazere.util.csv.CSVLineBuilder;
import com.trazere.util.lang.InternalException;
import com.trazere.util.record.DuplicateFieldException;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.store.CSVReportStore;
import com.trazere.util.type.Maybe;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * The {@link AbstractTaskCSVReportStore} class represents task report stores relying on CSV files.
 * 
 * @param <Entry> Type of the report entries.
 */
public abstract class AbstractTaskCSVReportStore<Entry extends TaskReportEntry>
extends CSVReportStore<Entry> {
	public static final String NAME_HEADER = "Name";
	public static final String STATUS_HEADER = "Status";
	public static final String COMMENT_HEADER = "Comment";
	
	private static final List<String> HEADERS = Arrays.asList(new String[] {
	    NAME_HEADER,
	    STATUS_HEADER,
	    COMMENT_HEADER,
	});
	
	/**
	 * Instantiate a new store with the given path.
	 * 
	 * @param path Path of the backing CSV file.
	 */
	public AbstractTaskCSVReportStore(final File path) {
		super(path);
	}
	
	@Override
	protected List<String> getEntryHeaders() {
		return HEADERS;
	}
	
	@Override
	protected void serializeEntry(final Entry entry, final CSVLineBuilder builder)
	throws ReportException {
		assert null != entry;
		assert null != builder;
		
		try {
			// Name.
			builder.add(NAME_HEADER, entry.getName());
			
			// Status.
			builder.add(STATUS_HEADER, entry.getStatus().toString());
			
			// Comment.
			builder.add(COMMENT_HEADER, entry.getComment());
		} catch (final DuplicateFieldException exception) {
			throw new InternalException(exception);
		}
	}
	
	@Override
	protected Maybe<Entry> buildEntry(final CSVLine line)
	throws ReportException {
		// Get the name.
		final String name = line.get(NAME_HEADER, null);
		if (null == name) {
			return Maybe.none();
		}
		
		// Get the status.
		final String statusField = line.get(STATUS_HEADER, null);
		if (null == statusField) {
			return Maybe.none();
		}
		
		final TaskStatus status;
		try {
			status = TaskStatus.valueOf(statusField);
		} catch (final IllegalArgumentException exception) {
			return Maybe.none();
		}
		
		// Get the comment.
		final String comment = line.get(COMMENT_HEADER, null);
		
		// Build.
		return Maybe.some(buildEntry(line, name, status, comment));
	}
	
	protected abstract Entry buildEntry(final CSVLine line, final String name, final TaskStatus status, final String comment)
	throws ReportException;
}
