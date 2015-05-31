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
 * The {@link FoldAccumulator} class implements accumulators that update their state using a binary operation combining the previous state and the accumulated
 * element.
 * <p>
 * They simulate left folding functions.
 * 
 * @param <E> Type of the accumulated elements.
 * @param <S> Type of the state.
 * @since 1.0
 */
public abstract class FoldAccumulator<E, S>
implements Accumulator<E, S> {
	/** Current state. */
	private S _state;
	
	/**
	 * Instantiates a new accumulator.
	 * 
	 * @param initialState Initial state.
	 * @since 1.0
	 */
	public FoldAccumulator(final S initialState) {
		_state = initialState;
	}
	
	@Override
	public void add(final E element) {
		_state = fold(_state, element);
	}
	
	/**
	 * Applies the binary operation with the given state and element.
	 * 
	 * @param state Current state.
	 * @param element Accumulated element.
	 * @return The new state.
	 * @since 1.0
	 */
	protected abstract S fold(S state, E element);
	
	@Override
	public S get() {
		return _state;
	}
}
