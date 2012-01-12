/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.record.FieldSignature;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleRecordReaderBuilder} class helps to build {@link SimpleRecordReader simple record readers}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleRecordReaderBuilder<K, V>
implements RecordReaderBuilder<K, V, SimpleRecordReader<K, V>> {
	/** Field readers. */
	protected final Map<K, ValueReader<? extends V>> _fields;
	
	/**
	 * Instantiate a new empty record reader builder.
	 */
	public SimpleRecordReaderBuilder() {
		// Initialization.
		_fields = new HashMap<K, ValueReader<? extends V>>();
	}
	
	/**
	 * Instantiate a new record reader builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 */
	public SimpleRecordReaderBuilder(final Map<? extends K, ? extends ValueReader<? extends V>> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = new HashMap<K, ValueReader<? extends V>>(fields);
	}
	
	/**
	 * Instantiate a new record reader builder populated with the fields of the given record reader builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @throws ValueException When the given record builder cannot populate the new record builder.
	 */
	public SimpleRecordReaderBuilder(final RecordReaderBuilder<? extends K, ? extends V, ?> builder)
	throws ValueException {
		assert null != builder;
		
		// Populate.
		_fields = new HashMap<K, ValueReader<? extends V>>();
		builder.populate(this);
	}
	
	public void add(final K key, final ValueReader<? extends V> value)
	throws ValueException {
		assert null != key;
		assert null != value;
		
		// Add the field.
		if (!_fields.containsKey(key)) {
			_fields.put(key, value);
		} else {
			throw new ValueException("Field \"" + key + "\" already exists in builder " + this);
		}
	}
	
	public <T extends V> void add(final FieldSignature<K, T> field, final ValueReader<? extends T> value)
	throws ValueException {
		assert null != field;
		
		// Add the field.
		add(field.getKey(), value);
	}
	
	public void addAll(final Map<? extends K, ? extends ValueReader<? extends V>> fields)
	throws ValueException {
		assert null != fields;
		
		// Add the fields.
		for (final Map.Entry<? extends K, ? extends ValueReader<? extends V>> entry : fields.entrySet()) {
			final K key = entry.getKey();
			if (!_fields.containsKey(key)) {
				_fields.put(key, entry.getValue());
			} else {
				throw new ValueException("Field \"" + key + "\" already exists in builder " + this);
			}
		}
	}
	
	public boolean contains(final K key) {
		assert null != key;
		
		// Test.
		return _fields.containsKey(key);
	}
	
	public Set<K> getKeys() {
		return Collections.unmodifiableSet(_fields.keySet());
	}
	
	public <B extends RecordReaderBuilder<? super K, ? super V, ?>> B populate(final B builder)
	throws ValueException {
		assert null != builder;
		
		// Populate.
		builder.addAll(Collections.unmodifiableMap(_fields));
		
		return builder;
	}
	
	public SimpleRecordReader<K, V> build() {
		return new SimpleRecordReader<K, V>(new HashMap<K, ValueReader<? extends V>>(_fields));
	}
}
