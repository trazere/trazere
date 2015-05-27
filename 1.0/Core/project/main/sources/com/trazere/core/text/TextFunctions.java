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

import com.trazere.core.functional.Function;
import com.trazere.core.util.Maybe;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The {@link TextFunctions} class provides various factories of {@link Function functions} related to text.
 * 
 * @see Function
 */
public class TextFunctions {
	// Strings.
	
	/**
	 * Builds a function that converts strings to lower case.
	 * 
	 * @return The built function.
	 * @see String#toLowerCase()
	 */
	public static Function<String, String> toLowerCase() {
		return TO_LOWER_CASE;
	}
	
	private static final Function<String, String> TO_LOWER_CASE = s -> s.toLowerCase();
	
	/**
	 * Builds a function that converts strings to upper case.
	 * 
	 * @return The built function.
	 * @see String#toUpperCase()
	 */
	public static Function<String, String> toUpperCase() {
		return TO_UPPER_CASE;
	}
	
	private static final Function<String, String> TO_UPPER_CASE = s -> s.toUpperCase();
	
	/**
	 * Builds a function that capitalizes strings.
	 * 
	 * @return The built function.
	 * @see TextUtils#capitalize(CharSequence)
	 */
	public static Function<String, String> capitalize() {
		return CAPITALIZE;
	}
	
	private static final Function<String, String> CAPITALIZE = s -> TextUtils.capitalize(s).toString();
	
	/**
	 * Builds a function that trims the head and tail of strings.
	 *
	 * @param filter Predicate to use to filter the characters to trim.
	 * @return The built function.
	 */
	public static Function<String, String> trim(final CharPredicate filter) {
		return s -> TextUtils.trim(s, filter).toString();
	}
	
	/**
	 * Builds a function that trims the head of strings.
	 *
	 * @param filter Predicate to use to filter the characters to trim.
	 * @return The built function.
	 */
	public static Function<String, String> trimHeading(final CharPredicate filter) {
		return s -> TextUtils.trimHeading(s, filter).toString();
	}
	
	/**
	 * Builds a function that trims the tail of strings.
	 *
	 * @param filter Predicate to use to filter the characters to trim.
	 * @return The built function.
	 */
	public static Function<String, String> trimTrailing(final CharPredicate filter) {
		return s -> TextUtils.trimTrailing(s, filter).toString();
	}
	
	// Numbers.
	
	/**
	 * Builds a function that formats numbers.
	 *
	 * @param <N> Type of the numbers to format.
	 * @param format Format of the numbers.
	 * @return The built function.
	 * @see TextUtils#formatNumber(NumberFormat, Number)
	 */
	public static <N extends Number> Function<N, String> formatNumber(final NumberFormat format) {
		assert null != format;
		
		return value -> TextUtils.formatNumber(format, value);
	}
	
	/**
	 * Builds a function that parses number representations.
	 *
	 * @param <N> Type of the numbers to parse.
	 * @param format Format of the numbers.
	 * @param converter Function to use to convert the parsed numbers to the excepted type.
	 * @return The built function.
	 * @see TextUtils#parseNumber(NumberFormat, Function, String)
	 */
	public static <N extends Number> Function<String, Maybe<N>> parseNumber(final NumberFormat format, final Function<Number, N> converter) {
		assert null != format;
		assert null != converter;
		
		return representation -> TextUtils.parseNumber(format, converter, representation);
	}
	
	// Dates.
	
	/**
	 * Builds a function that formats dates.
	 *
	 * @param <D> Type of the dates.
	 * @param format Format of the dates.
	 * @return The built function.
	 * @see TextUtils#formatDate(DateFormat, Date)
	 */
	public static <D extends Date> Function<D, String> formatDate(final DateFormat format) {
		assert null != format;
		
		return value -> TextUtils.formatDate(format, value);
	}
	
	/**
	 * Builds a function that parses date representations.
	 *
	 * @param format Format of the dates.
	 * @return The built function.
	 * @see TextUtils#parseDate(DateFormat, String)
	 */
	public static Function<String, Maybe<Date>> parseDate(final DateFormat format) {
		assert null != format;
		
		return representation -> TextUtils.parseDate(format, representation);
	}
	
	// UUIDs.
	
	/**
	 * Builds a function that formats UUIDs.
	 *
	 * @return The built function.
	 * @see TextUtils#formatUuid(UUID)
	 */
	public static Function<UUID, String> formatUuid() {
		return FORMAT_UUID;
	}
	
	private static final Function<UUID, String> FORMAT_UUID = TextUtils::formatUuid;
	
	/**
	 * Builds a function that parses UUID representations.
	 *
	 * @return The built function.
	 * @see TextUtils#parseUuid(String)
	 */
	public static Function<String, Maybe<UUID>> parseUuid() {
		return PARSE_UUID;
	}
	
	private static final Function<String, Maybe<UUID>> PARSE_UUID = TextUtils::parseUuid;
	
	private TextFunctions() {
		// Prevents instantiation.
	}
}