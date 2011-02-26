/*
 *  Copyright 2006-2010 Julien Dufour
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>LogReportListener</code> class represents report listeners which forward the entries to a logger.
 * 
 * @param <Entry> Type of the entries.
 */
public class LogReportListener<Entry extends ReportEntry<?, ?>>
implements ReportListener<Entry> {
	private static final Logger _LOGGER = LoggerFactory.getLogger(LogReportListener.class);
	
	/** Logger to use. */
	protected final Logger _logger;
	
	/**
	 * Instantiate a new listener with the given logger.
	 * 
	 * @param logger Logger to use.
	 */
	public LogReportListener(final Logger logger) {
		assert null != logger;
		
		// Initialization.
		_logger = logger;
	}
	
	/**
	 * Get the logger used by the receiver listener.
	 * 
	 * @return The logger.
	 */
	public Logger getLogger() {
		return _logger;
	}
	
	public void report(final ReportLevel level, final Entry entry) {
		assert null != entry;
		
		// Render the message.
		final String message = ReportUtils.render(entry);
		
		// Log.
		switch (level) {
			case NOTICE: {
				_logger.info(message);
				break;
			}
			case WARNING: {
				_logger.warn(message);
				break;
			}
			case ERROR: {
				_logger.error(message);
				break;
			}
			default: {
				_LOGGER.warn("Invalid report level " + level + " for entry " + entry);
				_logger.error(message);
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
