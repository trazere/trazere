/*
 *  Copyright 2006-2008 Julien Dufour
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
 * The {@link MutableReference} class represents mutable refererences.
 * 
 * @param <T> Type of the referenced values.
 */
public class MutableReference<T>
implements Reference<T>, Describable {
	/** Value. */
	protected Maybe<T> _value;
	
	/**
	 * Instantiate an unset reference.
	 */
	public MutableReference() {
		this(Maybe.<T>none());
	}
	
	/**
	 * Instantiate a reference set with the given value.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 */
	public MutableReference(final T value) {
		this(Maybe.some(value));
	}
	
	/**
	 * Instantiate a reference with the given value.
	 * 
	 * @param value Initial value.
	 */
	public MutableReference(final Maybe<T> value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
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
		// Check that the reference is not set.
		if (_value.isSome()) {
			throw new ReferenceAlreadySetException("Reference already set to " + _value.asSome().getValue());
		}
		
		// Set the value.
		_value = Maybe.some(value);
	}
	
	/**
	 * Set or reset the receiver reference according to the given value.
	 * <p>
	 * The reference must not be set when a value is given.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 * @throws ReferenceAlreadySetException When the reference has already been set.
	 */
	public void set(final Maybe<T> value)
	throws ReferenceAlreadySetException {
		assert null != value;
		
		// Check that the reference is not set.
		if (value.isSome() && _value.isSome()) {
			throw new ReferenceAlreadySetException("Reference already set to " + _value.asSome().getValue());
		}
		
		// Update the value.
		_value = value;
	}
	
	/**
	 * Reset the receiver reference.
	 * 
	 * @throws ReferenceNotSetException When the reference has not been set.
	 */
	public void reset() {
		_value = Maybe.none();
	}
	
	/**
	 * Update the receiver reference to the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 */
	public void update(final T value) {
		_value = Maybe.some(value);
	}
	
	/**
	 * Set or reset the receiver reference according to the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param value Value to set.
	 */
	public void update(final Maybe<T> value) {
		assert null != value;
		
		// Update.
		_value = value;
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
	 * Get a view of the value set in the receiver reference as an instance of {@link Maybe}.
	 * 
	 * @return The set value wrapped in {@link Some}, or {@link None} when the reference has not been set.
	 */
	public Maybe<T> asMaybe() {
		return _value;
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_value);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final MutableReference<?> reference = (MutableReference<?>) object;
			return LangUtils.equals(_value, reference._value);
		} else {
			return false;
		}
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
