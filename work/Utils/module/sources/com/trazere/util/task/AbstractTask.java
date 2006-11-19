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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.trazere.util.Assert;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportLevel;
import com.trazere.util.report.ReportListener;

/**
 * The <code>AbstractTask</code> represents abstract task.
 */
public abstract class AbstractTask
implements Task {
	private static final Log LOG = LogFactory.getLog(AbstractTask.class);

	/** Name of the task. */
	protected final String _name;

	/** Report listener to use. May be <code>null</code>. */
	protected final ReportListener<TaskReportEntry> _reportListener;

	public AbstractTask(final String name, final ReportListener<TaskReportEntry> reportListener) {
		Assert.notNull(name);

		// Initialize instance.
		_name = name;
		_reportListener = reportListener;
	}

	public String getName() {
		return _name;
	}

	public ReportListener<? extends ReportEntry<String, TaskStatus>> getReportListener() {
		return _reportListener;
	}

	public void performTask()
	throws TaskException {
		performTask(false);
	}

	public boolean unsafePerformTask() {
		try {
			performTask(true);
			return true;
		} catch (final TaskException exception) {
			return false;
		}
	}

	protected void performTask(final boolean trace)
	throws TaskException {
		LOG.info("*** " + _name + " ***");

		try {
			// Perform the task.
			performAbstractTask();

			// Log.
			LOG.info("*** " + _name + " [OK] ***");

			// Report.
			if (null != _reportListener) {
				try {
					_reportListener.report(ReportLevel.NOTICE, new TaskReportEntry(_name, TaskStatus.SUCCEEDED));
				} catch (final ReportException exception) {
					LOG.info("Failed reporting", exception);
				}
			}
		} catch (final TaskException exception) {
			// Log.
			if (trace) {
				LOG.info("*** " + _name + " [FAILED] ***");
			} else {
				LOG.info("*** " + _name + " [FAILED] ***", exception);
			}

			// Report.
			if (null != _reportListener) {
				try {
					_reportListener.report(ReportLevel.ERROR, new TaskReportEntry(_name, TaskStatus.FAILED));
				} catch (final ReportException exception1) {
					LOG.info("Failed reporting", exception1);
				}
			}

			throw exception;
		} catch (final RuntimeException exception) {
			// Log.
			if (trace) {
				LOG.info("*** " + _name + " [FAILED] ***");
			} else {
				LOG.info("*** " + _name + " [FAILED] ***", exception);
			}

			// Report.
			if (null != _reportListener) {
				try {
					_reportListener.report(ReportLevel.WARNING, new TaskReportEntry(_name, TaskStatus.FAILED));
				} catch (final ReportException exception1) {
					LOG.info("Failed reporting", exception1);
				}
			}

			throw exception;
		} finally {
			LOG.info("");
		}
	}

	protected abstract void performAbstractTask()
	throws TaskException;
}
