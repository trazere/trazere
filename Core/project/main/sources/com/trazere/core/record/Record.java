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
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.IteratorUtils;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.IterableUtils;
import com.trazere.core.util.Maybe;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link Record} interface defines records.
 * <p>
 * A record is a collection of {@link Field fields} uniquely identified by their {@link FieldKey keys}.
 * 
 * @param <K> Type of the field keys.
 * @see Field
 * @since 2.0
 */
// TODO: implement traversable ?
public interface Record<K extends FieldKey<K, ?>> {
	/**
	 * Gets the size of this record.
	 * <p>
	 * The size of a record corresponds to the number of fields it contains.
	 * 
	 * @return The size of the record.
	 * @since 2.0
	 */
	default int size() {
		return keys().size();
	}
	
	/**
	 * Indicates whether this record is empty or not.
	 * <p>
	 * A record is empty when it contains no fields.
	 * 
	 * @return <code>true</code> when the record is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean isEmpty() {
		return 0 == size();
	}
	
	/**
	 * Indicates whether this record contains a field identified by the given key or not.
	 * 
	 * @param key Key of the field to look for.
	 * @return <code>true</code> when the record contains a field identified by the given key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean contains(final FieldKey<K, ?> key) {
		return keys().contains(key);
	}
	
	/**
	 * Gets a view of the keys identifying the fields of this record.
	 * 
	 * @return An unmodiable set of the field keys.
	 * @since 2.0
	 */
	default Set<? extends FieldKey<K, ?>> keys() {
		return Collections.unmodifiableSet(new AbstractSet<FieldKey<K, ?>>() {
			@Override
			public int size() {
				return fields().size();
			}
			
			@Override
			public boolean isEmpty() {
				return fields().isEmpty();
			}
			
			@Override
			public Iterator<FieldKey<K, ?>> iterator() {
				return IteratorUtils.map(fields().iterator(), Field::getKey);
			}
		});
	}
	
	/**
	 * Gets the signature of this record.
	 * 
	 * @return The signature of the record.
	 */
	RecordSignature<K> getSignature();
	
