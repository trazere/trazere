/*
 *  Copyright 2006-2013 Julien Dufour
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

import com.trazere.util.function.Predicate1;
import java.util.Collection;
import java.util.Set;

/**
 * The {@link RecordSignatureBuilder} interface defines builders of {@link RecordSignature record signatures}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @see RecordSignature
 */
public interface RecordSignatureBuilder<K, V, R extends RecordSignature<K, V>> {
	/**
	 * Add the field signature corresponding to the given key and type to the receiver record signature builder.
	 * <p>
	 * The added field must not be signed in the receiver record signature builder.
	 * 
	 * @param key The key of the field.
	 * @param type The type of the values.
	 * @param nullable The flag indicating whether the values can be <code>null</code> or not.
	 * @throws DuplicateFieldException When the field is already signed.
	 */
	public void add(final K key, final Class<? extends V> type, final boolean nullable)
	throws DuplicateFieldException;
	
	/**
	 * Add the given field signature to the receiver record signature builder.
	 * <p>
	 * The added field must not be signed in the receiver record signature builder.
	 * 
	 * @param field Field signature to add.
	 * @throws DuplicateFieldException When the field is already signed.
	 */
	public void add(final FieldSignature<K, ? extends V> field)
	throws DuplicateFieldException;
	
	/**
	 * Add the given field signatures to the receiver record signature builder.
	 * <p>
	 * The added fields must not be signed in the receiver record signature builder.
	 * 
	 * @param fields Field signatures to add.
	 * @throws DuplicateFieldException When some field is already signed.
	 */
	public void addAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws DuplicateFieldException;
	
	/**
	 * Add the field signatures of the given record signature to the receiver record signature builder.
	 * <p>
	 * The added fields must not be signed in the receiver record signature builder.
	 * 
	 * @param signature Record signature containing the field signatures to add.
	 * @throws InvalidFieldException When the some field of the given signature record cannot be read.
	 * @throws DuplicateFieldException When some field is already signed.
	 */
	public void addAll(final RecordSignature<K, ? extends V> signature)
	throws InvalidFieldException, DuplicateFieldException;
	
	/**
	 * Unify the signature of the field corresponding to the given key and type within the receiver record signature builder.
	 * <p>
	 * The unified field must either not be signed or be signed with a compatible type in the receiver record signature builder.
	 * 
	 * @param key The key of the field.
	 * @param type The type of the values.
	 * @param nullable The flag indicating whether the values can be <code>null</code> or not.
	 * @throws IncompatibleFieldException When the given and current signature of the field are not compatible.
	 */
	public void unify(final K key, final Class<? extends V> type, final boolean nullable)
	throws IncompatibleFieldException;
	
	/**
	 * Unify the given field signature within the receiver record signature builder.
	 * <p>
	 * The unified field must either not be signed or be signed with a compatible type in the receiver record signature builder.
	 * 
	 * @param field Field signature to unify.
	 * @throws IncompatibleFieldException When the given and current signature of the field are not compatible.
	 */
	public void unify(final FieldSignature<K, ? extends V> field)
	throws IncompatibleFieldException;
	
	/**
	 * Unify the given field signatures within the receiver record signature builder.
	 * <p>
	 * The unified fields must either not be signed or be signed with compatible types in the receiver record signature builder.
	 * 
	 * @param fields Field signatures to unify.
	 * @throws IncompatibleFieldException When the given and current signature of some field are not compatible.
	 */
	public void unifyAll(final Collection<? extends FieldSignature<K, ? extends V>> fields)
	throws IncompatibleFieldException;
	
	/**
	 * Unify the field signatures of the given record signature within the receiver record signature builder.
	 * <p>
	 * The unified fields must either not be signed or be signed with compatible types in the receiver record signature builder.
	 * 
	 * @param signature Record signature to unify.
	 * @throws InvalidFieldException When the some field of the given signature record cannot be read.
	 * @throws IncompatibleFieldException When the given and current signature of some field are not compatible.
	 */
	public void unifyAll(final RecordSignature<K, ? extends V> signature)
	throws InvalidFieldException, IncompatibleFieldException;
	
	// TODO: kill, replace by abstract record filtering
	/**
	 * Unify the field signatures of the given record signature accepted by the given filter within the receiver record signature builder.
	 * <p>
	 * The unified fields must either not be signed or be signed with compatible types in the receiver record signature builder.
	 * 
	 * @param <FX> Type of the filter exception.
	 * @param filter The filter.
	 * @param signature Record signature to unify.
	 * @throws InvalidFieldException When the some field of the given signature record cannot be read.
	 * @throws IncompatibleFieldException When the given and current signature of some field are not compatible.
	 * @throws FX When some filter evaluation fails.
	 */
	public <FX extends Exception> void unifyAll(final Predicate1<? super FieldSignature<K, ? extends V>, FX> filter, final RecordSignature<K, ? extends V> signature)
	throws IncompatibleFieldException, FX, InvalidFieldException, FX;
	
	/**
	 * Test whether the receiver record signature builder is empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test whether the field identified by the given key is signed in the receiver record signature builder.
	 * 
	 * @param key Key of the field.
	 * @return <code>true</code> when the field is signed, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the keys identifying the fields signed in the receiver record signature builder.
	 * 
	 * @return An unmodiable set of the keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Remove the signature of the field identified by the given key from the receiver record signature builder.
	 * <p>
	 * The field identified by the given key must be signed in the receiver record signature builder.
	 * 
	 * @param key Key of the field signature to remove.
	 * @throws MissingFieldException When the field identified by the given key is not signed.
	 */
	public void remove(final K key)
	throws MissingFieldException;
	
	/**
	 * Remove all field signatures from the receiver record signature builder.
	 */
	public void clear();
	
	/**
	 * Build a new record signature populated with the field signatures of the receiver record signature builder.
	 * 
	 * @return The built record signature.
	 * @throws RecordException When the record signature cannot be built.
	 */
	public R build()
	throws RecordException;
}
