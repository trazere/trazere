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
	 * Gets the value of this mutable long integer.
	 * 
	 * @return The value.
	 */
	public long get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable long integer.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 */
	public long set(final long value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable long integer.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 */
	public long update(final Function<? super Long, ? extends Long> function) {
		return set(function.evaluate(_value).longValue());
	}
	
	/**
	 * Negates the value of this mutable long integer.
	 * 
	 * @return The resulting value.
	 */
	public long neg() {
		return set(-_value);
	}
	
	/**
	 * Adds the given value to the value of this mutable long integer.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 */
	public long add(final long value) {
		return set(_value + value);
	}
	
	/**
	 * Substracts the given value from the value of this mutable long integer.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 */
	public long sub(final long value) {
		return set(_value - value);
	}
	
	/**
	 * Multiplies the value of this mutable long integer by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 */
	public long mul(final long value) {
		return set(_value * value);
	}
	
	/**
	 * Divides the value of this mutable long integer by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 */
	public long div(final long value) {
		return set(_value / value);
	}
	
	/**
	 * Sets the value of this mutable long integer to the reminder of the division by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 */
	public long mod(final long value) {
		return set(_value % value);
	}
	
	/**
	 * Shifts the value of this mutable long integer to the left by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 */
	public long shiftl(final int position) {
		return set(_value << position);
	}
	
	/**
	 * Shifts the value of this mutable long integer to the right by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 */
	public long shiftr(final int position) {
		return set(_value >> position);
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
