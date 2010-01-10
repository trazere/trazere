/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.type;

import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

/**
 * The {@link Tuple2} class represents the 2-tuple (pair) data type which stores a sequence of 2 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 */
public class Tuple2<T1, T2> {
	/**
	 * Build a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @return The tuple.
	 */
	public static <T1, T2> Tuple2<T1, T2> build(final T1 first, final T2 second) {
		return new Tuple2<T1, T2>(first, second);
	}
	
	/**
	 * Compare the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> int compare(final Tuple2<T1, T2> tuple1, final Tuple2<T1, T2> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp1 = LangUtils.compare(tuple1._first, tuple2._first);
		return 0 != comp1 ? comp1 : LangUtils.compare(tuple1._second, tuple2._second);
	}
	
	/** First value. May be <code>null</code>. */
	protected final T1 _first;
	
	/** Second value. May be <code>null</code>. */
	protected final T2 _second;
	
	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 */
	public Tuple2(final T1 first, final T2 second) {
		// Initialization.
		_first = first;
		_second = second;
	}
	
	/**
	 * Get the first value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T1 getFirst() {
		return _first;
	}
	
	/**
	 * Get the second value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T2 getSecond() {
		return _second;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_first);
		hashCode.append(_second);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple2<?, ?> tuple = (Tuple2<?, ?>) object;
			return LangUtils.equals(_first, tuple._first) && LangUtils.equals(_second, tuple._second);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + ")";
	}
}
