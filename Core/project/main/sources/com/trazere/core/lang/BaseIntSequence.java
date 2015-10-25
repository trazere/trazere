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

/**
 * The {@link BaseIntSequence} class implements finite sequences of integers.
 * 
 * @since 2.0
 */
public abstract class BaseIntSequence
implements IntSequence {
	/**
	 * Instanciates a new sequence of integers separated by the given interval.
	 * 
	 * @param start Starting value of the sequence.
	 * @param increment Increment between consecutive values of the sequence.
	 * @since 2.0
	 */
	protected BaseIntSequence(final int start, final int increment) {
		_start = start;
		_increment = increment;
		_logic = _increment >= 0 ? INCREASING : DECREASING;
	}
	
	// Logic.
	
	/**
	 * The {@link Logic} interface defines the logic of integer sequences.
	 * 
	 * @since 2.0
	 */
	protected static interface Logic {
		/**
		 * Computes the distance of the given value from the given starting value.
		 * 
		 * @param start Starting value of the sequence.
		 * @param value Value whose distance is to be computed.
		 * @return The distance.
		 */
		int computeDistance(int start, int value);
		
		/**
		 * Tests whether the given value is over the given bound.
		 * 
		 * @param value Value to test.
		 * @param bound Bound value.
		 * @return <code>true</code> when the value is over the bound, <code>false</code> otherwise.
		 */
		boolean isOver(int value, int bound);
	}
	
	/**
	 * Logic for increasing sequences.
	 * 
	 * @since 2.0
	 */
	protected static final Logic INCREASING = new Logic() {
		@Override
		public int computeDistance(final int start, final int value) {
			return value - start;
		}
		
		@Override
		public boolean isOver(final int value, final int bound) {
			return value >= bound;
		}
	};
	
	/**
	 * Logic for decreasing sequences.
	 * 
	 * @since 2.0
	 */
	protected static final Logic DECREASING = new Logic() {
		@Override
		public int computeDistance(final int start, final int value) {
			return start - value;
		}
		
		@Override
		public boolean isOver(final int value, final int bound) {
			return value <= bound;
		}
	};
	
	/**
	 * Logic of the sequence.
	 * 
	 * @since 2.0
	 */
	protected final Logic _logic;
	
	// Start.
	
	/**
	 * Starting value of the sequence.
	 * 
	 * @since 2.0
	 */
	protected final int _start;
	
	@Override
	public int getStart() {
		return _start;
	}
	
	// Increment.
	
	/**
	 * Increment between consecutive values.
	 * 
	 * @since 2.0
	 */
	protected final int _increment;
	
	@Override
	public int getIncrement() {
		return _increment;
	}
	
	// Interval.
	
	@Override
	public int getInterval() {
		return _logic.computeDistance(0, _increment);
	}
	
	// Predicate.
	
	@Override
	public boolean evaluate(final Integer arg) {
		return includes(arg.intValue());
	}
}
