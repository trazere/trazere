/*
 *  Copyright 2006-2008 Julien Dufour
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
package com.trazere.util.function;

/**
 * The {@link Predicate2} interface defines two arguments predicate functions.
 * 
 * @param <T1> Type of the first argument values.
 * @param <T2> Type of the second argument values.
 * @param <E> Type of the exceptions.
 */
public interface Predicate2<T1, T2, E extends Exception> {
	/**
	 * Evaluate the receiver predicate with the given argument values.
	 * 
	 * @param value1 First argument value.
	 * @param value2 Second argument value.
	 * @return The result of the predicate evaluation.
	 * @throws E When the predicate evaluation fails.
	 */
	public boolean evaluate(final T1 value1, final T2 value2)
	throws E;
}
