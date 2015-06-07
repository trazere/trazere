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
 * The {@link Effects} class provides various factories of {@link Effect effects}.
 * 
 * @see Effect
 * @since 1.0
 */
public class Effects {
	/**
	 * Builds an effect that does nothing.
	 * 
	 * @return The built effect.
	 * @since 1.0
	 */
	public static Effect nop() {
		return NOP;
	}
	
	private static final Effect NOP = () -> {
		// Nothing to do.
	};
	
	// TODO: sequence
	
	/**
	 * Builds an effect that lifts the given runnable.
	 * 
	 * @param runnable Runnable to lift.
	 * @return The built effect.
	 * @since 1.0
	 */
	public static Effect fromRunnable(final Runnable runnable) {
		assert null != runnable;
		
		return () -> {
			runnable.run();
		};
	}
	
	private Effects() {
		// Prevents instantiation.
	}
}
