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
package com.trazere.util.record;

import java.util.Map;
import java.util.Set;

/**
 * The {@link RecordBuilder} interface defines builders of {@link Record records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see Record
 */
public interface RecordBuilder<K, V, R extends Record<K, V>> {
	/**
	 * Add a field identified by the given key and containing the given value to the receiver record builder.
	 * <p>
	 * No fields may be identified by the given key in the receiver record builder.
	 * 
	 * @param key The key identifying the field to add.
	 * @param value The value of the field to add.
	 * @throws DuplicateFieldException When some field is already identified by the given key.
	 * @throws RecordException When the field cannot be added.
	 */
	public void add(final K key, final V value)
	throws RecordException;
	
	/**
	 * Add a field with the given signature and containing the given value to the receiver record builder.
	 * <p>
	 * No fields may be identified by the key of the given signature in the receiver record builder.
	 * 
	 * @param <T> Type of the value.
	 * @param field The signature of the field to add.
	 * @param value The value of the field to add.
	 * @throws DuplicateFieldException When some field is already identified by the key of the given signature.
	 * @throws RecordException When the field cannot be added.
	 */
	public <T extends V> void add(final FieldSignature<K, T> field, final T value)
	throws RecordException;
	
	/**
	 * Add the given fields to the receiver record builder.
	 * <p>
	 * No fields may be identified by the key of any given field in the receiver record builder.
	 * 
	 * @param fields The values of the fields to add identified by their keys.
	 * @throws DuplicateFieldException When some field is already identified by the key of any given field.
	 * @throws RecordException When some field cannot be added.
	 */
	public void addAll(final Map<? extends K, ? extends V> fields)
	throws RecordException;
	
	/**
	 * Add the fields of the given record to the receiver record builder.
	 * <p>
	 * No fields may be identified by the key identifying any field of the given record builder in the receiver record builder.
	 * 
	 * @param record The record containing the fields to add.
	 * @throws DuplicateFieldException When some field is already identified by the key identifying any field of the given record.
	 * @throws RecordException When some field cannot be added.
	 */
	public void addAll(final Record<? extends K, ? extends V> record)
	throws RecordException;
	
	/**
	 * Test whether the receiver record builder is empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test whether some field is identified by the given key in the receiver record builder or not.
	 * 
	 * @param key The key of the field.
	 * @return <code>true</code> when some field is identified by the given key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Test whether some field is identified by the key of the given signature in the receiver record builder or not.
	 * 
	 * @param field The signature of the field.
	 * @return <code>true</code> when some field is identified by the key of the given signature, <code>false</code> otherwise.
	 */
	public boolean contains(final FieldSignature<K, ? extends V> field);
	
	/**
	 * Get the keys identifying the fields of the receiver record builder.
	 * 
	 * @return An unmodiable set of the keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Remove the field identified by the given key from the receiver record builder.
	 * <p>
	 * Some field must by identified be the given key in the receiver record builder.
	 * 
	 * @param key The key of the field to remove.
	 * @throws MissingFieldException When no fields are identified by the given key.
	 * @throws RecordException When the field cannot be removed.
	 */
	public void remove(final K key)
	throws RecordException;
	
	/**
	 * Remove the field identified by the given signature from the receiver record builder.
	 * <p>
	 * Some field must by identified be the key of the given signature in the receiver record builder.
	 * 
	 * @param field The signature of the field to remove.
	 * @throws MissingFieldException When no fields are identified by the key of the given signature.
	 * @throws RecordException When the field cannot be removed.
	 */
	public void remove(final FieldSignature<K, ? extends V> field)
	throws RecordException;
	
	/**
	 * Remove all fields from the receiver record builder.
	 * 
	 * @throws RecordException When the record builder cannot be cleared.
	 */
	public void clear()
	throws RecordException;
	
	/**
	 * Populate the given record builder with the fields of the receiver record builder.
	 * <p>
	 * No fields may be identified by the key identifying any field of the receiver record builder in the given record builder.
	 * 
	 * @param <B> Type of the record builder to populate.
	 * @param builder The record builder to populate.
	 * @return The given record builder.
	 * @throws DuplicateFieldException When some field is already identified by the key identifying any populated field.
	 * @throws RecordException When the given record builder cannot be populated.
	 */
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws RecordException;
	
	/**
	 * Populate the given record builder with the fields of the receiver record builder identified by the given keys.
	 * <p>
	 * No fields may be identified by any given key in the given record builder and the a field must be identified by every given key in the receiver record
	 * builder.
	 * 
	 * @param <B> Type of the record builder to populate.
	 * @param builder The record builder to populate.
	 * @param keys The keys of the fields to populate.
	 * @return The given record builder.
	 * @throws DuplicateFieldException When some field is already identified by any given key.
	 * @throws RecordException When the given record builder cannot be populated.
	 */
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder, final Set<? extends K> keys)
	throws RecordException;
	
	/**
	 * Build a new record populated with the fields of the receiver record builder.
	 * 
	 * @return The built record.
	 * @throws RecordException When the record cannot be built.
	 */
	public R build()
	throws RecordException;
}
