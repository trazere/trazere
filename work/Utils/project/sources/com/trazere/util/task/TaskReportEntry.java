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

import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportUtils;

/**
 * The {@link TaskReportEntry} class represents report entries of task executions.
 * <p>
 * The entries use the name of the tasks as category and their code reflects the status of the task according to the {@link TaskStatus} enumeration.
 * 
 * @see Task
 */
public class TaskReportEntry
implements ReportEntry<String, TaskStatus> {
	/** Name of the task. */
	protected final String _name;
	
	/** Status of the task. */
	protected final TaskStatus _status;
	
	/** Comment of the task. May be <code>null</code>. */
	protected final String _comment;
	
	/**
	 * Instantiate a new entry with the given task name and status.
	 * <p>
	 * The entry uses the current date.
	 * 
	 * @param name Name of the task.
	 * @param status Status of the task.
	 * @param comment Comment of the task. May be <code>null</code>.
	 */
	public TaskReportEntry(final String name, final TaskStatus status, final String comment) {
		assert null != name;
		assert null != status;
		
		// Initialization.
		_name = name;
		_status = status;
		_comment = comment;
	}
	
	/**
	 * Get the name of the receiver task entry.
	 * 
	 * @return The name.
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * Get the status of the receiver task entry.
	 * 
	 * @return The status.
	 */
	public TaskStatus getStatus() {
		return _status;
	}
	
	/**
	 * Get the comment of the receiver task entry.
	 * 
	 * @return The comment. May be <code>null</code>.
	 */
	public String getComment() {
		return _comment;
	}
	
	public String getCategory() {
		return _name;
	}
	
	public TaskStatus getCode() {
		return _status;
	}
	
	public String getMessage() {
		return _comment;
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_name);
		result.append(_status);
		result.append(_comment);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final TaskReportEntry entry = (TaskReportEntry) object;
			return _name.equals(entry._name) && _status == entry._status && LangUtils.equals(_comment, entry._comment);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return ReportUtils.render(this);
	}
}
