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
 * The {@link ParameterSetBuilder} interface defines builders of {@link ParameterSet parameter sets}.
 * 
 * @param <T> Type of the parameter values.
 * @param <S> Type of the parameter sets.
 * @see ParameterSet
 */
public interface ParameterSetBuilder<T, S extends ParameterSet<T>> {
	/**
	 * Add a parameter identified by the given name with the given value to the receiver builder.
	 * <p>
	 * The receiver builder not must contain a parameter identified by the given name.
	 * 
	 * @param name Name of the parameter to add.
	 * @param value Value of the parameter to add. May be <code>null</code>.
	 * @throws DuplicateParameterException When the builder contains a parameter identified by the given name.
	 * @throws ParameterException When the parameter cannot by added.
	 */
	public void add(final String name, final T value)
	throws ParameterException;
	
	/**
	 * Add parameters corresponding to the parameters of the given set to the receiver builder.
	 * <p>
	 * The receiver builder must not contain parameters identified by names of the parameters of the given set.
	 * 
	 * @param parameters Parameters to add.
	 * @throws DuplicateParameterException When the builder contains parameters identified by names of the parameters of the given set.
	 * @throws ParameterException When some parameter cannot by added.
	 */
	public void addAll(final ParameterSet<? extends T> parameters)
	throws ParameterException;
	
	/**
	 * Add the given parameters to the receiver builder.
	 * <p>
	 * The receiver builder must not contain parameters identified by names of the given parameters.
	 * 
	 * @param parameters Values of the parameters to add identified by their names.
	 * @throws DuplicateParameterException When the builder contains parameters identified by the given names.
	 * @throws ParameterException When some parameter cannot by added.
	 */
	public void addAll(final Map<String, ? extends T> parameters)
	throws ParameterException;
	
	/**
	 * Test wether the receiver builder is empty or not.
	 * 
	 * @return <code>true</code> if the receiver builder is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test wether the receiver builder contains a parameter identified by the given name.
	 * 
	 * @param name Name of the parameter.
	 * @return <code>true</code> if the builder contains a parameter for the the given name, <code>false</code> otherwise.
	 */
	public boolean contains(final String name);
	
	/**
	 * Get the names of the parameters of the receiver builder.
	 * 
	 * @return The names.
	 */
	public Set<String> getNames();
	
	/**
	 * Remove the parameter identified by the given name from the receiver builder.
	 * <p>
	 * The receiver builder must contain a parameter identified by the given name.
	 * 
	 * @param name Name of the parameter to remove.
	 * @throws MissingParameterException When the receiver builder contains no parameters with the given name.
	 * @throws ParameterException When the parameter cannot by removed.
	 */
	public void remove(final String name)
	throws ParameterException;
	
	/**
	 * Remove all paramaters from the receiver builder.
	 * 
	 * @throws ParameterException When the builder cannot be cleared.
	 */
	public void clear()
	throws ParameterException;
	
	/**
	 * Fill the given builder with the fields of the receiver builder.
	 * <p>
	 * The given builder must not contain parameters identified by names of parameters of the receiver builder.
	 * 
	 * @param <B> Type of the builder to fill.
	 * @param builder Builder to fill.
	 * @return The given builder.
	 * @throws DuplicateParameterException When the given builder contain parameters identified by names of parameters of the given builder.
	 * @throws ParameterException When the given builder cannot be filled.
	 */
	public <B extends ParameterSetBuilder<? super T, ?>> B populate(final B builder)
	throws ParameterException;
	
	/**
	 * Fill the given builder with the fields of the receiver builder identified by the given names.
	 * <p>
	 * The receiver builder must contain parameters for all given keys and the given builder must not contain parameters identified by given names.
	 * 
	 * @param <B> Type of the builder to fill.
	 * @param builder Builder to fill.
	 * @param names Names of the parameters to copy.
	 * @return The given builder.
	 * @throws DuplicateParameterException When the given builder contain parameters identified by keys of parameters of the given builder.
	 * @throws ParameterException When the given builder cannot be filled.
	 */
	public <B extends ParameterSetBuilder<? super T, ?>> B populate(final B builder, final Set<String> names)
	throws ParameterException;
	
	/**
	 * Build a new parameter set filled with the parameters of the receiver builder.
	 * 
	 * @return The built parameter set.
	 * @throws ParameterException When the parameter set cannot be built.
	 */
	public S build()
	throws ParameterException;
}
