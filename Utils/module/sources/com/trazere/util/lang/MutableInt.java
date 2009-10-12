/*
 *  Copyright 2006-2009 Julien Dufour
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
 * The {@link MutableInt} class represents mutable integer values.
 */
public class MutableInt
implements Describable {
	/** The value. */
	protected int _value;
	
	/**
	 * Instantiate a new mutable integer with the given value.
	 * 
	 * @param value The initial value.
	 */
	public MutableInt(final int value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Get the value set in the receiver mutable integer.
	 * 
	 * @return The value.
	 */
	public int get() {
		return _value;
	}
	
	/**
	 * Set the receiver mutable integer to the given value.
	 * 
	 * @param value The value.
	 */
	public void set(final int value) {
		_value = value;
	}
	
	/**
	 * Update the receiver mutable integer by negating its value.
	 */
	public void neg() {
		_value = -_value;
	}
	
	/**
	 * Update the receiver mutable integer by adding the given value.
	 * 
	 * @param value The value
	 */
	public void add(final int value) {
		_value = _value + value;
	}
	
	/**
	 * Update the receiver mutable integer by substracting the given value.
	 * 
	 * @param value The value
	 */
	public void sub(final int value) {
		_value = _value - value;
	}
	
	/**
	 * Update the receiver mutable integer by multiplying with the given value.
	 * 
	 * @param value The value
	 */
	public void mul(final int value) {
		_value = _value * value;
	}
	
	/**
	 * Update the receiver mutable integer by dividing by the given value.
	 * 
	 * @param value The value
	 */
	public void div(final int value) {
		_value = _value / value;
	}
	
	/**
	 * Update the receiver mutable integer by shifting it to the left by the given number of position.
	 * 
	 * @param position The value
	 */
	public void shiftl(final int position) {
		_value = _value << position;
	}
	
	/**
	 * Update the receiver mutable integer by shifting it to the right by the given number of position.
	 * 
	 * @param position The value
	 */
	public void shiftr(final int position) {
		_value = _value >> position;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
