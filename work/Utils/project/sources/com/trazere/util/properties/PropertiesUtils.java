/*
 *  Copyright 2006-2011 Julien Dufour
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
import java.util.List;
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
	 * @throws PropertiesException When some property file is not optional and does not exist.
	 * @throws PropertiesException When some property file cannot be loaded.
	 */
	public static Properties loadProperties(final List<Tuple2<Input, Boolean>> inputs)
	throws PropertiesException {
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
	 * @throws PropertiesException When some property file is not optional and does not exist.
	 * @throws PropertiesException When some property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final List<Tuple2<Input, Boolean>> inputs)
	throws PropertiesException {
		assert null != properties;
		assert null != inputs;
		
		for (final Tuple2<Input, Boolean> file : inputs) {
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
	 * @throws PropertiesException When the property file is not optional and does not exist.
	 * @throws PropertiesException When the property file cannot be loaded.
	 */
	public static Properties loadProperties(final Properties properties, final Input input, final boolean optional)
	throws PropertiesException {
		assert null != properties;
		assert null != input;
		
		try {
			if (!optional || input.exists()) {
				final InputStream stream = input.open();
				try {
					properties.load(stream);
				} finally {
					stream.close();
				}
			}
			return properties;
		} catch (final IOException exception) {
			throw new PropertiesException("Failed loading properties from " + input, exception);
		}
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
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 */
	public static Maybe<String> getStringProperty(final Properties properties, final String name) {
		assert null != properties;
		assert null != name;
		
		return Maybe.fromValue(properties.getProperty(name));
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 */
	public static Maybe<Boolean> getBooleanProperty(final Properties properties, final String name) {
		return getStringProperty(properties, name).map(_PARSE_BOOLEAN_FUNCTION);
	}
	
	private static final Function1<String, Boolean, RuntimeException> _PARSE_BOOLEAN_FUNCTION = new Function1<String, Boolean, RuntimeException>() {
		public Boolean evaluate(final String representation) {
			assert null != representation;
			
			return Boolean.valueOf(representation);
		}
	};
	
	/**
	 * Gets the value of the integer property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static Maybe<Integer> getIntegerProperty(final Properties properties, final String name)
	throws PropertiesException {
		try {
			return getStringProperty(properties, name).map(_PARSE_INTEGER_FUNCTION);
		} catch (final PropertiesException exception) {
			throw new PropertiesException("Invalid property \"" + name + "\"", exception);
		}
	}
	
	private static final Function1<String, Integer, PropertiesException> _PARSE_INTEGER_FUNCTION = new Function1<String, Integer, PropertiesException>() {
		public Integer evaluate(final String representation)
		throws PropertiesException {
			assert null != representation;
			
			try {
				return Integer.parseInt(representation);
			} catch (final NumberFormatException exception) {
				throw new PropertiesException("Invalid int value \"" + representation + "\"");
			}
		}
	};
	
	/**
	 * Gets the value of the long property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static Maybe<Long> getLongProperty(final Properties properties, final String name)
	throws PropertiesException {
		try {
			return getStringProperty(properties, name).map(_PARSE_LONG_FUNCTION);
		} catch (final PropertiesException exception) {
			throw new PropertiesException("Invalid property \"" + name + "\"", exception);
		}
	}
	
	private static final Function1<String, Long, PropertiesException> _PARSE_LONG_FUNCTION = new Function1<String, Long, PropertiesException>() {
		public Long evaluate(final String representation)
		throws PropertiesException {
			assert null != representation;
			
			try {
				return Long.parseLong(representation);
			} catch (final NumberFormatException exception) {
				throw new PropertiesException("Invalid long value \"" + representation + "\"");
			}
		}
	};
	
	/**
	 * Gets the value of the float property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static Maybe<Float> getFloatProperty(final Properties properties, final String name)
	throws PropertiesException {
		try {
			return getStringProperty(properties, name).map(_PARSE_FLOAT_FUNCTION);
		} catch (final PropertiesException exception) {
			throw new PropertiesException("Invalid property \"" + name + "\"", exception);
		}
	}
	
	private static final Function1<String, Float, PropertiesException> _PARSE_FLOAT_FUNCTION = new Function1<String, Float, PropertiesException>() {
		public Float evaluate(final String representation)
		throws PropertiesException {
			assert null != representation;
			
			try {
				return Float.parseFloat(representation);
			} catch (final NumberFormatException exception) {
				throw new PropertiesException("Invalid float value \"" + representation + "\"");
			}
		}
	};
	
	/**
	 * Gets the value of the double property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static Maybe<Double> getDoubleProperty(final Properties properties, final String name)
	throws PropertiesException {
		try {
			return getStringProperty(properties, name).map(_PARSE_DOUBLE_FUNCTION);
		} catch (final PropertiesException exception) {
			throw new PropertiesException("Invalid property \"" + name + "\"", exception);
		}
	}
	
	private static final Function1<String, Double, PropertiesException> _PARSE_DOUBLE_FUNCTION = new Function1<String, Double, PropertiesException>() {
		public Double evaluate(final String representation)
		throws PropertiesException {
			assert null != representation;
			
			try {
				return Double.parseDouble(representation);
			} catch (final NumberFormatException exception) {
				throw new PropertiesException("Invalid double value \"" + representation + "\"");
			}
		}
	};
	
	/**
	 * Gets the value of the file property with the given name from the given properties.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 */
	public static Maybe<File> getFileProperty(final Properties properties, final String name) {
		return getStringProperty(properties, name).map(_MAKE_FILE_FUNCTION);
	}
	
	private static final Function1<String, File, RuntimeException> _MAKE_FILE_FUNCTION = new Function1<String, File, RuntimeException>() {
		public File evaluate(final String representation) {
			assert null != representation;
			
			return new File(representation);
		}
	};
	
	/**
	 * Gets the value of the property with the given name from the given properties. The given default value is returned when the property does not exist. x
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 */
	public static String getStringProperty(final Properties properties, final String name, final String defaultValue) {
		assert null != defaultValue;
		
		return getStringProperty(properties, name, Functions.<String, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the property with the given name from the given properties. The given default value is returned when the property does not exist. x
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> String getStringProperty(final Properties properties, final String name, final Function0<String, X> defaultValue)
	throws X {
		assert null != defaultValue;
		
		final Maybe<String> value = getStringProperty(properties, name);
		return value.isSome() ? value.asSome().getValue() : defaultValue.evaluate(); // TODO: #14
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given properties. The given default value is returned when the property does not
	 * exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 */
	public static boolean getBooleanProperty(final Properties properties, final String name, final boolean defaultValue) {
		return getBooleanProperty(properties, name, Functions.<Boolean, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given properties. The given default value is returned when the property does not
	 * exist.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> boolean getBooleanProperty(final Properties properties, final String name, final Function0<Boolean, X> defaultValue)
	throws X {
		assert null != defaultValue;
		
		final Maybe<Boolean> value = getBooleanProperty(properties, name);
		return (value.isSome() ? value.asSome().getValue() : defaultValue.evaluate()).booleanValue(); // TODO: #14
	}
	
	/**
	 * Gets the value of the integer property with the given name from the given properties. The given default value is returned when the property does not
	 * exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static int getIntegerProperty(final Properties properties, final String name, final int defaultValue)
	throws PropertiesException {
		return getIntegerProperty(properties, name, Functions.<Integer, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the integer property with the given name from the given properties. The given default value is returned when the property does not
	 * exist.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> int getIntegerProperty(final Properties properties, final String name, final Function0<Integer, X> defaultValue)
	throws PropertiesException, X {
		assert null != defaultValue;
		
		final Maybe<Integer> value = getIntegerProperty(properties, name);
		return (value.isSome() ? value.asSome().getValue() : defaultValue.evaluate()).intValue(); // TODO: #14
	}
	
	/**
	 * Gets the value of the long property with the given name from the given properties. The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static long getLongProperty(final Properties properties, final String name, final long defaultValue)
	throws PropertiesException {
		return getLongProperty(properties, name, Functions.<Long, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the long property with the given name from the given properties. The given default value is returned when the property does not exist.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> long getLongProperty(final Properties properties, final String name, final Function0<Long, X> defaultValue)
	throws PropertiesException, X {
		assert null != defaultValue;
		
		final Maybe<Long> value = getLongProperty(properties, name);
		return (value.isSome() ? value.asSome().getValue() : defaultValue.evaluate()).longValue(); // TODO: #14
	}
	
	/**
	 * Gets the value of the float property with the given name from the given properties. The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static float getFloatProperty(final Properties properties, final String name, final float defaultValue)
	throws PropertiesException {
		return getFloatProperty(properties, name, Functions.<Float, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the float property with the given name from the given properties. The given default value is returned when the property does not exist.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> float getFloatProperty(final Properties properties, final String name, final Function0<Float, X> defaultValue)
	throws PropertiesException, X {
		assert null != defaultValue;
		
		final Maybe<Float> value = getFloatProperty(properties, name);
		return (value.isSome() ? value.asSome().getValue() : defaultValue.evaluate()).floatValue(); // TODO: #14
	}
	
	/**
	 * Gets the value of the double property with the given name from the given properties. The given default value is returned when the property does not
	 * exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static double getDoubleProperty(final Properties properties, final String name, final double defaultValue)
	throws PropertiesException {
		return getDoubleProperty(properties, name, Functions.<Double, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the double property with the given name from the given properties. The given default value is returned when the property does not
	 * exist.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws PropertiesException When the value of the property is invalid.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> double getDoubleProperty(final Properties properties, final String name, final Function0<Double, X> defaultValue)
	throws PropertiesException, X {
		assert null != defaultValue;
		
		final Maybe<Double> value = getDoubleProperty(properties, name);
		return (value.isSome() ? value.asSome().getValue() : defaultValue.evaluate()).doubleValue();
	}
	
	/**
	 * Gets the value of the file property with the given name from the given properties. The given default value is returned when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 */
	public static File getFileProperty(final Properties properties, final String name, final File defaultValue) {
		assert null != defaultValue;
		
		return getFileProperty(properties, name, Functions.<File, RuntimeException>constant0(defaultValue));
	}
	
	/**
	 * Gets the value of the file property with the given name from the given properties. The given default value is returned when the property does not exist.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @param defaultValue The default value.
	 * @return The value of the property.
	 * @throws X When the default value cannot be evaluated.
	 */
	public static <X extends Exception> File getFileProperty(final Properties properties, final String name, final Function0<File, X> defaultValue)
	throws X {
		assert null != defaultValue;
		
		final Maybe<File> value = getFileProperty(properties, name);
		return value.isSome() ? value.asSome().getValue() : defaultValue.evaluate();
	}
	
	/**
	 * Gets the value of the mandatory property with the given name from the given properties. An exception is raised when the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 */
	public static String getMandatoryStringProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<String> value = getStringProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory boolean property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 */
	public static boolean getMandatoryBooleanProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<Boolean> value = getBooleanProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue().booleanValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory integer property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static int getMandatoryIntegerProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<Integer> value = getIntegerProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue().intValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory long integer property with the given name from the given properties. An exception is raised if the property does not
	 * exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static long getMandatoryLongProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<Long> value = getLongProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue().longValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory float property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static float getMandatoryFloatProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<Float> value = getFloatProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue().floatValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory double property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 * @throws PropertiesException When the value of the property is invalid.
	 */
	public static double getMandatoryDoubleProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<Double> value = getDoubleProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue().doubleValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	/**
	 * Gets the value of the mandatory file property with the given name from the given properties. An exception is raised if the property does not exist.
	 * 
	 * @param properties The properties to read.
	 * @param name The name of the property.
	 * @return The value of the property.
	 * @throws PropertiesException When the property does not exist.
	 */
	public static File getMandatoryFileProperty(final Properties properties, final String name)
	throws PropertiesException {
		final Maybe<File> value = getFileProperty(properties, name);
		if (value.isSome()) {
			return value.asSome().getValue();
		} else {
			throw new PropertiesException("Missing property \"" + name + "\"");
		}
	}
	
	private PropertiesUtils() {
		// Prevents instantiation.
	}
}
