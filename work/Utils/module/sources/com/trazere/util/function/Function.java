/*
 *  Copyright 2008 Julien Dufour
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
 * The {@link Function} interface defines one argument functions.
 * 
 * @param <T> Type of the argument values.
 * @param <R> Type of the result values.
 */
public interface Function<T, R> {
	/**
	 * Apply the receiver function to the given argument value.
	 * 
	 * @param value Argument value of the function.
	 * @return The result of the function application.
	 * @throws ApplicationException When the function application fails.
	 */
	public R apply(final T value)
	throws ApplicationException;
}
