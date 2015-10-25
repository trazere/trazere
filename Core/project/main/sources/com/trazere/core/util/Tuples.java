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
package com.trazere.core.util;

/**
 * The {@link Tuples} class provides various factories of tuples.
 * 
 * @see Tuple1
 * @see Tuple2
 * @see Tuple3
 * @see Tuple4
 * @see Tuple5
 * @see Tuple6
 * @see Tuple7
 * @see Tuple8
 * @see Tuple9
 * @see Tuple10
 * @since 2.0
 */
public class Tuples {
	/**
	 * Builds a 1-tuple with the given element.
	 * 
	 * @param <E1> Type of the first element.
	 * @param e1 First element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1> Tuple1<E1> tuple1(final E1 e1) {
		return new Tuple1<>(e1);
	}
	
	/**
	 * Builds a 2-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2> Tuple2<E1, E2> tuple2(final E1 e1, final E2 e2) {
		return new Tuple2<>(e1, e2);
	}
	
	/**
	 * Builds a 3-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3> Tuple3<E1, E2, E3> tuple3(final E1 e1, final E2 e2, final E3 e3) {
		return new Tuple3<>(e1, e2, e3);
	}
	
	/**
	 * Builds a 4-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4> Tuple4<E1, E2, E3, E4> tuple4(final E1 e1, final E2 e2, final E3 e3, final E4 e4) {
		return new Tuple4<>(e1, e2, e3, e4);
	}
	
	/**
	 * Builds a 5-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4, E5> Tuple5<E1, E2, E3, E4, E5> tuple5(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5) {
		return new Tuple5<>(e1, e2, e3, e4, e5);
	}
	
	/**
	 * Builds a 6-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4, E5, E6> Tuple6<E1, E2, E3, E4, E5, E6> tuple6(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6) {
		return new Tuple6<>(e1, e2, e3, e4, e5, e6);
	}
	
	/**
	 * Builds a 7-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4, E5, E6, E7> Tuple7<E1, E2, E3, E4, E5, E6, E7> tuple7(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7) {
		return new Tuple7<>(e1, e2, e3, e4, e5, e6, e7);
	}
	
	/**
	 * Builds a 8-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8> Tuple8<E1, E2, E3, E4, E5, E6, E7, E8> tuple8(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8) {
		return new Tuple8<>(e1, e2, e3, e4, e5, e6, e7, e8);
	}
	
	/**
	 * Builds a 9-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param <E9> Type of the ninth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 * @param e9 Ninth element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8, E9> Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, E9> tuple9(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8, final E9 e9) {
		return new Tuple9<>(e1, e2, e3, e4, e5, e6, e7, e8, e9);
	}
	
	/**
	 * Builds a 10-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param <E9> Type of the ninth element.
	 * @param <E10> Type of the tenth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 * @param e9 Ninth element.
	 * @param e10 Tenth element.
	 * @return The built tuple.
	 * @since 2.0
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8, E9, E10> Tuple10<E1, E2, E3, E4, E5, E6, E7, E8, E9, E10> tuple10(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8, final E9 e9, final E10 e10) {
		return new Tuple10<>(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
	}
	
	private Tuples() {
		// Prevent instantiation.
	}
}
