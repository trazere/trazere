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

import com.trazere.util.Assert;
import java.util.Properties;

/**
 * The <code>ReloadableProperties</code> class represent reloadble persistent sets of properties stored in files.
 * <p>
 * TODO: local feature
 */
public class ReloadableProperties {
	/** Path of the file containing the properties. */
	protected String _path = null;
	
	/** Flag indicating wether the properties may be overridden in a <em>local</em> file. */
	protected boolean _local = false;
	
	/** Properties. May be <code>null</code> when no properties has ever been loaded. */
	protected final Properties _properties;
	
	/**
	 * Instantiate new reloadable properties with no path and no default properties.
	 */
	public ReloadableProperties() {
		this(null, false, null);
	}
	
	/**
	 * Instantiate new reloadable properties with the given path and no default properties.
	 * <p>
	 * The properties are no loaded by the constructor.
	 * 
	 * @param path Path of the file containing the properties.
	 * @param local Flag indicating wether the properties may be overridden in a <em>local</em> file.
	 */
	public ReloadableProperties(final String path, final boolean local) {
		this(path, local, null);
	}
	
	/**
	 * Instantiate new reloadable properties with no path and the given default properties.
	 * 
	 * @param defaults Default properties.
	 */
	public ReloadableProperties(final Properties defaults) {
		this(null, false, defaults);
	}
	
	/**
	 * Instantiate new reloadable properties with the given path and default properties.
	 * <p>
	 * The properties are no loaded by the constructor.
	 * 
	 * @param path Path of the file containing the properties.
	 * @param local Flag indicating wether the properties may be overridden in a <em>local</em> file.
	 * @param defaults Default properties.
	 */
	public ReloadableProperties(final String path, final boolean local, final Properties defaults) {
		// Initialization.
		_path = path;
		_local = local;
		_properties = null == defaults ? new Properties() : new Properties(defaults);
	}
	
	/**
	 * Get the path of the receiver reloadable properties.
	 * 
	 * @return The path. May be <code>null</code>.
	 */
	public String getPath() {
		return _path;
	}
	
	/**
	 * Get the flag indicating wether the properties may be overridden in a <em>local</em> file.
	 * 
	 * @return The flag.
	 */
	public boolean getLocal() {
		return _local;
	}
	
	/**
	 * Load the properties at the given path.
	 * 
	 * @param path Path of the properties.
	 * @param local Flag indicating wether the properties may be overridden in a <em>local</em> file.
	 * @throws ConfigurationException
	 */
	public synchronized void load(final String path, final boolean local)
	throws ConfigurationException {
		Assert.notNull(path);
		
		// Set the path.
		_path = path;
		_local = local;
		
		// Load the properties.
		_properties.clear();
		ConfigurationUtils.loadConfiguration(_properties, _path, _local);
	}
	
	/**
	 * Reload the properties using the current path.
	 * <p>
	 * This method should not be called when no path has been set.
	 * 
	 * @throws ConfigurationException
	 * @throws IllegalStateException When no path has been set.
	 */
	public synchronized void reload()
	throws ConfigurationException {
		if (null != _path) {
			// Reload the properties.
			_properties.clear();
			ConfigurationUtils.loadConfiguration(_properties, _path, _local);
		} else {
			throw new IllegalStateException("No path");
		}
	}
	
	/**
	 * Get the loaded or default properties.
	 * 
	 * @return The properties. May be <code>null</code> when no default properties have been provided and when no properties have ever been loaded.
	 */
	public Properties getProperties() {
		return _properties;
	}
	
	@Override
	public String toString() {
		return _properties.toString();
	}
}
