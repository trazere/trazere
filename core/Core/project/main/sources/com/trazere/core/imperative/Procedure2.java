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
package com.trazere.core.imperative;

/**
 * The {@link Procedure2} interface defines uncurried procedures (functions which return no results) that take two arguments.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 */
@FunctionalInterface
public interface Procedure2<A1, A2> {
	/**
	 * Executes the receiver procedure with the given arguments.
	 * 
	 * @param arg1 First argument to execute the procedure with.
	 * @param arg2 Second argument to execute the procedure with.
	 */
	void execute(A1 arg1, A2 arg2);
}