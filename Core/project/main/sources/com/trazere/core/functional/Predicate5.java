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
package com.trazere.core.functional;

import com.trazere.core.util.Tuple5;

/**
 * The {@link Predicate5} interface defines uncurried functions that take five arguments and result to booleans.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 * @param <A3> Type of the third arguments.
 * @param <A4> Type of the fourth arguments.
 * @param <A5> Type of the fifth arguments.
 * @since 2.0
 */
@FunctionalInterface
public interface Predicate5<A1, A2, A3, A4, A5> {
	/**
	 * Evaluates this predicate with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the predicate with.
	 * @param arg2 Second argument to evaluate the predicate with.
	 * @param arg3 Third argument to evaluate the predicate with.
	 * @param arg4 Fourth argument to evaluate the predicate with.
	 * @param arg5 Fifth argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 2.0
	 */
	boolean evaluate(A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5);
	
	/**
	 * Evaluates this predicate with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the predicate with.
	 * @param arg2 Second argument to evaluate the predicate with.
	 * @param arg3 Third argument to evaluate the predicate with.
	 * @param arg4 Fourth argument to evaluate the predicate with.
	 * @param arg5 Fifth argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 2.0
	 */
	default boolean synchronizedEvaluate(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4, final A5 arg5) {
		synchronized (this) {
			return evaluate(arg1, arg2, arg3, arg4, arg5);
		}
	}
	
	/**
	 * Builds a synchronized view of this predicate.
	 * 
	 * @return The built predicate.
	 * @see #synchronizedEvaluate(Object, Object, Object, Object, Object)
	 * @since 2.0
	 */
	default Predicate5<A1, A2, A3, A4, A5> synchronized_() {
		return this::synchronizedEvaluate;
	}
	
	/**
	 * Gets an uncurried view of this predicate (as a predicate that takes quintuples of elements).
	 *
	 * @return The built predicate.
	 * @since 2.0
	 */
	default Predicate<Tuple5<A1, A2, A3, A4, A5>> uncurried() {
		final Predicate5<A1, A2, A3, A4, A5> self = this;
		return arg -> self.evaluate(arg.get1(), arg.get2(), arg.get3(), arg.get4(), arg.get5());
	}
}
