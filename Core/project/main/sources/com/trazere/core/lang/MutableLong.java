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
 * The {@link MutableLong} class represents mutable long integer values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 */
public class MutableLong
implements Describable {
	/** Current value. */
	protected long _value;
	
	/**
	 * Instantiates a new mutable long integer.
	 * 
	 * @param value Initial value.
	 */
	public MutableLong(final long value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of the receiver mutable long integer.
	 * 
	 * @return The current value.
	 */
	public long get() {
		return _value;
	}
	
	/**
	 * Sets the current value of the receiver mutable long integer.
	 * 
	 * @param value New value.
	 * @return The given value.
	 */
	public long set(final long value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the current value of the receiver mutable long integer.
	 * 
	 * @return The resulting value.
	 */
	public long neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the current value of the receiver mutable long integer.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 */
	public long add(final long value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the current value of the receiver mutable long integer.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 */
	public long sub(final long value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the current value of the receiver mutable long integer by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 */
	public long mul(final long value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the current value of the receiver mutable long integer by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 */
	public long div(final long value) {
		_value = _value / value;
		return _value;
	}
	
	/**
	 * Sets the current value of the receiver mutable long integer to the reminder of the division by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 */
	public long mod(final long value) {
		_value = _value % value;
		return _value;
	}
	
	/**
	 * Shifts the current value of the receiver mutable long integer to the left by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 */
	public long shiftl(final int position) {
		_value = _value << position;
		return _value;
	}
	
	/**
	 * Shifts the current value of the receiver mutable long integer to the right by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 */
	public long shiftr(final int position) {
		_value = _value >> position;
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
