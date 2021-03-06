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
package com.trazere.util.lang;

import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link MutableInt} class represents mutable integer values.
 * 
 * @deprecated Use {@link com.trazere.core.lang.MutableInt}.
 */
@Deprecated
public class MutableInt
implements Describable {
	/** The value. */
	protected int _value;
	
	/**
	 * Instantiates a new mutable integer with the given value.
	 * 
	 * @param value The initial value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#MutableInt(int)}.
	 */
	@Deprecated
	public MutableInt(final int value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Gets the value of the receiver mutable integer.
	 * 
	 * @return The value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#get()}.
	 */
	@Deprecated
	public int get() {
		return _value;
	}
	
	/**
	 * Sets the value of the receiver mutable integer to the given value.
	 * 
	 * @param value The value.
	 * @return The given value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#set(int)}.
	 */
	@Deprecated
	public int set(final int value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the value of the receiver mutable integer.
	 * 
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#neg()}.
	 */
	@Deprecated
	public int neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the value of the receiver mutable integer.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#add(int)}.
	 */
	@Deprecated
	public int add(final int value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the value of the receiver mutable integer.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#sub(int)}.
	 */
	@Deprecated
	public int sub(final int value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the value of the receiver mutable integer by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#mul(int)}.
	 */
	@Deprecated
	public int mul(final int value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the value of the receiver mutable integer by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#div(int)}.
	 */
	@Deprecated
	public int div(final int value) {
		_value = _value / value;
		return _value;
	}
	
	/**
	 * Updates the value of the receiver mutable integer with the reminder of the division by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#mod(int)}.
	 */
	@Deprecated
	public int mod(final int value) {
		_value = _value % value;
		return _value;
	}
	
	/**
	 * Shifts the value of the receiver mutable integer to the left by the given number of bits.
	 * 
	 * @param position The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#shiftl(int)}.
	 */
	@Deprecated
	public int shiftl(final int position) {
		_value = _value << position;
		return _value;
	}
	
	/**
	 * Shifts the value of the receiver mutable integer to the right by the given number of bits.
	 * 
	 * @param position The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableInt#shiftr(int)}.
	 */
	@Deprecated
	public int shiftr(final int position) {
		_value = _value >> position;
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
