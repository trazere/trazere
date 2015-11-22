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
package com.trazere.core.record;

/**
 * The {@link Field} interface describes fields.
 * <p>
 * Fields represent associations of a value to a key.
 * <p>
 * Field equality relies on the equality of their key and value.
 * 
 * @param <K> Type of the key.
 * @param <V> Type of the value.
 * @see FieldKey
 * @since 2.0
 */
public interface Field<K extends FieldKey<K, ?>, V> {
	/**
	 * Gets the key of this field.
	 * 
	 * @return The key.
	 * @since 2.0
	 */
	FieldKey<K, V> getKey();
	
	/**
	 * Gets the value of this field.
	 * 
	 * @return The value. May be <code>null</code>.
	 * @since 2.0
	 */
	V getValue();
}
