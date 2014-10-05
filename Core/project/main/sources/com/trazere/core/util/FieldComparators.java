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

import java.util.Comparator;

/**
 * The {@link FieldComparators} class provides various comparators related to fields.
 */
public class FieldComparators {
	/**
	 * Builds a comparator of fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field<T>> field(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get());
	}
	
	/**
	 * Builds a comparator of first fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field1<T>> field1(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get1());
	}
	
	/**
	 * Builds a comparator of second fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field2<T>> field2(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get2());
	}
	
	/**
	 * Builds a comparator of third fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field3<T>> field3(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get3());
	}
	
	/**
	 * Builds a comparator of fourth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field4<T>> field4(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get4());
	}
	
	/**
	 * Builds a comparator of fifth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field5<T>> field5(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get5());
	}
	
	/**
	 * Builds a comparator of sixth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field6<T>> field6(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get6());
	}
	
	/**
	 * Builds a comparator of seventh fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field7<T>> field7(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get7());
	}
	
	/**
	 * Builds a comparator of eighth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field8<T>> field8(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get8());
	}
	
	/**
	 * Builds a comparator of ninth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field9<T>> field9(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get9());
	}
	
	/**
	 * Builds a comparator of tenth fields.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator Comparator of the values.
	 * @return The built comparator.
	 */
	public static <T> Comparator<Field10<T>> field10(final Comparator<? super T> comparator) {
		return ComparatorUtils.map(comparator, FieldFunctions.get10());
	}
	
	private FieldComparators() {
		// Prevent instantiation.
	}
}
