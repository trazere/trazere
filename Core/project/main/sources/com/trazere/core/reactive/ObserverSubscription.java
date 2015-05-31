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

import com.trazere.core.lang.Releasable;

// TODO: extends/replace by Cancellable ?

/**
 * The {@link ObserverSubscription} interface defines subscriptions of {@link Observer observers} to {@link Observable observables}.
 * <p>
 * Observer subscriptions must keep a strong reference to the subscribed observers so that they can not be garbaged collected while their corresponding
 * subscription is alive. That allows the subscribers to prevent the subscriptions from being automatically cancelled without having to reference both the
 * observer and the subscription.
 * 
 * @see Observable
 * @see Observer
 * @since 1.0
 */
@FunctionalInterface
public interface ObserverSubscription
extends Releasable {
	/**
	 * Cancels this subscription.
	 * 
	 * @since 1.0
	 */
	void unsubscribe();
	
	// Releasable.
	
	@Override
	default void release() {
		unsubscribe();
	}
}
