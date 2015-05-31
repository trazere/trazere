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

import com.trazere.core.functional.Predicate;

/**
 * The {@link IntSequence} class represents sequences of integers.
 * <p>
 * Sequences include their starting value and exclude their ending value. Sequences whose starting and ending value are the same are empty.
 * <p>
 * Sequences may be finite or infinite.
 * <ul>
 * <li>Finite sequences have an ending value.
 * <li>Infinite sequences have no ending value.
 * <p>
 * Sequences may be increasing or decreasing.
 * <ul>
 * <li>The starting value of increasing sequences is less than their ending value, and their increment is positive.
 * <li>The starting value of increasing sequences is greater than their ending value, and their increment is negative.
 * 
 * @since 1.0
 */
public interface IntSequence
extends Predicate<Integer>, Iterable<Integer> {
	// Start.
	
	/**
	 * Gets the starting value of this sequence.
	 * 
	 * @return The starting value.
	 * @since 1.0
	 */
	int getStart();
	
	// End.
	
	/**
	 * Indicates whether this sequence is finite or not.
	 * 
	 * @return <code>true</code> when the sequence is finite, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean isFinite();
	
	// TODO
	//	/**
	//	 * Gets the ending value of this sequence.
	//	 *
	//	 * @return The ending value.
	//	 * @since 1.0
	//	 */
	//	Maybe<Integer> getEnd();
	
	/**
	 * Indicates whether this sequence is empty of not.
	 * <p>
	 * Empty sequences contains no values. Infinite sequences are never empty.
	 * 
	 * @return <code>true</code> when the sequence is empty, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean isEmpty();
	
	// Increment.
	
	/**
	 * Gets the increment between consecutive values of this sequence.
	 * <p>
	 * The increment of the increasing sequences is positive, and the one of decreasing sequences is negative.
	 * 
	 * @return The increment.
	 * @since 1.0
	 */
	int getIncrement();
	
	// Interval.
	
	/**
	 * Gets the interval between consecutive values of this sequence.
	 * <p>
	 * The interval is equivalent to the absolute value of the increment, it is always positive.
	 * 
	 * @return The increment.
	 * @since 1.0
	 */
	int getInterval();
	
	// Inclusion.
	
	/**
	 * Tests whether the given value belongs to this sequence.
	 * 
	 * @param value Value to test.
	 * @return <code>true</code> when the value belongs to the sequence, <code>false</code> otherwise.
	 * @since 1.0
	 */
	boolean includes(int value);
}
