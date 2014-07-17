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
package com.trazere.core.design;

import com.trazere.core.functional.Thunk;

/**
 * The {@link Factories} class provides various factories of {@link Factory factories}.
 * 
 * @see Factory
 */
public class Factories {
	/**
	 * Builds a factory that builds the given value.
	 * 
	 * @param <T> Type of the built values.
	 * @param value Value to build.
	 * @return The built factory.
	 */
	// TODO: constant ?
	public static <T> Factory<T> fromValue(final T value) {
		return () -> value;
	}
	
	/**
	 * Build a factory that lifts the given thunks.
	 * 
	 * @param <T> Type of the built values.
	 * @param thunk Thunk to lift.
	 * @return The built factory.
	 */
	public static <T> Factory<T> fromThunk(final Thunk<? extends T> thunk) {
		assert null != thunk;
		
		return () -> thunk.evaluate();
	}
	
	private Factories() {
		// Prevents instantiation.
	}
}
