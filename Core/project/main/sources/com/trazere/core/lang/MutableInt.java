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
package com.trazere.core.lang;

import com.trazere.core.functional.Function;
import com.trazere.core.text.Describable;
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.TextUtils;

/**
 * The {@link MutableInt} class represents mutable integer values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @since 1.0
 */
public class MutableInt
implements Describable {
	/**
	 * Current value.
	 * 
	 * @since 1.0
	 */
	protected int _value;
	
	/**
	 * Instantiates a new mutable integer.
	 * 
	 * @param value Initial value.
	 * @since 1.0
	 */
	public MutableInt(final int value) {
		_value = value;
	}
	
	/**
	 * Gets the value of this mutable integer.
	 * 
	 * @return The value.
	 * @since 1.0
	 */
	public int get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable integer.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 * @since 1.0
	 */
	public int set(final int value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable integer.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 * @since 1.0
	 */
	public int update(final Function<? super Integer, ? extends Integer> function) {
		return set(function.evaluate(_value).intValue());
	}
	
	/**
	 * Negates the value of this mutable integer.
	 * 
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int neg() {
		return set(-_value);
	}
	
	/**
	 * Adds the given value to the value of this mutable integer.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int add(final int value) {
		return set(_value + value);
	}
	
	/**
	 * Substracts the given value from the value of this mutable integer.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int sub(final int value) {
		return set(_value - value);
	}
	
	/**
	 * Multiplies the value of this mutable integer by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int mul(final int value) {
		return set(_value * value);
	}
	
	/**
	 * Divides the value of this mutable integer by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int div(final int value) {
		return set(_value / value);
	}
	
	/**
	 * Sets the value of this mutable integer to the reminder of the division by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int mod(final int value) {
		return set(_value % value);
	}
	
	/**
	 * Shifts the value of this mutable integer to the left by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int shiftl(final int position) {
		return set(_value << position);
	}
	
	/**
	 * Shifts the value of this mutable integer to the right by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public int shiftr(final int position) {
		return set(_value >> position);
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final DescriptionBuilder description) {
		description.append("Value", _value);
	}
}
