package com.trazere.util.report.store;

import java.util.Date;

import com.trazere.util.Assert;
import com.trazere.util.report.ReportEntry;
import com.trazere.util.report.ReportLevel;

/**
 * The <code>ReportStoreEntry</code> class represents entries of the report stores.
 * <p>
 * Report store entries wrap report entries along their generation date and reporting level.
 * 
 * @param <Entry> Type of the report entries.
 */
public class ReportStoreEntry<Entry extends ReportEntry<?, ?>> {
	/** Date of the report entry. */
	protected final Date _date;

	/** Level of the report entry. */
	protected final ReportLevel _level;

	/** Report entry. */
	protected final Entry _entry;

	/**
	 * Instanciate a new store report entry with the given date, level and entry.
	 * 
	 * @param date Date of the report entry.
	 * @param level Level of the report entry.
	 * @param entry Report entry.
	 */
	public ReportStoreEntry(final Date date, final ReportLevel level, final Entry entry) {
		Assert.notNull(date);
		Assert.notNull(level);
		Assert.notNull(entry);

		// Initialization.
		_date = date;
		_level = level;
		_entry = entry;
	}

	/**
	 * Get the date of the receiver report entry.
	 * 
	 * @return The date.
	 */
	public Date getDate() {
		return _date;
	}

	/**
	 * Get the level of the receiver report entry.
	 * 
	 * @return The level.
	 */
	public ReportLevel getLevel() {
		return _level;
	}

	/**
	 * Get the report entry of the receiver store report entry.
	 * 
	 * @return The report entry.
	 */
	public Entry getEntry() {
		return _entry;
	}

	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		result = result * 31 + _date.hashCode();
		result = result * 31 + _level.hashCode();
		result = result * 31 + _entry.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final ReportStoreEntry<Entry> entry = (ReportStoreEntry<Entry>) object;
			return _date.equals(entry._date) && _level == entry._level && _entry.equals(entry._entry);
		} else {
			return false;
		}
	}
}
