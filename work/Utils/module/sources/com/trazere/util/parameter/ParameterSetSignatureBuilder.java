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
package com.trazere.util.parameter;

import java.util.Collection;
import java.util.Set;

/**
 * The {@link ParameterSetSignatureBuilder} interface defines builders of {@link ParameterSetSignature parameter set signatures}.
 * 
 * @param <T> Type of the parameter values.
 */
public interface ParameterSetSignatureBuilder<T> {
	/**
	 * Add the given parameter signature to the receiver builder.
	 * <p>
	 * The corresponding parameter must not be signed in the receiver builder.
	 * 
	 * @param signature Parameter signature to add.
	 * @throws DuplicateParameterException When the parameter is already signed in the builder.
	 * @throws ParameterException When the parameter signature cannot by added.
	 */
	public void add(final ParameterSignature<? extends T> signature)
	throws ParameterException;
	
	/**
	 * Add the given parameter signatures to the receiver builder.
	 * <p>
	 * The corresponding parameters must not be signed in the receiver builder.
	 * 
	 * @param signatures Parameter signatures to add.
	 * @throws DuplicateParameterException When some parameter is already signed in the builder.
	 * @throws ParameterException When the parameter signatures cannot by added.
	 */
	public void addAll(final Collection<? extends ParameterSignature<? extends T>> signatures)
	throws ParameterException;
	
	/**
	 * Add the parameter signatures of the given parameter signature set to the receiver builder.
	 * <p>
	 * The parameters signed in the given parameter set signature must not be signed in the receiver builder.
	 * 
	 * @param signature Parameter set signature containing the parameter signatures to add.
	 * @throws DuplicateParameterException When some parameter is already signed in the builder.
	 * @throws ParameterException When the parameter signatures cannot by added.
	 */
	public void addAll(final ParameterSetSignature<? extends T> signature)
	throws ParameterException;
	
	/**
	 * Unify the given parameter signature within the receiver builder.
	 * <p>
	 * The corresponding parameter must either not be signed or be signed with a compatible type in the receiver builder.
	 * 
	 * @param signature Parameter signature to unify.
	 * @throws IncompatibleParameterException When the given and current signature of the parameter are not compatible.
	 * @throws ParameterException When the parameter signature cannot be unified.
	 */
	public void unify(final ParameterSignature<? extends T> signature)
	throws ParameterException;
	
	/**
	 * Unify the given parameter signatures within the receiver builder.
	 * <p>
	 * The corresponding parameters must either not be signed or be signed with compatible types in the receiver builder.
	 * 
	 * @param signatures Parameter signatures to unify.
	 * @throws IncompatibleParameterException When the given and current signature of some parameter are not compatible.
	 * @throws ParameterException When the parameter signatures cannot be unified.
	 */
	public void unifyAll(final Collection<? extends ParameterSignature<? extends T>> signatures)
	throws ParameterException;
	
	/**
	 * Unify the parameter signatures of the given parameter signature set within the receiver builder.
	 * <p>
	 * The parameters signed in the given builder must either not be signed or be signed with compatible types in the receiver builder.
	 * 
	 * @param signature Parameter set signature to unify.
	 * @throws IncompatibleParameterException When the given and current signature of some parameter are not compatible.
	 * @throws ParameterException When the parameter signatures cannot be unified.
	 */
	public void unifyAll(final ParameterSetSignature<? extends T> signature)
	throws ParameterException;
	
	/**
	 * Test wether the receiver builder is empty or not.
	 * 
	 * @return <code>true</code> if the receiver builder is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test wether the parameter identified by the given name is signed in the receiver builder.
	 * 
	 * @param name Name of the parameter.
	 * @return <code>true</code> if the parameter is signed, <code>false</code> otherwise.
	 */
	public boolean contains(final String name);
	
	/**
	 * Get the names of the parameters signed in the receiver builder.
	 * 
	 * @return The parameter names.
	 */
	public Set<String> getNames();
	
	/**
	 * Remove the signature of the parameter identified by the given name from the receiver builder.
	 * <p>
	 * The parameter identified by the given name must be signed in the receiver builder.
	 * 
	 * @param name Name of the parameter signature to remove.
	 * @throws MissingParameterException When the parameter identified by the given name is not signed in the builder.
	 * @throws ParameterException When the parameter signature cannot be removed.
	 */
	public void remove(final String name)
	throws ParameterException;
	
	/**
	 * Remove all parameter signatures from the receiver builder.
	 * 
	 * @throws ParameterException When the builder cannot be cleared.
	 */
	public void clear()
	throws ParameterException;
	
	/**
	 * Build a new parameter set signature filled with the parameter signatures of the receiver builder.
	 * 
	 * @return The built parameter set signature.
	 * @throws ParameterException When the parameter set signature cannot be built.
	 */
	public ParameterSetSignature<T> build()
	throws ParameterException;
}
