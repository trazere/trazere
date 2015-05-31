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
package com.trazere.core.imperative;

/**
 * The {@link Procedure5} interface defines uncurried procedures (functions which return no results) that take five arguments.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 * @param <A3> Type of the third arguments.
 * @param <A4> Type of the fourth arguments.
 * @param <A5> Type of the fifth arguments.
 * @since 1.0
 */
@FunctionalInterface
public interface Procedure5<A1, A2, A3, A4, A5> {
	/**
	 * Executes this procedure with the given arguments.
	 * 
	 * @param arg1 First argument to execute the procedure with.
	 * @param arg2 Second argument to execute the procedure with.
	 * @param arg3 Third argument to execute the procedure with.
	 * @param arg4 Fourth argument to execute the procedure with.
	 * @param arg5 Fifth argument to execute the procedure with.
	 * @since 1.0
	 */
	void execute(A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5);
}
