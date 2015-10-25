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
package com.trazere.core.reactive;

import com.trazere.core.imperative.Accumulator;

/**
 * The {@link ReactiveAccumulators} class provides various factories of {@link Accumulator accumulators} related to reactive features.
 * 
 * @see Accumulator
 * @see Observable
 * @since 2.0
 */
public class ReactiveAccumulators {
	/**
	 * Builds an accumulator that fulfils the given promise if it not already fulfilled.
	 * 
	 * @param <T> Type of the value.
	 * @param promise Promise to fulfil.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <T> Accumulator<T, Future<T>> fulfilIfNot(final Promise<T> promise) {
		assert null != promise;
		
		return new Accumulator<T, Future<T>>() {
			@Override
			public void add(final T value) {
				promise.fulfilIfNot(value);
			}
			
			@Override
			public Future<T> get() {
				return promise.getFuture();
			}
		};
	}
	
	private ReactiveAccumulators() {
		// Prevents instantiation.
	}
}
