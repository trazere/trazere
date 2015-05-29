package com.trazere.util.text;

import java.util.Comparator;

/**
 * The {@link TextComparators} class provides various factories of comparators related to text.
 * 
 * @see Comparator
 * @deprecated Use core.
 */
@Deprecated
public class TextComparators {
	/**
	 * Builds a comparator of string that ignores and handle <code>null</code> values.
	 * 
	 * @return The built comparator.
	 * @see TextUtils#safeCompareIgnoreCase(String, String)
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#safe(Comparator)}.
	 */
	@Deprecated
	public static Comparator<String> safeIgnoreCase() {
		return _SAFE_IGNORE_CASE;
	}
	
	private static Comparator<String> _SAFE_IGNORE_CASE = new Comparator<String>() {
		@Override
		public int compare(final String value1, final String value2) {
			return TextUtils.safeCompareIgnoreCase(value1, value2);
		}
	};
	
	private TextComparators() {
		// Prevents instantiation.
	}
}
