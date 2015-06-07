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

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Predicate2;
import com.trazere.core.functional.PredicateUtils;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The {@link AccumulatorUtils} class provides various utilities regarding {@link Accumulator accumulators}.
 * 
 * @see Accumulator
 * @see Accumulator2
 * @since 1.0
 */
public class AccumulatorUtils {
	/**
	 * Builds a accumulators that forwards the accumulated elements and results to the given accumulator.
	 * 
	 * @param <E> Type of the elements.
	 * @param <A> Type of the delegate accumulator.
	 * @param accumulator Delegate accumulator.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, A extends Accumulator<? super E, ?>> Accumulator<E, A> delegate(final A accumulator) {
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
	 * Filters the elements accumulated into the given accumulator using the given filter.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the filtered elements.
	 * @param filter Predicate to use to filter the elements.
	 * @return The built accumulator.
	 * @since 1.0
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
	 * Normalizes the elements accumulated into the given accumulator.
	 * 
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the normalized elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, S> Accumulator<E, S> normalize(final Accumulator<? super E, ? extends S> accumulator) {
		return AccumulatorUtils.filter(accumulator, ImperativePredicates.normalizer());
	}
	
	/**
	 * Normalizes the elements accumulated into the given accumulator according to the given hash function.
	 * <p>
	 * At most one element will be accumulated for each hash value.
	 * 
	 * @param <E> Type of the accumulated elements.
	 * @param <H> Type of the hash values.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the normalized elements.
	 * @param hashFunction Function that hashes the elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, H, S> Accumulator<E, S> normalize(final Accumulator<? super E, ? extends S> accumulator, final Function<? super E, H> hashFunction) {
		return AccumulatorUtils.filter(accumulator, PredicateUtils.map(ImperativePredicates.normalizer(), hashFunction));
	}
	
	// TODO: rename to ???
	/**
	 * Transforms the elements accumulated into the given accumulator using the given function.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <TE> Type of the transformed accumulated elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the transformed elements.
	 * @param function Function to use to transform the elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, TE, S> Accumulator<E, S> map(final Accumulator<? super TE, ? extends S> accumulator, final Function<? super E, ? extends TE> function) {
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
	 * Extracts the elements accumulated into the given accumulator using the given extractor.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <EE> Type of the accumulated extracted elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, EE, S> Accumulator<E, S> extract(final Accumulator<? super EE, ? extends S> accumulator, final Function<? super E, ? extends Maybe<? extends EE>> extractor) {
		assert null != extractor;
		assert null != accumulator;
		
		return new Accumulator<E, S>() {
			@Override
			public void add(final E element) {
				final Maybe<? extends EE> extractedElement = extractor.evaluate(element);
				if (extractedElement.isSome()) {
					accumulator.add(extractedElement.asSome().getValue());
				}
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
	 * @param <EE> Type of the accumulated extracted elements.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the extracted elements.
	 * @param extractor Function to use to extract the elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, EE, S> Accumulator<E, S> extractAll(final Accumulator<? super EE, ? extends S> accumulator, final Function<? super E, ? extends Iterable<? extends EE>> extractor) {
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
	
	// TODO: rename to map
	/**
	 * Transforms the state of the given accumulator using the given function.
	 *
	 * @param <E> Type of the accumulated elements.
	 * @param <S> Type of the state.
	 * @param <TS> Type of the transformed states.
	 * @param accumulator Accumulator to transform.
	 * @param function Function to use to transform the state.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E, S, TS> Accumulator<E, TS> mapState(final Accumulator<? super E, ? extends S> accumulator, final Function<? super S, ? extends TS> function) {
		assert null != accumulator;
		assert null != function;
		
		return new Accumulator<E, TS>() {
			@Override
			public void add(final E element) {
				accumulator.add(element);
			}
			
			@Override
			public TS get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	/**
	 * Builds a consumer that lifts the given accumulator.
	 * 
	 * @param <E> Type of the accumulated elements.
	 * @param accumulator Accumulator to lift.
	 * @return The built consumer.
	 * @since 1.0
	 */
	public static <E> Consumer<E> toConsumer(final Accumulator<? super E, ?> accumulator) {
		assert null != accumulator;
		
		return t -> accumulator.add(t);
	}
	
	/**
	 * Builds a accumulators that forwards the accumulated elements and results to the given accumulator.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <A> Type of the delegate accumulator.
	 * @param accumulator Delegate accumulator.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, A extends Accumulator2<? super E1, ? super E2, ?>> Accumulator2<E1, E2, A> delegate2(final A accumulator) {
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
	
	/**
	 * Filters the paris of elements accumulated into the given accumulator using the given filter.
	 *
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the filtered paris of elements.
	 * @param filter Predicate to use to filter the pairs of elements.
	 * @return The built accumulator.
	 * @since 1.0
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
	 * @param <TE1> Type of the first elements of transformed accumulated pairs.
	 * @param <TE2> Type of the second elements of transformed accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the transformed pairs of elements.
	 * @param function Function to use to transform the pairs of elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, TE1, TE2, S> Accumulator2<E1, E2, S> map2(final Accumulator2<? super TE1, ? super TE2, ? extends S> accumulator, final Function2<? super E1, ? super E2, ? extends Tuple2<? extends TE1, ? extends TE2>> function) {
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
	 * @param <EE1> Type of the first elements of transformed accumulated pairs.
	 * @param <EE2> Type of the second elements of transformed accumulated pairs.
	 * @param <S> Type of the state.
	 * @param accumulator Accumulator of the extracted pairs of elements.
	 * @param extractor Function to use to extract the pairs of elements.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, EE1, EE2, S> Accumulator2<E1, E2, S> extract2(final Accumulator2<? super EE1, ? super EE2, ? extends S> accumulator, final Function2<? super E1, ? super E2, ? extends Iterable<? extends Tuple2<? extends EE1, ? extends EE2>>> extractor) {
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
	 * @param <TS> Type of the transformed state.
	 * @param accumulator Accumulator to transform.
	 * @param function Function to use to transform the state.
	 * @return The built accumulator.
	 * @since 1.0
	 */
	public static <E1, E2, S, TS> Accumulator2<E1, E2, TS> mapState2(final Accumulator2<? super E1, ? super E2, ? extends S> accumulator, final Function<? super S, ? extends TS> function) {
		assert null != accumulator;
		assert null != function;
		
		return new Accumulator2<E1, E2, TS>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				accumulator.add(element1, element2);
			}
			
			@Override
			public TS get() {
				return function.evaluate(accumulator.get());
			}
		};
	}
	
	/**
	 * Builds a bi-consumer that lifts the given two argument accumulator.
	 * 
	 * @param <E1> Type of the first element of the accumulated pairs.
	 * @param <E2> Type of the second element of the accumulated pairs.
	 * @param accumulator Accumulator to lift.
	 * @return The built bi-consumer.
	 * @since 1.0
	 */
	public static <E1, E2> BiConsumer<E1, E2> toBiConsumer(final Accumulator2<? super E1, ? super E2, ?> accumulator) {
		assert null != accumulator;
		
		return (t, u) -> accumulator.add(t, u);
	}
	
	private AccumulatorUtils() {
		// Prevents instantiation.
	}
}
