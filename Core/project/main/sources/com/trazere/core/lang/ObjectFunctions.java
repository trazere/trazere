package com.trazere.core.lang;

import com.trazere.core.functional.Function;

/**
 * The {@link ObjectFunctions} class provides functions related to {@link Object}s.
 */
public class ObjectFunctions {
	/**
	 * Builds a function that gets the concrete Java class of objects.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Class<? extends T>> getClass_() {
		return (Function<T, Class<? extends T>>) GET_CLASS;
	}
	
	private static final Function<?, ? extends Class<?>> GET_CLASS = Object::getClass;
	
	/**
	 * Builds a function that computes the hash code of objects.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Integer> hashCode_() {
		return (Function<T, Integer>) HASH_CODE;
	}
	
	private static final Function<?, Integer> HASH_CODE = Object::hashCode;
	
	/**
	 * Builds a function that computes the string representation of objects.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, String> toString_() {
		return (Function<T, String>) TO_STRING;
	}
	
	private static final Function<?, String> TO_STRING = Object::toString;
	
	/**
	 * Builds a function that matches objects against the given type.
	 * 
	 * @param <T> Type of the arguments.
	 * @param <R> Type of the match.
	 * @param type Type against which to match.
	 * @param throwableFactory Throwable factory to use.
	 * @return The built extractor.
	 * @see ObjectUtils#match(Object, Class, ThrowableFactory)
	 */
	public static <T, R extends T> Function<T, R> match(final Class<R> type, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		assert null != type;
		assert null != throwableFactory;
		
		return object -> ObjectUtils.match(object, type, throwableFactory);
	}
	
	private ObjectFunctions() {
		// Prevent instantiation.
	}
}
