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

import com.trazere.util.collection.LazyMap;
import com.trazere.util.function.Predicate1;
import com.trazere.util.type.Maybe;

/**
 * The {@link SimpleObservatory} class provides a simple implementation of observatories.
 * <p>
 * This implementation is thread safe.
 * 
 * @param <S> Type of the subjects.
 * @param <E> Type of the events.
 */
public class SimpleObservatory<S, E>
implements Observatory<S, E> {
	protected final LazyMap<S, SimpleObservable<E>, RuntimeException> _observables = new LazyMap<S, SimpleObservable<E>, RuntimeException>() {
		@Override
		protected SimpleObservable<E> compute(final S subject) {
			return new SimpleObservable<E>() {
				@Override
				protected void unsubscribe(final LiveObserver observer) {
					super.unsubscribe(observer);
					
					// Reset the observable.
					if (!isObserved()) {
						synchronized (_observables) {
							remove(subject);
						}
					}
				}
			};
		}
	};
	
	@Override
	public Observable<E> observe(final S subject) {
		assert null != subject;
		
		return new Observable<E>() {
			@Override
			public ObserverSubscription subscribe(final Observer<? super E> observer) {
				final SimpleObservable<E> observable;
				synchronized (_observables) {
					observable = _observables.get(subject);
				}
				synchronized (observable) {
					return observable.subscribe(observer);
				}
			}
			
			@Override
			public ObserverSubscription subscribeOnce(final Observer<? super E> observer) {
				final SimpleObservable<E> observable;
				synchronized (_observables) {
					observable = _observables.get(subject);
				}
				synchronized (observable) {
					return observable.subscribeOnce(observer);
				}
			}
			
			@Override
			public ObserverSubscription subscribeWhile(final Observer<? super E> observer, final Predicate1<? super E, RuntimeException> condition) {
				final SimpleObservable<E> observable;
				synchronized (_observables) {
					observable = _observables.get(subject);
				}
				synchronized (observable) {
					return observable.subscribeWhile(observer, condition);
				}
			}
		};
	}
	
	@Override
	public void notify(final S subject, final E event) {
		assert null != subject;
		assert null != event;
		
		// Get the observable.
		final Maybe<SimpleObservable<E>> observable;
		synchronized (_observables) {
			observable = _observables.probe(subject);
		}
		
		// Notify.
		if (observable.isSome()) {
			observable.asSome().getValue().notify(event);
		}
	}
}
