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
 * The {@link EffectProcedures} class provides various factories of {@link Procedure procedures} related to {@link Effect effects}.
 * 
 * @see Procedure
 * @see Effect
 * @since 1.0
 */
public class EffectProcedures {
	/**
	 * Builds a procedure that executes the argument effects.
	 *
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static Procedure<Effect> execute() {
		return EXECUTE;
	}
	
	private static final Procedure<Effect> EXECUTE = effect -> effect.execute();
	
	private EffectProcedures() {
		// Prevents instantiation.
	}
}
