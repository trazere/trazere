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
package com.trazere.util.lang;

import com.trazere.util.function.Predicate1;

/**
 * The {@link LangPredicates} class provides various factories of predicates related to the language.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class LangPredicates {
	/**
	 * Builds a predicate that test the membership for the given type.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param type The type to match.
	 * @return The built predicate.
	 * @deprecated Use {@link LangPredicates#isInstanceOf(Class)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Predicate1<T, X> isInstanceOf(final Class<? extends T> type) {
		assert null != type;
		
		return new Predicate1<T, X>() {
			@Override
			public boolean evaluate(final T value) {
				return null != value && type.isInstance(value);
			}
		};
	}
	
	private LangPredicates() {
		// Prevents instantiation.
	}
}
