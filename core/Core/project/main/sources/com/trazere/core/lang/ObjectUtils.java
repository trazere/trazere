package com.trazere.core.lang;

import com.trazere.core.util.Maybe;

/**
 * The {@link ObjectUtils} class provides various utilities related to {@link Object}s.
 */
public class ObjectUtils {
	/**
	 * Gets the concrete Java class of the given object.
	 * 
	 * @param <T> Type of the object.
	 * @param object Object to read.
	 * @return The Java class.
	 * @see Object#getClass()
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<? extends T> getClass(final T object) {
		return (Class<? extends T>) object.getClass();
	}
	
	/**
	 * Casts the given object to some other type.
	 * <p>
	 * This methods aims to work around various limitations of the Java type system where regular casts cannot be used. It performs no verifications whatsoever
	 * and should be used as seldom as possible because it is inherently unsafe.
	 *
	 * @param <R> Target type.
	 * @param object Object to cast. May be <code>null</code>.
	 * @return The given casted object. May be <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <R> R cast(final Object object) {
		return (R) object;
	}
	
	/**
	 * Casts the given object to some subtype.
	 * <p>
	 * This methods aims to work around various limitations of the Java type system where regular casts cannot be used. It is a little safer than
	 * {@link #cast(Object)} because the target type is constrained to a subtype, but it performs no verifications whatsoever and should also be used as seldom
	 * as possible because it is inherently unsafe.
	 *
	 * @param <T> Original type.
	 * @param <R> Target type.
	 * @param object Object to cast. May be <code>null</code>.
	 * @return The given casted object. May be <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <T, R extends T> R downcast(final T object) {
		return (R) object;
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
	 * @see Object#equals(Object)
	 */
	public static <T extends Object> boolean safeEquals(final T object1, final T object2) {
		return object1 == object2 || null != object1 && object1.equals(object2);
	}
	
	/**
	 * Matches the given object against the given type.
	 * <p>
	 * This methods tests that the given object is not <code>null</code> and is assignable to the given type.
	 *
	 * @param <T> Type of the match.
	 * @param object Object to match. May be <code>null</code>.
	 * @param type Type against which to match.
	 * @return The given matched object.
	 */
	public static <T> Maybe<T> match(final Object object, final Class<T> type) {
		if (null != object && type.isInstance(object)) {
			return Maybe.some(type.cast(object));
		} else {
			return Maybe.none();
		}
	}
	
	/**
	 * Matches the given object against the given type.
	 * <p>
	 * This methods tests that the given object is not <code>null</code> and is assignable to the given type.
	 *
	 * @param <T> Type of the match.
	 * @param <X> Type of the exceptions.
	 * @param object Object to match. May be <code>null</code>.
	 * @param type Type against which to match.
	 * @param throwableFactory Throwable factory to use.
	 * @return The given matched object.
	 * @throws X When the given object is null or is not assignable to the given type.
	 */
	public static <T, X extends Exception> T match(final Object object, final Class<T> type, final ThrowableFactory<X> throwableFactory)
	throws X {
		if (null != object && type.isInstance(object)) {
			return type.cast(object);
		} else {
			throw throwableFactory.build("Invalid value \"" + object + "\"");
		}
	}
	
	private ObjectUtils() {
		// Prevent instantiation.
	}
}
