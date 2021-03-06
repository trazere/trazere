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
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.TextUtils;

/**
 * The {@link MutableChar} class represents mutable char values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @since 2.0
 */
public class MutableChar
implements Describable {
	/**
	 * Current value.
	 * 
	 * @since 2.0
	 */
	protected char _value;
	
	/**
	 * Instantiates a new mutable char.
	 * 
	 * @param value Initial value.
	 * @since 2.0
	 */
	public MutableChar(final char value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of this mutable char.
	 * 
	 * @return The current value.
	 * @since 2.0
	 */
	public char get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable char.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 * @since 2.0
	 */
	public char set(final char value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable char.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 * @since 2.0
	 */
	public char update(final Function<? super Character, ? extends Character> function) {
		return set(function.evaluate(_value).charValue());
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final DescriptionBuilder description) {
		description.append("Value", _value);
	}
}
