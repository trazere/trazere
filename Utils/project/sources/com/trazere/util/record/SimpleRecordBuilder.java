/*
 *  Copyright 2006-2011 Julien Dufour
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
package com.trazere.util.record;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link SimpleRecordBuilder} class implements builders of {@link SimpleRecord simple records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecord
 */
public class SimpleRecordBuilder<K, V>
extends BaseSimpleRecordBuilder<K, V, SimpleRecord<K, V>> {
	/**
	 * Instantiate a new empty record builder.
	 */
	public SimpleRecordBuilder() {
		super();
	}
	
	/**
	 * Instantiate a new record builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 */
	protected SimpleRecordBuilder(final Map<? extends K, ? extends V> fields) {
		super(fields);
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 * @throws RecordException When the given record cannot be read.
	 */
	public SimpleRecordBuilder(final Record<? extends K, ? extends V> record)
	throws RecordException {
		super(record);
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @throws RecordException When the given record builder cannot populate the new record builder.
	 */
	public SimpleRecordBuilder(final RecordBuilder<? extends K, ? extends V, ?> builder)
	throws RecordException {
		super(builder);
	}
	
	public SimpleRecord<K, V> build() {
		return new SimpleRecord<K, V>(new HashMap<K, V>(_fields));
	}
}
