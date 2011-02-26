package com.trazere.util.xml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link XMLUtils} class provides various utilities regarding the manipulation of XML.
 */
public class XMLUtils {
	/**
	 * Escapes the XML entities of the given string.
	 * 
	 * @param s The string.
	 * @return The escaped string.
	 */
	public static String escapeEntities(final String s) {
		return escapeEntities(s, new StringBuilder()).toString();
	}
	
	/**
	 * Escapes the XML entities of the given string and appends it to the given builder.
	 * 
	 * @param s The string.
	 * @param result The builder to fill.
	 * @return The given builder.
	 */
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
	 */
	public static String unescapeEntities(final String s) {
		return unescapeEntities(s, new StringBuilder()).toString();
	}
	
	/**
	 * Unescapes the XML entities of the given string and appends it to the given builder.
	 * 
	 * @param s The string.
	 * @param result The builder to fill.
	 * @return The given builder.
	 */
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
	
	private XMLUtils() {
		// Prevent instantiation.
	}
}
