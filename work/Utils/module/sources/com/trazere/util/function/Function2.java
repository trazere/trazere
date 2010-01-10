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
 * The {@link Function2} interface defines two arguments functions.
 * 
 * @param <T1> Type of the first argument values.
 * @param <T2> Type of the second argument values.
 * @param <R> Type of the result values.
 * @param <X> Type of the exceptions.
 */
public interface Function2<T1, T2, R, X extends Exception> {
	/**
	 * Evaluate the receiver function with the given argument values.
	 * 
	 * @param value1 First argument value.
	 * @param value2 Second argument value.
	 * @return The result of the function evaluation.
	 * @throws X When the function evaluation fails.
	 */
	public R evaluate(final T1 value1, final T2 value2)
	throws X;
}
