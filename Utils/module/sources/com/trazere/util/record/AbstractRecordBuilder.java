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

import com.trazere.util.Assert;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link AbstractRecordBuilder} abstract class implements skeletons of builders of {@link Record records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see Record
 */
public abstract class AbstractRecordBuilder<K, V, R extends Record<K, V>>
implements RecordBuilder<K, V, R>, Describable {
	/** Values of the fields of the builder identified by their keys. */
	protected final Map<K, V> _fields;
	
	/**
	 * Instantiate a new empty record builder.
	 */
	public AbstractRecordBuilder() {
		// Initialization.
		_fields = new HashMap<K, V>();
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 * @throws RecordException When the record cannot be read.
	 */
	public AbstractRecordBuilder(final Record<? extends K, ? extends V> record)
	throws RecordException {
		Assert.notNull(record);
		
		// Initialization.
		_fields = new HashMap<K, V>(record.asMap());
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @throws RecordException When the record builder cannot be read.
	 */
	public AbstractRecordBuilder(final RecordBuilder<? extends K, ? extends V, ?> builder)
	throws RecordException {
		Assert.notNull(builder);
		
		// Populate.
		_fields = new HashMap<K, V>();
		builder.populate(this);
	}
	
	/**
	 * Instantiate a new record builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 */
	public AbstractRecordBuilder(final Map<? extends K, ? extends V> fields) {
		Assert.notNull(fields);
		
		// Initialization.
		_fields = new HashMap<K, V>(fields);
	}
	
	public void add(final K key, final V value)
	throws RecordException {
		Assert.notNull(key);
		
		// Add the field.
		if (!_fields.containsKey(key)) {
			_fields.put(key, value);
		} else {
			throw new DuplicateFieldRecordException("Field \"" + key + "\" already exists in builder " + this);
		}
	}
	
	public void addAll(final Record<? extends K, ? extends V> record)
	throws RecordException {
		Assert.notNull(record);
		
		// Add the fields.
		addAll(record.asMap());
	}
	
	public void addAll(final Map<? extends K, ? extends V> fields)
	throws RecordException {
		Assert.notNull(fields);
		
		// Add the fields.
		for (final Map.Entry<? extends K, ? extends V> entry : fields.entrySet()) {
			final K key = entry.getKey();
			if (!_fields.containsKey(key)) {
				_fields.put(key, entry.getValue());
			} else {
				throw new DuplicateFieldRecordException("Field \"" + key + "\" already exists in builder " + this);
			}
		}
	}
	
	public boolean isEmpty() {
		return _fields.isEmpty();
	}
	
	public boolean contains(final K key) {
		Assert.notNull(key);
		
		// Test.
		return _fields.containsKey(key);
	}
	
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_fields.keySet());
	}
	
	public void remove(final K key)
	throws RecordException {
		Assert.notNull(key);
		
		// Remove the field.
		if (_fields.containsKey(key)) {
			_fields.remove(key);
		} else {
			throw new MissingFieldRecordException("Field \"" + key + "\" does not exist in builder " + this);
		}
	}
	
	public void clear() {
		// Remove all fields.
		_fields.clear();
	}
	
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws RecordException {
		Assert.notNull(builder);
		
		// Fill.
		builder.addAll(Collections.unmodifiableMap(_fields));
		
		return builder;
	}
	
	public <B extends RecordBuilder<? super K, ? super V, ?>> B populate(final B builder, final Set<? extends K> keys)
	throws RecordException {
		Assert.notNull(builder);
		Assert.notNull(keys);
		
		// Fill.
		for (final K key : keys) {
			if (_fields.containsKey(key)) {
				builder.add(key, _fields.get(key));
			} else {
				throw new MissingFieldRecordException("Field \"" + key + "\" does not exist in builder " + this);
			}
		}
		
		return builder;
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Values = ").append(_fields);
	}
}