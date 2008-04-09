/*
 *  Copyright 2008 Julien Dufour
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
	 * The receiver builder must not contain a field identified by the given key.
	 * 
	 * @param key Key identifying the field to add.
	 * @param value Value of the field to add.
	 * @throws DuplicateFieldRecordException When the builder contains a field identified by the given key.
	 * @throws RecordException When the field cannot be added.
	 */
	public void add(final K key, final V value)
	throws RecordException;
	
	/**
	 * Add fields corresponding to the fields of the given record to the receiver record builder.
	 * <p>
	 * The receiver builder must not contain fields identified by keys of the fields of the given record.
	 * 
	 * @param record Record containing the fields to add.
	 * @throws DuplicateFieldRecordException When the builder contains fields identified by keys of the fields of the given record.
	 * @throws RecordException When some field cannot be added.
	 */
	public void addAll(final Record<? extends K, ? extends V> record)
	throws RecordException;
	
	/**
	 * Add the given fields to the receiver record builder.
	 * <p>
	 * The receiver builder must not contain fields identified by keys of the given fields.
	 * 
	 * @param fields Values of the fields to add identified by their keys.
	 * @throws DuplicateFieldRecordException When the builder contains fields identified by keys of the given fields.
	 * @throws RecordException When some field cannot be added.
	 */
	public void addAll(final Map<? extends K, ? extends V> fields)
	throws RecordException;
	
	/**
	 * Test wether the receiver record builder is empty or not.
	 * 
	 * @return <code>true</code> when the record builder is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test wether the receiver record builder contains a field identified by the given key or not.
	 * 
	 * @param key Key of the field to test.
	 * @return <code>true</code> when the record builder contains a field identified by the given key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the keys of the fields of the receiver record builder.
	 * 
	 * @return An unmodiable set of the keys identifying the fields.
	 */
	public Set<K> getKeys();
	
	/**
	 * Remove the field identified by the given key from the receiver record builder.
	 * <p>
	 * The receiver builder must contain a field identified by the given key.
	 * 
	 * @param key Key of the field to remove.
	 * @throws MissingFieldRecordException When the builder does not contain a field identified by the given key.
	 * @throws RecordException When the field cannot be removed.
	 */
	public void remove(final K key)
	throws RecordException;
	
	/**
	 * Remove all fields from the receiver record.
	 * 
	 * @throws RecordException When the builder cannot be cleared.
	 */
	public void clear()
	throws RecordException;
	
	/**
	 * Populate the given record builder with the fields of the receiver record builder.
	 * <p>
	 * The given builder must not contain fields identified by keys of fields of the receiver builder.
	 * 
	 * @param <B> Type of the record builder to fill.
	 * @param builder Record builder to fill.
	 * @return The given record builder.
	 * @throws DuplicateFieldRecordException When the given builder contain fields identified by keys of the given record builder.
	 * @throws RecordException When the given builder cannot be populated.
	 */
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws RecordException;
	
	/**
	 * Populate the given record builder with the fields of the receiver record builder identified by the given keys.
	 * <p>
	 * The receiver builder must contain fields for all given keys and the given builder must not contain fields identified by given keys.
	 * 
	 * @param <B> Type of the record builder to fill.
	 * @param builder Record builder to fill.
	 * @param keys Keys of the fields to copy.
	 * @return The given record builder.
	 * @throws DuplicateFieldRecordException When the given builder contain fields identified by keys of the given record builder.
	 * @throws RecordException When the given builder cannot be populated.
	 */
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder, final Set<? extends K> keys)
	throws RecordException;
	
	/**
	 * Build a new record filled with the fields of the receiver record builder.
	 * 
	 * @return The built record.
	 * @throws RecordException When the record cannot be built.
	 */
	public R build()
	throws RecordException;
}
