package com.trazere.util.report.store;

import java.util.List;

import com.trazere.util.function.Filter;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportException;
import com.trazere.util.report.ReportListener;

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
