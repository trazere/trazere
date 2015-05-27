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

import com.trazere.core.functional.Predicate;

/**
 * The {@link TextPredicates} class provides various factories of {@link Predicate predicates} related to text.
 * 
 * @see Predicate
 */
public class TextPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for empty char sequences.
	 * 
	 * @return The built predicate.
	 */
	public static Predicate<CharSequence> isEmpty() {
		return IS_EMPTY;
	}
	
	private static Predicate<CharSequence> IS_EMPTY = s -> 0 == s.length();
	
	private TextPredicates() {
		// Prevents instantiation.
	}
}
