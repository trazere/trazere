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

import java.util.Comparator;

/**
 * The {@link FieldComparators} class provides various factories of {@link Comparator comparators} related to {@link Field fields}.
 * 
 * @see Comparator
 * @see Field
 * @since 2.0
 */
public class FieldComparators {
	/**
	 * Builds a comparator of fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field<T>> field(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field::get);
	}
	
	/**
	 * Builds a comparator of first fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field1<T>> field1(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field1::get1);
	}
	
	/**
	 * Builds a comparator of second fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field2<T>> field2(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field2::get2);
	}
	
	/**
	 * Builds a comparator of third fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field3<T>> field3(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field3::get3);
	}
	
	/**
	 * Builds a comparator of fourth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field4<T>> field4(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field4::get4);
	}
	
	/**
	 * Builds a comparator of fifth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field5<T>> field5(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field5::get5);
	}
	
	/**
	 * Builds a comparator of sixth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field6<T>> field6(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field6::get6);
	}
	
	/**
	 * Builds a comparator of seventh fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field7<T>> field7(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field7::get7);
	}
	
	/**
	 * Builds a comparator of eighth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field8<T>> field8(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field8::get8);
	}
	
	/**
	 * Builds a comparator of ninth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field9<T>> field9(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field9::get9);
	}
	
	/**
	 * Builds a comparator of tenth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <T> ExComparator<Field10<T>> field10(final Comparator<? super T> comparator) {
		return ComparatorUtils.mapping(comparator, Field10::get10);
	}
	
	private FieldComparators() {
		// Prevent instantiation.
	}
}
