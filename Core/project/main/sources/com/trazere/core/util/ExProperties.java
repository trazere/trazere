package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Thunk;
import com.trazere.core.io.Input;
import com.trazere.core.lang.PairIterable;
import com.trazere.core.record.InvalidFieldException;
import com.trazere.core.record.MissingFieldException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

// TODO: UUID

/**
 * The {@link ExProperties} class implements {@link Properties property tables} extended with various utilities.
 * 
 * @see Properties
 * @since 2.0
 */
public class ExProperties
extends Properties {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new property table with no default values.
	 * 
	 * @since 2.0
	 */
	public ExProperties() {
		super();
	}
	
	/**
	 * Instantiates a new property table with the given default values.
	 * 
	 * @param defaults Default properties.
	 * @since 2.0
	 */
	public ExProperties(@SuppressWarnings("hiding") final Properties defaults) {
		super(defaults);
	}
	
	// Properties.
	
	/**
	 * Loads the properties from the given input in a simple line-oriented format into this properties table.
	 * 
	 * @param input Input providing the properties to load.
	 * @throws IOException When the input cannot be read.
	 * @see PropertiesUtils#load(Properties, Input)
	 * @since 2.0
	 */
	public void load(final Input input)
	throws IOException {
		PropertiesUtils.load(this, input);
	}
	
	/**
	 * Loads the properties represented by the XML document from the given input into this properties table.
	 * 
	 * @param input Input providing the properties to load.
	 * @throws IOException When the input cannot be read.
	 * @see PropertiesUtils#loadFromXML(Properties, Input)
	 * @since 2.0
	 */
	public void loadFromXML(final Input input)
	throws IOException {
		PropertiesUtils.loadFromXML(this, input);
	}
	
	/**
	 * Gets a view of the properties corresponding to this property table.
	 * 
	 * @return The properties.
	 * @see PropertiesUtils#properties(Properties)
	 * @since 2.0
	 */
	// TODO: ExSet & PairIterable => BindingSet ?
	// TODO: PairCollection
	public PairIterable<String, String> properties() {
		return PropertiesUtils.properties(this);
	}
	
	/**
	 * Gets the value of the property with the given name from this property table.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#get(Properties, String, Function)
	 * @since 2.0
	 */
	public <T> Maybe<T> get(final String name, final Function<? super String, ? extends T> deserializer)
	throws InvalidFieldException {
		return PropertiesUtils.get(this, name, deserializer);
	}
	
	/**
	 * Gets the value of the property with the given name from this property table.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#get(Properties, String, Serializer)
	 * @since 2.0
	 */
	public <T> Maybe<T> get(final String name, final Serializer<? extends T, ? super String> deserializer)
	throws InvalidFieldException {
		return PropertiesUtils.get(this, name, deserializer);
	}
	
	/**
	 * Gets the value of the string property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getString(Properties, String)
	 * @since 2.0
	 */
	public Maybe<String> getString(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getString(this, name);
	}
	
	/**
	 * Gets the value of the boolean property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getBoolean(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Boolean> getBoolean(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getBoolean(this, name);
	}
	
	/**
	 * Gets the value of the integer property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getInteger(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Integer> getInteger(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getInteger(this, name);
	}
	
	/**
	 * Gets the value of the unsigned integer property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getUnsignedInteger(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Integer> getUnsignedInteger(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getUnsignedInteger(this, name);
	}
	
	/**
	 * Gets the value of the long integer property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getLong(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Long> getLong(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getLong(this, name);
	}
	
	/**
	 * Gets the value of the unsigned long integer property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getUnsignedLong(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Long> getUnsignedLong(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getUnsignedLong(this, name);
	}
	
	/**
	 * Gets the value of the float property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getFloat(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Float> getFloat(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getFloat(this, name);
	}
	
	/**
	 * Gets the value of the double property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getDouble(Properties, String)
	 * @since 2.0
	 */
	public Maybe<Double> getDouble(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getDouble(this, name);
	}
	
	/**
	 * Gets the value of the file property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getFile(Properties, String)
	 * @since 2.0
	 */
	public Maybe<File> getFile(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getFile(this, name);
	}
	
	/**
	 * Gets the value of the URI property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getUri(Properties, String)
	 * @since 2.0
	 */
	public Maybe<URI> getUri(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getUri(this, name);
	}
	
	/**
	 * Gets the value of the URL property with the given name from this property table.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property, or nothing when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getUrl(Properties, String)
	 * @since 2.0
	 */
	public Maybe<URL> getUrl(final String name)
	throws InvalidFieldException {
		return PropertiesUtils.getUrl(this, name);
	}
	
	/**
	 * Gets the value of the optional property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptional(Properties, String, Function, Object)
	 * @since 2.0
	 */
	public <T> T getOptional(final String name, final Function<? super String, ? extends T> deserializer, final T defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptional(this, name, deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptional(Properties, String, Serializer, Object)
	 * @since 2.0
	 */
	public <T> T getOptional(final String name, final Serializer<? extends T, ? super String> deserializer, final T defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptional(this, name, deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional string property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalString(Properties, String, String)
	 * @since 2.0
	 */
	public String getOptionalString(final String name, final String defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalString(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional boolean property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalBoolean(Properties, String, boolean)
	 * @since 2.0
	 */
	public boolean getOptionalBoolean(final String name, final boolean defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalBoolean(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalInteger(Properties, String, int)
	 * @since 2.0
	 */
	public int getOptionalInteger(final String name, final int defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalInteger(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUnsignedInteger(Properties, String, int)
	 * @since 2.0
	 */
	public int getOptionalUnsignedInteger(final String name, final int defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUnsignedInteger(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional long integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalLong(Properties, String, long)
	 * @since 2.0
	 */
	public long getOptionalLong(final String name, final long defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalLong(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned long integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUnsignedLong(Properties, String, long)
	 * @since 2.0
	 */
	public long getOptionalUnsignedLong(final String name, final long defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalLong(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional float property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalFloat(Properties, String, float)
	 * @since 2.0
	 */
	public float getOptionalFloat(final String name, final float defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalFloat(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional double property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalDouble(Properties, String, double)
	 * @since 2.0
	 */
	public double getOptionalDouble(final String name, final double defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalDouble(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional file property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalFile(Properties, String, File)
	 * @since 2.0
	 */
	public File getOptionalFile(final String name, final File defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalFile(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional URI property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUri(Properties, String, URI)
	 * @since 2.0
	 */
	public URI getOptionalUri(final String name, final URI defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUri(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional URL property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUrl(Properties, String, URL)
	 * @since 2.0
	 */
	public URL getOptionalUrl(final String name, final URL defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUrl(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptional(Properties, String, Function, Thunk)
	 * @since 2.0
	 */
	public <T> T getOptional(final String name, final Function<? super String, ? extends T> deserializer, final Thunk<? extends T> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptional(this, name, deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptional(Properties, String, Serializer, Thunk)
	 * @since 2.0
	 */
	public <T> T getOptional(final String name, final Serializer<? extends T, ? super String> deserializer, final Thunk<? extends T> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptional(this, name, deserializer, defaultValue);
	}
	
	/**
	 * Gets the value of the optional string property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalString(Properties, String, Thunk)
	 * @since 2.0
	 */
	public String getOptionalString(final String name, final Thunk<? extends String> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalString(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional boolean property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalBoolean(Properties, String, Thunk)
	 * @since 2.0
	 */
	public boolean getOptionalBoolean(final String name, final Thunk<? extends Boolean> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalBoolean(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalInteger(Properties, String, Thunk)
	 * @since 2.0
	 */
	public int getOptionalInteger(final String name, final Thunk<? extends Integer> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalInteger(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUnsignedInteger(Properties, String, Thunk)
	 * @since 2.0
	 */
	public int getOptionalUnsignedInteger(final String name, final Thunk<? extends Integer> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUnsignedInteger(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional long integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalLong(Properties, String, Thunk)
	 * @since 2.0
	 */
	public long getOptionalLong(final String name, final Thunk<? extends Long> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalLong(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional unsigned long integer property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUnsignedLong(Properties, String, Thunk)
	 * @since 2.0
	 */
	public long getOptionalUnsignedLong(final String name, final Thunk<? extends Long> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUnsignedLong(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional float property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalFloat(Properties, String, Thunk)
	 * @since 2.0
	 */
	public float getOptionalFloat(final String name, final Thunk<? extends Float> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalFloat(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional double property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalDouble(Properties, String, Thunk)
	 * @since 2.0
	 */
	public double getOptionalDouble(final String name, final Thunk<? extends Double> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalDouble(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional file property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalFile(Properties, String, Thunk)
	 * @since 2.0
	 */
	public File getOptionalFile(final String name, final Thunk<? extends File> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalFile(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional URI property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUri(Properties, String, Thunk)
	 * @since 2.0
	 */
	public URI getOptionalUri(final String name, final Thunk<? extends URI> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUri(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the optional URL property with the given name from this property table.
	 * <p>
	 * The given default value is returned when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @param defaultValue Default value when the property does not exist.
	 * @return The value of the property, or the default value when the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getOptionalUrl(Properties, String, Thunk)
	 * @since 2.0
	 */
	public URL getOptionalUrl(final String name, final Thunk<? extends URL> defaultValue)
	throws InvalidFieldException {
		return PropertiesUtils.getOptionalUrl(this, name, defaultValue);
	}
	
	/**
	 * Gets the value of the mandatory property with the given name from this property table.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Function to use to deserialize the representation.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatory(Properties, String, Function)
	 * @since 2.0
	 */
	public <T> T getMandatory(final String name, final Function<? super String, ? extends T> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatory(this, name, deserializer);
	}
	
	/**
	 * Gets the value of the mandatory property with the given name from this property table.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param <T> Type of the value.
	 * @param name Name of the property to read.
	 * @param deserializer Serializer to use to deserialize the representation.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatory(Properties, String, Serializer)
	 * @since 2.0
	 */
	public <T> T getMandatory(final String name, final Serializer<? extends T, ? super String> deserializer)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatory(this, name, deserializer);
	}
	
	/**
	 * Gets the value of the mandatory string property with the given name from this property table.
	 * <p>
	 * An exception is raised when the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryString(Properties, String)
	 * @since 2.0
	 */
	public String getMandatoryString(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryString(this, name);
	}
	
	/**
	 * Gets the value of the mandatory boolean property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryBoolean(Properties, String)
	 * @since 2.0
	 */
	public boolean getMandatoryBoolean(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryBoolean(this, name);
	}
	
	/**
	 * Gets the value of the mandatory integer property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryInteger(Properties, String)
	 * @since 2.0
	 */
	public int getMandatoryInteger(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryInteger(this, name);
	}
	
	/**
	 * Gets the value of the mandatory unsigned integer property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryUnsignedInteger(Properties, String)
	 * @since 2.0
	 */
	public int getMandatoryUnsignedInteger(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryUnsignedInteger(this, name);
	}
	
	/**
	 * Gets the value of the mandatory long integer property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryLong(Properties, String)
	 * @since 2.0
	 */
	public long getMandatoryLong(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryLong(this, name);
	}
	
	/**
	 * Gets the value of the mandatory unsigned long integer property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryUnsignedLong(Properties, String)
	 * @since 2.0
	 */
	public long getMandatoryUnsignedLong(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryLong(this, name);
	}
	
	/**
	 * Gets the value of the mandatory float property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryFloat(Properties, String)
	 * @since 2.0
	 */
	public float getMandatoryFloat(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryFloat(this, name);
	}
	
	/**
	 * Gets the value of the mandatory double property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryDouble(Properties, String)
	 * @since 2.0
	 */
	public double getMandatoryDouble(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryDouble(this, name);
	}
	
	/**
	 * Gets the value of the mandatory file property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryFile(Properties, String)
	 * @since 2.0
	 */
	public File getMandatoryFile(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryFile(this, name);
	}
	
	/**
	 * Gets the value of the mandatory URI property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryUri(Properties, String)
	 * @since 2.0
	 */
	public URI getMandatoryUri(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryUri(this, name);
	}
	
	/**
	 * Gets the value of the mandatory URL property with the given name from this property table.
	 * <p>
	 * An exception is raised if the property does not exist.
	 * 
	 * @param name Name of the property to read.
	 * @return The value of the property.
	 * @throws MissingFieldException When the property does not exist.
	 * @throws InvalidFieldException When the representation is invalid.
	 * @see PropertiesUtils#getMandatoryUrl(Properties, String)
	 * @since 2.0
	 */
	public URL getMandatoryUrl(final String name)
	throws MissingFieldException, InvalidFieldException {
		return PropertiesUtils.getMandatoryUrl(this, name);
	}
}
