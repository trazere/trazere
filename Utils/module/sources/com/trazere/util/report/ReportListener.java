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

/**
 * The <code>ReportListener</code> interface defines report listener.
 * 
 * @param <Entry> Type of the entries.
 * @see ReportLevel
 * @see ReportEntry
 */
public interface ReportListener<Entry extends ReportEntry<?, ?>> {
	/**
	 * Report the given entry with the given level.
	 * 
	 * @param level Level of the report entry.
	 * @param entry Report entry.
	 * @throws ReportException
	 */
	public void report(final ReportLevel level, final Entry entry)
	throws ReportException;
	
	/**
	 * Put the receiver listener to sleep between reports.
	 * <p>
	 * This method allows the receiver to temporarily release its resources. For instance, a file based report store may close the file until the next report.
	 * 
	 * @throws ReportException
	 */
	public void sleep()
	throws ReportException;
}
