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
 * The {@link Accumulator2} interface defines left folding functions of pairs of values over an internal state.
 * <p>
 * Accumulators are dual to iterators.
 * 
 * @param <V1> Type of the first value of the accumulated pairs.
 * @param <V2> Type of the second value of the accumulated pairs.
 * @param <S> Type of the state.
 */
public interface Accumulator2<V1, V2, S>
extends Procedure2<V1, V2> {
	/**
	 * Accumulates the given pair of values into the receiver accumulator.
	 * 
	 * @param value1 First value of the pair to accumulate.
	 * @param value2 Second value of the pair to accumulate.
	 */
	void add(V1 value1, V2 value2);
	
	/**
	 * Accumulates the given pair of values into the receiver accumulator.
	 * 
	 * @param value Pair of values to accumulate.
	 */
	default void add(final Tuple2<? extends V1, ? extends V2> value) {
		add(value.get1(), value.get2());
	}
	
	/**
	 * Accumulates the given pairs of values into the receiver accumulator.
	 * 
	 * @param values Pairs of values to accumulate.
	 */
	default void addAll(final Iterable<? extends Tuple2<? extends V1, ? extends V2>> values) {
		for (final Tuple2<? extends V1, ? extends V2> value : values) {
			add(value);
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
	default void execute(final V1 value1, final V2 value2) {
		add(value1, value2);
	}
}
