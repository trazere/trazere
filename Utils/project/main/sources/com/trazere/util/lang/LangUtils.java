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
package com.trazere.util.lang;

import com.trazere.util.function.Function1;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.reference.Reference;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link LangUtils} class provides various helpers regarding the manipulation of objets.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class LangUtils {
	/**
	 * Gets the Java class the given object.
	 * 
	 * @param <T> Type of the object.
	 * @param object The object.
	 * @return The class.
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#getClass(Object)}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> getClass(final T object) {
		assert null != object;
		
		return (Class<? extends T>) object.getClass();
	}
	
	/**
	 * Builds a recursive value using the given factory.
	 * <p>
	 * The factory is a function that takes a reference that will provide the built value. This reference must not be called during the evaluation of the
	 * factory (because the value does not exist yet).
	 * 
	 * @param <T> Type of the value.
	 * @param <X> Type of the exceptions.
	 * @param factory Factory that build the value.
	 * @return The built value.
	 * @throws X When the factory evaluation fails.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#rec(com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T, X extends Exception> T rec(final Function1<? super Reference<T>, ? extends T, ? extends X> factory)
	throws X {
		final MutableReference<T> ref = new MutableReference<T>();
		return ref.set(factory.evaluate(ref));
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
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#cast(Object)}.
	 */
	@Deprecated
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
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#downcast(Object)}.
	 */
	@Deprecated
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
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#match(Object, Class)}.
	 */
	@Deprecated
	public static <T> Maybe<T> match(final Object object, final Class<T> type) {
		assert null != type;
		
		if (null != object && type.isInstance(object)) {
			return Maybe.some(type.cast(object));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Matches the given object according to the given type.
	 * <p>
	 * This methods tests that the given object is not <code>null</code> and is assignable to the given type.
	 * 
	 * @param <T> Type of the match.
	 * @param <X> Type of the exceptions.
	 * @param object The object. May be <code>null</code>.
	 * @param type The type.
	 * @param throwableFactory The throwable factory to use.
	 * @return The given matched object.
	 * @throws X When the given object is not assignable to the given type.
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#match(Object, Class, com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static <T, X extends Exception> T match(final Object object, final Class<T> type, final ThrowableFactory<X> throwableFactory)
	throws X {
		assert null != type;
		assert null != throwableFactory;
		
		if (null != object && type.isInstance(object)) {
			return type.cast(object);
		} else {
			throw throwableFactory.build("Invalid value \"" + object + "\"");
		}
	}
	
	// TODO: rename to safeBooleanValue or safeUnbox
	/**
	 * Gets the value of the given boolean wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#safeUnbox(Boolean, boolean)}.
	 */
	@Deprecated
	public static boolean get(final Boolean value, final boolean defaultValue) {
		return null != value ? value.booleanValue() : defaultValue;
	}
	
	// TODO: rename to safeByteValue or safeUnbox
	/**
	 * Gets the value of the given byte wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#safeUnbox(Byte, byte)}.
	 */
	@Deprecated
	public static int get(final Byte value, final byte defaultValue) {
		return null != value ? value.byteValue() : defaultValue;
	}
	
	// TODO: rename to safeShortValue or safeUnbox
	/**
	 * Gets the value of the given short wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#safeUnbox(Short, short)}.
	 */
	@Deprecated
	public static int get(final Short value, final short defaultValue) {
		return null != value ? value.shortValue() : defaultValue;
	}
	
	// TODO: rename to safeIntValue or safeUnbox
	/**
	 * Gets the value of the given integer wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated {@link com.trazere.core.lang.LangUtils#safeUnbox(Integer, int)}.
	 */
	@Deprecated
	public static int get(final Integer value, final int defaultValue) {
		return null != value ? value.intValue() : defaultValue;
	}
	
	// TODO: rename to safeLongValue or safeUnbox
	/**
	 * Gets the value of the given long wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#safeUnbox(Long, long)}.
	 */
	@Deprecated
	public static long get(final Long value, final long defaultValue) {
		return null != value ? value.longValue() : defaultValue;
	}
	
	// TODO: rename to safeFloatValue or safeUnbox
	/**
	 * Gets the value of the given float wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated {@link com.trazere.core.lang.LangUtils#safeUnbox(Float, float)}.
	 */
	@Deprecated
	public static float get(final Float value, final float defaultValue) {
		return null != value ? value.floatValue() : defaultValue;
	}
	
	// TODO: rename to safeDoubleValue or safeUnbox
	/**
	 * Gets the value of the given double wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#safeUnbox(Double, double)}.
	 */
	@Deprecated
	public static double get(final Double value, final double defaultValue) {
		return null != value ? value.doubleValue() : defaultValue;
	}
	
	// TODO: rename to safeCharValue or safeUnbox
	/**
	 * Gets the value of the given character wrapper.
	 * 
	 * @param value The wrapper. May be <code>null</code>.
	 * @param defaultValue The default value.
	 * @return The value of the wrapper or the default value when it is <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.LangUtils#safeUnbox(Character, char)}.
	 */
	@Deprecated
	public static char get(final Character value, final char defaultValue) {
		return null != value ? value.charValue() : defaultValue;
	}
	
	// TODO: rename to ?? (safeUnbox ?)
	/**
	 * Gets the value of the given object.
	 * 
	 * @param <T> Type of the value.
	 * @param value The object. May be <code>null</code>.
	 * @param defaultValue The default value. May be <code>null</code>.
	 * @return The object or the default value when it is <code>null</code>. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#safe(Object, Object)}.
	 */
	@Deprecated
	public static <T> T get(final T value, final T defaultValue) {
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
	 * @deprecated Use {@link com.trazere.core.lang.ObjectUtils#safeEquals(Object, Object)}.
	 */
	@Deprecated
	public static <T extends Object> boolean safeEquals(final T object1, final T object2) {
		return object1 == object2 || null != object1 && object1.equals(object2);
	}
	
	// TODO: move to ComparatorUtils
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
	 * @deprecated Use {@link com.trazere.core.lang.ComparableUtils#safeCompareTo(Comparable, Comparable)}.
	 */
	@Deprecated
	public static <T extends Comparable<T>> int safeCompare(final T object1, final T object2) {
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : object1.compareTo(object2);
		}
	}
	
	// TODO: move to ComparatorUtils
	/**
	 * Compares the given values using the given comparator.
	 * <p>
	 * This method supports comparison of <code>null</code> values. <code>null</code> values are considered as less than non <code>null</code> values.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param object1 The first value. May be <code>null</code>.
	 * @param object2 The second value. May be <code>null</code>.
	 * @return The result of the comparison as defined by the {@link Comparator#compare(Object, Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#safeCompare(Comparator, Object, Object)}.
	 */
	@Deprecated
	public static <T> int safeCompare(final Comparator<T> comparator, final T object1, final T object2) {
		assert null != comparator;
		
		if (null == object1) {
			return null == object2 ? 0 : -1;
		} else {
			return null == object2 ? 1 : comparator.compare(object1, object2);
		}
	}
	
	// TODO: move to ComparatorUtils or CollectionUtils
	/**
	 * Gets the least of the given values according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return The least value.
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#least(Comparator, Object, Object)}.
	 */
	@Deprecated
	public static <T> T least(final Comparator<? super T> comparator, final T value1, final T value2) {
		assert null != comparator;
		
		return comparator.compare(value1, value2) <= 0 ? value1 : value2;
	}
	
	// TODO: move to ComparatorUtils or CollectionUtils
	/**
	 * Gets the least given value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param values The values.
	 * @return The least value.
	 * @deprecated Use {@link com.trazere.core.lang.IterableUtils#least(Iterable, Comparator)}.
	 */
	@Deprecated
	public static <T> Maybe<T> least(final Comparator<? super T> comparator, final Collection<? extends T> values) {
		assert null != values;
		
		return least(comparator, values.iterator());
	}
	
	// TODO: move to ComparatorUtils or CollectionUtils
	/**
	 * Gets the least given value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param values The values.
	 * @return The least value.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#least(Iterator, Comparator)}.
	 */
	@Deprecated
	public static <T> Maybe<T> least(final Comparator<? super T> comparator, final Iterator<? extends T> values) {
		assert null != comparator;
		assert null != values;
		
		// Get the first value.
		if (!values.hasNext()) {
			return Maybe.none();
		}
		
		// Get the least value.
		final MutableObject<T> least = new MutableObject<T>(values.next());
		while (values.hasNext()) {
			final T value = values.next();
			if (comparator.compare(value, least.get()) < 0) {
				least.set(value);
			}
		}
		return Maybe.some(least.get());
	}
	
	// TODO: move to ComparatorUtils or CollectionUtils
	/**
	 * Gets the greatest of the given values according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return The greatest value.
	 * @deprecated Use {@link com.trazere.core.util.ComparatorUtils#greatest(Comparator, Object, Object)}.
	 */
	@Deprecated
	public static <T> T greatest(final Comparator<? super T> comparator, final T value1, final T value2) {
		assert null != comparator;
		
		return comparator.compare(value1, value2) >= 0 ? value1 : value2;
	}
	
	// TODO: move to ComparatorUtils or CollectionUtils
	/**
	 * Gets the greatest given value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param values The values.
	 * @return The greatest value.
	 * @deprecated Use {@link com.trazere.core.lang.IterableUtils#greatest(Iterable, Comparator)}.
	 */
	@Deprecated
	public static <T> Maybe<T> greatest(final Comparator<? super T> comparator, final Collection<? extends T> values) {
		assert null != values;
		
		return greatest(comparator, values.iterator());
	}
	
	// TODO: move to ComparatorUtils or CollectionUtils
	/**
	 * Gets the greatest given value according to the given comparator.
	 * 
	 * @param <T> Type of the values.
	 * @param comparator The comparator.
	 * @param values The values.
	 * @return The greatest value.
	 * @deprecated Use {@link com.trazere.core.imperative.IteratorUtils#greatest(Iterator, Comparator)}.
	 */
	@Deprecated
	public static <T> Maybe<T> greatest(final Comparator<? super T> comparator, final Iterator<? extends T> values) {
		assert null != comparator;
		assert null != values;
		
		// Get the first value.
		if (!values.hasNext()) {
			return Maybe.none();
		}
		
		// Get the greatest value.
		final MutableObject<T> greatest = new MutableObject<T>(values.next());
		while (values.hasNext()) {
			final T value = values.next();
			if (comparator.compare(value, greatest.get()) > 0) {
				greatest.set(value);
			}
		}
		return Maybe.some(greatest.get());
	}
	
	/**
	 * Puts the current thread to sleep for the given amount of time.
	 * <p>
	 * This method throws an exception when the sleep is interrupted.
	 * 
	 * @param <X> Type of the interruption exception.
	 * @param timeout The timeout in milliseconds.
	 * @param throwableFactory The exception factory for interruptions.
	 * @throws X When the sleep is interrupted.
	 * @see Thread#sleep(long)
	 * @deprecated Use {@link com.trazere.core.lang.ThreadUtils#sleep(java.time.Duration, com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static <X extends Exception> void sleep(final long timeout, final ThrowableFactory<X> throwableFactory)
	throws X {
		assert null != throwableFactory;
		
		try {
			Thread.sleep(timeout);
		} catch (final InterruptedException exception) {
			throw throwableFactory.build(exception);
		}
	}
	
	/**
	 * Puts the current thread to sleep for the given amount of time or until the given object is notified.
	 * <p>
	 * This method throws an exception when the sleep is interrupted.
	 * 
	 * @param <X> Type of the interruption exception.
	 * @param object The object to observe.
	 * @param timeout The timeout in milliseconds.
	 * @param throwableFactory The exception factory for interruptions.
	 * @throws X When the sleep is interrupted.
	 * @see Thread#sleep(long)
	 * @deprecated Use {@link com.trazere.core.lang.ThreadUtils#wait(Object, java.time.Duration, com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static <X extends Exception> void wait(final Object object, final long timeout, final ThrowableFactory<X> throwableFactory)
	throws X {
		assert null != object;
		assert null != throwableFactory;
		
		try {
			synchronized (object) {
				object.wait(timeout);
			}
		} catch (final InterruptedException exception) {
			throw throwableFactory.build(exception);
		}
	}
	
	/**
	 * Puts the current thread to sleep for the given amount of time or until the given object is notified.
	 * <p>
	 * This method throws an exception when the sleep is interrupted.
	 * 
	 * @param <X> Type of the interruption exception.
	 * @param object The object to observe.
	 * @param timeout The timeout in milliseconds.
	 * @param throwableFactory The exception factory for interruptions.
	 * @throws X When the sleep is interrupted.
	 * @see Thread#sleep(long)
	 * @deprecated Use
	 *             {@link com.trazere.core.lang.ThreadUtils#waitOrSleep(com.trazere.core.util.Maybe, java.time.Duration, com.trazere.core.lang.ThrowableFactory)}
	 *             .
	 */
	@Deprecated
	public static <X extends Exception> void waitOrSleep(final Maybe<?> object, final long timeout, final ThrowableFactory<X> throwableFactory)
	throws X {
		assert null != object;
		
		if (object.isSome()) {
			wait(object.asSome().getValue(), timeout, throwableFactory);
		} else {
			LangUtils.sleep(timeout, throwableFactory);
		}
	}
	
	private LangUtils() {
		// Prevents instantiation.
	}
}
