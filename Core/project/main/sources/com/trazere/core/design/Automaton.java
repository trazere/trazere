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
import com.trazere.core.functional.Thunk;
import com.trazere.core.reference.MutableReference;
import com.trazere.core.util.Maybe;

/**
 * The {@link Automaton} class provides a skeleton implementation of automatons.
 * <p>
 * This class provides the basis for a simple state machine. It does not provide any support to represent the corresponding graph. The recommanded usage method
 * is to delegate the transition logic to the states through polymorphism.
 * <p>
 * The automatons have to be started before they can be transitioned. They can be started again after they have stopped.
 * 
 * @param <S> Type of the states.
 * @since 2.0
 */
public class Automaton<S> {
	// State.
	
	/**
	 * Current state of the automaton.
	 * 
	 * @since 2.0
	 */
	private final MutableReference<S> _state = new MutableReference<>();
	
	/**
	 * Indicates whether this automaton is started.
	 * 
	 * @return <code>true</code> when the automaton is started, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public boolean isStarted() {
		return _state.isSet();
	}
	
	/**
	 * Starts this automaton with the given state.
	 * 
	 * @param transition Transition to the initial state to perform.
	 * @return The initial state.
	 * @throws IllegalStateException When the automaton is already started.
	 * @since 2.0
	 */
	protected S start(final S transition)
	throws IllegalStateException {
		// Check that the automaton is not started.
		if (_state.isSet()) {
			throw new IllegalStateException("Automaton " + this + " is already started");
		}
		
		// Start.
		return _state.set(transition);
	}
	
	/**
	 * Starts this automaton with the given state.
	 * 
	 * @param transition Transition to the initial state to perform.
	 * @return The initial state.
	 * @throws IllegalStateException When the automaton is already started.
	 * @since 2.0
	 */
	protected S start(final Thunk<? extends S> transition)
	throws IllegalStateException {
		// Check that the automaton is not started.
		if (_state.isSet()) {
			throw new IllegalStateException("Automaton " + this + " is already started");
		}
		
		// Start.
		return _state.set(transition.evaluate());
	}
	
	/**
	 * Gets the current state of this automaton.
	 * 
	 * @return The current state, or nothing when the automaton is not started.
	 * @since 2.0
	 */
	public Maybe<S> getState() {
		return _state.asMaybe();
	}
	
	/**
	 * Performs a transition using the given state transformation function.
	 * <p>
	 * The transformation function takes the current state as argument and results to the new state, or nothing to stop the automaton.
	 * 
	 * @param transition Function to use to transition the state.
	 * @return The new state, or nothing when the automaton has been stopped.
	 * @throws IllegalStateException When the automaton is not started.
	 * @since 2.0
	 */
	protected Maybe<? extends S> transition(final Function<? super S, ? extends Maybe<? extends S>> transition)
	throws IllegalStateException {
		// Check that the automaton is started.
		if (!_state.isSet()) {
			throw new IllegalStateException("Automaton " + this + " is not started");
		}
		
		// Start.
		return _state.update(transition.evaluate(_state.get()));
	}
}
