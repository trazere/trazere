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
package com.trazere.util.record;

import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * The {@link Record} interface defines unmutable records.
 * <p>
 * A record is a collection of fields uniquely identified by keys.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see RecordBuilder
 */
public interface Record<K, V> {
	/**
	 * Test whether the receiver record is empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test whether some field is identified by the given key in the receiver record or not.
	 * 
	 * @param key Key of the field to test.
	 * @return <code>true</code> when some field is identified by the given key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the keys identifying the fields of the receiver record.
	 * 
	 * @return An unmodiable set of the keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the given key.
	 * @throws RecordException When the field cannot be got.
	 */
	public V get(final K key)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws RecordException When the field cannot be got.
	 */
	public V get(final K key, final V defaultValue)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @return The value of the field.
	 * @throws RecordException When the field cannot be got.
	 */
	public Maybe<V> getMaybe(final K key)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key according to the given type.
	 * 
	 * @param <T> Type of the value.
	 * @param key Key of the field.
	 * @param type Type of the value.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the given key.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws RecordException When the field cannot be got.
	 */
	public <T extends V> T getTyped(final K key, final Class<T> type)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key according to the given type.
	 * 
	 * @param <T> Type of the value.
	 * @param key Key of the field.
	 * @param type Type of the value.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws RecordException When the field cannot be got.
	 */
	public <T extends V> T getTyped(final K key, final Class<T> type, final T defaultValue)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key according to the given type.
	 * 
	 * @param <T> Type of the value.
	 * @param key Key of the field.
	 * @param type Type of the value.
	 * @return The value of the field.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws RecordException When the field cannot be got.
	 */
	public <T extends V> Maybe<T> getTypedMaybe(final K key, final Class<T> type)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <T> Type of the value.
	 * @param signature Signature of the field.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the key of the given signature.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws RecordException When the field cannot be got.
	 */
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <T> Type of the value.
	 * @param signature Signature of the field.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field or the given default value when no fields are identified by the key of the given signature. May be <code>null</code>.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws RecordException When the field cannot be got.
	 */
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature, final T defaultValue)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <T> Type of the value.
	 * @param signature Signature of the field.
	 * @return The value of the field.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws RecordException When the field cannot be got.
	 */
	public <T extends V> Maybe<T> getTypedMaybe(final FieldSignature<? extends K, T> signature)
	throws RecordException;
	
	/**
	 * Get the values of the fields of the receiver record.
	 * 
	 * @return An unmodiable collection of the values of the fields.
	 * @throws RecordException When the values of the fields cannot by got.
	 */
	public Collection<V> getValues()
	throws RecordException;
	
	/**
	 * Get a view of the fields of the receiver record as a map.
	 * 
	 * @return An unmodiable map of the values of the fields identified by their keys.
	 * @throws RecordException When the view cannot be built.
	 */
	public Map<K, V> asMap()
	throws RecordException;
}
