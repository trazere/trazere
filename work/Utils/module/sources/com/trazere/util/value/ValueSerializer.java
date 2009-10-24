/*
 *  Copyright 2006-2009 Julien Dufour
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
 * The {@link ValueSerializer} interface defines functions for serializing/deserializing values to/from string representations.
 * 
 * @param <T> Type of the values.
 * @param <X> Type of the exceptions.
 */
public interface ValueSerializer<T, X extends Exception> {
	/**
	 * Get the type of the values serialized by the receiver reader.
	 * 
	 * @return The Java type of the values.
	 */
	public Class<T> getType();
	
	/**
	 * Serialize the given value to its corresponding string representation.
	 * 
	 * @param value The value.
	 * @return The representation of the value.
	 * @throws X When the serialization fails.
	 */
	public String serialize(final T value)
	throws X;
	
	/**
	 * Deserialize the given string representation to its corresponding value.
	 * 
	 * @param representation The representation of the value.
	 * @return The value.
	 * @throws X When the deserialization fails.
	 */
	public T deserialize(final String representation)
	throws X;
}
