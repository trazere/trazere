/*
 *  Copyright 2006 Julien Dufour
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
 * The <code>Procedure</code> interface defines one argument procedures (one argument functions which return no results).
 * 
 * @param <T> Type of the argument values.
 */
public interface Procedure<T> {
	/**
	 * Process the given argument value.
	 * 
	 * @param value Argument value to process.
	 * @throws ApplicationException When the function application fails.
	 */
	public void apply(final T value)
	throws ApplicationException;
}