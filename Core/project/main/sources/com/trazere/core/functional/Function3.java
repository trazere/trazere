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

import com.trazere.core.util.Tuple3;

/**
 * The {@link Function3} interface defines uncurried functions that take three arguments.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 * @param <A3> Type of the third arguments.
 * @param <R> Type of the results.
 * @since 2.0
 */
@FunctionalInterface
public interface Function3<A1, A2, A3, R> {
	/**
	 * Evaluates this function with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the function with.
	 * @param arg2 Second argument to evaluate the function with.
	 * @param arg3 Third argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @since 2.0
	 */
	R evaluate(A1 arg1, A2 arg2, A3 arg3);
	
	/**
	 * Evaluates this function with the given arguments in a thread safe way.
	 * 
	 * @param arg1 First argument to evaluate the function with.
	 * @param arg2 Second argument to evaluate the function with.
	 * @param arg3 Third argument to evaluate the function with.
	 * @return The result of the function evaluation.
	 * @see #evaluate(Object, Object, Object)
	 * @since 2.0
	 */
	default R synchronizedEvaluate(final A1 arg1, final A2 arg2, final A3 arg3) {
		synchronized (this) {
			return evaluate(arg1, arg2, arg3);
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
	default <TR> Function3<A1, A2, A3, TR> map(final Function<? super R, ? extends TR> function) {
		assert null != function;
		
		final Function3<A1, A2, A3, R> self = this;
		return (arg1, arg2, arg3) -> function.evaluate(self.evaluate(arg1, arg2, arg3));
	}
	
	/**
	 * Builds a synchronized view of this function.
	 * 
	 * @return The built function.
	 * @see #synchronizedEvaluate(Object, Object, Object)
	 * @since 2.0
	 */
	default Function3<A1, A2, A3, R> synchronized_() {
		return this::synchronizedEvaluate;
	}
	
	/**
	 * Gets an uncurried view of this function (as a function that takes triples of elements).
	 *
	 * @return The built function.
	 * @since 2.0
	 */
	default Function<Tuple3<A1, A2, A3>, R> uncurried() {
		final Function3<A1, A2, A3, R> self = this;
		return arg -> self.evaluate(arg.get1(), arg.get2(), arg.get3());
	}
}
