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
 * The {@link Predicate2} interface defines uncurried functions that take two arguments and result to booleans.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 */
public interface Predicate2<A1, A2> {
	/**
	 * Evaluates the receiver predicate with the given arguments.
	 * 
	 * @param arg1 First argument to evaluate the predicate with.
	 * @param arg2 Second argument to evaluate the predicate with.
	 * @return The result of the predicate evaluation.
	 */
	boolean evaluate(A1 arg1, A2 arg2);
}
