/*
 *  Copyright 2006-2013 Julien Dufour
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
import com.trazere.core.imperative.IntCounter;
import com.trazere.core.lang.FiniteIntSequence;
import com.trazere.core.util.Maybe;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * The {@link TextUtils} class provides various utilities regarding text.
 */
public class TextUtils {
	// Sequences of characters.
	
	/**
	 * Capitalizes the given sequence of characters.
	 *
	 * @param s Sequence of characters to capitalize.
	 * @return The capitalized sequence of characters.
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
	 * Tests whether the given sequence contains the given character.
	 *
	 * @param s Sequence of characters to test.
	 * @param c Character to look for.
	 * @return <code>true</code> when the sequence contains the character, <code>false</code> otherwise.
	 */
	public static boolean contains(final CharSequence s, final char c) {
		for (final int i : new FiniteIntSequence(0, s.length())) {
			if (c == s.charAt(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether any character of the given sequence is accepted by the given filter.
	 *
	 * @param s Sequence of characters to test.
	 * @param filter Predicate to use to filter the characters.
	 * @return <code>true</code> when any character of the sequence is accepted by the filter, <code>false</code> otherwise.
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
	 * Tests whether the given sequence of characters contains the given sub-sequence.
	 *
	 * @param s Sequence of characters to test.
	 * @param sub Sub-sequence of characters to look for.
	 * @return <code>true</code> when the sequence contains the sub-sequence, <code>false</code> otherwise.
	 */
	public static boolean contains(final CharSequence s, final CharSequence sub) {
		for (final int i : new FiniteIntSequence(0, s.length())) {
			if (equals(s, i, sub, 0, sub.length())) {
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
	 * Finds the first index of the given character in the given sequence.
	 *
	 * @param s Sequence of characters to test.
	 * @param c Character to look for.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
	 */
	public static int indexOf(final CharSequence s, final char c) {
		return indexOf(s, c, 0);
	}
	
	/**
	 * Finds the first index of the given character in the given sequence.
	 *
	 * @param s Sequence of characters to test.
	 * @param c Character to look for.
	 * @param offset Index at which the search should start.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
	 */
	public static int indexOf(final CharSequence s, final char c, final int offset) {
		for (final int i : new FiniteIntSequence(offset, s.length())) {
			if (c == s.charAt(i)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Finds the index of the first character accepted by the given filter in the given sequence.
	 *
	 * @param s Sequence of characters to test.
	 * @param filter Predicate to use to filter the characters.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
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
	 * Finds the first index of the given sub-sequence in the given sequence of characters.
	 *
	 * @param s Sequence of characters to test.
	 * @param sub Sub-sequence of characters to look for.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
	 */
	public static int indexOf(final CharSequence s, final CharSequence sub) {
		return indexOf(s, sub, 0);
	}
	
	/**
	 * Finds the first index of the given sub-sequence in the given sequence of characters.
	 *
	 * @param s Sequence of characters to test.
	 * @param sub Character to look for.
	 * @param offset Index at which the search should start.
	 * @return The index of the first accepted character, or <code>-1</code> when no characters is accepted by the filter.
	 */
	public static int indexOf(final CharSequence s, final CharSequence sub, final int offset) {
		for (final int i : new FiniteIntSequence(0, s.length())) {
			if (equals(s, i, sub, 0, sub.length())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Counts the number of occurences of the given sub-sequence in the given sequence of characters.
	 * <p>
	 * The occurences may overlap.
	 *
	 * @param s String containing the substring occurences to count.
	 * @param sub Substring whose occurences should be counter.
	 * @return The number of occurences.
	 */
	public static int count(final CharSequence s, final CharSequence sub) {
		final IntCounter count = new IntCounter();
		for (final int i : new FiniteIntSequence(0, Math.max(0, s.length() - sub.length()))) {
			if (equals(s, i, sub, 0, sub.length())) {
				count.inc();
			}
		}
		return count.get();
	}
	
	private static boolean equals(final CharSequence s1, final int offset1, final CharSequence s2, final int offset2, final int n) {
		for (final int i : new FiniteIntSequence(0, n)) {
			if (s1.charAt(offset1 + i) != s2.charAt(offset2 + i)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Filters the characters of the given sequence using the given filter.
	 *
	 * @param s Sequence of characters to filter.
	 * @param filter Predicate to use to filter the characters.
	 * @return A sequence of the accepted characters.
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
	/**
	 * Adjusts the length of the given string.
	 * <p>
	 * The string is truncated when it is too long and right-padded using spaces when it it to short.
	 * 
	 * @param s String to adjust.
	 * @param length Desired length, or <code>0</code> to leave the string as it is.
	 * @return The adjusted string.
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
	 * @param object The object.
	 * @return The description.
	 */
	public static String description(final Describable object) {
		final Description description = new Description(className(object.getClass()));
		object.appendDescription(description);
		return description.toString();
	}
	
	// Bytes.
	
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
	 * Computes the hexadecimal text representation of the given array of bytes.
	 *
	 * @param bytes Bytes to render.
	 * @return The text representation.
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
	
	// Numbers.
	
	/**
	 * Formats the given number using the given format.
	 * <p>
	 * This method synchronizes the format to ensure reentrancy.
	 *
	 * @param format Format of the number.
	 * @param value Number to format.
	 * @return The representation of the number.
	 */
	public static String formatNumber(final NumberFormat format, final Number value) {
		synchronized (format) {
			return format.format(value);
		}
	}
	
	/**
	 * Parses the given number representation according to the given format.
	 * <p>
	 * This method synchronizes the format to ensure reentrancy.
	 *
	 * @param <N> Type of the number to parse.
	 * @param format Format of the number.
	 * @param converter Function to use to convert the parsed number to the excepted type.
	 * @param representation Representation of the number to parse.
	 * @return The parsed number, or nothing when the representation is not valid.
	 */
	// TODO: return Result
	public static <N extends Number> Maybe<N> parseNumber(final NumberFormat format, final Function<? super Number, ? extends N> converter, final String representation) {
		synchronized (format) {
			final ParsePosition position = new ParsePosition(0);
			final Number number = format.parse(representation, position);
			if (null != number && position.getIndex() == representation.length()) {
				return Maybe.<N>some(converter.evaluate(number));
			} else {
				return Maybe.none();
			}
		}
	}
	
	// Dates.
	
	/**
	 * Formats the given date using the given format.
	 * <p>
	 * This method synchronizes the format to ensure reentrancy.
	 *
	 * @param format Format of the date.
	 * @param value Date to format.
	 * @return The representation of the date.
	 */
	public static String formatDate(final DateFormat format, final Date value) {
		synchronized (format) {
			return format.format(value);
		}
	}
	
	/**
	 * Parses the given date representation according to the given format.
	 * <p>
	 * This method synchronizes the format to ensure reentrancy.
	 *
	 * @param format Format of the date.
	 * @param representation Representation of the date to parse.
	 * @return The parsed date, or nothing when the representation is not valid.
	 */
	// TODO: return Result
	public static Maybe<Date> parseDate(final DateFormat format, final String representation) {
		synchronized (format) {
			final ParsePosition position = new ParsePosition(0);
			final Date date = format.parse(representation, position);
			if (null != date && position.getIndex() == representation.length()) {
				return Maybe.some(date);
			} else {
				return Maybe.none();
			}
		}
	}
	
	// Temporals.
	
	/**
	 * Formats the given temporal using the given format.
	 * <p>
	 * This method synchronizes the format to ensure reentrancy.
	 *
	 * @param formatter Format of the temporal.
	 * @param value Temporal to format.
	 * @return The representation of the temporal.
	 */
	public static String formatTemporal(final DateTimeFormatter formatter, final TemporalAccessor value) {
		return formatter.format(value);
	}
	
	/**
	 * Parses the given temporal representation according to the given format.
	 * <p>
	 * This method synchronizes the format to ensure reentrancy.
	 * 
	 * @param <T> Type of the temporal.
	 * @param formatter Formatter of the temporal.
	 * @param query Query that defines the type of the temporal.
	 * @param representation Representation of the temporal to parse.
	 * @return The parsed temporal, or nothing when the representation is not valid.
	 */
	// TODO: return Result
	public static <T extends TemporalAccessor> Maybe<T> parseTemporal(final DateTimeFormatter formatter, final TemporalQuery<T> query, final String representation) {
		try {
			return Maybe.some(formatter.parse(representation, query));
		} catch (final DateTimeParseException exception) {
			return Maybe.none();
		}
	}
	
	// UUIDs.
	
	/**
	 * Formats the given UUID using the standard format.
	 *
	 * @param value UUID to format.
	 * @return The representation of the UUID.
	 */
	public static String formatUuid(final UUID value) {
		return value.toString();
	}
	
	/**
	 * Parses the given UUID representation according to the standard format.
	 *
	 * @param representation Representation of the UUID to parse.
	 * @return The parsed UUID, or nothing when the representation is not valid.
	 */
	// TODO: return Result
	public static Maybe<UUID> parseUuid(final String representation) {
		try {
			return Maybe.some(UUID.fromString(representation));
		} catch (final IllegalArgumentException exception) {
			return Maybe.none();
		}
	}
	
	private TextUtils() {
		// Prevents instantiation.
	}
}
