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

import com.trazere.core.util.Tuple4;

/**
 * The {@link Predicate4} interface defines uncurried functions that take four arguments and result to booleans.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 * @param <A3> Type of the third arguments.
 * @param <A4> Type of the fourth arguments.
 * @since 2.0
 */
@FunctionalInterface
public interface Predicate4<A1, A2, A3, A4> {
	/**
	 * Evaluates this predicate with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the predicate with.
	 * @param arg2 Second argument to evaluate the predicate with.
	 * @param arg3 Third argument to evaluate the predicate with.
	 * @param arg4 Fourth argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 2.0
	 */
	boolean evaluate(A1 arg1, A2 arg2, A3 arg3, A4 arg4);
	
	/**
	 * Evaluates this predicate with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the predicate with.
	 * @param arg2 Second argument to evaluate the predicate with.
	 * @param arg3 Third argument to evaluate the predicate with.
	 * @param arg4 Fourth argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 2.0
	 */
	default boolean synchronizedEvaluate(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4) {
		synchronized (this) {
			return evaluate(arg1, arg2, arg3, arg4);
		}
	}
	
	/**
	 * Builds a synchronized view of this predicate.
	 * 
	 * @return The built predicate.
	 * @see #synchronizedEvaluate(Object, Object, Object, Object)
	 * @since 2.0
	 */
	default Predicate4<A1, A2, A3, A4> synchronized_() {
		return this::synchronizedEvaluate;
	}
	
	/**
	 * Gets an uncurried view of this predicate (as a predicate that takes quadruples of elements).
	 *
	 * @return The built predicate.
	 * @since 2.0
	 */
	default Predicate<Tuple4<A1, A2, A3, A4>> uncurried() {
		final Predicate4<A1, A2, A3, A4> self = this;
		return arg -> self.evaluate(arg.get1(), arg.get2(), arg.get3(), arg.get4());
	}
}
