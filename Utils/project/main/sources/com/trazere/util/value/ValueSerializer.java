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

/**
 * The {@link ValueSerializer} interface defines serialization/deserialization functions.
 * 
 * @param <T> Type of the values.
 * @param <R> Type of the representations.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.util.Serializer}.
 */
@Deprecated
public interface ValueSerializer<T, R, X extends Exception> {
	/**
	 * Gets the type of the values supported by the receiver serializer.
	 * 
	 * @return The Java type of the values.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public Class<T> getValueClass();
	
	/**
	 * Gets the type of the representation supported by the receiver serializer.
	 * 
	 * @return The Java type of the serialized values.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public Class<R> getRepresentationClass();
	
	/**
	 * Serializes the given value to its corresponding representation.
	 * 
	 * @param value The value.
	 * @return The representation of the value.
	 * @throws X When the serialization fails.
	 * @deprecated Use {@link com.trazere.core.util.Serializer#serialize(Object)}.
	 */
	@Deprecated
	public R serialize(final T value)
	throws X;
	
	/**
	 * Deserializes the given representation to its corresponding value.
	 * 
	 * @param representation The representation of the value.
	 * @return The value.
	 * @throws X When the deserialization fails.
	 * @deprecated Use {@link com.trazere.core.util.Serializer#deserialize(Object)}.
	 */
	@Deprecated
	public T deserialize(final R representation)
	throws X;
}
