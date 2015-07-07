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

// TODO: MutableChar

/**
 * The {@link MutableObject} class represents mutable object values.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @param <V> Type of the value.
 * @since 1.0
 */
public class MutableObject<V>
implements Describable {
	/**
	 * Current value.
	 * 
	 * @since 1.0
	 */
	protected V _value;
	
	/**
	 * Instantiates a new mutable object.
	 * 
	 * @param value Initial value.
	 * @since 1.0
	 */
	public MutableObject(final V value) {
		_value = value;
	}
	
	/**
	 * Gets the current value of this mutable object.
	 * 
	 * @return The current value.
	 * @since 1.0
	 */
	public V get() {
		return _value;
	}
	
	/**
	 * Sets the value of this mutable obejct.
	 * 
	 * @param <NV> Type of the new value.
	 * @param value New value.
	 * @return The given new value.
	 * @since 1.0
	 */
	public <NV extends V> NV set(final NV value) {
		_value = value;
		return value;
	}
	
	/**
	 * Updates the value of this mutable object.
	 * 
	 * @param <NV> Type of the new value.
	 * @param function Function to use to compute the new value.
	 * @return The computed new value.
	 * @since 1.0
	 */
	public <NV extends V> NV update(final Function<? super V, ? extends NV> function) {
		return set(function.evaluate(_value));
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
