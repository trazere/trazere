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

import java.util.Map;
import java.util.Set;

/**
 * The {@link RecordBuilder} interface defines builders of {@link Record records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see Record
 * @deprecated Use {@link com.trazere.core.record.RecordBuilder}.
 */
// TODO: extends Accumulator or provide an accumulator factory for add
@Deprecated
public interface RecordBuilder<K, V, R extends Record<K, V>> {
	/**
	 * Adds a field identified by the given key and containing the given value to the receiver record builder.
	 * <p>
	 * No fields may be identified by the given key in the receiver record builder.
	 * 
	 * @param key The key identifying the field to add.
	 * @param value The value of the field to add.
	 * @throws DuplicateFieldException When some field is already identified by the given key.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public void add(final K key, final V value)
	throws DuplicateFieldException;
	
	/**
	 * Adds a field with the given signature and containing the given value to the receiver record builder.
	 * <p>
	 * No fields may be identified by the key of the given signature in the receiver record builder.
	 * 
	 * @param <T> Type of the value.
	 * @param field The signature of the field to add.
	 * @param value The value of the field to add.
	 * @throws DuplicateFieldException When some field is already identified by the key of the given signature.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#add(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public <T extends V> void add(final FieldSignature<K, T> field, final T value)
	throws DuplicateFieldException, NullFieldException;
	
	/**
	 * Adds the given fields to the receiver record builder.
	 * <p>
	 * No fields may be identified by the key of any given field in the receiver record builder.
	 * 
	 * @param fields The values of the fields to add identified by their keys.
	 * @throws DuplicateFieldException When some field is already identified by the key of any given field.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public void addAll(final Map<? extends K, ? extends V> fields)
	throws DuplicateFieldException;
	
	/**
	 * Adds the fields of the given record to the receiver record builder.
	 * <p>
	 * No fields may be identified by the key identifying any field of the given record builder in the receiver record builder.
	 * 
	 * @param record The record containing the fields to add.
	 * @throws InvalidFieldException When the some field of the given record cannot be read.
	 * @throws DuplicateFieldException When some field is already identified by the key identifying any field of the given record.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#addAll(com.trazere.core.record.Record)}.
	 */
	@Deprecated
	public void addAll(final Record<? extends K, ? extends V> record)
	throws InvalidFieldException, DuplicateFieldException;
	
	/**
	 * Test whether the receiver record builder is empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#isEmpty()}.
	 */
	@Deprecated
	public boolean isEmpty();
	
	/**
	 * Tests whether some field is identified by the given key in the receiver record builder or not.
	 * 
	 * @param key The key of the field.
	 * @return <code>true</code> when some field is identified by the given key, <code>false</code> otherwise.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public boolean contains(final K key);
	
	/**
	 * Tests whether some field is identified by the key and has the type of the given signature in the receiver record builder or not.
	 * 
	 * @param field The signature of the field.
	 * @return <code>true</code> when some field is identified by the key of the given signature, <code>false</code> otherwise.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#contains(com.trazere.core.record.FieldKey)}.
	 */
	@Deprecated
	public boolean contains(final FieldSignature<K, ? extends V> field);
	
	/**
	 * Gets the keys identifying the fields of the receiver record builder.
	 * 
	 * @return An unmodiable set of the keys.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#keys()}.
	 */
	@Deprecated
	public Set<K> getKeys();
	
	/**
	 * Replaces the value contained in the field identified by the given key in the receiver record builder.
	 * 
	 * @param key The key of the field to replace.
	 * @param value The new value of the field.
	 * @return <code>true</code> when some value was replaced, <code>false</code> when the field was added.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#set(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public boolean replace(final K key, final V value);
	
	/**
	 * Replaces the value contained in the field identified by the given key in the receiver record builder.
	 * 
	 * @param <T> Type of the value.
	 * @param field The signature of the field to replace.
	 * @param value The new value of the field.
	 * @return <code>true</code> when some value was replaced, <code>false</code> when the field was added.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#set(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public <T extends V> boolean replace(final FieldSignature<K, T> field, final T value)
	throws NullFieldException;
	
	/**
	 * Removes the field identified by the given key from the receiver record builder.
	 * 
	 * @param key The key of the field to remove.
	 * @return <code>true</code> when some field was removed, <code>false</code> otherwise.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public boolean remove(final K key);
	
	/**
	 * Removes all fields from the receiver record builder.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#clear()}.
	 */
	@Deprecated
	public void clear();
	
	/**
	 * Populates the given record builder with the fields of the receiver record builder.
	 * <p>
	 * No fields may be identified by the key identifying any field of the receiver record builder in the given record builder.
	 * 
	 * @param <B> Type of the record builder to populate.
	 * @param builder The record builder to populate.
	 * @return The given record builder.
	 * @throws DuplicateFieldException When some field is already identified by the key identifying any populated field.
	 * @deprecated To be removed.
	 */
	// TODO: use Accumulator ?
	@Deprecated
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws DuplicateFieldException;
	
	/**
	 * Populates the given record builder with the fields of the receiver record builder identified by the given keys.
	 * <p>
	 * No fields may be identified by any given key in the given record builder and the a field must be identified by every given key in the receiver record
	 * builder.
	 * 
	 * @param <B> Type of the record builder to populate.
	 * @param builder The record builder to populate.
	 * @param keys The keys of the fields to populate.
	 * @return The given record builder.
	 * @throws MissingFieldException When some field is missing.
	 * @throws DuplicateFieldException When some field is already identified by any given key.
	 * @deprecated To be removed.
	 */
	// TODO: use Accumulator ?
	@Deprecated
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder, final Set<? extends K> keys)
	throws MissingFieldException, DuplicateFieldException;
	
	/**
	 * Builds a new record populated with the fields of the receiver record builder.
	 * 
	 * @return The built record.
	 * @throws RecordException When the record cannot be built.
	 * @deprecated Use {@link com.trazere.core.record.RecordBuilder#build()}.
	 */
	@Deprecated
	public R build()
	throws RecordException;
}
