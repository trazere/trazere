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
package com.trazere.core.functional;

import com.trazere.core.util.Tuple2;
import com.trazere.core.util.Tuple3;
import com.trazere.core.util.Tuple4;
import com.trazere.core.util.Tuple5;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * The {@link FunctionUtils} class provides various utilities regarding {@link Function functions}.
 * 
 * @see Function
 * @see ResettableFunction
 * @see Function2
 * @see Function3
 * @since 2.0
 */
public class FunctionUtils {
	/**
	 * Curries the given function which takes pairs of elements into a function that takes two arguments.
	 *
	 * @param <A1> Type of the first element of the argument pairs.
	 * @param <A2> Type of the second element of the argument pairs.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, R> Function2<A1, A2, R> curry2(final Function<? super Tuple2<A1, A2>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2) -> function.evaluate(new Tuple2<>(arg1, arg2));
	}
	
	/**
	 * Curries the given function which takes triples of elements into a function that takes three arguments.
	 *
	 * @param <A1> Type of the first element of the argument triples.
	 * @param <A2> Type of the second element of the argument triples.
	 * @param <A3> Type of the third element of the argument triples.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, A3, R> Function3<A1, A2, A3, R> curry3(final Function<? super Tuple3<A1, A2, A3>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2, arg3) -> function.evaluate(new Tuple3<>(arg1, arg2, arg3));
	}
	
	/**
	 * Curries the given function which takes quadruples of elements into a function that takes four arguments.
	 *
	 * @param <A1> Type of the first element of the argument quadruples.
	 * @param <A2> Type of the second element of the argument quadruples.
	 * @param <A3> Type of the third element of the argument quadruples.
	 * @param <A4> Type of the fourth element of the argument quadruples.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, A3, A4, R> Function4<A1, A2, A3, A4, R> curry4(final Function<? super Tuple4<A1, A2, A3, A4>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2, arg3, arg4) -> function.evaluate(new Tuple4<>(arg1, arg2, arg3, arg4));
	}
	
	/**
	 * Curries the given function which takes quintuples of elements into a function that takes five arguments.
	 *
	 * @param <A1> Type of the first element of the argument quintuples.
	 * @param <A2> Type of the second element of the argument quintuples.
	 * @param <A3> Type of the third element of the argument quintuples.
	 * @param <A4> Type of the fourth element of the argument quintuples.
	 * @param <A5> Type of the fifth element of the argument quintuples.
	 * @param <R> Type of the results.
	 * @param function Function to curry.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <A1, A2, A3, A4, A5, R> Function5<A1, A2, A3, A4, A5, R> curry5(final Function<? super Tuple5<A1, A2, A3, A4, A5>, ? extends R> function) {
		assert null != function;
		
		return (arg1, arg2, arg3, arg4, arg5) -> function.evaluate(new Tuple5<>(arg1, arg2, arg3, arg4, arg5));
	}
	
	/**
	 * Lifts the given function as a Java 8 unary operator.
	 * 
	 * @param <V> Type of the arguments and results.
	 * @param function Function to lift.
	 * @return The built unary operator.
	 * @since 2.0
	 */
	public static <V> UnaryOperator<V> toUnaryOperator(final Function<? super V, ? extends V> function) {
		assert null != function;
		
		return function::evaluate;
	}
	
	/**
	 * Lifts the given two arguments function as a Java 8 binary operator.
	 * 
	 * @param <V> Type of the arguments and results.
	 * @param function Function to lift.
	 * @return The built binary operator.
	 * @since 2.0
	 */
	public static <V> BinaryOperator<V> toBinaryOperator(final Function2<? super V, ? super V, ? extends V> function) {
		assert null != function;
		
		return function::evaluate;
	}
	
	private FunctionUtils() {
		// Prevent instantiation.
	}
}
