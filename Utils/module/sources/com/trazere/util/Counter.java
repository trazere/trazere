/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util;

/**
 * The {@link Counter} class represents integer counters.
 */
public class Counter {
	/** Start value of the counter. */
	protected final int _start;
	
	/** Increment of the counter. */
	protected final int _increment;
	
	/** Current value of the counter. */
	protected int _value;
	
	/**
	 * Instantiate a new counter starting at <code>0</code> and incrementing by <code>1</code>.
	 */
	public Counter() {
		this(0, 1);
	}
	
	/**
	 * Instantiate a new counter starting at the given value and incrementing by the given increment.
	 * 
	 * @param start Start value of the counter.
	 * @param increment Increment of the counter.
	 */
	public Counter(final int start, final int increment) {
		_start = start;
		_increment = increment;
		_value = start;
	}
	
	/**
	 * Get the start value of the receiver counter.
	 * 
	 * @return The start value.
	 */
	public int getStart() {
		return _start;
	}
	
	/**
	 * Get the increment of the receiver counter.
	 * 
	 * @return The increment.
	 */
	public int getIncrement() {
		return _increment;
	}
	
	/**
	 * Reset the receiver counter to its start value.
	 */
	public void reset() {
		_value = _start;
	}
	
	/**
	 * Increment the receiver counter.
	 */
	public void inc() {
		_value += _increment;
	}
	
	/**
	 * Get the current value of the given counter.
	 * 
	 * @return The current value.
	 */
	public int get() {
		return _value;
	}
	
	/**
	 * Indicates wether the value of the receiver counter is the start value.
	 * 
	 * @return <code>true</code> when the value if the start value, <code>false</code> otherwise.
	 */
	public boolean isStart() {
		return _start == _value;
	}
}