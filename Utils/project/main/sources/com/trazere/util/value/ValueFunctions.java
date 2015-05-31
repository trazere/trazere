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
package com.trazere.util.value;

import com.trazere.util.function.Function1;

/**
 * The {@link ValueFunctions} class provides various factories of functions related to values.
 * 
 * @deprecated Use core.
 */
@Deprecated
public class ValueFunctions {
	/**
	 * Builds a function that serializes values using the given serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param <X> Type of the exceptions.
	 * @param serializer The serializer.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.util.SerializerFunctions#serialize(com.trazere.core.util.Serializer)}.
	 */
	@Deprecated
	public static <T, R, X extends Exception> Function1<T, R, X> serialize(final ValueSerializer<? super T, ? extends R, ? extends X> serializer) {
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
	 * @deprecated Use {@link com.trazere.core.util.SerializerFunctions#deserialize(com.trazere.core.util.Serializer)}.
	 */
	@Deprecated
	public static <T, R, X extends Exception> Function1<R, T, X> deserialize(final ValueSerializer<? extends T, ? super R, ? extends X> serializer) {
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
