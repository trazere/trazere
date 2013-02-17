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

import com.trazere.util.record.BaseParametrable;
import com.trazere.util.record.Record;
import com.trazere.util.record.SimpleRecordBuilder;

/**
 * The {@link BaseRecordReader} abstract class provides a skeleton implementation of {@link RecordReader record readers}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class BaseRecordReader<K, V>
extends BaseParametrable<String, Object>
implements RecordReader<K, V> {
	@Override
	public Record<K, V> read(final Record<String, Object> parameters)
	throws ValueException {
		return read(parameters, new SimpleRecordBuilder<K, V>()).build();
	}
	
	@Override
	public Record<K, V> evaluate(final Record<String, Object> parameters)
	throws ValueException {
		return read(parameters);
	}
	
	@Override
	public RecordReader<K, V> compose(final RecordReader<String, Object> reader)
	throws ValueException {
		assert null != reader;
		
		final SimpleRecordReaderBuilder<K, V> builder = new SimpleRecordReaderBuilder<K, V>();
		for (final K key : getKeys()) {
			builder.add(key, get(key).compose(reader));
		}
		return builder.build();
	}
}
