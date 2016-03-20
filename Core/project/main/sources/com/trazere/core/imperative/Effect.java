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
 * The {@link Effect} interface defines abstract effects.
 * <p>
 * Effects represent no-arguments procedures whose execution solely relies on the captured context. They provide an abstraction for lazy side effects.
 * 
 * @since 2.0
 */
@FunctionalInterface
public interface Effect {
	/**
	 * Executes this effect.
	 * 
	 * @since 2.0
	 */
	void execute();
	
	/**
	 * Executes this effect in a thread safe way.
	 * 
	 * @since 2.0
	 */
	default void synchronizedExecute() {
		synchronized (this) {
			execute();
		}
	}
	
	/**
	 * Builds a synchronized view of this effect.
	 * 
	 * @return The built effect.
	 * @see #synchronizedExecute()
	 * @since 2.0
	 */
	default Effect synchronized_() {
		return this::synchronizedExecute;
	}
	
	/**
	 * Lifts this effect as a runnable.
	 * 
	 * @return The built runnable.
	 * @since 2.0
	 */
	default Runnable toRunnable() {
		return this::execute;
	}
}
