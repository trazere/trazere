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
package com.trazere.core.design;

import com.trazere.core.functional.Function;
import com.trazere.core.lang.MutableObject;

// TODO: deport state initialization out of the constructor (optional ref + start method ?) -> voir ExportAutomaton

/**
 * The {@link Automaton} class provides a skeleton implementation of automatons.
 * <p>
 * This class provides the basis for a simple state machine. It does not provide any support to represent the corresponding graph. It has been designed so that
 * the state is reprensented as a continuation for the next step. A typical implementation of the continuations is an interface that provides a method for every
 * transition. The workflow is therefore effeciently provides through polymorphism.
 * 
 * @param <S> Type of the states.
 * @since 1.0
 */
public class Automaton<S> {
	/**
	 * Instantiates a new automaton.
	 * 
	 * @param initialState Initial state of the automaton.
	 * @since 1.0
	 */
	protected Automaton(final S initialState) {
		assert null != initialState;
		
		// Initialization.
		_state = new MutableObject<>(initialState);
	}
	
	// State.
	
	/**
	 * Current state of the automaton.
	 * 
	 * @since 1.0
	 */
	protected final MutableObject<S> _state;
	
	/**
	 * Gets the current state of this automaton.
	 * 
	 * @return The current state.
	 * @since 1.0
	 */
	public S getState() {
		return _state.get();
	}
	
	/**
	 * Performs the transition corresponding to the given state transformation function.
	 * 
	 * @param transition Transition to perform.
	 * @return The new state.
	 * @since 1.0
	 */
	protected S transition(final Function<? super S, ? extends S> transition) {
		return _state.update(transition);
	}
}
