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

import com.trazere.core.collection.MapUtils;
import com.trazere.core.util.Maybe;
import java.util.HashMap;
import java.util.Map;

// TODO: rename to SetRecordSignatureBuilder ?

/**
 * The {@link BaseRecordSignatureBuilder} class provides a skeleton implementation of {@link RecordSignatureBuilder builders of record signatures}.
 * 
 * @param <K> Type of the field keys.
 * @param <R> Type of the record signatures.
 * @since 2.0
 */
public abstract class BaseRecordSignatureBuilder<K extends FieldKey<K, ?>, R extends RecordSignature<K>>
implements RecordSignatureBuilder<K, R> {
	/**
	 * Field keys identified by themselves.
	 * 
	 * @since 2.0
	 */
	protected final Map<FieldKey<K, ?>, FieldKey<K, ?>> _keys = new HashMap<>();
	
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
		return _keys.containsKey(key);
	}
	
	@Override
	public void add(final FieldKey<K, ?> key)
	throws DuplicateFieldException {
		if (!_keys.containsKey(key)) {
			set(key);
		} else {
			throw new DuplicateFieldException("Conflicting field key \"" + key + "\"");
		}
	}
	
	@Override
	public void complete(final FieldKey<K, ?> key) {
		if (!_keys.containsKey(key)) {
			set(key);
		}
	}
	
	@Override
	public void unify(final FieldKey<K, ?> key)
	throws IncompatibleFieldException {
		final Maybe<FieldKey<K, ?>> currentKey = MapUtils.optionalGet(_keys, key);
		if (currentKey.isSome()) {
			set(currentKey.asSome().getValue().unifyWith(key));
		} else {
			set(key);
		}
	}
	
	@Override
	public void set(final FieldKey<K, ?> key) {
		assert null != key;
		
		_keys.remove(key); // Note: update the key as well
		_keys.put(key, key);
	}
	
	@Override
	public void remove(final FieldKey<K, ?> key) {
		assert null != key;
		
		_keys.remove(key);
	}
	
	@Override
	public void clear() {
		_keys.clear();
	}
}
