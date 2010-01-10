/*
 *  Copyright 2006-2010 Julien Dufour
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
 * The {@link MutableByte} class represents mutable byte values.
 */
public class MutableByte
implements Describable {
	/** The value. */
	protected byte _value;
	
	/**
	 * Instantiate a new mutable byte with the given value.
	 * 
	 * @param value The initial value.
	 */
	public MutableByte(final byte value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Get the value set in the receiver mutable byte.
	 * 
	 * @return The value.
	 */
	public byte get() {
		return _value;
	}
	
	/**
	 * Set the receiver mutable byte to the given value.
	 * 
	 * @param value The value.
	 */
	public void set(final byte value) {
		_value = value;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
