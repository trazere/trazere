/*
 *  Copyright 2006-2015 Julien Dufour
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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.PairIterator;
import com.trazere.core.io.Input;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.record.FieldUtils;
import com.trazere.core.record.InvalidFieldException;
import com.trazere.core.record.MissingFieldException;
import com.trazere.core.text.TextSerializers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

/**
 * The {@link PropertiesUtils} class provides various utilities regarding {@link Properties properties}.
 * 
 * @see Properties
 * @since 2.0
 */
public class PropertiesUtils {
	/**
	 * Loads the properties from the given input in a simple line-oriented format into the given properties table.
	 * 
	 * @param properties Property table to populate.
	 * @param input Input providing the properties to load.
	 * @throws IOException When the input cannot be read.
	 * @see Properties#load(InputStream)
	 * @since 2.0
	 */
	public static void load(final Properties properties, final Input input)
	throws IOException {
		try (final InputStream stream = input.open()) {
			properties.load(stream);
		}
	}
	
	/**
	 * Loads the properties represented by the XML document from the given input into this properties table.
	 * 
	 * @param properties Property table to populate.
	 * @param input Input providing the properties to load.
	 * @throws IOException When the input cannot be read.
	 * @see Properties#load(InputStream)
	 * @since 2.0
	 */
	public static void loadFromXML(final Properties properties, final Input input)
	throws IOException {
		try (final InputStream stream = input.open()) {
			properties.loadFromXML(stream);
		}
	}
	
	/**
	 * Gets a view of the properties corresponding to the given property table.
	 * 
	 * @param properties Property table to read.
	 * @return The properties.
	 * @since 2.0
	 */
	// TODO: ExSet & PairIterable => BindingSet ?
	// TODO: PairCollection
	public static PairIterable<String, String> properties(final Properties properties) {
		// HACK: not a lambda to work around a bug of Eclipse
		return new PairIterable<String, String>() {
			@Override
			public PairIterator<String, String> iterator() {
				final Iterator<String> names = properties.stringPropertyNames().iterator();
				return new PairIterator<String, String>() {
					@Override
					public boolean hasNext() {
						return names.hasNext();
					}
					
					@Override
					public Tuple2<String, String> next() {
						final String name = names.next();
						return new Tuple2<>(name, properties.getProperty(name));
					}
				};
			}
		};
	}
	
