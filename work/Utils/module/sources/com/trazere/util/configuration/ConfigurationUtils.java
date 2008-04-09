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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The <code>ConfigurationUtils</code> class provides various helpers regarding the configuration.
 */
public class ConfigurationUtils {
	/**
	 * Load the configuration file located at the given path, and its possible local version.
	 * <p>
	 * Local property file is after the property file with the <tt>.local</tt> extension.
	 * 
	 * @param path Path of the property file.
	 * @param local Flag indicating wether the local property file should be loaded and merged within the properties.
	 * @return The loaded properties.
	 * @throws ConfigurationException
	 */
	public static Properties loadConfiguration(final String path, final boolean local)
	throws ConfigurationException {
		Assert.notNull(path);
		
		// Load the properties.
		final Properties properties = new Properties();
		loadConfiguration(properties, path, local);
		
		return properties;
	}
	
	/**
	 * Load the configuration file located at the given path, and its possible local version.
	 * <p>
	 * Local property file is after the property file with the <tt>.local</tt> extension.
	 * 
	 * @param properties Properties to fill.
	 * @param path Path of the property file.
	 * @param local Flag indicating wether the local property file should be loaded and merged within the properties.
	 * @throws ConfigurationException
	 */
	public static void loadConfiguration(final Properties properties, final String path, final boolean local)
	throws ConfigurationException {
		Assert.notNull(properties);
		Assert.notNull(path);
		
		// Load the properties.
		final File file = new File(path);
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
		
		// Load the local properties if needed.
		if (local) {
			final File localFile = new File(path + ".local");
			try {
				if (localFile.exists()) {
					final InputStream localStream = new FileInputStream(localFile);
					try {
						properties.load(localStream);
					} finally {
						localStream.close();
					}
				}
			} catch (final IOException exception) {
				throw new ConfigurationException("Failed loading properties", exception);
			}
		}
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
		Assert.notNull(properties);
		Assert.notNull(name);
		
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
		Assert.notNull(properties);
		
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
		Assert.notNull(properties);
		Assert.notNull(name);
		
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
		Assert.notNull(properties);
		
		// Get.
		return getMandatoryProperty(properties.getProperties(), name);
	}
	
	private ConfigurationUtils() {
		// Prevent instantiation.
	}
}
