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
 * The {@link Function4} interface defines uncurried functions that take four arguments.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 * @param <A3> Type of the third arguments.
 * @param <A4> Type of the fourth arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
@FunctionalInterface
public interface Function4<A1, A2, A3, A4, R> {
	/**
	 * Evaluates this function with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the function with.
	 * @param arg2 Second argument to evaluate the function with.
	 * @param arg3 Third argument to evaluate the function with.
	 * @param arg4 Fourth argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 2.0
	 */
	R evaluate(A1 arg1, A2 arg2, A3 arg3, A4 arg4);
	
	/**
	 * Evaluates this function with the given arguments in a thread safe way.
	 * 
	 * @param arg1 First argument to evaluate the function with.
	 * @param arg2 Second argument to evaluate the function with.
	 * @param arg3 Third argument to evaluate the function with.
	 * @param arg4 Fourth argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @see #evaluate(Object, Object, Object, Object)
	 * @since 2.0
	 */
	default R synchronizedEvaluate(final A1 arg1, final A2 arg2, final A3 arg3, final A4 arg4) {
		synchronized (this) {
			return evaluate(arg1, arg2, arg3, arg4);
		}
	}
	
	/**
	 * Transforms the results of this function using the given function.
	 *
	 * @param <TR> Type of the transformed results.
	 * @param function Function to use to transform the results.
	 * @return A function evaluating to the transformed results.
	 * @since 2.0
	 */
	default <TR> Function4<A1, A2, A3, A4, TR> map(final Function<? super R, ? extends TR> function) {
		assert null != function;
		
		final Function4<A1, A2, A3, A4, R> self = this;
		return (arg1, arg2, arg3, arg4) -> function.evaluate(self.evaluate(arg1, arg2, arg3, arg4));
	}
	
	/**
	 * Builds a synchronized view of this function.
	 * 
	 * @return The built function.
	 * @see #synchronizedEvaluate(Object, Object, Object, Object)
	 * @since 2.0
	 */
	default Function4<A1, A2, A3, A4, R> synchronized_() {
		final Function4<A1, A2, A3, A4, R> self = this;
		return (arg1, arg2, arg3, arg4) -> self.synchronizedEvaluate(arg1, arg2, arg3, arg4);
	}
	
	/**
	 * Gets an uncurried view of this function (as a function that takes quadruples of elements).
	 *
	 * @return The built function.
	 * @since 2.0
	 */
	default Function<Tuple4<A1, A2, A3, A4>, R> uncurried() {
		final Function4<A1, A2, A3, A4, R> self = this;
		return arg -> self.evaluate(arg.get1(), arg.get2(), arg.get3(), arg.get4());
	}
}
