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
package com.trazere.core.time;

import java.time.Duration;
import java.time.Instant;

/**
 * The {@link Timer} class allows to measure time.
 * 
 * @since 1.0
 */
public class Timer {
	/**
	 * Starts a new timer.
	 * 
	 * @return The built timer.
	 * @since 1.0
	 */
	public static Timer start() {
		return new Timer();
	}
	
	/** Instant the timer was created. */
	private final Instant _start;
	
	/**
	 * Instantiates a new timer.
	 * 
	 * @since 1.0
	 */
	public Timer() {
		_start = Instant.now();
	}
	
	/**
	 * Gets the elapsed time since the creation of this timer.
	 * 
	 * @return The elapsed duration.
	 * @since 1.0
	 */
	public Duration read() {
		return Duration.between(_start, Instant.now());
	}
}
