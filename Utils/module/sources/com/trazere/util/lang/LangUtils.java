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
package com.trazere.util.lang;

import com.trazere.util.type.Maybe;
import java.util.Comparator;

/**
 * The {@link LangUtils} class provides various helpers regarding the manipulation of objets.
 */
public class LangUtils {
	/**
	 * Gets the Java class the given object.
	 * 
	 * @param <T> Type of the object.
	 * @param object The object.
	 * @return The class.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> getClass(final T object) {
		assert null != object;
		
		return (Class<? extends T>) object.getClass();
	}
	
	/**
	 * Casts the given object to the given type.
	 * <p>
	 * This methods aims to work around various limitations of the Java type system where regular casts cannot be used. It performs no verifications whatsoever
	 * and should be used as seldom as possible because it is inherently unsafe.
	 * 
	 * @param <R> Target type.
	 * @param object The object. May be <code>null</code>.
	 * @return The casted object. May be <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <R> R cast(final Object object) {
		return (R) object;
	}
	
	/**
	 * Casts the given object to some subtype.
	 * <p>
	 * This methods aims to be called where regular downcasts would be used in order to track them in the code. It is a little safer than {@link #cast(Object)}
	 * because the result type can statically be constrained by the argument type but should still be used as seldom as possible.
	 * 
	 * @param <T> Original type.
	 * @param <R> Target type.
	 * @param object The object. May be <code>null</code>.
	 * @return The casted object. May be <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> R downcast(final T object) {
		return (R) object;
	}
	
	/**
	 * Matches the given object according to the given type.
	 * <p>
	 * This methods tests that the given object is not <code>null</code> and is assignable to the given type.
	 * 
	 * @param <T> Type of the match.
	 * @param object The object. May be <code>null</code>.
	 * @param type The type.
	 * @return The given matched object.
	 */
	public static <T> Maybe<T> match(final Object object, final Class<T> type) {
		assert null != type;
		
		if (null != object && type.isInstance(object)) {
			return Maybe.some(type.cast(object));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Gets the value of the given boolean wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static boolean getBoolean(final Boolean value, final boolean defaultValue) {
		return null != value ? value.booleanValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given byte wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static int getByte(final Byte value, final byte defaultValue) {
		return null != value ? value.byteValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given integer wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static int getInt(final Integer value, final int defaultValue) {
		return null != value ? value.intValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given long wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static long getLong(final Long value, final long defaultValue) {
		return null != value ? value.longValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given float wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static float getFloat(final Float value, final float defaultValue) {
		return null != value ? value.floatValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given double wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static double getDouble(final Double value, final double defaultValue) {
		return null != value ? value.doubleValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given character wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 */
	public static char getChar(final Character value, final char defaultValue) {
		return null != value ? value.charValue() : defaultValue;
	}
	
	/**
	 * Gets the value of the given object.
	 * 
	 * @param <T> Type of the value.
	 * @param value The object. May be <code>null</code>.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The object or the default value when it is <code>null</code>. May be <code>null</code>.
	 */
	public static <T> T getObject(final T value, final T defaultValue) {
		return null != value ? value : defaultValue;
	}
	
	/**
	 * Tests for equality of the given values.
	 * <p>
	 * This method supports comparison of <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 The first value. May be <code>null</code>.
	 * @param object2 The second value. May be <code>null</code>.
	 * @return <code>true</code> if the values are both <code>null</code> or both not <code>null</code> and equal.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T extends Object> boolean equals(final T object1, final T object2) {
		return object1 == object2 || null != object1 && object1.equals(object2);
	}
	
	/**
	 * Builds a natural value comparator.
	 * 
	 * @param <T> Type of the value.
	 * @return The built comparator.
	 */
	public static <T extends Comparable<T>> Comparator<T> comparator() {
		return new Comparator<T>() {
			public int compare(final T object1, final T object2) {
				return object1.compareTo(object2);
			}
		};
	}
	
	/**
	 * Compares the given comparable values.
	 * <p>
	 * This method supports comparison of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 The first value. May be <code>null</code>.
	 * @param object2 The second value. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T extends Comparable<T>> int compare(final T object1, final T object2) {
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : object1.compareTo(object2);
		}
	}
	
	/**
	 * Compares the given values using the given comparator.
	 * <p>
	 * This method supports comparison of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param object1 The first value. May be <code>null</code>.
	 * @param object2 The second value. May be <code>null</code>.
	 * @param comparator The comparator.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T> int compare(final T object1, final T object2, final Comparator<T> comparator) {
		assert null != comparator;
		
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : comparator.compare(object1, object2);
		}
	}
	
	/**
	 * Gets the least of the given values according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return The least value.
	 */
	public static <T> T min(final Comparator<? super T> comparator, final T value1, final T value2) {
		assert null != comparator;
		
		return comparator.compare(value1, value2) <= 0 ? value1 : value2;
	}
	
	/**
	 * Gets the greatest of the given values according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return The greatest value.
	 */
	public static <T> T max(final Comparator<? super T> comparator, final T value1, final T value2) {
		assert null != comparator;
		
		return comparator.compare(value1, value2) >= 0 ? value1 : value2;
	}
	
	private LangUtils() {
		// Prevent instantiation.
	}
}
