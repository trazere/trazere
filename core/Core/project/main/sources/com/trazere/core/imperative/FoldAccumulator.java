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
 * The {@link FoldAccumulator} class implements {@link Accumulator accumulators} based on an initial state and a folding binary operation.
 * 
 * @param <A> Type of the values.
 * @param <S> Type of the state.
 */
public abstract class FoldAccumulator<A, S>
implements Accumulator<A, S> {
	/** Current state. */
	private S _state;
	
	/**
	 * Instantiates a new accumulator.
	 * 
	 * @param initialState Initial state.
	 */
	public FoldAccumulator(final S initialState) {
		_state = initialState;
	}
	
	@Override
	public void add(final A value) {
		_state = fold(_state, value);
	}
	
	/**
	 * Folds the state by applying the binary operation with given value.
	 * 
	 * @param currentState Current state.
	 * @param value Value to accumulate.
	 * @return The folded state.
	 */
	protected abstract S fold(S currentState, A value);
	
	@Override
	public S get() {
		return _state;
	}
}
