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

/**
 * The {@link PredicateUtils} class provides various utilities regarding {@link Predicate predicates}.
 * 
 * @see Predicate
 * @since 2.0
 */
public class PredicateUtils {
	/**
	 * Curries the given predicate which takes pairs of elements into a predicate that takes two arguments.
	 *
	 * @param <A1> Type of the first element of the argument pairs.
	 * @param <A2> Type of the second element of the argument pairs.
	 * @param predicate Predicate to curry.
	 * @return The built predicate.
	 * @since 2.0
	 */
	public static <A1, A2> Predicate2<A1, A2> curry2(final Predicate<? super Tuple2<A1, A2>> predicate) {
		assert null != predicate;
		
		return (arg1, arg2) -> predicate.evaluate(new Tuple2<>(arg1, arg2));
	}
	
	/**
	 * Curries the given predicate which takes triples of elements into a predicate that takes three arguments.
	 *
	 * @param <A1> Type of the first element of the argument triples.
	 * @param <A2> Type of the second element of the argument triples.
	 * @param <A3> Type of the third element of the argument triples.
	 * @param predicate Predicate to curry.
	 * @return The built predicate.
	 * @since 2.0
	 */
	public static <A1, A2, A3> Predicate3<A1, A2, A3> curry3(final Predicate<? super Tuple3<A1, A2, A3>> predicate) {
		assert null != predicate;
		
		return (arg1, arg2, arg3) -> predicate.evaluate(new Tuple3<>(arg1, arg2, arg3));
	}
	
	/**
	 * Curries the given predicate which takes quadruples of elements into a predicate that takes four arguments.
	 *
	 * @param <A1> Type of the first element of the argument quadruples.
	 * @param <A2> Type of the second element of the argument quadruples.
	 * @param <A3> Type of the third element of the argument quadruples.
	 * @param <A4> Type of the fourth element of the argument quadruples.
	 * @param predicate Predicate to curry.
	 * @return The built predicate.
	 * @since 2.0
	 */
	public static <A1, A2, A3, A4> Predicate4<A1, A2, A3, A4> curry4(final Predicate<? super Tuple4<A1, A2, A3, A4>> predicate) {
		assert null != predicate;
		
		return (arg1, arg2, arg3, arg4) -> predicate.evaluate(new Tuple4<>(arg1, arg2, arg3, arg4));
	}
	
	/**
	 * Curries the given predicate which takes quintuples of elements into a predicate that takes five arguments.
	 *
	 * @param <A1> Type of the first element of the argument quintuples.
	 * @param <A2> Type of the second element of the argument quintuples.
	 * @param <A3> Type of the third element of the argument quintuples.
	 * @param <A4> Type of the fourth element of the argument quintuples.
	 * @param <A5> Type of the fifth element of the argument quintuples.
	 * @param predicate Predicate to curry.
	 * @return The built predicate.
	 * @since 2.0
	 */
	public static <A1, A2, A3, A4, A5> Predicate5<A1, A2, A3, A4, A5> curry5(final Predicate<? super Tuple5<A1, A2, A3, A4, A5>> predicate) {
		assert null != predicate;
		
		return (arg1, arg2, arg3, arg4, arg5) -> predicate.evaluate(new Tuple5<>(arg1, arg2, arg3, arg4, arg5));
	}
	
	private PredicateUtils() {
		// Prevent instantiation.
	}
}
