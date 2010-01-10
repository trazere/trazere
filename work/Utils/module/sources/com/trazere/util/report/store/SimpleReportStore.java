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
package com.trazere.util.report.store;

import com.trazere.util.function.FunctionUtils;
import com.trazere.util.function.Predicate1;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportLevel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The <code>SimpleReportStore</code> class represents report stores relying on the memory.
 * <p>
 * This class supports a higher bound for the number of entries. Oldest entries are deleted when the bound is reached.
 * 
 * @param <Entry> Type of the report entries.
 */
public class SimpleReportStore<Entry extends ReportEntry<?, ?>>
implements ReportStore<Entry> {
	/** Report store entries. */
	protected final List<ReportStoreEntry<Entry>> _entries = new ArrayList<ReportStoreEntry<Entry>>();
	
	/** Higher bound. */
	protected final int _limit;
	
	/**
	 * Instantiate a new store with the given higher bound.
	 * 
	 * @param limit Maximum number of entries, or a negative value to disable the higher bound.
	 */
	public SimpleReportStore(final int limit) {
		// Initialization.
		_limit = limit;
	}
	
	/**
	 * Get the maximum number of entries (higher bound) of the receiver store.
	 * 
	 * @return The maximum number, or a negative value if the higher bound is disabled.
	 */
	public int getLimit() {
		return _limit;
	}
	
	public synchronized void report(final ReportLevel level, final Entry entry)
	throws ReportException {
		// Add the entry.
		_entries.add(new ReportStoreEntry<Entry>(new Date(), level, entry));
		
		// Constrain to the higher bound.
		if (_limit >= 0) {
			while (_entries.size() > _limit) {
				_entries.remove(0);
			}
		}
	}
	
	public void sleep()
	throws ReportException {
		// Nothing to do.
	}
	
	public int countEntries(final Predicate1<? super ReportStoreEntry<Entry>, ReportException> filter)
	throws ReportException {
		if (null != filter) {
			return FunctionUtils.count(filter, _entries);
		} else {
			return _entries.size();
		}
	}
	
	/**
	 * Get all entries.
	 * <p>
	 * This method returns the backing list of entries. It is faster than {@link #getEntries(Predicate1, int, boolean)} to get all the entries (no copy).
	 * 
	 * @return The unmodifiable list of entries.
	 */
	public List<ReportStoreEntry<Entry>> getEntries() {
		return Collections.unmodifiableList(_entries);
	}
	
	public List<ReportStoreEntry<Entry>> getEntries(final Predicate1<? super ReportStoreEntry<Entry>, ReportException> filter, final int limit, final boolean fromEnd)
	throws ReportException {
		return ReportStoreUtils.filterEntries(_entries, filter, limit, fromEnd);
	}
}
