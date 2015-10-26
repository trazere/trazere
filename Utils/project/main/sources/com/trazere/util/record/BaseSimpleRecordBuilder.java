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
package com.trazere.util.record;

import java.util.Map;

/**
 * The {@link BaseSimpleRecordBuilder} abstract class provides a skeleton implementation of {@link RecordBuilder builders of records}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @deprecated Use {@link BaseRecordBuilder}. (since 1.0)
 */
@Deprecated
public abstract class BaseSimpleRecordBuilder<K, V, R extends Record<K, V>>
extends BaseRecordBuilder<K, V, R> {
	/**
	 * Instantiate a new empty record builder.
	 */
	public BaseSimpleRecordBuilder() {
		super();
	}
	
	/**
	 * Instantiate a new record builder populated with the given fields.
	 * 
	 * @param fields Values of the initial fields identified by their keys.
	 */
	public BaseSimpleRecordBuilder(final Map<? extends K, ? extends V> fields) {
		super(fields);
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record.
	 * 
	 * @param record Record containing the initial fields of the new record builder.
	 * @throws RecordException When the given record cannot be read.
	 */
	public BaseSimpleRecordBuilder(final Record<? extends K, ? extends V> record)
	throws RecordException {
		super(record);
	}
	
	/**
	 * Instantiate a new record builder populated with the fields of the given record builder.
	 * 
	 * @param builder Record builder containing the initial fields of the new record builder.
	 * @throws RecordException When the given record builder cannot populate the new record builder.
	 */
	public BaseSimpleRecordBuilder(final RecordBuilder<? extends K, ? extends V, ?> builder)
	throws RecordException {
		super(builder);
	}
}
