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
package com.trazere.util.report.store;

import com.trazere.util.function.Predicate1;
import com.trazere.util.record.RecordException;
import com.trazere.util.report.ReportEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * The {@link ReportStoreUtils} class provides various helpers regarding the manipulation of report stores.
 */
public class ReportStoreUtils {
	/**
	 * Build a report store entry filter using the given parameters.
	 * 
	 * @param <Category> Type of the categories.
	 * @param <Code> Type of the codes.
	 * @param <Entry> Type of the report entries.
	 * @param startDate Earliest date of the entries to accept. May be <code>null</code> to disable this limit.
	 * @param endDate Latest date of the entries to accept. May be <code>null</code> to disable this limit.
	 * @param categories Categories of the entries to accept. May be <code>null</code> to accept all categories.
	 * @param codes Codes of the entries to accept. May be <code>null</code> to accept all codes.
	 * @return The filter.
	 */
	public static <Category, Code extends Enum<?>, Entry extends ReportEntry<Category, Code>> Predicate1<ReportStoreEntry<Entry>, RecordException> buildFilter(final Date startDate, final Date endDate, final Set<Category> categories, final Set<Code> codes) {
		return new Predicate1<ReportStoreEntry<Entry>, RecordException>() {
			public boolean evaluate(final ReportStoreEntry<Entry> entry) {
				// Start date.
				if (null != startDate && entry.getDate().before(startDate)) {
					return false;
				}
				
				// End date.
				if (null != endDate && !entry.getDate().before(endDate)) {
					return false;
				}
				
				// Category.
				if (null != categories && !categories.contains(entry.getEntry().getCategory())) {
					return false;
				}
				
				// Code.
				if (null != codes && !codes.contains(entry.getEntry().getCode())) {
					return false;
				}
				
				return true;
			}
		};
	}
	
	/**
	 * Filter the given entries using the given filter and parameters.
	 * 
	 * @param <Entry> Type of the report entries.
	 * @param <X> Type of the exceptions.
	 * @param entries Report store entries to filter.
	 * @param filter Filter to use. May be <code>null</code> to accept all entries.
	 * @param limit Maximum number of retrieved entries. May be negative to disable the limit.
	 * @param fromEnd Flag indicating whether the entries should be extracted from the beginning or the end of the store. This flag changes nothing when the
	 *        maximum number of retrieved entries is not limited.
	 * @return The filtered entries.
	 * @throws X When some filter application fails.
	 */
	public static <Entry extends ReportEntry<?, ?>, X extends Exception> List<ReportStoreEntry<Entry>> filterEntries(final List<ReportStoreEntry<Entry>> entries, final Predicate1<? super ReportStoreEntry<Entry>, X> filter, final int limit, final boolean fromEnd)
	throws X {
		// Filtered entries.
		if (null != filter) {
			final List<ReportStoreEntry<Entry>> filteredEntries = new ArrayList<ReportStoreEntry<Entry>>();
			
			// Filter the entries, and limit the result.
			if (fromEnd) {
				final ListIterator<ReportStoreEntry<Entry>> entriesIt = entries.listIterator(entries.size());
				while (entriesIt.hasPrevious() && (limit < 0 || filteredEntries.size() < limit)) {
					final ReportStoreEntry<Entry> entry = entriesIt.previous();
					if (filter.evaluate(entry)) {
						filteredEntries.add(entry);
					}
				}
				Collections.reverse(filteredEntries);
			} else {
				final Iterator<ReportStoreEntry<Entry>> entriesIt = entries.iterator();
				while (entriesIt.hasNext() && (limit < 0 || filteredEntries.size() < limit)) {
					final ReportStoreEntry<Entry> entry = entriesIt.next();
					if (filter.evaluate(entry)) {
						filteredEntries.add(entry);
					}
				}
			}
			
			return filteredEntries;
		}
		
		// Limited entries.
		final int count = entries.size();
		if (limit >= 0 && limit < count) {
			// Truncate the entries.
			final List<ReportStoreEntry<Entry>> truncatedEntries;
			if (fromEnd) {
				truncatedEntries = entries.subList(count - limit, count);
			} else {
				truncatedEntries = entries.subList(0, limit);
			}
			return new ArrayList<ReportStoreEntry<Entry>>(truncatedEntries);
		}
		
		// All entries.
		return new ArrayList<ReportStoreEntry<Entry>>(entries);
	}
	
	private ReportStoreUtils() {
		// Prevent instantiation.
	}
}