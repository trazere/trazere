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

/**
 * The {@link Observatory} interface defines relays for routing events from publishers to observers which do not know about each other.
 * <p>
 * Publishers and observers are associated using a common subjets.
 * 
 * @param <S> Type of the subjects.
 * @param <E> Type of the events.
 * @deprecated Use {@link com.trazere.core.reactive.Observatory}.
 */
@Deprecated
public interface Observatory<S, E> {
	/**
	 * Gets an observable that allows to subscribe to events routed by the reveiver observatory for the given subject.
	 * 
	 * @param subject The subject.
	 * @return The observable.
	 * @deprecated Use {@link com.trazere.core.reactive.Observatory#observe(Object)}.
	 */
	@Deprecated
	public Observable<E> observe(final S subject);
	
	/**
	 * Gets an observable that allows to subscribe to all events routed by the reveiver observatory.
	 * 
	 * @return The observable.
	 * @deprecated Use {@link com.trazere.core.reactive.Observatory#observeAll()}.
	 */
	@Deprecated
	public Observable<E> observeAll();
	
	/**
	 * Notifies the observers of the events routed by the receiver observatory for the given subject with the given event.
	 * 
	 * @param subject The subject.
	 * @param event The event.
	 * @deprecated Use {@link com.trazere.core.reactive.Observatory#notify(Object, Object)}.
	 */
	@Deprecated
	public void notify(final S subject, final E event);
}
