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
 * The {@link Tuple1} class represents the 1-tuple data type which stores a sequence of 1 values.
 * 
 * @param <T1> Type of the first value.
 */
public class Tuple1<T1> {
	/**
	 * Build a tuple with the given value.
	 * 
	 * @param <T1> Type of the first value.
	 * @param first First value. May be <code>null</code>.
	 * @return The tuple.
	 */
	public static <T1> Tuple1<T1> build(final T1 first) {
		return new Tuple1<T1>(first);
	}
	
	/**
	 * Compare the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>> int compare(final Tuple1<T1> tuple1, final Tuple1<T1> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		return LangUtils.compare(tuple1._first, tuple2._first);
	}
	
	/** First value. May be <code>null</code>. */
	protected final T1 _first;
	
	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 */
	public Tuple1(final T1 first) {
		// Initialization.
		_first = first;
	}
	
	/**
	 * Get the first value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T1 getFirst() {
		return _first;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_first);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple1<?> tuple = (Tuple1<?>) object;
			return LangUtils.equals(_first, tuple._first);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + ")";
	}
}
