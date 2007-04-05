/*
 *  Copyright 2006 Julien Dufour
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

import com.trazere.util.Assert;

/**
 * The <code>CharFilters</code> class provides various standard character filters.
 */
public class CharFilters {
	/**
	 * Filter accepting no characters.
	 */
	public static final CharFilter NONE = new CharFilter() {
		public boolean filter(final char c) {
			return false;
		}
	};

	/**
	 * Filter accepting all characters.
	 */
	public static final CharFilter ALL = new CharFilter() {
		public boolean filter(final char c) {
			return true;
		}
	};

	/**
	 * Filter accepting the digit characters.
	 * 
	 * @see Character#isDigit(char)
	 */
	public static final CharFilter DIGIT = new CharFilter() {
		public boolean filter(final char c) {
			return Character.isDigit(c);
		}
	};

	/**
	 * Filter accepting the letter characters.
	 * 
	 * @see Character#isLetter(char)
	 */
	public static final CharFilter LETTER = new CharFilter() {
		public boolean filter(final char c) {
			return Character.isLetter(c);
		}
	};

	/**
	 * Filter accepting the alphanumeric characters.
	 * 
	 * @see Character#isLetterOrDigit(char)
	 */
	public static final CharFilter ALPHANUMERIC = new CharFilter() {
		public boolean filter(final char c) {
			return Character.isLetterOrDigit(c) || '_' == c;
		}
	};

	/**
	 * Build a char filter accepting the given char.
	 * 
	 * @param c Accepted char.
	 * @return The char filter.
	 */
	public static CharFilter build(final char c) {
		return new CharFilter() {
			public boolean filter(final char c_) {
				return c == c_;
			}
		};
	}

	/**
	 * Build a char filter accepting any char from the given string.
	 * 
	 * @param chars Accepted chars.
	 * @return The char filter.
	 */
	public static CharFilter build(final String chars) {
		Assert.notNull(chars);

		// Build.
		return new CharFilter() {
			public boolean filter(final char c) {
				return chars.indexOf(c) >= 0;
			}
		};
	}

	private CharFilters() {
		// Prevent instanciation.
	}
}
