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
package com.trazere.core.reactive;

import com.trazere.core.reference.ReferenceAlreadySetException;

// TODO: subclass Future ?
// TODO: add some observer factory that forwards to a broadcaster

/**
 * The {@link Promise} class implements placeholders for values that will be set in the future.
 * 
 * @param <T> Type of the value.
 */
public class Promise<T> {
	/**
	 * Instantiates a new promise.
	 */
	public Promise() {
		this(new PromiseFuture<>());
	}
	
	/**
	 * Instantiates a new promise.
	 * 
	 * @param future Future providing the value.
	 */
	protected Promise(final PromiseFuture<T> future) {
		assert null != future;
		
		// Initialization.
		_future = future;
	}
	
	// Future.
	
	/**
	 * The {@link Promise.PromiseFuture} class implements futures of promises.
	 * 
	 * @param <T> Type of the value.
	 */
	protected static class PromiseFuture<T>
	extends BaseFuture<T> {
		// Nothing to do.
	}
	
	/** Future providing the value. */
	protected final PromiseFuture<T> _future;
	
	/**
	 * Gets the future providing the value of this promise.
	 * 
	 * @return The future.
	 */
	public Future<T> getFuture() {
		return _future;
	}
	
	// Value.
	
	/**
	 * Indicates whether this promise has been fulfilled (ie, the value of the promise has been set) or not.
	 * 
	 * @return <code>true</code> when the pro
	 */
	public boolean isFulfilled() {
		return _future.isAvailable();
	}
	
	/**
	 * Fulfils this promise with the given value.
	 * <p>
	 * This method fails when the promise has already been fulfilled.
	 * 
	 * @param value Value with which the promise should be fulfilled.
	 * @throws ReferenceAlreadySetException When the promise has already been fulfilled.
	 */
	public void fulfil(final T value) {
		_future.set(value);
	}
	
	/**
	 * Fulfils this promise with the next event value raised by the given observable.
	 * <p>
	 * This observer fails when the promise has already been fulfilled when notified.
	 * 
	 * @param observable Observable providing the value with which the promise should be fulfilled.
	 * @return The subscription.
	 */
	public ObserverSubscription fulfil(final Observable<? extends T> observable) {
		return observable.subscribe((final T value) -> {
			fulfil(value);
			return true;
		});
	}
	
	/**
	 * Fulfils this promise with the given value if it has not been fulfilled yet.
	 * 
	 * @param value Value with which the promise should be fulfilled.
	 * @return <code>true</code> when the promise has been fulfilled with the value, <code>false</code> when it was already fulfilled.
	 */
	public boolean fulfilIfNot(final T value) {
		return _future.setIfNot(value);
	}
	
	/**
	 * Fulfils this promise with the next event value raised by the given observable if it has not been fulfilled yet.
	 * 
	 * @param observable Observable providing the value with which the promise should be fulfilled.
	 * @return The subscription.
	 */
	public ObserverSubscription fulfilIfNot(final Observable<? extends T> observable) {
		return observable.subscribe((final T value) -> {
			fulfilIfNot(value);
			return true;
		});
	}
}
