/*
 *  Copyright 2006-2009 Julien Dufour
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
package com.trazere.util.accumulator;

import com.trazere.util.function.Function2;

/**
 * The {@link Accumulators} class provides various common accumulators.
 */
public class Accumulators {
	/**
	 * Build an accumulator using the given function and initial value.
	 * 
	 * @param <T> Type of the accumulated values.
	 * @param <V> Type of the accumulation arguments.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param initialValue The initial value.
	 * @return The built accumulator.
	 */
	public static <T, V, X extends Exception> Accumulator<T, V, X> function(final Function2<? super T, ? super V, ? extends T, ? extends X> function, final T initialValue) {
		assert null != function;
		
		return new AbstractAccumulator<T, V, X>(initialValue) {
			@Override
			protected T compute(final T accumulator, final V value)
			throws X {
				return function.evaluate(accumulator, value);
			}
		};
	}
	
	/**
	 * Build a logical accumulator corresponding to a conjonction.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initialValue The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator<Boolean, Boolean, X> and(final boolean initialValue) {
		return new AbstractAccumulator<Boolean, Boolean, X>(initialValue) {
			@Override
			protected Boolean compute(final Boolean accumulator, final Boolean value)
			throws X {
				return (null != accumulator && accumulator.booleanValue()) && (null != value && value.booleanValue());
			}
		};
	}
	
	/**
	 * Build a logical accumulator corresponding to a disjunction.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param initialValue The initial value.
	 * @return The built accumulator.
	 */
	public static <X extends Exception> Accumulator<Boolean, Boolean, X> or(final boolean initialValue) {
		return new AbstractAccumulator<Boolean, Boolean, X>(initialValue) {
			@Override
			protected Boolean compute(final Boolean accumulator, final Boolean value)
			throws X {
				return (null != accumulator && accumulator.booleanValue()) || (null != value && value.booleanValue());
			}
		};
	}
	
	private Accumulators() {
		// Prevent instantiation.
	}
}
