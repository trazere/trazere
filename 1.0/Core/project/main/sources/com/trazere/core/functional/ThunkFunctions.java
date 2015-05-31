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

/**
 * The {@link ThunkFunctions} class provides various factories of {@link Function functions} related to {@link Thunk thunks}.
 * 
 * @see Function
 * @see Thunk
 * @since 1.0
 */
public class ThunkFunctions {
	/**
	 * Builds a function that evaluates the argument thunks.
	 *
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Thunk<? extends T>, T> evaluate() {
		return (Function<Thunk<? extends T>, T>) EVALUATE;
	}
	
	private static final Function<? extends Thunk<?>, ?> EVALUATE = thunk -> thunk.evaluate();
	
	private ThunkFunctions() {
		// Prevent instantiation.
	}
}
