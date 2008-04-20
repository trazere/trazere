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

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * The {@link ReloadableProperties} class wraps properties which can be loaded and reloaded from a list of files.
 */
public class ReloadableProperties {
	/** Property files to load paired with flags indicating wether they are optional. */
	protected final List<Tuple2<File, Boolean>> _files;
	
	/** Properties. */
	protected final Properties _properties;
	
	/**
	 * Instantiate a new wrapper with the given property files.
	 * <p>
	 * The properties are not loaded until the {@link #load()} method is called.
	 * 
	 * @param files Files containing the properties.
	 */
	public ReloadableProperties(final List<Tuple2<File, Boolean>> files) {
		this(files, Maybe.<Properties>none());
	}
	
	/**
	 * Instantiate a new wrapper with the given property files and default properties.
	 * <p>
	 * The constructor does not load the properties.
	 * 
	 * @param files Files containing the properties.
	 * @param defaultProperties Default properties.
	 */
	public ReloadableProperties(final List<Tuple2<File, Boolean>> files, final Maybe<Properties> defaultProperties) {
		assert null != files;
		assert null != defaultProperties;
		
		// Initialization.
		_files = Collections.unmodifiableList(files);
		_properties = defaultProperties.isSome() ? new Properties(defaultProperties.asSome().getValue()) : new Properties();
	}
	
	/**
	 * Get the property files loaded by the receiver wrapper.
	 * 
	 * @return The property files paired with flags indicating wether they are optional.
	 */
	public List<Tuple2<File, Boolean>> getFiles() {
		return _files;
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
		ConfigurationUtils.loadProperties(_properties, _files);
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
