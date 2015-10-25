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
package com.trazere.core.reference;

import com.trazere.core.imperative.Accumulator;

/**
 * The {@link ReferenceAccumulators} class provides various factories of {@link Accumulator accumulators} related to {@link Reference references}.
 * 
 * @see Accumulator
 * @see Reference
 * @since 2.0
 */
public class ReferenceAccumulators {
	/**
	 * Builds an accumulator that updates the given mutable reference.
	 * 
	 * @param <T> Type of the referenced values.
	 * @param <R> Type of the reference.
	 * @param reference Reference to update.
	 * @return The built accumulator.
	 * @see MutableReference#update(com.trazere.core.util.Maybe)
	 * @since 2.0
	 */
	public static <T, R extends MutableReference<T>> Accumulator<T, R> update(final R reference) {
		assert null != reference;
		
		return new Accumulator<T, R>() {
			@Override
			public void add(final T value) {
				reference.update(value);
			}
			
			@Override
			public R get() {
				return reference;
			}
		};
	}
	
	private ReferenceAccumulators() {
		// Prevents instantiation.
	}
}
