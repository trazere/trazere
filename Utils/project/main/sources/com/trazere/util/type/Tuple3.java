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
 * The {@link Tuple3} class represents a 3-tuple (triplet) data type which stores sequences of 3 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 * @deprecated Use {@link com.trazere.core.util.Tuple3}.
 */
@Deprecated
public class Tuple3<T1, T2, T3>
extends Tuple2<T1, T2> {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a tuple with the given values.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <T3> Type of the third value.
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 * @return The built tuple.
	 * @deprecated Use {@link com.trazere.core.util.Tuples#tuple3(Object, Object, Object)}.
	 */
	@Deprecated
	public static <T1, T2, T3> Tuple3<T1, T2, T3> build(final T1 first, final T2 second, final T3 third) {
		return new Tuple3<>(first, second, third);
	}
	
	// TODO: name clash
	//	/**
	//	 * Builds a function which wraps its arguments in {@link Tuple3} instances (currying).
	//	 *
	//	 * @param <T1> Type of the first value.
	//	 * @param <T2> Type of the second value.
	//	 * @param <T3> Type of the third value.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @deprecated Use {@link com.trazere.core.util.TupleFunctions#tuple3()}.
	//	 */
	//	@Deprecated
	//	@SuppressWarnings("unchecked")
	//	public static <T1, T2, T3, X extends Exception> Function3<T1, T2, T3, Tuple3<T1, T2, T3>, X> buildFunction() {
	//		return (Function3<T1, T2, T3, Tuple3<T1, T2, T3>, X>) _BUILD_FUNCTION;
	//	}
	//
	//	private static final Function3<?, ?, ?, ?, ?> _BUILD_FUNCTION = new Function3<Object, Object, Object, Tuple3<Object, Object, Object>, RuntimeException>() {
	//		@Override
	//		public Tuple3<Object, Object, Object> evaluate(final Object first, final Object second, final Object third) {
	//			return Tuple3.build(first, second, third);
	//		}
	//	};
	
	/**
	 * Instantiates a new instance with the given values.
	 * 
	 * @param first The first value. May be <code>null</code>.
	 * @param second The second value. May be <code>null</code>.
	 * @param third The third value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.Tuple3#Tuple3(Object, Object, Object)}.
	 */
	@Deprecated
	public Tuple3(final T1 first, final T2 second, final T3 third) {
		super(first, second);
		
		// Initialization.
		_third = third;
	}
	
	// Third.
	
	/** Third value. May be <code>null</code>. */
	protected final T3 _third;
	
	/**
	 * Gets the third value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 * @deprecated Use {@link com.trazere.core.util.Field3#get3()}.
	 */
	@Deprecated
	public T3 getThird() {
		return _third;
	}
	
	/**
	 * Builds a function which gets the third value of the argument tuples.
	 * 
	 * @param <T3> Type of the third values of the tuples.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.util.FieldFunctions#get3()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T3, X extends Exception> Function1<Tuple3<?, ?, ? extends T3>, T3, X> getThirdFunction() {
		return (Function1<Tuple3<?, ?, ? extends T3>, T3, X>) _GET_THIRD_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_THIRD_FUNCTION = new Function1<Tuple3<Object, Object, Object>, Object, RuntimeException>() {
		@Override
		public Object evaluate(final Tuple3<Object, Object, Object> value) {
			return value.getThird();
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
	 * @param tuple1 The first tuple.
	 * @param tuple2 The second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 * @deprecated Use {@link com.trazere.core.util.TupleComparators#tuple3(java.util.Comparator, java.util.Comparator, java.util.Comparator)}.
	 */
	@Deprecated
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> int compare(final Tuple3<T1, T2, T3> tuple1, final Tuple3<T1, T2, T3> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		final int comp = Tuple2.compare(tuple1, tuple2);
		return 0 != comp ? comp : LangUtils.safeCompare(tuple1._third, tuple2._third);
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_first);
		result.append(_second);
		result.append(_third);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple3<?, ?, ?> tuple = (Tuple3<?, ?, ?>) object;
			return LangUtils.safeEquals(_first, tuple._first) && LangUtils.safeEquals(_second, tuple._second) && LangUtils.safeEquals(_third, tuple._third);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _first + "," + _second + "," + _third + ")";
	}
}
