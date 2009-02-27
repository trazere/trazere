/*
 *  Copyright 2006-2009 Julien Dufour
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
 */
public class MutableObject<T>
implements Describable {
	/** The value. May be <code>null</code>. */
	protected T _value;
	
	/**
	 * Instantiate a new mutable object with the given value.
	 * 
	 * @param value The initial value. May be <code>null</code>.
	 */
	public MutableObject(final T value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Set the receiver mutable object to the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public void set(final T value) {
		_value = value;
	}
	
	/**
	 * Get the value set in the receiver mutable object.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T get() {
		return _value;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final Description description) {
		description.append("Value", _value);
	}
}
