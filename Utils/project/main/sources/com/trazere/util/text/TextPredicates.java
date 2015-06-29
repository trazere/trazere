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
package com.trazere.util.text;

import com.trazere.util.function.Predicate1;

/**
 * The {@link TextPredicates} class provides various factories of predicates related to text.
 * 
 * @see Predicate1
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class TextPredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for empty strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 * @deprecated Use {@link com.trazere.core.text.TextPredicates#isEmpty()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Predicate1<String, X> isEmptyString() {
		return (Predicate1<String, X>) _IS_EMPTY_STRING;
	}
	
	private static Predicate1<String, ?> _IS_EMPTY_STRING = new Predicate1<String, RuntimeException>() {
		@Override
		public boolean evaluate(final String value) {
			assert null != value;
			
			return 0 == value.length();
		}
	};
	
	private TextPredicates() {
		// Prevents instantiation.
	}
}
