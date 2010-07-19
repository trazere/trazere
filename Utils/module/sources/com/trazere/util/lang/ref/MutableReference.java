/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.lang.ref;

import com.trazere.util.Releasable;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;

/**
 * The {@link MutableReference} class represents mutable refererences.
 * 
 * @param <T> Type of the referenced values.
 */
public class MutableReference<T>
implements Reference<T>, Releasable<RuntimeException>, Describable {
	/** The value. */
	protected Maybe<T> _value;
	
	/**
	 * Instantiates an unset reference.
	 */
	public MutableReference() {
		this(Maybe.<T>none());
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public MutableReference(final T value) {
		this(Maybe.some(value));
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value.
	 */
	public MutableReference(final Maybe<T> value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
	/**
	 * Tests whether the receiver reference is set.
	 * 
	 * @return <code>true</code> when the reference is set, <code>false</code> otherwise.
	 */
	public boolean isSet() {
		return _value.isSome();
	}
	
	/**
	 * Sets the receiver reference to the given value.
	 * <p>
	 * The reference must not be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The given value. May be <code>null</code>.
	 * @throws ReferenceAlreadySetException When the reference was already set.
	 */
	public <V extends T> V set(final V value)
	throws ReferenceAlreadySetException {
		// Check that the reference is not set.
		if (_value.isSome()) {
			throw new ReferenceAlreadySetException("Reference already set to " + _value.asSome().getValue());
		}
		
		// Set the value.
		_value = Maybe.<T>some(value);
		
		return value;
	}
	
	/**
	 * Sets the receiver reference according to the given value.
	 * <p>
	 * The reference must not be set when a value is given.
	 * 
	 * @param <V> Type of the value.
	 * @param value The value.
	 * @return The given value.
	 * @throws ReferenceAlreadySetException When the reference has already been set.
	 */
	public <V extends T> Maybe<V> set(final Maybe<V> value)
	throws ReferenceAlreadySetException {
		assert null != value;
		
		// Set.
		if (value.isSome()) {
			set(value.asSome().getValue());
		}
		
		return value;
	}
	
	/**
	 * Resets the receiver reference.
	 * 
	 * @throws ReferenceNotSetException When the reference has not been set.
	 */
	public void reset() {
		_value = Maybe.none();
	}
	
	/**
	 * Updates the receiver reference to the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value The value. May be <code>null</code>.
	 * @return The given value. May be <code>null</code>.
	 */
	public <V extends T> V update(final V value) {
		_value = Maybe.<T>some(value);
		
		return value;
	}
	
	/**
	 * Updates or resets the receiver reference according to the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value The value.
	 * @return The given value.
	 */
	public <V extends T> Maybe<V> update(final Maybe<V> value) {
		assert null != value;
		
		// Update.
		if (value.isSome()) {
			update(value.asSome().getValue());
		} else {
			reset();
		}
		
		return value;
	}
	
	public T get()
	throws ReferenceNotSetException {
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			throw new ReferenceNotSetException("Reference " + this + " is not set");
		}
	}
	
	public Maybe<T> asMaybe() {
		return _value;
	}
	
	public void release() {
		reset();
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
	
	public void fillDescription(final Description description) {
		if (_value.isSome()) {
			description.append("Value", _value.asSome().getValue());
		}
	}
}
