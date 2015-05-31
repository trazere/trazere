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

import com.trazere.core.record.FieldKey;
import com.trazere.core.util.Serializer;

/**
 * The {@link CSVHeader} class represents column headers of CSV tables.
 * 
 * @param <V> Type of the value.
 * @since 1.0
 */
public final class CSVHeader<V>
extends FieldKey<CSVHeader<V>, V> {
	/**
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @param serializer Serializer of the value of the field to its string representation.
	 * @since 1.0
	 */
	public CSVHeader(final String label, final Class<V> type, final Serializer<V, String> serializer) {
		super(label, type);
		
		// Checks.
		assert null != serializer;
		
		// Initialization.
		_serializer = serializer;
	}
	
	// Serializer.
	
	/**
	 * Serializer of the value of the field to its string representation.
	 * 
	 * @since 1.0
	 */
	protected final Serializer<V, String> _serializer;
	
	/**
	 * Gets the serializer of the value of the field.
	 * 
	 * @return The serializer.
	 */
	public Serializer<V, String> getSerializer() {
		return _serializer;
	}
}
