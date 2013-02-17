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
package com.trazere.util.lang;

/**
 * The {@link Timer} class allows to measure time.
 */
public class Timer {
	/**
	 * Builds a new timer.
	 * 
	 * @return The timer.
	 */
	public static Timer build() {
		return new Timer();
	}
	
	/** The time the timer was started. */
	private final long _start;
	
	/**
	 * Instantiates a new timer.
	 */
	public Timer() {
		_start = System.currentTimeMillis();
	}
	
	/**
	 * Gets the elapsed time since the creation of the receiver timer.
	 * 
	 * @return The elapsed number of milliseconds.
	 * @see System#currentTimeMillis()
	 */
	public long read() {
		return System.currentTimeMillis() - _start;
	}
}
