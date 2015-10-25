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

import com.trazere.core.design.Factory;

/**
 * The {@link RecordFactory} interface defines factories of {@link Record records}.
 * 
 * @param <K> Type of the field keys.
 * @param <R> Type of the records.
 * @see Record
 * @since 2.0
 */
public interface RecordFactory<K extends FieldKey<? extends K, ?>, R extends Record<K>>
extends Factory<R> {
	/**
	 * Builds a new empty record.
	 * 
	 * @return The built record.
	 * @since 2.0
	 */
	@Override
	R build();
	
	// TODO: build that take an iterable of fields
	
	/**
	 * Builds a new record containing the fields of given record.
	 * 
	 * @param record Record to copy.
	 * @return The built record.
	 * @since 2.0
	 */
	default R build(final Record<? extends K> record) {
		final RecordBuilder<K, R> builder = newBuilder();
		builder.addAll(record);
		return builder.build();
	}
	
	/**
	 * Builds a new empty builder of record.
	 * 
	 * @return The built record builder.
	 * @since 2.0
	 */
	RecordBuilder<K, R> newBuilder();
}
