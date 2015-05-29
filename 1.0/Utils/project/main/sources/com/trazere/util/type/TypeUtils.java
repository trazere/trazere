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
package com.trazere.util.type;

import java.util.Comparator;

/**
 * The {@link TypeUtils} class provides various helpers regarding types.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class TypeUtils {
	/**
	 * Gets the boolean value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, boolean)}.
	 */
	@Deprecated
	public static boolean get(final Maybe<Boolean> value, final boolean defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().booleanValue() : defaultValue;
	}
	
	/**
	 * Gets the byte value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, byte)}.
	 */
	@Deprecated
	public static int get(final Maybe<Byte> value, final byte defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().byteValue() : defaultValue;
	}
	
	/**
	 * Gets the short value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, short)}.
	 */
	@Deprecated
	public static int get(final Maybe<Short> value, final short defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().shortValue() : defaultValue;
	}
	
	/**
	 * Gets the integer value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, int)}.
	 */
	@Deprecated
	public static int get(final Maybe<Integer> value, final int defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().intValue() : defaultValue;
	}
	
	/**
	 * Gets the long value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, long)}.
	 */
	@Deprecated
	public static long get(final Maybe<Long> value, final long defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().longValue() : defaultValue;
	}
	
	/**
	 * Gets the float value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, float)}.
	 */
	@Deprecated
	public static float get(final Maybe<Float> value, final float defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().floatValue() : defaultValue;
	}
	
	/**
	 * Gets the double value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, double)}.
	 */
	@Deprecated
	public static double get(final Maybe<Double> value, final double defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().doubleValue() : defaultValue;
	}
	
	/**
	 * Gets the character value of the given {@link Maybe} instance using the given default value when the value is {@link Maybe.None}.
	 * 
	 * @param value The value.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.MaybeUtils#get(com.trazere.core.util.Maybe, char)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public static char get(final Maybe<Character> value, final char defaultValue) {
		assert null != value;
		
		return value.isSome() ? value.asSome().getValue().charValue() : defaultValue;
	}
	
	/**
	 * Compares the values wrapped in the given maybe instances using the given comparator.
	 * <p>
	 * <code>None</code> instances are considered as less than <code>Some</code> instances.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 The first instance.
	 * @param value2 The second instance.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link com.trazere.core.util.MaybeComparators#maybe(Comparator)}.
	 */
	@Deprecated
	public static <T> int compare(final Comparator<T> comparator, final Maybe<? extends T> value1, final Maybe<? extends T> value2) {
		assert null != comparator;
		assert null != value1;
		assert null != value2;
		
		if (value1.isNone()) {
			return value2.isNone() ? 0 : -1;
		} else {
			return value2.isNone() ? 1 : comparator.compare(value1.asSome().getValue(), value2.asSome().getValue());
		}
	}
	
	private TypeUtils() {
		// Prevents instantiation.
	}
}
