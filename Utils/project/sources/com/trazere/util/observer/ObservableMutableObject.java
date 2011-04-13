/*
 *  Copyright 2006-2011 Julien Dufour
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
import com.trazere.util.lang.MutableObject;

/**
 * The {@link ObservableMutableObject} class represents mutable object values whose changes can be observed.
 * 
 * @param <T> Type of the value.
 */
public class ObservableMutableObject<T>
extends MutableObject<T>
implements Observable<T> {
	/**
	 * Instantiates a new observable object with the given initial value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public ObservableMutableObject(final T value) {
		super(value);
	}
	
	// Observer.
	
	protected final SimpleObservable<T> _observable = new SimpleObservable<T>();
	
	public ObserverSubscription subscribe(final Observer<? super T> observer) {
		return _observable.subscribe(observer);
	}
	
	public ObserverSubscription subscribeOnce(final Observer<? super T> observer) {
		return _observable.subscribeOnce(observer);
	}
	
	public ObserverSubscription subscribeWhile(final Observer<? super T> observer, final Predicate1<? super T, RuntimeException> condition) {
		return _observable.subscribeWhile(observer, condition);
	}
}
