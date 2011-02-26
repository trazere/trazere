/*
 *  Copyright 2006-2011 Julien Dufour
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
 * The {@link HashCode} class helps to compute the hash code of objects.
 * <p>
 * This class works as an accumulator, each instance can only be used to compute a single hash code. The computation methods relies on the concrete class of the
 * object whose hash code is computed and a list of explicitely given values.
 */
public class HashCode
implements Describable {
	/** Hash code accumulator. */
	private int _hashCode;
	
	/**
	 * Instantiate a new hash code computation for the given object.
	 * 
	 * @param object Object whose hash code should be computed.
	 */
	public HashCode(final Object object) {
		assert null != object;
		
		// Initialization.
		_hashCode = object.getClass().hashCode();
	}
	
	/**
	 * Append the given boolean value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final boolean value) {
		_hashCode = _hashCode * 31 + (value ? 1 : 0);
		return this;
	}
	
	/**
	 * Append the given byte value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final byte value) {
		_hashCode = _hashCode * 31 + value;
		return this;
	}
	
	/**
	 * Append the given int value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final int value) {
		_hashCode = _hashCode * 31 + value;
		return this;
	}
	
	/**
	 * Append the given long value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final long value) {
		_hashCode = _hashCode * 31 + (int) (value >> 32);
		_hashCode = _hashCode * 31 + (int) (value & 0xFFFFFFFF);
		return this;
	}
	
	/**
	 * Append the given object value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append. May be <code>null</code>.
	 * @return The receiver hash code.
	 */
	public HashCode append(final Object value) {
		_hashCode = _hashCode * 31 + (null != value ? value.hashCode() : 0);
		return this;
	}
	
	/**
	 * Get the computed hash code.
	 * 
	 * @return The hash code.
	 */
	public int get() {
		return _hashCode;
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Hash code", _hashCode);
	}
}
