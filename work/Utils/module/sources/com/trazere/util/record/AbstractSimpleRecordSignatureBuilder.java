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

import com.trazere.util.function.Predicate1;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
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
	
	public void add(final K key, final Class<? extends V> type)
	throws RecordException {
		assert null != key;
		assert null != type;
		
		// Add the field signature.
		if (!_fields.containsKey(key)) {
			_fields.put(key, FieldSignature.build(key, type));
		} else {
			throw new DuplicateFieldException("Field \"" + key + "\" already signed in builder " + this);
		}
	}
	
	public void add(final FieldSignature<K, ? extends V> signature)
	throws DuplicateFieldException {
		assert null != signature;
		
		// Add the field signature.
		final K key = signature.getKey();
		if (!_fields.containsKey(key)) {
			_fields.put(key, signature);
		} else {
			throw new DuplicateFieldException("Field \"" + key + "\" already signed in builder " + this);
		}
	}
	
	public void addAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws DuplicateFieldException {
		assert null != fields;
		
		// Add the field signatures.
		for (final FieldSignature<K, ? extends V> field : fields) {
			final K key = field.getKey();
			if (!_fields.containsKey(key)) {
				_fields.put(key, field);
			} else {
				throw new DuplicateFieldException("Field \"" + key + "\" already signed in builder " + this);
			}
		}
	}
	
	public void addAll(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Add the field signatures.
		addAll(signature.asMap().values());
	}
	
	public void unify(final K key, final Class<? extends V> type)
	throws RecordException {
		assert null != key;
		assert null != type;
		
		// Unify the field signature.
		if (_fields.containsKey(key)) {
			final Class<? extends V> currentType = _fields.get(key).getType();
			if (!type.isAssignableFrom(currentType)) {
				if (currentType.isAssignableFrom(type)) {
					_fields.put(key, FieldSignature.build(key, type));
				} else {
					throw new IncompatibleFieldException("Cannot unify field + \"" + key + "\" of type " + type + " with type " + currentType + " in builder " + this);
				}
			}
		} else {
			_fields.put(key, FieldSignature.build(key, type));
		}
	}
	
	public void unify(final FieldSignature<K, ? extends V> field)
	throws IncompatibleFieldException {
		assert null != field;
		
		// Unify the field signature.
		final K key = field.getKey();
		if (_fields.containsKey(key)) {
			final Class<? extends V> type = field.getType();
			final Class<? extends V> currentType = _fields.get(key).getType();
			if (!type.isAssignableFrom(currentType)) {
				if (currentType.isAssignableFrom(type)) {
					_fields.put(key, field);
				} else {
					throw new IncompatibleFieldException("Cannot unify field + \"" + key + "\" of type " + type + " with type " + currentType + " in builder " + this);
				}
			}
		} else {
			_fields.put(key, field);
		}
	}
	
	public void unifyAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws IncompatibleFieldException {
		assert null != fields;
		
		// Unify the field signatures.
		for (final FieldSignature<K, ? extends V> field : fields) {
			unify(field);
		}
	}
	
	public void unifyAll(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Unify the field signatures.
		for (final K key : signature.getKeys()) {
			unify(signature.get(key));
		}
	}
	
	public void unifyAll(final Predicate1<? super FieldSignature<K, ? extends V>, ? extends RecordException> filter, final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != filter;
		assert null != signature;
		
		// Unify the field signatures.
		for (final K key : signature.getKeys()) {
			final FieldSignature<K, ? extends V> fieldSignature = signature.get(key);
			if (filter.evaluate(fieldSignature)) {
				unify(signature.get(key));
			}
		}
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
		
		// Remove the field signature.
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
	
	public void fillDescription(final Description description) {
		for (final FieldSignature<K, ? extends V> field : _fields.values()) {
			description.append(field.getKey().toString(), field);
		}
	}
}
