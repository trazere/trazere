/*
 *  Copyright 2006-2011 Julien Dufour
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

import com.trazere.util.function.Function0;

/**
 * The {@link Factories} class provides various factories.
 * 
 * @see Factory
 */
public class Factories {
	/**
	 * Build a factory producing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built factory.
	 */
	public static <T, X extends Exception> Factory<T, X> value(final T value) {
		return new BaseFactory<T, X>() {
			public T build() {
				return value;
			}
		};
	}
	
	/**
	 * Build a factory producing values return by given zero arguments function.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built factory.
	 */
	public static <T, X extends Exception> Factory<T, X> function(final Function0<T, X> function) {
		assert null != function;
		
		return new BaseFactory<T, X>() {
			public T build()
			throws X {
				return function.evaluate();
			}
		};
	}
	
	private Factories() {
		// Prevents instantiation.
	}
}
