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

import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link AbstractSimpleRecordBuilder} abstract class implements skeletons of builders of {@link Record records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public abstract class AbstractSimpleRecordBuilder<K, V, R extends Record<K, V>>
implements RecordBuilder<K, V, R>, Describable {
	/** Values of the fields identified by their keys. */
	protected final Map<K, V> _fields;
	
	/**
	 * Instantiate a new empty record builder.
	 */
	public AbstractSimpleRecordBuilder() {
		// Initialization.
		_fields = new HashMap<K, V>();
	}
	
	/**
	 * Instantiate a new record builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 */
	public AbstractSimpleRecordBuilder(final Map<? extends K, ? extends V> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = new HashMap<K, V>(fields);
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 * @throws RecordException When the given record cannot be read.
	 */
	public AbstractSimpleRecordBuilder(final Record<? extends K, ? extends V> record)
	throws RecordException {
		assert null != record;
		
		// Initialization.
		_fields = new HashMap<K, V>(record.asMap());
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @throws RecordException When the given record builder cannot populate the new record builder.
	 */
	public AbstractSimpleRecordBuilder(final RecordBuilder<? extends K, ? extends V, ?> builder)
	throws RecordException {
		assert null != builder;
		
		// Populate.
		_fields = new HashMap<K, V>();
		builder.populate(this);
	}
	
	public void add(final K key, final V value)
	throws DuplicateFieldException {
		assert null != key;
		
		// Add the field.
		if (!_fields.containsKey(key)) {
			_fields.put(key, value);
		} else {
			throw new DuplicateFieldException("Field \"" + key + "\" already exists in builder " + this);
		}
	}
	
	public <T extends V> void add(final FieldSignature<K, T> field, final T value)
	throws RecordException {
		assert null != field;
		
		// Add the field.
		add(field.getKey(), value);
	}
	
	public void addAll(final Map<? extends K, ? extends V> fields)
	throws DuplicateFieldException {
		assert null != fields;
		
		// Add the fields.
		for (final Map.Entry<? extends K, ? extends V> entry : fields.entrySet()) {
			final K key = entry.getKey();
			if (!_fields.containsKey(key)) {
				_fields.put(key, entry.getValue());
			} else {
				throw new DuplicateFieldException("Field \"" + key + "\" already exists in builder " + this);
			}
		}
	}
	
	public void addAll(final Record<? extends K, ? extends V> record)
	throws RecordException {
		assert null != record;
		
		// Add the fields.
		addAll(record.asMap());
	}
	
	public boolean isEmpty() {
		return _fields.isEmpty();
	}
	
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _fields.containsKey(key);
	}
	
	public boolean contains(final FieldSignature<K, ?> field) {
		assert null != field;
		
		// Test.
		return contains(field.getKey());
	}
	
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_fields.keySet());
	}
	
	public void remove(final K key)
	throws MissingFieldException {
		assert null != key;
		
		// Remove the field.
		if (_fields.containsKey(key)) {
			_fields.remove(key);
		} else {
			throw new MissingFieldException("Field \"" + key + "\" does not exist in builder " + this);
		}
	}
	
	public void remove(final FieldSignature<K, ?> field)
	throws RecordException {
		assert null != field;
		
		// Remote the field.
		remove(field.getKey());
	}
	
	public void clear() {
		// Remove all fields.
		_fields.clear();
	}
	
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws RecordException {
		assert null != builder;
		
		// Populate.
		builder.addAll(Collections.unmodifiableMap(_fields));
		
		return builder;
	}
	
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder, final Set<? extends K> keys)
	throws RecordException {
		assert null != builder;
		assert null != keys;
		
		// Populate.
		for (final K key : keys) {
			if (_fields.containsKey(key)) {
				builder.add(key, _fields.get(key));
			} else {
				throw new MissingFieldException("Field \"" + key + "\" does not exist in builder " + this);
			}
		}
		
		return builder;
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		for (final Map.Entry<K, V> entry : _fields.entrySet()) {
			description.append(entry.getKey().toString(), entry.getValue());
		}
	}
}
