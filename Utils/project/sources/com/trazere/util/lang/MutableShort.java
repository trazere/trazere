/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.lang;

import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link MutableShort} class represents mutable short values.
 */
public class MutableShort
implements Describable {
	/** The value. */
	protected short _value;
	
	/**
	 * Instantiates a new mutable short with the given value.
	 * 
	 * @param value The initial value.
	 */
	public MutableShort(final short value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Gets the value of the receiver mutable short.
	 * 
	 * @return The value.
	 */
	public short get() {
		return _value;
	}
	
	/**
	 * Sets the value of the receiver mutable boolean to the given value.
	 * 
	 * @param value The value.
	 * @return The given value.
	 */
	public short set(final short value) {
		_value = value;
		return value;
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
