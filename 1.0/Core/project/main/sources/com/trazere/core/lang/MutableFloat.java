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
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;

/**
 * The {@link MutableFloat} class represents mutable float values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @since 1.0
 */
public class MutableFloat
implements Describable {
	/**
	 * Current value.
	 * 
	 * @since 1.0
	 */
	protected float _value;
	
	/**
	 * Instantiates a new mutable float.
	 * 
	 * @param value Initial value.
	 * @since 1.0
	 */
	public MutableFloat(final float value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of this mutable float.
	 * 
	 * @return The current value.
	 * @since 1.0
	 */
	public float get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable float.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 * @since 1.0
	 */
	public float set(final float value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable float.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 * @since 1.0
	 */
	public float update(final Function<? super Float, ? extends Float> function) {
		return set(function.evaluate(_value).floatValue());
	}
	
	/**
	 * Negates the value of this mutable float.
	 * 
	 * @return The resulting value.
	 * @since 1.0
	 */
	public float neg() {
		return set(-_value);
	}
	
	/**
	 * Adds the given value to the value of this mutable float.
	 * 
	 * @param value Value to add.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public float add(final float value) {
		return set(_value + value);
	}
	
	/**
	 * Substracts the given value from the value of this mutable float.
	 * 
	 * @param value Value to substract.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public float sub(final float value) {
		return set(_value - value);
	}
	
	/**
	 * Multiplies the value of this mutable float by the given value.
	 * 
	 * @param value Value to multiply by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public float mul(final float value) {
		return set(_value * value);
	}
	
	/**
	 * Divides the value of this mutable float by the given value.
	 * 
	 * @param value Value to divide by.
	 * @return The resulting value.
	 * @since 1.0
	 */
	public float div(final float value) {
		return set(_value / value);
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
