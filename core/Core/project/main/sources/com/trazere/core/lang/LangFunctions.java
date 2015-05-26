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
package com.trazere.core.lang;

import com.trazere.core.functional.Function;

/**
 * The {@link LangFunctions} class provides various factories of functions related to the language.
 */
public class LangFunctions {
	// Booleans.
	
	/**
	 * Builds a function that negates the boolean arguments.
	 *
	 * @return The built function.
	 */
	public static Function<Boolean, Boolean> not() {
		return NOT;
	}
	
	private static final Function<Boolean, Boolean> NOT = b -> !b.booleanValue();
	
	// Numbers.
	
	/**
	 * Builds a function that extracts the value of the argument numbers as bytes.
	 *
	 * @param <N> Type of the numbers.
	 * @return The built function.
	 * @see Number#byteValue()
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number> Function<N, Byte> byteValue() {
		return (Function<N, Byte>) BYTE_VALUE;
	}
	
	private static final Function<? extends Number, Byte> BYTE_VALUE = Number::byteValue;
	
	/**
	 * Builds a function that extracts the value of the argument numbers as shorts.
	 *
	 * @param <N> Type of the numbers.
	 * @return The built function.
	 * @see Number#shortValue()
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number> Function<N, Short> shortValue() {
		return (Function<N, Short>) SHORT_VALUE;
	}
	
	private static final Function<? extends Number, Short> SHORT_VALUE = Number::shortValue;
	
	/**
	 * Builds a function that extracts the value of the argument numbers as integers.
	 *
	 * @param <N> Type of the numbers.
	 * @return The built function.
	 * @see Number#intValue()
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number> Function<N, Integer> intValue() {
		return (Function<N, Integer>) INT_VALUE;
	}
	
	private static final Function<? extends Number, Integer> INT_VALUE = Number::intValue;
	
	/**
	 * Builds a function that extracts the value of the argument numbers as long integers.
	 *
	 * @param <N> Type of the numbers.
	 * @return The built function.
	 * @see Number#longValue()
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number> Function<N, Long> longValue() {
		return (Function<N, Long>) LONG_VALUE;
	}
	
	private static final Function<? extends Number, Long> LONG_VALUE = Number::longValue;
	
	/**
	 * Builds a function that extracts the value of the argument numbers as floats.
	 *
	 * @param <N> Type of the numbers.
	 * @return The built function.
	 * @see Number#floatValue()
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number> Function<N, Float> floatValue() {
		return (Function<N, Float>) FLOAT_VALUE;
	}
	
	private static final Function<? extends Number, Float> FLOAT_VALUE = Number::floatValue;
	
	/**
	 * Builds a function that extracts the value of the argument numbers as doubles.
	 *
	 * @param <N> Type of the numbers.
	 * @return The built function.
	 * @see Number#doubleValue()
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number> Function<N, Double> doubleValue() {
		return (Function<N, Double>) DOUBLE_VALUE;
	}
	
	private static final Function<? extends Number, Double> DOUBLE_VALUE = Number::doubleValue;
	
	// Enums.
	
	/**
	 * Builds a function that gets the name of enumeration entries.
	 * 
	 * @param <E> Type of the enumeration.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> Function<E, String> name() {
		return (Function<E, String>) NAME;
	}
	
	private static final Function<? extends Enum<?>, String> NAME = new Function<Enum<?>, String>() {
		@Override
		public String evaluate(final Enum<?> value) {
			return value.name();
		}
	};
	
	private LangFunctions() {
		// Prevents instantiation.
	}
}
