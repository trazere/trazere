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

import com.trazere.core.reference.MutableReference;
import com.trazere.core.reference.ReferenceAlreadySetException;
import com.trazere.core.util.Maybe;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link BaseFuture} class provides a skeleton implementation of {@link Future futures}.
 * <p>
 * Note that this implementation cancels all subscriptions once the value of the future is set. Therefore, {@link #isObserved()} will always return
 * <code>false</code> when {@link #isAvailable()} returns <code>true</code>.
 * 
 * @param <T> Type of the value.
 * @since 2.0
 */
public abstract class BaseFuture<T>
extends BaseObservable<T>
implements Future<T> {
	// Observers.
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		final Maybe<T> value;
		synchronized (this) {
			// Get the value.
			value = get();
			
			if (value.isNone()) {
				// Subscribe the observer value is pending.
				return super.subscribe(observer);
			}
		}
		
		// Notify immediately when value is available.
		// Note: avoid holding the lock while notifying to prevent possible deadlocks.
		notify(observer, value.asSome().getValue());
		
		// Build an nop subscription.
		return ObserverSubscriptions.nop();
	}
	
	// Value.
	
	/**
	 * Value of the future.
	 * 
	 * @since 2.0
	 */
	protected final MutableReference<T> _value = new MutableReference<>();
	
	@Override
	public synchronized boolean isAvailable() {
		return _value.isSet();
	}
	
	@Override
	public synchronized Maybe<T> get() {
		return _value.asMaybe();
	}
	
	/**
	 * Sets the value of this future.
	 * <p>
	 * This method fails when the value of the future has already been set.
	 * 
	 * @param value Value to set.
	 * @throws ReferenceAlreadySetException When the value of the future is already set.
	 * @since 2.0
	 */
	protected void set(final T value) {
		final List<ObserverRef> observers;
		synchronized (this) {
			// Set the value.
			_value.set(value);
			
			// Note: copy the observers for thread safety.
			observers = new ArrayList<>(_observers);
			
			// Unsubscribe all observers.
			unsubscribeAll();
		}
		
		// Notify the observers.
		// Note: avoid holding the lock while notifying to prevent possible deadlocks.
		for (final ObserverRef observer : observers) {
			// Note: ignore the result because all subscriptions have been cleared anyway.
			observer.notify(value);
		}
	}
	
	/**
	 * Sets the value of this future if it has not already been set.
	 * 
	 * @param value Value to set.
	 * @return <code>true</code> when the value has been set, <code>false</code> when the value was already set.
	 * @since 2.0
	 */
	protected boolean setIfNot(final T value) {
		final List<ObserverRef> observers;
		synchronized (this) {
			// Check that the value is not already set.
			if (_value.isSet()) {
				return false;
			}
			
			// Set the value.
			_value.set(value);
			
			// Note: copy the observers for thread safety.
			observers = new ArrayList<>(_observers);
			
			// Unsubscribe all observers.
			unsubscribeAll();
		}
		
		// Notify the observers.
		// Note: avoid holding the lock while notifying to prevent possible deadlocks.
		for (final ObserverRef observer : observers) {
			// Note: ignore the result because all subscriptions have been cleared anyway.
			observer.notify(value);
		}
		
		return true;
	}
}
