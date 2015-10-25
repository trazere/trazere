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
package com.trazere.core.lang;

import com.trazere.core.functional.Function;
import com.trazere.core.reference.MutableReference;
import com.trazere.core.reference.Reference;

/**
 * The {@link LangUtils} class provides various utilities regarding the Java language.
 * 
 * @since 2.0
 */
public class LangUtils {
	/**
	 * Safely unboxes the given boolean wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>false</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static boolean safeUnbox(final Boolean value) {
		return safeUnbox(value, false);
	}
	
	/**
	 * Safely unboxes the given boolean wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static boolean safeUnbox(final Boolean value, final boolean nullReplacement) {
		return null != value ? value.booleanValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given byte wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>0</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static int safeUnbox(final Byte value) {
		return safeUnbox(value, (byte) 0);
	}
	
	/**
	 * Safely unboxes the given byte wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static int safeUnbox(final Byte value, final byte nullReplacement) {
		return null != value ? value.byteValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given short integer wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>0</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static int safeUnbox(final Short value) {
		return safeUnbox(value, (short) 0);
	}
	
	/**
	 * Safely unboxes the given short integer wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static int safeUnbox(final Short value, final short nullReplacement) {
		return null != value ? value.shortValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given integer wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>0</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static int safeUnbox(final Integer value) {
		return safeUnbox(value, 0);
	}
	
	/**
	 * Safely unboxes the given integer wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static int safeUnbox(final Integer value, final int nullReplacement) {
		return null != value ? value.intValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given long integer wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>0</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static long safeUnbox(final Long value) {
		return safeUnbox(value, 0L);
	}
	
	/**
	 * Safely unboxes the given long integer wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static long safeUnbox(final Long value, final long nullReplacement) {
		return null != value ? value.longValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given float wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>0</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static float safeUnbox(final Float value) {
		return safeUnbox(value, 0f);
	}
	
	/**
	 * Safely unboxes the given float wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static float safeUnbox(final Float value, final float nullReplacement) {
		return null != value ? value.floatValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given double wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>0</code> when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static double safeUnbox(final Double value) {
		return safeUnbox(value, 0d);
	}
	
	/**
	 * Safely unboxes the given double wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static double safeUnbox(final Double value, final double nullReplacement) {
		return null != value ? value.doubleValue() : nullReplacement;
	}
	
	/**
	 * Safely unboxes the given character wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @param nullReplacement Value to use for <code>null</code> wrappers.
	 * @return The unboxed value or the default value when the wrapper is <code>null</code>.
	 * @since 2.0
	 */
	public static char safeUnbox(final Character value, final char nullReplacement) {
		return null != value ? value.charValue() : nullReplacement;
	}
	
	/**
	 * Builds a recursive value using the given factory.
	 * <p>
	 * The factory is a function that takes a reference that will provide the built value. This reference must not be called during the evaluation of the
	 * factory (because the value does not exist yet).
	 * 
	 * @param <T> Type of the value.
	 * @param factory Factory that builds the value.
	 * @return The built value.
	 * @since 2.0
	 */
	public static <T> T rec(final Function<? super Reference<T>, ? extends T> factory) {
		final MutableReference<T> ref = new MutableReference<>();
		return ref.set(factory.evaluate(ref));
	}
	
	private LangUtils() {
		// Prevents instantiation.
	}
}
