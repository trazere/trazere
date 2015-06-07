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

import com.trazere.core.reference.MutableReference;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Unit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The {@link Accumulators} class provides various factories of {@link Accumulator accumulators}.
 * 
 * @see Accumulator
 * @see Accumulator2
 * @since 1.0
 */
public class Accumulators {
	/**
	 * Builds an accumulator that ignores the accumulated elements and keeps a constant state.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param state State of the accumulator.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, S> Accumulator<E, S> constant(final S state) {
		return new Accumulator<E, S>() {
			@Override
			public void add(final E element) {
				// Nothing to do.
			}
			
			@Override
			public S get() {
				return state;
			}
		};
	}
	
	/**
	 * Builds an accumulator that retains the first accumulated element.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E> Accumulator<E, Maybe<E>> first() {
		return new Accumulator<E, Maybe<E>>() {
			private final MutableReference<E> _element = new MutableReference<>();
			
			@Override
			public void add(final E element) {
				if (!_element.isSet()) {
					_element.set(element);
				}
			}
			
			@Override
			public Maybe<E> get() {
				return _element.asMaybe();
			}
		};
	}
	
	/**
	 * Builds an accumulator that counts the accumulated elements (number of times some element is accumulated).
	 *
	 * @param <E> Type of the accumulated elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E> Accumulator<E, Integer> counter() {
		return new Accumulator<E, Integer>() {
			private final IntCounter _result = new IntCounter();
			
			@Override
			public void add(final E element) {
				_result.inc();
			}
			
			@Override
			public Integer get() {
				return _result.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator that lifts the given consumer.
	 * 
	 * @param <E> Type of the accumulated elements.
	 * @param consumer Consumer to lift.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E> Accumulator<E, Unit> fromConsumer(final Consumer<? super E> consumer) {
		assert null != consumer;
		
		return new Accumulator<E, Unit>() {
			@Override
			public void add(final E element) {
				consumer.accept(element);
			}
			
			@Override
			public Unit get() {
				return Unit.UNIT;
			}
		};
	}
	
	/**
	 * Builds an accumulator that ignores the accumulated pairs of elements and keeps a constant state.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param state State of the accumulator.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, S> Accumulator2<E1, E2, S> constant2(final S state) {
		return new Accumulator2<E1, E2, S>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				// Nothing to do.
			}
			
			@Override
			public S get() {
				return state;
			}
		};
	}
	
	/**
	 * Builds an accumulator that retains the first accumulated pair of elements.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2> Accumulator2<E1, E2, Maybe<Tuple2<E1, E2>>> first2() {
		return new Accumulator2<E1, E2, Maybe<Tuple2<E1, E2>>>() {
			private final MutableReference<Tuple2<E1, E2>> _value = new MutableReference<>();
			
			@Override
			public void add(final E1 element1, final E2 element2) {
				if (!_value.isSet()) {
					_value.set(new Tuple2<>(element1, element2));
				}
			}
			
			@Override
			public Maybe<Tuple2<E1, E2>> get() {
				return _value.asMaybe();
			}
		};
	}
	
	/**
	 * Builds an accumulator that counts the accumulated pairs of elements (number of times some pair of elements is accumulated).
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2> Accumulator2<E1, E2, Integer> counter2() {
		return new Accumulator2<E1, E2, Integer>() {
			private final IntCounter _result = new IntCounter();
			
			@Override
			public void add(final E1 element1, final E2 element2) {
				_result.inc();
			}
			
			@Override
			public Integer get() {
				return _result.get();
			}
		};
	}
	
	/**
	 * Builds an accumulator of pairs of elements that lifts the given bi-consumer.
	 * 
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param consumer Bi-consumer to lift.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2> Accumulator2<E1, E2, Unit> fromBiConsumer(final BiConsumer<? super E1, ? super E2> consumer) {
		assert null != consumer;
		
		return new Accumulator2<E1, E2, Unit>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				consumer.accept(element1, element2);
			}
			
			@Override
			public Unit get() {
				return Unit.UNIT;
			}
		};
	}
	
	/**
	 * Curries the given accumulator of pair of elements.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator to curry.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, S> Accumulator2<E1, E2, S> curry(final Accumulator<? super Tuple2<E1, E2>, ? extends S> accumulator) {
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
	 * Uncurries the given accumulator of pair of elements.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator to uncurry.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, S> Accumulator<Tuple2<E1, E2>, S> uncurry(final Accumulator2<? super E1, ? super E2, ? extends S> accumulator) {
		assert null != accumulator;
		
		return new Accumulator<Tuple2<E1, E2>, S>() {
			@Override
			public void add(final Tuple2<E1, E2> element) {
				accumulator.add(element.get1(), element.get2());
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	private Accumulators() {
		// Prevents instantiation.
	}
}
