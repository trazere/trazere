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

import com.trazere.util.type.Tuple2;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * The <code>ConfigurationUtils</code> class provides various helpers regarding the configuration.
 */
public class ConfigurationUtils {
	/**
	 * Load and merge the property files at the given paths.
	 * <p>
	 * Later files in the list are having priority over the previous ones.
	 * 
	 * @param files Property files to load paired with flags indicating wether they are optional.
	 * @return The loaded properties.
	 * @throws ConfigurationException When some property file is not optional and does not exist.
	 * @throws ConfigurationException When some property file cannot be loaded.
	 */
	public static Properties loadProperties(final List<Tuple2<File, Boolean>> files)
	throws ConfigurationException {
		return loadProperties(new Properties(), files);
	}
	
	/**
	 * Populate the given properties with the contents of the given property files.
	 * <p>
	 * The loaded properties override the given properties. Later files in the list are having priority over the previous ones.
	 * 
	 * @param properties Properties to fill.
	 * @param files Property files to load paired with flags indicating wether they are optional.
	 * @return The given properties.
	 * @throws ConfigurationException When some property file is not optional and does not exist.
	 * @throws ConfigurationException When some property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final List<Tuple2<File, Boolean>> files)
	throws ConfigurationException {
		assert null != properties;
		assert null != files;
		
		// Load the properties.
		for (final Tuple2<File, Boolean> file : files) {
			loadProperties(properties, file.getFirst(), file.getSecond().booleanValue());
		}
		return properties;
	}
	
	/**
	 * Populate the given properties with the content of the given property file.
	 * <p>
	 * The loaded properties override the given properties.
	 * 
	 * @param properties Properties to populate.
	 * @param file Property file to load.
	 * @param optional Flag indicating wether the property file is optional.
	 * @return The given properties.
	 * @throws ConfigurationException When the property file is not optional and does not exist.
	 * @throws ConfigurationException When the property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final File file, final boolean optional)
	throws ConfigurationException {
		assert null != properties;
		assert null != file;
		
		if (!optional || file.exists()) {
			// Load the properties.
			try {
				final InputStream stream = new FileInputStream(file);
				try {
					properties.load(stream);
				} finally {
					stream.close();
				}
			} catch (final IOException exception) {
				throw new ConfigurationException("Failed loading properties", exception);
			}
		}
		return properties;
	}
	
	/**
	 * Get the property with the given name from the given properties. The read value is trimmed, and the given default value is returned if the property does
	 * not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist. May be <code>null</code>.
	 * @return The value.
	 */
	public static String getProperty(final Properties properties, final String name, final String defaultValue) {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		return null != value ? value.trim() : defaultValue;
	}
	
	/**
	 * Get the property with the given name from the given properties. The read value is trimmed, and the given default value is returned if the property does
	 * not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist. May be <code>null</code>.
	 * @return The value.
	 */
	public static String getProperty(final ReloadableProperties properties, final String name, final String defaultValue) {
		assert null != properties;
		
		// Get.
		return getProperty(properties.getProperties(), name, defaultValue);
	}
	
	/**
	 * Get the mandatory property with the given name from the given properties. The read value is trimmed, and an exception is raised if the property does not
	 * exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the requested property is not found.
	 */
	public static String getMandatoryProperty(final Properties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		if (null != value) {
			return value.trim();
		} else {
			throw new ConfigurationException("Missing property " + name);
		}
	}
	
	/**
	 * Get the mandatory property with the given name from the given properties. The read value is trimmed, and an exception is raised if the property does not
	 * exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the requested property is not found.
	 */
	public static String getMandatoryProperty(final ReloadableProperties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		
		// Get.
		return getMandatoryProperty(properties.getProperties(), name);
	}
	
	private ConfigurationUtils() {
		// Prevent instantiation.
	}
}
