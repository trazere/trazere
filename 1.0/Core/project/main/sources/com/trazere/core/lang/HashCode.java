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

// TODO: implement Accumulator1

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
	 * Instantiates a new hash code computation for the given object.
	 * 
	 * @param object Object whose hash code should be computed.
	 */
	public HashCode(final Object object) {
		this(object.getClass());
	}
	
	/**
	 * Instantiates a new hash code computation for the given class.
	 * 
	 * @param class_ Class of the object whose hash code should be computed.
	 */
	public HashCode(final Class<?> class_) {
		this(class_.hashCode());
	}
	
	/**
	 * Instantiates a new hash code computation.
	 * 
	 * @param seed Seed hash code value.
	 */
	public HashCode(final int seed) {
		_hashCode = seed;
	}
	
	/**
	 * Appends the given boolean value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final boolean value) {
		_hashCode = _hashCode * 31 + (value ? 1 : 0);
		return this;
	}
	
	/**
	 * Appends the given byte value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final byte value) {
		_hashCode = _hashCode * 31 + value;
		return this;
	}
	
	/**
	 * Appends the given int value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append.
	 * @return The receiver hash code.
	 */
	public HashCode append(final int value) {
		_hashCode = _hashCode * 31 + value;
		return this;
	}
	
	/**
	 * Appends the given long value to the computation of the receiver hash code.
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
	 * Appends the given object value to the computation of the receiver hash code.
	 * 
	 * @param value Value to append. May be <code>null</code>.
	 * @return The receiver hash code.
	 */
	public HashCode append(final Object value) {
		_hashCode = _hashCode * 31 + (null != value ? value.hashCode() : 0);
		return this;
	}
	
	/**
	 * Gets the computed hash code.
	 * 
	 * @return The hash code.
	 */
	public int get() {
		return _hashCode;
	}
	
	// Object.
	
	@Override
	public String toString() {
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final Description description) {
		description.append("Hash code", _hashCode);
	}
}
