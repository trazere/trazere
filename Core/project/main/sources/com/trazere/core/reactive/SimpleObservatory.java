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

import com.trazere.core.functional.BaseResettableFunction;
import com.trazere.core.functional.ResettableFunction;
import com.trazere.core.reactive.Broadcaster.BroadcasterObservable;
import com.trazere.core.util.Maybe;
import org.slf4j.Logger;

/**
 * The {@link SimpleObservatory} class provides a simple implementation of observatories.
 * <p>
 * This implementation is thread safe.
 * 
 * @param <S> Type of the subjects.
 * @param <E> Type of the events.
 * @since 2.0
 */
public class SimpleObservatory<S, E>
implements Observatory<S, E> {
	/**
	 * Logger to use.
	 * 
	 * @since 2.0
	 */
	protected final Logger _logger;
	
	/**
	 * Instantiates a new observatory.
	 * 
	 * @since 2.0
	 */
	public SimpleObservatory() {
		this(ObservableUtils.LOGGER);
	}
	
	/**
	 * Instantiates a new observatory.
	 * 
	 * @param logger Logger to use.
	 * @since 2.0
	 */
	public SimpleObservatory(final Logger logger) {
		assert null != logger;
		
		// Initialization.
		_logger = logger;
		_allBroadcaster = new Broadcaster<>(_logger);
	}
	
	/**
	 * Event broadcasters by subjects.
	 * 
	 * @since 2.0
	 */
	protected final ResettableFunction<S, Broadcaster<E>> _subjectBroadcasters = new BaseResettableFunction<S, Broadcaster<E>>() {
		@Override
		protected Broadcaster<E> compute(final S subject) {
			return new Broadcaster<>(new BroadcasterObservable<E>(_logger) {
				@Override
				protected synchronized void unsubscribe(final BaseObservable<E>.ObserverRef observer) {
					super.unsubscribe(observer);
					
					// Reset the broadcaster if not observed anymore.
					if (!isObserved()) {
						synchronized (_subjectBroadcasters) {
							_subjectBroadcasters.reset(subject);
						}
					}
				}
				
				@Override
				protected synchronized void unsubscribeAll() {
					super.unsubscribeAll();
					
					// Reset the broadcaster.
					synchronized (_subjectBroadcasters) {
						_subjectBroadcasters.reset(subject);
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
				synchronized (_subjectBroadcasters) {
					broadcaster = _subjectBroadcasters.evaluate(subject);
				}
				synchronized (broadcaster) {
					return broadcaster.getObservable().subscribe(observer);
				}
			}
		};
	}
	
	/**
	 * Event broadcaster for all subjects.
	 * 
	 * @since 2.0
	 */
	protected final Broadcaster<E> _allBroadcaster;
	
	@Override
	public Observable<E> observeAll() {
		return _allBroadcaster.getObservable();
	}
	
	@Override
	public void notify(final S subject, final E event) {
		assert null != subject;
		assert null != event;
		
		// Get the observable.
		final Maybe<Broadcaster<E>> subjectBroadcaster;
		synchronized (_subjectBroadcasters) {
			subjectBroadcaster = _subjectBroadcasters.probe(subject);
		}
		
		// Notify.
		subjectBroadcaster.foreach(broadcaster -> {
			broadcaster.fire(event);
		});
		
		// Notify for all.
		_allBroadcaster.fire(event);
	}
}
