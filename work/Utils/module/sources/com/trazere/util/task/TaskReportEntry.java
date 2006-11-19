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

import com.trazere.util.Assert;
import com.trazere.util.report.ReportEntry;

/**
 * The <code>TaskReportEntry</code> class represents report entries regarding task execution.
 * <p>
 * The entries use the name of the tasks as category and their code reflect the status of the task according to the {@link TaskStatus} enumeration.
 * 
 * @see Task
 */
public class TaskReportEntry
implements ReportEntry<String, TaskStatus> {
	/** Name of the task. */
	protected final String _name;

	/** Status of the task. */
	protected final TaskStatus _status;

	/**
	 * Instanciate a new entry with the given task name and status.
	 * <p>
	 * The entry uses the current date.
	 * 
	 * @param name Name of the task.
	 * @param status Status of the task.
	 */
	public TaskReportEntry(final String name, final TaskStatus status) {
		Assert.notNull(name);
		Assert.notNull(status);

		// Initialization.
		_name = name;
		_status = status;
	}

	public String getCategory() {
		return _name;
	}

	public TaskStatus getCode() {
		return _status;
	}

	public String getMessage() {
		return _status.getMessage();
	}

	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		result = result * 31 + _name.hashCode();
		result = result * 31 + _status.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final TaskReportEntry entry = (TaskReportEntry) object;
			return _name.equals(entry._name) && _status == _status;
		} else {
			return false;
		}
	}
}
