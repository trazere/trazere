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
 * The {@link MutableFloat} class represents mutable float values.
 * 
 * @deprecated Use {@link com.trazere.core.lang.MutableFloat}.
 */
@Deprecated
public class MutableFloat
implements Describable {
	/** The value. */
	protected float _value;
	
	/**
	 * Instantiates a new mutable float with the given value.
	 * 
	 * @param value The initial value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#MutableFloat(float)}.
	 */
	@Deprecated
	public MutableFloat(final float value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Gets the value of the receiver mutable float.
	 * 
	 * @return The value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#get()}.
	 */
	@Deprecated
	public float get() {
		return _value;
	}
	
	/**
	 * Sets the value of the receiver mutable boolean to the given value.
	 * 
	 * @param value The value.
	 * @return The given value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#set(float)}.
	 */
	@Deprecated
	public float set(final float value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the value of the receiver mutable float.
	 * 
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#neg()}.
	 */
	@Deprecated
	public float neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the value of the receiver mutable float.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#add(float)}.
	 */
	@Deprecated
	public float add(final float value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the value of the receiver mutable float.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#sub(float)}.
	 */
	@Deprecated
	public float sub(final float value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the value of the receiver mutable float by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#mul(float)}.
	 */
	@Deprecated
	public float mul(final float value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the value of the receiver mutable float by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableFloat#div(float)}.
	 */
	@Deprecated
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
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
