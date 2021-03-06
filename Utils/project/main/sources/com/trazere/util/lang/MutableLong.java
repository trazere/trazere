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
 * The {@link MutableLong} class represents mutable long integer values.
 * 
 * @deprecated Use {@link com.trazere.core.lang.MutableLong}.
 */
@Deprecated
public class MutableLong
implements Describable {
	/** The value. */
	protected long _value;
	
	/**
	 * Instantiates a new mutable long with the given value.
	 * 
	 * @param value The initial value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#MutableLong(long)}.
	 */
	@Deprecated
	public MutableLong(final long value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Gets the value of the receiver mutable long integer.
	 * 
	 * @return The value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#get()}.
	 */
	@Deprecated
	public long get() {
		return _value;
	}
	
	/**
	 * Sets the value of the receiver mutable long integer to the given value.
	 * 
	 * @param value The value.
	 * @return The given value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#set(long)}.
	 */
	@Deprecated
	public long set(final long value) {
		_value = value;
		return value;
	}
	
	/**
	 * Negates the value of the receiver mutable long integer.
	 * 
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#neg()}.
	 */
	@Deprecated
	public long neg() {
		_value = -_value;
		return _value;
	}
	
	/**
	 * Adds the given value to the value of the receiver mutable long integer.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#add(long)}.
	 */
	@Deprecated
	public long add(final long value) {
		_value = _value + value;
		return _value;
	}
	
	/**
	 * Substracts the given value from the value of the receiver mutable long integer.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#sub(long)}.
	 */
	@Deprecated
	public long sub(final long value) {
		_value = _value - value;
		return _value;
	}
	
	/**
	 * Multiplies the value of the receiver mutable long integer by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#mul(long)}.
	 */
	@Deprecated
	public long mul(final long value) {
		_value = _value * value;
		return _value;
	}
	
	/**
	 * Divides the value of the receiver mutable long integer by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#div(long)}.
	 */
	@Deprecated
	public long div(final long value) {
		_value = _value / value;
		return _value;
	}
	
	/**
	 * Updates the value of the receiver mutable long integer with the reminder of the division by the given value.
	 * 
	 * @param value The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#mod(long)}.
	 */
	@Deprecated
	public long mod(final long value) {
		_value = _value % value;
		return _value;
	}
	
	/**
	 * Shifts the value of the receiver mutable long integer to the left by the given number of bits.
	 * 
	 * @param position The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#shiftl(int)}.
	 */
	@Deprecated
	public long shiftl(final int position) {
		_value = _value << position;
		return _value;
	}
	
	/**
	 * Shifts the value of the receiver mutable long integer to the right by the given number of bits.
	 * 
	 * @param position The value
	 * @return The resulting value.
	 * @deprecated Use {@link com.trazere.core.lang.MutableLong#shiftr(int)}.
	 */
	@Deprecated
	public long shiftr(final int position) {
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
