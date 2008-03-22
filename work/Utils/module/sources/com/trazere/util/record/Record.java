/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.util.record;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * The {@link Record} interface defines unmutable collections of identified fields.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see RecordBuilder
 */
public interface Record<K, V> {
	/**
	 * Test wether the receiver record is empty or not.
	 * 
	 * @return <code>true</code> when the record is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test wether the receiver record contains a field identified by the given key or not.
	 * 
	 * @param key Key of the field to test.
	 * @return <code>true</code> when the record contains a field identified by the given key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field to read.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws RecordException When the field does not exist.
	 */
	public V get(final K key)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field to read.
	 * @param defaultValue Default value to return. May be <code>null</code>.
	 * @return The value of the field or the given default value when the field does not exist. May be <code>null</code>.
	 */
	public V get(final K key, final V defaultValue);
	
	/**
	 * Get the keys identifying the fields of the receiver record.
	 * 
	 * @return An unmodiable set of the keys identifying the fields.
	 */
	public Set<K> keys();
	
	/**
	 * Get the values of the fields of the receiver record.
	 * 
	 * @return An unmodiable collection of the values of the fields.
	 */
	public Collection<V> values();
	
	/**
	 * Get the contents of the receiver record represented as a map.
	 * 
	 * @return An unmodiable map of the values of the fields identified by their keys.
	 */
	public Map<K, V> asMap();
}
