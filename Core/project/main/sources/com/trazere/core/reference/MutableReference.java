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
package com.trazere.core.reference;

import com.trazere.core.functional.Thunk;
import com.trazere.core.text.Describable;
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.TextUtils;
import com.trazere.core.util.Maybe;

/**
 * The {@link MutableReference} class implements refererences that can change.
 * <p>
 * This class can be used instead of non-final variables to help tagging side effects.
 * 
 * @param <T> Type of the referenced value.
 * @since 1.0
 */
public class MutableReference<T>
implements ReleasableReference<T>, Describable {
	/**
	 * Instantiates an new unset reference.
	 * 
	 * @since 1.0
	 */
	public MutableReference() {
		this(Maybe.<T>none());
	}
	
	/**
	 * Instantiates a new reference set to the given value.
	 * 
	 * @param value Value to set.
	 * @since 1.0
	 */
	public MutableReference(final T value) {
		this(Maybe.some(value));
	}
	
	/**
	 * Instantiates a new reference set to the given value if any.
	 * 
	 * @param value Value to set.
	 * @since 1.0
	 */
	public MutableReference(final Maybe<T> value) {
		assert null != value;
		
		// Initialization.
		_value = value;
	}
	
	// Value.
	
	/**
	 * Referenced value.
	 * 
	 * @since 1.0
	 */
	protected Maybe<T> _value;
	
	@Override
	public boolean isSet() {
		return _value.isSome();
	}
	
	/**
	 * Sets this reference with the given value.
	 * <p>
	 * The reference must not be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set.
	 * @return The given value.
	 * @throws ReferenceAlreadySetException When the reference is already set.
	 * @since 1.0
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
	 * Sets this reference with the given value if any.
	 * <p>
	 * The reference must not be set when some value is given.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set.
	 * @return The given value.
	 * @throws ReferenceAlreadySetException When the reference is already set.
	 * @since 1.0
	 */
	public <V extends T> Maybe<V> set(final Maybe<V> value)
	throws ReferenceAlreadySetException {
		// Set.
		if (value.isSome()) {
			set(value.asSome().getValue());
		}
		
		return value;
	}
	
	/**
	 * Resets this reference.
	 * 
	 * @since 1.0
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
	 * Updates this reference with the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set.
	 * @return The given value.
	 * @since 1.0
	 */
	public <V extends T> V update(final V value) {
		// Dispose of the current value.
		if (_value.isSome()) {
			dispose(_value.asSome().getValue());
		}
		
		// Set.
		_value = Maybe.<T>some(value);
		
		return value;
	}
	
	/**
	 * Updates or resets this reference according to the given value.
	 * <p>
	 * The reference may already be set.
	 * 
	 * @param <V> Type of the value.
	 * @param value Value to set.
	 * @return The given value.
	 * @since 1.0
	 */
	public <V extends T> Maybe<? extends V> update(final Maybe<? extends V> value) {
		if (value.isSome()) {
			// Update.
			update(value.asSome().getValue());
		} else {
			// Reset.
			reset();
		}
		
		return value;
	}
	
	/**
	 * Disposes of the given value.
	 * <p>
	 * This methods is called when this set reference is reset or replaced. The defaut implementation does nothing.
	 * 
	 * @param value Value to dispose of.
	 * @since 1.0
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
	public T get(final Thunk<? extends T> defaultValue) {
		return _value.get(defaultValue);
	}
	
	/**
	 * Gets the value set in this reference or sets it with the given value.
	 * 
	 * @param value Value to set when the reference is not set.
	 * @return The referenced or set value.
	 * @since 1.0
	 */
	public T getOrSet(final T value) {
		if (_value.isSome()) {
			// Get.
			return _value.asSome().getValue();
		} else {
			// Set.
			_value = Maybe.<T>some(value);
			return value;
		}
	}
	
	/**
	 * Gets the value set in this reference or sets it with the given value.
	 * 
	 * @param value Value to set when the reference is not set.
	 * @return The current or set value.
	 * @since 1.0
	 */
	public T getOrSet(final Thunk<? extends T> value) {
		if (_value.isSome()) {
			// Get.
			return _value.asSome().getValue();
		} else {
			// Set.
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
		return TextUtils.description(this);
	}
	
	@Override
	public void appendDescription(final DescriptionBuilder description) {
		if (_value.isSome()) {
			description.append("Value", _value.asSome().getValue());
		}
	}
}
