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

import java.util.Map;
import java.util.Set;

/**
 * The {@link ParameterSetSignature} interface defines signatures of {@link ParameterSet parameter sets}.
 * 
 * @param <T> Type of the parameter values.
 */
public interface ParameterSetSignature<T> {
	/**
	 * Test wether the receiver parameter set signature is empty or not.
	 * 
	 * @return <code>true</code> when the signature is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test wether the parameter identified by the given name is signed in the receiver parameter set signature.
	 * 
	 * @param name Name of the parameter.
	 * @return <code>true</code> if the parameter is signed, <code>false</code> otherwise.
	 */
	public boolean contains(final String name);
	
	/**
	 * Get the names of the parameters signed in the receiver parameter set signature.
	 * 
	 * @return The parameter names.
	 */
	public Set<String> getNames();
	
	/**
	 * Get the signature of the parameter identified by the given name of the receiver parameter set signature.
	 * 
	 * @param name Name of the parameter.
	 * @return The signature of the parameter.
	 * @throws MissingParameterException When the receiver set contains no signatures for the parameter identified by the given name.
	 * @throws ParameterException When the signature of the parameter cannot be got.
	 */
	public ParameterSignature<? extends T> get(final String name)
	throws ParameterException;
	
	/**
	 * Get the signature of the parameter identified by the given name of the receiver parameter set signature.
	 * 
	 * @param name Name of the parameter.
	 * @param defaultSignature Default signature of the parameter. May be <code>null</code>.
	 * @return The signature of the parameter, or the default signature when the parameter set signature contains no signatures for the parameter identified by
	 *         the given name.
	 * @throws ParameterException When the signature of the parameter cannot be got.
	 */
	public ParameterSignature<? extends T> get(final String name, final ParameterSignature<? extends T> defaultSignature)
	throws ParameterException;
	
	/**
	 * Get a view of the receiver parameter set signature as a map.
	 * 
	 * @return An unmodiable map of the signatures of the parameters identified by their names.
	 * @throws ParameterException When the view cannot be built.
	 */
	public Map<String, ParameterSignature<? extends T>> asMap()
	throws ParameterException;
	
	/**
	 * Check wether the given parameter set is accepted by the receiver signature.
	 * <p>
	 * Parameter sets are accepted when then contain a compatible parameter for every parameter signed in the receiver parameter set signature.
	 * 
	 * @param parameters Parameter set to check.
	 * @return <code>true</code> when the parameter set is accepted, <code>false</code> otherwise.
	 * @throws ParameterException
	 */
	public boolean accepts(final ParameterSet<? extends T> parameters)
	throws ParameterException;
}
