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
import com.trazere.core.util.Tuple2;
import java.util.function.BiConsumer;

/**
 * The {@link Accumulator2} interface defines automata that incrementally refine an internal state over a sequence of pairs of elements.
 * <p>
 * Accumulators are dual to iterators.
 * 
 * @param <E1> Type of the first element of the accumulated pairs.
 * @param <E2> Type of the second element of the accumulated pairs.
 * @param <S> Type of the state.
 * @since 2.0
 */
public interface Accumulator2<E1, E2, S>
extends Procedure2<E1, E2> {
	/**
	 * Accumulates the given pair of elements into this accumulator.
	 * 
	 * @param element1 First element of the pair to accumulate.
	 * @param element2 Second element of the pair to accumulate.
	 * @since 2.0
	 */
	void add(E1 element1, E2 element2);
	
	/**
	 * Accumulates the given pair element into this accumulator.
	 * 
	 * @param elementPair Pair element to accumulate.
	 * @since 2.0
	 */
	default void add(final Tuple2<? extends E1, ? extends E2> elementPair) {
		add(elementPair.get1(), elementPair.get2());
	}
	
	/**
	 * Accumulates the pair elements into this accumulator.
	 * 
	 * @param elements Pair elements to accumulate.
	 * @since 2.0
	 */
	default void addAll(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> elements) {
		for (final Tuple2<? extends E1, ? extends E2> element : elements) {
			add(element);
		}
	}
	
	/**
	 * Gets the current state of this accumulator.
	 * 
	 * @return The state.
	 * @since 2.0
	 */
	S get();
	
	/**
	 * Builds a view of this accumulator that filters the accumulated pairs of elements using the given filter.
	 *
	 * @param filter Predicate to use to filter the pairs of elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default Accumulator2<E1, E2, S> filtering(final Predicate2<? super E1, ? super E2> filter) {
		assert null != filter;
		
		final Accumulator2<E1, E2, S> self = this;
		return new Accumulator2<E1, E2, S>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				if (filter.evaluate(element1, element2)) {
					self.add(element1, element2);
				}
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that transforms the accumulated pairs of elements using the given function.
	 *
	 * @param <TE1> Type of the first element of the pairs to transform for accumulation.
	 * @param <TE2> Type of the second element of the pairs to transform for accumulation.
	 * @param function Function to use to transform the pairs of elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default <TE1, TE2> Accumulator2<TE1, TE2, S> mapping(final Function2<? super TE1, ? super TE2, ? extends Tuple2<? extends E1, ? extends E2>> function) {
		assert null != function;
		
		final Accumulator2<E1, E2, S> self = this;
		return new Accumulator2<TE1, TE2, S>() {
			@Override
			public void add(final TE1 element1, final TE2 element2) {
				self.add(function.evaluate(element1, element2));
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that extracts the accumulated pairs of elements using the given extractor.
	 *
	 * @param <EE1> Type of the first element of the pairs to extract from for accumulation.
	 * @param <EE2> Type of the second element of the pairs to extract from for accumulation.
	 * @param extractor Function to use to extract the pairs of elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default <EE1, EE2> Accumulator2<EE1, EE2, S> extracting(final Function2<? super EE1, ? super EE2, ? extends Tuple2<? extends E1, ? extends E2>> extractor) {
		assert null != extractor;
		
		final Accumulator2<E1, E2, S> self = this;
		return new Accumulator2<EE1, EE2, S>() {
			@Override
			public void add(final EE1 element1, final EE2 element2) {
				self.add(extractor.evaluate(element1, element2));
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that extracts the accumulated pairs of elements using the given extractor.
	 *
	 * @param <EE1> Type of the first element of the pairs to extract from for accumulation.
	 * @param <EE2> Type of the second element of the pairs to extract from for accumulation.
	 * @param extractor Function to use to extract the pairs of elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default <EE1, EE2> Accumulator2<EE1, EE2, S> extractingAll(final Function2<? super EE1, ? super EE2, ? extends Iterable<? extends Tuple2<? extends E1, ? extends E2>>> extractor) {
		assert null != extractor;
		
		final Accumulator2<E1, E2, S> self = this;
		return new Accumulator2<EE1, EE2, S>() {
			@Override
			public void add(final EE1 element1, final EE2 element2) {
				self.addAll(extractor.evaluate(element1, element2));
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that normalizes the accumulated pairs of elements.
	 * <p>
	 * Each pair of elements will be accumulated at most once.
	 * 
	 * @return The built view.
	 * @see ImperativePredicates#normalizer2()
	 * @since 2.0
	 */
	default Accumulator2<E1, E2, S> normalizing() {
		return filtering(ImperativePredicates.normalizer2());
	}
	
	/**
	 * Builds a view of this accumulator that normalizes the accumulated pairs of elements according to the given hash function.
	 * <p>
	 * At most one pair of elements will be accumulated for each hash value.
	 * 
	 * @param <K> Type of the hash values.
	 * @param hashFunction Function that computes the hash valuees of the accumulated pairs of elements.
	 * @return The built view.
	 * @see ImperativePredicates#normalizer()
	 * @since 2.0
	 */
	default <K> Accumulator2<E1, E2, S> normalizing(final Function2<? super E1, ? super E2, ? extends K> hashFunction) {
		final Predicate<K> normalizer = ImperativePredicates.normalizer();
		return filtering((arg1, arg2) -> normalizer.evaluate(hashFunction.evaluate(arg1, arg2)));
	}
	
	/**
	 * Builds a view of this accumulator that uncurries accumulated pairs of elements.
	 *
	 * @return The built view.
	 * @since 2.0
	 */
	default Accumulator<Tuple2<E1, E2>, S> uncurrying() {
		final Accumulator2<E1, E2, S> self = this;
		return new Accumulator<Tuple2<E1, E2>, S>() {
			@Override
			public void add(final Tuple2<E1, E2> element) {
				self.add(element.get1(), element.get2());
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that transforms the state using the given function.
	 *
	 * @param <TS> Type of the transformed state.
	 * @param function Function to use to transform the state.
	 * @return The built accumulator.
	 * @since 2.0
	 */
	default <TS> Accumulator2<E1, E2, TS> map(final Function<? super S, ? extends TS> function) {
		assert null != function;
		
		final Accumulator2<E1, E2, S> self = this;
		return new Accumulator2<E1, E2, TS>() {
			@Override
			public void add(final E1 element1, final E2 element2) {
				self.add(element1, element2);
			}
			
			@Override
			public TS get() {
				return function.evaluate(self.get());
			}
		};
	}
	
	/**
	 * Lifts this accumulator as a Java 8 bi-consumer.
	 * 
	 * @return The built Java 8 bi-consumer.
	 * @since 2.0
	 */
	@Override
	default BiConsumer<E1, E2> toBiConsumer() {
		return this::add;
	}
	
	// Procedure.
	
	@Override
	default void execute(final E1 element1, final E2 element2) {
		add(element1, element2);
	}
}
