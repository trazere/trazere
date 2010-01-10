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
 * The {@link Procedure1} interface defines one argument procedures (one argument functions which return no results).
 * 
 * @param <T> Type of the argument values.
 * @param <X> Type of the exceptions.
 */
public interface Procedure1<T, X extends Exception> {
	/**
	 * Execute the receiver procedure with the given argument value.
	 * 
	 * @param value Argument value.
	 * @throws X When the procedure execution fails.
	 */
	public void execute(final T value)
	throws X;
}
