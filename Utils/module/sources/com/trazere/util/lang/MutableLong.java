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
 * The {@link MutableLong} class represents mutable long values.
 */
public class MutableLong
implements Describable {
	/** The value. */
	protected long _value;
	
	/**
	 * Instantiate a new mutable long with the given value.
	 * 
	 * @param value The initial value.
	 */
	public MutableLong(final long value) {
		// Initialization.
		_value = value;
	}
	
	/**
	 * Set the receiver mutable long to the given value.
	 * 
	 * @param value The value.
	 */
	public void set(final long value) {
		_value = value;
	}
	
	/**
	 * Get the value set in the receiver mutable long.
	 * 
	 * @return The value.
	 */
	public long get() {
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
