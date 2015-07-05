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

import java.util.function.BiPredicate;

/**
 * The {@link PredicateUtils} class provides various utilities regarding {@link Predicate predicates}.
 * 
 * @see Predicate
 * @since 1.0
 */
public class PredicateUtils {
	// TODO: rename to something else (map over arg vs map over result, covariant vs contravariant)
	/**
	 * Transforms the given predicate using the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <TA> Type of the transformed arguments.
	 * @param predicate Predicate to transform.
	 * @param function Function to use to transform the arguments.
	 * @return The built predicate.
	 * @since 1.0
	 */
	public static <A, TA> Predicate<A> map(final Predicate<? super TA> predicate, final Function<? super A, ? extends TA> function) {
		assert null != predicate;
		assert null != function;
		
		return arg -> predicate.evaluate(function.evaluate(arg));
	}
	
	// TODO: memoized
	// TODO: resettable
	// TODO: synchronized_
	
	/**
	 * Builds a Java 8 predicate that lifts the given predicate.
	 * 
	 * @param <A> Type of the arguments.
	 * @param predicate Predicate to lift.
	 * @return The built Java 8 predicate.
	 * @since 1.0
	 */
	public static <A> java.util.function.Predicate<A> toPredicate(final Predicate<? super A> predicate) {
		assert null != predicate;
		
		return t -> predicate.evaluate(t);
	}
	
	/**
	 * Builds a bi-predicate that lifts the given two arguments predicate.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicate Predicate to lift.
	 * @return The built bi-predicate.
	 * @since 1.0
	 */
	public static <A1, A2> BiPredicate<A1, A2> toBiPredicate(final Predicate2<? super A1, ? super A2> predicate) {
		assert null != predicate;
		
		return (t, u) -> predicate.evaluate(t, u);
	}
	
	private PredicateUtils() {
		// Prevent instantiation.
	}
}
