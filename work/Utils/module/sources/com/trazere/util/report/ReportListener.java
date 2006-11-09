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
}
