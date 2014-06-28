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
 * The {@link LangUtils} class provides various helpers regarding objects and the Java language.
 */
public class LangUtils {
	// TODO: move to ComparableUtils
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
	public static <T extends Comparable<T>> int safeCompare(final T object1, final T object2) {
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : object1.compareTo(object2);
		}
	}
	
	//	// TODO: rename to safeBooleanValue or safeUnbox
	//	/**
	//	 * Gets the value of the given boolean wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static boolean get(final Boolean value, final boolean defaultValue) {
	//		return null != value ? value.booleanValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeByteValue or safeUnbox
	//	/**
	//	 * Gets the value of the given byte wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static int get(final Byte value, final byte defaultValue) {
	//		return null != value ? value.byteValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeShortValue or safeUnbox
	//	/**
	//	 * Gets the value of the given short wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static int get(final Short value, final short defaultValue) {
	//		return null != value ? value.shortValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeIntValue or safeUnbox
	//	/**
	//	 * Gets the value of the given integer wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static int get(final Integer value, final int defaultValue) {
	//		return null != value ? value.intValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeLongValue or safeUnbox
	//	/**
	//	 * Gets the value of the given long wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static long get(final Long value, final long defaultValue) {
	//		return null != value ? value.longValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeFloatValue or safeUnbox
	//	/**
	//	 * Gets the value of the given float wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static float get(final Float value, final float defaultValue) {
	//		return null != value ? value.floatValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeDoubleValue or safeUnbox
	//	/**
	//	 * Gets the value of the given double wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static double get(final Double value, final double defaultValue) {
	//		return null != value ? value.doubleValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to safeCharValue or safeUnbox
	//	/**
	//	 * Gets the value of the given character wrapper.
	//	 *
	//	 * @param value The wrapper. May be <code>null</code>.
	//	 * @param defaultValue The default value.
	//	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	//	 */
	//	public static char get(final Character value, final char defaultValue) {
	//		return null != value ? value.charValue() : defaultValue;
	//	}
	//
	//	// TODO: rename to ?? (safeUnbox ?)
	//	/**
	//	 * Gets the value of the given object.
	//	 *
	//	 * @param <T> Type of the value.
	//	 * @param value The object. May be <code>null</code>.
	//	 * @param defaultValue The default value. May be <code>null</code>.
	//	 * @return The object or the default value when it is <code>null</code>. May be <code>null</code>.
	//	 */
	//	public static <T> T get(final T value, final T defaultValue) {
	//		return null != value ? value : defaultValue;
	//	}
	
	//	// TODO: move to IterableUtils or CollectionUtils
	//	/**
	//	 * Gets the least given value according to the given comparator.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param comparator The comparator.
	//	 * @param values The values.
	//	 * @return The least value.
	//	 */
	//	public static <T> Maybe<T> least(final Comparator<? super T> comparator, final Collection<? extends T> values) {
	//		assert null != values;
	//
	//		return least(comparator, values.iterator());
	//	}
	//
	//	// TODO: move to IteratorUtils
	//	/**
	//	 * Gets the least given value according to the given comparator.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param comparator The comparator.
	//	 * @param values The values.
	//	 * @return The least value.
	//	 */
	//	public static <T> Maybe<T> least(final Comparator<? super T> comparator, final Iterator<? extends T> values) {
	//		assert null != comparator;
	//		assert null != values;
	//
	//		// Get the first value.
	//		if (!values.hasNext()) {
	//			return Maybe.none();
	//		}
	//
	//		// Get the least value.
	//		final MutableObject<T> least = new MutableObject<T>(values.next());
	//		while (values.hasNext()) {
	//			final T value = values.next();
	//			if (comparator.compare(value, least.get()) < 1) {
	//				least.set(value);
	//			}
	//		}
	//		return Maybe.some(least.get());
	//	}
	//
	//	// TODO: move to IterableUtils or CollectionUtils
	//	/**
	//	 * Gets the greatest given value according to the given comparator.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param comparator The comparator.
	//	 * @param values The values.
	//	 * @return The greatest value.
	//	 */
	//	public static <T> Maybe<T> greatest(final Comparator<? super T> comparator, final Collection<? extends T> values) {
	//		assert null != values;
	//
	//		return greatest(comparator, values.iterator());
	//	}
	//
	//	// TODO: move to IteratorUtils
	//	/**
	//	 * Gets the greatest given value according to the given comparator.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param comparator The comparator.
	//	 * @param values The values.
	//	 * @return The greatest value.
	//	 */
	//	public static <T> Maybe<T> greatest(final Comparator<? super T> comparator, final Iterator<? extends T> values) {
	//		assert null != comparator;
	//		assert null != values;
	//
	//		// Get the first value.
	//		if (!values.hasNext()) {
	//			return Maybe.none();
	//		}
	//
	//		// Get the greatest value.
	//		final MutableObject<T> greatest = new MutableObject<T>(values.next());
	//		while (values.hasNext()) {
	//			final T value = values.next();
	//			if (comparator.compare(value, greatest.get()) > 1) {
	//				greatest.set(value);
	//			}
	//		}
	//		return Maybe.some(greatest.get());
	//	}
	//
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
