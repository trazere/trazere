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
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Tuple2;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * The <code>ConfigurationUtils</code> class provides various helpers regarding the configuration.
 */
public class ConfigurationUtils {
	/** Common delimiter of the property names. */
	public static final String PROPERTY_NAME_SEPARATOR = ".";
	
	/** Common delimiter of the property values. */
	public static final String PROPERTY_VALUE_DELIMITER = ",";
	
	/**
	 * Load and merge the property files provided by the given inputs.
	 * <p>
	 * Later inputs in the list are having priority over the previous ones.
	 * 
	 * @param inputs Input of the property files to load paired with flags indicating wether they are optional.
	 * @return The loaded properties.
	 * @throws ConfigurationException When some property file is not optional and does not exist.
	 * @throws ConfigurationException When some property file cannot be loaded.
	 */
	public static Properties loadProperties(final List<Tuple2<Input, Boolean>> inputs)
	throws ConfigurationException {
		return loadProperties(new Properties(), inputs);
	}
	
	/**
	 * Populate the given properties with the contents of the given property file inputs.
	 * <p>
	 * The loaded properties override the given properties. Later inputs in the list are having priority over the previous ones.
	 * 
	 * @param properties Properties to fill.
	 * @param inputs Inputs of the property files to load paired with flags indicating wether they are optional.
	 * @return The given properties.
	 * @throws ConfigurationException When some property file is not optional and does not exist.
	 * @throws ConfigurationException When some property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final List<Tuple2<Input, Boolean>> inputs)
	throws ConfigurationException {
		assert null != properties;
		assert null != inputs;
		
		// Load the properties.
		for (final Tuple2<Input, Boolean> file : inputs) {
			loadProperties(properties, file.getFirst(), file.getSecond().booleanValue());
		}
		return properties;
	}
	
	/**
	 * Populate the given properties with the content of the given property file input.
	 * <p>
	 * The loaded properties override the given properties.
	 * 
	 * @param properties Properties to populate.
	 * @param input Input of the property file to load.
	 * @param optional Flag indicating wether the property file is optional.
	 * @return The given properties.
	 * @throws ConfigurationException When the property file is not optional and does not exist.
	 * @throws ConfigurationException When the property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final Input input, final boolean optional)
	throws ConfigurationException {
		assert null != properties;
		assert null != input;
		
		try {
			if (!optional || input.exists()) {
				// Load the properties.
				final InputStream stream = input.open();
				try {
					properties.load(stream);
				} finally {
					stream.close();
				}
			}
			
			return properties;
		} catch (final IOException exception) {
			throw new ConfigurationException("Failed loading properties from " + input, exception);
		}
	}
	
	/**
	 * Compute the name of a configuration property using the given delimiter and parts.
	 * 
	 * @param delimiter Delimiter.
	 * @param parts Parts.
	 * @return The property name.
	 */
	public static String computePropertyName(final String delimiter, final String... parts) {
		assert null != delimiter;
		assert null != parts;
		
		// Compute.
		final StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (final String part : parts) {
			if (!first) {
				builder.append(delimiter);
			}
			builder.append(part);
			first = false;
		}
		return builder.toString();
	}
	
	/**
	 * Get the property with the given name from the given properties. The the given default value is returned if the property does not exist.
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
		return null != value ? value : defaultValue;
	}
	
	/**
	 * Get the property with the given name from the given properties. The given default value is returned if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist. May be <code>null</code>.
	 * @return The value.
	 */
	public static String getProperty(final ReloadableProperties properties, final String name, final String defaultValue) {
		assert null != properties;
		
		// Get the property.
		return getProperty(properties.getProperties(), name, defaultValue);
	}
	
