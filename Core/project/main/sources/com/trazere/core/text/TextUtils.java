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
package com.trazere.core.text;

import com.trazere.core.collection.Feed;
import com.trazere.core.functional.Function;
import com.trazere.core.lang.FiniteIntSequence;
import com.trazere.core.util.Maybe;
import java.util.regex.Pattern;

/**
 * The {@link TextUtils} class provides various utilities regarding text.
 * 
 * @since 2.0
 */
public class TextUtils {
	// Sequences of characters.
	
	/**
	 * Capitalizes the given sequence of characters.
	 *
	 * @param s Sequence of characters to capitalize.
	 * @return The capitalized sequence of characters.
	 * @since 2.0
	 */
	public static CharSequence capitalize(final CharSequence s) {
		if (s.length() > 0) {
			final StringBuilder result = new StringBuilder();
			result.append(Character.toUpperCase(s.charAt(0)));
			result.append(s, 1, s.length());
			return result;
		} else {
			return "";
		}
	}
	
	/**
	 * Tests whether any character of the given sequence is accepted by the given filter.
	 *
	 * @param s Sequence of characters to test.
	 * @param filter Predicate to use to filter the characters.
	 * @return <code>true</code> when any character of the sequence is accepted by the filter, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static boolean contains(final CharSequence s, final CharPredicate filter) {
		for (final int i : new FiniteIntSequence(0, s.length())) {
			if (filter.evaluate(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether all characters of the given sequence are accepted by the given filter.
	 *
	 * @param s Sequence of characters to test.
	 * @param filter Predicate to use to filter the characters.
	 * @return <code>true</code> when all characters of the sequence is accepted by the filter, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public static boolean matches(final CharSequence s, final CharPredicate filter) {
		for (final int i : new FiniteIntSequence(0, s.length())) {
			if (!filter.evaluate(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Finds the index of the first character accepted by the given filter in the given sequence.
	 *
	 * @param s Sequence of characters to test.
	 * @param filter Predicate to use to filter the characters.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
	 * @since 2.0
	 */
	public static int indexOf(final CharSequence s, final CharPredicate filter) {
		return indexOf(s, filter, 0);
	}
	
