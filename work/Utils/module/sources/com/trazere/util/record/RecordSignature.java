/*
 *  Copyright 2006-2010 Julien Dufour
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

import com.trazere.util.type.Maybe;
import java.util.Map;
import java.util.Set;

// TODO: add some getMaybe

/**
 * The {@link RecordSignature} interface defines signatures of {@link Record records}.
 * <p>
 * The signature is caracterized by a set of field signatures.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public interface RecordSignature<K, V> {
	/**
	 * Test whether the receiver record signature is empty or not.
	 * 
	 * @return <code>true</code> when empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test whether the field identified by the given key is signed in the receiver record signature or not.
	 * 
	 * @param key Key of the field to test.
	 * @return <code>true</code> when the field is signed, <code>false</code> otherwise.
	 */
	public boolean contains(final K key);
	
	/**
	 * Get the keys identifying the fields signed in the receiver record signature.
	 * 
	 * @return An unmodiable set of the keys.
	 */
	public Set<K> getKeys();
	
	/**
	 * Get the signature of the field identified by the given key of the receiver record signature.
	 * 
	 * @param key Key of the field.
	 * @return The signature of the field.
	 * @throws MissingFieldException When the field identified by the given key is not signed.
	 * @throws RecordException When the field signature cannot be got.
	 */
	public FieldSignature<K, ? extends V> get(final K key)
	throws RecordException;
	
	/**
	 * Get the signature of the field identified by the given key of the receiver record signature.
	 * 
	 * @param key Key of the field.
	 * @return The signature of the field.
	 * @throws MissingFieldException When the field identified by the given key is not signed.
	 * @throws RecordException When the field signature cannot be got.
	 */
	public Maybe<FieldSignature<K, ? extends V>> getMaybe(final K key)
	throws RecordException;
	
	/**
	 * Get a view of the receiver record signature as a map.
	 * 
	 * @return An unmodiable map of the field signatures identified by their keys.
	 * @throws RecordException When the view cannot be built.
	 */
	public Map<K, FieldSignature<K, ? extends V>> asMap()
	throws RecordException;
	
	// TODO: should return a reason in case of failure
	/**
	 * Check whether the receiver record signature accepts the given record.
	 * <p>
	 * Records are accepted when they contain compatible fields for every field signature of the receiver record signature.
	 * 
	 * @param record Record to test.
	 * @return <code>true</code> when the record is accepted, <code>false</code> otherwise.
	 * @throws RecordException When the record cannot be tested.
	 */
	public boolean accepts(final Record<? super K, ? extends V> record)
	throws RecordException;
	
	// TODO: should return a reason in case of failure
	/**
	 * Check whether the receiver record signature accepts the given record signature.
	 * <p>
	 * Signatures are accepted when they contain compatible field signatures for every field signature of the receiver record signature.
	 * 
	 * @param signature Signature to test.
	 * @return <code>true</code> when the signature is accepted, <code>false</code> otherwise.
	 * @throws RecordException When the signature cannot be tested.
	 */
	public boolean accepts(final RecordSignature<? super K, ? extends V> signature)
	throws RecordException;
}
