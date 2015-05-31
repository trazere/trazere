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
 * The {@link FieldKey} class represents keys of {@link Field fields}.
 * <p>
 * Field keys identify fields. They are caracterized by :
 * <ul>
 * <li>the label of the field (for display purpose),
 * <li>the type of the value of the field,
 * <li>the nullability of the value of the field.
 * </ul>
 * <p>
 * Field key equality relies on their physical identity.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the value of the field.
 * @since 1.0
 */
public abstract class FieldKey<K extends FieldKey<K, V>, V> {
	/**
	 * Instantiates a new non-nullable field key.
	 * 
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @since 1.0
	 */
	public FieldKey(final String label, final Class<V> type) {
		this(label, type, false);
	}
	
	/**
	 * Instantiates a new field key.
	 * 
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @param nullable Flag indicating whether the value of the field is nullable of not.
	 * @since 1.0
	 */
	public FieldKey(final String label, final Class<V> type, final boolean nullable) {
		assert null != label;
		assert null != type;
		
		// Initialization.
		_label = label;
		_type = type;
		_nullable = nullable;
	}
	
	// Label.
	
	/** Label of the field. */
	private final String _label;
	
	/**
	 * Gets the label of the field identified by this key.
	 * 
	 * @return The key.
	 * @since 1.0
	 */
	public String getLabel() {
		return _label;
	}
	
	// Type.
	
	/** Type of the value of the field. */
	private final Class<V> _type;
	
	/**
	 * Gets the type of the value of the fields identified by this key.
	 * 
	 * @return The type.
	 * @since 1.0
	 */
	public Class<V> getType() {
		return _type;
	}
	
	// Nullable.
	
	/** Flag indicating whether the value of the field is nullable or not. */
	private final boolean _nullable;
	
	/**
	 * Indicates whether the value of the fields identified by this key is nullable or not.
	 * 
	 * @return <code>true</code> when the value is nullable, <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean isNullable() {
		return _nullable;
	}
	
	// Object.
	
	// Note: prevents overriding
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
	
	// Note: prevents overriding
	@Override
	public final boolean equals(final Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return _label + " :: " + _type + (_nullable ? "?" : "");
	}
	
	// TODO
	//	// Signature.
	//
	//	/**
	//	 * Casts the given value to a value compatible with this field signature.
	//	 * <p>
	//	 * A value is accepted when its type and nullability are compatible with the constraints of the signature.
	//	 *
	//	 * @param value Value to cast.
	//	 * @return The casted value.
	//	 * @throws NullFieldException When the value is <code>null</code> and the field signature is not nullable.
	//	 * @throws IncompatibleFieldException When the value is not compatible with the value type of the field signature.
	//	 */
	//	// TODO: find a better method name
	//	public V cast(final Object value) {
	//		if (null == value) {
	//			if (isNullable()) {
	//				return null;
	//			} else {
	//				throw new NullFieldException("Value is null");
	//			}
	//		} else {
	//			final Class<V> type = getType();
	//			if (type.isInstance(value)) {
	//				return type.cast(value);
	//			} else {
	//				throw new IncompatibleFieldException("Value \"" + value + "\" has not type \"" + type + "\"");
	//			}
	//		}
	//	}
	//
	//	/**
	//	 * Tests whether the given value is compatible with this field signature.
	//	 * <p>
	//	 * Values are compatible when their type and nullity are compatible with the constraints of the signature.
	//	 *
	//	 * @param value Value to test.
	//	 * @return A success when the value is compatible with the signature, a failure otherwise.
	//	 */
	//	public Result<Unit> isCompatible(final Object value) {
	//		if (null == value) {
	//			return isNullable() ? SUCCESS : NULL_FAILURE;
	//		} else if (!getType().isInstance(value)) {
	//			return Result.failure(new IncompatibleFieldException("Value \"" + value + "\" is not compatible with type \"" + getType() + "\""));
	//		} else {
	//			return SUCCESS;
	//		}
	//	}
	//
	//	/**
	//	 * Tests whether the given field signature is a sub signature this field signature.
	//	 * <p>
	//	 * The sub-typing relation is defined as follow :
	//	 * <ul>
	//	 * <li>the key must be the same,
	//	 * <li>the type of the value must be a sub type,
	//	 * <li>non-nullability must be preserved.
	//	 * </ul>
	//	 *
	//	 * @param key Field fignature to test.
	//	 * @return A success when the field signature is a sub signature, a failure otherwise.
	//	 */
	//	public Result<Unit> isSubSignature(final FieldKey<?> key) {
	//		if (!getName().equals(key.getName())) {
	//			return Result.failure(new MissingFieldException("Key \"" + key.getName() + "\" is not compatible with key \"" + getName() + "\""));
	//		} else if (!isNullable() && key.isNullable()) {
	//			return NULLABILITY_FAILURE;
	//		} else if (!getType().isAssignableFrom(key.getType())) {
	//			return Result.failure(new IncompatibleFieldException("Value type \"" + key.getType() + "\" is not compatible with type \"" + getType() + "\""));
	//		} else {
	//			return SUCCESS;
	//		}
	//	}
	//
	//	private static final Result<Unit> SUCCESS = Result.success(Unit.UNIT);
	//	private static final Result<Unit> NULL_FAILURE = Result.failure(new NullFieldException("Value must not be null"));
	//	private static final Result<Unit> NULLABILITY_FAILURE = Result.failure(new NullFieldException("Nullable field is not compatible with non-nullable field"));
}
