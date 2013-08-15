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
	
	private FunctionAccumulators() {
		// Prevents instantiation.
	}
}
