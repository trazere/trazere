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
 * The {@link MutableShort} class represents mutable short integer values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @since 1.0
 */
public class MutableShort
implements Describable {
	/**
	 * Current value.
	 * 
	 * @since 1.0
	 */
	protected short _value;
	
	/**
	 * Instantiates a new mutable short integer.
	 * 
	 * @param value Initial value.
	 * @since 1.0
	 */
	public MutableShort(final short value) {
		_value = value;
	}
	
	/**
	 * Gets the value of this mutable short integer.
	 * 
	 * @return The value.
	 * @since 1.0
	 */
	public short get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable short integer.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 * @since 1.0
	 */
	public short set(final short value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable short integer.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 * @since 1.0
	 */
	public short update(final Function<? super Short, ? extends Short> function) {
		return set(function.evaluate(_value).shortValue());
	}
	
	/**
	 * Negates the value of this mutable short integer.
	 * 
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short neg() {
		return set((short) -_value);
	}
	
	/**
	 * Adds the given value to the value of this mutable short integer.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short add(final short value) {
		return set((short) (_value + value));
	}
	
	/**
	 * Substracts the given value from the value of this mutable short integer.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short sub(final short value) {
		return set((short) (_value - value));
	}
	
	/**
	 * Multiplies the value of this mutable short integer by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short mul(final short value) {
		return set((short) (_value * value));
	}
	
	/**
	 * Divides the value of this mutable short integer by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short div(final short value) {
		return set((short) (_value / value));
	}
	
	/**
	 * Sets the value of this mutable short integer to the reminder of the division by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short mod(final short value) {
		return set((short) (_value % value));
	}
	
	/**
	 * Shifts the value of this mutable short integer to the left by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short shiftl(final int position) {
		return set((short) (_value << position));
	}
	
	/**
	 * Shifts the value of this mutable short integer to the right by the given number of bits.
	 * 
	 * @param position Number of bits to shift.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public short shiftr(final int position) {
		return set((short) (_value >> position));
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
