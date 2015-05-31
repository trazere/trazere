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
 * pair of elements.
 * <p>
 * They simulate left folding functions.
 * 
 * @param <E1> Type of the first element of the accumulated pairs.
 * @param <E2> Type of the second element of the accumulated pairs.
 * @param <S> Type of the states.
 * @since 1.0
 */
public abstract class FoldAccumulator2<E1, E2, S>
implements Accumulator2<E1, E2, S> {
	/** Current state. */
	private S _state;
	
	/**
	 * Instantiates a new accumulator.
	 * 
	 * @param initialState Initial state.
	 * @since 1.0
	 */
	public FoldAccumulator2(final S initialState) {
		_state = initialState;
	}
	
	@Override
	public void add(final E1 element1, final E2 element2) {
		_state = fold(_state, element1, element2);
	}
	
	/**
	 * Applies the binary operation with the given state and pair of elements.
	 * 
	 * @param state Current state.
	 * @param element1 First element of the accumulated pair.
	 * @param element2 Second element of the accumulated pair.
	 * @return The new state.
	 * @since 1.0
	 */
	protected abstract S fold(S state, E1 element1, E2 element2);
	
	@Override
	public S get() {
		return _state;
	}
}
