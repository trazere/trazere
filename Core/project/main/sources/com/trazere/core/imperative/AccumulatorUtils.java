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
package com.trazere.core.imperative;

import com.trazere.core.util.Tuple2;

/**
 * The {@link AccumulatorUtils} class provides various utilities regarding {@link Accumulator accumulators}.
 * 
 * @see Accumulator
 * @see Accumulator2
 * @since 2.0
 */
public class AccumulatorUtils {
	/**
	 * Builds a view of the given accumulator that curries accumulated pairs of elements.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator to wrap.
	 * @return The built view.
	 * @since 2.0
	 */
	public static <E1, E2, S> Accumulator2<E1, E2, S> currying2(final Accumulator<? super Tuple2<E1, E2>, ? extends S> accumulator) {
		assert null != accumulator;
		
		return new Accumulator2<E1, E2, S>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				accumulator.add(new Tuple2<>(element1, element2));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds a delegated view of the given accumulator.
	 * <p>
	 * The view forwards the accumulated elements and results to the given accumulator.
	 * 
	 * @param <E> Type of the elements.
	 * @param <A> Type of the delegate accumulator.
	 * @param accumulator Delegate accumulator.
	 * @return The built view.
	 * @since 2.0
	 */
	public static <E, A extends Accumulator<? super E, ?>> Accumulator<E, A> delegated(final A accumulator) {
		assert null != accumulator;
		
		return new Accumulator<E, A>() {
			@Override
			public void add(final E element) {
				accumulator.add(element);
			}
			
			@Override
			public void addAll(final Iterable<? extends E> elements) {
				accumulator.addAll(elements);
			}
			
			@Override
			public A get() {
				return accumulator;
			}
		};
	}
	
	/**
	 * Builds a delegated view of the given accumulator.
	 * <p>
	 * The view forwards the accumulated pairs of elements and results to the given accumulator.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <A> Type of the delegate accumulator.
	 * @param accumulator Delegate accumulator.
	 * @return The built view.
	 * @since 2.0
	 */
	public static <E1, E2, A extends Accumulator2<? super E1, ? super E2, ?>> Accumulator2<E1, E2, A> delegated2(final A accumulator) {
		assert null != accumulator;
		
		return new Accumulator2<E1, E2, A>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				accumulator.add(element1, element2);
			}
			
			@Override
			public void addAll(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> elements) {
				accumulator.addAll(elements);
			}
			
			@Override
			public A get() {
				return accumulator;
			}
		};
	}
	
	private AccumulatorUtils() {
		// Prevents instantiation.
	}
}
