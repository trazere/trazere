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
	
	// Procedure.
	
	@Override
	default void execute(final E element) {
		add(element);
	}
}
