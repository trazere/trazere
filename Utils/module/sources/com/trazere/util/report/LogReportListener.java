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
package com.trazere.util.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The <code>LogReportListener</code> class represents report listeners which forward the entries to a logger.
 * 
 * @param <Entry> Type of the entries.
 */
public class LogReportListener<Entry extends ReportEntry<?, ?>>
implements ReportListener<Entry> {
	private static final Log LOG = LogFactory.getLog(LogReportListener.class);
	
	/** Logger to use. */
	protected final Log _log;
	
	/**
	 * Instantiate a new listener with the given logger.
	 * 
	 * @param log Logger to use.
	 */
	public LogReportListener(final Log log) {
		assert null != log;
		
		// Initialization.
		_log = log;
	}
	
	/**
	 * Get the logger used by the receiver listener.
	 * 
	 * @return The logger.
	 */
	public Log getLog() {
		return _log;
	}
	
	public void report(final ReportLevel level, final Entry entry) {
		assert null != entry;
		
		// Render the message.
		final String message = ReportUtils.render(entry);
		
		// Log.
		switch (level) {
			case NOTICE: {
				_log.info(message);
				break;
			}
			case WARNING: {
				_log.warn(message);
				break;
			}
			case ERROR: {
				_log.error(message);
				break;
			}
			default: {
				LOG.warn("Invalid report level " + level + " for entry " + entry);
				_log.error(message);
				break;
			}
		}
	}
	
	protected String computeMessage(final Entry entry) {
		return ReportUtils.render(entry);
	}
	
	public void sleep()
	throws ReportException {
		// Nothing to do.
	}
}
