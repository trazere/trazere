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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Function3;
import com.trazere.core.functional.Function4;
import com.trazere.core.functional.Function5;

/**
 * The {@link TupleFunctions} class provides various functions related to tuples.
 */
public class TupleFunctions {
	/**
	 * Builds a function that builds 1-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1> Function<E1, Tuple1<E1>> tuple1() {
		return (Function<E1, Tuple1<E1>>) TUPLE1;
	}
	
	private static final Function<?, ? extends Tuple1<?>> TUPLE1 = Tuple1<Object>::new;
	
	/**
	 * Builds a function that builds 2-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2> Function2<E1, E2, Tuple2<E1, E2>> tuple2() {
		return (Function2<E1, E2, Tuple2<E1, E2>>) TUPLE2;
	}
	
	private static final Function2<?, ?, ? extends Tuple2<?, ?>> TUPLE2 = Tuple2<Object, Object>::new;
	
	/**
	 * Builds a function that builds 3-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2, E3> Function3<E1, E2, E3, Tuple3<E1, E2, E3>> tuple3() {
		return (Function3<E1, E2, E3, Tuple3<E1, E2, E3>>) TUPLE3;
	}
	
	private static final Function3<?, ?, ?, ? extends Tuple3<?, ?, ?>> TUPLE3 = Tuple3<Object, Object, Object>::new;
	
	/**
	 * Builds a function that builds 4-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2, E3, E4> Function4<E1, E2, E3, E4, Tuple4<E1, E2, E3, E4>> tuple4() {
		return (Function4<E1, E2, E3, E4, Tuple4<E1, E2, E3, E4>>) TUPLE4;
	}
	
	private static final Function4<?, ?, ?, ?, ? extends Tuple4<?, ?, ?, ?>> TUPLE4 = Tuple4<Object, Object, Object, Object>::new;
	
	/**
	 * Builds a function that builds 5-tuples.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E1, E2, E3, E4, E5> Function5<E1, E2, E3, E4, E5, Tuple5<E1, E2, E3, E4, E5>> tuple5() {
		return (Function5<E1, E2, E3, E4, E5, Tuple5<E1, E2, E3, E4, E5>>) TUPLE5;
	}
	
	private static final Function5<?, ?, ?, ?, ?, ? extends Tuple5<?, ?, ?, ?, ?>> TUPLE5 = Tuple5<Object, Object, Object, Object, Object>::new;
	
	private TupleFunctions() {
		// Prevent instantiation.
	}
}
