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

import com.trazere.core.util.Maybe;

/**
 * The {@link Future} interface defines values that will be available in the future.
 * 
 * @param <T> Type of the value.
 * @since 1.0
 */
public interface Future<T>
extends Observable<T> {
	/**
	 * Subscribes the given observer to this future.
	 * <p>
	 * The observer will be called only once with the value of the future as soon as it becomes available. It is called immediately when the value of the future
	 * is already available.
	 * 
	 * @param observer Observer to subscribe.
	 * @return The resulting subcription.
	 * @since 1.0
	 */
	@Override
	ObserverSubscription subscribe(Observer<? super T> observer);
	
	/**
	 * Indicates whether the value of this future is already available or not.
	 * 
	 * @return <code>true</code> when the value is available, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean isAvailable();
	
	/**
	 * Gets the value of this future.
	 * 
	 * @return The value of the future, or nothing when the value is not yet available.
	 * @since 1.0
	 */
	Maybe<T> get();
}
