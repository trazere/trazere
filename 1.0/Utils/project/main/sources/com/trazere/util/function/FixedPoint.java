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
package com.trazere.util.function;

import com.trazere.util.lang.MutableObject;

/**
 * The {@link FixedPoint} abstract class implements convergence functions to some fixed point.
 * 
 * @param <T> Type of the values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.functional.FixedPoint}.
 */
@Deprecated
public abstract class FixedPoint<T, X extends Exception>
implements Function1<T, T, X> {
	/**
	 * Builds a fixed point function using the given step convergence function and termination predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param step The convergence function.
	 * @param done The termination predicate.
	 * @return The fixed point function.
	 * @deprecated Use {@link com.trazere.core.functional.FixedPoint#build(com.trazere.core.functional.Function, com.trazere.core.functional.Predicate2)}.
	 */
	@Deprecated
	public static <T, X extends Exception> FixedPoint<T, X> build(final Function1<? super T, ? extends T, ? extends X> step, final Predicate2<? super T, ? super T, ? extends X> done) {
		assert null != step;
		assert null != done;
		
		return new FixedPoint<T, X>() {
			@Override
			protected T evaluateStep(final T value)
			throws X {
				return step.evaluate(value);
			}
			
			@Override
			protected boolean isDone(final T precValue, final T value)
			throws X {
				return done.evaluate(precValue, value);
			}
		};
	}
	
	@Override
	public T evaluate(final T initial)
	throws X {
		final MutableObject<T> accumulator = new MutableObject<T>(initial);
		while (true) {
			final T previousValue = accumulator.get();
			final T value = evaluateStep(previousValue);
			if (isDone(previousValue, value)) {
				return value;
			} else {
				accumulator.set(value);
			}
		}
	}
	
	/**
	 * Evaluates a convergence step from the given value.
	 * 
	 * @param value The value.
	 * @return The next value.
	 * @throws X When the evaluation fails.
	 */
	protected abstract T evaluateStep(final T value)
	throws X;
	
	/**
	 * Tests whether the fixed point value has been reached.
	 * 
	 * @param previousValue The previous value.
	 * @param value The current value.
	 * @return <code>true</code> when the fixed point has been reached, <code>false</code> to continue to converge.
	 * @throws X When the computation fails.
	 */
	protected abstract boolean isDone(final T previousValue, final T value)
	throws X;
}
