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
package com.trazere.util.lang;

import com.trazere.core.lang.MutableObject;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.util.function.Function1;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.reference.Reference;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * The {@link LangUtils} class provides various helpers regarding the manipulation of objets.
 */
public class LangUtils {
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
	public static <T> T get(final T value, final T defaultValue) {
		return null != value ? value : defaultValue;
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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