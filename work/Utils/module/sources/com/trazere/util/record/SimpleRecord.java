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
package com.trazere.util.record;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.lang.HashCode;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
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
	 * Build a record populated with one field using the given key and value.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key Key identifying the field.
	 * @param value Value of the field.
	 * @return The built record.
	 */
	public static <K, V> SimpleRecord<K, V> build(final K key, final V value) {
		assert null != key;
		
		// Build.
		return new SimpleRecord<K, V>(CollectionUtils.map(key, value));
	}
	
	/**
	 * Build a record populated with two fields using the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key identifying the first field.
	 * @param value1 Value of the first field. May be <code>null</code>.
	 * @param key2 Key identifying the second field.
	 * @param value2 Value of the second field. May be <code>null</code>.
	 * @return The built record.
	 */
	public static <K, V> SimpleRecord<K, V> build(final K key1, final V value1, final K key2, final V value2) {
		assert null != key1;
		assert null != key2;
		
		// Build.
		final Map<K, V> fields = new HashMap<K, V>();
		fields.put(key1, value1);
		fields.put(key2, value2);
		return new SimpleRecord<K, V>(fields);
	}
	
	/**
	 * Build a record populated with the given fields.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param fields Values of the fields identified by their keys.
	 * @return The built record.
	 */
	public static <K, V> SimpleRecord<K, V> build(final Map<? extends K, ? extends V> fields) {
		assert null != fields;
		
		// Build.
		return new SimpleRecord<K, V>(new HashMap<K, V>(fields));
	}
	
	/**
	 * Build a record populated with the fields of the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record Record to copy.
	 * @return The built record.
	 * @throws RecordException When the given record cannot be read.
	 */
	public static <K, V> SimpleRecord<K, V> build(final Record<? extends K, ? extends V> record)
	throws RecordException {
		assert null != record;
		
		// Build.
		return new SimpleRecord<K, V>(new HashMap<K, V>(record.asMap()));
	}
	
	/** Values of the fields identified by their keys. */
	protected Map<K, V> _fields;
	
	/**
	 * Instantiate a new record with the given fields.
	 * 
	 * @param fields Values of the fields identified by their keys.
	 */
	protected SimpleRecord(final Map<? extends K, ? extends V> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = Collections.unmodifiableMap(fields);
	}
	
	public boolean isEmpty() {
		return _fields.isEmpty();
	}
	
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _fields.containsKey(key);
	}
	
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_fields.keySet());
	}
	
	public V get(final K key)
	throws MissingFieldException {
		assert null != key;
		
		// Get.
		if (_fields.containsKey(key)) {
			return _fields.get(key);
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
	}
	
	public V get(final K key, final V defaultValue) {
		assert null != key;
		
		// Get.
		return _fields.containsKey(key) ? _fields.get(key) : defaultValue;
	}
	
	public <T extends V> T getTyped(final K key, final Class<T> type)
	throws MissingFieldException, IncompatibleFieldException {
		assert null != key;
		assert null != type;
		
		// Get.
		if (_fields.containsKey(key)) {
			final V value = _fields.get(key);
			if (null == value) {
				return null;
			} else if (type.isInstance(value)) {
				return type.cast(value);
			} else {
				throw new IncompatibleFieldException("Field " + key + " is not compatible with type " + type + " in record " + this);
			}
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
	}
	
	public <T extends V> T getTyped(final K key, final Class<T> type, final T defaultValue)
	throws IncompatibleFieldException {
		assert null != key;
		assert null != type;
		
		// Get.
		if (_fields.containsKey(key)) {
			final V value = _fields.get(key);
			if (null == value) {
				return null;
			} else if (type.isInstance(value)) {
				return type.cast(value);
			} else {
				throw new IncompatibleFieldException("Field " + key + " is not compatible with type " + type + " in record " + this);
			}
		} else {
			return defaultValue;
		}
	}
	
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature)
	throws MissingFieldException, IncompatibleFieldException {
		assert null != signature;
		
		// Get.
		return getTyped(signature.getKey(), signature.getType());
	}
	
	public <T extends V> T getTyped(final com.trazere.util.record.FieldSignature<? extends K, T> signature, final T defaultValue)
	throws IncompatibleFieldException {
		assert null != signature;
		
		// Get.
		return getTyped(signature.getKey(), signature.getType(), defaultValue);
	}
	
	public Collection<V> getValues() {
		return Collections.unmodifiableCollection(_fields.values());
	}
	
	public Map<K, V> asMap() {
		return _fields;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_fields);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SimpleRecord<?, ?> record = (SimpleRecord<?, ?>) object;
			return _fields.equals(record._fields);
		} else {
			return false;
		}
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
