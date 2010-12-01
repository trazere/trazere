/*
 *  Copyright 2006-2010 Julien Dufour
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
import com.trazere.util.record.Record;
import com.trazere.util.record.RecordBuilder;
import com.trazere.util.record.RecordException;
import com.trazere.util.record.RecordSignature;
import com.trazere.util.record.RecordSignatureBuilder;
import com.trazere.util.record.SimpleRecord;
import com.trazere.util.record.SimpleRecordBuilder;
import com.trazere.util.record.SimpleRecordSignature;
import com.trazere.util.record.SimpleRecordSignatureBuilder;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleRecordReader} class implements simple record readers.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public class SimpleRecordReader<K, V>
extends AbstractRecordReader<K, V> {
	/** Field readers. */
	protected final Map<K, ValueReader<? extends V>> _fields;
	
	/**
	 * Instanciate a new record reader with the given field readers.
	 * 
	 * @param fields Field readers.
	 */
	public SimpleRecordReader(final Map<K, ValueReader<? extends V>> fields) {
		assert null != fields;
		
		// Initialization.
		_fields = Collections.unmodifiableMap(fields);
	}
	
	@Override
	public RecordSignature<String, Object> getRequirements()
	throws RecordException {
		if (_fields.isEmpty()) {
			return SimpleRecordSignature.build();
		} else {
			final RecordSignatureBuilder<String, Object, ?> builder = new SimpleRecordSignatureBuilder<String, Object>();
			unifyRequirements(builder);
			return builder.build();
		}
	}
	
	public <B extends RecordSignatureBuilder<String, Object, ?>> B unifyRequirements(final B builder)
	throws RecordException {
		for (final ValueReader<? extends V> field : _fields.values()) {
			field.unifyRequirements(builder);
		}
		return builder;
	}
	
	public boolean isEmpty() {
		return _fields.isEmpty();
	}
	
	public boolean contains(final K key) {
		assert null != key;
		
		return _fields.containsKey(key);
	}
	
	public Set<K> getKeys() {
		return _fields.keySet();
	}
	
	public ValueReader<? extends V> get(final K key)
	throws ValueException {
		assert null != key;
		
		// Get.
		if (_fields.containsKey(key)) {
			return _fields.get(key);
		} else {
			throw new ValueException("Missing reader for field \"" + key + "\" in record reader " + this);
		}
	}
	
	public RecordSignature<K, V> getSignature()
	throws ValueException {
		try {
			final RecordSignatureBuilder<K, V, ?> builder = new SimpleRecordSignatureBuilder<K, V>();
			for (final Map.Entry<K, ValueReader<? extends V>> entry : _fields.entrySet()) {
				builder.add(FieldSignature.build(entry.getKey(), entry.getValue().getValueClass(), entry.getValue().isNullable()));
			}
			return builder.build();
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
	
	@Override
	public Record<K, V> read(final Record<String, Object> parameters)
	throws ValueException {
		if (_fields.isEmpty()) {
			return SimpleRecord.build();
		} else {
			return read(parameters, new SimpleRecordBuilder<K, V>()).build();
		}
	}
	
	public <B extends RecordBuilder<K, V, ?>> B read(final Record<String, Object> parameters, final B builder)
	throws ValueException {
		assert null != builder;
		
		try {
			// Populate.
			for (final Map.Entry<K, ValueReader<? extends V>> entry : _fields.entrySet()) {
				builder.add(entry.getKey(), entry.getValue().read(parameters));
			}
			
			return builder;
		} catch (final RecordException exception) {
			throw new ValueException(exception);
		}
	}
}
