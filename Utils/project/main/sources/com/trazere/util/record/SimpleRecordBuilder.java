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
package com.trazere.util.record;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link SimpleRecordBuilder} class implements builders of {@link SimpleRecord simple records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecord
 * @deprecated Use {@link com.trazere.core.record.SimpleRecordBuilder}.
 */
@Deprecated
public class SimpleRecordBuilder<K, V>
extends BaseRecordBuilder<K, V, SimpleRecord<K, V>> {
	/**
	 * Instantiates a new empty record builder.
	 * 
	 * @deprecated Use {@link com.trazere.core.record.SimpleRecordBuilder#SimpleRecordBuilder()}.
	 */
	@Deprecated
	public SimpleRecordBuilder() {
		super();
	}
	
	/**
	 * Instantiates a new record builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 * @deprecated To be removed.
	 */
	@Deprecated
	protected SimpleRecordBuilder(final Map<? extends K, ? extends V> fields) {
		super(fields);
	}
	
	/**
	 * Instantiates a new record builder populated with the fields of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 * @throws InvalidFieldException When the some field of the given record cannot be read.
	 * @deprecated Use com.trazere.core.record.SimpleRecordBuilder#addAll(com.trazere.core.record.Record)}.
	 */
	@Deprecated
	public SimpleRecordBuilder(final Record<? extends K, ? extends V> record)
	throws InvalidFieldException {
		super(record);
	}
	
	/**
	 * Instantiates a new record builder populated with the fields of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @deprecated To be removed.
	 */
	@Deprecated
	public SimpleRecordBuilder(final RecordBuilder<? extends K, ? extends V, ?> builder) {
		super(builder);
	}
	
	@Override
	public SimpleRecord<K, V> build() {
		return new SimpleRecord<>(new HashMap<>(_fields));
	}
}
