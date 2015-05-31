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
package com.trazere.util.reference;

import com.trazere.util.accumulator.Accumulator1;
import com.trazere.util.accumulator.BaseAccumulator1;

/**
 * The {@link ReferenceAccumulators} class provides various factories of accumulators related to references.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class ReferenceAccumulators {
	/**
	 * Builds an accumulator that updates the given reference.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the reference.
	 * @param <X> Type of the exceptions.
	 * @param reference The reference to set.
	 * @return The built accumulator.
	 * @deprecated Use {@link com.trazere.core.reference.ReferenceAccumulators#update(com.trazere.core.reference.MutableReference)}.
	 */
	@Deprecated
	public static <T, R extends MutableReference<T>, X extends Exception> Accumulator1<T, R, X> update(final R reference) {
		assert null != reference;
		
		return new BaseAccumulator1<T, R, X>() {
			@Override
			public void add(final T value)
			throws X {
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
