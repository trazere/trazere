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
	
	/**
	 * Unifier of {@link LabelFieldKey}s.
	 * 
	 * @since 2.0
	 */
	public static final FieldKeyUnifier<LabelFieldKey<?>> UNIFIER = new FieldKeyUnifier<LabelFieldKey<?>>() {
		@Override
		public <V1, V2> FieldKey<LabelFieldKey<?>, ?> unify(final FieldKey<LabelFieldKey<?>, V1> key1, final FieldKey<LabelFieldKey<?>, V2> key2) {
			// Compute the label.
			final String label1 = key1.getLabel();
			final String label2 = key2.getLabel();
			if (!label1.equals(label2)) {
				throw new IncompatibleFieldException("Cannot unify field key + \"" + key1 + "\" with fueld key \"" + key2 + "\" (incompatible labels)");
			}
			final String unifiedLabel = label1;
			
			// Compute the type.
			final Class<V1> type1 = key1.getType();
			final Class<V2> type2 = key2.getType();
			final Class<?> unifiedType;
			if (type1.isAssignableFrom(type2)) {
				unifiedType = type2;
			} else if (type2.isAssignableFrom(type1)) {
				unifiedType = type1;
			} else {
				throw new IncompatibleFieldException("Cannot unify field key + \"" + key1 + "\" with fueld key \"" + key2 + "\" (incompatible types)");
			}
			
			// Compute the nullabilty.
			final boolean unifiedNullable = key1.isNullable() && key2.isNullable();
			
			// Build the unified key.
			return new LabelFieldKey<>(unifiedLabel, unifiedType, unifiedNullable);
		}
	};
	
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
	
	public static void main(final String[] args) {
		final RecordSignatureBuilder<LabelFieldKey<?>, ?> builder = new SimpleRecordSignatureBuilder<>();
		builder.add(new LabelFieldKey<>("A", Object.class, false));
		builder.unify(new LabelFieldKey<>("A", String.class, true), UNIFIER);
		builder.unify(new LabelFieldKey<>("B", Integer.class, true), UNIFIER);
		System.out.println(builder.build());
	}
}
