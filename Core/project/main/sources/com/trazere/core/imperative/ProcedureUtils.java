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
import com.trazere.core.util.Tuple3;
import com.trazere.core.util.Tuple4;
import com.trazere.core.util.Tuple5;

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
	
	/**
	 * Curries the given procedure which takes triples of elements into a procedure that takes three arguments.
	 *
	 * @param <A1> Type of the first element of the argument triples.
	 * @param <A2> Type of the second element of the argument triples.
	 * @param <A3> Type of the third element of the argument triples.
	 * @param procedure Procedure to curry.
	 * @return The built procedure.
	 * @since 2.0
	 */
	public static <A1, A2, A3> Procedure3<A1, A2, A3> curry3(final Procedure<? super Tuple3<A1, A2, A3>> procedure) {
		assert null != procedure;
		
		return (arg1, arg2, arg3) -> procedure.execute(new Tuple3<>(arg1, arg2, arg3));
	}
	
	/**
	 * Curries the given procedure which takes quadruples of elements into a procedure that takes four arguments.
	 *
	 * @param <A1> Type of the first element of the argument quadruples.
	 * @param <A2> Type of the second element of the argument quadruples.
	 * @param <A3> Type of the third element of the argument quadruples.
	 * @param <A4> Type of the fourth element of the argument quadruples.
	 * @param procedure Procedure to curry.
	 * @return The built procedure.
	 * @since 2.0
	 */
	public static <A1, A2, A3, A4> Procedure4<A1, A2, A3, A4> curry4(final Procedure<? super Tuple4<A1, A2, A3, A4>> procedure) {
		assert null != procedure;
		
		return (arg1, arg2, arg3, arg4) -> procedure.execute(new Tuple4<>(arg1, arg2, arg3, arg4));
	}
	
	/**
	 * Curries the given procedure which takes quintuples of elements into a procedure that takes five arguments.
	 *
	 * @param <A1> Type of the first element of the argument quintuples.
	 * @param <A2> Type of the second element of the argument quintuples.
	 * @param <A3> Type of the third element of the argument quintuples.
	 * @param <A4> Type of the fourth element of the argument quintuples.
	 * @param <A5> Type of the fifth element of the argument quintuples.
	 * @param procedure Procedure to curry.
	 * @return The built procedure.
	 * @since 2.0
	 */
	public static <A1, A2, A3, A4, A5> Procedure5<A1, A2, A3, A4, A5> curry5(final Procedure<? super Tuple5<A1, A2, A3, A4, A5>> procedure) {
		assert null != procedure;
		
		return (arg1, arg2, arg3, arg4, arg5) -> procedure.execute(new Tuple5<>(arg1, arg2, arg3, arg4, arg5));
	}
	
	private ProcedureUtils() {
		// Prevent instantiation.
	}
}
