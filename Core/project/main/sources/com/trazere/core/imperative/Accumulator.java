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


/**
 * The {@link Accumulator} interface defines left folding functions over an internal state.
 * <p>
 * Accumulators are dual to iterators.
 * 
 * @param <A> Type of the arguments.
 * @param <S> Type of the state.
 */
public interface Accumulator<A, S>
extends Procedure<A> {
	/**
	 * Accumulates the given value into the receiver accumulator.
	 * 
	 * @param value Value to accumulate.
	 */
	void add(A value);
	
	/**
	 * Accumulates the given values into the receiver accumulator.
	 * <p>
	 * Values are accumulated in the order they are iterated.
	 * 
	 * @param values Values to accumulate.
	 */
	default void addAll(final Iterable<? extends A> values) {
		for (final A value : values) {
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
	default void execute(final A value) {
		add(value);
	}
}
