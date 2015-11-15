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

import com.trazere.core.util.MapComparator;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.MaybeComparators;
import java.util.Comparator;

/**
 * The {@link RecordComparators} class provides various factories of {@link Comparator comparators} related to {@link Record records}.
 * 
 * @see Comparator
 * @see Record
 * @since 2.0
 */
public class RecordComparators {
	/**
	 * Builds a comparator of records according to the comparison of the value of some field.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <V> Type of the values.
	 * @param key Key identifying the field whose value to compare.
	 * @param comparator Comparator of the values of the fields.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V> Comparator<Record<K>> fieldValue(final FieldKey<? extends K, V> key, final Comparator<? super V> comparator) {
		assert null != key;
		
		return new MapComparator<Record<K>, Maybe<V>>(MaybeComparators.maybe(comparator)) {
			@Override
			protected Maybe<V> mapValue(final Record<K> record) {
				return record.get(key);
			}
		};
	}
	
	/**
	 * Builds a comparator of records according to the comparison of the value of some optional field.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <V> Type of the values.
	 * @param key Key identifying the field whose value to compare.
	 * @param defaultValue Default value of the fields.
	 * @param comparator Comparator of the values of the fields.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V> Comparator<Record<K>> optionalFieldValue(final FieldKey<? extends K, V> key, final V defaultValue, final Comparator<? super V> comparator) {
		assert null != key;
		
		return new MapComparator<Record<K>, V>(comparator) {
			@Override
			protected V mapValue(final Record<K> record) {
				return record.getOptional(key, defaultValue);
			}
		};
	}
	
	/**
	 * Builds a comparator of records according to the comparison of the value of some mandatory field.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <V> Type of the values.
	 * @param key Key identifying the field whose value to compare.
	 * @param comparator Comparator of the values of the fields.
	 * @return The built comparator.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, V> Comparator<Record<K>> mandatoryFieldValue(final FieldKey<? extends K, V> key, final Comparator<? super V> comparator) {
		assert null != key;
		
		return new MapComparator<Record<K>, V>(comparator) {
			@Override
			protected V mapValue(final Record<K> record) {
				return record.getMandatory(key);
			}
		};
	}
	
	private RecordComparators() {
		// Prevent instantiation.
	}
}
