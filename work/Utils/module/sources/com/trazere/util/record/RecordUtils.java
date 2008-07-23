/*
 *  Copyright 2008 Julien Dufour
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

import com.trazere.util.lang.MultipleComparator;
import com.trazere.util.lang.ReverseComparator;
import com.trazere.util.type.Tuple2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The {@link RecordUtils} class provides various utilities regarding record manipulatation.
 */
public class RecordUtils {
	private static final RecordFactory<?, ?, ?> _SIMPLE_RECORD_FACTORY = new RecordFactory<Object, Object, SimpleRecord<Object, Object>>() {
		public SimpleRecord<Object, Object> build()
		throws RecordException {
			return SimpleRecord.build();
		}
		
		public SimpleRecord<Object, Object> build(final Map<Object, Object> fields)
		throws RecordException {
			return SimpleRecord.build(fields);
		}
	};
	
	/**
	 * Build a factory of simple records.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @return The factory.
	 * @see SimpleRecord
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> RecordFactory<K, V, SimpleRecord<K, V>> buildSimpleRecordFactory() {
		return (RecordFactory<K, V, SimpleRecord<K, V>>) _SIMPLE_RECORD_FACTORY;
	}
	
	/**
	 * Fill the given record builder with fields identified by the given keys and containing the given value.
	 * <p>
	 * No fields may be identified by any given keys in the given builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value of the fields. May be <code>null</code>.
	 * @throws DuplicateFieldException When some field is identified by the given key in the receiver builder.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V> void fillRecord(final RecordBuilder<? super K, ? super V, ?> builder, final Collection<? extends K> keys, final V value)
	throws RecordException {
		assert null != builder;
		assert null != keys;
		
		// Fill.
		for (final K key : keys) {
			builder.add(key, value);
		}
	}
	
	/**
	 * Fill the given record builder with fields identified by the missing given keys and containing the given value.
	 * <p>
	 * This method only add the missing fields, the existing ones are not modified.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the value.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value to set. May be <code>null</code>.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V> void completeRecord(final RecordBuilder<? super K, ? super V, ?> builder, final Collection<? extends K> keys, final V value)
	throws RecordException {
		assert null != builder;
		assert null != keys;
		
		// Fill.
		for (final K key : keys) {
			if (!builder.contains(key)) {
				builder.add(key, value);
			}
		}
	}
	
	/**
	 * Compute the sub record of the given record containing only the fields identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record Record to read.
	 * @param keys Key of the fields to keep.
	 * @return The sub record.
	 * @throws MissingFieldException When no fields are identified by any given key in the the given record.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V> SimpleRecord<K, V> subRecord(final Record<? super K, ? extends V> record, final Set<? extends K> keys)
	throws RecordException {
		return subRecord(record, keys, RecordUtils.<K, V>buildSimpleRecordFactory());
	}
	
	/**
	 * Compute the sub record of the given record containing only the fields identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the sub record.
	 * @param record Record to read.
	 * @param keys Key of the fields to keep.
	 * @param factory Record factory to use.
	 * @return The sub record.
	 * @throws MissingFieldException When no fields are identified by any given key in the the given record.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V, R extends Record<K, V>> R subRecord(final Record<? super K, ? extends V> record, final Set<? extends K> keys, final RecordFactory<K, V, R> factory)
	throws RecordException {
		assert null != record;
		assert null != keys;
		assert null != factory;
		
		// Build the sub record.
		if (keys.isEmpty()) {
			return factory.build();
		} else {
			final Map<K, V> fields = new HashMap<K, V>();
			for (final K key : keys) {
				fields.put(key, record.get(key));
			}
			return factory.build(fields);
		}
	}
	
	/**
	 * Popultate the given record builder with the fields of the given record identified by the given keys.
	 * <p>
	 * Some field must be identified by every given key in the given record and no fields may be identified by any given key in the given builder.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <B> Type of the record builder.
	 * @param record Record to read.
	 * @param keys Key of the fields to copy.
	 * @param builder Record builder to populate.
	 * @return The given record builder.
	 * @throws MissingFieldException When no fields are identified by any given key in the the given record.
	 * @throws DuplicateFieldException When some field is identified by any given key in the given builder.
	 * @throws RecordException When the fields cannot be read.
	 * @throws RecordException When the fields cannot be added.
	 */
	public static <K, V, B extends RecordBuilder<? super K, ? super V, ?>> B subRecord(final Record<? super K, ? extends V> record, final Set<? extends K> keys, final B builder)
	throws RecordException {
		assert null != record;
		assert null != keys;
		assert null != builder;
		
		// Fill the builder.
		for (final K key : keys) {
			builder.add(key, record.get(key));
		}
		return builder;
	}
	
