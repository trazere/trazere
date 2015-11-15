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
package com.trazere.core.record;

/**
 * The {@link Fields} class provides various factories of {@link Field fields}.
 * 
 * @see Field
 * @since 2.0
 */
public class Fields {
	// TODO
	//	/**
	//	 * Builds a field with the given value according to the given signature.
	//	 * <p>
	//	 * The given value must be compatible with the constraints of the signature.
	//	 *
	//	 * @param <K> Type of the key.
	//	 * @param <V> Type of the value.
	//	 * @param signature Signature of the field.
	//	 * @param value Value of the field.
	//	 * @return The built field.
	//	 * @throws NullFieldException When the value is <code>null</code> and the field signature is not nullable.
	//	 * @throws IncompatibleFieldException When the value is not compatible with the value type of the field signature.
	//	 */
	//	public static <K, V> Field<K, V> cast(final FieldSignature<K, V> signature, final Object value)
	//	throws NullFieldException, IncompatibleFieldException {
	//		return fromValue(signature.getKey(), signature.cast(value));
	//	}
	
	/**
	 * Builds a field with the given value according to the given signature.
	 * <p>
	 * The given value must be compatible with the constraints of the signature.
	 * 
	 * @param <K> Type of the key.
	 * @param <V> Type of the value.
	 * @param key Signature of the field.
	 * @param value Value of the field. May be <code>null</code>.
	 * @return The built field.
	 * @throws NullFieldException When the value is <code>null</code> and the field is not nullable.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V> Field<K, V> fromKeyAndValue(final FieldKey<? extends K, V> key, final V value)
	throws NullFieldException {
		assert null != key;
		assert null != value;
		
		// Check the value.
		if (null == value && !key.isNullable()) {
			throw new NullFieldException("Field \"" + key + "\" is not nullable");
		}
		
		// Build the field.
		return new BaseField<K, V>() {
			// Key.
			
			@Override
			public FieldKey<? extends K, V> getKey() {
				return key;
			}
			
			// Value.
			
			@Override
			public V getValue() {
				return value;
			}
		};
	}
	
	private Fields() {
		// Prevent instantiation.
	}
}
