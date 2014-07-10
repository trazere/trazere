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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The {@link TupleComparators} class provides various comparators related to tuples.
 */
public class TupleComparators {
	/**
	 * Builds a comparator of tuples according to their first element.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param comparator Comparator of the first elements.
	 * @return The built comparator.
	 */
	public static <E1> Comparator<Tuple1<E1>> element1(final Comparator<? super E1> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get1());
	}
	
	/**
	 * Builds a comparator of tuples according to their second element.
	 * 
	 * @param <E2> Type of the seconds elements.
	 * @param comparator Comparator of the second elements.
	 * @return The built comparator.
	 */
	public static <E2> Comparator<Tuple2<?, E2>> element2(final Comparator<? super E2> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get2());
	}
	
	/**
	 * Builds a comparator of tuples according to their third element.
	 * 
	 * @param <E3> Type of the third elements.
	 * @param comparator Comparator of the third elements.
	 * @return The built comparator.
	 */
	public static <E3> Comparator<Tuple3<?, ?, E3>> element3(final Comparator<? super E3> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get3());
	}
	
	/**
	 * Builds a comparator of tuples according to their fourth element.
	 * 
	 * @param <E4> Type of the fourth elements.
	 * @param comparator Comparator of the fourth elements.
	 * @return The built comparator.
	 */
	public static <E4> Comparator<Tuple4<?, ?, ?, E4>> element4(final Comparator<? super E4> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get4());
	}
	
	/**
	 * Builds a comparator of tuples according to their fifth element.
	 * 
	 * @param <E5> Type of the fifth elements.
	 * @param comparator Comparator of the fifth elements.
	 * @return The built comparator.
	 */
	public static <E5> Comparator<Tuple5<?, ?, ?, ?, E5>> element5(final Comparator<? super E5> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get5());
	}
	
	/**
	 * Builds a comparator of tuples according to their sixth element.
	 * 
	 * @param <E6> Type of the sixth elements.
	 * @param comparator Comparator of the sixth elements.
	 * @return The built comparator.
	 */
	public static <E6> Comparator<Tuple6<?, ?, ?, ?, ?, E6>> element6(final Comparator<? super E6> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get6());
	}
	
	/**
	 * Builds a comparator of tuples according to their seventh element.
	 * 
	 * @param <E7> Type of the seventh elements.
	 * @param comparator Comparator of the seventh elements.
	 * @return The built comparator.
	 */
	public static <E7> Comparator<Tuple7<?, ?, ?, ?, ?, ?, E7>> element7(final Comparator<? super E7> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get7());
	}
	
	/**
	 * Builds a comparator of tuples according to their eighth element.
	 * 
	 * @param <E8> Type of the eighth elements.
	 * @param comparator Comparator of the eighth elements.
	 * @return The built comparator.
	 */
	public static <E8> Comparator<Tuple8<?, ?, ?, ?, ?, ?, ?, E8>> element8(final Comparator<? super E8> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get8());
	}
	
	/**
	 * Builds a comparator of tuples according to their ninth element.
	 * 
	 * @param <E9> Type of the ninth elements.
	 * @param comparator Comparator of the ninth elements.
	 * @return The built comparator.
	 */
	public static <E9> Comparator<Tuple9<?, ?, ?, ?, ?, ?, ?, ?, E9>> element9(final Comparator<? super E9> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get9());
	}
	
	/**
	 * Builds a comparator of tuples according to their tenth element.
	 * 
	 * @param <E10> Type of the tenth elements.
	 * @param comparator Comparator of the tenth elements.
	 * @return The built comparator.
	 */
	public static <E10> Comparator<Tuple10<?, ?, ?, ?, ?, ?, ?, ?, ?, E10>> element10(final Comparator<? super E10> comparator) {
		return ComparatorUtils.map(comparator, TupleFunctions.get10());
	}
	
	/**
	 * Builds a comparator of 1-tuples according using the given element comparator.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param comparator1 Comparator of the first elements.
	 * @return The built comparator.
	 */
	public static <E1> Comparator<Tuple1<E1>> tuple1(final Comparator<? super E1> comparator1) {
		return element1(comparator1);
	}
	
	/**
	 * Builds a comparator of 2-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @return The built comparator.
	 */
	public static <E1, E2> Comparator<Tuple2<E1, E2>> tuple2(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2) {
		final List<Comparator<? super Tuple2<E1, E2>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 3-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3> Comparator<Tuple3<E1, E2, E3>> tuple3(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3) {
		final List<Comparator<? super Tuple3<E1, E2, E3>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 4-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4> Comparator<Tuple4<E1, E2, E3, E4>> tuple4(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4) {
		final List<Comparator<? super Tuple4<E1, E2, E3, E4>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 5-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @param comparator5 Comparator of the fifth elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4, E5> Comparator<Tuple5<E1, E2, E3, E4, E5>> tuple5(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4, final Comparator<? super E5> comparator5) {
		final List<Comparator<? super Tuple5<E1, E2, E3, E4, E5>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		comparators.add(element5(comparator5));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 6-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @param comparator5 Comparator of the fifth elements.
	 * @param comparator6 Comparator of the sixth elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4, E5, E6> Comparator<Tuple6<E1, E2, E3, E4, E5, E6>> tuple6(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4, final Comparator<? super E5> comparator5, final Comparator<? super E6> comparator6) {
		final List<Comparator<? super Tuple6<E1, E2, E3, E4, E5, E6>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		comparators.add(element5(comparator5));
		comparators.add(element6(comparator6));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 7-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @param comparator5 Comparator of the fifth elements.
	 * @param comparator6 Comparator of the sixth elements.
	 * @param comparator7 Comparator of the seventh elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4, E5, E6, E7> Comparator<Tuple7<E1, E2, E3, E4, E5, E6, E7>> tuple7(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4, final Comparator<? super E5> comparator5, final Comparator<? super E6> comparator6, final Comparator<? super E7> comparator7) {
		final List<Comparator<? super Tuple7<E1, E2, E3, E4, E5, E6, E7>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		comparators.add(element5(comparator5));
		comparators.add(element6(comparator6));
		comparators.add(element7(comparator7));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 8-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @param comparator5 Comparator of the fifth elements.
	 * @param comparator6 Comparator of the sixth elements.
	 * @param comparator7 Comparator of the seventh elements.
	 * @param comparator8 Comparator of the eighth elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8> Comparator<Tuple8<E1, E2, E3, E4, E5, E6, E7, E8>> tuple8(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4, final Comparator<? super E5> comparator5, final Comparator<? super E6> comparator6, final Comparator<? super E7> comparator7, final Comparator<? super E8> comparator8) {
		final List<Comparator<? super Tuple8<E1, E2, E3, E4, E5, E6, E7, E8>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		comparators.add(element5(comparator5));
		comparators.add(element6(comparator6));
		comparators.add(element7(comparator7));
		comparators.add(element8(comparator8));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 9-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param <E9> Type of the ninth element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @param comparator5 Comparator of the fifth elements.
	 * @param comparator6 Comparator of the sixth elements.
	 * @param comparator7 Comparator of the seventh elements.
	 * @param comparator8 Comparator of the eighth elements.
	 * @param comparator9 Comparator of the ninth elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8, E9> Comparator<Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, E9>> tuple9(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4, final Comparator<? super E5> comparator5, final Comparator<? super E6> comparator6, final Comparator<? super E7> comparator7, final Comparator<? super E8> comparator8, final Comparator<? super E9> comparator9) {
		final List<Comparator<? super Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, E9>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		comparators.add(element5(comparator5));
		comparators.add(element6(comparator6));
		comparators.add(element7(comparator7));
		comparators.add(element8(comparator8));
		comparators.add(element9(comparator9));
		return Comparators.sequence(comparators);
	}
	
	/**
	 * Builds a comparator of 10-tuples according using the given element comparators.
	 * 
	 * @param <E1> Type of the first elements.
	 * @param <E2> Type of the second elements.
	 * @param <E3> Type of the third elements.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param <E9> Type of the ninth element.
	 * @param <E10> Type of the tenth element.
	 * @param comparator1 Comparator of the first elements.
	 * @param comparator2 Comparator of the second elements.
	 * @param comparator3 Comparator of the third elements.
	 * @param comparator4 Comparator of the fourth elements.
	 * @param comparator5 Comparator of the fifth elements.
	 * @param comparator6 Comparator of the sixth elements.
	 * @param comparator7 Comparator of the seventh elements.
	 * @param comparator8 Comparator of the eighth elements.
	 * @param comparator9 Comparator of the ninth elements.
	 * @param comparator10 Comparator of the tenth elements.
	 * @return The built comparator.
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8, E9, E10> Comparator<Tuple10<E1, E2, E3, E4, E5, E6, E7, E8, E9, E10>> tuple10(final Comparator<? super E1> comparator1, final Comparator<? super E2> comparator2, final Comparator<? super E3> comparator3, final Comparator<? super E4> comparator4, final Comparator<? super E5> comparator5, final Comparator<? super E6> comparator6, final Comparator<? super E7> comparator7, final Comparator<? super E8> comparator8, final Comparator<? super E9> comparator9, final Comparator<? super E10> comparator10) {
		final List<Comparator<? super Tuple10<E1, E2, E3, E4, E5, E6, E7, E8, E9, E10>>> comparators = new ArrayList<>();
		comparators.add(element1(comparator1));
		comparators.add(element2(comparator2));
		comparators.add(element3(comparator3));
		comparators.add(element4(comparator4));
		comparators.add(element5(comparator5));
		comparators.add(element6(comparator6));
		comparators.add(element7(comparator7));
		comparators.add(element8(comparator8));
		comparators.add(element9(comparator9));
		comparators.add(element10(comparator10));
		return Comparators.sequence(comparators);
	}
	
	private TupleComparators() {
		// Prevent instantiation.
	}
}
