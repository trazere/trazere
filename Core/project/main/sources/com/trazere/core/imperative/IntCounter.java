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
package com.trazere.core.imperative;

/**
 * The {@link IntCounter} class implements integer counters.
 */
public class IntCounter {
	/**
	 * Instantiates a new counter starting at <code>0</code> and incrementing by <code>1</code>.
	 */
	public IntCounter() {
		this(0, 1);
	}
	
	/**
	 * Instantiates a new counter starting at the given value and incrementing by <code>1</code>.
	 * 
	 * @param start Starting value.
	 */
	public IntCounter(final int start) {
		this(start, 1);
	}
	
	/**
	 * Instantiates a new counter.
	 * 
	 * @param start Starting value.
	 * @param increment Increment between consecutive values.
	 */
	public IntCounter(final int start, final int increment) {
		_start = start;
		_increment = increment;
		_value = start;
	}
	
	// Start.
	
	/** Starting value of the counter. */
	protected final int _start;
	
	/**
	 * Gets the starting value of this counter.
	 * 
	 * @return The start value.
	 */
	public int getStart() {
		return _start;
	}
	
	// Increment.
	
	/** Increment between consecutive values. */
	protected final int _increment;
	
	/**
	 * Gets the increment between consecutive values of this counter.
	 * 
	 * @return The increment.
	 */
	public int getIncrement() {
		return _increment;
	}
	
	// Value.
	
	/** Current value. */
	protected int _value;
	
	/**
	 * Gets the current value of this counter.
	 * 
	 * @return The current value.
	 */
	public int get() {
		return _value;
	}
	
	/**
	 * Indicates whether the current value of this counter is the starting value.
	 * 
	 * @return <code>true</code> when the current value is the starting value, <code>false</code> otherwise.
	 */
	public boolean isStart() {
		return _start == _value;
	}
	
	/**
	 * Resets this counter to its starting value.
	 */
	public void reset() {
		_value = _start;
	}
	
	/**
	 * Increments this counter.
	 * 
	 * @return The incremented value.
	 */
	public int inc() {
		_value += _increment;
		return _value;
	}
	
	/**
	 * Increments this counter <code>n</code> times.
	 * 
	 * @param n Number of times to increment the counter.
	 * @return The incremented value.
	 */
	public int inc(final int n) {
		_value += _increment * n;
		return _value;
	}
	
	/**
	 * Decrements this counter.
	 * 
	 * @return The decremented value.
	 */
	public int dec() {
		_value -= _increment;
		return _value;
	}
	
	/**
	 * Decrements this counter <code>n</code> times.
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
