/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.function;

/**
 * The {@link Procedures} class provides various common procedures.
 * 
 * @see Procedure0
 * @see Procedure1
 * @see Procedure2
 */
public class Procedures {
	/**
	 * Builds a zero arguments procedure which does nothing.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built procedure.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Procedure0<X> nop0() {
		return (Procedure0<X>) _NOP0;
	}
	
	private static final Procedure0<?> _NOP0 = new Procedure0<RuntimeException>() {
		public void execute() {
			// Nothing to do.
		}
	};
	
	/**
	 * Builds a one argument procedure which does nothing.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built procedure.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Procedure1<T, X> nop1() {
		return (Procedure1<T, X>) _NOP1;
	}
	
	private static final Procedure1<?, ?> _NOP1 = new Procedure1<Object, RuntimeException>() {
		public void execute(final Object value) {
			// Nothing to do.
		}
	};
	
	/**
	 * Builds a two arguments procedure which does nothing.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built procedure.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Procedure2<T1, T2, X> nop2() {
		return (Procedure2<T1, T2, X>) _NOP2;
	}
	
	private static final Procedure2<?, ?, ?> _NOP2 = new Procedure2<Object, Object, RuntimeException>() {
		public void execute(final Object value1, final Object value2) {
			// Nothing to do.
		}
	};
	
	private Procedures() {
		// Prevent instantiation.
	}
}
