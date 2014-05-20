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
 * The {@link MutableDouble} class represents mutable double values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 */
public class MutableDouble
implements Describable {
	/** Current value. */
	protected double _value;
	
	/**
	 * Instantiates a new mutable double.
	 * 
	 * @param value Initial value.
	 */
	public MutableDouble(final double value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of the receiver mutable double.
	 * 
	 * @return The current value.
	 */
	public double get() {
		return _value;
	}
	
	/**
	 * Sets the current value of the receiver mutable double.
	 * 
	 * @param value New value.
	 * @return The given value.
	 */
	public double set(final double value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the current value of the receiver mutable double.
	 * 
	 * @return The resulting value.
	 */
	public double neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the current value of the receiver mutable double.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 */
	public double add(final double value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the current value of the receiver mutable double.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 */
	public double sub(final double value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the current value of the receiver mutable double by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 */
	public double mul(final double value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the current value of the receiver mutable double by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 */
	public double div(final double value) {
		_value = _value / value;
		return _value;
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void appendDescription(final Description description) {
		description.append("Value", _value);
	}
}
