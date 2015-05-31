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
 * @since 1.0
 */
@FunctionalInterface
public interface Predicate<A> {
	/**
	 * Evaluates this predicate with the given argument.
	 * 
	 * @param arg Argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 * @since 1.0
	 */
	boolean evaluate(A arg);
}
