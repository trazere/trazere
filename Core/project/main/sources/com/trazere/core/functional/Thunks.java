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
package com.trazere.core.functional;

import com.trazere.core.util.Maybe;

/**
 * The {@link Thunks} class provides various factories of {@link Thunk thunks}.
 * 
 * @see Thunk
 * @since 2.0
 */
public class Thunks {
	/**
	 * Builds a thunk evaluating to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value of the thunk.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> MemoizedThunk<T> constant(final T value) {
		return new MemoizedThunk<T>() {
			@Override
			public T evaluate() {
				return value;
			}
			
			@Override
			public boolean isMemoized() {
				return true;
			}
			
			@Override
			public Maybe<T> probe() {
				return Maybe.some(value);
			}
		};
	}
	
	/**
	 * Builds a thunk that throws the given exception.
	 *
	 * @param <T> Type of the values.
	 * @param exception Exception to throw.
	 * @return The built thunk.
	 * @since 2.0
	 */
	public static <T> Thunk<T> failure(final RuntimeException exception) {
		assert null != exception;
		
		return () -> {
			throw exception;
		};
	}
	
	private Thunks() {
		// Prevent instantiation.
	}
}
