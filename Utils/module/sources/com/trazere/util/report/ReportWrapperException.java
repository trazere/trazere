package com.trazere.util.report;

/**
 * A <code>ReportWrapperException</code> exception wraps a report entry using Java's exception mecanism.
 * <p>
 * Using exception helps to clarify the code by avoiding sequential tests when some error occurs. This exception also provides support for chaining report
 * entries as on would chain exception (cause).
 */
public class ReportWrapperException
extends Exception {
	protected final ReportEntry _entry;

	/**
	 * Instanciate a new exception using the given entry.
	 * 
	 * @param entry Report entry.
	 */
	public ReportWrapperException(final ReportEntry<?, ?> entry) {
		super(entry.toString());

		// Initialization.
		_entry = entry;
	}

	/**
	 * Instanciate a new exception using the given entry and cause.
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
