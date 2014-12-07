/*
 *  Copyright 2006-2013 Julien Dufour
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

import java.util.HashMap;

/**
 * The {@link SimpleRecordBuilder} class provides a simple implementation of {@link RecordBuilder record builders}.
 * 
 * @param <K> Type of the keys.
 */
public class SimpleRecordBuilder<K extends FieldKey<? extends K, ?>>
extends BaseRecordBuilder<K, Record<K>> {
	/**
	 * Instantiates a new empty record builder.
	 */
	public SimpleRecordBuilder() {
		super();
	}
	
	@Override
	public Record<K> build() {
		return new SimpleRecord<>(new HashMap<>(_fields));
	}
}
