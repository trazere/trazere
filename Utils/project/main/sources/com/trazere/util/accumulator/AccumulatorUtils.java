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
package com.trazere.util.accumulator;

import com.trazere.core.imperative.Accumulator;
import com.trazere.core.util.Tuple2;
import com.trazere.util.lang.WrapException;

/**
 * The {@link AccumulatorUtils} class provides various utilities regarding accumulators.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class AccumulatorUtils {
	/**
	 * Adapts the given util accumulator to a core accumulator.
	 * 
	 * @param <T> Type of the values.
	 * @param <S> Type of the states.
	 * @param accumulator Util accumulator to adapt.
	 * @return The adapted core accumulator.
	 * @deprecated Use {@link Accumulator}.
	 */
	@Deprecated
	public static <T, S> Accumulator<T, S> toAccumulator(final Accumulator1<T, ? extends S, ?> accumulator) {
		assert null != accumulator;
		
		return new Accumulator<T, S>() {
			@Override
			public void add(final T element) {
				try {
					accumulator.add(element);
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			// Note: project is still 1.6
			@Override
			public void addAll(final Iterable<? extends T> elements) {
				for (final T element : elements) {
					add(element);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
			
			// Note: project is still 1.6
			@Override
			public void execute(final T element) {
				add(element);
			}
		};
	}
	
	/**
	 * Adapts the given core accumulator to an util accumulator.
	 * 
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param accumulator Core accumulator to adapt.
	 * @return The adapted util accumulator.
	 * @deprecated Use {@link Accumulator}.
	 */
	@Deprecated
	public static <E, S> Accumulator1<E, S, RuntimeException> fromAccumulator(final Accumulator<? super E, ? extends S> accumulator) {
		assert null != accumulator;
		
		return new BaseAccumulator1<E, S, RuntimeException>() {
			@Override
			public void add(final E value) {
				accumulator.add(value);
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Adapts the given util accumulator to a core accumulator.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <S> Type of the result.
	 * @param accumulator Util accumulator to adapt.
	 * @return The adapted core accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulator2}.
	 */
	@Deprecated
	public static <T1, T2, S> com.trazere.core.imperative.Accumulator2<T1, T2, S> toAccumulator2(final Accumulator2<? super T1, ? super T2, ? extends S, ?> accumulator) {
		assert null != accumulator;
		
		return new com.trazere.core.imperative.Accumulator2<T1, T2, S>() {
			@Override
			public void add(final T1 element1, final T2 element2) {
				try {
					accumulator.add(element1, element2);
				} catch (final Exception exception) {
					throw new WrapException(exception);
				}
			}
			
			// Note: project is still 1.6
			@Override
			public void add(final Tuple2<? extends T1, ? extends T2> elementPair) {
				add(elementPair.get1(), elementPair.get2());
			}
			
			// Note: project is still 1.6
			@Override
			public void addAll(final Iterable<? extends Tuple2<? extends T1, ? extends T2>> elements) {
				for (final Tuple2<? extends T1, ? extends T2> element : elements) {
					add(element);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
			
			// Note: project is still 1.6
			@Override
			public void execute(final T1 element1, final T2 element2) {
				add(element1, element2);
			}
		};
	}
	
	/**
	 * Adapts the given core accumulator to an util accumulator.
	 * 
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Core accumulator to adapt.
	 * @return The adapted util accumulator.
	 * @deprecated Use {@link com.trazere.core.imperative.Accumulator2}.
	 */
	@Deprecated
	public static <E1, E2, S> Accumulator2<E1, E2, S, RuntimeException> fromAccumulator2(final com.trazere.core.imperative.Accumulator2<? super E1, ? super E2, ? extends S> accumulator) {
		assert null != accumulator;
		
		return new BaseAccumulator2<E1, E2, S, RuntimeException>() {
			@Override
			public void add(final E1 value1, final E2 value2) {
				accumulator.add(value1, value2);
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	private AccumulatorUtils() {
		// Prevent instantiation.
	}
}
