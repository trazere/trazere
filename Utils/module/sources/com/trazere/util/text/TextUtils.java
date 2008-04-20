/*
 *  Copyright 2008 Julien Dufour
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

import java.util.Collection;

/**
 * The <code>TextUtils</code> class provides various helpers regarding the manipulation of text.
 */
public class TextUtils {
	/**
	 * Compare the given string ignoring case.
	 * <p>
	 * This method supports comparisons of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param string1 First string to compare. May be <code>null</code>.
	 * @param string2 Second string to compare. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link String#compareToIgnoreCase(String)} method.
	 * @see String#compareToIgnoreCase(String)
	 */
	public static int compareIgnoreCase(final String string1, final String string2) {
		if (null == string1) {
			return null == string2 ? 0 : -1;
		} else {
			return null == string2 ? 1 : string1.compareToIgnoreCase(string2);
		}
	}
	
	/**
	 * Strip the given string, that is converting the empty string to <code>null</code>.
	 * 
	 * @param s String to strip.
	 * @return <code>null</code> when the given string is empty, the given string otherwise.
	 */
	public static String strip(final String s) {
		assert null != s;
		
		// Strip.
		return s.length() > 0 ? s : null;
	}
	
	/**
	 * Unstrip the given string, that is converting <code>null</code> to the empty string.
	 * 
	 * @param s String to unstrip
	 * @return The empty string when the given string is <code>null</code>, the given string otherwise.
	 */
	public static String unstrip(final String s) {
		return null != s ? s : "";
	}
	
	/**
	 * Split the given string according to the given delimiter.
	 * 
	 * @param <C> Type of the collection filled with the results.
	 * @param string String to split.
	 * @param delimiter Delimiter string.
	 * @param trim Flag indicating wether the empty tokens are trimmed.
	 * @param ignoreEmpty Flag indicating wether the empty tokens are ignored.
	 * @param results Collection to fill with the tokens.
	 * @return The given result collection.
	 */
	public static <C extends Collection<? super String>> C split(final String string, final String delimiter, final boolean trim, final boolean ignoreEmpty, final C results) {
		assert null != string;
		assert null != delimiter;
		
		// Split the string.
		for (final String token : string.split(delimiter.replaceAll("[^\\w]", "\\\\$0"))) {
			final String trimmedToken = trim ? token.trim() : token;
			if (!ignoreEmpty || trimmedToken.length() > 0) {
				results.add(trimmedToken);
			}
		}
		return results;
	}
	
	/**
	 * Join the given string token using the given delimiter.
	 * 
	 * @param tokens The token to join.
	 * @param delimiter The delimiter.
	 * @return The resulting string.
	 */
	public static String join(final Collection<String> tokens, final String delimiter) {
		return join(tokens, delimiter, new StringBuilder()).toString();
	}
	
	/**
	 * Join the given string token using the given delimiter.
	 * 
	 * @param tokens The token to join.
	 * @param delimiter The delimiter.
	 * @param builder The string build to fill.
	 * @return The given string builder.
	 */
	public static StringBuilder join(final Collection<String> tokens, final String delimiter, final StringBuilder builder) {
		assert null != tokens;
		assert null != delimiter;
		assert null != builder;
		
		// Join the strings.
		boolean first = true;
		for (final String token : tokens) {
			if (!first) {
				builder.append(delimiter);
			}
			builder.append(token);
			first = false;
		}
		
		return builder;
	}
	
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
	 * Compute the hexadecimal representation of the given array of bytes.
	 * <p>
	 * The leading zeros are not stripped.
	 * 
	 * @param bytes Bytes representing the hexadecimal value.
	 * @return The string representation.
	 */
	public static String toHexString(final byte[] bytes) {
		assert null != bytes;
		
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bytes.length; i += 1) {
			final byte b = bytes[i];
			builder.append(HEX_DIGITS[(b >> 4) & 0xF]);
			builder.append(HEX_DIGITS[b & 0xF]);
		}
		return builder.toString();
	}
	
	/**
	 * Capitalize the given string.
	 * 
	 * @param s The string to capitalize.
	 * @return The capitalized string.
	 */
	public static String capitalize(final String s) {
		if (s.length() > 0) {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		} else {
			return s;
		}
	}
	
	/**
	 * Compute the name of the given class (without package information).
	 * 
	 * @param class_ The class whose name should be computed.
	 * @return The name of the class.
	 */
	public static String computeClassName(final Class<?> class_) {
		assert null != class_;
		
		// Compute.
		final String fullName = class_.getName();
		final int index = fullName.lastIndexOf('.');
		return index > 0 ? fullName.substring(index + 1) : fullName;
	}
	
	/**
	 * Compute the description of the given object.
	 * <p>
	 * This method aims to be used in {@link Object#toString()} implementations.
	 * 
	 * @param object Object whose description should be computed.
	 * @return The description.
	 */
	public static String computeDescription(final Describable object) {
		assert null != object;
		
		// Compute.
		final StringBuilder builder = new StringBuilder();
		builder.append("[").append(TextUtils.computeClassName(object.getClass()));
		object.fillDescription(builder);
		builder.append("]");
		return builder.toString();
	}
	
	private TextUtils() {
		// Prevent instantiation.
	}
}
