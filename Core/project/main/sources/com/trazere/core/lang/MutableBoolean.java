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
 * The {@link MutableBoolean} class represents mutable boolean values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @since 2.0
 */
public class MutableBoolean
implements Describable {
	/**
	 * Current value.
	 * 
	 * @since 2.0
	 */
	protected boolean _value;
	
	/**
	 * Instantiates a new mutable boolean.
	 * 
	 * @param value Initial value.
	 * @since 2.0
	 */
	public MutableBoolean(final boolean value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of this mutable boolean.
	 * 
	 * @return The current value.
	 * @since 2.0
	 */
	public boolean get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable boolean.
	 * 
	 * @param value New value.
	 * @return The given new value.
	 * @since 2.0
	 */
	public boolean set(final boolean value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable boolean.
	 * 
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 * @since 2.0
	 */
	public boolean update(final Function<? super Boolean, ? extends Boolean> function) {
		return set(function.evaluate(_value).booleanValue());
	}
	
	/**
	 * Inverses the value of this mutable boolean.
	 * 
	 * @return The resulting new value.
	 * @since 2.0
	 */
	public boolean not() {
		return set(!_value);
	}
	
	/**
	 * Updates the value of this mutable boolean by conjunction with the given value.
	 * 
	 * @param value Value.
	 * @return The resulting new value.
	 * @since 2.0
	 */
	public boolean and(final boolean value) {
		return set(_value && value);
	}
	
	/**
	 * Updates the value of this mutable boolean by disjunction with the given value.
	 * 
	 * @param value Value.
	 * @return The resulting new value.
	 * @since 2.0
	 */
	public boolean or(final boolean value) {
		return set(_value || value);
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
