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
package com.trazere.util.automaton;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.MutableObject;

/**
 * The {@link Automaton} class provides a skeleton implementation of automatons.
 * <p>
 * This class provides the basis for a simple state machine. It however does not provide any support to represent the corresponding graph. It has been designed
 * so that the state is reprensented as a continuation for the next step. A typical implementation of the continuations is an interface that provides a method
 * for every transition. The workflow is therefore effeciently provides through polymorphism.
 * 
 * @param <S> Type of the states.
 * @deprecated Use {@link com.trazere.core.design.Automaton}.
 */
@Deprecated
public class Automaton<S> {
	/**
	 * Instantiates a new automaton.
	 * 
	 * @param initialState Initial state of the automaton.
	 * @deprecated Use {@link com.trazere.core.design.Automaton#Automaton(Object)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected Automaton(final S initialState) {
		assert null != initialState;
		
		// Initialization.
		_state = new MutableObject<S>(initialState);
	}
	
	// State.
	
	/**
	 * Current state of the automaton.
	 * 
	 * @deprecated Use {@link com.trazere.core.design.Automaton#_state}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected MutableObject<S> _state;
	
	/**
	 * Performs the transition corresponding to the given state transformation function.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param transition Transition to perform.
	 * @return The new state.
	 * @throws X When the transition evalution fails.
	 * @deprecated Use {@link com.trazere.core.design.Automaton#transition(com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected <X extends Exception> S performTransition(final Function1<? super S, ? extends S, X> transition)
	throws X {
		assert null != transition;
		
		return _state.set(transition.evaluate(_state.get()));
	}
}
