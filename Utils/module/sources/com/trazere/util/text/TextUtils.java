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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.trazere.util.Assert;

/**
 * The <code>TextUtils</code> class provides various helpers regarding the manipulation of text.
 */
public class TextUtils {
	/** Array of the hexadecimal digits characters (upper case). */
	public static final char[] HEX_DIGITS = {
	    '0',
	    '1',
	    '2',
	    '3',
	    '4',
	    '5',
	    '6',
	    '7',
	    '8',
	    '9',
	    'A',
	    'B',
	    'C',
	    'D',
	    'E',
	    'F'
	};

	/**
	 * Split the given string according to the given delimiter.
	 * 
	 * @param string String to split.
	 * @param delimiter Delimiter string.
	 * @param trim Flag indicating wether the empty tokens are trimmed.
	 * @param ignoreEmpty Flag indicating wether the empty tokens are ignored.
	 * @return An unmodifiable <code>List</code> of the parts.
	 */
	public static List<String> split(final String string, final String delimiter, final boolean trim, final boolean ignoreEmpty) {
		Assert.notNull(string);
		Assert.notNull(delimiter);

		// Split the string.
		final List<String> allTokens = Arrays.asList(string.split(delimiter.replaceAll("[^\\w]", "\\\\$0")));

		final List<String> tokens = new ArrayList<String>(allTokens.size());
		for (final String token : allTokens) {
			final String trimmedToken = trim ? token.trim() : token;
			if (!ignoreEmpty || trimmedToken.length() > 0) {
				tokens.add(trimmedToken);
			}
		}
		return tokens;
	}

	/**
	 * Compute the hexadecimal representation of the given array of bytes.
	 * <p>
	 * The leading zeros are not stripped.
	 * 
	 * @param bytes Bytes representing the hexadecimal value.
	 * @return The string representation.
	 */
	public static String toHexString(final byte[] bytes) {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bytes.length; i += 1) {
			final byte b = bytes[i];
			builder.append(HEX_DIGITS[(b >> 4) & 0xF]);
			builder.append(HEX_DIGITS[b & 0xF]);
		}
		return builder.toString();
	}

	private TextUtils() {
		// Prevent instanciation.
	}
}
