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

import com.trazere.util.Assert;
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
 * The {@link RecordUtils} class provides various utilities to manipulate records.
 */
public class RecordUtils {
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
		Assert.notNull(criterion);
		Assert.notNull(comparator);
		
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
		Assert.notNull(criterion);
		
		// Build the comparator.
		final List<Comparator<R>> comparators = new ArrayList<Comparator<R>>();
		for (final Tuple2<K, Boolean> criteria : criterion) {
			comparators.add(ReverseComparator.build(new RecordComparator<K, V, R>(criteria.getFirst(), comparator), criteria.getSecond().booleanValue()));
		}
		return new MultipleComparator<R>(comparators);
	}
	
	/**
	 * Fill the given record builder with new fields containing the given value for the given keys.
	 * <p>
	 * This method only add the missing fields, the existing ones are not set.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the value.
	 * @param builder Record builder to fill.
	 * @param keys Keys identifying the fields to fill.
	 * @param value Value to set. May be <code>null</code>.
	 * @throws RecordException When some field already exists.
	 */
	public static <K, V> void fillRecord(final RecordBuilder<? super K, ? super V, ?> builder, final Collection<? extends K> keys, final V value)
	throws RecordException {
		Assert.notNull(builder);
		Assert.notNull(keys);
		
		// Fill.
		for (final K key : keys) {
			if (!builder.contains(key)) {
				builder.add(key, value);
			}
		}
	}
	
	/**
	 * Compute the sub record of the given record containing only the fields identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <R> Type of the sub record.
	 * @param record Record to read.
	 * @param keys Key of the fields to keep.
	 * @param factory Record factory to use.
	 * @return The sub record.
	 * @throws RecordException When some field does not exist.
	 */
	public static <K, V, R extends Record<K, V>> R subRecord(final Record<? super K, ? extends V> record, final Set<? extends K> keys, final RecordFactory<K, V, R> factory)
	throws RecordException {
		Assert.notNull(record);
		Assert.notNull(keys);
		Assert.notNull(factory);
		
		// Build the sub record.
		if (keys.isEmpty()) {
			return factory.build();
		} else {
			final Map<K, V> values = new HashMap<K, V>();
			for (final K key : keys) {
				values.put(key, record.get(key));
			}
			return factory.build(values);
		}
	}
	
	/**
	 * Fill the given record builder with the fields of the given record identified by the given keys.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param record Record to read.
	 * @param keys Key of the fields to keep.
	 * @param builder Record builder to fill.
	 * @throws RecordException When some field already exist.
	 */
	public static <K, V> void subRecord(final Record<? super K, ? extends V> record, final Set<? extends K> keys, final RecordBuilder<? super K, ? super V, ?> builder)
	throws RecordException {
		Assert.notNull(record);
		Assert.notNull(keys);
		Assert.notNull(builder);
		
		// Fill the builder.
		for (final K key : keys) {
			builder.add(key, record.get(key));
		}
	}
	
	private RecordUtils() {
		// Prevent instantiation.
	}
}
