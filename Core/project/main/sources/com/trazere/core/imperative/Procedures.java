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
 * The {@link Procedures} class provides various factories of {@link Procedure procedures}.
 * 
 * @see Procedure
 * @see Procedure2
 * @since 2.0
 */
public class Procedures {
	/**
	 * Builds a procedure that does nothing.
	 * 
	 * @param <A> Type of the arguments.
	 * @return The built procedure.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <A> Procedure<A> nop() {
		return (Procedure<A>) NOP;
	}
	
	private static final Procedure<?> NOP = arg -> {
		// Nothing to do.
	};
	
	/**
	 * Builds a two arguments procedure that does nothing.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @return The built procedure.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <A1, A2> Procedure2<A1, A2> nop2() {
		return (Procedure2<A1, A2>) NOP2;
	}
	
	private static final Procedure2<?, ?> NOP2 = (arg1, arg2) -> {
		// Nothing to do.
	};
	
	private Procedures() {
		// Prevents instantiation.
	}
}
