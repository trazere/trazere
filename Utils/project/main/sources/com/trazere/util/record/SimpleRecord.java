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
package com.trazere.util.record;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.collection.Maps;
import com.trazere.util.lang.HashCode;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// TODO: rename to MapRecord ?

/**
 * The {@link SimpleRecord} class implements simple records.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecordBuilder
 * @deprecated Use {@link com.trazere.core.record.SimpleRecord}.
 */
@Deprecated
public class SimpleRecord<K, V>
implements Record<K, V>, Describable {
	private static final SimpleRecord<?, ?> EMPTY = new SimpleRecord<>(Collections.emptyMap());
	
	// TODO: move to Records
	/**
	 * Builds an empty record.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The empty record.
	 * @deprecated Use {@link com.trazere.core.record.Records#empty()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <K, V> SimpleRecord<K, V> build() {
		return (SimpleRecord<K, V>) EMPTY;
	}
	
	// TODO: move to Records
	/**
	 * Builds a record populated with one field using the given key and value.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key Key identifying the field.
	 * @param value Value of the field.
	 * @return The built record.
	 * @deprecated Use {@link com.trazere.core.record.Records#fromKeyAndValue(com.trazere.core.record.FieldKey, Object)}.
	 */
	@Deprecated
	public static <K, V> SimpleRecord<K, V> build(final K key, final V value) {
		assert null != key;
		
		// Build.
		return new SimpleRecord<>(Maps.fromBinding(key, value));
	}
	
	// TODO: move to Records
	/**
	 * Builds a record populated with two fields using the given keys and values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param key1 Key identifying the first field.
	 * @param value1 Value of the first field. May be <code>null</code>.
	 * @param key2 Key identifying the second field.
	 * @param value2 Value of the second field. May be <code>null</code>.
	 * @return The built record.
	 * @deprecated Use
	 *             {@link com.trazere.core.record.Records#fromKeysAndValues(com.trazere.core.record.FieldKey, Object, com.trazere.core.record.FieldKey, Object)}
	 *             .
	 */
	@Deprecated
	public static <K, V> SimpleRecord<K, V> build(final K key1, final V value1, final K key2, final V value2) {
		assert null != key1;
		assert null != key2;
		
		// Build.
		final Map<K, V> fields = new HashMap<>();
		fields.put(key1, value1);
		fields.put(key2, value2);
		return new SimpleRecord<>(fields);
	}
	
	/**
	 * Builds a record populated with the given fields.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param fields Values of the fields identified by their keys.
	 * @return The built record.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> SimpleRecord<K, V> build(final Map<? extends K, ? extends V> fields) {
		assert null != fields;
		
		// Build.
		return new SimpleRecord<>(new HashMap<K, V>(fields));
	}
	
	/**
	 * Build a record populated with the fields of the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record Record to copy.
	 * @return The built record.
	 * @throws InvalidFieldException When some field cannot be computed.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> SimpleRecord<K, V> build(final Record<? extends K, ? extends V> record)
	throws InvalidFieldException {
		assert null != record;
		
		// Build.
		return new SimpleRecord<>(new HashMap<K, V>(record.asMap()));
	}
	
	/** Values of the fields identified by their keys. */
	protected Map<K, V> _fields;
	
	/**
	 * Instantiate a new record with the given fields.
	 * 
	 * @param fields Values of the fields identified by their keys.
	 * @deprecated Use {@link com.trazere.core.record.SimpleRecord#SimpleRecord(Map)}.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected SimpleRecord(final Map<? extends K, ? extends V> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = Collections.unmodifiableMap(fields);
	}
	
	@Override
	public boolean isEmpty() {
		return _fields.isEmpty();
	}
	
	@Override
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _fields.containsKey(key);
	}
	
	@Override
	public Set<K> getKeys() {
		return _fields.keySet();
	}
	
	@Override
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
	
	@Override
	public V get(final K key, final V defaultValue) {
		assert null != key;
		
		// Get.
		return _fields.containsKey(key) ? _fields.get(key) : defaultValue;
	}
	
	@Override
	public Maybe<V> getMaybe(final K key) {
		assert null != key;
		
		// Get.
		return CollectionUtils.get(_fields, key);
	}
	
	@Override
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
				throw new IncompatibleFieldException("Field \"" + key + "\" is not compatible with type \"" + type + "\" in record " + this);
			}
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
	}
	
	@Override
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
				throw new IncompatibleFieldException("Field \"" + key + "\" is not compatible with type \"" + type + "\" in record " + this);
			}
		} else {
			return defaultValue;
		}
	}
	
	@Override
	public <T extends V> Maybe<T> getTypedMaybe(final K key, final Class<T> type)
	throws IncompatibleFieldException {
		// Get.
		if (_fields.containsKey(key)) {
			final V value = _fields.get(key);
			if (null == value) {
				return null;
			} else if (type.isInstance(value)) {
				return Maybe.some(type.cast(value));
			} else {
				throw new IncompatibleFieldException("Field \"" + key + "\" is not compatible with type \"" + type + "\" in record " + this);
			}
		} else {
			return Maybe.none();
		}
	}
	
	@Override
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature)
	throws MissingFieldException, IncompatibleFieldException, NullFieldException {
		assert null != signature;
		
		// Get.
		final T value = getTyped(signature.getKey(), signature.getType());
		if (null != value || signature.isNullable()) {
			return value;
		} else {
			throw new NullFieldException("Field \"" + signature.getKey() + "\" has null value in record " + this);
		}
	}
	
	@Override
	public <T extends V> T getTyped(final FieldSignature<? extends K, T> signature, final T defaultValue)
	throws IncompatibleFieldException, NullFieldException {
		assert null != signature;
		
		// Get.
		final T value = getTyped(signature.getKey(), signature.getType(), defaultValue);
		if (null != value || signature.isNullable()) {
			return value;
		} else {
			throw new NullFieldException("Field \"" + signature.getKey() + "\" has null value in record " + this);
		}
	}
	
	@Override
	public <T extends V> Maybe<T> getTypedMaybe(final FieldSignature<? extends K, T> signature)
	throws IncompatibleFieldException, NullFieldException {
		assert null != signature;
		
		// Get.
		final Maybe<T> value = getTypedMaybe(signature.getKey(), signature.getType());
		if (value.isNone() || null != value.asSome().getValue() || signature.isNullable()) {
			return value;
		} else {
			throw new NullFieldException("Field \"" + signature.getKey() + "\" has null value in record " + this);
		}
	}
	
	@Override
	public Collection<V> getValues() {
		return _fields.values();
	}
	
	@Override
	public Map<K, V> asMap() {
		return _fields;
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_fields);
		return result.get();
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
	
	@Override
	public void fillDescription(final Description description) {
		for (final Map.Entry<K, V> entry : _fields.entrySet()) {
			description.append(entry.getKey().toString(), entry.getValue());
		}
	}
}
