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
package com.trazere.util.type;

import com.trazere.util.Assert;
import com.trazere.util.lang.ObjectUtils;

/**
 * The {@link Tuple5} class represents the 5-tuple (quintuplet) data type which stores a sequence of 5 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 * @param <T4> Type of the fourth value.
 * @param <T5> Type of the fifth value.
 */
public class Tuple5<T1, T2, T3, T4, T5>
extends Tuple4<T1, T2, T3, T4> {
	/**
	 * Build a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 * @param fourth Fourth value. May be <code>null</code>.
	 * @param fifth Fifth value. May be <code>null</code>.
	 * @return The tuple.
	 */
	public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> build(final T1 first, final T2 second, final T3 third, final T4 fourth, final T5 fifth) {
		return new Tuple5<T1, T2, T3, T4, T5>(first, second, third, fourth, fifth);
	}
	
	/**
	 * Compare the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <T3> Type of the third values.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> int compare(final Tuple5<T1, T2, T3, T4, T5> tuple1, final Tuple5<T1, T2, T3, T4, T5> tuple2) {
		Assert.notNull(tuple1);
		Assert.notNull(tuple2);
		
		// Compare.
		final int comp = Tuple4.compare(tuple1, tuple2);
		return 0 != comp ? comp : ObjectUtils.compare(tuple1._fifth, tuple2._fifth);
	}
	
	/** Fifth value. May be <code>null</code>. */
	protected final T5 _fifth;
	
	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 * @param fourth Fourth value. May be <code>null</code>.
	 * @param fifth Fifth value. May be <code>null</code>.
	 */
	public Tuple5(final T1 first, final T2 second, final T3 third, final T4 fourth, final T5 fifth) {
		super(first, second, third, fourth);
		
		// Initialization.
		_fifth = fifth;
	}
	
	/**
	 * Get the fifth value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T5 getFifth() {
		return _fifth;
	}
	
	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		if (null != _first) {
			result = result * 31 + _first.hashCode();
		}
		if (null != _second) {
			result = result * 31 + _second.hashCode();
		}
		if (null != _third) {
			result = result * 31 + _third.hashCode();
		}
		if (null != _fourth) {
			result = result * 31 + _fourth.hashCode();
		}
		if (null != _fifth) {
			result = result * 31 + _fifth.hashCode();
		}
		return result;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple5<?, ?, ?, ?, ?> tuple = (Tuple5<?, ?, ?, ?, ?>) object;
			return ObjectUtils.equals(_first, tuple._first) && ObjectUtils.equals(_second, tuple._second) && ObjectUtils.equals(_third, tuple._third) && ObjectUtils.equals(_fourth, tuple._fourth) && ObjectUtils.equals(_fifth, tuple._fifth);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + "," + _fourth + "," + _fifth + ")";
	}
}
