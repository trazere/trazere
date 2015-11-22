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
package com.trazere.csv;

import com.trazere.core.record.StrongFieldKey;
import com.trazere.core.util.Serializer;

/**
 * The {@link CSVHeader} class represents column headers of CSV tables.
 * 
 * @param <V> Type of the value.
 * @since 2.0
 */
public final class CSVHeader<V>
extends StrongFieldKey<CSVHeader<?>, V> {
	/**
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @param serializer Serializer of the value of the field to its string representation.
	 * @since 2.0
	 */
	public CSVHeader(final String label, final Class<V> type, final Serializer<V, String> serializer) {
		super(label, type);
		
		// Checks.
		assert null != serializer;
		
		// Initialization.
		_serializer = serializer;
	}
	
	// Signature.
	
	//	/**
	//	 * Unifier of {@link CSVHeader}s.
	//	 *
	//	 * @since 2.0
	//	 */
	//	public static final FieldKeyUnifier<CSVHeader<?>> UNIFIER = new FieldKeyUnifier<CSVHeader<?>>() {
	//		@Override
	//		@SuppressWarnings("unchecked")
	//		public <V1, V2> FieldKey<? extends CSVHeader<?>, ?> unify(final FieldKey<? extends CSVHeader<?>, V1> key1, final FieldKey<? extends CSVHeader<?>, V2> key2) {
	//			// Compute the label.
	//			final String label1 = key1.getLabel();
	//			final String label2 = key2.getLabel();
	//			if (!label1.equals(label2)) {
	//				throw new IncompatibleFieldException("Cannot unify field key + \"" + key1 + "\" with fueld key \"" + key2 + "\" (incompatible labels)");
	//			}
	//			final String unifiedLabel = label1;
	//
	//			// Compute the type.
	//			final Class<V1> type1 = key1.getType();
	//			final Class<V2> type2 = key2.getType();
	//			final Class<?> unifiedType;
	//			final Serializer<?, String> unifiedSerializer;
	//			if (type1.isAssignableFrom(type2)) {
	//				unifiedType = type2;
	//				unifiedSerializer = key2.self().getSerializer();
	//			} else if (type2.isAssignableFrom(type1)) {
	//				unifiedType = type1;
	//				unifiedSerializer = key1.self().getSerializer();
	//			} else {
	//				throw new IncompatibleFieldException("Cannot unify field key + \"" + key1 + "\" with fueld key \"" + key2 + "\" (incompatible types)");
	//			}
	//
	//			// Build the unified key.
	//			return new CSVHeader<>(unifiedLabel, (Class<Object>) unifiedType, (Serializer<Object, String>) unifiedSerializer);
	//		}
	//	};
	
	// Serializer.
	
	/**
	 * Serializer of the value of the field to its string representation.
	 * 
	 * @since 2.0
	 */
	protected final Serializer<V, String> _serializer;
	
	/**
	 * Gets the serializer of the value of the field.
	 * 
	 * @return The serializer.
	 * @since 2.0
	 */
	public Serializer<V, String> getSerializer() {
		return _serializer;
	}
}
