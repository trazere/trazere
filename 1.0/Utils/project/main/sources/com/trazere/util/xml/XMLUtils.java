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
package com.trazere.util.xml;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link XMLUtils} class provides various utilities regarding the manipulation of XML.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class XMLUtils {
	/**
	 * Escapes the XML entities of the given string.
	 * 
	 * @param s The string.
	 * @return The escaped string.
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#escape(CharSequence)}. Careful, new version escapes apos.
	 */
	@Deprecated
	public static String escapeEntities(final String s) {
		return escapeEntities(s, new StringBuilder()).toString();
	}
	
	/**
	 * Escapes the XML entities of the given string and appends it to the given builder.
	 * 
	 * @param s The string.
	 * @param result The builder to fill.
	 * @return The given builder.
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#escape(CharSequence, Appendable, com.trazere.core.lang.ThrowableFactory)}. Careful, new version escapes
	 *             apos.
	 */
	@Deprecated
	public static StringBuilder escapeEntities(final String s, final StringBuilder result) {
		assert null != s;
		assert null != result;
		
		final int length = s.length();
		for (int index = 0; index < length; index += 1) {
			final char c = s.charAt(index);
			if (_ENTITIES.containsKey(c)) {
				result.append("&").append(_ENTITIES.get(c)).append(";");
			} else {
				result.append(c);
			}
		}
		return result;
	}
	
	private static final Map<Character, String> _ENTITIES;
	static {
		final Map<Character, String> entities = new HashMap<Character, String>();
		entities.put('&', "amp");
		//		entities.put('\'', "apos");
		entities.put('>', "gt");
		entities.put('<', "lt");
		entities.put('"', "quot");
		_ENTITIES = Collections.unmodifiableMap(entities);
	}
	
	/**
	 * Unescapes the XML entities of the given string.
	 * 
	 * @param s The string.
	 * @return The escaped string.
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#unescape(CharSequence)}.
	 */
	@Deprecated
	public static String unescapeEntities(final String s) {
		return unescapeEntities(s, new StringBuilder()).toString();
	}
	
	/**
	 * Unescapes the XML entities of the given string and appends it to the given builder.
	 * 
	 * @param s The string.
	 * @param result The builder to fill.
	 * @return The given builder.
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#unescape(CharSequence, Appendable, com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static StringBuilder unescapeEntities(final String s, final StringBuilder result) {
		assert null != s;
		assert null != result;
		
		final int length = s.length();
		for (int index = 0; index < length; index += 1) {
			final char c = s.charAt(index);
			if (c == '&') {
				final int comaIndex = s.indexOf(';', index + 1);
				if (comaIndex >= 0) {
					final String entity = s.substring(index + 1, comaIndex);
					if (_CHARACTERS.containsKey(entity)) {
						result.append(_CHARACTERS.get(entity).charValue());
						index = comaIndex;
					} else {
						result.append(c);
					}
				} else {
					result.append(c);
				}
			} else {
				result.append(c);
			}
		}
		return result;
	}
	
	private static final Map<String, Character> _CHARACTERS;
	static {
		final Map<String, Character> chars = new HashMap<String, Character>();
		chars.put("amp", '&');
		//		entities.put("apos", '\'');
		chars.put("gt", '>');
		chars.put("lt", '<');
		chars.put("quot", '"');
		_CHARACTERS = chars;
	}
	
	/**
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#DATE_FORMAT}.
	 */
	@Deprecated
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-ddZ");
	
	/**
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#DATE_TIME_FORMAT}.
	 */
	@Deprecated
	public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	/**
	 * @deprecated Use {@link com.trazere.xml.XMLUtils#TIME_FORMAT}.
	 */
	@Deprecated
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSSZ");
	
	private XMLUtils() {
		// Prevents instantiation.
	}
}
