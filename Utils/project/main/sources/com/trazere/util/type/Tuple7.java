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
package com.trazere.util.type;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

/**
 * The {@link Tuple7} class represents a 7-tuple (septuplet) data type which stores sequences of 7 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 * @param <T4> Type of the fourth value.
 * @param <T5> Type of the fifth value.
 * @param <T6> Type of the sixth value.
 * @param <T7> Type of the seventh value.
 * @deprecated Use {@link com.trazere.core.util.Tuple7}.
 */
@Deprecated
public class Tuple7<T1, T2, T3, T4, T5, T6, T7>
extends Tuple6<T1, T2, T3, T4, T5, T6> {
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
	 * @param first The first value. May be <code>null</code>.
	 * @param second The second value. May be <code>null</code>.
	 * @param third The third value. May be <code>null</code>.
	 * @param fourth The fourth value. May be <code>null</code>.
	 * @param fifth The fifth value. May be <code>null</code>.
	 * @param sixth The sixth value. May be <code>null</code>.
	 * @param seventh The seventh value. May be <code>null</code>.
	 * @return The built tuple.
	 * @deprecated {@link com.trazere.core.util.Tuples#tuple7(Object, Object, Object, Object, Object, Object, Object)}.
	 */
	@Deprecated
	public static <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> build(final T1 first, final T2 second, final T3 third, final T4 fourth, final T5 fifth, final T6 sixth, final T7 seventh) {
		return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(first, second, third, fourth, fifth, sixth, seventh);
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
	 */
	public Tuple7(final T1 first, final T2 second, final T3 third, final T4 fourth, final T5 fifth, final T6 sixth, final T7 seventh) {
		super(first, second, third, fourth, fifth, sixth);
		
		// Initialization.
		_seventh = seventh;
	}
	
	// Seventh.
	
	/** Seventh value. May be <code>null</code>. */
	protected final T7 _seventh;
	
	/**
	 * Gets the seventh value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.Field7#get7()}.
	 */
	@Deprecated
	public T7 getSeventh() {
		return _seventh;
	}
	
	/**
	 * Builds a function which gets the seventh value of the argument tuples.
	 * 
	 * @param <T7> Type of the seventh value of the tuples.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.util.FieldFunctions#get7()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T7, X extends Exception> Function1<Tuple7<?, ?, ?, ?, ?, ?, ? extends T7>, T7, X> getSeventhFunction() {
		return (Function1<Tuple7<?, ?, ?, ?, ?, ?, ? extends T7>, T7, X>) _GET_SEVENTH_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_SEVENTH_FUNCTION = new Function1<Tuple7<Object, Object, Object, Object, Object, Object, Object>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Tuple7<Object, Object, Object, Object, Object, Object, Object> value) {
			return value.getSeventh();
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
	 * @param tuple1 The first tuple.
	 * @param tuple2 The second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use
	 *             {@link com.trazere.core.util.TupleComparators#tuple7(java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator, java.util.Comparator)}
	 *             .
	 */
	@Deprecated
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>, T6 extends Comparable<T6>, T7 extends Comparable<T7>> int compare(final Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple1, final Tuple7<T1, T2, T3, T4, T5, T6, T7> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp = Tuple6.compare(tuple1, tuple2);
		return 0 != comp ? comp : LangUtils.safeCompare(tuple1._seventh, tuple2._seventh);
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
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple7<?, ?, ?, ?, ?, ?, ?> tuple = (Tuple7<?, ?, ?, ?, ?, ?, ?>) object;
			return LangUtils.safeEquals(_first, tuple._first) && LangUtils.safeEquals(_second, tuple._second) && LangUtils.safeEquals(_third, tuple._third) && LangUtils.safeEquals(_fourth, tuple._fourth) && LangUtils.safeEquals(_fifth, tuple._fifth) && LangUtils.safeEquals(_sixth, tuple._sixth) && LangUtils.safeEquals(_seventh, tuple._seventh);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + "," + _fourth + "," + _fifth + "," + _sixth + "," + _seventh + ")";
	}
}
