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
package com.trazere.core.lang;

import java.util.Iterator;

/**
 * The {@link InfiniteIntSequence} class implements infinite sequences of integers.
 * 
 * @since 1.0
 */
public class InfiniteIntSequence
extends BaseIntSequence {
	/**
	 * Sequence of the natural integers.
	 * 
	 * @since 1.0
	 */
	public static final InfiniteIntSequence NATURAL = new InfiniteIntSequence(0);
	
	/**
	 * Instanciates a new increasing sequence of consecutive integers.
	 * 
	 * @param start Starting value of the sequence.
	 * @since 1.0
	 */
	public InfiniteIntSequence(final int start) {
		this(start, 1);
	}
	
	/**
	 * Instanciates a new sequence of integers with the the given increment.
	 * 
	 * @param start Starting value of the sequence.
	 * @param increment Increment between consecutive the values of the sequences. Must not be <code>0</code>.
	 * @since 1.0
	 */
	protected InfiniteIntSequence(final int start, final int increment) {
		super(start, increment);
		
		// Check the interval.
		if (increment == 0) {
			throw new IllegalArgumentException("Increment " + increment + " must not be zero");
		}
	}
	
	// End.
	
	@Override
	public boolean isFinite() {
		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}
	
	// Inclusion.
	
	@Override
	public boolean includes(final int value) {
		return _logic.isOver(value, _start) && 0 == (_logic.computeDistance(_start, value) % getInterval());
	}
	
	// Iterable.
	
	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			private int _value = _start;
			
			@Override
			public boolean hasNext() {
				return true;
			}
			
			@Override
			public Integer next() {
				final int value = _value;
				_value += _increment;
				return value;
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
		result.append(_increment);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final InfiniteIntSequence sequence = (InfiniteIntSequence) object;
			return _start == sequence._start && _increment == sequence._increment;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		final int start = getStart();
		final int interval = getInterval();
		return 1 == interval ? "[" + start + ", ..." : "[" + start + " / " + interval + ", ...";
	}
}
