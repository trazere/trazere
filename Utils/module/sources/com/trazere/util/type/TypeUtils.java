/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.type;

/**
 * The {@link TypeUtils} class provides various helpers regarding the types.
 */
public class TypeUtils {
	/**
	 * Get the boolean value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static boolean get(final Maybe<Boolean> value, final boolean defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().booleanValue() : defaultValue;
	}
	
	/**
	 * Get the byte value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static int get(final Maybe<Byte> value, final byte defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().byteValue() : defaultValue;
	}
	
	/**
	 * Get the integer value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static int get(final Maybe<Integer> value, final int defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().intValue() : defaultValue;
	}
	
	/**
	 * Get the long value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static long get(final Maybe<Long> value, final long defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().longValue() : defaultValue;
	}
	
	/**
	 * Get the float value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static float get(final Maybe<Float> value, final float defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().floatValue() : defaultValue;
	}
	
	/**
	 * Get the double value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static double get(final Maybe<Double> value, final double defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().doubleValue() : defaultValue;
	}
	
	/**
	 * Get the character value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static char get(final Maybe<Character> value, final char defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().charValue() : defaultValue;
	}
	
	/**
	 * Get the value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param <T> Type of the value.
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 */
	public static <T> T get(final Maybe<? extends T> value, final T defaultValue) {
		assert null != value;
		
		// Get.
		return value.isSome() ? value.asSome().getValue() : defaultValue;
	}
	
	private TypeUtils() {
		// Prevent instantiation.
	}
}
