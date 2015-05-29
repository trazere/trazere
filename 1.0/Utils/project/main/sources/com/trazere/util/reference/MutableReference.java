/*
 *  Copyright 2006-2013 Julien Dufour
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
package com.trazere.util.reference;

import com.trazere.util.function.Function0;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;

/**
 * The {@link MutableReference} class represents mutable refererences.
 * 
 * @param <T> Type of the referenced values.
 * @deprecated Use {@link com.trazere.core.reference.MutableReference}.
 */
@Deprecated
public class MutableReference<T>
implements ReleasableReference<T, RuntimeException>, Describable {
	/**
	 * Instantiates an unset reference.
	 */
	public MutableReference() {
		this(Maybe.<T>none());
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 */
	public MutableReference(final T value) {
		this(Maybe.some(value));
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value Value to set.
	 */
	public MutableReference(final Maybe<T> value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
	// Value.
	
	/** Referenced value. */
	protected Maybe<T> _value;
	
	@Override
	public boolean isSet() {
		return _value.isSome();
	}
	
	/**
	 * Sets the receiver reference with the given value.
	 * <p>
	 * The reference must not be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set. May be <code>null</code>.
	 * @return The given value. May be <code>null</code>.
	 * @throws ReferenceAlreadySetException When the reference is already set.
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
	 * Sets the receiver reference with the given value if any.
	 * <p>
	 * The reference must not be set when a value is given.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set.
	 * @return The given value.
	 * @throws ReferenceAlreadySetException When the reference is already been set.
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
	 */
	public void reset() {
		if (_value.isSome()) {
			// Dispose.
			dispose(_value.asSome().getValue());
			
			// Reset.
			_value = Maybe.none();
		}
	}
	
	/**
	 * Updates the receiver reference with the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set. May be <code>null</code>.
	 * @return The given value. May be <code>null</code>.
	 */
	public <V extends T> V update(final V value) {
		// Dispose the current value.
		if (_value.isSome()) {
			dispose(_value.asSome().getValue());
		}
		
		// Set.
		_value = Maybe.<T>some(value);
		
		return value;
	}
	
	/**
	 * Updates or resets the receiver reference according to the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set.
	 * @return The given value.
	 */
	public <V extends T> Maybe<V> update(final Maybe<V> value) {
		assert null != value;
		
		if (value.isSome()) {
			update(value.asSome().getValue());
		} else {
			reset();
		}
		
		return value;
	}
	
	/**
	 * Disposes the given current value of the receiver reference.
	 * <p>
	 * This methods is called when the receiver set reference is reset or updated. The defaut implementation does nothing.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	protected void dispose(final T value) {
		// Nothing to do.
	}
	
	@Override
	public T get()
	throws ReferenceNotSetException {
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			throw new ReferenceNotSetException("Reference " + this + " is not set");
		}
	}
	
	@Override
	public T get(final T defaultValue) {
		return _value.get(defaultValue);
	}
	
	@Override
	public <X extends Exception> T get(final Function0<? extends T, X> defaultValue)
	throws X {
		return _value.get(defaultValue);
	}
	
	/**
	 * Gets the value of the receiver reference if any or sets the reference with the given value.
	 * 
	 * @param <X> Type of the value evaluation exceptions.
	 * @param value Value to set. Maybe <code>null</code>.
	 * @return The current or set value. Maybe <code>null</code>.
	 * @throws X When the value cannot be evaluated.
	 */
	public <X extends Exception> T getOrSet(final T value)
	throws X {
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			_value = Maybe.<T>some(value);
			return value;
		}
	}
	
	/**
	 * Gets the value of the receiver reference if any or sets the reference with the given value.
	 * 
	 * @param <X> Type of the value evaluation exceptions.
	 * @param value Value to set.
	 * @return The current or set value.
	 * @throws X When the value cannot be evaluated.
	 * @deprecated Use {@link com.trazere.core.reference.MutableReference#getOrSet(com.trazere.core.functional.Thunk)}.
	 */
	@Deprecated
	public <X extends Exception> T getOrSet(final Function0<? extends T, X> value)
	throws X {
		assert null != value;
		
		if (_value.isSome()) {
			return _value.asSome().getValue();
		} else {
			final T value_ = value.evaluate();
			_value = Maybe.<T>some(value_);
			return value_;
		}
	}
	
	@Override
	public Maybe<T> asMaybe() {
		return _value;
	}
	
	// Releasable.
	
	@Override
	public void release() {
		reset();
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	@Override
	public void fillDescription(final Description description) {
		if (_value.isSome()) {
			description.append("Value", _value.asSome().getValue());
		}
	}
}
