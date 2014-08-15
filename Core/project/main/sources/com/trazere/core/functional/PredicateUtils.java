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
package com.trazere.core.functional;

/**
 * The {@link PredicateUtils} class provides various utilities regarding predicates.
 */
public class PredicateUtils {
	// TODO: rename to ???
	/**
	 * Transforms the given predicate using the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param <TA> Type of the transformed arguments.
	 * @param predicate Predicate to transform.
	 * @param function Function to use to transform the arguments.
	 * @return The built predicate.
	 */
	public static <A, TA> Predicate<A> map(final Predicate<? super TA> predicate, final Function<? super A, ? extends TA> function) {
		assert null != predicate;
		assert null != function;
		
		return arg -> predicate.evaluate(function.evaluate(arg));
	}
	
	private PredicateUtils() {
		// Prevent instantiation.
	}
}
