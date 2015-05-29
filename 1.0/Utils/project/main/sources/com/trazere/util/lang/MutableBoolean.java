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
package com.trazere.util.lang;

import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link MutableBoolean} class represents mutable boolean values.
 * 
 * @deprecated Use {@link com.trazere.core.lang.MutableBoolean}.
 */
@Deprecated
public class MutableBoolean
implements Describable {
	/** The value. */
	protected boolean _value;
	
	/**
	 * Instantiates a new mutable boolean with the given value.
	 * 
	 * @param value The initial value.
	 */
	public MutableBoolean(final boolean value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Gets the value of the receiver mutable boolean.
	 * 
	 * @return The value.
	 */
	public boolean get() {
		return _value;
	}
	
	/**
	 * Sets the value of the receiver mutable boolean to the given value.
	 * 
	 * @param value The value.
	 * @return The given value.
	 */
	public boolean set(final boolean value) {
		_value = value;
		return value;
	}
	
	/**
	 * Inverses the value of the receiver mutable boolean.
	 * 
	 * @return The resulting value.
	 */
	public boolean not() {
		_value = !_value;
		return _value;
	}
	
	/**
	 * Updates the value of the receiver mutable boolean by conjunction with the given value.
	 * 
	 * @param value The value.
	 * @return The resulting value.
	 */
	public boolean and(final boolean value) {
		_value = _value && value;
		return _value;
	}
	
	/**
	 * Updates the value of the receiver mutable boolean by disjunction with the given value.
	 * 
	 * @param value The value.
	 * @return The resulting value.
	 */
	public boolean or(final boolean value) {
		_value = _value || value;
		return _value;
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
