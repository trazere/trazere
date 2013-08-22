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

import com.trazere.util.accumulator.Accumulator1;
import com.trazere.util.accumulator.Accumulator2;
import com.trazere.util.accumulator.BaseAccumulator1;
import com.trazere.util.accumulator.FoldAccumulator1;

/**
 * The {@link FunctionAccumulators} class provides various factories of accumulators related to functions.
 */
public class FunctionAccumulators {
	/**
	 * Builds an accumulator that left folds the accumulated values using the given binary operator and initial result.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param operator The operator.
	 * @param initialResult The initial result.
	 * @return The built accumulator.
	 */
	public static <R, V, X extends Exception> Accumulator1<V, R, X> fold(final Function2<? super R, ? super V, ? extends R, ? extends X> operator, final R initialResult) {
		assert null != operator;
		
		return new FoldAccumulator1<R, V, X>(initialResult) {
			@Override
			protected R fold(final R result, final V value)
			throws X {
				return operator.evaluate(result, value);
			}
		};
	}
	
	/**
	 * Builds an accumulator that populates the given map by projecting the values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param projector The projection function.
	 * @param results The accumulator to populate.
	 * @return The built accumulator.
	 */
	public static <K, V, S, X extends Exception> Accumulator1<V, S, X> projectValues(final Function1<? super V, ? extends K, ? extends X> projector, final Accumulator2<? super K, ? super V, S, ? extends X> results) {
		assert null != projector;
		assert null != results;
		
		return new BaseAccumulator1<V, S, X>() {
			@Override
			public void add(final V value)
			throws X {
				results.add(projector.evaluate(value), value);
			}
			
			@Override
			public S get() {
				return results.get();
			}
		};
	}
	
	private FunctionAccumulators() {
		// Prevents instantiation.
	}
}
