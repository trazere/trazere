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
package com.trazere.util.function;

/**
 * The {@link Predicate1} interface defines one argument predicate functions.
 * 
 * @param <T> Type of the argument values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.functional.Predicate}.
 */
@Deprecated
public interface Predicate1<T, X extends Exception> {
	/**
	 * Evaluates the receiver predicate with the given argument value.
	 * 
	 * @param value The argument value.
	 * @return The result of the predicate evaluation.
	 * @throws X When the predicate evaluation fails.
	 * @deprecated Use {@link com.trazere.core.functional.Predicate#evaluate(Object)}.
	 */
	@Deprecated
	public boolean evaluate(final T value)
	throws X;
}
