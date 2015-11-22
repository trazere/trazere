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

import com.trazere.core.collection.Lists;
import com.trazere.core.collection.Sets;
import com.trazere.core.util.Result;
import com.trazere.core.util.Unit;
import java.util.Arrays;
import java.util.Set;

/**
 * The {@link RecordSignatures} class provides various factories of {@link RecordSignature record signatures}.
 * 
 * @see RecordSignature
 * @since 2.0
 */
public class RecordSignatures {
	/**
	 * Builds an empty record signature.
	 * 
	 * @param <K> Type of the field keys.
	 * @return The built record signature.
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <K extends FieldKey<K, ?>> RecordSignature<K> empty() {
		return (RecordSignature<K>) EMPTY;
	}
	
	private static final RecordSignature<?> EMPTY = new BaseRecordSignature<DummyFieldKey<?>>() {
		@Override
		public int size() {
			return 0;
		}
		
		@Override
		public boolean isEmpty() {
			return true;
		}
		
		@Override
		public boolean contains(final FieldKey<DummyFieldKey<?>, ?> key) {
			return false;
		}
		
		@Override
		public Set<? extends FieldKey<DummyFieldKey<?>, ?>> keys() {
			return Sets.empty();
		}
		
		@Override
		public boolean isAssignableFrom(final RecordSignature<DummyFieldKey<?>> signature) {
			return true;
		}
		
		@Override
		public Result<Unit> checkAssignableFrom(final RecordSignature<DummyFieldKey<?>> signature) {
			return CHECK_SUCCESS;
		}
		
		@Override
		public Result<Unit> checkValues(final Record<DummyFieldKey<?>> record) {
			return CHECK_SUCCESS;
		}
	};
	
	private static abstract class DummyFieldKey<V>
	extends FieldKey<DummyFieldKey<?>, V> {
		public DummyFieldKey(final String label, final Class<V> type, final boolean nullable) {
			super(label, type, nullable);
		}
	}
	
	private static final Result<Unit> CHECK_SUCCESS = Result.success(Unit.UNIT);
	
	/**
	 * Builds a record signature containing the given field key.
	 * 
	 * @param <K> Type of the field keys.
	 * @param key Field key.
	 * @return The built record signature.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> fromKey(final FieldKey<K, ?> key) {
		return fromKeys(Lists.fromElement(key));
	}
	
	/**
	 * Builds a record signature containing the given field keys.
	 * 
	 * @param <K> Type of the field keys.
	 * @param key1 First field key.
	 * @param key2 Second field key.
	 * @return The built record signature.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> fromKeys(final FieldKey<K, ?> key1, final FieldKey<K, ?> key2) {
		return fromKeys(Lists.fromElements(key1, key2));
	}
	
	/**
	 * Builds a record signature containing the given field keys.
	 * 
	 * @param <K> Type of the field keys.
	 * @param key1 First field key.
	 * @param key2 Second field key.
	 * @param key3 Third field key.
	 * @return The built record signature.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> fromKeys(final FieldKey<K, ?> key1, final FieldKey<K, ?> key2, final FieldKey<K, ?> key3) {
		return fromKeys(Lists.fromElements(key1, key2, key3));
	}
	
	/**
	 * Builds a record signature containing the given field keys.
	 * 
	 * @param <K> Type of the field keys.
	 * @param keys Field keys.
	 * @return The built record signature.
	 * @since 2.0
	 */
	@SafeVarargs
	public static <K extends FieldKey<K, ?>> RecordSignature<K> fromKeys(final FieldKey<K, ?>... keys) {
		return fromKeys(Arrays.asList(keys));
	}
	
	/**
	 * Builds a record signature containing the given field keys.
	 * 
	 * @param <K> Type of the field keys.
	 * @param keys Field keys.
	 * @return The built record signature.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> fromKeys(final Iterable<? extends FieldKey<K, ?>> keys) {
		final RecordSignatureBuilder<K, ?> builder = new SimpleRecordSignatureBuilder<>();
		builder.addAll(keys);
		return builder.build();
	}
	
	private RecordSignatures() {
		// Prevent instantiation.
	}
}
