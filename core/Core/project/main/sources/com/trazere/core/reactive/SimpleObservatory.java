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

import com.trazere.core.functional.ResettableFunction;
import com.trazere.core.reactive.Broadcaster.BroadcasterObservable;
import com.trazere.core.util.Maybe;

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
	protected final ResettableFunction<S, Broadcaster<E>> _broadcasters = new ResettableFunction<S, Broadcaster<E>>() {
		@Override
		protected Broadcaster<E> compute(final S subject) {
			return new Broadcaster<>(new BroadcasterObservable<E>() {
				@Override
				protected synchronized void unsubscribe(final BaseObservable<E>.ObserverRef observer) {
					super.unsubscribe(observer);
					
					// Reset the broadcaster if not observed anymore.
					if (!isObserved()) {
						synchronized (_broadcasters) {
							_broadcasters.reset(subject);
						}
					}
				}
				
				@Override
				protected synchronized void unsubscribeAll() {
					super.unsubscribeAll();
					
					// Reset the broadcaster.
					synchronized (_broadcasters) {
						_broadcasters.reset(subject);
					}
				}
			});
		}
	};
	
	@Override
	public Observable<E> observe(final S subject) {
		assert null != subject;
		
		return new Observable<E>() {
			@Override
			public ObserverSubscription subscribe(final Observer<? super E> observer) {
				final Broadcaster<E> broadcaster;
				synchronized (_broadcasters) {
					broadcaster = _broadcasters.evaluate(subject);
				}
				synchronized (broadcaster) {
					return broadcaster.getObservable().subscribe(observer);
				}
			}
		};
	}
	
	@Override
	public void notify(final S subject, final E event) {
		assert null != subject;
		assert null != event;
		
		// Get the observable.
		final Maybe<Broadcaster<E>> broadcaster;
		synchronized (_broadcasters) {
			broadcaster = _broadcasters.get(subject);
		}
		
		// Notify.
		if (broadcaster.isSome()) {
			broadcaster.asSome().getValue().fire(event);
		}
	}
}
