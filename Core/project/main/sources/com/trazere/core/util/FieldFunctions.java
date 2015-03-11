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
	
	private static final Function<? extends Field<?>, ?> GET = (Function<Field<Object>, Object>) Field::get; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field1<?>, ?> GET1 = (Function<Field1<Object>, Object>) Field1::get1; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field2<?>, ?> GET2 = (Function<Field2<Object>, Object>) Field2::get2; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field3<?>, ?> GET3 = (Function<Field3<Object>, Object>) Field3::get3; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field4<?>, ?> GET4 = (Function<Field4<Object>, Object>) Field4::get4; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field5<?>, ?> GET5 = (Function<Field5<Object>, Object>) Field5::get5; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field6<?>, ?> GET6 = (Function<Field6<Object>, Object>) Field6::get6; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field7<?>, ?> GET7 = (Function<Field7<Object>, Object>) Field7::get7; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field8<?>, ?> GET8 = (Function<Field8<Object>, Object>) Field8::get8; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field9<?>, ?> GET9 = (Function<Field9<Object>, Object>) Field9::get9; // HACK: explicit cast to work around a bug of javac
	
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
	
	private static final Function<? extends Field10<?>, ?> GET10 = (Function<Field10<Object>, Object>) Field10::get10; // HACK: explicit cast to work around a bug of javac
	
	private FieldFunctions() {
		// Prevent instantiation.
	}
}
