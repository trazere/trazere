/*
 *  Copyright 2006-2013 Julien Dufour
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

// TODO: rename to BaseRecordSignatureBuilder ?

/**
 * The {@link BaseSimpleRecordSignatureBuilder} abstract class provides a skeleton implementation of {@link RecordSignatureBuilder builders of record
 * signatures}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 */
public abstract class BaseSimpleRecordSignatureBuilder<K, V, R extends SimpleRecordSignature<K, V>>
implements RecordSignatureBuilder<K, V, R>, Describable {
	/** Signatures of the fields identified by their keys. */
	final Map<K, FieldSignature<K, ? extends V>> _fields;
	
	/**
	 * Instantiate a new empty builder.
	 */
	public BaseSimpleRecordSignatureBuilder() {
		// Initialization.
		_fields = new HashMap<K, FieldSignature<K, ? extends V>>();
	}
	
	/**
	 * Instantiate a new builder populated with the given field signatures.
	 * 
	 * @param fields Initial field signatures identified by their keys.
	 */
	public BaseSimpleRecordSignatureBuilder(final Map<K, ? extends FieldSignature<K, ? extends V>> fields) {
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
	public BaseSimpleRecordSignatureBuilder(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Initialization.
		_fields = new HashMap<K, FieldSignature<K, ? extends V>>(signature.asMap());
	}
	
	@Override
	public void add(final K key, final Class<? extends V> type, final boolean nullable)
	throws RecordException {
		assert null != key;
		assert null != type;
		
		// Add the field signature.
		if (!_fields.containsKey(key)) {
			_fields.put(key, FieldSignature.build(key, type, nullable));
		} else {
			throw new DuplicateFieldException("Field \"" + key + "\" already signed in builder " + this);
		}
	}
	
	@Override
	public void add(final FieldSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Add the field signature.
		add(signature.getKey(), signature.getType(), signature.isNullable());
	}
	
	@Override
	public void addAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws RecordException {
		assert null != fields;
		
		// Add the field signatures.
		for (final FieldSignature<K, ? extends V> field : fields) {
			add(field);
		}
	}
	
	@Override
	public void addAll(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Add the field signatures.
		for (final K key : signature.getKeys()) {
			add(signature.get(key));
		}
	}
	
	@Override
	public void unify(final K key, final Class<? extends V> type, final boolean nullable)
	throws RecordException {
		assert null != key;
		assert null != type;
		
		// Unify the field signature.
		unify(FieldSignature.build(key, type, nullable));
	}
	
	@Override
	public void unify(final FieldSignature<K, ? extends V> field)
	throws RecordException {
		assert null != field;
		
		// Unify the field signature.
		final K key = field.getKey();
		final FieldSignature<K, ? extends V> unifiedField;
		if (_fields.containsKey(key)) {
			final FieldSignature<K, ? extends V> currentField = _fields.get(key);
			
			// Unify the type.
			final Class<? extends V> type = field.getType();
			final Class<? extends V> currentType = currentField.getType();
			final Class<? extends V> unifiedType;
			if (type.isAssignableFrom(currentType)) {
				unifiedType = currentType;
			} else if (currentType.isAssignableFrom(type)) {
				unifiedType = type;
			} else {
				throw new IncompatibleFieldException("Cannot unify field + \"" + key + "\" of type \"" + type + "\" with type \"" + currentType + "\" in builder " + this);
			}
			
			// Unify the nullability.
			final boolean unifiedNullable = field.isNullable() && currentField.isNullable();
			
			// Build the unified field.
			unifiedField = FieldSignature.build(key, unifiedType, unifiedNullable);
		} else {
			unifiedField = field;
		}
		_fields.put(key, unifiedField);
	}
	
	@Override
	public void unifyAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws RecordException {
		assert null != fields;
		
		// Unify the field signatures.
		for (final FieldSignature<K, ? extends V> field : fields) {
			unify(field);
		}
	}
	
	@Override
	public void unifyAll(final RecordSignature<K, ? extends V> signature)
	throws RecordException {
		assert null != signature;
		
		// Unify the field signatures.
		for (final K key : signature.getKeys()) {
			unify(signature.get(key));
		}
	}
	
	@Override
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
		return Collections.unmodifiableSet(_fields.keySet());
	}
	
	@Override
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
	
	@Override
	public void clear() {
		_fields.clear();
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		for (final FieldSignature<K, ? extends V> field : _fields.values()) {
			description.append(field.getKey().toString(), field);
		}
	}
}
