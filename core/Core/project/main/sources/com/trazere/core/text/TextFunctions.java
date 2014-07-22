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

/**
 * The {@link TextFunctions} class provides various factories of {@link Function functions} related to text.
 * 
 * @see Function
 */
public class TextFunctions {
	// Cases.
	
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
	
	//	// Numbers.
	//
	//	/**
	//	 * Builds a number formatting function.
	//	 * <p>
	//	 * This method synchronizes the format.
	//	 *
	//	 * @param <T> Type of the numbers.
	//	 * @param <X> Type of the exception.
	//	 * @param format The number format.
	//	 * @return The built function.
	//	 * @see TextUtils#formatNumber(NumberFormat, Number)
	//	 */
	//	public static <T extends Number, X extends Exception> Function1<T, String, X> formatNumber(final NumberFormat format) {
	//		assert null != format;
	//
	//		return new Function1<T, String, X>() {
	//			@Override
	//			public String evaluate(final T number) {
	//				return TextUtils.formatNumber(format, number);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a number parsing function.
	//	 *
	//	 * @param <T> Type of the numbers to parse.
	//	 * @param <X> Type of the exceptions.
	//	 * @param format The number format.
	//	 * @param converter The converter of the {@link Number} instance to the excepted type.
	//	 * @return The built function.
	//	 * @see TextUtils#parseNumber(NumberFormat, Function1, String)
	//	 */
	//	public static <T extends Number, X extends Exception> Function1<String, Maybe<T>, X> parseNumber(final NumberFormat format, final Function1<Number, T, RuntimeException> converter) {
	//		assert null != format;
	//		assert null != converter;
	//
	//		return new Function1<String, Maybe<T>, X>() {
	//			@Override
	//			public Maybe<T> evaluate(final String representation) {
	//				return TextUtils.parseNumber(format, converter, representation);
	//			}
	//		};
	//	}
	//
	//	// Dates.
	//
	//	/**
	//	 * Builds a date formatting function.
	//	 * <p>
	//	 * This method synchronizes the format.
	//	 *
	//	 * @param <T> Type of the dates.
	//	 * @param <X> Type of the exception.
	//	 * @param format The date format.
	//	 * @return The built function.
	//	 * @see TextUtils#formatDate(DateFormat, Date)
	//	 */
	//	public static <T extends Date, X extends Exception> Function1<T, String, X> formatDate(final DateFormat format) {
	//		assert null != format;
	//
	//		return new Function1<T, String, X>() {
	//			@Override
	//			public String evaluate(final T date) {
	//				return TextUtils.formatDate(format, date);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds a date parsing function.
	//	 *
	//	 * @param <X> Type of the exceptions.
	//	 * @param format The date format.
	//	 * @return The built function.
	//	 * @see TextUtils#parseDate(DateFormat, String)
	//	 */
	//	public static <X extends Exception> Function1<String, Maybe<Date>, X> parseDate(final DateFormat format) {
	//		assert null != format;
	//
	//		return new Function1<String, Maybe<Date>, X>() {
	//			@Override
	//			public Maybe<Date> evaluate(final String representation) {
	//				return TextUtils.parseDate(format, representation);
	//			}
	//		};
	//	}
	//
	//	/**
	//	 * Builds an UUID formatting function.
	//	 *
	//	 * @param <X> Type of the exception.
	//	 * @return The built function.
	//	 * @see TextUtils#formatUuid(UUID)
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <X extends Exception> Function1<UUID, String, X> formatUuid() {
	//		return (Function1<UUID, String, X>) _FORMAT_UUID;
	//	}
	//
	//	private static final Function1<UUID, String, ?> _FORMAT_UUID = new Function1<UUID, String, RuntimeException>() {
	//		@Override
	//		public String evaluate(final UUID uuid) {
	//			return TextUtils.formatUuid(uuid);
	//		}
	//	};
	//
	//	/**
	//	 * Builds an UUID parsing function.
	//	 *
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see TextUtils#parseUuid(String)
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <X extends Exception> Function1<String, Maybe<UUID>, X> parseUuid() {
	//		return (Function1<String, Maybe<UUID>, X>) _PARSE_UUID;
	//	}
	//
	//	private static final Function1<String, Maybe<UUID>, ?> _PARSE_UUID = new Function1<String, Maybe<UUID>, RuntimeException>() {
	//		@Override
	//		public Maybe<UUID> evaluate(final String representation) {
	//			return TextUtils.parseUuid(representation);
	//		}
	//	};
	
	private TextFunctions() {
		// Prevents instantiation.
	}
}
