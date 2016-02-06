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

/**
 * The {@link Predicate} interface defines functions that result to booleans.
 * <p>
 * TODO: compare to first order logic predicates
 * 
 * @param <A> Type of the arguments.
 * @since 2.0
 */
@FunctionalInterface
public interface Predicate<A> {
	/**
	 * Evaluates this predicate with the given argument.
	 * 
	 * @param arg Argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 2.0
	 */
	boolean evaluate(A arg);
	
	/**
	 * Composes this predicate with the given function.
	 * 
	 * @param <NA> Type of the composition arguments.
	 * @param function Function to use to transform the arguments.
	 * @return The built predicate.
	 * @since 2.0
	 */
	default <NA> Predicate<NA> compose(final Function<? super NA, ? extends A> function) {
		assert null != function;
		
		final Predicate<A> self = this;
		return arg -> self.evaluate(function.evaluate(arg));
	}
	
	// TODO: memoized
	// TODO: resettable
	// TODO: synchronized_
	
	/**
	 * Lifts this predicate as a Java 8 predicate.
	 * 
	 * @return The built Java 8 predicate.
	 * @since 2.0
	 */
	default java.util.function.Predicate<A> toPredicate() {
		final Predicate<A> self = this;
		return t -> self.evaluate(t);
	}
}
