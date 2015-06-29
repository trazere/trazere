/*
 *  Copyright 2006-2015 Julien Dufour
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
package com.trazere.util.text;

import java.util.Comparator;

/**
 * The {@link TextComparators} class provides various factories of comparators related to text.
 * 
 * @see Comparator
 * @deprecated Use {@link com.trazere.core}.
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
