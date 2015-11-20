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
 * The {@link RecordSignatureBuilder} interface defines builders of {@link RecordSignature record signatures}.
 * 
 * @param <K> Type of the field keys.
 * @param <R> Type of the record signatures.
 * @see RecordSignature
 * @since 2.0
 */
public interface RecordSignatureBuilder<K extends FieldKey<K, ?>, R extends RecordSignature<K>>
extends Factory<R> {
	/**
	 * Gets the current size of the record signature being built by this builder.
	 * <p>
	 * The size of a record signature corresponds to the number of field keys it contains.
	 * 
	 * @return The size of the record signature.
	 * @since 2.0
	 */
	int size();
	
	/**
	 * Indicates whether the record signature being built by this builder is empty of not.
	 * <p>
	 * A record signature is empth when it contains no field keys.
	 * 
	 * @return <code>true</code> when the record signature is empty, <code>false</code> otherwise.
	 * @since 2.0
	 */
	default boolean isEmpty() {
		return 0 == size();
	}
	
	/**
	 * Indicates whether the record signature being built by this builder contains the given field key or not.
	 * 
	 * @param key Field key to look for.
	 * @return <code>true</code> when the record signature contains the key, <code>false</code> otherwise.
	 * @since 2.0
	 */
	boolean contains(final FieldKey<? extends K, ?> key);
	
	/**
	 * Adds the given field key to the record signature being built by this builder.
	 * <p>
	 * The record signature must not already contain the given field key.
	 * 
	 * @param key Field key to add.
	 * @throws DuplicateFieldException When the record signature already contains the field key.
	 * @since 2.0
	 */
	default void add(final FieldKey<? extends K, ?> key)
	throws DuplicateFieldException {
		if (!contains(key)) {
			set(key);
		} else {
			throw new DuplicateFieldException("Conflicting field key \"" + key + "\"");
		}
	}
	
	/**
	 * Adds the given field keys to the record signature beign built by this builder.
	 * <p>
	 * The record signature must not already contain any given field key.
	 * 
	 * @param keys Field keys to add.
	 * @throws DuplicateFieldException When the record signature already contains some field key.
	 * @since 2.0
	 */
	default void addAll(final Iterable<? extends FieldKey<? extends K, ?>> keys)
	throws DuplicateFieldException {
		for (final FieldKey<? extends K, ?> key : keys) {
			add(key);
		}
	}
	
	/**
	 * Adds the field keys of the given record signature to the record signature being built by this builder.
	 * <p>
	 * The record signature must not already contain any field key of the given record signature.
	 * 
	 * @param signature Record signature containing the field keys to add.
	 * @throws DuplicateFieldException When the record signature already contains some field key.
	 * @since 2.0
	 */
	default void addAll(final RecordSignature<? extends K> signature)
	throws DuplicateFieldException {
		addAll(signature.keys());
	}
	
	/**
	 * Completes the record signature being built by this builder with the given field key.
	 * <p>
	 * The key is added only when the record signature does not already contain it.
	 * 
	 * @param key Field key to complete the record signature with.
	 * @since 2.0
	 */
	default void complete(final FieldKey<? extends K, ?> key) {
		if (!contains(key)) {
			set(key);
		}
	}
	
	/**
	 * Completes the record signature being built by this builder with the given field keys.
	 * <p>
	 * The keys are added only when the record signature does not already contain them.
	 * 
	 * @param keys Field keys to complete the record signature with.
	 * @since 2.0
	 */
	default void completeAll(final Iterable<? extends FieldKey<? extends K, ?>> keys) {
		for (final FieldKey<? extends K, ?> key : keys) {
			complete(key);
		}
	}
	
	/**
	 * Completes the record signature being built by this builder with the field keys of the given record signature.
	 * <p>
	 * The keys are added only when the record signature does not already contain them.
	 * 
	 * @param signature Record signature containing the field keys to complete the record signature with.
	 * @since 2.0
	 */
	default void completeAll(final RecordSignature<? extends K> signature) {
		completeAll(signature.keys());
	}
	
	/**
	 * Unifies the given field key into the record signature being built by this builder.
	 * <p>
	 * The record signature must either not contain the given field key, or contain a compatible field key.
	 * 
	 * @param key Field key to unify.
	 * @param unifier Field key unifier to use.
	 * @throws IncompatibleFieldException When the field key is not compatible with the record signature.
	 * @see FieldKeyUnifier
	 * @since 2.0
	 */
	void unify(FieldKey<? extends K, ?> key, FieldKeyUnifier<K> unifier)
	throws IncompatibleFieldException;
	
	/**
	 * Unifies the given field keys into the record signature being built by this builder.
	 * <p>
	 * The record signature must either not contain the given field keys, or contain compatible field keys.
	 * 
	 * @param keys Field keys to unify.
	 * @param unifier Field key unifier to use.
	 * @throws IncompatibleFieldException When some field key is not compatible with the record signature.
	 * @see FieldKeyUnifier
	 * @since 2.0
	 */
	default void unifyAll(final Iterable<? extends FieldKey<? extends K, ?>> keys, final FieldKeyUnifier<K> unifier)
	throws IncompatibleFieldException {
		for (final FieldKey<? extends K, ?> key : keys) {
			unify(key, unifier);
		}
	}
	
	/**
	 * Unifies the field keys of the given record signature into the record signature being built by this builder.
	 * <p>
	 * The record signature must either not contain the given field keys, or contain compatible field keys.
	 * 
	 * @param signature Record signature containing the field keys to unify.
	 * @param unifier Field key unifier to use.
	 * @throws IncompatibleFieldException When some field key is not compatible with the record signature.
	 * @see FieldKeyUnifier
	 * @since 2.0
	 */
	default void unifyAll(final RecordSignature<? extends K> signature, final FieldKeyUnifier<K> unifier)
	throws IncompatibleFieldException {
		unifyAll(signature.keys(), unifier);
	}
	
	/**
	 * Sets the given field key in the record signature being built by this builder.
	 * <p>
	 * The record signature may already contain the given field key.
	 * 
	 * @param key Field key to set.
	 * @since 2.0
	 */
	void set(FieldKey<? extends K, ?> key);
	
	/**
	 * Sets the given field keys in the record signature beign built by this builder.
	 * <p>
	 * The record signature may already contain any given field key.
	 * 
	 * @param keys Field keys to set.
	 * @since 2.0
	 */
	default void setAll(final Iterable<? extends FieldKey<? extends K, ?>> keys) {
		for (final FieldKey<? extends K, ?> key : keys) {
			set(key);
		}
	}
	
	/**
	 * Sets the field keys of the given record signature to the record signature being built by this builder.
	 * <p>
	 * The record signature may already contain any field key of the given record signature.
	 * 
	 * @param signature Record signature containing the field keys to set.
	 * @since 2.0
	 */
	default void setAll(final RecordSignature<? extends K> signature) {
		setAll(signature.keys());
	}
	
	/**
	 * Removes the given field key from the record signature being built by this builder.
	 *
	 * @param key Field key to remove.
	 * @since 2.0
	 */
	void remove(FieldKey<? extends K, ?> key);
	
	/**
	 * Removes the given field keys from the record signature being built by this builder.
	 *
	 * @param keys Field keys to remove.
	 * @since 2.0
	 */
	default void removeAll(final Iterable<? extends FieldKey<? extends K, ?>> keys) {
		for (final FieldKey<? extends K, ?> key : keys) {
			remove(key);
		}
	}
	
	/**
	 * Removes the field keys of the given record signature from the record signature being built by this builder.
	 *
	 * @param signature Record signature containing the field keys to remove.
	 * @since 2.0
	 */
	default void removeAll(final RecordSignature<? extends K> signature) {
		removeAll(signature.keys());
	}
	
	/**
	 * Removes all field keys from the record signature being built by this builder.
	 * 
	 * @since 2.0
	 */
	void clear();
	
	/**
	 * Builds the record signature.
	 *
	 * @return The built record signature.
	 * @since 2.0
	 */
	@Override
	R build();
}
