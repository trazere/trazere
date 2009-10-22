/*
 *  Copyright 2006-2009 Julien Dufour
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

import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportLevel;
import com.trazere.util.report.ReportListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The {@link TaskListeners} class provides various task listeners.
 */
public class TaskListeners {
	private static final Log LOG = LogFactory.getLog(TaskListener.class);
	
	/**
	 * Build a task listener which logs the task executions using the given log.
	 * 
	 * @param log The log to use.
	 * @param trace Flag indicated whether the exception trace should be logged or not.
	 * @return The built listener.
	 */
	public static TaskListener log(final Log log, final boolean trace) {
		assert null != log;
		
		// Build.
		return new TaskListener() {
			public void taskStarted(final Task task) {
				log.info("*** " + task.getName() + " ***");
			}
			
			public void taskSucceeded(final Task task) {
				log.info("*** " + task.getName() + " [OK] ***");
			}
			
			public void taskFailed(final Task task, final TaskException exception) {
				if (trace) {
					log.info("*** " + task.getName() + " [FAILED] ***", exception);
				} else {
					log.info("*** " + task.getName() + " [FAILED] ***");
				}
			}
			
			public void taskFailed(final Task task, final RuntimeException exception) {
				if (trace) {
					log.info("*** " + task.getName() + " [FAILED] ***", exception);
				} else {
					log.info("*** " + task.getName() + " [FAILED] ***");
				}
			}
			
			public void taskEnded(final Task task) {
				log.info("");
			}
		};
	}
	
	/**
	 * Build a task listener which reports the task executions using the given report listener.
	 * 
	 * @param reportListener The report listener to use.
	 * @return The built listener.
	 */
	public static TaskListener report(final ReportListener<TaskReportEntry> reportListener) {
		assert null != reportListener;
		
		// Build.
		return new TaskListener() {
			public void taskStarted(final Task task) {
				// Nothing to do.
			}
			
			public void taskSucceeded(final Task task) {
				try {
					reportListener.report(ReportLevel.NOTICE, new TaskReportEntry(task.getName(), TaskStatus.SUCCEEDED, null));
					reportListener.sleep();
				} catch (final ReportException exception) {
					LOG.info("Failed reporting execution of task " + task.getName(), exception);
				}
			}
			
			public void taskFailed(final Task task, final TaskException exception) {
				try {
					reportListener.report(ReportLevel.ERROR, new TaskReportEntry(task.getName(), TaskStatus.FAILED, exception.getMessage()));
					reportListener.sleep();
				} catch (final ReportException exception1) {
					LOG.info("Failed reporting execution of task " + task.getName(), exception1);
				}
			}
			
			public void taskFailed(final Task task, final RuntimeException exception) {
				try {
					reportListener.report(ReportLevel.WARNING, new TaskReportEntry(task.getName(), TaskStatus.FAILED, exception.getMessage()));
					reportListener.sleep();
				} catch (final ReportException exception1) {
					LOG.info("Failed reporting execution of task " + task.getName(), exception1);
				}
			}
			
			public void taskEnded(final Task task) {
				// Nothing to do.
			}
		};
	}
	
	private TaskListeners() {
		// Prevent instantiation.
	}
}
