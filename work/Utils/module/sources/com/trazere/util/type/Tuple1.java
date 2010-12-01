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

import com.trazere.util.function.Function1;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;

/**
 * The {@link Tuple1} class represents a 1-tuple data type which stores sequences of 1 value.
 * 
 * @param <T1> Type of the first value.
 */
public class Tuple1<T1> {
	/**
	 * Builds a tuple with the given value.
	 * 
	 * @param <T1> Type of the first value.
	 * @param first The first value. May be <code>null</code>.
	 * @return The built tuple.
	 */
	public static <T1> Tuple1<T1> build(final T1 first) {
		return new Tuple1<T1>(first);
	}
	
	/**
	 * Builds a function which wraps its arguments in {@link Tuple1} instances (currying).
	 * 
	 * @param <T1> Type of the first value.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, X extends Exception> Function1<T1, Tuple1<T1>, X> buildFunction() {
		return (Function1<T1, Tuple1<T1>, X>) _BUILD_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _BUILD_FUNCTION = new Function1<Object, Tuple1<Object>, RuntimeException>() {
		public Tuple1<Object> evaluate(final Object first) {
			return Tuple1.build(first);
		}
	};
	
	/**
	 * Instantiates a new instance with the given value.
	 * 
	 * @param first First value. May be <code>null</code>.
	 */
	public Tuple1(final T1 first) {
		// Initialization.
		_first = first;
	}
	
	// First.
	
	/** First value. May be <code>null</code>. */
	protected final T1 _first;
	
	/**
	 * Gets the first value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T1 getFirst() {
		return _first;
	}
	
	/**
	 * Builds a function which gets the first value of the argument tuples.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function1<Tuple2<? extends T1, ? extends T2>, T1, X> getFirstFunction() {
		return (Function1<Tuple2<? extends T1, ? extends T2>, T1, X>) _GET_FIRST_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _GET_FIRST_FUNCTION = new Function1<Tuple2<Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple2<Object, Object> tuple) {
			return tuple.getFirst();
		}
	};
	
	// Comparison.
	
	/**
	 * Compares the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param tuple1 The first tuple.
	 * @param tuple2 The second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>> int compare(final Tuple1<T1> tuple1, final Tuple1<T1> tuple2) {
		assert null != tuple1;
		assert null != tuple2;
		
		// Compare.
		return LangUtils.compare(tuple1._first, tuple2._first);
	}
	
	// Object.
	
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
