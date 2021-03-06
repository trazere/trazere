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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// TODO: rename to MapRecordBuilder ?

/**
 * The {@link BaseRecordBuilder} class provides a skeleton implementation of {@link RecordBuilder record builders}.
 * 
 * @param <K> Type of the field keys.
 * @param <R> Type of the records.
 * @since 2.0
 */
public abstract class BaseRecordBuilder<K extends FieldKey<K, ?>, R extends Record<K>>
implements RecordBuilder<K, R> {
	/**
	 * Fields identified by their keys.
	 * 
	 * @since 2.0
	 */
	protected Map<FieldKey<K, ?>, Field<K, ?>> _fields = new HashMap<>();
	
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
	public void add(final Field<K, ?> field)
	throws DuplicateFieldException {
		final FieldKey<K, ?> key = field.getKey();
		if (!_fields.containsKey(key)) {
			set(field);
		} else {
			throw new DuplicateFieldException("Conflicting field for \"" + key + "\"");
		}
	}
	
	@Override
	public void complete(final Field<K, ?> field) {
		if (!_fields.containsKey(field.getKey())) {
			set(field);
		}
	}
	
	@Override
	public void set(final Field<K, ?> field) {
		_fields.put(field.getKey(), field);
	}
	
	@Override
	public void remove(final FieldKey<K, ?> key) {
		_fields.remove(key);
	}
	
	@Override
	public void clear() {
		_fields.clear();
	}
}
