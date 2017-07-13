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
package com.trazere.core.lang;

import com.trazere.core.functional.Function;

/**
 * The {@link ObjectFunctions} class provides {@link Function functions} related to {@link Object objects}.
 * 
 * @see Function
 * @see Object
 * @since 2.0
 */
public class ObjectFunctions {
	// TODO: move to LangFunctions ?
	/**
	 * Builds a function that matches objects against the given type.
	 * <p>
	 * The built function evaluates to matched argument object, or throws an exception when the argument object does not match the given type.
	 * 
	 * @param <T> Type of the arguments.
	 * @param <R> Type of the match.
	 * @param type Type against which to match.
	 * @param mismatchFactory Factory of the exceptions for the mismatches.
	 * @return The built extractor.
	 * @see ObjectUtils#match(Object, Class, ThrowableFactory)
	 * @since 2.0
	 */
	public static <T, R extends T> Function<T, R> match(final Class<R> type, final ThrowableFactory<? extends RuntimeException> mismatchFactory) {
		assert null != type;
		assert null != mismatchFactory;
		
		return object -> ObjectUtils.match(object, type, mismatchFactory);
	}
	
	private ObjectFunctions() {
		// Prevent instantiation.
	}
}
