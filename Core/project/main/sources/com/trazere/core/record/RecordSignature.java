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
import com.trazere.core.util.Result;
import com.trazere.core.util.Unit;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link RecordSignature} interface defines signatures of {@link Record records}.
 * <p>
 * A record signature is the set of keys that identify the fields that the record contains.
 * 
 * @param <K> Type of the field keys.
 * @see FieldKey
 * @since 2.0
 */
public interface RecordSignature<K extends FieldKey<K, ?>> {
	// Keys.
	
	/**
	 * Gets the size of this record signature.
	 * <p>
	 * The size of a record signature corresponds to the number of field keys it contains.
	 * 
	 * @return The size of the record signature.
	 * @since 2.0
	 */
	default int size() {
		return keys().size();
	}
	
	/**
	 * Indicates whether this record signature is empty or not.
	 * <p>
	 * A record signature is empty when it contains no field keys.
	 * 
	 * @return <code>true</code> when the record signature is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean isEmpty() {
		return 0 == size();
	}
	
	/**
	 * Indicates whether this record signature contains the given key field or not.
	 * 
	 * @param key Field key to look for.
	 * @return <code>true</code> when the record signature contains the field key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean contains(final FieldKey<K, ?> key) {
		return keys().contains(key);
	}
	
	/**
	 * Gets a view of the field keys composing this record signature.
	 * 
	 * @return An unmodiable set of the field keys.
	 * @since 2.0
	 */
	Set<? extends FieldKey<K, ?>> keys();
	
	/**
	 * Left folds over the field keys of this record signature using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	default <S> S fold(final Function2<? super S, ? super FieldKey<K, ?>, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(keys(), operator, initialState);
	}
	
	/**
	 * Tests whether any field key of this record signature is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the field keys.
	 * @return <code>true</code> when some field key is accepted by the filter, <code>false</code> when all fields are rejected.
	 * @since 2.0
	 */
	default boolean isAny(final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.isAny(keys(), filter);
	}
	
	/**
	 * Tests whether all field keys of this record signature are accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the field keys.
	 * @return <code>true</code> when all field keys are accepted by the filter, <code>false</code> when some field is rejected.
	 * @since 2.0
	 */
	default boolean areAll(final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.areAll(keys(), filter);
	}
	
	/**
	 * Counts the field keys of this record signature accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the field keys.
	 * @return The number of accepted fields.
	 * @since 2.0
	 */
	default int count(final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.count(keys(), filter);
	}
	
	/**
	 * Filters this record signature using the given filter.
	 * <p>
	 * TODO: view
	 * 
	 * @param filter Predicate to use to filter the field keys.
	 * @return A record signature containing the accepted field keys.
	 * @since 2.0
	 */
	default RecordSignature<K> filter(final Predicate<? super FieldKey<K, ?>> filter) {
		assert null != filter;
		
		final RecordSignature<K> self = this;
		final Set<FieldKey<K, ?>> keys = SetUtils.unmodifiable(new AbstractSet<FieldKey<K, ?>>() {
			@Override
			public int size() {
				return IterableUtils.count(self.keys(), filter);
			}
			
			@Override
			public boolean isEmpty() {
				return !IterableUtils.isAny(self.keys(), filter);
			}
			
			@Override
			public Iterator<FieldKey<K, ?>> iterator() {
				return IteratorUtils.filter(self.keys().iterator(), filter);
			}
		});
		
		return new BaseRecordSignature<K>() {
			@Override
			public int size() {
				return keys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return keys.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return self.contains(key) && filter.evaluate(key);
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return keys;
			}
		};
	}
	
	// TODO: filter with factory
	
	/**
	 * Gets the first field key of this record signature accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the field keys.
	 * @return The first accepted field key.
	 * @since 2.0
	 */
	default Maybe<FieldKey<K, ?>> filterFirst(final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.filterFirst(keys(), filter);
	}
	
	// TODO: map ?
	// TODO: extract ?
	
	/**
	 * Gets the first element extracted from the field keys of this record signature by the given extractor.
	 * 
	 * @param <EE> Type of the extracted elements.
	 * @param extractor Function to use to extract from the field keys.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	default <EE> Maybe<EE> extractFirst(final Function<? super FieldKey<K, ?>, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(keys(), extractor);
	}
	
	/**
	 * Appends the given record signature to this record signature.
	 * <p>
	 * In case of conflict, the field keys of this record signature have precedence over the field keys of the given record signature.
	 * <p>
	 * TODO: view
	 * 
	 * @param signature Record signature to append.
	 * @return A record signature containing the union of the field keys.
	 * @since 2.0
	 */
	default RecordSignature<K> append(final RecordSignature<K> signature) {
		assert null != signature;
		
		// Compute the duplicate keys.
		final Set<? extends FieldKey<K, ?>> keys1 = keys();
		final Set<FieldKey<K, ?>> duplicateKeys = CollectionUtils.filter(signature.keys(), key -> keys1.contains(key), CollectionFactories.hashSet());
		
		// Build the record signature.
		final RecordSignature<K> self = this;
		return new BaseRecordSignature<K>() {
			@Override
			public int size() {
				return self.size() + signature.size() - duplicateKeys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return self.isEmpty() && signature.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return self.contains(key) || signature.contains(key);
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return new AbstractSet<FieldKey<K, ?>>() {
					@Override
					public int size() {
						return self.size() + signature.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<FieldKey<K, ?>> iterator() {
						return IteratorUtils.append(self.keys().iterator(), IteratorUtils.filter(signature.keys().iterator(), key -> !duplicateKeys.contains(key)));
					}
				};
			}
		};
	}
	
	// TODO: append with factory
	
	/**
	 * Executes the given procedure with each field key of this record signature.
	 * 
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	default void foreach(final Procedure<? super FieldKey<K, ?>> procedure) {
		IterableUtils.foreach(keys(), procedure);
	}
	
	// Signature.
	
	/**
	 * Tests whether records with the given signauture are compatible with this signature.
	 * <p>
	 * The compliance is defined as follow :
	 * <ul>
	 * <li>each key of this signature must belong to the given record signature,
	 * <li>each key of this signature must be assignable from its couterpart key in the given signature.
	 * </ul>
	 *
	 * @param signature Record signature compatibility is to be tested.
	 * @return <code>true</code> when the record signature is compliant, <code>false</code> otherwise.
	 * @see FieldKey#checkAssignableFrom(FieldKey)
	 */
	boolean isAssignableFrom(RecordSignature<K> signature);
	
	/**
	 * Tests whether records with the given signauture are compatible with this signature.
	 * <p>
	 * The compliance is defined as follow :
	 * <ul>
	 * <li>each key of this signature must belong to the given record signature,
	 * <li>each key of this signature must be assignable from its couterpart key in the given signature.
	 * </ul>
	 *
	 * @param signature Record signature compatibility is to be tested.
	 * @return A success when the record signature is compliant, a failure otherwise.
	 * @see FieldKey#checkAssignableFrom(FieldKey)
	 */
	Result<Unit> checkAssignableFrom(RecordSignature<K> signature);
	
	/**
	 * Tests whether the values of the given record are compatible this signature.
	 * <p>
	 * A value is compatible when its type and nullity are compliant with the constraints of the key.
	 *
	 * @param record Record to test.
	 * @return A success when the values are compatible with the signature, a failure otherwise.
	 * @see FieldKey#checkValue(Object)
	 * @since 2.0
	 */
	Result<Unit> checkValues(Record<K> record);
}
