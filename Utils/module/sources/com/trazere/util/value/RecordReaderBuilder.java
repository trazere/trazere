/*
 *  Copyright 2006-2008 Julien Dufour
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

import java.util.Map;
import java.util.Set;

/**
 * The {@link RecordReaderBuilder} interface defines builder of {@link RecordReader record readers}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the record readers.
 */
public interface RecordReaderBuilder<K, V, R extends RecordReader<K, V>> {
	/**
	 * Add a field identified by the given key with the given value reader.
	 * 
	 * @param key Key identifying the field to add.
	 * @param value Reader of the field to add.
	 * @throws ValueException When some field is already identified by the given key.
	 */
	public void add(final K key, final ValueReader<? extends V> value)
	throws ValueException;
	
	/**
	 * Add the given fields to the receiver record reader builder.
	 * <p>
	 * No fields may be identified by any given keys in the receiver record reader builder.
	 * 
	 * @param fields Values of the fields to add identified by their keys.
	 * @throws ValueException When fields are already identified by given keys.
	 * @throws ValueException When some field cannot be added.
	 */
	public void addAll(final Map<? extends K, ? extends ValueReader<? extends V>> fields)
	throws ValueException;
	
	/**
	 * Test wether some field is identified by the given key in the receiver record reader builder or not.
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
	 * @throws ValueException When fields are already identified by keys identifying the populated fields.
	 * @throws ValueException When the given record reader builder cannot be populated.
	 */
	public <B extends RecordReaderBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws ValueException;
	
	/**
	 * Build a new record reader populated with the fields of the receiver record reader builder.
	 * 
	 * @return The new record reader.
	 * @throws ValueException When the record reader cannot be built.
	 */
	public R build()
	throws ValueException;
}
