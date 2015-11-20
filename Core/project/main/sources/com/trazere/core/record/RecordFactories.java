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

/**
 * The {@link RecordFactories} class provides various factories of {@link RecordFactory record factories}.
 * 
 * @see RecordFactory
 * @since 2.0
 */
public class RecordFactories {
	/**
	 * Builds a simple factory of records.
	 * 
	 * @param <K> Type of the field keys.
	 * @return The built record factory.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <K extends FieldKey<K, ?>> RecordFactory<K, Record<K>> simple() {
		return (RecordFactory<K, Record<K>>) SIMPLE;
	}
	
	private static final RecordFactory<?, ? extends Record<?>> SIMPLE = new RecordFactory<SimpleFieldKey<?>, Record<SimpleFieldKey<?>>>() {
		@Override
		public Record<SimpleFieldKey<?>> build() {
			return Records.empty();
		}
		
		@Override
		public RecordBuilder<SimpleFieldKey<?>, Record<SimpleFieldKey<?>>> newBuilder() {
			return new SimpleRecordBuilder<>();
		}
	};
	
	private RecordFactories() {
		// Prevent instantiation.
	}
}
