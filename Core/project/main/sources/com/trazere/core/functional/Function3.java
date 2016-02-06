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

import com.trazere.core.util.Maybe;

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
	 * Gets a view of this function that filters the results using the given filter.
	 * 
	 * @param filter Predicate to use to filter the results.
	 * @return The built function.
	 * @since 2.0
	 */
	default Function3<A1, A2, A3, Maybe<R>> filtered(final Predicate<? super R> filter) {
		assert null != filter;
		
		final Function3<A1, A2, A3, R> self = this;
		return (arg1, arg2, arg3) -> Maybe.some(self.evaluate(arg1, arg2, arg3)).filter(filter);
	}
	
	/**
	 * Gets a view of this function that transforms the results using the given function.
	 *
	 * @param
	 * 		<TR>
	 *        Type of the transformed results.
	 * @param function Function to use to transform the results.
	 * @return The built function.
	 * @since 2.0
	 */
	default <TR> Function3<A1, A2, A3, TR> mapped(final Function<? super R, ? extends TR> function) {
		assert null != function;
		
		final Function3<A1, A2, A3, R> self = this;
		return (arg1, arg2, arg3) -> function.evaluate(self.evaluate(arg1, arg2, arg3));
	}
	
	// TODO: add uncurry
}
