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

import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.observer.LiveObserver;
import com.trazere.util.observer.Observer;
import com.trazere.util.observer.ObserverSubscription;
import com.trazere.util.observer.SimpleObservable;
import com.trazere.util.type.Maybe;

/**
 * The {@link MutableReference} class represents mutable, observable refererences.
 * 
 * @param <T> Type of the referenced values.
 */
public class MutableObservableReference<T>
extends MutableReference<T>
implements ObservableReference<T> {
	/**
	 * Instantiates an unset reference.
	 */
	public MutableObservableReference() {
		super();
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public MutableObservableReference(final T value) {
		super(value);
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value.
	 */
	public MutableObservableReference(final Maybe<T> value) {
		super(value);
	}
	
	// Value.
	
	@Override
	public <V extends T> V set(final V value)
	throws ReferenceAlreadySetException {
		// Set.
		final V result = super.set(value);
		
		// Notify.
		_observable.raise(_value);
		
		return result;
	}
	
	@Override
	public <V extends T> Maybe<V> set(final Maybe<V> value)
	throws ReferenceAlreadySetException {
		// Set.
		final Maybe<V> result = super.set(value);
		
		// Notify.
		if (value.isSome()) {
			_observable.raise(_value);
		}
		
		return result;
	}
	
	@Override
	public void reset() {
		// Reset.
		final Maybe<T> currentValue = _value;
		super.reset();
		
		// Notify.
		if (currentValue.isSome()) {
			_observable.raise(_value);
		}
	}
	
	@Override
	public <V extends T> V update(final V value) {
		// Update.
		final Maybe<T> currentValue = _value;
		final V result = super.update(value);
		
		// Notify.
		if (currentValue.isNone() || !LangUtils.equals(currentValue.asSome().getValue(), value)) {
			_observable.raise(_value);
		}
		
		return result;
	}
	
	@Override
	public <V extends T> Maybe<V> update(final Maybe<V> value) {
		// Update.
		final Maybe<T> currentValue = _value;
		final Maybe<V> result = super.update(value);
		
		// Notify.
		if (!currentValue.equals(value)) {
			_observable.raise(_value);
		}
		
		return result;
	}
	
	// Observer.
	
	protected final SimpleObservable<Maybe<T>> _observable = new SimpleObservable<Maybe<T>>();
	
	public ObserverSubscription subscribe(final LiveObserver<? super Maybe<T>> observer) {
		return _observable.subscribe(observer);
	}
	
	public ObserverSubscription subscribe(final Observer<? super Maybe<T>> observer) {
		return _observable.subscribe(observer);
	}
	
	public ObserverSubscription subscribeOnce(final Observer<? super Maybe<T>> observer) {
		return _observable.subscribeOnce(observer);
	}
	
	public ObserverSubscription subscribeWhile(final Observer<? super Maybe<T>> observer, final Predicate1<? super Maybe<T>, RuntimeException> condition) {
		return _observable.subscribeWhile(observer, condition);
	}
}
