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
	 * Evaluates this predicate with the given argument in a thread safe way.
	 * 
	 * @param arg Argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 2.0
	 */
	default boolean synchronizedEvaluate(final A arg) {
		synchronized (this) {
			return evaluate(arg);
		}
	}
	
	/**
	 * Composes this predicate with the given function.
	 * <p>
	 * TODO: detail function composition
	 * 
	 * @param <CA> Type of the composition arguments.
	 * @param function Function to use to transform the arguments.
	 * @return The composition predicate.
	 * @since 2.0
	 */
	default <CA> Predicate<CA> compose(final Function<? super CA, ? extends A> function) {
		assert null != function;
		
		final Predicate<A> self = this;
		return arg -> self.evaluate(function.evaluate(arg));
	}
	
	// TODO: memoized
	// TODO: resettable
	
	/**
	 * Builds a synchronized view of this predicate.
	 * 
	 * @return The built predicate.
	 * @see #synchronizedEvaluate(Object)
	 * @since 2.0
	 */
	default Predicate<A> synchronized_() {
		return this::synchronizedEvaluate;
	}
	
	/**
	 * Lifts this predicate as a Java 8 predicate.
	 * 
	 * @return The built Java 8 predicate.
	 * @since 2.0
	 */
	default java.util.function.Predicate<A> toPredicate() {
		return this::evaluate;
	}
}
