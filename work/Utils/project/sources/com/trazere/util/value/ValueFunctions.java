package com.trazere.util.value;

import com.trazere.util.function.Function1;

/**
 * The {@link ValueFunctions} class provides various factories of functions related to values.
 */
public class ValueFunctions {
	/**
	 * Builds a function that serializes values using the given serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param <X> Type of the exceptions.
	 * @param serializer The serializer.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<T, R, X> serializeFunction(final ValueSerializer<? super T, ? extends R, ? extends X> serializer) {
		assert null != serializer;
		
		return new Function1<T, R, X>() {
			@Override
			public R evaluate(final T value)
			throws X {
				return serializer.serialize(value);
			}
		};
	}
	
	/**
	 * Builds a function that deserializes values using the given serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param <X> Type of the exceptions.
	 * @param serializer The serializer.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<R, T, X> deserializeFunction(final ValueSerializer<? extends T, ? super R, ? extends X> serializer) {
		assert null != serializer;
		
		return new Function1<R, T, X>() {
			@Override
			public T evaluate(final R representation)
			throws X {
				return serializer.deserialize(representation);
			}
		};
	}
	
	private ValueFunctions() {
		// Prevents instantiation.
	}
}
