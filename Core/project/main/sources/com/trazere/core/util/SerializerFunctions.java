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
package com.trazere.core.util;

import com.trazere.core.functional.Function;

/**
 * The {@link SerializerFunctions} class provides various factories of {@link Function functions} related to {@link Serializer serializers}.
 * 
 * @see Function
 * @see Serializer
 * @since 2.0
 */
public class SerializerFunctions {
	/**
	 * Builds a function that serializes values using the given serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param serializer Serializer to use.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <T, R> Function<T, R> serialize(final Serializer<? super T, ? extends R> serializer) {
		return serializer::serialize;
	}
	
	/**
	 * Builds a function that deserializes values using the given serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the representations.
	 * @param serializer Deserializer to use.
	 * @return The built function.
	 * @since 2.0
	 */
	public static <T, R> Function<R, T> deserialize(final Serializer<? extends T, ? super R> serializer) {
		return serializer::deserialize;
	}
	
	private SerializerFunctions() {
		// Prevents instantiation.
	}
}
