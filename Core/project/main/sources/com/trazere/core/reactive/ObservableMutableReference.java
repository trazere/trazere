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
package com.trazere.core.reactive;

import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.reference.MutableReference;
import com.trazere.core.reference.ReferenceAlreadySetException;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Tuple2;

/**
 * The {@link ObservableMutableReference} class represents mutable, observable refererences.
 * 
 * @param <T> Type of the referenced values.
 * @since 2.0
 */
public class ObservableMutableReference<T>
extends MutableReference<T>
implements ObservableReference<T> {
	/**
	 * Instantiates an unset reference.
	 * 
	 * @since 2.0
	 */
	public ObservableMutableReference() {
		this(new Broadcaster<>());
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value Value to set.
	 * @since 2.0
	 */
	public ObservableMutableReference(final T value) {
		this(value, new Broadcaster<>());
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value Value to set.
	 * @since 2.0
	 */
	public ObservableMutableReference(final Maybe<T> value) {
		this(value, new Broadcaster<>());
	}
	
	/**
	 * Instantiates an unset reference.
	 * 
	 * @param broadcaster Broadcaster to use to fire the change events.
	 * @since 2.0
	 */
	public ObservableMutableReference(final Broadcaster<Maybe<T>> broadcaster) {
		super();
		
		// Checks.
		assert null != broadcaster;
		
		// Initialization.
		_broadcaster = broadcaster;
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value Value to set.
	 * @param broadcaster Broadcaster to use to fire the change events.
	 * @since 2.0
	 */
	public ObservableMutableReference(final T value, final Broadcaster<Maybe<T>> broadcaster) {
		super(value);
		
		// Checks.
		assert null != broadcaster;
		
		// Initialization.
		_broadcaster = broadcaster;
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value Value to set.
	 * @param broadcaster Broadcaster to use to fire the change events.
	 * @since 2.0
	 */
	public ObservableMutableReference(final Maybe<T> value, final Broadcaster<Maybe<T>> broadcaster) {
		super(value);
		
		// Checks.
		assert null != broadcaster;
		
		// Initialization.
		_broadcaster = broadcaster;
	}
	
	// Value.
	
	@Override
	public <V extends T> V set(final V value)
	throws ReferenceAlreadySetException {
		// Set.
		final V result = super.set(value);
		
		// Notify.
		_broadcaster.fire(_value);
		
		return result;
	}
	
	@Override
	public void reset() {
		// Reset.
		final Maybe<T> currentValue = _value;
		super.reset();
		
		// Notify.
		if (currentValue.isSome()) {
			_broadcaster.fire(_value);
		}
	}
	
	@Override
	public <V extends T> V update(final V value) {
		// Update.
		final Maybe<T> currentValue = _value;
		final V result = super.update(value);
		
		// Notify.
		if (currentValue.isNone() || !ObjectUtils.safeEquals(currentValue.asSome().getValue(), value)) {
			_broadcaster.fire(_value);
		}
		
		return result;
	}
	
	// Observer.
	
	/**
	 * Broadcaster to use to fire the change events.
	 * 
	 * @since 2.0
	 */
	protected final Broadcaster<Maybe<T>> _broadcaster;
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super Maybe<T>> observer) {
		return _broadcaster.getObservable().subscribe(observer);
	}
	
	@Override
	public Tuple2<Maybe<T>, ObserverSubscription> subscribeToValue(final Observer<? super Maybe<T>> observer) {
		return new Tuple2<>(asMaybe(), subscribe(observer));
	}
}
