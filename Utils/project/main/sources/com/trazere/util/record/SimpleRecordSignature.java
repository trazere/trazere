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

// TODO: rename to MapRecordSignature ?

/**
 * The {@link SimpleRecordSignature} class implements simple record signatures.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @deprecated To be removed.
 */
@Deprecated
public class SimpleRecordSignature<K, V>
implements RecordSignature<K, V>, Describable {
	private static final SimpleRecordSignature<?, ?> EMPTY = new SimpleRecordSignature<Object, Object>(Collections.<Object, FieldSignature<Object, Object>>emptyMap());
	
	// TODO: move to RecordSignatures
	@Deprecated
	/**
	 * Builds an empty record signature.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The record signature.
	 * @deprecated To be removed.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> SimpleRecordSignature<K, V> build() {
		return (SimpleRecordSignature<K, V>) EMPTY;
	}
	
	// TODO: move to RecordSignatures
	/**
	 * Builds a record signature with the given field signatures.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param fields Signatures of the fields.
	 * @return The record signature.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public static <K, V> SimpleRecordSignature<K, V> build(final Collection<? extends FieldSignature<K, ? extends V>> fields) {
		assert null != fields;
		
		// Build.
		final Map<K, FieldSignature<K, ? extends V>> fieldsByKeys = new HashMap<K, FieldSignature<K, ? extends V>>();
		for (final FieldSignature<K, ? extends V> field : fields) {
			fieldsByKeys.put(field.getKey(), field);
		}
		return new SimpleRecordSignature<K, V>(fieldsByKeys);
	}
	
	// TODO: move to RecordSignatures
	/**
	 * Builds a record signature with the given field signatures.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param fields Signatures of the fields.
	 * @return The record signature.
	 * @deprecated To be removed.
	 */
	@Deprecated
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
	 * Instantiates a new record signature with the given field signatures.
	 * 
	 * @param signatures Signatures of the fields identified by their keys.
	 * @deprecated To be removed.
	 */
	@Deprecated
	protected SimpleRecordSignature(final Map<K, ? extends FieldSignature<K, ? extends V>> signatures) {
		assert null != signatures;
		
		// Initialization.
		_fields = Collections.unmodifiableMap(signatures);
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
	
	@Override
	public Maybe<FieldSignature<K, ? extends V>> getMaybe(final K key) {
		assert null != key;
		
		return CollectionUtils.get(_fields, key);
	}
	
	@Override
	public Map<K, FieldSignature<K, ? extends V>> asMap() {
		return _fields;
	}
	
	@Override
	public boolean accepts(final Record<? super K, ? extends V> record)
	throws InvalidFieldException {
		assert null != record;
		
		for (final Map.Entry<K, FieldSignature<K, ? extends V>> requirement : _fields.entrySet()) {
			final Maybe<? extends V> value = record.getMaybe(requirement.getKey());
			if (value.isNone() || !requirement.getValue().acceptsValue(value.asSome().getValue())) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean accepts(final RecordSignature<? super K, ? extends V> signature)
	throws InvalidFieldException {
		assert null != signature;
		
		for (final Map.Entry<K, FieldSignature<K, ? extends V>> requirement : _fields.entrySet()) {
			final K key = requirement.getKey();
			final Maybe<? extends FieldSignature<? super K, ?>> maybeFieldSignature = signature.getMaybe(key);
			if (maybeFieldSignature.isNone()) {
				return false;
			} else {
				final FieldSignature<? super K, ?> fieldSignature = maybeFieldSignature.asSome().getValue();
				// TODO: accepts method with fieldsignature as arg
				if (!requirement.getValue().accepts(fieldSignature.getType(), fieldSignature.isNullable())) {
					return false;
				}
			}
		}
		return true;
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
	
	@Override
	public void fillDescription(final Description description) {
		for (final FieldSignature<K, ? extends V> field : _fields.values()) {
			description.append(field.getKey().toString(), field);
		}
	}
}
