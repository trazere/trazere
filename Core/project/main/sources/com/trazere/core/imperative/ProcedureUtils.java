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

import com.trazere.core.util.Tuple2;

/**
 * The {@link ProcedureUtils} class provides various utilities regarding {@link Procedure procedures}.
 * 
 * @see Procedure
 * @see Procedure2
 * @since 2.0
 */
public class ProcedureUtils {
	/**
	 * Curries the given procedure which takes pairs of elements into a procedure that takes two arguments.
	 *
	 * @param <A1> Type of the first element of the argument pairs.
	 * @param <A2> Type of the second element of the argument pairs.
	 * @param procedure Procedure to curry.
	 * @return The built procedure.
	 * @since 2.0
	 */
	public static <A1, A2> Procedure2<A1, A2> curry2(final Procedure<? super Tuple2<A1, A2>> procedure) {
		assert null != procedure;
		
		return (arg1, arg2) -> procedure.execute(new Tuple2<>(arg1, arg2));
	}
	
	private ProcedureUtils() {
		// Prevent instantiation.
	}
}
