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
package com.trazere.core.imperative;

import com.trazere.core.functional.Thunk;

/**
 * The {@link ImperativeThunks} class provides various factories of {@link Thunk thunks} with imperative features.
 * 
 * @see Thunk
 */
public class ImperativeThunks {
	/**
	 * Builds a thunk that lifts the given effect.
	 *
	 * @param effect Effect to lift.
	 * @return The built thunk.
	 */
	public static Thunk<Void> fromEffect(final Effect effect) {
		return fromEffect(effect, (Void) null);
	}
	
	/**
	 * Builds a thunk that lifts the given effect and evaluates to the given value.
	 * 
	 * @param <T> Type of the value.
	 * @param effect Effect to lift.
	 * @param value Value of the thunk.
	 * @return The built thunk.
	 */
	public static <T> Thunk<T> fromEffect(final Effect effect, final T value) {
		assert null != effect;
		
		return () -> {
			effect.execute();
			return value;
		};
	}
	
	private ImperativeThunks() {
		// Prevent instantiation.
	}
}
