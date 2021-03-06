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

import com.trazere.util.function.Function0;

/**
 * The {@link Factories} class provides factories of factories.
 * 
 * @see Factory
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class Factories {
	/**
	 * Builds a factory producing the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built factory.
	 * @deprecated Use {@link com.trazere.core.design.Factories#fromValue(Object)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Factory<T, X> fromValue(final T value) {
		return new BaseFactory<T, X>() {
			@Override
			public T build() {
				return value;
			}
		};
	}
	
	/**
	 * Build a factory producing values returned by given zero arguments function.
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built factory.
	 * @deprecated Use {@link com.trazere.core.design.Factories#fromThunk(com.trazere.core.functional.Thunk)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Factory<T, X> fromFunction(final Function0<? extends T, ? extends X> function) {
		assert null != function;
		
		return new BaseFactory<T, X>() {
			@Override
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
