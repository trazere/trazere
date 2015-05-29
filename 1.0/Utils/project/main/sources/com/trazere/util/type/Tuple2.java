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

import com.trazere.util.function.Function1;
import com.trazere.util.function.Function2;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

/**
 * The {@link Tuple2} class represents a 2-tuple (pair) data types which stores sequences of 2 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @deprecated Use {@link com.trazere.core.util.Tuple2}.
 */
@Deprecated
public class Tuple2<T1, T2>
extends Tuple1<T1> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param first The first value. May be <code>null</code>.
	 * @param second The second value. May be <code>null</code>.
	 * @return The built tuple.
	 * @deprecated {@link com.trazere.core.util.Tuples#tuple2(Object, Object)}.
	 */
	@Deprecated
	public static <T1, T2> Tuple2<T1, T2> build(final T1 first, final T2 second) {
		return new Tuple2<T1, T2>(first, second);
	}
	
	/**
	 * Builds a function which wraps its arguments in {@link Tuple2} instances (currying).
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated {@link com.trazere.core.util.TupleFunctions#tuple2()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function2<T1, T2, Tuple2<T1, T2>, X> buildFunction() {
		return (Function2<T1, T2, Tuple2<T1, T2>, X>) _BUILD_FUNCTION;
	}
	
	private static final Function2<?, ?, ?, ?> _BUILD_FUNCTION = new Function2<Object, Object, Tuple2<Object, Object>, RuntimeException>() {
		@Override
		public Tuple2<Object, Object> evaluate(final Object first, final Object second) {
			return Tuple2.build(first, second);
		}
	};
	
	/**
	 * Instantiates a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 */
	public Tuple2(final T1 first, final T2 second) {
		super(first);
		
		// Initialization.
		_second = second;
	}
	
	// Second.
	
	/** Second value. May be <code>null</code>. */
	protected final T2 _second;
	
	/**
	 * Gets the second value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.Field2#get2()}.
	 */
	@Deprecated
	public T2 getSecond() {
		return _second;
	}
	
	/**
	 * Builds a function which gets the second value of the argument tuples.
	 * 
	 * @param <T2> Type of the second values of the tuples.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated {@link com.trazere.core.util.FieldFunctions#get2()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T2, X extends Exception> Function1<Tuple2<?, ? extends T2>, T2, X> getSecondFunction() {
		return (Function1<Tuple2<?, ? extends T2>, T2, X>) _GET_SECOND_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_SECOND_FUNCTION = new Function1<Tuple2<Object, Object>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Tuple2<Object, Object> tuple) {
			return tuple.getSecond();
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
	 * @param tuple1 The first tuple.
	 * @param tuple2 The second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link com.trazere.core.util.TupleComparators#tuple2(java.util.Comparator, java.util.Comparator)}.
	 */
	@Deprecated
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> int compare(final Tuple2<T1, T2> tuple1, final Tuple2<T1, T2> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp1 = LangUtils.safeCompare(tuple1._first, tuple2._first);
		return 0 != comp1 ? comp1 : LangUtils.safeCompare(tuple1._second, tuple2._second);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_first);
		result.append(_second);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple2<?, ?> tuple = (Tuple2<?, ?>) object;
			return LangUtils.safeEquals(_first, tuple._first) && LangUtils.safeEquals(_second, tuple._second);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + ")";
	}
}
