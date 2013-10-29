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
package com.trazere.util.value;

import com.trazere.util.lang.Factory;
import com.trazere.util.record.DuplicateFieldException;
import com.trazere.util.record.FieldSignature;
import com.trazere.util.record.RecordException;
import java.util.Map;
import java.util.Set;

/**
 * The {@link RecordReaderBuilder} interface defines builder of {@link RecordReader record readers}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the record readers.
 */
// TODO: extends RecordBuilder
public interface RecordReaderBuilder<K, V, R extends RecordReader<K, V>>
extends Factory<R, RecordException> {
	/**
	 * Add a field identified by the given key with the given value reader.
	 * 
	 * @param key Key identifying the field to add.
	 * @param value Reader of the field to add.
	 * @throws DuplicateFieldException When some field is already identified by the given key.
	 */
	public void add(final K key, final ValueReader<? extends V> value)
	throws DuplicateFieldException;
	
	/**
	 * Add a field identified by the given key with the given value reader.
	 * 
	 * @param <T> Type of the value.
	 * @param field Signature of the field to add.
	 * @param value Reader of the field to add.
	 * @throws DuplicateFieldException When some field is already identified by the given key.
	 */
	public <T extends V> void add(final FieldSignature<K, T> field, final ValueReader<? extends T> value)
	throws DuplicateFieldException;
	
	/**
	 * Add the given fields to the receiver record reader builder.
	 * <p>
	 * No fields may be identified by any given keys in the receiver record reader builder.
	 * 
	 * @param fields Values of the fields to add identified by their keys.
	 * @throws DuplicateFieldException When fields are already identified by given keys.
	 */
	public void addAll(final Map<? extends K, ? extends ValueReader<? extends V>> fields)
	throws DuplicateFieldException;
	
	/**
	 * Test whether some field is identified by the given key in the receiver record reader builder or not.
	 * 
	 * @param key Key of the field.
	 * @return <code>true</code> when some field is identified by the given key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the keys identifying the fields of the receiver record reader builder.
	 * 
	 * @return An unmodiable set of the keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Populate the given record reader builder with the fields of the receiver record reader builder.
	 * <p>
	 * No fields may be identified by any key identifying the fields of the receiver record reader builder in the given record reader builder.
	 * 
	 * @param <B> Type of the record reader builder to populate.
	 * @param builder Record reader builder to populate.
	 * @return The given record reader builder.
	 * @throws DuplicateFieldException When fields are already identified by keys identifying the populated fields.
	 */
	public <B extends RecordReaderBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws DuplicateFieldException;
	
	/**
	 * Build a new record reader populated with the fields of the receiver record reader builder.
	 * 
	 * @return The new record reader.
	 */
	@Override
	public R build();
}
