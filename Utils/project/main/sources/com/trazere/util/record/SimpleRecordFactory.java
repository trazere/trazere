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

import com.trazere.util.lang.BaseFactory;
import java.util.Map;

/**
 * The {@link SimpleRecordFactory} class implements factories of {@link SimpleRecord simple records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see SimpleRecord
 * @deprecated Use {@link com.trazere.core.record.RecordFactories#simple()}.
 */
@Deprecated
public class SimpleRecordFactory<K, V>
extends BaseFactory<SimpleRecord<K, V>, RecordException>
implements RecordFactory<K, V, SimpleRecord<K, V>> {
	private static final SimpleRecordFactory<?, ?> _FACTORY = new SimpleRecordFactory<Object, Object>();
	
	/**
	 * Builds a simple record factory.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The factory.
	 * @deprecated Use {@link com.trazere.core.record.RecordFactories#simple()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <K, V> SimpleRecordFactory<K, V> factory() {
		return (SimpleRecordFactory<K, V>) _FACTORY;
	}
	
	@Override
	public SimpleRecord<K, V> build() {
		return SimpleRecord.build();
	}
	
	@Override
	public SimpleRecord<K, V> build(final Map<? extends K, ? extends V> fields) {
		return SimpleRecord.build(fields);
	}
	
	@Override
	public SimpleRecord<K, V> build(final Record<? extends K, ? extends V> record)
	throws InvalidFieldException {
		return SimpleRecord.build(record);
	}
}
