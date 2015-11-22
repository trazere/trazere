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
package com.trazere.core.record;

import com.trazere.core.collection.MapUtils;
import com.trazere.core.util.Maybe;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleRecord} class provides a simple implementation of {@link Record records}.
 * 
 * @param <K> Type of the field keys.
 * @since 2.0
 */
public class SimpleRecord<K extends FieldKey<K, ?>>
extends BaseRecord<K> {
	/**
	 * Fields identified by their keys.
	 * 
	 * @since 2.0
	 */
	protected Map<FieldKey<K, ?>, Field<K, ?>> _fields;
	
	/**
	 * Instantiates a new record with the given fields.
	 * 
	 * @param fields Values of the fields identified by their keys.
	 * @since 2.0
	 */
	protected SimpleRecord(final Map<FieldKey<K, ?>, Field<K, ?>> fields) {
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
	public boolean contains(final FieldKey<K, ?> key) {
		return _fields.containsKey(key);
	}
	
	@Override
	public Set<? extends FieldKey<K, ?>> keys() {
		return _fields.keySet();
	}
	
	@Override
	public RecordSignature<K> getSignature() {
		return new SimpleRecordSignature<>(_fields.keySet());
	}
	
	@Override
	public <V> Maybe<V> get(final FieldKey<K, V> key) {
		return MapUtils.get(_fields, key).map(field -> key.castValue(field.getValue()));
	}
	
	@Override
	public Collection<Field<K, ?>> fields() {
		return _fields.values();
	}
}
