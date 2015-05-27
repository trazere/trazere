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

import com.trazere.core.functional.Predicate;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link ImperativePredicates} class provides various factories of {@link Predicate predicates} with imperative features.
 * 
 * @see Predicate
 */
public class ImperativePredicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for each value only the first time they are tested.
	 *
	 * @param <A> Type of the arguments.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> normalizer() {
		final Set<A> visited = new HashSet<>();
		return arg -> visited.add(arg);
	}
	
	private ImperativePredicates() {
		// Prevent instantiation.
	}
}