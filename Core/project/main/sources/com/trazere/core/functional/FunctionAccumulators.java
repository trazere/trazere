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

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.imperative.Accumulator2;
import com.trazere.core.imperative.FoldAccumulator2;

/**
 * The {@link FunctionAccumulators} class provides various factories of {@link Accumulator accumulators} related to {@link Function functions}.
 * 
 * @see Accumulator
 * @see Function
 * @since 2.0
 */
public class FunctionAccumulators {
	// TODO
	//	/**
	//	 * Builds an accumulator that populates the given map by projecting the values.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <S> Type of the states.
	//	 * @param <X> Type of the exceptions.
	//	 * @param projector The projection function.
	//	 * @param results The accumulator to populate.
	//	 * @return The built accumulator.
	//	 * @since 2.0
	//	 */
	//	public static <K, V, S, X extends Exception> Accumulator1<V, S, X> projectValues(final Function1<? super V, ? extends K, ? extends X> projector, final Accumulator2<? super K, ? super V, S, ? extends X> results) {
	//		assert null != projector;
	//		assert null != results;
	//
	//		return new BaseAccumulator1<V, S, X>() {
	//			@Override
	//			public void add(final V value)
	//			throws X {
	//				results.add(projector.evaluate(value), value);
	//			}
	//
	//			@Override
	//			public S get() {
	//				return results.get();
	//			}
	//		};
	//	}
	
	/**
	 * Builds an accumulator that left folds over the accumulated pairs of elements using the given binary operator and initial state.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param operator Binary operator.
	 * @param initialState Initial state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	public static <E1, E2, S> Accumulator2<E1, E2, S> fold2(final Function3<? super S, ? super E1, ? super E2, ? extends S> operator, final S initialState) {
		assert null != operator;
		
		return new FoldAccumulator2<E1, E2, S>(initialState) {
			@Override
			protected S fold(final S state, final E1 element1, final E2 element2) {
				return operator.evaluate(state, element1, element2);
			}
		};
	}
	
	private FunctionAccumulators() {
		// Prevents instantiation.
	}
}
