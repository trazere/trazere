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

import com.trazere.core.util.Tuple2;

/**
 * The {@link Accumulator2} interface defines automata that incrementally refine an internal state over a sequence of pairs of elements.
 * <p>
 * Accumulators are dual to iterators.
 * 
 * @param <E1> Type of the first element of the accumulated pairs.
 * @param <E2> Type of the second element of the accumulated pairs.
 * @param <S> Type of the state.
 */
public interface Accumulator2<E1, E2, S>
extends Procedure2<E1, E2> {
	/**
	 * Accumulates the given pair of elements into the receiver accumulator.
	 * 
	 * @param element1 First element of the pair to accumulate.
	 * @param element2 Second element of the pair to accumulate.
	 */
	void add(E1 element1, E2 element2);
	
	/**
	 * Accumulates the given pair element into the receiver accumulator.
	 * 
	 * @param elementPair Pair element to accumulate.
	 */
	default void add(final Tuple2<? extends E1, ? extends E2> elementPair) {
		add(elementPair.get1(), elementPair.get2());
	}
	
	/**
	 * Accumulates the pair elements into the receiver accumulator.
	 * 
	 * @param elements Pair elements to accumulate.
	 */
	default void addAll(final Iterable<? extends Tuple2<? extends E1, ? extends E2>> elements) {
		for (final Tuple2<? extends E1, ? extends E2> element : elements) {
			add(element);
		}
	}
	
	/**
	 * Gets the current state of the receiver accumulator.
	 * 
	 * @return The state.
	 */
	S get();
	
	// Procedure.
	
	@Override
	default void execute(final E1 element1, final E2 element2) {
		add(element1, element2);
	}
}
