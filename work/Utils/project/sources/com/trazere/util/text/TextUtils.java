/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.function.Function1;
import com.trazere.util.function.Functions;
import com.trazere.util.function.Procedure2;
import com.trazere.util.lang.Counter;
import com.trazere.util.lang.MutableBoolean;
import com.trazere.util.lang.MutableInt;
import com.trazere.util.type.Maybe;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * The {@link TextUtils} class provides various helpers regarding the manipulation of text.
 */
public class TextUtils {
	/**
	 * Compares the given string ignoring case.
	 * <p>
	 * This method supports comparisons of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param string1 The first string to compare. May be <code>null</code>.
	 * @param string2 The second string to compare. May be <code>null</code>.
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
	 * Tests whether the given string contains some characters accepted by the given filter.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @param s The string.
	 * @return <code>true</code> when the string contains some character accepted by the filter.
	 * @throws X When some filter evaluation fails.
	 */
	public static <X extends Exception> boolean contains(final CharPredicate<X> filter, final String s)
	throws X {
		assert null != filter;
		assert null != s;
		
		for (int index = 0; index < s.length(); index += 1) {
			if (filter.evaluate(s.charAt(index))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether the all characters of the string contains matches the given filter.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @param s The string.
	 * @return <code>true</code> when the string contains some character accepted by the filter.
	 * @throws X When some filter evaluation fails.
	 */
	public static <X extends Exception> boolean matches(final CharPredicate<X> filter, final String s)
	throws X {
		assert null != filter;
		assert null != s;
		
		for (int index = 0; index < s.length(); index += 1) {
			if (!filter.evaluate(s.charAt(index))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Filters the given string using the given filter.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @param s The string.
	 * @return The filtered string.
	 * @throws X When some filter evaluation fails.
	 */
	public static <X extends Exception> String filter(final CharPredicate<X> filter, final String s)
	throws X {
		assert null != filter;
		assert null != s;
		
		final StringBuilder result = new StringBuilder();
		for (int index = 0; index < s.length(); index += 1) {
			final char c = s.charAt(index);
			if (filter.evaluate(c)) {
				result.append(c);
			}
		}
		return result.toString();
	}
	
	/**
	 * Trims the heading and trailing characters of the given string accepted by the given filter.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @param s The string to trim.
	 * @return The trimmed string.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <X extends Exception> String trim(final CharPredicate<X> filter, final String s)
	throws X {
		assert null != filter;
		assert null != s;
		
		final int length = s.length();
		int start = 0;
		while (start < length && filter.evaluate(s.charAt(start))) {
			start += 1;
		}
		int stop = length;
		while (stop > start && filter.evaluate(s.charAt(stop - 1))) {
			stop -= 1;
		}
		return start > 0 || stop < length ? s.substring(start, stop) : s;
	}
	
	/**
	 * Trims the heading characters of the given string accepted by the given filter.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @param s The string to trim.
	 * @return The trimmed string.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <X extends Exception> String trimHeading(final CharPredicate<X> filter, final String s)
	throws X {
		assert null != filter;
		assert null != s;
		
		final int length = s.length();
		int index = 0;
		while (index < length && filter.evaluate(s.charAt(index))) {
			index += 1;
		}
		return index > 0 ? s.substring(index) : s;
	}
	
	/**
	 * Trims the trailing characters of the given string accepted by the given filter.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param filter The filter.
	 * @param s The string to trim.
	 * @return The trimmed string.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <X extends Exception> String trimTrailing(final CharPredicate<X> filter, final String s)
	throws X {
		assert null != filter;
		assert null != s;
		
		final int length = s.length();
		int index = length;
		while (index > 0 && filter.evaluate(s.charAt(index - 1))) {
			index -= 1;
		}
		return index < length ? s.substring(0, index) : s;
	}
	
	/**
	 * Splits the given string according to the given delimiter.
	 * 
	 * @param <C> Type of the collection filled with the results.
	 * @param string The string to split.
	 * @param delimiter The delimiter string.
	 * @param trim Indicates whether the empty tokens are trimmed or not.
	 * @param ignoreEmpty Indicates whether the empty tokens are ignored or not.
	 * @param results The collection to fill with the tokens.
	 * @return The given result collection.
	 */
	public static <C extends Collection<? super String>> C split(final String string, final String delimiter, final boolean trim, final boolean ignoreEmpty, final C results) {
		assert null != string;
		assert null != delimiter;
		
		for (final String token : string.split(delimiter.replaceAll("[^\\w]", "\\\\$0"))) {
			final String trimmedToken = trim ? token.trim() : token;
			if (!ignoreEmpty || trimmedToken.length() > 0) {
				results.add(trimmedToken);
			}
		}
		return results;
	}
	
	/**
	 * Counts the number of occurences of the given substring in the given string.
	 * <p>
	 * The occurences may overlap.
	 * 
	 * @param string The string.
	 * @param sub The substring.
	 * @return The number of occurences.
	 */
	public static int occurences(final String string, final String sub) {
		assert null != string;
		assert null != sub;
		
		// Count.
		final Counter count = new Counter();
		final MutableInt index = new MutableInt(string.indexOf(sub));
		while (index.get() >= 0) {
			count.inc();
			index.set(string.indexOf(sub, index.get() + 1));
		}
		return count.get();
	}
	
	/**
	 * Joins the given string tokens using the given delimiter.
	 * 
	 * @param tokens The tokens.
	 * @param delimiter The delimiter.
	 * @return The resulting string.
	 */
	public static String join(final Collection<String> tokens, final String delimiter) {
		return join(tokens, delimiter, new StringBuilder()).toString();
	}
	
	/**
	 * Joins the given string tokens using the given delimiter.
	 * 
	 * @param tokens The tokens.
	 * @param delimiter The delimiter.
	 * @param builder The string builder to fill.
	 * @return The given string builder.
	 */
	public static StringBuilder join(final Collection<String> tokens, final String delimiter, final StringBuilder builder) {
		assert null != tokens;
		
		return join(tokens.iterator(), delimiter, builder);
	}
	
	/**
	 * Joins the string tokens provided by the given iterator using the given delimiter.
	 * 
	 * @param tokens The tokens.
	 * @param delimiter The delimiter.
	 * @return The resulting string.
	 */
	public static String join(final Iterator<String> tokens, final String delimiter) {
		return join(tokens, delimiter, new StringBuilder()).toString();
	}
	
	/**
	 * Joins the string tokens provided by the given iterator using the given delimiter.
	 * 
	 * @param tokens The tokens.
	 * @param delimiter The delimiter.
	 * @param builder The string builder to fill.
	 * @return The given string builder.
	 */
	public static StringBuilder join(final Iterator<String> tokens, final String delimiter, final StringBuilder builder) {
		return join(tokens, Functions.<String, RuntimeException>identity(), delimiter, builder);
	}
	
	/**
	 * Joins the given string tokens using the given renderer and delimiter.
	 * 
	 * @param <T> Type of the tokens.
	 * @param <X> Type of the exceptions.
	 * @param tokens The tokens.
	 * @param renderer The token renderer.
	 * @param delimiter The delimiter.
	 * @return The resulting string.
	 * @throws X When some rendering fails.
	 */
	public static <T, X extends Exception> String join(final Collection<T> tokens, final Function1<? super T, String, X> renderer, final String delimiter)
	throws X {
		return join(tokens, renderer, delimiter, new StringBuilder()).toString();
	}
	
	/**
	 * Joins the given string tokens using the given renderer and delimiter.
	 * 
	 * @param <T> Type of the tokens.
	 * @param <X> Type of the exceptions.
	 * @param tokens The tokens.
	 * @param renderer The token renderer.
	 * @param delimiter The delimiter.
	 * @param builder The string builder to fill.
	 * @return The given string builder.
	 * @throws X When some rendering fails.
	 */
	public static <T, X extends Exception> StringBuilder join(final Collection<T> tokens, final Function1<? super T, String, X> renderer, final String delimiter, final StringBuilder builder)
	throws X {
		assert null != tokens;
		
		return join(tokens.iterator(), renderer, delimiter, builder);
	}
	
	/**
	 * Joins the string tokens provided by the given iterator using the given renderer and delimiter.
	 * 
	 * @param <T> Type of the tokens.
	 * @param <X> Type of the exceptions.
	 * @param tokens The tokens.
	 * @param renderer The token renderer.
	 * @param delimiter The delimiter.
	 * @param builder The string builder to fill.
	 * @return The given string builder.
	 * @throws X When some rendering fails.
	 */
	public static <T, X extends Exception> StringBuilder join(final Iterator<T> tokens, final Function1<? super T, String, X> renderer, final String delimiter, final StringBuilder builder)
	throws X {
		assert null != renderer;
		
		return join(tokens, new Procedure2<StringBuilder, T, X>() {
			public void execute(final StringBuilder builder_, final T token)
			throws X {
				builder_.append(renderer.evaluate(token));
			}
		}, delimiter, builder);
	}
	
	/**
	 * Joins the string tokens provided by the given iterator using the given renderer and delimiter.
	 * 
	 * @param <T> Type of the tokens.
	 * @param <X> Type of the exceptions.
	 * @param tokens The tokens.
	 * @param renderer The token renderer.
	 * @param delimiter The delimiter.
	 * @param builder The string builder to fill.
	 * @return The given string builder.
	 * @throws X When some rendering fails.
	 */
	public static <T, X extends Exception> StringBuilder join(final Iterator<T> tokens, final Procedure2<StringBuilder, ? super T, X> renderer, final String delimiter, final StringBuilder builder)
	throws X {
		assert null != tokens;
		assert null != renderer;
		assert null != delimiter;
		assert null != builder;
		
		final MutableBoolean first = new MutableBoolean(true);
		while (tokens.hasNext()) {
			final T token = tokens.next();
			if (!first.get()) {
				builder.append(delimiter);
			} else {
				first.set(false);
			}
			renderer.execute(builder, token);
		}
		
		return builder;
	}
	
	/**
	 * Builds a function which converts strings to lower case.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see String#toLowerCase()
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, String, X> toLowerCaseFunction() {
		return (Function1<String, String, X>) _TO_LOWER_CASE_FUNCTION;
	}
	
	private static final Function1<String, String, ?> _TO_LOWER_CASE_FUNCTION = new Function1<String, String, RuntimeException>() {
		public String evaluate(final String value) {
			assert null != value;
			
			return value.toLowerCase();
		}
	};
	
	/**
	 * Builds a function which converts strings to upper case.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see String#toUpperCase()
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, String, X> toUpperCaseFunction() {
		return (Function1<String, String, X>) _TO_UPPER_CASE_FUNCTION;
	}
	
	private static final Function1<String, String, ?> _TO_UPPER_CASE_FUNCTION = new Function1<String, String, RuntimeException>() {
		public String evaluate(final String value) {
			assert null != value;
			
			return value.toUpperCase();
		}
	};
	
	/**
	 * Capitalizes the given string.
	 * 
	 * @param s The string to capitalize.
	 * @return The capitalized string.
	 */
	public static String capitalize(final String s) {
		assert null != s;
		
		if (s.length() > 0) {
			return s.substring(0, 1).toUpperCase() + s.substring(1);
		} else {
			return s;
		}
	}
	
	/**
	 * Builds a function which capitalizes strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see #capitalize(String)
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, String, X> capitalizeFunction() {
		return (Function1<String, String, X>) _CAPITALIZE_FUNCTION;
	}
	
	private static final Function1<String, String, ?> _CAPITALIZE_FUNCTION = new Function1<String, String, RuntimeException>() {
		public String evaluate(final String s) {
			return capitalize(s);
		}
	};
	
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
	 * Computes the hexadecimal representation of the given array of bytes.
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
			builder.append(HEX_DIGITS[b >> 4 & 0xF]);
			builder.append(HEX_DIGITS[b & 0xF]);
		}
		return builder.toString();
	}
	
	/**
	 * Builds a regular expression corresponding to the given string.
	 * 
	 * @param s The string.
	 * @return The regular expression.
	 */
	public static String regexp(final String s) {
		assert null != s;
		
		return s.replaceAll("[^\\w]", "\\\\$0"); // Escape all special chars
	}
	
	/**
	 * Computes the name of the given class (without package information).
	 * 
	 * @param class_ The class.
	 * @return The name of the class.
	 */
	public static String computeClassName(final Class<?> class_) {
		assert null != class_;
		
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
	public static String computeDescription(final Describable object) {
		assert null != object;
		
		final Description description = Description.buildObjectDescription(object);
		object.fillDescription(description);
		return description.toString();
	}
	
	/**
	 * Formats the given number using the given format.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param format The number format.
	 * @param number The number to format.
	 * @return The number representation.
	 */
	public static String formatNumber(final NumberFormat format, final Number number) {
		assert null != format;
		assert null != number;
		
		synchronized (format) {
			return format.format(number);
		}
	}
	
	/**
	 * Builds a number formatting function.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param <T> Type of the numbers.
	 * @param <X> Type of the exception.
	 * @param format The number format.
	 * @return The built function.
	 */
	public static <T extends Number, X extends Exception> Function1<T, String, X> formatNumberFunction(final NumberFormat format) {
		assert null != format;
		
		return new Function1<T, String, X>() {
			public String evaluate(final T number) {
				return formatNumber(format, number);
			}
		};
	}
	
	/**
	 * Parses the given number representation using the given format and extractor.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param <T> Type of the number to parse.
	 * @param format The number format.
	 * @param converter The extractor of the {@link Number} instance of the excepted type.
	 * @param representation The representation to parse.
	 * @return The parsed number.
	 */
	public static <T extends Number> Maybe<T> parseNumber(final NumberFormat format, final Function1<Number, T, RuntimeException> converter, final String representation) {
		assert null != format;
		assert null != converter;
		assert null != representation;
		
		synchronized (format) {
			final ParsePosition position = new ParsePosition(0);
			final Number number = format.parse(representation, position);
			if (null != number && position.getIndex() == representation.length()) {
				return Maybe.some(converter.evaluate(number));
			} else {
				return Maybe.none();
			}
		}
	}
	
	/**
	 * Builds a number parsing function.
	 * 
	 * @param <T> Type of the numbers to parse.
	 * @param <X> Type of the exceptions.
	 * @param format The number format.
	 * @param converter The converter of the {@link Number} instance to the excepted type.
	 * @return The built function.
	 * @see #parseNumber(NumberFormat, Function1, String)
	 */
	public static <T extends Number, X extends Exception> Function1<String, Maybe<T>, X> parseNumberFunction(final NumberFormat format, final Function1<Number, T, RuntimeException> converter) {
		assert null != format;
		assert null != converter;
		
		return new Function1<String, Maybe<T>, X>() {
			public Maybe<T> evaluate(final String representation) {
				return parseNumber(format, converter, representation);
			}
		};
	}
	
	/**
	 * Formats the given date using the given format.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param format The date format.
	 * @param date The date to format.
	 * @return The date representation.
	 */
	public static String formatDate(final DateFormat format, final Date date) {
		assert null != format;
		assert null != date;
		
		synchronized (format) {
			return format.format(date);
		}
	}
	
	/**
	 * Builds a date formatting function.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param <T> Type of the dates.
	 * @param <X> Type of the exception.
	 * @param format The date format.
	 * @return The built function.
	 */
	public static <T extends Date, X extends Exception> Function1<T, String, X> formatDateFunction(final DateFormat format) {
		assert null != format;
		
		return new Function1<T, String, X>() {
			public String evaluate(final T date) {
				return formatDate(format, date);
			}
		};
	}
	
	/**
	 * Parses the given date representation using the given format.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param format The date format.
	 * @param representation The representation to parse.
	 * @return The parsed number.
	 */
	public static Maybe<Date> parseDate(final DateFormat format, final String representation) {
		assert null != format;
		assert null != representation;
		
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
	
	/**
	 * Builds a date parsing function.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param format The date format.
	 * @return The built function.
	 * @see #parseDate(DateFormat, String)
	 */
	public static <X extends Exception> Function1<String, Maybe<Date>, X> parseDateFunction(final DateFormat format) {
		assert null != format;
		
		return new Function1<String, Maybe<Date>, X>() {
			public Maybe<Date> evaluate(final String representation) {
				return parseDate(format, representation);
			}
		};
	}
	
	private TextUtils() {
		// Prevents instantiation.
	}
}
