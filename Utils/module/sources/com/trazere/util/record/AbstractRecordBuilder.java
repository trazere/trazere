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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.trazere.util.Assert;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link AbstractRecordBuilder} abstract class provides a skeleton for record builder implementations
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public abstract class AbstractRecordBuilder<K, V, R extends Record<K, V>>
implements RecordBuilder<K, V, R>, Describable {
	protected final Map<K, V> _values;
	
	/**
	 * Instantiate a new empty record builder.
	 */
	public AbstractRecordBuilder() {
		// Initialization.
		_values = new HashMap<K, V>();
	}
	
	/**
	 * Instantiate a new record builder containing the contents of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 */
	public AbstractRecordBuilder(final Record<? extends K, ? extends V> record) {
		Assert.notNull(record);
		
		// Initialization.
		_values = new HashMap<K, V>(record.asMap());
	}
	
	/**
	 * Instantiate a new record builder containing the contents of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 */
	public AbstractRecordBuilder(final RecordBuilder<? extends K, ? extends V, ?> builder) {
		Assert.notNull(builder);
		
		// Initialization.
		_values = new HashMap<K, V>(builder.asMap());
	}
	
	public void add(final K key, final V value)
	throws RecordException {
		Assert.notNull(key);
		
		// Add.
		if (!_values.containsKey(key)) {
			_values.put(key, value);
		} else {
			throw new DuplicateValueRecordException("Value already defined for key " + key);
		}
	}
	
	public void addAll(final Record<? extends K, ? extends V> record)
	throws RecordException {
		Assert.notNull(record);
		
		// Add.
		addAll(record.asMap());
	}
	
	public void addAll(final RecordBuilder<? extends K, ? extends V, ?> builder)
	throws RecordException {
		Assert.notNull(builder);
		
		// Add.
		addAll(builder.asMap());
	}
	
	protected void addAll(final Map<? extends K, ? extends V> values)
	throws RecordException {
		// Iterate over the values.
		for (final Map.Entry<? extends K, ? extends V> entry : values.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			
			// Add.
			if (!_values.containsKey(key)) {
				_values.put(key, value);
			} else {
				throw new DuplicateValueRecordException("Value already defined for key " + key);
			}
		}
	}
	
	public boolean isEmpty() {
		return _values.isEmpty();
	}
	
	public boolean contains(final K key) {
		Assert.notNull(key);
		
		// Test.
		return _values.containsKey(key);
	}
	
	public V get(final K key)
	throws RecordException {
		Assert.notNull(key);
		
		// Get.
		if (_values.containsKey(key)) {
			return _values.get(key);
		} else {
			throw new MissingValueRecordException("Missing value for key " + key);
		}
	}
	
	public V get(final K key, final V defaultValue) {
		Assert.notNull(key);
		Assert.notNull(defaultValue);
		
		// Get.
		return _values.containsKey(key) ? _values.get(key) : defaultValue;
	}
	
	public Map<K, V> asMap() {
		return Collections.unmodifiableMap(_values);
	}
	
	public void set(final K key, final V value) {
		Assert.notNull(value);
		
		// Set.
		_values.put(key, value);
	}
	
	public void setAll(final Record<? extends K, ? extends V> record) {
		Assert.notNull(record);
		
		// Set.
		_values.putAll(record.asMap());
	}
	
	public void setAll(final RecordBuilder<? extends K, ? extends V, ?> builder) {
		Assert.notNull(builder);
		
		// Set.
		_values.putAll(builder.asMap());
	}
	
	public void remove(final K key)
	throws RecordException {
		Assert.notNull(key);
		
		// Remove.
		if (_values.containsKey(key)) {
			_values.remove(key);
		} else {
			throw new MissingValueRecordException("Missing value for key " + key);
		}
	}
	
	public void clear(final K key) {
		Assert.notNull(key);
		
		// Clear.
		_values.remove(key);
	}
	
	public void clear() {
		// Clear.
		_values.clear();
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Values = ").append(_values);
	}
}
