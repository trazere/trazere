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
 * The {@link MutableDouble} class represents mutable double values.
 * 
 * @deprecated Use {@link com.trazere.core.lang.MutableDouble}.
 */
@Deprecated
public class MutableDouble
implements Describable {
	/** The value. */
	protected double _value;
	
	/**
	 * Instantiates a new mutable double with the given value.
	 * 
	 * @param value The initial value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#MutableDouble(double)}.
	 */
	@Deprecated
	public MutableDouble(final double value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Gets the value of the receiver mutable double.
	 * 
	 * @return The value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#get()}.
	 */
	@Deprecated
	public double get() {
		return _value;
	}
	
	/**
	 * Sets the value of the receiver mutable boolean to the given value.
	 * 
	 * @param value The value.
	 * @return The given value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#set(double)}.
	 */
	@Deprecated
	public double set(final double value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the value of the receiver mutable double.
	 * 
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#neg()}.
	 */
	@Deprecated
	public double neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the value of the receiver mutable double.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#add(double)}.
	 */
	@Deprecated
	public double add(final double value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the value of the receiver mutable double.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#sub(double)}.
	 */
	@Deprecated
	public double sub(final double value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the value of the receiver mutable double by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#mul(double)}.
	 */
	@Deprecated
	public double mul(final double value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the value of the receiver mutable double by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableDouble#div(double)}.
	 */
	@Deprecated
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
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
