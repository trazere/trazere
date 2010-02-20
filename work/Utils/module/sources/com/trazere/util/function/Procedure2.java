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
 * The {@link Procedure2} interface defines two arguments procedures (two arguments functions which return no results).
 * 
 * @param <T1> Type of the first argument values.
 * @param <T2> Type of the second argument values.
 * @param <X> Type of the exceptions.
 */
public interface Procedure2<T1, T2, X extends Exception> {
	/**
	 * Execute the receiver procedure with the given argument values.
	 * 
	 * @param value1 First argument value.
	 * @param value2 Second argument value.
	 * @throws X When the procedure execution fails.
	 */
	public void execute(final T1 value1, final T2 value2)
	throws X;
}