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
package com.trazere.core.util;

import com.trazere.core.lang.ThrowableFactory;

/**
 * The {@link BaseSerializer} class provides a skeleton implementation of {@link Serializer serializers}.
 * 
 * @param <V> Type of the values.
 * @param <R> Type of the representations.
 */
public abstract class BaseSerializer<V, R>
implements Serializer<V, R> {
	/** Factory of the serialization/deserialization failures. */
	protected final ThrowableFactory<? extends RuntimeException> _failureFactory;
	
	/**
	 * Instantiates a new serializer.
	 */
	public BaseSerializer() {
		this(SerializerException.FACTORY);
	}
	
	/**
	 * Instantiates a new serializer.
	 * 
	 * @param failureFactory Factory of the serialization/deserialization failures.
	 */
	public BaseSerializer(final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != failureFactory;
		
		// Initialization.
		_failureFactory = failureFactory;
	}
	
	@Override
	public R serialize(final V value) {
		try {
			return innerSerialize(value);
		} catch (final Exception exception) {
			throw _failureFactory.build("Failed serializing value \"" + value + "\"", exception);
		}
	}
	
	/**
	 * Serializes the given value to its corresponding representation.
	 * 
	 * @param value Value to serialize.
	 * @return The representation of the value.
	 * @throws Exception When the serialization fails.
	 */
	protected abstract R innerSerialize(V value)
	throws Exception;
	
	@Override
	public V deserialize(final R representation) {
		try {
			return innerDeserialize(representation);
		} catch (final Exception exception) {
			throw _failureFactory.build("Failed deserializing representation \"" + representation + "\"", exception);
		}
	}
	
	/**
	 * Deserializes the given representation to its corresponding value.
	 * 
	 * @param representation Representation of the value to deserialize.
	 * @return The value.
	 * @throws Exception When the deserialization fails.
	 */
	protected abstract V innerDeserialize(R representation)
	throws Exception;
}
