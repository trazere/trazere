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
package com.trazere.core.imperative;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.util.Tuple2;

/**
 * The {@link AccumulatorUtils} class provides various helpers regarding accumulators.
 * 
 * @see Accumulator
 * @see Accumulator2
 */
public class AccumulatorUtils {
	/**
	 * Filters the elements accumulated into the given accumulator using the given filter.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the filtered elements.
	 * @param filter Predicate to use to filter the elements.
	 * @return The built accumulator.
	 */
	public static <E, S> Accumulator<E, S> filter(final Accumulator<? super E, ? extends S> accumulator, final Predicate<? super E> filter) {
		assert null != accumulator;
		assert null != filter;
		
		return new Accumulator<E, S>() {
			@Override
			public void add(final E element) {
				if (filter.evaluate(element)) {
					accumulator.add(element);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Transforms the elements accumulated into the given accumulator using the given function.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <RE> Type of the transformed accumulated elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return The built accumulator.
	 */
	public static <E, RE, S> Accumulator<E, S> map(final Accumulator<? super RE, ? extends S> accumulator, final Function<? super E, ? extends RE> function) {
		assert null != accumulator;
		assert null != function;
		
		return new Accumulator<E, S>() {
			@Override
			public void add(final E element) {
				accumulator.add(function.evaluate(element));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Extracts and flattens the elements accumulated into the given accumulator using the given extractor.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <RE> Type of the accumulated extracted elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The built accumulator.
	 */
	public static <E, RE, S> Accumulator<E, S> extract(final Accumulator<? super RE, ? extends S> accumulator, final Function<? super E, ? extends Iterable<? extends RE>> extractor) {
		assert null != extractor;
		assert null != accumulator;
		
		return new Accumulator<E, S>() {
			@Override
			public void add(final E element) {
				accumulator.addAll(extractor.evaluate(element));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Transforms the state of the given accumulator using the given function.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param <RS> Type of the transformed states.
	 * @param accumulator Accumulator to transform.
	 * @param function Function to use to transform the state.
	 * @return The built accumulator.
	 */
	public static <E, S, RS> Accumulator<E, RS> mapState(final Accumulator<? super E, ? extends S> accumulator, final Function<? super S, ? extends RS> function) {
		assert null != accumulator;
		assert null != function;
		
		return new Accumulator<E, RS>() {
			@Override
			public void add(final E value) {
				accumulator.add(value);
			}
			
			@Override
			public RS get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	/**
	 * Filters the paris of elements accumulated into the given accumulator using the given filter.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the filtered paris of elements.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The built accumulator.
	 */
	public static <E1, E2, S> Accumulator2<E1, E2, S> filter2(final Accumulator2<? super E1, ? super E2, ? extends S> accumulator, final Predicate2<? super E1, ? super E2> filter) {
		assert null != accumulator;
		assert null != filter;
		
		return new Accumulator2<E1, E2, S>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				if (filter.evaluate(element1, element2)) {
					accumulator.add(element1, element2);
				}
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Transforms the pairs of elements accumulated into the given accumulator using the given function.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <RE1> Type of the first elements of transformed accumulated pairs.
	 * @param <RE2> Type of the second elements of transformed accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the transformed pairs of elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return The built accumulator.
	 */
	public static <E1, E2, RE1, RE2, S> Accumulator2<E1, E2, S> map2(final Accumulator2<? super RE1, ? super RE2, ? extends S> accumulator, final Function2<? super E1, ? super E2, ? extends Tuple2<? extends RE1, ? extends RE2>> function) {
		assert null != accumulator;
		assert null != function;
		
		return new Accumulator2<E1, E2, S>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				accumulator.add(function.evaluate(element1, element2));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Extracts and flattens the pairs of elements accumulated into the given accumulator using the given extractor.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <RE1> Type of the first elements of transformed accumulated pairs.
	 * @param <RE2> Type of the second elements of transformed accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the extracted pairs of elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return The built accumulator.
	 */
	public static <E1, E2, RE1, RE2, S> Accumulator2<E1, E2, S> extract2(final Accumulator2<? super RE1, ? super RE2, ? extends S> accumulator, final Function2<? super E1, ? super E2, ? extends Iterable<? extends Tuple2<? extends RE1, ? extends RE2>>> extractor) {
		assert null != extractor;
		assert null != accumulator;
		
		return new Accumulator2<E1, E2, S>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				accumulator.addAll(extractor.evaluate(element1, element2));
			}
			
			@Override
			public S get() {
				return accumulator.get();
			}
		};
	}
	
	/**
	 * Transforms the state of the given accumulator using the given function.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param <RS> Type of the transformed state.
	 * @param accumulator Accumulator to transform.
	 * @param function Function to use to transform the state.
	 * @return The built accumulator.
	 */
	public static <E1, E2, S, RS> Accumulator2<E1, E2, RS> mapState2(final Accumulator2<? super E1, ? super E2, ? extends S> accumulator, final Function<? super S, ? extends RS> function) {
		assert null != accumulator;
		assert null != function;
		
		return new Accumulator2<E1, E2, RS>() {
			@Override
			public void add(final E1 value1, final E2 value2) {
				accumulator.add(value1, value2);
			}
			
			@Override
			public RS get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	private AccumulatorUtils() {
		// Prevents instantiation.
	}
}
