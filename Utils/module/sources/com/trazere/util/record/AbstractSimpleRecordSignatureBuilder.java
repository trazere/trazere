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

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link AbstractSimpleRecordSignatureBuilder} abstract class implements skeletons of builders of {@link RecordSignature record signatures}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public abstract class AbstractSimpleRecordSignatureBuilder<K, V, R extends SimpleRecordSignature<K, V>>
implements RecordSignatureBuilder<K, V, R>, Describable {
	/** Signatures of the fields identified by their keys. */
	final Map<K, FieldSignature<K, ? extends V>> _fields;
	
	/**
	 * Instantiate a new empty builder.
	 */
	public AbstractSimpleRecordSignatureBuilder() {
		// Initialization.
		_fields = new HashMap<K, FieldSignature<K, ? extends V>>();
	}
	
	/**
	 * Instantiate a new builder populated with the given field signatures.
	 * 
	 * @param fields Initial field signatures identified by their keys.
	 */
	public AbstractSimpleRecordSignatureBuilder(final Map<K, ? extends FieldSignature<K, ? extends V>> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = new HashMap<K, FieldSignature<K, ? extends V>>(fields);
	}
	
	/**
	 * Instantiate a new builder populated with the field signatures of the given record signature.
	 * 
	 * @param signature Record signature containing the initial field signatures.
	 * @throws RecordException When the record signature cannot be read.
	 */
	public AbstractSimpleRecordSignatureBuilder(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Initialization.
		_fields = new HashMap<K, FieldSignature<K, ? extends V>>(signature.asMap());
	}
	
	public void add(final FieldSignature<K, ? extends V> signature)
	throws DuplicateFieldException {
		assert null != signature;
		
		// Add the field signature.
		final K key = signature.getKey();
		if (!_fields.containsKey(key)) {
			_fields.put(key, signature);
		} else {
			throw new DuplicateFieldException("Field signature \"" + key + "\" already exists in builder " + this);
		}
	}
	
	public void addAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws DuplicateFieldException {
		assert null != fields;
		
		// Add the parameter signatures.
		for (final FieldSignature<K, ? extends V> field : fields) {
			final K key = field.getKey();
			if (!_fields.containsKey(key)) {
				_fields.put(key, field);
			} else {
				throw new DuplicateFieldException("Field signature \"" + key + "\" already exists in builder " + this);
			}
		}
	}
	
	public void addAll(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Add the parameter signatures.
		addAll(signature.asMap().values());
	}
	
	public void unify(final FieldSignature<K, ? extends V> field)
	throws IncompatibleFieldException {
		assert null != field;
		
		// Unify the parameter signature.
		final K key = field.getKey();
		if (_fields.containsKey(key)) {
			final Class<? extends V> type = field.getType();
			final Class<? extends V> currentType = _fields.get(key).getType();
			if (!type.isAssignableFrom(currentType)) {
				if (currentType.isAssignableFrom(type)) {
					_fields.put(key, field);
				} else {
					throw new IncompatibleFieldException("Cannot unify field signature " + field + " with type " + currentType + " in builder " + this);
				}
			}
		} else {
			_fields.put(key, field);
		}
	}
	
	public void unifyAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws IncompatibleFieldException {
		assert null != fields;
		
		// Unify the parameter signatures.
		for (final FieldSignature<K, ? extends V> field : fields) {
			final K key = field.getKey();
			if (_fields.containsKey(key)) {
				final Class<? extends V> type = field.getType();
				final Class<? extends V> currentType = _fields.get(key).getType();
				if (!type.isAssignableFrom(currentType)) {
					if (currentType.isAssignableFrom(type)) {
						_fields.put(key, field);
					} else {
						throw new IncompatibleFieldException("Cannot unify field signature " + field + " with type " + currentType + " in builder " + this);
					}
				}
			} else {
				_fields.put(key, field);
			}
		}
	}
	
	public void unifyAll(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Unify the parameter signatures.
		unifyAll(signature.asMap().values());
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
	
	public void remove(final K key)
	throws MissingFieldException {
		assert null != key;
		
		// Remove the parameter signature.
		if (_fields.containsKey(key)) {
			_fields.remove(key);
		} else {
			throw new MissingFieldException("Field signature \"" + key + "\" does not exist in builder " + this);
		}
	}
	
	public void clear() {
		_fields.clear();
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		for (final FieldSignature<K, ? extends V> field : _fields.values()) {
			builder.append(" - ").append(field.getKey()).append(" = ").append(field);
		}
	}
}
