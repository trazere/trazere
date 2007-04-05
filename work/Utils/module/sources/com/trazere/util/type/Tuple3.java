/*
 *  Copyright 2006 Julien Dufour
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
import com.trazere.util.ObjectUtils;

/**
 * The {@link Tuple3} class represents the 3-tuple (triplet) data type which stores a sequence of 3 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 */
public class Tuple3<T1, T2, T3>
extends Tuple2<T1, T2> {
	/**
	 * Build a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 * @return The tuple.
	 */
	public static <T1, T2, T3> Tuple3<T1, T2, T3> build(final T1 first, final T2 second, final T3 third) {
		return new Tuple3<T1, T2, T3>(first, second, third);
	}

	/**
	 * Compare the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <T3> Type of the third values.
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> int compare(final Tuple3<T1, T2, T3> tuple1, final Tuple3<T1, T2, T3> tuple2) {
		Assert.notNull(tuple1);
		Assert.notNull(tuple2);

		// Compare.
		final int comp = Tuple2.compare(tuple1, tuple2);
		if (0 != comp) {
			return comp;
		} else {
			return ObjectUtils.compare(tuple1._third, tuple2._third);
		}
	}

	/** Third value. May be <code>null</code>. */
	protected final T3 _third;

	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 */
	public Tuple3(final T1 first, final T2 second, final T3 third) {
		super(first, second);

		// Initialization.
		_third = third;
	}

	/**
	 * Get the third value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T3 getThird() {
		return _third;
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
		return result;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple3<?, ?, ?> tuple = (Tuple3<?, ?, ?>) object;
			return ObjectUtils.equals(_first, tuple._first) && ObjectUtils.equals(_second, tuple._second) && ObjectUtils.equals(_third, tuple._third);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + ")";
	}
}
