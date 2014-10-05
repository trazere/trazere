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

/**
 * The {@link FieldFunctions} class provides various functions related to tuples.
 */
public class FieldFunctions {
	/**
	 * Builds a function that gets the value of fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field<? extends T>, T> get() {
		return (Function<Field<? extends T>, T>) GET;
	}
	
	private static final Function<? extends Field<?>, ?> GET = Field::get;
	
	/**
	 * Builds a function that gets the value of first fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field1<? extends T>, T> get1() {
		return (Function<Field1<? extends T>, T>) GET1;
	}
	
	private static final Function<? extends Field1<?>, ?> GET1 = Field1::get1;
	
	/**
	 * Builds a function that gets the value of second fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field2<? extends T>, T> get2() {
		return (Function<Field2<? extends T>, T>) GET2;
	}
	
	private static final Function<? extends Field2<?>, ?> GET2 = Field2::get2;
	
	/**
	 * Builds a function that gets the value of third fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field3<? extends T>, T> get3() {
		return (Function<Field3<? extends T>, T>) GET3;
	}
	
	private static final Function<? extends Field3<?>, ?> GET3 = Field3::get3;
	
	/**
	 * Builds a function that gets the value of fourth fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field4<? extends T>, T> get4() {
		return (Function<Field4<? extends T>, T>) GET4;
	}
	
	private static final Function<? extends Field4<?>, ?> GET4 = Field4::get4;
	
	/**
	 * Builds a function that gets the value of fifth fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field5<? extends T>, T> get5() {
		return (Function<Field5<? extends T>, T>) GET5;
	}
	
	private static final Function<? extends Field5<?>, ?> GET5 = Field5::get5;
	
	/**
	 * Builds a function that gets the value of sixth fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field6<? extends T>, T> get6() {
		return (Function<Field6<? extends T>, T>) GET6;
	}
	
	private static final Function<? extends Field6<?>, ?> GET6 = Field6::get6;
	
	/**
	 * Builds a function that gets the value of seventh fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field7<? extends T>, T> get7() {
		return (Function<Field7<? extends T>, T>) GET7;
	}
	
	private static final Function<? extends Field7<?>, ?> GET7 = Field7::get7;
	
	/**
	 * Builds a function that gets the value of eighth fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field8<? extends T>, T> get8() {
		return (Function<Field8<? extends T>, T>) GET8;
	}
	
	private static final Function<? extends Field8<?>, ?> GET8 = Field8::get8;
	
	/**
	 * Builds a function that gets the value of ninth fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field9<? extends T>, T> get9() {
		return (Function<Field9<? extends T>, T>) GET9;
	}
	
	private static final Function<? extends Field9<?>, ?> GET9 = Field9::get9;
	
	/**
	 * Builds a function that gets the value of tenth fields.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Field10<? extends T>, T> get10() {
		return (Function<Field10<? extends T>, T>) GET10;
	}
	
	private static final Function<? extends Field10<?>, ?> GET10 = Field10::get10;
	
	private FieldFunctions() {
		// Prevent instantiation.
	}
}
