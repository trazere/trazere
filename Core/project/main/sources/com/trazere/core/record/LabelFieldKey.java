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

import com.trazere.core.lang.HashCode;

/**
 * The {@link LabelFieldKey} class implements keys of {@link Field fields} whose equality relies on their label.
 * 
 * @param <V> Type of the value of the field.
 * @since 2.0
 */
public class LabelFieldKey<V>
extends FieldKey<LabelFieldKey<?>, V> {
	/**
	 * Instantiates a new non-nullable field key.
	 * 
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @since 2.0
	 */
	public LabelFieldKey(final String label, final Class<V> type) {
		super(label, type);
	}
	
	/**
	 * Instantiates a new field key.
	 * 
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @param nullable Flag indicating whether the value of the field is nullable of not.
	 * @since 2.0
	 */
	public LabelFieldKey(final String label, final Class<V> type, final boolean nullable) {
		super(label, type, nullable);
	}
	
	// Signature.
	
	@Override
	public FieldKey<LabelFieldKey<?>, ?> unifyWith(final FieldKey<LabelFieldKey<?>, ?> key) {
		if (key == this) {
			return this;
		} else {
			// Unify the label.
			final String label = getLabel();
			final String keyLabel = key.getLabel();
			if (!label.equals(keyLabel)) {
				throw new IncompatibleFieldException("Cannot unify field key + \"" + this + "\" with fueld key \"" + key + "\" (incompatible labels)");
			}
			final String unifiedLabel = label;
			
			// Unify the type.
			final Class<V> type = getType();
			final Class<?> keyType = key.getType();
			final Class<?> unifiedType;
			if (type.isAssignableFrom(keyType)) {
				unifiedType = keyType;
			} else if (keyType.isAssignableFrom(type)) {
				unifiedType = type;
			} else {
				throw new IncompatibleFieldException("Cannot unify field key + \"" + this + "\" with fueld key \"" + key + "\" (incompatible types)");
			}
			
			// Unify the nullabilty.
			final boolean unifiedNullable = isNullable() && key.isNullable();
			
			// Build the unified key.
			return new LabelFieldKey<>(unifiedLabel, unifiedType, unifiedNullable);
		}
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(getLabel());
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final LabelFieldKey<?> key = (LabelFieldKey<?>) object;
			return getLabel().equals(key.getLabel());
		} else {
			return false;
		}
	}
}
