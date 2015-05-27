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
package com.trazere.core.record;

import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleRecord} class provides a simple implementation of records.
 * 
 * @param <K> Witness type of the field keys.
 */
public class SimpleRecord<K extends FieldKey<? extends K, ?>>
extends BaseRecord<K> {
	/** Fields identified by their keys. */
	protected Map<FieldKey<? extends K, ?>, Field<? extends K, ?>> _fields;
	
	/**
	 * Instantiates a new record with the given fields.
	 * 
	 * @param fields Values of the fields identified by their keys.
	 */
	protected SimpleRecord(final Map<FieldKey<? extends K, ?>, Field<? extends K, ?>> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = Collections.unmodifiableMap(fields);
	}
	
	@Override
	public int size() {
		return _fields.size();
	}
	
	@Override
	public boolean isEmpty() {
		return _fields.isEmpty();
	}
	
	@Override
	public boolean contains(final FieldKey<? extends K, ?> key) {
		return _fields.containsKey(key);
	}
	
	@Override
	public Set<? extends FieldKey<? extends K, ?>> keys() {
		return _fields.keySet();
	}
	
	@Override
	public <V> Maybe<V> get(final FieldKey<? extends K, V> key) {
		if (_fields.containsKey(key)) {
			@SuppressWarnings("unchecked")
			final V value = (V) _fields.get(key).getValue();
			return Maybe.some(value);
		} else {
			return Maybe.none();
		}
	}
	
	@Override
	public <V> V getOptional(final FieldKey<? extends K, V> key, final V defaultValue) {
		if (_fields.containsKey(key)) {
			@SuppressWarnings("unchecked")
			final V value = (V) _fields.get(key).getValue();
			return value;
		} else {
			return defaultValue;
		}
	}
	
	@Override
	public <V> V getMandatory(final FieldKey<? extends K, V> key)
	throws MissingFieldException {
		if (_fields.containsKey(key)) {
			@SuppressWarnings("unchecked")
			final V value = (V) _fields.get(key).getValue();
			return value;
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
	}
	
	@Override
	public Collection<Field<? extends K, ?>> fields() {
		return _fields.values();
	}
}
