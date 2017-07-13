package com.trazere.core.util;

import com.trazere.core.io.Input;
import java.io.IOException;
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
}
