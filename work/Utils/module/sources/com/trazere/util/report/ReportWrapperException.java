/*
 *  Copyright 2008 Julien Dufour
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

/**
 * A <code>ReportWrapperException</code> exception wraps a report entry using Java's exception mecanism.
 * <p>
 * Using exception helps to clarify the code by avoiding sequential tests when some error occurs. This exception also provides support for chaining report
 * entries as on would chain exception (cause).
 */
public class ReportWrapperException
extends Exception {
	/**
	 * DOCME
	 */
	private static final long serialVersionUID = 1L;
	protected final ReportEntry<?, ?> _entry;
	
	/**
	 * Instantiate a new exception using the given entry.
	 * 
	 * @param entry Report entry.
	 */
	public ReportWrapperException(final ReportEntry<?, ?> entry) {
		super(entry.toString());
		
		// Initialization.
		_entry = entry;
	}
	
	/**
	 * Instantiate a new exception using the given entry and cause.
	 * 
	 * @param entry Report entry.
	 * @param cause Cause exception.
	 */
	public ReportWrapperException(final ReportEntry<?, ?> entry, final ReportWrapperException cause) {
		this(ReportUtils.chain(entry, cause.getEntry()));
	}
	
	/**
	 * Get the report entry.
	 * 
	 * @return The report entry.
	 */
	public ReportEntry<?, ?> getEntry() {
		return _entry;
	}
}
