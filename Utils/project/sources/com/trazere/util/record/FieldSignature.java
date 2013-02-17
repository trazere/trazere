/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.function.Function1;
import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.HashCode;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link FieldSignature} class describes signatures of record fields.
 * <p>
 * The signature is caracterized by the key and the type of the value of the field.
 * 
 * @param <K> Type of the key.
 * @param <V> Type of the values.
 */
public final class FieldSignature<K, V>
implements Describable {
	/**
	 * Build a new field signature with the given key and type.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the values.
	 * @param key The key of the field.
	 * @param type The type of the value of the field.
	 * @return The built signature.
	 */
	public static <K, V> FieldSignature<K, V> build(final K key, final Class<V> type) {
		return new FieldSignature<K, V>(key, type);
	}
	
	/**
	 * Build a new field signature with the given key and type.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the values.
	 * @param key The key of the field.
	 * @param type The type of the value of the field.
	 * @param nullable The flag indicating whether the value of the field can be <code>null</code> or not.
	 * @return The built signature.
	 */
	public static <K, V> FieldSignature<K, V> build(final K key, final Class<V> type, final boolean nullable) {
		return new FieldSignature<K, V>(key, type, nullable);
	}
	
	/** The key of the field. */
	private final K _key;
	
	/** The type of the value of the field. */
	private final Class<V> _type;
	
	/** The flag indicating whether the value of the field can be <code>null</code> or not. */
	private final boolean _nullable;
	
	/**
	 * Instantiate a new record field signature with the given key and type.
	 * 
	 * @param key The key of the field.
	 * @param type The type of the value of the field.
	 */
	public FieldSignature(final K key, final Class<V> type) {
		this(key, type, true);
	}
	
	/**
	 * Instantiate a new record field signature with the given key, type and nullability.
	 * 
	 * @param key The key of the field.
	 * @param type The type of the value of the field.
	 * @param nullable The flag indicating whether the value of the field can be <code>null</code> or not.
	 */
	public FieldSignature(final K key, final Class<V> type, final boolean nullable) {
		assert null != key;
		assert null != type;
		
		// Initialization.
		_key = key;
		_type = type;
		_nullable = nullable;
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
	 * Build a function which gets the key of field signatures.
	 * 
	 * @param <K> Type of the keys.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <K, X extends Exception> Function1<FieldSignature<K, ?>, K, X> getKeyFunction() {
		return (Function1<FieldSignature<K, ?>, K, X>) _GET_KEY_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_KEY_FUNCTION = new Function1<FieldSignature<Object, ?>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final FieldSignature<Object, ?> field) {
			assert null != field;
			
			return field.getKey();
		}
	};
	
	/**
	 * Get the type of the values of the record field described by the receiver signature.
	 * 
	 * @return The Java class of the type.
	 */
	public Class<V> getType() {
		return _type;
	}
	
	/**
	 * Build a function which gets the type of field signatures.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <V, X extends Exception> Function1<FieldSignature<?, V>, Class<V>, X> getTypeFunction() {
		return (Function1<FieldSignature<?, V>, Class<V>, X>) _GET_TYPE_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_TYPE_FUNCTION = new Function1<FieldSignature<?, Object>, Class<Object>, RuntimeException>() {
		@Override
		public Class<Object> evaluate(final FieldSignature<?, Object> field) {
			assert null != field;
			
			return field.getType();
		}
	};
	
	/**
	 * Indicates whether the value of the field can be <code>null</code> or not.
	 * 
	 * @return <code>true</code> when the value of the field can be <code>null</code>, <code>false</code> otherwise.
	 */
	public boolean isNullable() {
		return _nullable;
	}
	
	/**
	 * Build a predicate function which indicates the nullable of field signatures.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Predicate1<FieldSignature<?, ?>, X> getNullableFunction() {
		return (Predicate1<FieldSignature<?, ?>, X>) _GET_NULLABLE_FUNCTION;
	}
	
	private static final Predicate1<FieldSignature<?, ?>, RuntimeException> _GET_NULLABLE_FUNCTION = new Predicate1<FieldSignature<?, ?>, RuntimeException>() {
		@Override
		public boolean evaluate(final FieldSignature<?, ?> field) {
			assert null != field;
			
			return field.isNullable();
		}
	};
	
	// TODO: should return a reason in case of failure
	/**
	 * Check whether the receiver field signature accepts the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 * @return <code>true</code> when the value is accepted, <code>false</code> otherwise.
	 */
	public boolean acceptsValue(final Object value) {
		if (null == value) {
			return _nullable;
		} else {
			return _type.isAssignableFrom(value.getClass());
		}
	}
	
	// TODO: should return a reason in case of failure
	/**
	 * Check whether the receiver field signature accepts a value with the given type and nullability.
	 * 
	 * @param type The type.
	 * @param nullable The flag indicating whether the value of the field can be <code>null</code> or not.
	 * @return <code>true</code> when the type is accepted, <code>false</code> otherwise.
	 */
	public boolean accepts(final Class<?> type, final boolean nullable) {
		assert null != type;
		
		return _type.isAssignableFrom(type) && (_nullable || !nullable);
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_key);
		result.append(_type);
		result.append(_nullable);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FieldSignature<?, ?> signature = (FieldSignature<?, ?>) object;
			return _key.equals(signature._key) && _type.equals(signature._type) && _nullable == signature._nullable;
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
		description.append("Key", _key);
		description.append("Type", _type);
		description.append("Nullable", _nullable);
	}
}
