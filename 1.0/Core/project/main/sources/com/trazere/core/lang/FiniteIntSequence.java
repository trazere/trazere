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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@link FiniteIntSequence} class implements finite sequences of integers.
 */
public class FiniteIntSequence
extends BaseIntSequence {
	/**
	 * Instanciates a new sequence of consecutive integers.
	 * 
	 * @param start Starting value of the sequence.
	 * @param end Ending value of the sequence.
	 */
	public FiniteIntSequence(final int start, final int end) {
		this(start, end, 1);
	}
	
	/**
	 * Instanciates a new sequence of integers separated by the given interval.
	 * 
	 * @param start Starting value of the sequence.
	 * @param end Ending value of the sequence.
	 * @param interval Interval between consecutive the values of the sequences. Must be strictly positive.
	 */
	public FiniteIntSequence(final int start, final int end, final int interval) {
		super(start, start >= end ? interval : -interval);
		
		// Check the interval.
		if (interval <= 0) {
			throw new IllegalArgumentException("Interval " + interval + " must be positive");
		}
		
		// Initialization.
		_end = end;
	}
	
	// End.
	
	/** Ending value of the sequence. */
	protected final int _end;
	
	/**
	 * Gets the ending value of this sequence.
	 * 
	 * @return The ending value.
	 */
	public int getEnd() {
		return _end;
	}
	
	@Override
	public boolean isFinite() {
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return _start == _end;
	}
	
	// Inclusion.
	
	@Override
	public boolean includes(final int value) {
		return _logic.isOver(value, _start) && !_logic.isOver(value, _end) && 0 == (_logic.computeDistance(_start, value) % getInterval());
	}
	
	// Iterable.
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			private int _value = _start;
			
			@Override
			public boolean hasNext() {
				return !_logic.isOver(_value, _end);
			}
			
			@Override
			public Integer next() {
				if (_logic.isOver(_value, _end)) {
					throw new NoSuchElementException();
				} else {
					final int value = _value;
					_value += _increment;
					return value;
				}
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_start);
		result.append(_end);
		result.append(_increment);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final FiniteIntSequence sequence = (FiniteIntSequence) object;
			return _start == sequence._start && _end == sequence._end && _increment == sequence._increment;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		final int start = getStart();
		final int end = getEnd();
		final int interval = getInterval();
		return 1 == interval ? "[" + start + ", " + end + "[" : "[" + start + " / " + interval + ", " + end + "[";
	}
}
