/*
 *  Copyright 2006-2010 Julien Dufour
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
 * The {@link Function1} interface defines one argument functions.
 * 
 * @param <T> Type of the argument values.
 * @param <R> Type of the result values.
 * @param <X> Type of the exceptions.
 */
public interface Function1<T, R, X extends Exception> {
	/**
	 * Evaluate the receiver function with the given argument value.
	 * 
	 * @param value Argument value.
	 * @return The result of the function evaluation.
	 * @throws X When the function evaluation fails.
	 */
	public R evaluate(final T value)
	throws X;
}
