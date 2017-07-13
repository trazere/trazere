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
package com.trazere.xml;

import com.trazere.core.io.CharSequenceReader;
import com.trazere.core.lang.ThrowableFactories;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.text.Scanner;
import com.trazere.core.text.TextException;
import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link XMLUtils} class provides various utilities regarding XML.
 * 
 * @since 2.0
 */
public class XMLUtils {
	// Entities.
	
	/**
	 * Escapes the given text to produce a valid XML representation.
	 * 
	 * @param s Text to escape.
	 * @return The escaped text.
	 * @since 2.0
	 */
	public static CharSequence escape(final CharSequence s) {
		return escape(s, new StringBuilder(), ThrowableFactories.RUNTIME_EXCEPTION);
	}
	
	/**
	 * Escapes the given text to produce a valid XML representation.
	 * 
	 * @param <A> Type of the appendable.
	 * @param s Text to escape.
	 * @param appendable Appendable to populate with the XML representation.
	 * @param failureFactory Factory of the exceptions for the failures while appending.
	 * @return The given appendable.
	 * @since 2.0
	 */
	public static <A extends Appendable> A escape(final CharSequence s, final A appendable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		return escape(new CharSequenceReader(s), appendable, failureFactory);
	}
	
	/**
	 * Escapes the given text to produce a valid XML representation.
	 * 
	 * @param <A> Type of the appendable.
	 * @param reader Reader providing the text to escape.
	 * @param appendable Appendable to populate with the XML representation.
	 * @param failureFactory Factory of the exceptions for the failures while appending.
	 * @return The given appendable.
	 * @since 2.0
	 */
	public static <A extends Appendable> A escape(final Reader reader, final A appendable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		try {
			while (true) {
				// Read the next character.
				final int i = reader.read();
				if (i < 0) {
					return appendable;
				}
				final char c = (char) i;
				
				// Escape the character.
				if (ENTITY_NAMES.containsKey(c)) {
					appendable.append('&').append(ENTITY_NAMES.get(c)).append(';');
				} else {
					appendable.append(c);
				}
			}
		} catch (final IOException exception) {
			throw failureFactory.build("Failed escaping characters " + reader, exception);
		}
	}
	
	private static final Map<Character, String> ENTITY_NAMES;
	
	static {
		final Map<Character, String> names = new HashMap<>();
		names.put('&', "amp");
		names.put('\'', "apos");
		names.put('>', "gt");
		names.put('<', "lt");
		names.put('"', "quot");
		ENTITY_NAMES = Collections.unmodifiableMap(names);
	}
	
	/**
	 * Unescapes the given XML representation.
	 *
	 * @param s XML representation to unescape.
	 * @return The unescaped text.
	 * @since 2.0
	 */
	public static CharSequence unescape(final CharSequence s) {
		return unescape(s, new StringBuilder(), TextException.FACTORY);
	}
	
	/**
	 * Unescapes the given XML representation.
	 *
	 * @param <A> Type of the appendable.
	 * @param s XML representation to unescape.
	 * @param appendable Appendable to populate with the unescaped text.
	 * @param failureFactory Factory of the exceptions for the failures while appending.
	 * @return The given appendable.
	 * @since 2.0
	 */
	public static <A extends Appendable> A unescape(final CharSequence s, final A appendable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		return unescapeEntities(new CharSequenceReader(s), appendable, failureFactory);
	}
	
	/**
	 * Unescapes the given XML representation.
	 * 
	 * @param <A> Type of the appendable.
	 * @param reader Reader providing the XML representation to unescape.
	 * @param appendable Appendable to populate with the unescaped text.
	 * @param failureFactory Factory of the exceptions for the failures while appending.
	 * @return The given appendable.
	 * @since 2.0
	 */
	public static <A extends Appendable> A unescapeEntities(final Reader reader, final A appendable, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		try {
			@SuppressWarnings("resource")
			final Scanner scanner = new Scanner(reader);
			while (!scanner.isEof()) {
				// Copy normal text.
				// TODO: escape chars according to xml specs
				appendable.append(scanner.scanToChar('&')); // TODO: add scan methods with length limit in trazere
				
				// Scan for escaping.
				if (scanner.scanChar('&')) {
					if (scanner.scanChar('#')) {
						if (scanner.scanChar('x')) {
							final String codeRepresentation = scanner.scanToChar(';').toString();
							try {
								appendable.append(new String(Character.toChars(Integer.parseInt(codeRepresentation, 16))));
								scanner.scanChar(';');
							} catch (@SuppressWarnings("unused") final NumberFormatException exception) {
								appendable.append('&').append('#').append('x').append(codeRepresentation);
							}
						} else {
							final String codeRepresentation = scanner.scanToChar(';').toString();
							try {
								appendable.append(new String(Character.toChars(Integer.parseInt(codeRepresentation))));
								scanner.scanChar(';');
							} catch (@SuppressWarnings("unused") final NumberFormatException exception) {
								appendable.append('&').append('#').append(codeRepresentation);
							}
						}
					} else {
						final String entityName = scanner.scanToChar(';').toString();
						if (ENTITY_VALUES.containsKey(entityName)) {
							appendable.append(ENTITY_VALUES.get(entityName));
							scanner.scanChar(';');
						} else {
							appendable.append('&').append(entityName);
						}
					}
				}
			}
			return appendable;
		} catch (final IOException exception) {
			throw failureFactory.build("Failed unescaping characters " + reader, exception);
		}
	}
	
	private static final Map<String, Character> ENTITY_VALUES;
	
	static {
		final Map<String, Character> values = new HashMap<>();
		values.put("amp", '&');
		values.put("apos", '\'');
		values.put("gt", '>');
		values.put("lt", '<');
		values.put("quot", '"');
		ENTITY_VALUES = values;
	}
	
	private XMLUtils() {
		// Prevents instantiation.
	}
}
