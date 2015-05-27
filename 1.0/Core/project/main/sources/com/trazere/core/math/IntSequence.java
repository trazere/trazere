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
package com.trazere.core.math;

import com.trazere.core.imperative.IntCounter;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link IntSequence} class reprensents sequences of integers.
 * <p>
 * The sequences includes their lower bound and exclude their upper bound.
 */
public class IntSequence
implements Iterable<Integer> {
	/**
	 * Instantiates a new sequence with a step of <tt>1</tt>.
	 * 
	 * @param from Lower bound of the sequence (included).
	 * @param to Upper bound of the sequence (excluded).
	 */
	public IntSequence(final int from, final int to) {
		this(from, to, 1);
	}
	
	/**
	 * Instantiates a new sequence.
	 * 
	 * @param from Lower bound of the sequence (included).
	 * @param to Upper bound of the sequence (excluded).
	 * @param step Interval between two consecutive values of the sequence.
	 */
	public IntSequence(final int from, final int to, final int step) {
		assert from <= to;
		assert step > 0;
		
		// Initialization.
		_from = from;
		_to = to;
		_step = step;
	}
	
	// From.
	
	/** Lower bound of the sequence. */
	protected final int _from;
	
	/**
	 * Gets the lower bound of this sequence.
	 * 
	 * @return The lower bound.
	 */
	public int getFrom() {
		return _from;
	}
	
	// To.
	
	/** Upper bound of the sequence. */
	protected final int _to;
	
	/**
	 * Gets the upper bound of this sequence.
	 * 
	 * @return The upper bound.
	 */
	public int getTo() {
		return _to;
	}
	
	// Step.
	
	/** Interval between two consecutive values of the sequence. */
	protected final int _step;
	
	/**
	 * Gets the interval between two consecutive values of this sequence.
	 * 
	 * @return The interval.
	 */
	public int getStep() {
		return _step;
	}
	
	// Sequence.
	
	/**
	 * Indicates whether the given value belongs to this sequence.
	 * 
	 * @param value Value to test.
	 * @return <code>true</code> when the value belongs to the sequence, <code>false</code> otherwise.
	 */
	public boolean contains(final int value) {
		return value >= _from && value < _to && 0 == ((value - _from) % _step);
	}
	
	// Iterable.
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			private final IntCounter _index = new IntCounter(_from, _step);
			
			@Override
			public boolean hasNext() {
				return _index.get() < _to;
			}
			
			@Override
			public Integer next() {
				final int index = _index.get();
				if (index < _to) {
					_index.inc();
					return index;
				} else {
					throw new NoSuchElementException();
				}
			}
		};
	}
	
	// Object.
	
	@Override
	public String toString() {
		if (1 == _step) {
			return "[" + _from + ", " + _to + "[";
		} else {
			return "[" + _from + ", " + _to + " / " + _step + "[";
		}
	}
}
