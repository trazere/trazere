/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.function;

import com.trazere.util.type.Maybe;
import java.util.Map;

/**
 * The {@link Functions} class provides various standard functions.
 * 
 * @see Function
 * @see Function2
 */
public class Functions {
	/**
	 * Build a function always returning the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result value.
	 * @param result Result value.
	 * @return The function.
	 */
	public static <T, R> Function<T, R> constant(final R result) {
		return new Function<T, R>() {
			public R apply(final T value) {
				return result;
			}
		};
	}
	
	/**
	 * Build a function defined by the given map.
	 * <p>
	 * The built function returns the values associated to the keys in the map, or <code>null</code> for the unmapped keys.
	 * 
	 * @param <T> Type of the keys of the map (the argument values).
	 * @param <R> Type of the values of the map (the result values).
	 * @param map Map defining the function to build.
	 * @return The function.
	 */
	public static <T, R> Function<T, R> map(final Map<T, R> map) {
		assert null != map;
		
		// Build the function.
		return new Function<T, R>() {
			public R apply(final T key) {
				return map.get(key);
			}
		};
	}
	
	/**
	 * Build a function defined by the given map.
	 * <p>
	 * The built function returns the values associated to the keys in the map. Value resulting values are wrapped into a {@link Maybe maybe} instance to
	 * reflect the domain of the map.
	 * 
	 * @param <T> Type of the keys of the map (the argument values).
	 * @param <R> Type of the values of the map (the result values).
	 * @param map Map defining the function to build.
	 * @return The function.
	 */
	public static <T, R> Function<T, Maybe<R>> maybeMap(final Map<T, R> map) {
		assert null != map;
		
		// Build the function.
		return new Function<T, Maybe<R>>() {
			public Maybe<R> apply(final T key) {
				if (map.containsKey(key)) {
					return Maybe.some(map.get(key));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	private Functions() {
		// Prevent instantiation.
	}
}
