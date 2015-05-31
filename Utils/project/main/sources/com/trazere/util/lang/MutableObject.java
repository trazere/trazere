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
 * The {@link MutableObject} class represents mutable object values.
 * 
 * @param <T> Type of the value.
 * @deprecated Use {@link com.trazere.core.lang.MutableObject}.
 */
@Deprecated
public class MutableObject<T>
implements Describable {
	/** The value. May be <code>null</code>. */
	protected T _value;
	
	/**
	 * Instantiates a new mutable object with the given value.
	 * 
	 * @param value The initial value. May be <code>null</code>.
	 */
	public MutableObject(final T value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Sets the value of the receiver mutable object to the given value.
	 * 
	 * @param <V> Type of the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The given value. May be <code>null</code>.
	 */
	public <V extends T> V set(final V value) {
		_value = value;
		return value;
	}
	
	/**
	 * Gets the value of the receiver mutable object.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T get() {
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