	/**
	 * Finds the index of the first character accepted by the given filter in the given sequence.
	 *
	 * @param s Sequence of characters to test.
	 * @param filter Predicate to use to filter the characters.
	 * @param offset Index at which the search should start.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
	 * @since 2.0
	 */
	public static int indexOf(final CharSequence s, final CharPredicate filter, final int offset) {
		for (final int i : new FiniteIntSequence(offset, s.length())) {
			if (filter.evaluate(s.charAt(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Filters the characters of the given sequence using the given filter.
	 *
	 * @param s Sequence of characters to filter.
	 * @param filter Predicate to use to filter the characters.
	 * @return A sequence of the accepted characters.
	 * @since 2.0
	 */
	public static CharSequence filter(final CharSequence s, final CharPredicate filter) {
		final StringBuilder result = new StringBuilder();
		for (final int i : new FiniteIntSequence(0, s.length())) {
			final char c = s.charAt(i);
			if (filter.evaluate(c)) {
				result.append(c);
			}
		}
		return result;
	}
	
	/**
	 * Trims the heading and trailing characters accepted by the given filter of the given sequence.
	 *
	 * @param s Sequence of characters to trim.
	 * @param filter Predicate to use to filter the characters.
	 * @return A trimmed sequence of characters.
	 * @since 2.0
	 */
	public static CharSequence trim(final CharSequence s, final CharPredicate filter) {
		final int length = s.length();
		int start = 0;
		while (start < length && filter.evaluate(s.charAt(start))) {
			start += 1;
		}
		int end = length;
		while (end > start && filter.evaluate(s.charAt(end - 1))) {
			end -= 1;
		}
		return start > 0 || end < length ? s.subSequence(start, end) : s;
	}
	
	/**
	 * Trims the heading characters accepted by the given filter of the given sequence.
	 *
	 * @param s Sequence of characters to trim.
	 * @param filter Predicate to use to filter the characters.
	 * @return A trimmed sequence of characters.
	 * @since 2.0
	 */
	public static CharSequence trimHeading(final CharSequence s, final CharPredicate filter) {
		final int length = s.length();
		int index = 0;
		while (index < length && filter.evaluate(s.charAt(index))) {
			index += 1;
		}
		return index > 0 ? s.subSequence(index, s.length()) : s;
	}
	
	/**
	 * Trims the trailing characters accepted by the given filter of the given sequence.
	 *
	 * @param s Sequence of characters to trim.
	 * @param filter Predicate to use to filter the characters.
	 * @return A trimmed sequence of characters.
	 * @since 2.0
	 */
	public static CharSequence trimTrailing(final CharSequence s, final CharPredicate filter) {
		final int length = s.length();
		int index = length;
		while (index > 0 && filter.evaluate(s.charAt(index - 1))) {
			index -= 1;
		}
		return index < length ? s.subSequence(0, index) : s;
	}
	
	// TODO: arg for padding
	// TODO: arg for aligment
	/**
	 * Adjusts the length of the given string.
	 * <p>
	 * The string is truncated when it is too long and right-padded using spaces when it it to short.
	 * 
	 * @param s String to adjust.
	 * @param length Desired length, or <code>0</code> to leave the string as it is.
	 * @return The adjusted string.
	 * @since 2.0
	 */
	public static String adjust(final String s, final int length) {
		final int currentLength = s.length();
		if (length <= 0 || currentLength == length) {
			return s;
		} else if (currentLength > length) {
			return s.substring(0, length);
		} else {
			final StringBuilder builder = new StringBuilder(length);
			builder.append(s);
			while (builder.length() < length) {
				builder.append(' ');
			}
			return builder.toString();
		}
	}
	
	/**
	 * Joins the given text tokens with the given delimiter ignoring the empty ones.
	 *
	 * @param tokens Tokens to join.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @return The representation of the joined tokens.
	 * @since 2.0
	 */
	public static CharSequence join(final Iterable<? extends CharSequence> tokens, final CharSequence delimiter) {
		return Joiners.joiner(true, delimiter).join(tokens);
	}
	
	/**
	 * Joins the given text tokens with the given delimiter, using the given rendering function and ignoring the empty representations.
	 *
	 * @param <T> Type of the tokens.
	 * @param tokens Tokens to join.
	 * @param renderer Function to use to compute the string representation of the tokens.
	 * @param delimiter Delimiter to insert between the tokens.
	 * @return The representation of the joined tokens.
	 * @since 2.0
	 */
	public static <T> CharSequence join(final Iterable<? extends T> tokens, final Function<? super T, ? extends CharSequence> renderer, final CharSequence delimiter) {
		return Joiners.joiner(renderer, true, delimiter).join(tokens);
	}
	
	/**
	 * Splits the given sequence of characters with the given delimiter.
	 * 
	 * @param s Sequence of characters to split.
	 * @param delimiter Delimiter to use.
	 * @param trim Indicates whether to trim the whitespaces of the tokens.
	 * @return A feed of the split tokens.
	 * @since 2.0
	 */
	public static Feed<CharSequence> split(final CharSequence s, final char delimiter, final boolean trim) {
		return Splitters.delimiter(delimiter, false, trim ? Maybe.some(CharPredicates.whitespace()) : Maybe.<CharPredicate>none(), true).split(s);
	}
	
	/**
	 * Splits the given sequence of characters with the given delimiter.
	 * 
	 * @param s Sequence of characters to split.
	 * @param delimiter Delimiter to use.
	 * @param trim Indicates whether to trim the whitespaces of the tokens.
	 * @return A feed of the split tokens.
	 * @since 2.0
	 */
	public static Feed<CharSequence> split(final CharSequence s, final CharSequence delimiter, final boolean trim) {
		return Splitters.delimiter(delimiter, false, trim ? Maybe.some(CharPredicates.whitespace()) : Maybe.<CharPredicate>none(), true).split(s);
	}
	
	/**
	 * Splits the given sequence of characters with the given delimiter.
	 * 
	 * @param s Sequence of characters to split.
	 * @param delimiter Delimiter to use.
	 * @param trim Indicates whether to trim the whitespaces of the tokens.
	 * @return A feed of the split tokens.
	 * @since 2.0
	 */
	public static Feed<CharSequence> split(final CharSequence s, final Pattern delimiter, final boolean trim) {
		return Splitters.delimiter(delimiter, false, trim ? Maybe.some(CharPredicates.whitespace()) : Maybe.<CharPredicate>none(), true).split(s);
	}
	
	// Descriptions.
	
	// TODO: move to ClassUtils ?
	/**
	 * Computes the name of the given class (without package information).
	 * 
	 * @param class_ Class to compute the name of.
	 * @return The name of the class.
	 * @since 2.0
	 */
	public static String className(final Class<?> class_) {
		final String fullName = class_.getName();
		final int index = fullName.lastIndexOf('.');
		return index > 0 ? fullName.substring(index + 1) : fullName;
	}
	
	/**
	 * Computes the description of the given object.
	 * <p>
	 * This method aims to be used in {@link Object#toString()} implementations.
	 * 
	 * @param object Object to describe.
	 * @return The description.
	 * @since 2.0
	 */
	public static String description(final Describable object) {
		return description(object, DescriptionFormats.BASIC);
	}
	
	/**
	 * Computes the description of the given object.
	 * <p>
	 * This method aims to be used in {@link Object#toString()} implementations.
	 * 
	 * @param object Object to describe.
	 * @param format Format to use.
	 * @return The description.
	 * @since 2.0
	 */
	public static String description(final Describable object, final DescriptionFormat format) {
		final DescriptionBuilder builder = new DescriptionBuilder(format, className(object.getClass()));
		object.appendDescription(builder);
		return builder.toString();
	}
	
	// Bytes.
	
	/**
	 * Array of the hexadecimal digits characters (upper case).
	 * 
	 * @since 2.0
	 */
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
	 * Computes the hexadecimal text representation of the given array of bytes.
	 *
	 * @param bytes Bytes to render.
	 * @return The text representation.
	 * @since 2.0
	 */
	public static CharSequence toHex(final byte[] bytes) {
		final StringBuilder builder = new StringBuilder();
		for (final int i : new FiniteIntSequence(0, bytes.length)) {
			final byte b = bytes[i];
			builder.append(HEX_DIGITS[b >> 4 & 0xF]);
			builder.append(HEX_DIGITS[b & 0xF]);
		}
		return builder;
	}
	
	private TextUtils() {
		// Prevents instantiation.
	}
}
