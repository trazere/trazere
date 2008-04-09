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
 * The {@link ParameterSet} interface represents unmutable sets of parameters. Each parameter is identified by an unique name within the set.
 * 
 * @param <T> Type of the parameter values.
 */
public interface ParameterSet<T> {
	/**
	 * Test wether the receiver parameter set is empty or not.
	 * 
	 * @return <code>true</code> when the parameter set is empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * Test wether the receiver parameter set contains a value for the given name.
	 * 
	 * @param name Name of the parameter.
	 * @return <code>true</code> if the set contains a parameter for the given name, <code>false</code> otherwise.
	 */
	public boolean contains(final String name);
	
	/**
	 * Get the names of the parameters of the receiver set.
	 * 
	 * @return The names.
	 */
	public Set<String> getNames();
	
	/**
	 * Get the value of the parameter of the receiver set identified by the given name.
	 * 
	 * @param name Name of the parameter.
	 * @return The value of the parameter. May be <code>null</code>.
	 * @throws MissingParameterException When the receiver set contains no value for the given name.
	 * @throws ParameterException When the parameter cannot be got.
	 */
	public T get(final String name)
	throws ParameterException;
	
	/**
	 * Get the value of the parameter of the receiver set identified by the given name.
	 * 
	 * @param name Name of the parameter.
	 * @param defaultValue Default value of the parameter. May be <code>null</code>.
	 * @return The value of the parameter, or the given default value if the set contains no value for the given name. May be <code>null</code>.
	 * @throws ParameterException When the parameter cannot be got.
	 */
	public T get(final String name, final T defaultValue)
	throws ParameterException;
	
	/**
	 * Get the value of the parameter of the receiver set identified by the given name according to the given type.
	 * 
	 * @param <C> Type of the value.
	 * @param name Name of the parameter.
	 * @param type Class of the value.
	 * @return The value of the parameter. May be <code>null</code>.
	 * @throws MissingParameterException When the receiver set contains no value for the given name.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the given type.
	 * @throws ParameterException When the parameter cannot be got.
	 */
	public <C extends T> C getTyped(final String name, final Class<C> type)
	throws ParameterException;
	
	/**
	 * Get the value of the parameter of the receiver set identified by the given name according to the given type.
	 * 
	 * @param <C> Type of the value.
	 * @param name Name of the parameter.
	 * @param defaultValue Default value of the parameter. May be <code>null</code>.
	 * @param type Class of the value.
	 * @return The value of the parameter, or the given default value if the set contains no value for the given name. May be <code>null</code>.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the given type.
	 * @throws ParameterException When the parameter cannot be got.
	 */
	public <C extends T> C getTyped(final String name, final Class<C> type, final C defaultValue)
	throws ParameterException;
	
	/**
	 * Get the value of the parameter of the receiver set according to the given signature.
	 * 
	 * @param <C> Type of the value.
	 * @param signature Signature of the parameter.
	 * @return The value of the parameter. May be <code>null</code>.
	 * @throws MissingParameterException When the receiver set contains no value for the name of the given signature.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the type of the given signature.
	 * @throws ParameterException When the parameter cannot be got.
	 */
	public <C extends T> C getTyped(final ParameterSignature<C> signature)
	throws ParameterException;
	
	/**
	 * Get the value of the parameter of the receiver set according to the given signature.
	 * 
	 * @param <C> Type of the value.
	 * @param signature Signature of the parameter.
	 * @param defaultValue Default value of the parameter. May be <code>null</code>.
	 * @return The value of the parameter, or the given default value if the set contains no value for the given name. May be <code>null</code>.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the type of the given signature.
	 * @throws ParameterException When the parameter cannot be got.
	 */
	public <C extends T> C getTyped(final ParameterSignature<C> signature, final C defaultValue)
	throws ParameterException;
	
	/**
	 * Get a view of the parameters of the receiver set as a map.
	 * 
	 * @return An unmodiable map of the values of the parameters identified by their names.
	 * @throws ParameterException When the view cannot be built.
	 */
	public Map<String, T> asMap()
	throws ParameterException;
}
