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
package com.trazere.util.accumulator;

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;

/**
 * The {@link Accumulators} class provides various factories of accumulators.
 */
public class Accumulators {
	/**
	 * Builds an accumulator that ignores the accumulated values.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built accumulator.
	 */
	public static <T, S, X extends Exception> Accumulator1<T, S, X> constant1(final S result) {
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value) {
				// Nothing to do.
			}
			
			@Override
			public S get() {
				return result;
			}
		};
	}
	
	/**
	 * Builds an accumulator that maps the accumulated values using the given function.
	 * 
	 * @param <T1> Type of the values.
	 * @param <T2> Type of the result values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param accumulator The accumulator to map.
	 * @return The built accumulator.
	 */
	public static <T1, T2, S, X extends Exception> Accumulator1<T1, S, X> map(final Function1<? super T1, ? extends T2, ? extends RuntimeException> function, final Accumulator1<? super T2, ? extends S, ? extends X> accumulator) {
		assert null != function;
		assert null != accumulator;
		
		return new BaseAccumulator1<T1, S, X>() {
			@Override
			public void add(final T1 value)
			throws X {
				accumulator.add(function.evaluate(value));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that maps its state using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <S1> Type of the states.
	 * @param <S2> Type of the result states.
	 * @param <X> Type of the exceptions.
	 * @param function The mapping function.
	 * @param accumulator The accumulator to map.
	 * @return The built accumulator.
	 */
	public static <T, S1, S2, X extends Exception> Accumulator1<T, S2, X> mapState(final Function1<? super S1, ? extends S2, ? extends RuntimeException> function, final Accumulator1<? super T, ? extends S1, ? extends X> accumulator) {
		assert null != function;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, S2, X>() {
			@Override
			public void add(final T value)
			throws X {
				accumulator.add(value);
			}
			
			@Override
			public S2 get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	/**
	 * Builds an accumulator that filters the accumulated values using the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param accumulator The accumulator to map.
	 * @return The built accumulator.
	 */
	public static <T, S, X extends Exception> Accumulator1<T, S, X> filter(final Predicate1<? super T, ? extends RuntimeException> predicate, final Accumulator1<? super T, ? extends S, ? extends X> accumulator) {
		assert null != predicate;
		assert null != accumulator;
		
		return new BaseAccumulator1<T, S, X>() {
			@Override
			public void add(final T value)
			throws X {
				if (predicate.evaluate(value)) {
					accumulator.add(value);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that filters and transforms the accumulated values using the given extractor.
	 * 
	 * @param <T1> Type of the values.
	 * @param <T2> Type of the result values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param accumulator The accumulator to map.
	 * @return The built accumulator.
	 */
	public static <T1, T2, S, X extends Exception> Accumulator1<T1, S, X> mapFilter(final Function1<? super T1, ? extends Maybe<? extends T2>, ? extends RuntimeException> extractor, final Accumulator1<? super T2, ? extends S, ? extends X> accumulator) {
		assert null != extractor;
		assert null != accumulator;
		
		return new BaseAccumulator1<T1, S, X>() {
			@Override
			public void add(final T1 value)
			throws X {
				final Maybe<? extends T2> result = extractor.evaluate(value);
				if (result.isSome()) {
					accumulator.add(result.asSome().getValue());
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Builds a pair accumulator that ignores the accumulated values.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the states.
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built accumulator.
	 */
	public static <T1, T2, S, X extends Exception> Accumulator2<T1, T2, S, X> constant2(final S result) {
		return new BaseAccumulator2<T1, T2, S, X>() {
			@Override
			public void add(final T1 value1, final T2 value2) {
				// Nothing to do.
			}
			
			@Override
			public S get() {
				return result;
			}
		};
	}
	
	private Accumulators() {
		// Prevents instantiation.
	}
}
