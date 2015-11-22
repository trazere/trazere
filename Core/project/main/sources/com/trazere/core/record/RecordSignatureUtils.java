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
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The {@link RecordSignatureUtils} class provides various utilities regarding {@link RecordSignature record signatures}.
 * 
 * @see RecordSignature
 * @since 2.0
 */
public class RecordSignatureUtils {
	/**
	 * Executes the given procedure with each field key of the given record signature.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys over which to iterate.
	 * @param procedure Procedure to execute.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> void foreach(final RecordSignature<K> signature, final Procedure<? super FieldKey<K, ?>> procedure) {
		IterableUtils.foreach(signature.keys(), procedure);
	}
	
	/**
	 * Left folds over the field keys of the given record signature using the given binary operator and initial state.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <S> Type of the state.
	 * @param signature Record signature containing the field keys over which to fold.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, S> S fold(final RecordSignature<K> signature, final Function2<? super S, ? super FieldKey<K, ?>, ? extends S> operator, final S initialState) {
		return IterableUtils.fold(signature.keys(), operator, initialState);
	}
	
	/**
	 * Gets the first field key of the given record signature accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to filter.
	 * @param filter Predicate to use to filter the field keys.
	 * @return The first accepted field key.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> Maybe<FieldKey<K, ?>> first(final RecordSignature<K> signature, final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.first(signature.keys(), filter);
	}
	
	/**
	 * Gets the first element extracted from the field keys of the given record signature by the given extractor.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <EE> Type of the extracted elements.
	 * @param signature Record signature containing the field keys from which to extract.
	 * @param extractor Function to use to extract from the field keys.
	 * @return The first extracted element.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, EE> Maybe<? extends EE> extractFirst(final RecordSignature<K> signature, final Function<? super FieldKey<K, ?>, ? extends Maybe<? extends EE>> extractor) {
		return IterableUtils.extractFirst(signature.keys(), extractor);
	}
	
	/**
	 * Tests whether any field key of the given record signature is accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to test.
	 * @param filter Predicate to use to filter the field keys.
	 * @return <code>true</code> when some field key is accepted by the filter, <code>false</code> when all fields are rejected.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> boolean isAny(final RecordSignature<K> signature, final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.isAny(signature.keys(), filter);
	}
	
	/**
	 * Tests whether all field keys of the given record signature are accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to test.
	 * @param filter Predicate to use to filter the field keys.
	 * @return <code>true</code> when all field keys are accepted by the filter, <code>false</code> when some field is rejected.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> boolean areAll(final RecordSignature<K> signature, final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.areAll(signature.keys(), filter);
	}
	
	/**
	 * Counts the field keys of the given record signature accepted by the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature containing the field keys to count.
	 * @param filter Predicate to use to filter the field keys.
	 * @return The number of accepted fields.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> int count(final RecordSignature<K> signature, final Predicate<? super FieldKey<K, ?>> filter) {
		return IterableUtils.count(signature.keys(), filter);
	}
	
	// TODO: rename to append ?
	/**
	 * Builds the union of the given record signatures.
	 * <p>
	 * In case of conflict, the field keys of the first record signature have precedence over the field keys of the second record signature.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature1 First record signature.
	 * @param signature2 Second record signature.
	 * @return A record signature containing the union of the field keys.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> union(final RecordSignature<K> signature1, final RecordSignature<K> signature2) {
		final Set<? extends FieldKey<K, ?>> keys1 = signature1.keys();
		final Set<FieldKey<K, ?>> duplicateKeys = CollectionUtils.filter(signature2.keys(), key -> keys1.contains(key), CollectionFactories.hashSet());
		return new BaseRecordSignature<K>() {
			@Override
			public int size() {
				return signature1.size() + signature2.size() - duplicateKeys.size();
			}
			
			@Override
			public boolean isEmpty() {
				return signature1.isEmpty() && signature2.isEmpty();
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return signature1.contains(key) || signature2.contains(key);
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return new AbstractSet<FieldKey<K, ?>>() {
					@Override
					public int size() {
						return signature1.size() + signature2.size() - duplicateKeys.size();
					}
					
					@Override
					public Iterator<FieldKey<K, ?>> iterator() {
						return IteratorUtils.append(signature1.keys().iterator(), IteratorUtils.filter(signature2.keys().iterator(), key -> !duplicateKeys.contains(key)));
					}
				};
			}
		};
	}
	
	/**
	 * Filters the field keys of the given record signature using the given filter.
	 * 
	 * @param <K> Type of the field keys.
	 * @param signature Record signature to filter.
	 * @param filter Predicate to use to filter the field keys.
	 * @return A record signature containing the accepted field keys.
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>> RecordSignature<K> filter(final RecordSignature<K> signature, final Predicate<? super FieldKey<K, ?>> filter) {
		assert null != signature;
		assert null != filter;
		
		final Set<FieldKey<K, ?>> keys = SetUtils.unmodifiable(new AbstractSet<FieldKey<K, ?>>() {
			@Override
			public int size() {
				return IterableUtils.count(signature.keys(), filter);
			}
			
			@Override
			public Iterator<FieldKey<K, ?>> iterator() {
				return IteratorUtils.filter(signature.keys().iterator(), filter);
			}
		});
		
		return new BaseRecordSignature<K>() {
			@Override
			public int size() {
				return IterableUtils.count(signature.keys(), filter);
			}
			
			@Override
			public boolean isEmpty() {
				return !IterableUtils.isAny(signature.keys(), filter);
			}
			
			@Override
			public boolean contains(final FieldKey<K, ?> key) {
				return signature.contains(key) && filter.evaluate(key);
			}
			
			@Override
			public Set<? extends FieldKey<K, ?>> keys() {
				return keys;
			}
		};
	}
	
	// TODO: map ?
	// TODO: extract ?
	
	//	/**
	//	 * Typechecks the requirements of the given parametrable against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param parametrable The parametrable.
	//	 * @param signature The signature.
	//	 * @throws InvalidFieldException When some requirement field cannot be read.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K, V> void typeCheck(final Parametrable<K, V> parametrable, final RecordSignature<? super K, ? extends V> signature)
	//	throws InvalidFieldException, MissingFieldException, IncompatibleFieldException {
	//		assert null != parametrable;
	//
	//		typeCheck(parametrable.getRequirements(), signature);
	//	}
	//
	//	/**
	//	 * Typechecks the given requirements against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param requirements The requierements.
	//	 * @param signature The signature.
	//	 * @throws InvalidFieldException When some requirement field cannot be read.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K, V> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature)
	//	throws InvalidFieldException, MissingFieldException, IncompatibleFieldException {
	//		typeCheck(requirements, signature, Predicates.<K, InternalException>all());
	//	}
	//
	//	/**
	//	 * Typechecks the given requirements against the given signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param <V> Type of the values.
	//	 * @param <KX> Type of the key filter exceptions.
	//	 * @param requirements The requierements.
	//	 * @param signature The signature.
	//	 * @param keys The filter of the requirements to typecheck.
	//	 * @throws KX When some key filter evaluation fails.
	//	 * @throws InvalidFieldException When some requirement field cannot be read.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K, V, KX extends Exception> void typeCheck(final RecordSignature<K, V> requirements, final RecordSignature<? super K, ? extends V> signature, final Predicate1<? super K, KX> keys)
	//	throws KX, InvalidFieldException, MissingFieldException, IncompatibleFieldException {
	//		assert null != requirements;
	//		assert null != signature;
	//		assert null != keys;
	//
	//		for (final K key : requirements.getKeys()) {
	//			if (keys.evaluate(key)) {
	//				final FieldSignature<K, ? extends V> requirement;
	//				try {
	//					requirement = requirements.get(key);
	//				} catch (final MissingFieldException exception) {
	//					throw new InternalException(exception);
	//				}
	//				typeCheck(requirement, signature);
	//			}
	//		}
	//	}
	
	//	/**
	//	 * Type checks the given requirement against the given record signature.
	//	 *
	//	 * @param <K> Type of the keys.
	//	 * @param requirement The requierement.
	//	 * @param signature The signature.
	//	 * @throws MissingFieldException When some signature field is missing but required.
	//	 * @throws InvalidFieldException When some signature field cannot be read.
	//	 * @throws IncompatibleFieldException When some signature field is incompatible with the requirement.
	//	 */
	//	public static <K extends FieldKey<K, ?>> void typeCheck(final FieldKey<? extends K, ?> requirement, final RecordSignature<? super K> signature)
	//	throws MissingFieldException, InvalidFieldException, IncompatibleFieldException {
	//		final Maybe<? extends FieldSignature<? super K, ?>> maybeFieldSignature = signature.getMaybe(key);
	//		if (maybeFieldSignature.isNone()) {
	//			throw new MissingFieldException("Missing field \"" + key + "\" in signature " + signature);
	//		}
	//		final FieldSignature<? super K, ?> fieldSignature = maybeFieldSignature.asSome().getValue();
	//		if (requirement.isSubSignature(fieldSignature).isFailure()) {
	//			throw new IncompatibleFieldException("Incompatible field \"" + key + "\" with requirement " + requirement + " in signature " + signature);
	//		}
	//	}
	
	private RecordSignatureUtils() {
		// Prevent instantiation.
	}
}
