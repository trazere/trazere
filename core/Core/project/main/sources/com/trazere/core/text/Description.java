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
package com.trazere.core.text;

import com.trazere.core.lang.MutableBoolean;
import com.trazere.core.util.Maybe;

// TODO: rename to DescriptionBuilder
// TODO: extends Accumulator2
// TODO: generalize bounds and separator => generalize to or use some Joiner class ?

/**
 * The {@link Description} class helps to compute descriptions.
 * <p>
 * This class works as an accumulator of named properties that populates some string builder.
 */
public class Description {
	/**
	 * Builds a new description for the given object.
	 * <p>
	 * This factory method uses the class of the object as header.
	 * 
	 * @param object Object to describe.
	 * @return The built descriptor.
	 */
	public static Description build(final Object object) {
		return new Description(TextUtils.computeClassName(object.getClass()));
	}
	
	/** String to populate with the description. */
	protected final StringBuilder _builder;
	
	/** Indicates whether the description is still empty. */
	protected final MutableBoolean _empty;
	
	/**
	 * Instantiates a new empty description.
	 */
	public Description() {
		this(new StringBuilder("["), true);
	}
	
	/**
	 * Instantiates a new description with the given header.
	 * 
	 * @param header Header of the description.
	 */
	public Description(final String header) {
		this(new StringBuilder("[").append(header), false);
	}
	
	/**
	 * Instantiates a new description.
	 * 
	 * @param builder Builder to populate.
	 * @param empty Indicates whether the description is still empty.
	 */
	public Description(final StringBuilder builder, final boolean empty) {
		assert null != builder;
		
		// Initialization.
		_builder = builder;
		_empty = new MutableBoolean(empty);
	}
	
	/**
	 * Appends the given empty property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name) {
		if (_empty.get()) {
			_empty.set(false);
		} else {
			_builder.append(" - ");
		}
		_builder.append(name);
		
		return this;
	}
	
	/**
	 * Appends the given property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final Object value) {
		append(name);
		_builder.append(" = ").append(value);
		
		return this;
	}
	
	/**
	 * Appends the given boolean property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final boolean value) {
		return append(name, Boolean.valueOf(value));
	}
	
	/**
	 * Appends the given byte property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final byte value) {
		return append(name, Byte.valueOf(value));
	}
	
	/**
	 * Appends the given integer property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final int value) {
		return append(name, Integer.valueOf(value));
	}
	
	/**
	 * Appends the given long integer property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final long value) {
		return append(name, Long.valueOf(value));
	}
	
	/**
	 * Appends the given float property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final float value) {
		return append(name, Float.valueOf(value));
	}
	
	/**
	 * Appends the given double property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final double value) {
		return append(name, Double.valueOf(value));
	}
	
	/**
	 * Appends the given char property to the receiver description.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final char value) {
		return append(name, Character.valueOf(value));
	}
	
	/**
	 * Appends the given optional property to the receiver description.
	 * <p>
	 * Nothing is appended when the property has no values.
	 * 
	 * @param name Name of the property.
	 * @param value Value of the property.
	 * @return The receiver description.
	 */
	public Description append(final String name, final Maybe<?> value) {
		assert null != name;
		assert null != value;
		
		if (value.isSome()) {
			return append(name, value.asSome().getValue());
		} else {
			return this;
		}
	}
	
	// TODO: append(String, Iterable)
	
	/**
	 * Gets the resulting representation of the receiver description.
	 * 
	 * @return The string representation.
	 */
	@Override
	public String toString() {
		return _builder.toString() + "]";
	}
}
