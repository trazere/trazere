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
 * The {@link Counter} class represents counters.
 */
public class Counter {
	/** Start value. */
	protected final int _start;
	
	/** Increment. */
	protected final int _increment;
	
	/** Current value. */
	protected int _value;
	
	/**
	 * Instantiates a new counter starting at <code>0</code> and incrementing by <code>1</code>.
	 */
	public Counter() {
		this(0, 1);
	}
	
	/**
	 * Instantiates a new counter.
	 * 
	 * @param start Start value.
	 * @param increment Increment.
	 */
	public Counter(final int start, final int increment) {
		_start = start;
		_increment = increment;
		_value = start;
	}
	
	/**
	 * Gets the start value of the receiver counter.
	 * 
	 * @return The start value.
	 */
	public int getStart() {
		return _start;
	}
	
	/**
	 * Gets the increment of the receiver counter.
	 * 
	 * @return The increment.
	 */
	public int getIncrement() {
		return _increment;
	}
	
	/**
	 * Gets the current value of the given counter.
	 * 
	 * @return The current value.
	 */
	public int get() {
		return _value;
	}
	
	/**
	 * Indicates whether the value of the receiver counter is the start value.
	 * 
	 * @return <code>true</code> when the value is the start value, <code>false</code> otherwise.
	 */
	public boolean isStart() {
		return _start == _value;
	}
	
	/**
	 * Resets the receiver counter to its start value.
	 */
	public void reset() {
		_value = _start;
	}
	
	/**
	 * Increments the receiver counter.
	 * 
	 * @return The incremented value.
	 */
	public int inc() {
		_value += _increment;
		return _value;
	}
	
	/**
	 * Increments the receiver counter <code>n</code> times.
	 * 
	 * @param n Number of times to increment the counter.
	 * @return The incremented value.
	 */
	public int inc(final int n) {
		_value += _increment * n;
		return _value;
	}
	
	/**
	 * Decrements the receiver counter.
	 * 
	 * @return The decremented value.
	 */
	public int dec() {
		_value -= _increment;
		return _value;
	}
	
	/**
	 * Decrements the receiver counter <code>n</code> times.
	 * 
	 * @param n Number of times to decrement the counter.
	 * @return The decremented value.
	 */
	public int dec(final int n) {
		_value -= _increment * n;
		return _value;
	}
	
	// Object.
	
	@Override
	public String toString() {
		return String.valueOf(_value);
	}
}
