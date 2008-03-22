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

import java.util.Map;

import com.trazere.util.Assert;

/**
 * The <code>Functions</code> class provides various standard functions.
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
	 * The domain of the built function is the set of keys of the map. The built function returns the values associated to the keys in the map, or
	 * <code>null</code>.
	 * 
	 * @param <T> Type of the keys of the map (the argument values).
	 * @param <R> Type of the values of the map (the result values).
	 * @param map Map defining the function to build.
	 * @return The function.
	 */
	public static <T, R> Function<T, R> map(final Map<T, R> map) {
		// Checks.
		Assert.notNull(map);
		
		// Build the function.
		return new Function<T, R>() {
			public R apply(final T key) {
				return map.get(key);
			}
		};
	}
	
	private Functions() {
		// Prevent instantiation.
	}
}
