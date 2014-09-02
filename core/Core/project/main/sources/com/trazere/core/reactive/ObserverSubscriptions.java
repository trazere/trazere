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

/**
 * The {@link ObserverSubscriptions} class provides various factories of {@link ObserverSubscription observer subscriptions}.
 */
public class ObserverSubscriptions {
	/**
	 * Builds an observer subscription that does nothing.
	 * 
	 * @return The built subscription.
	 */
	public static ObserverSubscription nop() {
		return NOP;
	}
	
	private static final ObserverSubscription NOP = () -> {
		// Nothing to do.
	};
	
	private ObserverSubscriptions() {
		// Prevent instantiation.
	}
}
