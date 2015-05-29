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
package com.trazere.util.function;

/**
 * The {@link Procedures} class provides various common procedures.
 * 
 * @see Procedure0
 * @see Procedure1
 * @see Procedure2
 * @deprecated Use core.
 */
@Deprecated
public class Procedures {
	/**
	 * Builds a zero arguments procedure which does nothing.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built procedure.
	 * @deprecated Use {@link com.trazere.core.imperative.Effects#nop()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Procedure0<X> nop0() {
		return (Procedure0<X>) _NOP0;
	}
	
	private static final Procedure0<?> _NOP0 = new Procedure0<RuntimeException>() {
		@Override
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
	 * @deprecated Use {@link com.trazere.core.imperative.Procedures#nop()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Procedure1<T, X> nop1() {
		return (Procedure1<T, X>) _NOP1;
	}
	
	private static final Procedure1<?, ?> _NOP1 = new Procedure1<Object, RuntimeException>() {
		@Override
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
	 * @deprecated Use {@link com.trazere.core.imperative.Procedures#nop2()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Procedure2<T1, T2, X> nop2() {
		return (Procedure2<T1, T2, X>) _NOP2;
	}
	
	private static final Procedure2<?, ?, ?> _NOP2 = new Procedure2<Object, Object, RuntimeException>() {
		@Override
		public void execute(final Object value1, final Object value2) {
			// Nothing to do.
		}
	};
	
	// TODO: sequence
	
	@Deprecated
	/**
	 * Builds a procedure which executes its zero arguments procedure arguments.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built procedure.
	 * @deprecated Use {@link com.trazere.core.imperative.EffectProcedures#execute()}.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Procedure1<Procedure0<? extends X>, X> execute() {
		return (Procedure1<Procedure0<? extends X>, X>) _EXECUTE;
	}
	
	private static final Procedure1<? extends Procedure0<?>, ?> _EXECUTE = new Procedure1<Procedure0<?>, Exception>() {
		@Override
		public void execute(final Procedure0<? extends Exception> procedure)
		throws Exception {
			procedure.execute();
		}
	};
	
	/**
	 * Builds a procedure which executes its zero arguments procedure arguments.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built procedure.
	 * @deprecated Use {@link #execute()}.
	 */
	@Deprecated
	public static <X extends Exception> Procedure1<Procedure0<? extends X>, X> execute0() {
		return execute();
	}
	
	/**
	 * Builds a procedure which executes ite one argument procedure arguments with the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param value The argument value. May be <code>null</code>.
	 * @return The built procedure.
	 * @deprecated Use {@link com.trazere.core.imperative.Procedures#execute(Object)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Procedure1<Procedure1<? super T, ? extends X>, X> execute(final T value) {
		return new Procedure1<Procedure1<? super T, ? extends X>, X>() {
			@Override
			public void execute(final Procedure1<? super T, ? extends X> procedure)
			throws X {
				procedure.execute(value);
			}
		};
	}
	
	/**
	 * Builds a procedure which executes ite one argument procedure arguments with the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param value The argument value. May be <code>null</code>.
	 * @return The built procedure.
	 * @deprecated Use {@link #execute(Object)}.
	 */
	@Deprecated
	public static <T, X extends Exception> Procedure1<Procedure1<? super T, ? extends X>, X> execute1(final T value) {
		return execute(value);
	}
	
	/**
	 * Builds a procedure which executes ite two arguments procedure arguments with the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the secord argument values.
	 * @param <X> Type of the exceptions.
	 * @param value1 The first argument value. May be <code>null</code>.
	 * @param value2 The second argument value. May be <code>null</code>.
	 * @return The built procedure.
	 * @deprecated Use {@link com.trazere.core.imperative.Procedures#execute(Object, Object)}.
	 */
	@Deprecated
	public static <T1, T2, X extends Exception> Procedure1<Procedure2<? super T1, ? super T2, ? extends X>, X> execute(final T1 value1, final T2 value2) {
		return new Procedure1<Procedure2<? super T1, ? super T2, ? extends X>, X>() {
			@Override
			public void execute(final Procedure2<? super T1, ? super T2, ? extends X> procedure)
			throws X {
				procedure.execute(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a procedure which executes ite two arguments procedure arguments with the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the secord argument values.
	 * @param <X> Type of the exceptions.
	 * @param value1 The first argument value. May be <code>null</code>.
	 * @param value2 The second argument value. May be <code>null</code>.
	 * @return The built procedure.
	 * @deprecated Use {@link #execute(Object, Object)}.
	 */
	@Deprecated
	public static <T1, T2, X extends Exception> Procedure1<Procedure2<? super T1, ? super T2, ? extends X>, X> execute2(final T1 value1, final T2 value2) {
		return execute(value1, value2);
	}
	
	private Procedures() {
		// Prevents instantiation.
	}
}