	/**
	 * Gets the value of the field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field to get.
	 * @return The value of the field, or nothing when the record contains no fields are identified by the key.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> Maybe<V> get(final FieldKey<K, V> key)
	throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
		return CollectionUtils.filterAny(fields(), field -> field.getKey().equals(key)).map(field -> key.castValue(field.getValue()));
	}
	
	/**
	 * Gets the value of the optional field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field to get.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field, or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> V getOptional(final FieldKey<K, V> key, final V defaultValue)
	throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
		return get(key).get(defaultValue);
	}
	
	/**
	 * Gets the value of the optional field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field to get.
	 * @param defaultValue Default value of the field. May be <code>null</code>.
	 * @return The value of the field, or the given default value when no fields are identified by the given key. May be <code>null</code>.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> V getOptional(final FieldKey<K, V> key, final Thunk<? extends V> defaultValue)
	throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
		return get(key).get(defaultValue);
	}
	
	/**
	 * Gets the value of the mandatory field of this record identified by the given key.
	 * 
	 * @param <V> Type of the value of the field.
	 * @param key Key of the field.
	 * @return The value of the field. May be <code>null</code>.
	 * @throws MissingFieldException When no fields are identified by the given key in the record.
	 * @throws InvalidFieldException When the value of the field cannot be read.
	 * @throws NullFieldException When the value of the field is <code>null</code> and whereas the field is not nullable.
	 * @throws IncompatibleFieldException When the value of the field is not compatible with the type of the field.
	 * @since 2.0
	 */
	default <V> V getMandatory(final FieldKey<K, V> key)
	throws MissingFieldException, InvalidFieldException, NullFieldException, IncompatibleFieldException {
		final Maybe<V> value = get(key);
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new MissingFieldException("Missing field \"" + key + "\" in record " + this);
		}
	}
	
	/**
	 * Gets a view of the fields of this record.
	 * 
	 * @return An unmodiable collection of the fields.
	 * @since 2.0
	 */
	Collection<? extends Field<K, ?>> fields();
	
	/**
	 * Left folds over the fields of this record using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	default <S> S fold(final Function2<? super S, ? super Field<K, ?>, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(fields(), operator, initialState);
	}
	
	/**
	 * Tests whether any field of the this is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the fields.
	 * @return <code>true</code> when some field is accepted, <code>false</code> when all fields are rejected or when the record is empty.
	 * @since 2.0
	 */
	default boolean isAny(final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.isAny(fields(), filter);
	}
	
	/**
	 * Tests whether all fields of this record are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the fields.
	 * @return <code>true</code> when all fields are accepted or when the record is empty, <code>false</code> when some field is rejected.
	 * @since 2.0
	 */
	default boolean areAll(final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.areAll(fields(), filter);
	}
	
	/**
	 * Counts the fields of this record accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the fields.
	 * @return The number of accepted fields.
	 * @since 2.0
	 */
	default int count(final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.count(fields(), filter);
	}
	
	/**
	 * Filters this record using the given filter.
	 * <p>
	 * TODO: view
	 *
	 * @param filter Predicate to use to filter the fields.
	 * @return A view of the record containing the filtered fields.
	 * @since 2.0
	 */
	default Record<K> filter(final Predicate<? super Field<K, ?>> filter) {
		assert null != filter;
		
		final Record<K> self = this;
		final Collection<Field<K, ?>> fields = CollectionUtils.unmodifiable(new AbstractCollection<Field<K, ?>>() {
			@Override
			public int size() {
				return IterableUtils.count(self.fields(), filter);
			}
			
			@Override
			public boolean isEmpty() {
				return !IterableUtils.isAny(self.fields(), filter);
			}
			
			@Override
			public Iterator<Field<K, ?>> iterator() {
				return IteratorUtils.filter(self.fields().iterator(), filter);
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
				final Maybe<V> value = self.get(key);
				return value.isSome() && filter.evaluate(Fields.fromKeyAndValue(key, value.asSome().getValue()));
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return keys;
			}
			
			@Override
			public <V> Maybe<V> get(final FieldKey<K, V> key)
			throws InvalidFieldException, NullFieldException, IncompatibleFieldException {
				return self.get(key).filter(value -> filter.evaluate(Fields.fromKeyAndValue(key, value)));
			}
			
			@Override
			public Collection<? extends Field<K, ?>> fields() {
				return fields;
			}
		};
	}
	
	/**
	 * Filters this record using the given filter.
	 *
	 * @param <R> Type of the result record.
	 * @param filter Predicate to use to filter the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the filtered fields.
	 * @since 2.0
	 */
	default <R extends Record<K>> R filter(final Predicate<? super Field<K, ?>> filter, final RecordFactory<K, R> resultFactory) {
		final RecordBuilder<K, R> builder = resultFactory.newBuilder();
		for (final Field<K, ?> field : fields()) {
			if (filter.evaluate(field)) {
				builder.set(field);
			}
		}
		return builder.build();
	}
	
	/**
	 * Gets any field of this record accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the fields.
	 * @return The accepted field, or nothing when no fields are accepted by the filter.
	 * @since 2.0
	 */
	default Maybe<Field<K, ?>> filterAny(final Predicate<? super Field<K, ?>> filter) {
		return IterableUtils.filterAny(fields(), filter);
	}
	
	// TODO: add a map "view"
	
	/**
	 * Transforms this record using the given function.
	 *
	 * @param <TK> Type of the transformed field keys.
	 * @param <R> Type of the result record.
	 * @param function Function to use to transform the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the transformed fields.
	 * @throws DuplicateFieldException When sereral transformed field are conflicting.
	 * @since 2.0
	 */
	default <TK extends FieldKey<TK, ?>, R extends Record<TK>> R map(final Function<? super Field<K, ?>, ? extends Field<TK, ?>> function, final RecordFactory<TK, R> resultFactory)
	throws DuplicateFieldException {
		final RecordBuilder<TK, R> builder = resultFactory.newBuilder();
		for (final Field<K, ?> field : fields()) {
			builder.add(function.evaluate(field));
		}
		return builder.build();
	}
	
	// TODO: add an extract "view"
	
	/**
	 * Extracts a records from this record using the given extractor.
	 *
	 * @param <EK> Type of the extracted field keys.
	 * @param <R> Type of the result record.
	 * @param extractor Function to use to extract from the fields.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the extracted fields.
	 * @throws DuplicateFieldException When sereral extracted field are conflicting.
	 * @since 2.0
	 */
	default <EK extends FieldKey<EK, ?>, R extends Record<EK>> R extract(final Function<? super Field<K, ?>, ? extends Maybe<? extends Field<EK, ?>>> extractor, final RecordFactory<EK, R> resultFactory)
	throws DuplicateFieldException {
		final RecordBuilder<EK, R> builder = resultFactory.newBuilder();
		for (final Field<K, ?> field : fields()) {
			final Maybe<? extends Field<EK, ?>> extractedField = extractor.evaluate(field);
			if (extractedField.isSome()) {
				builder.add(extractedField.asSome().getValue());
			}
		}
		return builder.build();
	}
	
	/**
	 * Gets the element extracted from any field of this record using the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the fields.
	 * @return The extracted element, or nothing when no elements can be extracted from any field.
	 * @since 2.0
	 */
	default <EE> Maybe<EE> extractAny(final Function<? super Field<K, ?>, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractAny(fields(), extractor);
	}
	
	// TODO: extractAll
	
	/**
	 * Appends the given record to this record.
	 * <p>
	 * In case of conflict, the fields of this record have precedence over the fields of the given record.
	 * <p>
	 * TODO: view
	 * 
	 * @param record Record to append.
	 * @return A record containing the appended records.
	 * @since 2.0
	 */
	default Record<K> append(final Record<K> record) {
		assert null != record;
		
		// Compute the duplicate keys.
		final Set<? extends FieldKey<K, ?>> selfKeys = keys();
		final Set<FieldKey<K, ?>> duplicateKeys = CollectionUtils.filter(record.keys(), key -> selfKeys.contains(key), CollectionFactories.hashSet());
		
		// Build the record.
		final Record<K> self = this;
		return new BaseRecord<K>() {
			@Override
			public int size() {
				return self.size() + record.size() - duplicateKeys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return self.isEmpty() && record.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return self.contains(key) || record.contains(key);
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return new AbstractSet<FieldKey<K, ?>>() {
					@Override
					public int size() {
						return self.size() + record.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<FieldKey<K, ?>> iterator() {
						return IteratorUtils.append(self.keys().iterator(), IteratorUtils.filter(record.keys().iterator(), key -> !duplicateKeys.contains(key)));
					}
				};
			}
			
			@Override
			public <V> Maybe<V> get(final FieldKey<K, V> key)
			throws InvalidFieldException {
				final Maybe<V> value1 = self.get(key);
				if (value1.isSome()) {
					return value1;
				} else {
					return record.get(key);
				}
			}
			
			@Override
			public Collection<Field<K, ?>> fields() {
				return new AbstractCollection<Field<K, ?>>() {
					@Override
					public int size() {
						return self.size() + record.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<Field<K, ?>> iterator() {
						return IteratorUtils.append(self.fields().iterator(), IteratorUtils.filter(record.fields().iterator(), field -> !duplicateKeys.contains(field.getKey())));
					}
				};
			}
		};
	}
	
	/**
	 * Appends the given record to this record.
	 * <p>
	 * In case of conflict, the fields of this record have precedence over the fields of the given record.
	 * 
	 * @param <R> Type of the result record.
	 * @param record Record to append.
	 * @param resultFactory Factory of the result record.
	 * @return A record containing the union of the fields.
	 * @since 2.0
	 */
	default <R extends Record<K>> R append(final Record<K> record, final RecordFactory<K, R> resultFactory) {
		final RecordBuilder<K, R> builder = resultFactory.newBuilder();
		builder.setAll(this);
		builder.completeAll(record);
		return builder.build();
	}
	
	/**
	 * Executes the given procedure with each field of this record.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	default void foreach(final Procedure<? super Field<K, ?>> procedure) {
		IterableUtils.foreach(fields(), procedure);
	}
}
