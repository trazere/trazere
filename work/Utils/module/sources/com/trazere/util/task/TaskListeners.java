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
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportLevel;
import com.trazere.util.report.ReportListener;

/**
 * DOCME
 */
public class TaskListeners {
	private static final Log LOG = LogFactory.getLog(TaskListener.class);
	
	public static TaskListener log(final Task task, final Log log) {
		Assert.notNull(task);
		Assert.notNull(log);
		
		// Build.
		return new TaskListener() {
			public void taskStarted() {
				log.info("*** " + task.getName() + " ***");
			}
			
			public void taskSucceeded() {
				log.info("*** " + task.getName() + " [OK] ***");
			}
			
			public void taskFailed(final TaskException exception) {
				log.info("*** " + task.getName() + " [FAILED] ***");
			}
			
			public void taskFailed(final RuntimeException exception) {
				log.info("*** " + task.getName() + " [FAILED] ***");
			}
			
			public void taskEnded() {
				log.info("");
			}
		};
	}
	
	public static TaskListener trace(final Task task, final Log log) {
		Assert.notNull(task);
		Assert.notNull(log);
		
		// Build.
		return new TaskListener() {
			public void taskStarted() {
				log.info("*** " + task.getName() + " ***");
			}
			
			public void taskSucceeded() {
				log.info("*** " + task.getName() + " [OK] ***");
			}
			
			public void taskFailed(final TaskException exception) {
				log.info("*** " + task.getName() + " [FAILED] ***", exception);
			}
			
			public void taskFailed(final RuntimeException exception) {
				log.info("*** " + task.getName() + " [FAILED] ***", exception);
			}
			
			public void taskEnded() {
				log.info("");
			}
		};
	}
	
	public static TaskListener report(final Task task, final ReportListener<TaskReportEntry> reportListener) {
		Assert.notNull(task);
		Assert.notNull(reportListener);
		
		// Build.
		return new TaskListener() {
			public void taskStarted() {
				// Nothing to do.
			}
			
			public void taskSucceeded() {
				try {
					reportListener.report(ReportLevel.NOTICE, new TaskReportEntry(task.getName(), TaskStatus.SUCCEEDED, null));
					reportListener.sleep();
				} catch (final ReportException exception) {
					LOG.info("Failed reporting", exception);
				}
			}
			
			public void taskFailed(final TaskException exception) {
				try {
					reportListener.report(ReportLevel.ERROR, new TaskReportEntry(task.getName(), TaskStatus.FAILED, exception.getMessage()));
					reportListener.sleep();
				} catch (final ReportException exception1) {
					LOG.info("Failed reporting", exception1);
				}
			}
			
			public void taskFailed(final RuntimeException exception) {
				try {
					reportListener.report(ReportLevel.WARNING, new TaskReportEntry(task.getName(), TaskStatus.FAILED, exception.getMessage()));
					reportListener.sleep();
				} catch (final ReportException exception1) {
					LOG.info("Failed reporting", exception1);
				}
			}
			
			public void taskEnded() {
				// Nothing to do.
			}
		};
	}
	
	private TaskListeners() {
		// Prevent instantiation.
	}
}
