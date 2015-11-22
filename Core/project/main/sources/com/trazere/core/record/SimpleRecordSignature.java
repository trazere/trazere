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

import java.util.Collections;
import java.util.Set;

/**
 * The {@link SimpleRecordSignature} class provides a simple implementation of {@link RecordSignature record signatures}.
 * 
 * @param <K> Type of the field keys.
 * @since 2.0
 */
public class SimpleRecordSignature<K extends FieldKey<K, ?>>
extends BaseRecordSignature<K> {
	/**
	 * Keys the fields.
	 * 
	 * @since 2.0
	 */
	protected final Set<? extends FieldKey<K, ?>> _keys;
	
	/**
	 * Instantiates a new record signature with the given field keys.
	 * 
	 * @param keys Keys of the fields.
	 * @since 2.0
	 */
	protected SimpleRecordSignature(final Set<? extends FieldKey<K, ?>> keys) {
		assert null != keys;
		
		// Initialization.
		_keys = Collections.unmodifiableSet(keys);
	}
	
	@Override
	public int size() {
		return _keys.size();
	}
	
	@Override
	public boolean isEmpty() {
		return _keys.isEmpty();
	}
	
	@Override
	public boolean contains(final FieldKey<K, ?> key) {
		return _keys.contains(key);
	}
	
	@Override
	public Set<? extends FieldKey<K, ?>> keys() {
		return _keys;
	}
}
