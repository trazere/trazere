/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.util.properties;

import com.trazere.util.function.Function0;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Functions;
import com.trazere.util.io.Input;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * The {@link PropertiesUtils} class provides various utilities regarding the manipulation of properties.
 */
public class PropertiesUtils {
	/**
	 * Loads and merges the property files provided by the given inputs.
	 * <p>
	 * Later inputs in the list are having priority over the previous ones.
	 * 
	 * @param inputs Input of the property files to load paired with flags indicating whether they are optional.
	 * @return The loaded properties.
	 * @throws IOException When the property file cannot be loaded.
	 */
	public static Properties loadProperties(final Iterable<? extends Tuple2<? extends Input, Boolean>> inputs)
	throws IOException {
		return loadProperties(new Properties(), inputs);
	}
	
	/**
	 * Populates the given properties with the contents of the given property file inputs.
	 * <p>
	 * The loaded properties override the given properties. Later inputs in the list are having priority over the previous ones.
	 * 
	 * @param properties Properties to fill.
	 * @param inputs Inputs of the property files to load paired with flags indicating whether they are optional.
	 * @return The given properties.
	 * @throws IOException When the property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final Iterable<? extends Tuple2<? extends Input, Boolean>> inputs)
	throws IOException {
		assert null != properties;
		assert null != inputs;
		
		for (final Tuple2<? extends Input, Boolean> file : inputs) {
			loadProperties(properties, file.getFirst(), file.getSecond().booleanValue());
		}
		return properties;
	}
	
	/**
	 * Populates the given properties with the content of the given property file input.
	 * <p>
	 * The loaded properties override the given properties.
	 * 
	 * @param properties Properties to populate.
	 * @param input Input of the property file to load.
	 * @param optional Flag indicating whether the property file is optional.
	 * @return The given properties.
	 * @throws IOException When the property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final Input input, final boolean optional)
	throws IOException {
		assert null != properties;
		assert null != input;
		
		if (!optional || input.exists()) {
			final InputStream stream = input.open();
			try {
				properties.load(stream);
			} finally {
				stream.close();
			}
		}
		return properties;
	}
	
	/**
	 * Computes the name of a configuration property using the given delimiter and parts.
	 * 
	 * @param delimiter Delimiter.
	 * @param parts Parts.
	 * @return The property name.
	 */
	public static String computePropertyName(final String delimiter, final String... parts) {
		assert null != delimiter;
		assert null != parts;
		
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
	 * Gets the value of the property with the given name from the given properties.
	 * 
	 * @param <T> Type of the value.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param reader The value reader function.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static <T> Maybe<T> getProperty(final Properties properties, final String name, final Function1<? super String, ? extends T, ? extends InvalidPropertyException> reader)
	throws InvalidPropertyException {
		assert null != properties;
		assert null != name;
		assert null != reader;
		
		return Maybe.fromValue(properties.getProperty(name)).map(reader);
	}
	
	/**
	 * Gets the value of the string property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<String> getStringProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, Functions.<String, InvalidPropertyException>identity());
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<Boolean> getBooleanProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_BOOLEAN_FUNCTION);
	}
	
	/**
	 * Gets the value of the integer property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<Integer> getIntegerProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_INTEGER_FUNCTION);
	}
	
	/**
	 * Gets the value of the long property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<Long> getLongProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_LONG_FUNCTION);
	}
	
	/**
	 * Gets the value of the float property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<Float> getFloatProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_FLOAT_FUNCTION);
	}
	
	/**
	 * Gets the value of the double property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<Double> getDoubleProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_DOUBLE_FUNCTION);
	}
	
	/**
	 * Gets the value of the file property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<File> getFileProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_FILE_FUNCTION);
	}
	
	/**
	 * Gets the value of the URI property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<URI> getUriProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_URI_FUNCTION);
	}
	
	/**
	 * Gets the value of the URL property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static Maybe<URL> getUrlProperty(final Properties properties, final String name)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_URL_FUNCTION);
	}
	
	/**
	 * Gets the value of the property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param reader The value reader function.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static <T> T getProperty(final Properties properties, final String name, final Function1<? super String, ? extends T, ? extends InvalidPropertyException> reader, final Function0<? extends T, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		assert null != defaultValue;
		
		final Maybe<T> value = getProperty(properties, name, reader);
		return value.isSome() ? value.asSome().getValue() : defaultValue.evaluate();
	}
	
	/**
	 * Gets the value of the string property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static String getStringProperty(final Properties properties, final String name, final String defaultValue)
	throws InvalidPropertyException {
		return getStringProperty(properties, name, Functions.<String, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the string property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static String getStringProperty(final Properties properties, final String name, final Function0<? extends String, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, Functions.<String, InvalidPropertyException>identity(), defaultValue);
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static boolean getBooleanProperty(final Properties properties, final String name, final boolean defaultValue)
	throws InvalidPropertyException {
		return getBooleanProperty(properties, name, Functions.<Boolean, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static boolean getBooleanProperty(final Properties properties, final String name, final Function0<? extends Boolean, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_BOOLEAN_FUNCTION, defaultValue).booleanValue();
	}
	
	/**
	 * Gets the value of the integer property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static int getIntegerProperty(final Properties properties, final String name, final int defaultValue)
	throws InvalidPropertyException {
		return getIntegerProperty(properties, name, Functions.<Integer, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the integer property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static int getIntegerProperty(final Properties properties, final String name, final Function0<? extends Integer, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_INTEGER_FUNCTION, defaultValue).intValue();
	}
	
	/**
	 * Gets the value of the long property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static long getLongProperty(final Properties properties, final String name, final long defaultValue)
	throws InvalidPropertyException {
		return getLongProperty(properties, name, Functions.<Long, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the long property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static long getLongProperty(final Properties properties, final String name, final Function0<? extends Long, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_LONG_FUNCTION, defaultValue).longValue();
	}
	
	/**
	 * Gets the value of the float property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static float getFloatProperty(final Properties properties, final String name, final float defaultValue)
	throws InvalidPropertyException {
		return getFloatProperty(properties, name, Functions.<Float, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the float property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static float getFloatProperty(final Properties properties, final String name, final Function0<? extends Float, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_FLOAT_FUNCTION, defaultValue).floatValue();
	}
	
	/**
	 * Gets the value of the double property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static double getDoubleProperty(final Properties properties, final String name, final double defaultValue)
	throws InvalidPropertyException {
		return getDoubleProperty(properties, name, Functions.<Double, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the double property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static double getDoubleProperty(final Properties properties, final String name, final Function0<? extends Double, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_DOUBLE_FUNCTION, defaultValue).doubleValue();
	}
	
	/**
	 * Gets the value of the file property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static File getFileProperty(final Properties properties, final String name, final File defaultValue)
	throws InvalidPropertyException {
		assert null != defaultValue;
		
		return getFileProperty(properties, name, Functions.<File, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the file property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static File getFileProperty(final Properties properties, final String name, final Function0<? extends File, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_FILE_FUNCTION, defaultValue);
	}
	
	/**
	 * Gets the value of the URI property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static URI getUriProperty(final Properties properties, final String name, final URI defaultValue)
	throws InvalidPropertyException {
		assert null != defaultValue;
		
		return getUriProperty(properties, name, Functions.<URI, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the URI property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static URI getUriProperty(final Properties properties, final String name, final Function0<? extends URI, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_URI_FUNCTION, defaultValue);
	}
	
	/**
	 * Gets the value of the URL property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static URL getUrlProperty(final Properties properties, final String name, final URL defaultValue)
	throws InvalidPropertyException {
		assert null != defaultValue;
		
		return getUrlProperty(properties, name, Functions.<URL, InvalidPropertyException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the URL property with the given name from the given properties.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The function computing the default value.
	 * @return The value of the property.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 * @throws InvalidPropertyException When the default value cannot be evaluated.
	 */
	public static URL getUrlProperty(final Properties properties, final String name, final Function0<? extends URL, ? extends InvalidPropertyException> defaultValue)
	throws InvalidPropertyException {
		return getProperty(properties, name, READ_URL_FUNCTION, defaultValue);
	}
	
	/**
	 * Gets the value of the mandatory property with the given name from the given properties.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param reader The value reader function.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static <T> T getMandatoryProperty(final Properties properties, final String name, final Function1<? super String, ? extends T, ? extends InvalidPropertyException> reader)
	throws MissingPropertyException, InvalidPropertyException {
		final Maybe<T> value = getProperty(properties, name, reader);
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new MissingPropertyException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory string property with the given name from the given properties.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static String getMandatoryStringProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, Functions.<String, InvalidPropertyException>identity());
	}
	
	/**
	 * Gets the value of the mandatory boolean property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static boolean getMandatoryBooleanProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_BOOLEAN_FUNCTION).booleanValue();
	}
	
	/**
	 * Gets the value of the mandatory integer property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static int getMandatoryIntegerProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_INTEGER_FUNCTION).intValue();
	}
	
	/**
	 * Gets the value of the mandatory long integer property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static long getMandatoryLongProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_LONG_FUNCTION).longValue();
	}
	
	/**
	 * Gets the value of the mandatory float property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static float getMandatoryFloatProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_FLOAT_FUNCTION).floatValue();
	}
	
	/**
	 * Gets the value of the mandatory double property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static double getMandatoryDoubleProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_DOUBLE_FUNCTION).doubleValue();
	}
	
	/**
	 * Gets the value of the mandatory file property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static File getMandatoryFileProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_FILE_FUNCTION);
	}
	
	/**
	 * Gets the value of the mandatory URI property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static URI getMandatoryUriProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_URI_FUNCTION);
	}
	
	/**
	 * Gets the value of the mandatory URL property with the given name from the given properties.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws MissingPropertyException When the property does not exist.
	 * @throws InvalidPropertyException When the value of the property is invalid.
	 */
	public static URL getMandatoryUrlProperty(final Properties properties, final String name)
	throws MissingPropertyException, InvalidPropertyException {
		return getMandatoryProperty(properties, name, READ_URL_FUNCTION);
	}
	
	/**
	 * Reads the boolean property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static boolean readBoolean(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		return Boolean.parseBoolean(representation);
	}
	
	/** Function that reads boolean property values. */
	public static final Function1<String, Boolean, InvalidPropertyException> READ_BOOLEAN_FUNCTION = new Function1<String, Boolean, InvalidPropertyException>() {
		@Override
		public Boolean evaluate(final String representation) {
			assert null != representation;
			
			return Boolean.valueOf(representation);
		}
	};
	
	/**
	 * Reads the integer property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static int readInteger(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		try {
			return Integer.parseInt(representation);
		} catch (final NumberFormatException exception) {
			throw new InvalidPropertyException("Invalid integer value \"" + representation + "\"", exception);
		}
	}
	
	/** Function that reads integer property values. */
	public static final Function1<String, Integer, InvalidPropertyException> READ_INTEGER_FUNCTION = new Function1<String, Integer, InvalidPropertyException>() {
		@Override
		public Integer evaluate(final String representation)
		throws InvalidPropertyException {
			return readInteger(representation);
		}
	};
	
	/**
	 * Reads the long property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static long readLong(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		try {
			return Long.parseLong(representation);
		} catch (final NumberFormatException exception) {
			throw new InvalidPropertyException("Invalid long value \"" + representation + "\"", exception);
		}
	}
	
	/** Function that reads long property values. */
	public static final Function1<String, Long, InvalidPropertyException> READ_LONG_FUNCTION = new Function1<String, Long, InvalidPropertyException>() {
		@Override
		public Long evaluate(final String representation)
		throws InvalidPropertyException {
			return readLong(representation);
		}
	};
	
	/**
	 * Reads the float property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static float readFloat(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		try {
			return Float.parseFloat(representation);
		} catch (final NumberFormatException exception) {
			throw new InvalidPropertyException("Invalid float value \"" + representation + "\"", exception);
		}
	}
	
	/** Function that reads long property values. */
	public static final Function1<String, Float, InvalidPropertyException> READ_FLOAT_FUNCTION = new Function1<String, Float, InvalidPropertyException>() {
		@Override
		public Float evaluate(final String representation)
		throws InvalidPropertyException {
			return readFloat(representation);
		}
	};
	
	/**
	 * Reads the double property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static double readDouble(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		try {
			return Double.parseDouble(representation);
		} catch (final NumberFormatException exception) {
			throw new InvalidPropertyException("Invalid double value \"" + representation + "\"", exception);
		}
	}
	
	/** Function that reads double property values. */
	public static final Function1<String, Double, InvalidPropertyException> READ_DOUBLE_FUNCTION = new Function1<String, Double, InvalidPropertyException>() {
		@Override
		public Double evaluate(final String representation)
		throws InvalidPropertyException {
			return readDouble(representation);
		}
	};
	
	/**
	 * Reads the file property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static File readFile(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		return new File(representation);
	}
	
	/** Function that reads file property values. */
	public static final Function1<String, File, InvalidPropertyException> READ_FILE_FUNCTION = new Function1<String, File, InvalidPropertyException>() {
		@Override
		public File evaluate(final String representation)
		throws InvalidPropertyException {
			return readFile(representation);
		}
	};
	
	/**
	 * Reads the URI property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static URI readUri(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		try {
			return new URI(representation);
		} catch (final URISyntaxException exception) {
			throw new InvalidPropertyException("Invalid URI value \"" + representation + "\"", exception);
		}
	}
	
	/** Function that reads URI property values. */
	public static final Function1<String, URI, InvalidPropertyException> READ_URI_FUNCTION = new Function1<String, URI, InvalidPropertyException>() {
		@Override
		public URI evaluate(final String representation)
		throws InvalidPropertyException {
			return readUri(representation);
		}
	};
	
	/**
	 * Reads the URL property value from the given representation.
	 * 
	 * @param representation The representation.
	 * @return The value.
	 * @throws InvalidPropertyException When the representation is invalid.
	 */
	public static URL readUrl(final String representation)
	throws InvalidPropertyException {
		assert null != representation;
		
		try {
			return new URL(representation);
		} catch (final MalformedURLException exception) {
			throw new InvalidPropertyException("Invalid URL value \"" + representation + "\"", exception);
		}
	}
	
	/** Function that reads URL property values. */
	public static final Function1<String, URL, InvalidPropertyException> READ_URL_FUNCTION = new Function1<String, URL, InvalidPropertyException>() {
		@Override
		public URL evaluate(final String representation)
		throws InvalidPropertyException {
			return readUrl(representation);
		}
	};
	
	private PropertiesUtils() {
		// Prevents instantiation.
	}
}
