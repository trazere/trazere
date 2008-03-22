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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.trazere.util.Assert;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link SimpleRecord} class implements simple records.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecordBuilder
 */
public class SimpleRecord<K, V>
implements Record<K, V>, Describable {
	private static final SimpleRecord<?, ?> EMPTY = new SimpleRecord<Object, Object>(Collections.emptyMap());
	
	/**
	 * Build an empty record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The empty record.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> SimpleRecord<K, V> build() {
		return (SimpleRecord<K, V>) EMPTY;
	}
	
	/**
	 * Build a one field record with the given key and value.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key Key of the field.
	 * @param value Value of the field.
	 * @return The record.
	 */
	public static <K, V> SimpleRecord<K, V> build(final K key, final V value) {
		return new SimpleRecord<K, V>(CollectionUtils.map(key, value));
	}
	
	/** Values of the fields identified by their keys. */
	protected Map<K, V> _values;
	
	/**
	 * Instantiate a new record with the given values.
	 * 
	 * @param values Values of the fields identified by their keys.
	 */
	protected SimpleRecord(final Map<K, V> values) {
		Assert.notNull(values);
		
		// Initialization.
		_values = Collections.unmodifiableMap(values);
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
		
		// Get.
		return _values.containsKey(key) ? _values.get(key) : defaultValue;
	}
	
	public Set<K> keys() {
		return _values.keySet();
	}
	
	public Collection<V> values() {
		return _values.values();
	}
	
	public Map<K, V> asMap() {
		return _values;
	}
	
	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		result = result * 31 + _values.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SimpleRecord<?, ?> record = (SimpleRecord<?, ?>) object;
			return _values.equals(record._values);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Values = ").append(_values);
	}
}
