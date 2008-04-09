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
package com.trazere.util.report.store;

import com.trazere.util.function.Filter;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportListener;
import java.util.List;

/**
 * The <code>ReportStore</code> interface defines report entry stores.
 * 
 * @param <Entry> Type of the report entries.
 * @see ReportStoreEntry
 */
public interface ReportStore<Entry extends ReportEntry<?, ?>>
extends ReportListener<Entry> {
	/**
	 * Count the entries of the receiver store matching the given filter.
	 * 
	 * @param filter Filter of entries. May be <code>null</code> to accept all entries.
	 * @return The number of entries.
	 * @throws ReportException
	 */
	public int countEntries(final Filter<ReportStoreEntry<Entry>> filter)
	throws ReportException;
	
	/**
	 * Get the entries of the receiver store matching the the given filter and according to the given parameters.
	 * 
	 * @param filter Filter of entries. May be <code>null</code> to accept all entries.
	 * @param limit Maximum number of retrieved entries. May be negative to disable the limit.
	 * @param fromEnd Flag indicating wether the entries should be extracted from the beginning or the end of the store. This flag changes nothing when the
	 *        maximum number of retrieved entries is not limited.
	 * @return The entries.
	 * @throws ReportException
	 */
	public List<ReportStoreEntry<Entry>> getEntries(final Filter<ReportStoreEntry<Entry>> filter, final int limit, final boolean fromEnd)
	throws ReportException;
}
