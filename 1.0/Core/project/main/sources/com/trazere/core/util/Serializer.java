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

/**
 * The {@link Serializer} interface defines serialization/deserialization functions.
 * 
 * @param <V> Type of the values.
 * @param <R> Type of the representations.
 */
public interface Serializer<V, R> {
	/**
	 * Serializes the given value to its corresponding representation.
	 * 
	 * @param value Value to serialize.
	 * @return The representation of the value.
	 */
	R serialize(V value);
	
	/**
	 * Deserializes the given representation to its corresponding value.
	 * 
	 * @param representation Representation of the value to deserialize.
	 * @return The value.
	 */
	V deserialize(R representation);
}
