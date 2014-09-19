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

import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;

/**
 * The {@link MutableBoolean} class represents mutable boolean values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 */
public class MutableBoolean
implements Describable {
	/** Current value. */
	protected boolean _value;
	
	/**
	 * Instantiates a new mutable boolean.
	 * 
	 * @param value Initial value.
	 */
	public MutableBoolean(final boolean value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of the receiver mutable boolean.
	 * 
	 * @return The current value.
	 */
	public boolean get() {
		return _value;
	}
	
	/**
	 * Sets the current value of the receiver mutable boolean.
	 * 
	 * @param value New value.
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
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final Description description) {
		description.append("Value", _value);
	}
}