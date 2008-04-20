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

import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.ObjectUtils;

/**
 * The {@link Tuple4} class represents the 4-tuple (quadruplet) data type which stores a sequence of 4 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 * @param <T4> Type of the fourth value.
 */
public class Tuple4<T1, T2, T3, T4>
extends Tuple3<T1, T2, T3> {
	/**
	 * Build a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 * @param fourth Fourth value. May be <code>null</code>.
	 * @return The tuple.
	 */
	public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> build(final T1 first, final T2 second, final T3 third, final T4 fourth) {
		return new Tuple4<T1, T2, T3, T4>(first, second, third, fourth);
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
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> int compare(final Tuple4<T1, T2, T3, T4> tuple1, final Tuple4<T1, T2, T3, T4> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp = Tuple3.compare(tuple1, tuple2);
		return 0 != comp ? comp : ObjectUtils.compare(tuple1._fourth, tuple2._fourth);
	}
	
	/** Fourth value. May be <code>null</code>. */
	protected final T4 _fourth;
	
	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 * @param fourth Fourth value. May be <code>null</code>.
	 */
	public Tuple4(final T1 first, final T2 second, final T3 third, final T4 fourth) {
		super(first, second, third);
		
		// Initialization.
		_fourth = fourth;
	}
	
	/**
	 * Get the fourth value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T4 getFourth() {
		return _fourth;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_first);
		hashCode.append(_second);
		hashCode.append(_third);
		hashCode.append(_fourth);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple4<?, ?, ?, ?> tuple = (Tuple4<?, ?, ?, ?>) object;
			return ObjectUtils.equals(_first, tuple._first) && ObjectUtils.equals(_second, tuple._second) && ObjectUtils.equals(_third, tuple._third) && ObjectUtils.equals(_fourth, tuple._fourth);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + "," + _fourth + ")";
	}
}
