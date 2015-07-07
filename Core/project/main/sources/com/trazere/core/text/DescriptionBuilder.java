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
package com.trazere.core.text;

import com.trazere.core.lang.MutableBoolean;
import com.trazere.core.util.Maybe;
import java.util.Collection;

/**
 * The {@link DescriptionBuilder} class implements an utility to compute descriptions.
 * <p>
 * This class works as an accumulator of named properties that populates some string builder.
 * 
 * @since 1.0
 */
public class DescriptionBuilder {
	/**
	 * Instantiates a new empty description.
	 * 
	 * @param format Format of the description.
	 * @since 1.0
	 */
	public DescriptionBuilder(final DescriptionFormat format) {
		this(format, new StringBuilder(format.getOpening()), true);
	}
	
	/**
	 * Instantiates a new description with the given header.
	 * 
	 * @param format Format of the description.
	 * @param header Header of the description.
	 * @since 1.0
	 */
	public DescriptionBuilder(final DescriptionFormat format, final String header) {
		this(format, new StringBuilder(format.getOpening()).append(header), false);
	}
	
	/**
	 * Instantiates a new description.
	 * 
	 * @param format Format of the description.
	 * @param builder Builder to populate.
	 * @param empty Indicates whether the description is still empty.
	 * @since 1.0
	 */
	public DescriptionBuilder(final DescriptionFormat format, final StringBuilder builder, final boolean empty) {
		assert null != format;
		assert null != builder;
		
		// Initialization.
		_format = format;
		_builder = builder;
		_empty = new MutableBoolean(empty);
	}
	
	// Format.
	
	/** Format of the description. */
	protected final DescriptionFormat _format;
	
	/**
	 * Gets the format of this description.
	 * 
	 * @return The format.
	 * @since 1.0
	 */
	public DescriptionFormat getFormat() {
		return _format;
	}
	
	// Builder.
	
	/**
	 * String to populate with the description.
	 * 
	 * @since 1.0
	 */
	protected final StringBuilder _builder;
	
	/**
	 * Indicates whether the description is still empty.
	 * 
	 * @since 1.0
	 */
	protected final MutableBoolean _empty;
	
	/**
	 * Appends the given empty property to this description.
	 * 
	 * @param name Name of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name) {
		if (_empty.get()) {
			_empty.set(false);
		} else {
			_builder.append(_format.getDelimiter());
		}
		_builder.append(name);
		
		return this;
	}
	
	/**
	 * Appends the given property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final Object value) {
		append(name);
		_builder.append(_format.getAssigment()).append(value);
		
		return this;
	}
	
	/**
	 * Appends the given boolean property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final boolean value) {
		return append(name, Boolean.valueOf(value));
	}
	
	/**
	 * Appends the given byte property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final byte value) {
		return append(name, Byte.valueOf(value));
	}
	
	/**
	 * Appends the given integer property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final int value) {
		return append(name, Integer.valueOf(value));
	}
	
	/**
	 * Appends the given long integer property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final long value) {
		return append(name, Long.valueOf(value));
	}
	
	/**
	 * Appends the given float property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final float value) {
		return append(name, Float.valueOf(value));
	}
	
	/**
	 * Appends the given double property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final double value) {
		return append(name, Double.valueOf(value));
	}
	
	/**
	 * Appends the given char property to this description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final char value) {
		return append(name, Character.valueOf(value));
	}
	
	/**
	 * Appends the given optional property to this description.
	 * <p>
	 * Nothing is appended when the property has no values.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final Maybe<?> value) {
		assert null != name;
		assert null != value;
		
		if (value.isSome()) {
			return append(name, value.asSome().getValue());
		} else {
			return this;
		}
	}
	
	/**
	 * Appends the given collection property to this description.
	 * <p>
	 * Nothing is appended when the property has no values.
	 * 
	 * @param name Name of the property.
	 * @param values Values of the property.
	 * @return this description.
	 * @since 1.0
	 */
	public DescriptionBuilder append(final String name, final Collection<?> values) {
		assert null != name;
		assert null != values;
		
		if (!values.isEmpty()) {
			return append(name, values);
		} else {
			return this;
		}
	}
	
	// TODO: append(String, Iterable) ?
	
	// Object.
	
	/**
	 * Gets the resulting representation of this description.
	 * 
	 * @return The string representation.
	 * @since 1.0
	 */
	@Override
	public String toString() {
		return _builder.toString() + _format.getClosing();
	}
}
