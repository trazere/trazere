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
 * The {@link EffectUtils} class provides various utilities regarding {@link Effect effects}.
 * 
 * @see Effect
 * @since 2.0
 */
public class EffectUtils {
	/**
	 * Builds a runnable that lifts the given effect.
	 * 
	 * @param effect Effect to lift.
	 * @return The built runnable.
	 * @since 2.0
	 */
	public static Runnable toRunnable(final Effect effect) {
		assert null != effect;
		
		return () -> {
			effect.execute();
		};
	}
	
	private EffectUtils() {
		// Prevent instantiation.
	}
}
