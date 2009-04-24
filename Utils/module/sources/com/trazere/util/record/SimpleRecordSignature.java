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
 * The {@link SimpleRecordSignature} class implements simple record signatures.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleRecordSignature<K, V>
implements RecordSignature<K, V>, Describable {
	private static final SimpleRecordSignature<?, ?> EMPTY = new SimpleRecordSignature<Object, Object>(Collections.<Object, FieldSignature<Object, Object>>emptyMap());
	
	/**
	 * Build an empty record signature.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The record signature.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> SimpleRecordSignature<K, V> build() {
		return (SimpleRecordSignature<K, V>) EMPTY;
	}
	
	/**
	 * Build a record signature with the given field signatures.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param fields Signatures of the fields.
	 * @return The record signature.
	 */
	public static <K, V> SimpleRecordSignature<K, V> build(final Collection<? extends FieldSignature<K, ? extends V>> fields) {
		assert null != fields;
		
		// Build.
		final Map<K, FieldSignature<K, ? extends V>> fieldsByKeys = new HashMap<K, FieldSignature<K, ? extends V>>();
		for (final FieldSignature<K, ? extends V> field : fields) {
			fieldsByKeys.put(field.getKey(), field);
		}
		return new SimpleRecordSignature<K, V>(fieldsByKeys);
	}
	
	/**
	 * Build a record signature with the given field signatures.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param fields Signatures of the fields.
	 * @return The record signature.
	 */
	public static <K, V> SimpleRecordSignature<K, V> build(final FieldSignature<K, ? extends V>... fields) {
		assert null != fields;
		
		// Build.
		final Map<K, FieldSignature<K, ? extends V>> fieldsByKeys = new HashMap<K, FieldSignature<K, ? extends V>>();
		for (final FieldSignature<K, ? extends V> field : fields) {
			fieldsByKeys.put(field.getKey(), field);
		}
		return new SimpleRecordSignature<K, V>(fieldsByKeys);
	}
	
	/** Signatures of the fields identified by their keys. */
	protected final Map<K, FieldSignature<K, ? extends V>> _fields;
	
	/**
	 * Instantiate a new record signature with the given field signatures.
	 * 
	 * @param signatures Signatures of the fields identified by their keys.
	 */
	protected SimpleRecordSignature(final Map<K, ? extends FieldSignature<K, ? extends V>> signatures) {
		assert null != signatures;
		
		// Initialization.
		_fields = Collections.unmodifiableMap(signatures);
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
		return _fields.keySet();
	}
	
	public FieldSignature<K, ? extends V> get(final K key)
	throws MissingFieldException {
		assert null != key;
		
		// Get.
		if (_fields.containsKey(key)) {
			return _fields.get(key);
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record signature " + this);
		}
	}
	
	public Map<K, FieldSignature<K, ? extends V>> asMap() {
		return _fields;
	}
	
	public boolean accepts(final Record<? super K, ? extends V> record)
	throws RecordException {
		assert null != record;
		
		for (final Map.Entry<K, FieldSignature<K, ? extends V>> requirement : _fields.entrySet()) {
			final K key = requirement.getKey();
			if (!record.contains(key) || !requirement.getValue().getType().isInstance(record.get(key))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean accepts(final RecordSignature<? super K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		for (final Map.Entry<K, FieldSignature<K, ? extends V>> requirement : _fields.entrySet()) {
			final K key = requirement.getKey();
			if (!signature.contains(key) || !requirement.getValue().getType().isAssignableFrom(signature.get(key).getType())) {
				return false;
			}
		}
		return true;
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
			final SimpleRecordSignature<?, ?> signatures = (SimpleRecordSignature<?, ?>) object;
			return _fields.equals(signatures._fields);
		} else {
			return false;
		}
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		for (final FieldSignature<K, ? extends V> field : _fields.values()) {
			description.append(field.getKey().toString(), field);
		}
	}
}
