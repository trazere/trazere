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
 * The {@link StrongFieldKey} class implements keys of {@link Field fields} whose equality relies on their physical identity.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the value of the field.
 * @since 2.0
 */
public abstract class StrongFieldKey<K extends StrongFieldKey<K, V>, V>
extends FieldKey<K, V> {
	/**
	 * Instantiates a new non-nullable field key.
	 * 
	 * @param label Label of the field.
	 * @param type Type of the value of the field.
	 * @since 2.0
	 */
	public StrongFieldKey(final String label, final Class<V> type) {
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
	public StrongFieldKey(final String label, final Class<V> type, final boolean nullable) {
		super(label, type, nullable);
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
}
