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

import com.trazere.core.collection.CollectionFactories;
import com.trazere.core.collection.CollectionUtils;
import com.trazere.core.collection.SetUtils;
import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Predicate;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link RecordUtils} class provides various utilities regarding {@link Record records}.
 * 
 * @see Record
 * @since 2.0
 */
public class RecordUtils {
	/**
	 * Executes the given procedure with each field of the given record.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields over which to iterate.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> void foreach(final Record<K> record, final Procedure<? super Field<K, ?>> procedure) {
		IterableUtils.foreach(record.fields(), procedure);
	}
	
	/**
	 * Left folds over the fields of the given record using the given binary operator and initial state.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <S> Type of the state.
	 * @param record Record containing the fields over which to fold.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, S> S fold(final Record<K> record, final Function2<? super S, ? super Field<K, ?>, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(record.fields(), operator, initialState);
	}
	
	/**
	 * Gets the first field of the given record accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to filter.
	 * @param filter Predicate to use to filter the fields.
	 * @return The first accepted field.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Maybe<Field<K, ?>> first(final Record<K> record, final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.first(record.fields(), filter);
	}
	
	/**
	 * Gets the first element extracted from the fields of the given record by the given extractor.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <EE> Type of the extracted elements.
	 * @param record Record containing the fields from which to extract.
	 * @param extractor Function to use to extract from the fields.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, EE> Maybe<? extends EE> extractFirst(final Record<K> record, final Function<? super Field<K, ?>, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(record.fields(), extractor);
	}
	
	/**
	 * Tests whether any field of the given record is accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to test.
	 * @param filter Predicate to use to filter the fields.
	 * @return <code>true</code> when some field is accepted, <code>false</code> when all fields are rejected.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> boolean isAny(final Record<K> record, final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.isAny(record.fields(), filter);
	}
	
	/**
	 * Tests whether all fields of the given record are accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to test.
	 * @param filter Predicate to use to filter the fields.
	 * @return <code>true</code> when all fields are accepted, <code>false</code> when some field is rejected.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> boolean areAll(final Record<K> record, final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.areAll(record.fields(), filter);
	}
	
	/**
	 * Counts the fields of the given record accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to count.
	 * @param filter Predicate to use to filter the fields.
	 * @return The number of accepted fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> int count(final Record<K> record, final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.count(record.fields(), filter);
	}
	
	// TODO: rename to append ?
	/**
	 * Builds the union of the given records.
	 * <p>
	 * In case of conflict, the fields of the first record have precedence over the fields of the second record.
	 * 
	 * @param <K> Type of the field keys.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @return A record containing the union of the fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Record<K> union(final Record<K> record1, final Record<K> record2) {
		final Set<? extends FieldKey<K, ?>> keys1 = record1.keys();
		final Set<FieldKey<K, ?>> duplicateKeys = CollectionUtils.filter(record2.keys(), key -> keys1.contains(key), CollectionFactories.hashSet());
		return new BaseRecord<K>() {
			@Override
			public int size() {
				return record1.size() + record2.size() - duplicateKeys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return record1.isEmpty() && record2.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return record1.contains(key) || record2.contains(key);
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return new AbstractSet<FieldKey<K, ?>>() {
					@Override
					public int size() {
						return record1.size() + record2.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<FieldKey<K, ?>> iterator() {
						return IteratorUtils.append(record1.keys().iterator(), IteratorUtils.filter(record2.keys().iterator(), key -> !duplicateKeys.contains(key)));
					}
				};
			}
			
			@Override
			public <V> Maybe<V> get(final FieldKey<K, V> key)
			throws InvalidFieldException {
				final Maybe<V> value1 = record1.get(key);
				if (value1.isSome()) {
					return value1;
				} else {
					return record2.get(key);
				}
			}
			
			@Override
			public Collection<Field<K, ?>> fields() {
				return new AbstractCollection<Field<K, ?>>() {
					@Override
					public int size() {
						return record1.size() + record2.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<Field<K, ?>> iterator() {
						return IteratorUtils.append(record1.fields().iterator(), IteratorUtils.filter(record2.fields().iterator(), field -> !duplicateKeys.contains(field.getKey())));
					}
				};
			}
		};
	}
	
	// TODO: lazy union/append
	
	// TODO: rename to append
	/**
	 * Builds the union of the given records.
	 * <p>
	 * In case of conflict, the fields of the first record have precedence over the fields of the second record.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <R> Type of the result record.
	 * @param record1 First record.
	 * @param record2 Second record.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the union of the fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, R extends Record<K>> R union(final Record<K> record1, final Record<K> record2, final RecordFactory<K, R> resultFactory) {
		final RecordBuilder<K, R> builder = resultFactory.newBuilder();
		builder.setAll(record1);
		builder.completeAll(record2);
		return builder.build();
	}
	
	/**
	 * Filters the fields of the given record using the given filter.
	 *
	 * @param <K> Type of the field keys.
	 * @param record Record containing the fields to filter.
	 * @param filter Predicate to use to filter the fields.
	 * @return A record containing the filtered fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Record<K> filter(final Record<K> record, final Predicate<? super Field<K, ?>> filter) {
		assert null != record;
		assert null != filter;
		
		final Collection<Field<K, ?>> fields = CollectionUtils.unmodifiable(new AbstractCollection<Field<K, ?>>() {
			@Override
			public int size() {
				return IterableUtils.count(record.fields(), filter);
			}
			
			@Override
			public boolean isEmpty() {
				return !IterableUtils.isAny(record.fields(), filter);
			}
			
			@Override
			public Iterator<Field<K, ?>> iterator() {
				return IteratorUtils.filter(record.fields().iterator(), filter);
			}
		});
		
		final Set<FieldKey<K, ?>> keys = SetUtils.unmodifiable(new AbstractSet<FieldKey<K, ?>>() {
			@Override
			public int size() {
				return fields.size();
			}
			
			@Override
			public boolean isEmpty() {
				return fields.isEmpty();
			}
			
			@Override
			public Iterator<FieldKey<K, ?>> iterator() {
				return IteratorUtils.map(fields.iterator(), Field::getKey);
			}
		});
		
		return new BaseRecord<K>() {
			@Override
			public int size() {
				return fields.size();
			}
			
			@Override
			public boolean isEmpty() {
				return fields.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return innerContains(key);
			}
			
			private <V> boolean innerContains(final FieldKey<K, V> key) {
				final Maybe<V> value = record.get(key);
				return value.isSome() && filter.evaluate(Fields.fromKeyAndValue(key, value.asSome().getValue()));
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return keys;
			}
			
			@Override
			public <V> Maybe<V> get(final FieldKey<K, V> key)
			throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
				return record.get(key).filter(value -> filter.evaluate(Fields.fromKeyAndValue(key, value)));
			}
			
			@Override
			public Collection<? extends Field<K, ?>> fields() {
				return fields;
			}
		};
	}
	
	/**
	 * Filters the fields of the given record using the given filter.
	 *
	 * @param <K> Type of the field keys.
	 * @param <R> Type of the result record.
	 * @param record Record containing the fields to filter.
	 * @param filter Predicate to use to filter the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the filtered fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, R extends Record<K>> R filter(final Record<K> record, final Predicate<? super Field<K, ?>> filter, final RecordFactory<K, R> resultFactory) {
		final RecordBuilder<K, R> builder = resultFactory.newBuilder();
		for (final Field<K, ?> field : record.fields()) {
			if (filter.evaluate(field)) {
				builder.set(field);
			}
		}
		return builder.build();
	}
	
	// TODO: add a lazy map (no factory arg)
	
	/**
	 * Transforms the fields of the given record using the given function.
	 *
	 * @param <K> Type of the field keys.
	 * @param <TK> Type of the transformed field keys.
	 * @param <R> Type of the result record.
	 * @param record Record containing the fields to transform.
	 * @param function Function to use to transform the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the transformed fields.
	 * @throws DuplicateFieldException When sereral transformed field are conflicting.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, TK extends FieldKey<TK, ?>, R extends Record<TK>> R map(final Record<K> record, final Function<? super Field<K, ?>, ? extends Field<TK, ?>> function, final RecordFactory<TK, R> resultFactory)
	throws DuplicateFieldException {
		final RecordBuilder<TK, R> builder = resultFactory.newBuilder();
		for (final Field<K, ?> field : record.fields()) {
			builder.add(function.evaluate(field));
		}
		return builder.build();
	}
	
	// TODO: add a lazy extract (no factory arg)
	
	/**
	 * Extracts the fields of the given record using the given extractor.
	 *
	 * @param <K> Type of the field keys.
	 * @param <EK> Type of the extracted field keys.
	 * @param <R> Type of the result record.
	 * @param record Record containing the fields to extract from.
	 * @param extractor Function to use to extract the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the extracted fields.
	 * @throws DuplicateFieldException When sereral extracted field are conflicting.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, EK extends FieldKey<EK, ?>, R extends Record<EK>> R extract(final Record<K> record, final Function<? super Field<K, ?>, ? extends Maybe<? extends Field<EK, ?>>> extractor, final RecordFactory<EK, R> resultFactory)
	throws DuplicateFieldException {
		final RecordBuilder<EK, R> builder = resultFactory.newBuilder();
		for (final Field<K, ?> field : record.fields()) {
			final Maybe<? extends Field<EK, ?>> extractedField = extractor.evaluate(field);
			if (extractedField.isSome()) {
				builder.add(extractedField.asSome().getValue());
			}
		}
		return builder.build();
	}
	
	private RecordUtils() {
		// Prevent instantiation.
	}
}
