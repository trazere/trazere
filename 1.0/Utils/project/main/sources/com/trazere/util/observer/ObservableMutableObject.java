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
package com.trazere.util.observer;

import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.lang.MutableObject;
import com.trazere.util.type.Tuple2;

/**
 * The {@link ObservableMutableObject} class represents mutable object values whose changes can be observed.
 * 
 * @param <T> Type of the value.
 * @deprecated Use {@link com.trazere.core.reactive.ObservableMutableObject}.
 */
@Deprecated
public class ObservableMutableObject<T>
extends MutableObject<T>
implements ObservableValue<T> {
	/**
	 * Instantiates a new observable object with the given initial value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public ObservableMutableObject(final T value) {
		super(value);
	}
	
	// Value.
	
	@Override
	public <V extends T> V set(final V value) {
		// Update.
		final T currentValue = _value;
		final V result = super.set(value);
		
		// Notify.
		if (!LangUtils.safeEquals(currentValue, value)) {
			_observable.notify(_value);
		}
		
		return result;
	}
	
	// Observer.
	
	protected final SimpleObservable<T> _observable = buildObservable();
	
	protected SimpleObservable<T> buildObservable() {
		return new SimpleObservable<T>();
	}
	
	@Override
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		return _observable.subscribe(observer);
	}
	
	@Override
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer) {
		return _observable.subscribeOnce(observer);
	}
	
	@Override
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition) {
		return _observable.subscribeWhile(observer, condition);
	}
	
	@Override
	public ObserverSubscription subscribeAndNotify(final Observer<? super T> observer) {
		assert null != observer;
		
		final ObserverSubscription subscription = subscribe(observer);
		observer.notify(get());
		return subscription;
	}
	
	@Override
	public Tuple2<T, ObserverSubscription> subscribeToValue(final Observer<? super T> observer) {
		return Tuple2.build(get(), subscribe(observer));
	}
	
	@Override
	public Tuple2<T, ObserverSubscription> subscribeOnceToValue(final Observer<? super T> observer) {
		return Tuple2.build(get(), subscribeOnce(observer));
	}
	
	@Override
	public Tuple2<T, ObserverSubscription> subscribeWhileToValue(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition) {
		return Tuple2.build(get(), subscribeWhile(observer, condition));
	}
}
