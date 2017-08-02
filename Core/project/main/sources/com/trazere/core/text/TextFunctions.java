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

import java.util.function.Function;

/**
 * The {@link TextFunctions} class provides various factories of {@link Function functions} related to text.
 * 
 * @see Function
 * @since 2.0
 */
public class TextFunctions {
	// Strings.
	
	/**
	 * Builds a function that capitalizes strings.
	 * 
	 * @return The built function.
	 * @see TextUtils#capitalize(CharSequence)
	 * @since 2.0
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
	 * @since 2.0
	 */
	public static Function<String, String> trim(final CharPredicate filter) {
		return s -> TextUtils.trim(s, filter).toString();
	}
	
	/**
	 * Builds a function that trims the head of strings.
	 *
	 * @param filter Predicate to use to filter the characters to trim.
	 * @return The built function.
	 * @since 2.0
	 */
	public static Function<String, String> trimHeading(final CharPredicate filter) {
		return s -> TextUtils.trimHeading(s, filter).toString();
	}
	
	/**
	 * Builds a function that trims the tail of strings.
	 *
	 * @param filter Predicate to use to filter the characters to trim.
	 * @return The built function.
	 * @since 2.0
	 */
	public static Function<String, String> trimTrailing(final CharPredicate filter) {
		return s -> TextUtils.trimTrailing(s, filter).toString();
	}
	
	private TextFunctions() {
		// Prevents instantiation.
	}
}
