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
package com.trazere.core.imperative;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ImperativeFunctions} class provides various factories of {@link Function functions} with imperative features.
 * 
 * @see Function
 * @see Function2
 * @see Function3
 */
public class ImperativeFunctions {
	/**
	 * Builds a function that lifts the given procedure.
	 *
	 * @param <A> Type of the arguments.
	 * @param procedure Procedure to lift.
	 * @return The built function.
	 */
	public static <A> Function<A, Void> fromProcedure(final Procedure<? super A> procedure) {
		return fromProcedure(procedure, (Void) null);
	}
	
	/**
	 * Builds a function that lifts the given procedure and evaluates to the given result.
	 *
	 * @param <A> Type of the arguments.
	 * @param <R> Type of the results.
	 * @param procedure Procedure to lift.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A, R> Function<A, R> fromProcedure(final Procedure<? super A> procedure, final R result) {
		assert null != procedure;
		
		return arg -> {
			procedure.execute(arg);
			return result;
		};
	}
	
	/**
	 * Builds a function that normalizes its arguments according to the given hash function.
	 * <p>
	 * The built function always returns the same value for a given hash. The returned value corresponds to the first value that produced the corresponding
	 * hash.
	 *
	 * @param <A> Type of the arguments.
	 * @param <H> Type of the hash values.
	 * @param hashFunction Function that hashes the arguments.
	 * @return The built function.
	 */
	public static <A, H> Function<A, A> normalizer(final Function<? super A, ? extends H> hashFunction) {
		assert null != hashFunction;
		
		final Map<H, A> normalizedArgs = new HashMap<>();
		return (final A arg) -> {
			final H hash = hashFunction.evaluate(arg);
			if (normalizedArgs.containsKey(hash)) {
				return normalizedArgs.get(hash);
			} else {
				normalizedArgs.put(hash, arg);
				return arg;
			}
		};
	}
	
	/**
	 * Builds a two arguments function from the given two arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param procedure Procedure to lift.
	 * @return The built function.
	 */
	public static <A1, A2> Function2<A1, A2, Void> fromProcedure2(final Procedure2<? super A1, ? super A2> procedure) {
		return fromProcedure2(procedure, (Void) null);
	}
	
	/**
	 * Builds a two arguments function from the given two arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <R> Type of the results.
	 * @param procedure Procedure to lift.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A1, A2, R> Function2<A1, A2, R> fromProcedure2(final Procedure2<? super A1, ? super A2> procedure, final R result) {
		assert null != procedure;
		
		return (arg1, arg2) -> {
			procedure.execute(arg1, arg2);
			return result;
		};
	}
	
	/**
	 * Builds a three arguments function from the given three arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param procedure Procedure to lift.
	 * @return The built function.
	 */
	public static <A1, A2, A3> Function3<A1, A2, A3, Void> fromProcedure3(final Procedure3<? super A1, ? super A2, ? super A3> procedure) {
		return fromProcedure3(procedure, (Void) null);
	}
	
	/**
	 * Builds a three arguments function from the given three arguments procedure.
	 *
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param <A3> Type of the third arguments.
	 * @param <R> Type of the results.
	 * @param procedure Procedure to lift.
	 * @param result Result of the function.
	 * @return The built function.
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> fromProcedure3(final Procedure3<? super A1, ? super A2, ? super A3> procedure, final R result) {
		assert null != procedure;
		
		return (arg1, arg2, arg3) -> {
			procedure.execute(arg1, arg2, arg3);
			return result;
		};
	}
	
	private ImperativeFunctions() {
		// Prevent instantiation.
	}
}
