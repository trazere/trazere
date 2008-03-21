/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.util;

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;

/**
 * The <code>LazyReference</code> class represents undefined refererences which await to be filled.
 * 
 * @param <T> Type of the referenced values.
 */
public class LazyReference<T>
implements Describable {
	/** Reference to the value. */
	protected Maybe<T> _value = Maybe.none();
	
	/**
	 * Set the receiver reference to the given value.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 * @throws ReferenceAlreadySetException When the reference has already been set.
	 */
	public void set(final T value)
	throws ReferenceAlreadySetException {
		set(value, false);
	}
	
	/**
	 * Set the receiver reference to the given value.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 * @param overwrite Flag indicating wether already set reference may be overwritten.
	 * @throws ReferenceAlreadySetException When the reference has already been set.
	 */
	public void set(final T value, final boolean overwrite)
	throws ReferenceAlreadySetException {
		if (overwrite || !_value.isSome()) {
			_value = Maybe.some(value);
		} else {
			throw new ReferenceAlreadySetException("Reference already filled with " + _value.asSome().getValue());
		}
	}
	
	/**
	 * Get the current receiver reference.
	 * 
	 * @return A <code>Some</code> of the set value or a <code>None</code> instance when the reference has not been set.
	 */
	public Maybe<T> get() {
		return _value;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		if (_value.isSome()) {
			builder.append(" - Value = ").append(((Maybe.Some<?>) _value).getValue());
		} else {
			builder.append(" - Empty");
		}
	}
}
