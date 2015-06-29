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
 * @deprecated Use {@link com.trazere.core.record.Record}.
 */
@Deprecated
public interface Record<K, V> {
	/**
	 * Test whether the receiver record is empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.record.Record#isEmpty()}.
	 */
	@Deprecated
	public boolean isEmpty();
	
	/**
	 * Test whether some field is identified by the given key in the receiver record or not.
	 * 
	 * @param key Key of the field to test.
	 * @return <code>true</code> when some field is identified by the given key, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.record.Record#contains(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public boolean contains(final K key);
	
	/**
	 * Get the keys identifying the fields of the receiver record.
	 * 
	 * @return An unmodiable set of the keys.
	 * @deprecated Use {@link com.trazere.core.record.Record#keys()}.
	 */
	@Deprecated
	public Set<K> getKeys();
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the given key.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @deprecated Use {@link com.trazere.core.record.Record#getMandatory(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public V get(final K key)
	throws MissingFieldException, InvalidFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @deprecated Use {@link com.trazere.core.record.Record#getOptional(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public V get(final K key, final V defaultValue)
	throws InvalidFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @return The value of the field.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @deprecated Use {@link com.trazere.core.record.Record#get(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public Maybe<V> getMaybe(final K key)
	throws InvalidFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key according to the given type.
	 * 
	 * @param <T> Type of the value.
	 * @param key Key of the field.
	 * @param type Type of the value.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the given key.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @deprecated Use {@link com.trazere.core.record.Record#getMandatory(com.trazere.core.record.FieldKey).
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public <T extends V> T getTyped(final K key, final Class<T> type)
	throws MissingFieldException, InvalidFieldException, IncompatibleFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key according to the given type.
	 * 
	 * @param <T> Type of the value.
	 * @param key Key of the field.
	 * @param type Type of the value.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @deprecated Use {@link com.trazere.core.record.Record#getOptional(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public <T extends V> T getTyped(final K key, final Class<T> type, final T defaultValue)
	throws InvalidFieldException, IncompatibleFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given key according to the given type.
	 * 
	 * @param <T> Type of the value.
	 * @param key Key of the field.
	 * @param type Type of the value.
	 * @return The value of the field.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @deprecated Use {@link com.trazere.core.record.Record#get(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public <T extends V> Maybe<T> getTypedMaybe(final K key, final Class<T> type)
	throws InvalidFieldException, IncompatibleFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <T> Type of the value.
	 * @param signature Signature of the field.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the key of the given signature.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws NullFieldException When the value is null and the field is not nullable.
	 * @deprecated Use {@link com.trazere.core.record.Record#getMandatory(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature)
	throws MissingFieldException, InvalidFieldException, IncompatibleFieldException, NullFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <T> Type of the value.
	 * @param signature Signature of the field.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field or the given default value when no fields are identified by the key of the given signature. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws NullFieldException When the value is null and the field is not nullable.
	 * @deprecated Use {@link com.trazere.core.record.Record#getOptional(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature, final T defaultValue)
	throws InvalidFieldException, IncompatibleFieldException, NullFieldException;
	
	/**
	 * Get the value of the field of the receiver record identified by the given signature.
	 * 
	 * @param <T> Type of the value.
	 * @param signature Signature of the field.
	 * @return The value of the field.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the given type.
	 * @throws NullFieldException When the value is null and the field is not nullable.
	 * @deprecated Use {@link com.trazere.core.record.Record#get(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public <T extends V> Maybe<T> getTypedMaybe(final FieldSignature<? extends K, T> signature)
	throws InvalidFieldException, IncompatibleFieldException, NullFieldException;
	
	/**
	 * Get the values of the fields of the receiver record.
	 * 
	 * @return An unmodiable collection of the values of the fields.
	 * @throws InvalidFieldException When the value of some field cannot be read.
	 * @deprecated Use {@link com.trazere.core.record.Record#fields()}.
	 */
	@Deprecated
	public Collection<V> getValues()
	throws InvalidFieldException;
	
	// TODO: add populate methods similar to those of record builder
	
	/**
	 * Get a view of the fields of the receiver record as a map.
	 * 
	 * @return An unmodiable map of the values of the fields identified by their keys.
	 * @throws InvalidFieldException When some field cannot be read.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public Map<K, V> asMap()
	throws InvalidFieldException;
}
