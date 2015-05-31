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

import java.util.Observable;

/**
 * The {@link Observer} interface defines observers of events raised by {@link Observable observable sources}.
 * 
 * @param <T> Type of the observed event values.
 * @see Observable
 * @deprecated Use {@link com.trazere.core.reactive.Observer}.
 */
@Deprecated
public interface Observer<T> {
	/**
	 * Notifies the receiver observer with the given event.
	 * 
	 * @param value The event value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.reactive.Observer#onEvent(Object)}.
	 */
	// TODO: rename to onEvent(T event)
	@Deprecated
	public void notify(final T value);
}
