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

import com.trazere.core.lang.MutableObject;

/**
 * The {@link FixedPoint} abstract class implements convergence functions to some fixed point.
 * <p>
 * The fixed point is computed by recursively evaluating some function that should converge toward the fixed point from some initial value until the distance
 * between to successive steps is accepted by some predicate.
 * 
 * @param <V> Type of the values.
 * @since 1.0
 */
public abstract class FixedPoint<V>
implements Function<V, V> {
	/**
	 * Builds a fixed point function using the given step convergence function and termination predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param step Convergence function to use.
	 * @param done Termination predicate to use, evaluated with the previous and current value.
	 * @return The built fixed point function.
	 * @since 1.0
	 */
	public static <V> FixedPoint<V> build(final Function<? super V, ? extends V> step, final Predicate2<? super V, ? super V> done) {
		assert null != step;
		assert null != done;
		
		return new FixedPoint<V>() {
			@Override
			protected V evaluateStep(final V value) {
				return step.evaluate(value);
			}
			
			@Override
			protected boolean isDone(final V previousValue, final V value) {
				return done.evaluate(previousValue, value);
			}
		};
	}
	
	/**
	 * Builds a fixed point value using the given step convergence function, termination predicate and initial value.
	 * 
	 * @param <V> Type of the values.
	 * @param step Convergence function to use.
	 * @param done Termination predicate to use, evaluated with the previous and current value.
	 * @param initialValue Initial value.
	 * @return The fixed point value.
	 * @since 1.0
	 */
	public static <V> V compute(final Function<? super V, ? extends V> step, final Predicate2<? super V, ? super V> done, final V initialValue) {
		return FixedPoint.<V>build(step, done).evaluate(initialValue);
	}
	
	@Override
	public V evaluate(final V initialValue) {
		final MutableObject<V> accumulator = new MutableObject<>(initialValue);
		while (true) {
			final V previousValue = accumulator.get();
			final V value = evaluateStep(previousValue);
			if (isDone(previousValue, value)) {
				return value;
			} else {
				accumulator.set(value);
			}
		}
	}
	
	/**
	 * Evaluates the convergence function with the given current value.
	 * 
	 * @param value Current value.
	 * @return The next value.
	 * @since 1.0
	 */
	protected abstract V evaluateStep(V value);
	
	/**
	 * Tests whether the fixed point value has been reached.
	 * 
	 * @param previousValue Previous value.
	 * @param value Current value.
	 * @return <code>true</code> when the fixed point has been reached, <code>false</code> to continue to converge.
	 * @since 1.0
	 */
	protected abstract boolean isDone(V previousValue, V value);
}
