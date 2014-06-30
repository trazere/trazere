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
 * The {@link FoldAccumulator2} class implements {@link Accumulator2 pair accumulators} based on an initial state and a binary folding operation.
 * 
 * @param <A1> Type of the first value of the pairs.
 * @param <A2> Type of the second value of the pairs.
 * @param <S> Type of the states.
 */
public abstract class FoldAccumulator2<A1, A2, S>
extends BaseAccumulator2<A1, A2, S> {
	/** Current state. */
	private S _state;
	
	/**
	 * Instantiates a new accumulator.
	 * 
	 * @param initialState Initial state.
	 */
	public FoldAccumulator2(final S initialState) {
		_state = initialState;
	}
	
	@Override
	public void add(final A1 value1, final A2 value2) {
		_state = fold(_state, value1, value2);
	}
	
	/**
	 * Folds the state by applying the binary operation with given pair of values.
	 * 
	 * @param currentState Current state.
	 * @param value1 First value of the pair to accumulate.
	 * @param value2 Second value of the pair to accumulate.
	 * @return The folded state.
	 */
	protected abstract S fold(S currentState, A1 value1, A2 value2);
	
	@Override
	public S get() {
		return _state;
	}
}
