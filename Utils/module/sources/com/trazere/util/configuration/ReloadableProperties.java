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
package com.trazere.util.configuration;

import com.trazere.util.io.Input;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * The {@link ReloadableProperties} class wraps properties which can be loaded and reloaded from a list of inputs.
 */
public class ReloadableProperties {
	/** Inputs providing the property files paired with flags indicating wether they are optional. */
	protected final List<Tuple2<Input, Boolean>> _inputs;
	
	/** Properties. */
	protected final Properties _properties;
	
	/**
	 * Instantiate a new wrapper with the given property file inputs and no defaut properties.
	 * <p>
	 * The properties are not loaded until the {@link #load()} method is called.
	 * 
	 * @param inputs Inputs provinding the property files paired with flags indicating wether they are optional.
	 */
	public ReloadableProperties(final List<Tuple2<Input, Boolean>> inputs) {
		this(inputs, Maybe.<Properties>none());
	}
	
	/**
	 * Instantiate a new wrapper with the given property file inputs and default properties.
	 * <p>
	 * The constructor does not load the properties.
	 * 
	 * @param inputs Inputs provinding the property files paired with flags indicating wether they are optional.
	 * @param defaultProperties Default properties.
	 */
	public ReloadableProperties(final List<Tuple2<Input, Boolean>> inputs, final Maybe<Properties> defaultProperties) {
		assert null != inputs;
		assert null != defaultProperties;
		
		// Initialization.
		_inputs = Collections.unmodifiableList(inputs);
		_properties = defaultProperties.isSome() ? new Properties(defaultProperties.asSome().getValue()) : new Properties();
	}
	
	/**
	 * Get the inputs providing the property files loaded by the receiver wrapper.
	 * 
	 * @return The providing the property files paired with flags indicating wether they are optional.
	 */
	public List<Tuple2<Input, Boolean>> getInputs() {
		return _inputs;
	}
	
	/**
	 * Load or reload the properties of the receiver wrapper.
	 * 
	 * @throws ConfigurationException When some property file is not optional and does not exist.
	 * @throws ConfigurationException When some property file cannot be loaded.
	 */
	public void load()
	throws ConfigurationException {
		_properties.clear();
		ConfigurationUtils.loadProperties(_properties, _inputs);
	}
	
	/**
	 * Get the properties of the receiver wrapper.
	 * 
	 * @return The properties.
	 */
	public Properties getProperties() {
		return _properties;
	}
	
	@Override
	public String toString() {
		return _properties.toString();
	}
}
