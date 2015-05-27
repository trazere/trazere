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
package com.trazere.core.lang;

import com.trazere.core.util.Maybe;
import java.time.Duration;

/**
 * The {@link LangUtils} class provides various utilities regarding threads.
 */
public class ThreadUtils {
	/**
	 * Puts the current thread to sleep for the given amount of time.
	 * <p>
	 * This method throws an exception when the sleep is interrupted.
	 * 
	 * @param timeout Timeout.
	 * @param throwableFactory Exception factory to use for interruptions.
	 * @throws RuntimeException When the sleep is interrupted.
	 * @see Thread#sleep(long)
	 */
	public static void sleep(final Duration timeout, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		try {
			Thread.sleep(timeout.toMillis());
		} catch (final InterruptedException exception) {
			throw throwableFactory.build(exception);
		}
	}
	
	/**
	 * Puts the current thread to sleep for the given amount of time or until the given object is notified.
	 * <p>
	 * This method throws an exception when the sleep is interrupted.
	 * 
	 * @param object Object to monitor.
	 * @param timeout Timeout.
	 * @param throwableFactory Exception factory to use for interruptions.
	 * @throws RuntimeException When the sleep is interrupted.
	 * @see Object#wait(long)
	 */
	public static void wait(final Object object, final Duration timeout, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		try {
			synchronized (object) {
				object.wait(timeout.toMillis());
			}
		} catch (final InterruptedException exception) {
			throw throwableFactory.build(exception);
		}
	}
	
	/**
	 * Puts the current thread to sleep for the given amount of time or until the given object is notified.
	 * <p>
	 * This method throws an exception when the sleep is interrupted.
	 * 
	 * @param object Object to monitor.
	 * @param timeout Timeout.
	 * @param throwableFactory Exception factory to use for interruptions.
	 * @throws RuntimeException When the sleep is interrupted.
	 * @see #wait(Object, Duration, ThrowableFactory)
	 * @see #sleep(Duration, ThrowableFactory)
	 */
	public static void waitOrSleep(final Maybe<?> object, final Duration timeout, final ThrowableFactory<? extends RuntimeException> throwableFactory) {
		if (object.isSome()) {
			wait(object.asSome().getValue(), timeout, throwableFactory);
		} else {
			sleep(timeout, throwableFactory);
		}
	}
	
	private ThreadUtils() {
		// Prevent instantiation.
	}
}