	/**
	 * Build the union of the given records.
	 * <p>
	 * The keys identifying the fields of the given records must not overlap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @return The union record.
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V> SimpleRecord<K, V> union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2)
	throws RecordException {
		return union(record1, record2, RecordUtils.<K, V>buildSimpleRecordFactory());
	}
	
	/**
	 * Build the union of the given records.
	 * <p>
	 * The keys identifying the fields of the given records must not overlap.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the union record.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @param factory Record factory to use.
	 * @return The union record.
	 * @throws DuplicateFieldException When some fields are identified by a same key in both records.
	 * @throws RecordException When the fields cannot be read.
	 */
	public static <K, V, R extends Record<K, V>> R union(final Record<? extends K, ? extends V> record1, final Record<? extends K, ? extends V> record2, final RecordFactory<K, V, R> factory)
	throws RecordException {
		assert null != record1;
		assert null != record2;
		assert null != factory;
		
		// Compute the fields.
		final Map<K, V> fields = new HashMap<K, V>(record1.asMap());
		for (final Map.Entry<? extends K, ? extends V> entry : record2.asMap().entrySet()) {
			final K key = entry.getKey();
			if (fields.containsKey(key)) {
				throw new DuplicateFieldException("Field \"" + key + "\" exist in both record " + record1 + " and " + record2);
			}
			fields.put(key, entry.getValue());
		}
		
		// Build the record.
		if (fields.isEmpty()) {
			return factory.build();
		} else {
			return factory.build(fields);
		}
	}
	
	/**
	 * Build a record comparator according to the values of the fields identified by the given keys and the given order using the given comparator.
	 * <p>
	 * The given order is applied to every compared field.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the records.
	 * @param criterion List of the keys of the fields used for compararison in the order of importance.
	 * @param ascending <code>true</code> to sort in the ascending order, <code>false</code> to sort in the descending order.
	 * @param comparator Comparator of the values of the fields.
	 * @return The comparator.
	 */
	public static <K, V, R extends Record<? super K, ? extends V>> Comparator<R> buildRecordComparator(final List<K> criterion, final boolean ascending, final Comparator<V> comparator) {
		assert null != criterion;
		assert null != comparator;
		
		// Build the comparator.
		final List<Comparator<R>> comparators = new ArrayList<Comparator<R>>();
		for (final K criteria : criterion) {
			comparators.add(ReverseComparator.build(new RecordComparator<K, V, R>(criteria, comparator), ascending));
		}
		return new MultipleComparator<R>(comparators);
	}
	
	/**
	 * Build a record comparator according to the values of the fields identified by the given keys and the given orders using the given comparator.
	 * <p>
	 * A specific order is applied to every compared field.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the records.
	 * @param criterion List of the keys of the fields used for compararison and of their order in the order of importance.
	 * @param comparator Comparator of the values of the fields.
	 * @return The comparator.
	 */
	public static <K, V, R extends Record<? super K, ? extends V>> Comparator<R> buildRecordComparator(final List<Tuple2<K, Boolean>> criterion, final Comparator<V> comparator) {
		assert null != criterion;
		
		// Build the comparator.
		final List<Comparator<R>> comparators = new ArrayList<Comparator<R>>();
		for (final Tuple2<K, Boolean> criteria : criterion) {
			comparators.add(ReverseComparator.build(new RecordComparator<K, V, R>(criteria.getFirst(), comparator), criteria.getSecond().booleanValue()));
		}
		return new MultipleComparator<R>(comparators);
	}
	
	private RecordUtils() {
		// Prevent instantiation.
	}
}
