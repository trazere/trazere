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
package com.trazere.util.value;

import com.trazere.util.lang.BaseFactory;
import com.trazere.util.lang.InternalException;
import com.trazere.util.record.DuplicateFieldException;
import com.trazere.util.record.FieldSignature;
import com.trazere.util.record.RecordException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleRecordReaderBuilder} class helps to build {@link SimpleRecordReader simple record readers}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @deprecated To be removed.
 */
@Deprecated
public class SimpleRecordReaderBuilder<K, V>
extends BaseFactory<SimpleRecordReader<K, V>, RecordException>
implements RecordReaderBuilder<K, V, SimpleRecordReader<K, V>> {
	/** Field readers. */
	protected final Map<K, ValueReader<? extends V>> _fields;
	
	/**
	 * Instantiate a new empty record reader builder.
	 * 
	 * @deprecated To be removed.
	 */
	@Deprecated
	public SimpleRecordReaderBuilder() {
		// Initialization.
		_fields = new HashMap<K, ValueReader<? extends V>>();
	}
	
	/**
	 * Instantiate a new record reader builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public SimpleRecordReaderBuilder(final Map<? extends K, ? extends ValueReader<? extends V>> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = new HashMap<K, ValueReader<? extends V>>(fields);
	}
	
	/**
	 * Instantiate a new record reader builder populated with the fields of the given record reader builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public SimpleRecordReaderBuilder(final RecordReaderBuilder<? extends K, ? extends V, ?> builder) {
		assert null != builder;
		
		// Populate.
		_fields = new HashMap<K, ValueReader<? extends V>>();
		try {
			builder.populate(this);
		} catch (final DuplicateFieldException exception) {
			throw new InternalException(exception);
		}
	}
	
	@Override
	public void add(final K key, final ValueReader<? extends V> value)
	throws DuplicateFieldException {
		assert null != key;
		assert null != value;
		
		// Add the field.
		if (!_fields.containsKey(key)) {
			_fields.put(key, value);
		} else {
			throw new DuplicateFieldException("Field \"" + key + "\" already exists in builder " + this);
		}
	}
	
	@Override
	public <T extends V> void add(final FieldSignature<K, T> field, final ValueReader<? extends T> value)
	throws DuplicateFieldException {
		assert null != field;
		
		// Add the field.
		add(field.getKey(), value);
	}
	
	@Override
	public void addAll(final Map<? extends K, ? extends ValueReader<? extends V>> fields)
	throws DuplicateFieldException {
		assert null != fields;
		
		// Add the fields.
		for (final Map.Entry<? extends K, ? extends ValueReader<? extends V>> entry : fields.entrySet()) {
			final K key = entry.getKey();
			if (!_fields.containsKey(key)) {
				_fields.put(key, entry.getValue());
			} else {
				throw new DuplicateFieldException("Field \"" + key + "\" already exists in builder " + this);
			}
		}
	}
	
	@Override
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _fields.containsKey(key);
	}
	
	@Override
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_fields.keySet());
	}
	
	@Override
	public <B extends RecordReaderBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws DuplicateFieldException {
		assert null != builder;
		
		// Populate.
		builder.addAll(Collections.unmodifiableMap(_fields));
		
		return builder;
	}
	
	@Override
	public SimpleRecordReader<K, V> build() {
		return new SimpleRecordReader<K, V>(new HashMap<K, ValueReader<? extends V>>(_fields));
	}
}
