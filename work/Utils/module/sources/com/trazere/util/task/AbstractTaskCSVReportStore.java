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
package com.trazere.util.task;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.trazere.util.csv.CSVLine;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.store.CSVReportStore;

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
	protected void serializeEntry(final Entry entry, final CSVLine.Builder builder)
	throws ReportException {
		// Name.
		builder.setField(NAME_HEADER, entry.getName());
		
		// Status.
		builder.setField(STATUS_HEADER, entry.getStatus().toString());
		
		// Comment.
		if (null != entry.getComment()) {
			builder.setField(COMMENT_HEADER, entry.getComment());
		}
	}
	
	@Override
	protected Entry buildEntry(final CSVLine line)
	throws ReportException {
		// Get the name.
		final String name = line.getField(NAME_HEADER);
		if (null == name) {
			return null;
		}
		
		// Get the status.
		final String statusField = line.getField(STATUS_HEADER);
		if (null == statusField) {
			return null;
		}
		
		final TaskStatus status;
		try {
			status = TaskStatus.valueOf(statusField);
		} catch (final IllegalArgumentException exception) {
			return null;
		}
		
		// Get the comment.
		final String comment = line.getField(COMMENT_HEADER);
		
		// Build.
		return buildEntry(line, name, status, comment);
	}
	
	protected abstract Entry buildEntry(final CSVLine line, final String name, final TaskStatus status, final String comment)
	throws ReportException;
}
