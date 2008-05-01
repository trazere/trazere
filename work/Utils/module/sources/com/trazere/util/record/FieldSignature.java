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

import com.trazere.util.lang.HashCode;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link FieldSignature} class describes signatures of record fields.
 * <p>
 * The signature is caracterized by the key and the type of the value of the field.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public final class FieldSignature<K, V>
implements Describable {
	/** Key of the field. */
	private final K _key;
	
	/** Type of the value of the field. */
	private final Class<V> _type;
	
	/**
	 * Instantiate a new record field signature with the given key and type.
	 * 
	 * @param key Key of the field.
	 * @param type Type of the value of the field.
	 */
	public FieldSignature(final K key, final Class<V> type) {
		assert null != key;
		assert null != type;
		
		// Initialization.
		_key = key;
		_type = type;
	}
	
	/**
	 * Get the key of the record field described by the receiver signature.
	 * 
	 * @return The key.
	 */
	public K getKey() {
		return _key;
	}
	
	/**
	 * Get the type of the values of the record field described by the receiver signature.
	 * 
	 * @return The Java class of the type.
	 */
	public Class<V> getType() {
		return _type;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_key);
		hashCode.append(_type);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FieldSignature<?, ?> signature = (FieldSignature<?, ?>) object;
			return _key.equals(signature._key) && _type.equals(signature._type);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Key = ").append(_key);
		builder.append(" - Type = ").append(_type);
	}
}
