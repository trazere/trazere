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
package com.trazere.util.type;

import com.trazere.core.lang.ComparableUtils;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.util.FieldFunctions;
import com.trazere.core.util.TupleComparators;
import com.trazere.util.function.Function1;

/**
 * The {@link Tuple4} class represents a 4-tuple (quadruplet) data type which stores sequences of 4 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 * @param <T4> Type of the fourth value.
 * @deprecated Use {@link com.trazere.core.util.Tuple4}.
 */
@Deprecated
public class Tuple4<T1, T2, T3, T4>
extends Tuple3<T1, T2, T3> {
	/**
	 * Builds a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param first The first value. May be <code>null</code>.
	 * @param second The second value. May be <code>null</code>.
	 * @param third The third value. May be <code>null</code>.
	 * @param fourth The fourth value. May be <code>null</code>.
	 * @return The built tuple.
	 */
	public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> build(final T1 first, final T2 second, final T3 third, final T4 fourth) {
		return new Tuple4<T1, T2, T3, T4>(first, second, third, fourth);
	}
	
	/**
	 * Instantiates a new instance with the given values.
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
	
	// Fourth.
	
	/** Fourth value. May be <code>null</code>. */
	protected final T4 _fourth;
	
	/**
	 * Gets the fourth value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.Tuple4#get4()}.
	 */
	@Deprecated
	public T4 getFourth() {
		return _fourth;
	}
	
	/**
	 * Builds a function which gets the fourth value of the argument tuples.
	 * 
	 * @param <T4> Type of the fourth values of the tuples.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link FieldFunctions#get4()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T4, X extends Exception> Function1<Tuple4<?, ?, ?, ? extends T4>, T4, X> getFourthFunction() {
		return (Function1<Tuple4<?, ?, ?, ? extends T4>, T4, X>) _GET_FOURTH_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_FOURTH_FUNCTION = new Function1<Tuple4<Object, Object, Object, Object>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Tuple4<Object, Object, Object, Object> value) {
			return value.getFourth();
		}
	};
	
	// Comparison.
	
	/**
	 * Compares the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <T3> Type of the third values.
	 * @param <T4> Type of the fourth value.
	 * @param tuple1 The first tuple.
	 * @param tuple2 The second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link TupleComparators#tuple4(java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator)}.
	 */
	@Deprecated
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> int compare(final Tuple4<T1, T2, T3, T4> tuple1, final Tuple4<T1, T2, T3, T4> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp = Tuple3.compare(tuple1, tuple2);
		return 0 != comp ? comp : ComparableUtils.safeCompareTo(tuple1._fourth, tuple2._fourth);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_first);
		result.append(_second);
		result.append(_third);
		result.append(_fourth);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple4<?, ?, ?, ?> tuple = (Tuple4<?, ?, ?, ?>) object;
			return ObjectUtils.safeEquals(_first, tuple._first) && ObjectUtils.safeEquals(_second, tuple._second) && ObjectUtils.safeEquals(_third, tuple._third) && ObjectUtils.safeEquals(_fourth, tuple._fourth);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + "," + _fourth + ")";
	}
}
