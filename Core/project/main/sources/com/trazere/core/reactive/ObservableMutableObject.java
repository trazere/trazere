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

import com.trazere.core.lang.MutableObject;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.util.Tuple2;

/**
 * The {@link ObservableMutableObject} class represents mutable object values whose changes can be observed.
 * 
 * @param <T> Type of the value.
 * @since 2.0
 */
public class ObservableMutableObject<T>
extends MutableObject<T>
implements ObservableValue<T> {
	/**
	 * Instantiates a new observable object with the given initial value.
	 * 
	 * @param value Initial value.
	 * @since 2.0
	 */
	public ObservableMutableObject(final T value) {
		this(value, new Broadcaster<T>());
	}
	
	/**
	 * Instantiates a new observable object with the given initial value.
	 * 
	 * @param value Initial value.
	 * @param broadcaster Broadcaster to use to fire the change events.
	 * @since 2.0
	 */
	public ObservableMutableObject(final T value, final Broadcaster<T> broadcaster) {
		super(value);
		
		// Checks.
		assert null != broadcaster;
		
		// Initialization.
		_broadcaster = broadcaster;
	}
	
	// Value.
	
	@Override
	public <V extends T> V set(final V value) {
		// Update.
		final T currentValue = _value;
		final V result = super.set(value);
		
		// Notify.
		if (!ObjectUtils.safeEquals(currentValue, value)) {
			_broadcaster.fire(_value);
		}
		
		return result;
	}
	
	// Observable.
	
	/**
	 * Broadcaster to use to fire the change events.
	 * 
	 * @since 2.0
	 */
	protected final Broadcaster<T> _broadcaster;
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		return _broadcaster.getObservable().subscribe(observer);
	}
	
	@Override
	public Tuple2<T, ObserverSubscription> subscribeToValue(final Observer<? super T> observer) {
		return new Tuple2<>(get(), subscribe(observer));
	}
}
