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
package com.trazere.util.observer;

import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.reference.MutableReference;
import com.trazere.util.reference.ReferenceAlreadySetException;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;

/**
 * The {@link MutableReference} class represents mutable, observable refererences.
 * 
 * @param <T> Type of the referenced values.
 * @deprecated Use {@link com.trazere.core.reactive.ObservableMutableReference}.
 */
@Deprecated
public class ObservableMutableReference<T>
extends MutableReference<T>
implements ObservableReference<T> {
	/**
	 * Instantiates an unset reference.
	 */
	public ObservableMutableReference() {
		super();
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public ObservableMutableReference(final T value) {
		super(value);
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value.
	 */
	public ObservableMutableReference(final Maybe<T> value) {
		super(value);
	}
	
	// Value.
	
	@Override
	public <V extends T> V set(final V value)
	throws ReferenceAlreadySetException {
		// Set.
		final V result = super.set(value);
		
		// Notify.
		_observable.notify(_value);
		
		return result;
	}
	
	@Override
	public void reset() {
		// Reset.
		final Maybe<T> currentValue = _value;
		super.reset();
		
		// Notify.
		if (currentValue.isSome()) {
			_observable.notify(_value);
		}
	}
	
	@Override
	public <V extends T> V update(final V value) {
		// Update.
		final Maybe<T> currentValue = _value;
		final V result = super.update(value);
		
		// Notify.
		if (currentValue.isNone() || !LangUtils.safeEquals(currentValue.asSome().getValue(), value)) {
			_observable.notify(_value);
		}
		
		return result;
	}
	
	// Observer.
	
	protected final SimpleObservable<Maybe<T>> _observable = buildObservable();
	
	/**
	 * @deprecated To be removed.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	protected SimpleObservable<Maybe<T>> buildObservable() {
		return new SimpleObservable<Maybe<T>>();
	}
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super Maybe<T>> observer) {
		return _observable.subscribe(observer);
	}
	
	@Override
	public ObserverSubscription subscribeOnce(final Observer<? super Maybe<T>> observer) {
		return _observable.subscribeOnce(observer);
	}
	
	@Override
	public ObserverSubscription subscribeWhile(final Observer<? super Maybe<T>> observer, final Predicate1<? super Maybe<T>, RuntimeException> condition) {
		return _observable.subscribeWhile(observer, condition);
	}
	
	@Override
	public ObserverSubscription subscribeAndNotify(final Observer<? super Maybe<T>> observer) {
		assert null != observer;
		
		final ObserverSubscription subscription = subscribe(observer);
		observer.notify(asMaybe());
		return subscription;
	}
	
	@Override
	public Tuple2<Maybe<T>, ObserverSubscription> subscribeToValue(final Observer<? super Maybe<T>> observer) {
		return Tuple2.build(asMaybe(), subscribe(observer));
	}
	
	@Override
	public Tuple2<Maybe<T>, ObserverSubscription> subscribeOnceToValue(final Observer<? super Maybe<T>> observer) {
		return Tuple2.build(asMaybe(), subscribeOnce(observer));
	}
	
	@Override
	public Tuple2<Maybe<T>, ObserverSubscription> subscribeWhileToValue(final Observer<? super Maybe<T>> observer, final Predicate1<? super Maybe<T>, RuntimeException> condition) {
		return Tuple2.build(asMaybe(), subscribeWhile(observer, condition));
	}
}
