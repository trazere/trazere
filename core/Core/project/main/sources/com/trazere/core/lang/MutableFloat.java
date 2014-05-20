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
 * The {@link MutableFloat} class represents mutable float values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 */
public class MutableFloat
implements Describable {
	/** Current value. */
	protected float _value;
	
	/**
	 * Instantiates a new mutable float.
	 * 
	 * @param value Initial value.
	 */
	public MutableFloat(final float value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of the receiver mutable float.
	 * 
	 * @return The current value.
	 */
	public float get() {
		return _value;
	}
	
	/**
	 * Sets the current value of the receiver mutable float.
	 * 
	 * @param value New value.
	 * @return The given value.
	 */
	public float set(final float value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the current value of the receiver mutable float.
	 * 
	 * @return The resulting value.
	 */
	public float neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the current value of the receiver mutable float.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 */
	public float add(final float value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the current value of the receiver mutable float.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 */
	public float sub(final float value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the current value of the receiver mutable float by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 */
	public float mul(final float value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the current value of the receiver mutable float by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 */
	public float div(final float value) {
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
