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

import com.trazere.core.reference.MutableReference;

/**
 * The {@link ObserverSubscriptionMutableReference} class implements mutable references of {@link ObserverSubscription observer subscriptions}.
 * <p>
 * The referenced subscriptions are cancelled when the reference is modified.
 * 
 * @since 1.0
 */
public class ObserverSubscriptionMutableReference
extends MutableReference<ObserverSubscription> {
	@Override
	protected void dispose(final ObserverSubscription subscription) {
		subscription.unsubscribe();
	}
}
