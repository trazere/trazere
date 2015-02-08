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
package com.trazere.core.lang;

import com.trazere.core.functional.Function;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;

/**
 * The {@link MutableByte} class represents mutable byte values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 */
public class MutableByte
implements Describable {
	/** Current value. */
	protected byte _value;
	
	/**
	 * Instantiates a new mutable byte.
	 * 
	 * @param value Initial value.
	 */
	public MutableByte(final byte value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of this mutable byte.
	 * 
	 * @return The current value.
	 */
	public byte get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable byte.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 */
	public byte set(final byte value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable byte.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 */
	public byte update(final Function<? super Byte, ? extends Byte> function) {
		return set(function.evaluate(_value).byteValue());
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final Description description) {
		description.append("Value", _value);
	}
}
