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
 * The {@link Tuple8} class represents a 8-tuple (octuplet) data type which stores sequences of 8 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 * @param <T4> Type of the fourth value.
 * @param <T5> Type of the fifth value.
 * @param <T6> Type of the sixth value.
 * @param <T7> Type of the seventh value.
 * @param <T8> Type of the eighth value.
 * @deprecated Use {@link com.trazere.core.util.Tuple8}.
 */
@Deprecated
public class Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>
extends Tuple7<T1, T2, T3, T4, T5, T6, T7> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param <T4> Type of the fourth value.
	 * @param <T5> Type of the fifth value.
	 * @param <T6> Type of the sixth value.
	 * @param <T7> Type of the seventh value.
	 * @param <T8> Type of the eighth value.
	 * @param first The first value. May be <code>null</code>.
	 * @param second The second value. May be <code>null</code>.
	 * @param third The third value. May be <code>null</code>.
	 * @param fourth The fourth value. May be <code>null</code>.
	 * @param fifth The fifth value. May be <code>null</code>.
	 * @param sixth The sixth value. May be <code>null</code>.
	 * @param seventh The seventh value. May be <code>null</code>.
	 * @param eighth The eighth value. May be <code>null</code>.
	 * @return The built tuple.
	 */
	public static <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> build(final T1 first, final T2 second, final T3 third, final T4 fourth, final T5 fifth, final T6 sixth, final T7 seventh, final T8 eighth) {
		return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(first, second, third, fourth, fifth, sixth, seventh, eighth);
	}
	
	/**
	 * Instantiates a new instance with the given values.
	 * 
	 * @param first The first value. May be <code>null</code>.
	 * @param second The second value. May be <code>null</code>.
	 * @param third The third value. May be <code>null</code>.
	 * @param fourth The fourth value. May be <code>null</code>.
	 * @param fifth The fifth value. May be <code>null</code>.
	 * @param sixth The sixth value. May be <code>null</code>.
	 * @param seventh The seventh value. May be <code>null</code>.
	 * @param eighth The eighth value. May be <code>null</code>.
	 */
	public Tuple8(final T1 first, final T2 second, final T3 third, final T4 fourth, final T5 fifth, final T6 sixth, final T7 seventh, final T8 eighth) {
		super(first, second, third, fourth, fifth, sixth, seventh);
		
		// Initialization.
		_eighth = eighth;
	}
	
	// Eighth.
	
	/** Eighth value. May be <code>null</code>. */
	protected final T8 _eighth;
	
	/**
	 * Gets the eighth value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.Tuple8#get8()}.
	 */
	@Deprecated
	public T8 getEighth() {
		return _eighth;
	}
	
	/**
	 * Builds a function which gets the eighth value of the argument tuples.
	 * 
	 * @param <T8> Type of the eighth value of the tuples.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link FieldFunctions#get8()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T8, X extends Exception> Function1<Tuple8<?, ?, ?, ?, ?, ?, ?, ? extends T8>, T8, X> getEighthFunction() {
		return (Function1<Tuple8<?, ?, ?, ?, ?, ?, ?, ? extends T8>, T8, X>) _GET_EIGHTH_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_EIGHTH_FUNCTION = new Function1<Tuple8<Object, Object, Object, Object, Object, Object, Object, Object>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Tuple8<Object, Object, Object, Object, Object, Object, Object, Object> value) {
			return value.getEighth();
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
	 * @param <T5> Type of the fifth value.
	 * @param <T6> Type of the sixth value.
	 * @param <T7> Type of the seventh value.
	 * @param <T8> Type of the eighth value.
	 * @param tuple1 The first tuple.
	 * @param tuple2 The second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use
	 *             {@link TupleComparators#tuple8(java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator)}
	 *             .
	 */
	@Deprecated
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>, T6 extends Comparable<T6>, T7 extends Comparable<T7>, T8 extends Comparable<T8>> int compare(final Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple1, final Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp = Tuple7.compare(tuple1, tuple2);
		return 0 != comp ? comp : ComparableUtils.safeCompareTo(tuple1._eighth, tuple2._eighth);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_first);
		result.append(_second);
		result.append(_third);
		result.append(_fourth);
		result.append(_fifth);
		result.append(_sixth);
		result.append(_seventh);
		result.append(_eighth);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple8<?, ?, ?, ?, ?, ?, ?, ?> tuple = (Tuple8<?, ?, ?, ?, ?, ?, ?, ?>) object;
			return ObjectUtils.safeEquals(_first, tuple._first) && ObjectUtils.safeEquals(_second, tuple._second) && ObjectUtils.safeEquals(_third, tuple._third) && ObjectUtils.safeEquals(_fourth, tuple._fourth) && ObjectUtils.safeEquals(_fifth, tuple._fifth) && ObjectUtils.safeEquals(_sixth, tuple._sixth) && ObjectUtils.safeEquals(_seventh, tuple._seventh) && ObjectUtils.safeEquals(_eighth, tuple._eighth);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + "," + _fourth + "," + _fifth + "," + _sixth + "," + _seventh + "," + _eighth + ")";
	}
}
