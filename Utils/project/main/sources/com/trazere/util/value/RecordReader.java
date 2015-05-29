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

import com.trazere.util.record.InvalidFieldException;
import com.trazere.util.record.MissingFieldException;
import com.trazere.util.record.ParameterFunction;
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordBuilder;
import com.trazere.util.record.RecordSignature;
import java.util.Set;

// TODO: extends Record<K, ValueReader<? extends V>> ?
/**
 * The {@link RecordReader} interface defines record reading functions.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @deprecated To be removed.
 */
@Deprecated
public interface RecordReader<K, V>
extends ParameterFunction<String, Object, Record<K, V>, ValueException> {
	/**
	 * Test whether the records produced by the receiver reader are empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test whether some field is identified by the given key in the records produced by the receiver reader or not.
	 * 
	 * @param key Key of the field to test.
	 * @return <code>true</code> when some field is identified by the given key, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the keys identifying the fields of the records produced by the receiver reader.
	 * 
	 * @return An unmodiable set of the keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Get the value reader of the field produced by the receiver reader identified by the given key.
	 * 
	 * @param key Key of the field.
	 * @return The value reader of the field.
	 * @throws MissingFieldException When no fields are identified by the given key.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 */
	public ValueReader<? extends V> get(final K key)
	throws MissingFieldException, InvalidFieldException;
	
	/**
	 * Get the signature of the records produced by the receiver reader.
	 * 
	 * @return The signature of the record.
	 */
	public RecordSignature<K, V> getSignature();
	
	/**
	 * Read the record defined by the receiver reader using the given parameters.
	 * 
	 * @param parameters Parameters to use.
	 * @return The produced record.
	 * @throws ValueException When the record cannot be read.
	 */
	public Record<K, V> read(final Record<String, Object> parameters)
	throws ValueException;
	
	/**
	 * Populate the given record builder with the record read by the receiver reader using the given parameters.
	 * 
	 * @param <B> Type of the record builder.
	 * @param parameters Parameters to use.
	 * @param builder Record builder to populate.
	 * @return The produced record.
	 * @throws ValueException When the record cannot be read.
	 */
	public <B extends RecordBuilder<K, V, ?>> B read(final Record<String, Object> parameters, final B builder)
	throws ValueException;
	
	/**
	 * Compose the receiver and given readers.
	 * 
	 * @param reader The record reader.
	 * @return The composed value reader.
	 * @throws ValueException When the value reader cannot be composed.
	 */
	public RecordReader<K, V> compose(final RecordReader<String, Object> reader)
	throws ValueException;
}