	/**
	 * Gets the value of the property with the given name from the given property table.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> Maybe<T> get(final Properties properties, final String name, final Function<? super String, ? extends T> deserializer)
	throws InvalidFieldException {
		return FieldUtils.readNullable(name, properties.getProperty(name), deserializer);
	}
	
	/**
	 * Gets the value of the property with the given name from the given property table.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> Maybe<T> get(final Properties properties, final String name, final Serializer<? extends T, ? super String> deserializer)
	throws InvalidFieldException {
		return get(properties, name, deserializer::deserialize);
	}
	
	/**
	 * Gets the value of the string property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<String> getString(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.string());
	}
	
	/**
	 * Gets the value of the boolean property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Boolean> getBoolean(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.boolean_());
	}
	
	/**
	 * Gets the value of the integer property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Integer> getInteger(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.integer());
	}
	
	/**
	 * Gets the value of the unsigned integer property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Integer> getUnsignedInteger(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.unsignedInteger());
	}
	
	/**
	 * Gets the value of the long integer property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Long> getLong(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.long_());
	}
	
	/**
	 * Gets the value of the unsigned long integer property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Long> getUnsignedLong(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.unsignedLong());
	}
	
	/**
	 * Gets the value of the float property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Float> getFloat(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.float_());
	}
	
	/**
	 * Gets the value of the double property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<Double> getDouble(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.double_());
	}
	
	/**
	 * Gets the value of the file property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<File> getFile(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.file());
	}
	
	/**
	 * Gets the value of the URI property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<URI> getUri(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.uri());
	}
	
	/**
	 * Gets the value of the URL property with the given name from the given property table.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static Maybe<URL> getUrl(final Properties properties, final String name)
	throws InvalidFieldException {
		return get(properties, name, TextSerializers.url());
	}
	
	/**
	 * Gets the value of the optional property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> T getOptional(final Properties properties, final String name, final Function<? super String, ? extends T> deserializer, final T defaultValue)
	throws InvalidFieldException {
		return FieldUtils.readOptionalNullable(name, properties.getProperty(name), deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> T getOptional(final Properties properties, final String name, final Serializer<? extends T, ? super String> deserializer, final T defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, deserializer::deserialize, defaultValue);
	}
	
	/**
	 * Gets the value of the optional string property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static String getOptionalString(final Properties properties, final String name, final String defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.string(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional boolean property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static boolean getOptionalBoolean(final Properties properties, final String name, final boolean defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.boolean_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static int getOptionalInteger(final Properties properties, final String name, final int defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.integer(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static int getOptionalUnsignedInteger(final Properties properties, final String name, final int defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.unsignedInteger(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional long integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static long getOptionalLong(final Properties properties, final String name, final long defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.long_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned long integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static long getOptionalUnsignedLong(final Properties properties, final String name, final long defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.unsignedLong(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional float property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static float getOptionalFloat(final Properties properties, final String name, final float defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.float_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional double property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static double getOptionalDouble(final Properties properties, final String name, final double defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.double_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional file property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static File getOptionalFile(final Properties properties, final String name, final File defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.file(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional URI property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static URI getOptionalUri(final Properties properties, final String name, final URI defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.uri(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional URL property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static URL getOptionalUrl(final Properties properties, final String name, final URL defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.url(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> T getOptional(final Properties properties, final String name, final Function<? super String, ? extends T> deserializer, final Thunk<? extends T> defaultValue)
	throws InvalidFieldException {
		return FieldUtils.readOptionalNullable(name, properties.getProperty(name), deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> T getOptional(final Properties properties, final String name, final Serializer<? extends T, ? super String> deserializer, final Thunk<? extends T> defaultValue)
	throws InvalidFieldException {
		return FieldUtils.readOptionalNullable(name, properties.getProperty(name), deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional string property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static String getOptionalString(final Properties properties, final String name, final Thunk<? extends String> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.string(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional boolean property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static boolean getOptionalBoolean(final Properties properties, final String name, final Thunk<? extends Boolean> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.boolean_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static int getOptionalInteger(final Properties properties, final String name, final Thunk<? extends Integer> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.integer(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static int getOptionalUnsignedInteger(final Properties properties, final String name, final Thunk<? extends Integer> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.unsignedInteger(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional long integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static long getOptionalLong(final Properties properties, final String name, final Thunk<? extends Long> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.long_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned long integer property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static long getOptionalUnsignedLong(final Properties properties, final String name, final Thunk<? extends Long> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.unsignedLong(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional float property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static float getOptionalFloat(final Properties properties, final String name, final Thunk<? extends Float> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.float_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional double property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static double getOptionalDouble(final Properties properties, final String name, final Thunk<? extends Double> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.double_(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional file property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static File getOptionalFile(final Properties properties, final String name, final Thunk<? extends File> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.file(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional URI property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static URI getOptionalUri(final Properties properties, final String name, final Thunk<? extends URI> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.uri(), defaultValue);
	}
	
	/**
	 * Gets the value of the optional URL property with the given name from the given property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static URL getOptionalUrl(final Properties properties, final String name, final Thunk<? extends URL> defaultValue)
	throws InvalidFieldException {
		return getOptional(properties, name, TextSerializers.url(), defaultValue);
	}
	
	/**
	 * Gets the value of the mandatory property with the given name from the given property table.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> T getMandatory(final Properties properties, final String name, final Function<? super String, ? extends T> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return FieldUtils.readMandatoryNullable(name, properties.getProperty(name), deserializer);
	}
	
	/**
	 * Gets the value of the mandatory property with the given name from the given property table.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static <T> T getMandatory(final Properties properties, final String name, final Serializer<? extends T, ? super String> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, deserializer::deserialize);
	}
	
	/**
	 * Gets the value of the mandatory string property with the given name from the given property table.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static String getMandatoryString(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.string());
	}
	
	/**
	 * Gets the value of the mandatory boolean property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static boolean getMandatoryBoolean(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.boolean_()).booleanValue();
	}
	
	/**
	 * Gets the value of the mandatory integer property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static int getMandatoryInteger(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.integer()).intValue();
	}
	
	/**
	 * Gets the value of the mandatory unsigned integer property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static int getMandatoryUnsignedInteger(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.unsignedInteger()).intValue();
	}
	
	/**
	 * Gets the value of the mandatory long integer property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static long getMandatoryLong(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.long_()).longValue();
	}
	
	/**
	 * Gets the value of the mandatory unsigned long integer property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static long getMandatoryUnsignedLong(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.unsignedLong()).longValue();
	}
	
	/**
	 * Gets the value of the mandatory float property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static float getMandatoryFloat(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.float_()).floatValue();
	}
	
	/**
	 * Gets the value of the mandatory double property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static double getMandatoryDouble(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.double_()).doubleValue();
	}
	
	/**
	 * Gets the value of the mandatory file property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static File getMandatoryFile(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.file());
	}
	
	/**
	 * Gets the value of the mandatory URI property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static URI getMandatoryUri(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.uri());
	}
	
	/**
	 * Gets the value of the mandatory URL property with the given name from the given property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param properties Property table to read.
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @since 2.0
	 */
	public static URL getMandatoryUrl(final Properties properties, final String name)
	throws MissingFieldException, InvalidFieldException {
		return getMandatory(properties, name, TextSerializers.url());
	}
	
	private PropertiesUtils() {
		// Prevents instantiation.
	}
}
