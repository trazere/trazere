/*
 *  Copyright 2008 Julien Dufour
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
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Maybe.None;
import com.trazere.util.type.Maybe.Some;

/**
 * The {@link LazyReference} class represents undefined refererences which await to be filled.
 * 
 * @param <T> Type of the referenced values.
 */
public class LazyReference<T>
implements Describable {
	/** Value. */
	protected Maybe<T> _value = Maybe.none();
	
	/**
	 * Test wether the receiver reference is set.
	 * 
	 * @return <code>true</code> when the reference is set, <code>false</code> otherwise.
	 */
	public boolean isSet() {
		return _value.isSome();
	}
	
	/**
	 * Set the receiver reference to the given value.
	 * <p>
	 * The reference must not be set.
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
	 * <p>
	 * The reference must not be set or the overwrite flag must be <code>true</code>.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 * @param overwrite Flag indicating wether already set references may be overwritten.
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
	 * Get the value set in the receiver reference.
	 * <p>
	 * The reference must be set.
	 * 
	 * @return The set value.
	 * @throws ReferenceNotSetException When the reference has not been set.
	 */
	public T get()
	throws ReferenceNotSetException {
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			throw new ReferenceNotSetException("Reference " + this + " is not set");
		}
	}
	
	/**
	 * Reset the receiver reference.
	 */
	public void reset() {
		reset(true);
	}
	
	/**
	 * Reset the receiver reference.
	 * <p>
	 * The reference must be set, of the overwrite flag must be <code>true</code>.
	 * 
	 * @param overwrite Flag indicating wether unset references may be reset.
	 * @throws ReferenceNotSetException When the reference has not been set.
	 */
	public void reset(final boolean overwrite)
	throws ReferenceNotSetException {
		if (overwrite || !_value.isNone()) {
			_value = Maybe.none();
		} else {
			throw new ReferenceNotSetException("Reference " + this + " is not set");
		}
	}
	
	/**
	 * Get a view of the value set in the receiver reference as an instance of {@link Maybe}.
	 * 
	 * @return The set value wrapped in {@link Some}, or {@link None} when the reference has not been set.
	 */
	public Maybe<T> asMaybe() {
		return _value;
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		if (_value.isSome()) {
			builder.append(" - Value = ").append(_value.asSome().getValue());
		} else {
			builder.append(" - Empty");
		}
	}
}
