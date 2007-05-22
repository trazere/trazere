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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trazere.util.csv.CSVLine;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.store.CSVReportStore;

/**
 * The <code>TaskCSVReportStore</code> class represents task report stores relying on CSV files.
 */
public class TaskCSVReportStore
extends CSVReportStore<TaskReportEntry> {
	/**
	 * Instantiate a new store with the given path.
	 * 
	 * @param path Path of the backing CSV file.
	 */
	public TaskCSVReportStore(final File path) {
		super(path);
	}

	@Override
	protected List<String> getEntryHeaders() {
		return TaskCSVReportStoreHeaders.ENTRY_HEADERS;
	}

	@Override
	protected Map<String, String> serializeEntry(final TaskReportEntry entry)
	throws ReportException {
		final Map<String, String> fields = new HashMap<String, String>();
		fields.put(TaskCSVReportStoreHeaders.NAME, entry.getCategory());
		fields.put(TaskCSVReportStoreHeaders.STATUS, entry.getCode().toString());

		return fields;
	}

	@Override
	protected TaskReportEntry buildEntry(final CSVLine line)
	throws ReportException {
		// Get the name.
		final String name = line.getField(TaskCSVReportStoreHeaders.NAME);
		if (null == name) {
			return null;
		}

		// Get the status.
		final String statusField = line.getField(TaskCSVReportStoreHeaders.STATUS);
		if (null == statusField) {
			return null;
		}

		final TaskStatus status;
		try {
			status = TaskStatus.valueOf(statusField);
		} catch (final IllegalArgumentException exception) {
			return null;
		}

		// Build.
		return new TaskReportEntry(name, status);
	}
}
