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

import java.util.Map;

// TODO: inherit from Record ??

/**
 * The {@link RecordBuilder} interface defines builders of {@link Record records}.
 * <p>
 * Record builders behave like mutable records with a {@link #build() snapshot} method.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see Record
 */
public interface RecordBuilder<K, V, R extends Record<K, V>> {
	/**
	 * Add a new field identified by the given key and containing the given value to the receiver record.
	 * 
	 * @param key Key identifying the new field.
	 * @param value Value of the new field.
	 * @throws RecordException When the field already exists.
	 */
	public void add(final K key, final V value)
	throws RecordException;
	
	/**
	 * Add new fields corresponding the contents of the given record to the receiver record.
	 * 
	 * @param record Record containing the new fields to add.
	 * @throws RecordException When some field already exists.
	 */
	public void addAll(final Record<? extends K, ? extends V> record)
	throws RecordException;
	
	/**
	 * Add new fields corresponding the contents of the given record builder to the receiver record.
	 * 
	 * @param builder Record builder containing the new fields to add.
	 * @throws RecordException When some field already exists.
	 */
	public void addAll(final RecordBuilder<? extends K, ? extends V, ?> builder)
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
	 * Get the value of the field of the receiver record builder identified by the given key.
	 * 
	 * @param key Key of the field to read.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws RecordException When the field does not exist.
	 */
	public V get(final K key)
	throws RecordException;
	
	/**
	 * Get the value of the field of the receiver record builder identified by the given key.
	 * 
	 * @param key Key of the field to read.
	 * @param defaultValue Default value to return. May be <code>null</code>.
	 * @return The value of the field or the given default value when the field does not exist. May be <code>null</code>.
	 */
	public V get(final K key, final V defaultValue);
	
	/**
	 * Get the contents of the receiver record represented as a map.
	 * 
	 * @return An unmodiable map of the values of the fields identified by their keys.
	 */
	public Map<K, V> asMap();
	
	/**
	 * Set the given value in the field identified by the given key in the receiver record builder.
	 * <p>
	 * A new field is created if needed.
	 * 
	 * @param key Key of the field to set.
	 * @param value Value of the field. May be <code>null</code>.
	 */
	public void set(final K key, final V value);
	
	/**
	 * Set the fields corresponding to the contents of the given record in the receiver record builder.
	 * <p>
	 * New fields are created if needed.
	 * 
	 * @param record Record containing the fields to set.
	 */
	public void setAll(final Record<? extends K, ? extends V> record);
	
	/**
	 * Set the fields corresponding to the contents of the given record builder in the receiver record builder.
	 * <p>
	 * New fields are created if needed.
	 * 
	 * @param builder Record builder containing the fields to set.
	 */
	public void setAll(final RecordBuilder<? extends K, ? extends V, ?> builder);
	
	/**
	 * Remove the field identified by the given key from the receiver record builder.
	 * 
	 * @param key Key of the field to remove.
	 * @throws RecordException When the field does not exist.
	 */
	public void remove(final K key)
	throws RecordException;
	
	/**
	 * Remove the field identified by the given key from the receiver record builder if it exists.
	 * 
	 * @param key Key of the field to remove.
	 */
	public void clear(final K key);
	
	/**
	 * Remove all fields from the receiver record.
	 */
	public void clear();
	
	/**
	 * Build a new record with the contents of the receiver record builder.
	 * 
	 * @return The built record.
	 */
	public R build();
}
