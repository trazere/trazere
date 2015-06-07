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

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The {@link Procedures} class provides various factories of {@link Procedure procedures}.
 * 
 * @see Procedure
 * @see Procedure2
 * @since 1.0
 */
public class Procedures {
	/**
	 * Builds a procedure that does nothing.
	 * 
	 * @param <A> Type of the arguments.
	 * @return The built procedure.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <A> Procedure<A> nop() {
		return (Procedure<A>) NOP;
	}
	
	private static final Procedure<?> NOP = arg -> {
		// Nothing to do.
	};
	
	// TODO: sequence
	
	/**
	 * Builds a procedure that lifts the given consumer.
	 * 
	 * @param <A> Type of the arguments.
	 * @param consumer Consumer to lift.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A> Procedure<A> fromConsumer(final Consumer<? super A> consumer) {
		assert null != consumer;
		
		return arg -> {
			consumer.accept(arg);
		};
	}
	
	/**
	 * Builds a procedure that executes the argument procedures with the given argument.
	 *
	 * @param <A> Type of the arguments.
	 * @param arg Argument to use to execute the procedures.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A> Procedure<Procedure<? super A>> execute(final A arg) {
		return procedure -> procedure.execute(arg);
	}
	
	/**
	 * Builds a procedure that executes the two arguments argument procedures with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param arg1 First argument to use to execute the procedures.
	 * @param arg2 Second argument to use to execute the procedures.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A1, A2> Procedure<Procedure2<? super A1, ? super A2>> execute(final A1 arg1, final A2 arg2) {
		return procedure -> procedure.execute(arg1, arg2);
	}
	
	/**
	 * Builds a procedure that executes the three arguments argument procedures with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <A3> Type of the third argument.
	 * @param arg1 First argument to use to execute the procedures.
	 * @param arg2 Second argument to use to execute the procedures.
	 * @param arg3 Third argument to use to execute the procedures.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A1, A2, A3> Procedure<Procedure3<? super A1, ? super A2, ? super A3>> execute(final A1 arg1, final A2 arg2, final A3 arg3) {
		return procedure -> procedure.execute(arg1, arg2, arg3);
	}
	
	/**
	 * Builds a procedure that executes the four arguments argument procedures with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <A3> Type of the third argument.
	 * @param <A4> Type of the fourth argument.
	 * @param arg1 First argument to use to execute the procedures.
	 * @param arg2 Second argument to use to execute the procedures.
	 * @param arg3 Third argument to use to execute the procedures.
	 * @param arg4 Fourth argument to use to execute the procedures.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A1, A2, A3, A4> Procedure<Procedure4<? super A1, ? super A2, ? super A3, ? super A4>> execute(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4) {
		return procedure -> procedure.execute(arg1, arg2, arg3, arg4);
	}
	
	/**
	 * Builds a procedure that executes the five arguments argument procedures with the given arguments.
	 *
	 * @param <A1> Type of the first argument.
	 * @param <A2> Type of the second argument.
	 * @param <A3> Type of the third argument.
	 * @param <A4> Type of the fourth argument.
	 * @param <A5> Type of the fifth argument.
	 * @param arg1 First argument to use to execute the procedures.
	 * @param arg2 Second argument to use to execute the procedures.
	 * @param arg3 Third argument to use to execute the procedures.
	 * @param arg4 Fourth argument to use to execute the procedures.
	 * @param arg5 Fifth argument to use to execute the procedures.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A1, A2, A3, A4, A5> Procedure<Procedure5<? super A1, ? super A2, ? super A3, ? super A4, ? super A5>> execute(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4, final A5 arg5) {
		return procedure -> procedure.execute(arg1, arg2, arg3, arg4, arg5);
	}
	
	/**
	 * Builds a two arguments procedure that does nothing.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @return The built procedure.
	 * @since 1.0
	 */
	@SuppressWarnings("unchecked")
	public static <A1, A2> Procedure2<A1, A2> nop2() {
		return (Procedure2<A1, A2>) NOP2;
	}
	
	private static final Procedure2<?, ?> NOP2 = (arg1, arg2) -> {
		// Nothing to do.
	};
	
	// TODO: sequence2
	
	/**
	 * Builds a two arguments procedure that lifts the given bi-consumer.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param consumer Bi-consumer to lift.
	 * @return The built procedure.
	 * @since 1.0
	 */
	public static <A1, A2> Procedure2<A1, A2> fromBiConsumer(final BiConsumer<? super A1, ? super A2> consumer) {
		assert null != consumer;
		
		return (arg1, arg2) -> {
			consumer.accept(arg1, arg2);
		};
	}
	
	private Procedures() {
		// Prevents instantiation.
	}
}
