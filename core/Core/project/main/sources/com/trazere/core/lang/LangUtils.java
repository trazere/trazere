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

/**
 * The {@link LangUtils} class provides various utilities regarding objects and the Java language.
 */
public class LangUtils {
	/**
	 * Safely unboxes the given boolean wrapper.
	 * <p>
	 * This methods support <code>null</code> values.
	 *
	 * @param value Unsafe wrapper to unbox. May be <code>null</code>.
	 * @return The unboxed value or <code>false</code> when the wrapper is <code>null</code>.
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
	 */
	public static char safeUnbox(final Character value, final char nullReplacement) {
		return null != value ? value.charValue() : nullReplacement;
	}
	
	// TODO: move to ThreadUtils
	//	/**
	//	 * Puts the current thread to sleep for the given amount of time.
	//	 * <p>
	//	 * This method throws an exception when the sleep is interrupted.
	//	 *
	//	 * @param <X> Type of the interruption exception.
	//	 * @param timeout The timeout in milliseconds.
	//	 * @param throwableFactory The exception factory for interruptions.
	//	 * @throws X When the sleep is interrupted.
	//	 * @see Thread#sleep(long)
	//	 */
	//	public static <X extends Exception> void sleep(final long timeout, final ThrowableFactory<X> throwableFactory)
	//	throws X {
	//		assert null != throwableFactory;
	//
	//		try {
	//			Thread.sleep(timeout);
	//		} catch (final InterruptedException exception) {
	//			throw throwableFactory.build(exception);
	//		}
	//	}
	//
	// TODO: move to ObjectUtils
	//	/**
	//	 * Puts the current thread to sleep for the given amount of time or until the given object is notified.
	//	 * <p>
	//	 * This method throws an exception when the sleep is interrupted.
	//	 *
	//	 * @param <X> Type of the interruption exception.
	//	 * @param object The object to observe.
	//	 * @param timeout The timeout in milliseconds.
	//	 * @param throwableFactory The exception factory for interruptions.
	//	 * @throws X When the sleep is interrupted.
	//	 * @see Thread#sleep(long)
	//	 */
	//	public static <X extends Exception> void wait(final Object object, final long timeout, final ThrowableFactory<X> throwableFactory)
	//	throws X {
	//		assert null != object;
	//		assert null != throwableFactory;
	//
	//		try {
	//			synchronized (object) {
	//				object.wait(timeout);
	//			}
	//		} catch (final InterruptedException exception) {
	//			throw throwableFactory.build(exception);
	//		}
	//	}
	//
	//	/**
	//	 * Puts the current thread to sleep for the given amount of time or until the given object is notified.
	//	 * <p>
	//	 * This method throws an exception when the sleep is interrupted.
	//	 *
	//	 * @param <X> Type of the interruption exception.
	//	 * @param object The object to observe.
	//	 * @param timeout The timeout in milliseconds.
	//	 * @param throwableFactory The exception factory for interruptions.
	//	 * @throws X When the sleep is interrupted.
	//	 * @see Thread#sleep(long)
	//	 */
	//	public static <X extends Exception> void waitOrSleep(final Maybe<?> object, final long timeout, final ThrowableFactory<X> throwableFactory)
	//	throws X {
	//		assert null != object;
	//
	//		if (object.isSome()) {
	//			wait(object.asSome().getValue(), timeout, throwableFactory);
	//		} else {
	//			LangUtils.sleep(timeout, throwableFactory);
	//		}
	//	}
	
	private LangUtils() {
		// Prevents instantiation.
	}
}