	/**
	 * Get the boolean property with the given name from the given properties. The given default value is returned if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist. May be <code>null</code>.
	 * @return The value.
	 */
	public static boolean getBooleanProperty(final Properties properties, final String name, final boolean defaultValue) {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		if (null != value) {
			return Boolean.parseBoolean(value);
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * Get the boolean property with the given name from the given properties. The the given default value is returned if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value.
	 */
	public static boolean getBooleanProperty(final ReloadableProperties properties, final String name, final boolean defaultValue) {
		assert null != properties;
		
		// Get the property.
		return getBooleanProperty(properties.getProperties(), name, defaultValue);
	}
	
	/**
	 * Get the integer property with the given name from the given properties. The given default value is returned if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value.
	 * @throws ConfigurationException When the value of the property is invalid.
	 */
	public static int getIntProperty(final Properties properties, final String name, final int defaultValue)
	throws ConfigurationException {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		if (null != value) {
			try {
				return Integer.parseInt(value);
			} catch (final NumberFormatException exception) {
				throw new ConfigurationException("Invalid int value " + value + " for property " + name);
			}
		} else {
			return defaultValue;
		}
	}
	
	/**
	 * Get the integer property with the given name from the given properties. The the given default value is returned if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value.
	 * @throws ConfigurationException When the value of the property is invalid.
	 */
	public static int getIntProperty(final ReloadableProperties properties, final String name, final int defaultValue)
	throws ConfigurationException {
		assert null != properties;
		
		// Get the property.
		return getIntProperty(properties.getProperties(), name, defaultValue);
	}
	
	/**
	 * Get the mandatory property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the property does not exist.
	 */
	public static String getMandatoryProperty(final Properties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		if (null != value) {
			return value;
		} else {
			throw new ConfigurationException("Missing property " + name);
		}
	}
	
	/**
	 * Get the mandatory property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the property does not exist.
	 */
	public static String getMandatoryProperty(final ReloadableProperties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		
		// Get.
		return getMandatoryProperty(properties.getProperties(), name);
	}
	
	/**
	 * Get the mandatory boolean property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the property does not exist.
	 */
	public static boolean getMandatoryBooleanProperty(final Properties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		if (null != value) {
			return Boolean.parseBoolean(value);
		} else {
			throw new ConfigurationException("Missing property " + name);
		}
	}
	
	/**
	 * Get the mandatory boolean property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the property does not exist.
	 * @throws ConfigurationException When the value of the property is invalid.
	 */
	public static boolean getMandatoryBooleanProperty(final ReloadableProperties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		
		// Get.
		return getMandatoryBooleanProperty(properties.getProperties(), name);
	}
	
	/**
	 * Get the mandatory integer property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the property does not exist.
	 * @throws ConfigurationException When the value of the property is invalid.
	 */
	public static int getMandatoryIntProperty(final Properties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		assert null != name;
		
		// Get the property.
		final String value = properties.getProperty(name);
		if (null != value) {
			try {
				return Integer.parseInt(value);
			} catch (final NumberFormatException exception) {
				throw new ConfigurationException("Invalid int value " + value + " for property " + name);
			}
		} else {
			throw new ConfigurationException("Missing property " + name);
		}
	}
	
	/**
	 * Get the mandatory integer property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties Properties to read.
	 * @param name Name of the property to read.
	 * @return The value.
	 * @throws ConfigurationException When the property does not exist.
	 * @throws ConfigurationException When the value of the property is invalid.
	 */
	public static int getMandatoryIntProperty(final ReloadableProperties properties, final String name)
	throws ConfigurationException {
		assert null != properties;
		
		// Get.
		return getMandatoryIntProperty(properties.getProperties(), name);
	}
	
	/**
	 * Split the given property value according to the given delimiter.
	 * <p>
	 * An empty property results in no components. The components are trimmed and the empty components are included in the results.
	 * 
	 * @param delimiter Delimiter string.
	 * @param value Value to split.
	 * @return The value components.
	 */
	public static List<String> splitPropertyValue(final String delimiter, final String value) {
		if (value.length() > 0) {
			return TextUtils.split(value, delimiter, true, false, new ArrayList<String>());
		} else {
			return Collections.emptyList();
		}
	}
	
	private ConfigurationUtils() {
		// Prevent instantiation.
	}
}
