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
import com.trazere.core.functional.Predicate;
import com.trazere.core.util.Maybe;
import java.util.function.Consumer;

/**
 * The {@link Accumulator} interface defines automata that incrementally refine an internal state over a sequence of accumulated elements.
 * <p>
 * Accumulators are dual to iterators.
 * 
 * @param <E> Type of the accumulated elements.
 * @param <S> Type of the state.
 * @since 2.0
 */
public interface Accumulator<E, S>
extends Procedure<E> {
	/**
	 * Accumulates the given element into this accumulator.
	 * 
	 * @param element Element to accumulate.
	 * @since 2.0
	 */
	void add(E element);
	
	/**
	 * Accumulates the given elements into this accumulator.
	 * <p>
	 * Elements are accumulated in the order they are iterated.
	 * 
	 * @param elements Elements to accumulate.
	 * @since 2.0
	 */
	default void addAll(final Iterable<? extends E> elements) {
		for (final E element : elements) {
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
	 * Builds a view of this accumulator that filters the accumulated elements using the given filter.
	 *
	 * @param filter Predicate to use to filter the elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default Accumulator<E, S> filtering(final Predicate<? super E> filter) {
		assert null != filter;
		
		final Accumulator<E, S> self = this;
		return new Accumulator<E, S>() {
			@Override
			public void add(final E element) {
				if (filter.evaluate(element)) {
					self.add(element);
				}
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that transforms the accumulated elements using the given function.
	 *
	 * @param <TE> Type of the elements to transform for accumulation.
	 * @param function Function to use to transform the elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default <TE> Accumulator<TE, S> mapping(final Function<? super TE, ? extends E> function) {
		assert null != function;
		
		final Accumulator<E, S> self = this;
		return new Accumulator<TE, S>() {
			@Override
			public void add(final TE element) {
				self.add(function.evaluate(element));
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that extracts the accumulated elements using the given extractor.
	 *
	 * @param <EE> Type of the elements to extract from for accumulation.
	 * @param extractor Function to use to extract the elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default <EE> Accumulator<EE, S> extracting(final Function<? super EE, ? extends Maybe<? extends E>> extractor) {
		assert null != extractor;
		
		final Accumulator<E, S> self = this;
		return new Accumulator<EE, S>() {
			@Override
			public void add(final EE element) {
				final Maybe<? extends E> extractedElement = extractor.evaluate(element);
				if (extractedElement.isSome()) {
					self.add(extractedElement.asSome().getValue());
				}
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that extracts the accumulated elements using the given extractor.
	 *
	 * @param <EE> Type of the elements to extract from for accumulation.
	 * @param extractor Function to use to extract the elements to accumulate.
	 * @return The built view.
	 * @since 2.0
	 */
	default <EE> Accumulator<EE, S> extractingAll(final Function<? super EE, ? extends Iterable<? extends E>> extractor) {
		assert null != extractor;
		
		final Accumulator<E, S> self = this;
		return new Accumulator<EE, S>() {
			@Override
			public void add(final EE element) {
				self.addAll(extractor.evaluate(element));
			}
			
			@Override
			public S get() {
				return self.get();
			}
		};
	}
	
	/**
	 * Builds a view of this accumulator that normalizes the accumulated elements.
	 * <p>
	 * Each element will be accumulated at most once.
	 * 
	 * @return The built view.
	 * @see ImperativePredicates#normalizer()
	 * @since 2.0
	 */
	default Accumulator<E, S> normalizing() {
		return filtering(ImperativePredicates.normalizer());
	}
	
	/**
	 * Builds a view of this accumulator that normalizes the accumulated elements according to the given hash function.
	 * <p>
	 * At most one element will be accumulated for each hash value.
	 * 
	 * @param <K> Type of the hash values.
	 * @param hashFunction Function that computes the hash valuees of the accumulated elements.
	 * @return The built view.
	 * @see ImperativePredicates#normalizer()
	 * @since 2.0
	 */
	default <K> Accumulator<E, S> normalizing(final Function<? super E, ? extends K> hashFunction) {
		return filtering(ImperativePredicates.normalizer().compose(hashFunction));
	}
	
	/**
	 * Builds a view of this accumulator that transforms the state using the given function.
	 *
	 * @param <TS> Type of the transformed states.
	 * @param function Function to use to transform the state.
	 * @return The built view.
	 * @since 2.0
	 */
	default <TS> Accumulator<E, TS> map(final Function<? super S, ? extends TS> function) {
		assert null != function;
		
		final Accumulator<E, S> self = this;
		return new Accumulator<E, TS>() {
			@Override
			public void add(final E element) {
				self.add(element);
			}
			
			@Override
			public TS get() {
				return function.evaluate(self.get());
			}
		};
	}
	
	/**
	 * Lifts this accumulator as a Java 8 consumer.
	 * 
	 * @return The built Java 8 consumer.
	 * @since 2.0
	 */
	@Override
	default Consumer<E> toConsumer() {
		return this::add;
	}
	
	// Procedure.
	
	@Override
	default void execute(final E element) {
		add(element);
	}
}
