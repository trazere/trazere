/*
 *  Copyright 2006-2008 Julien Dufour
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
package com.trazere.util.function;

import com.trazere.util.lang.LangUtils;
import java.util.Collection;

/**
 * The {@link Predicates} class provides various standard predicate functions.
 * 
 * @see Predicate
 * @see Predicate2
 */
public class Predicates {
	private static final Predicate<?, ?> ALL = new Predicate<Object, Exception>() {
		public boolean evaluate(final Object value) {
			return true;
		}
	};
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate<T, X> all() {
		return (Predicate<T, X>) ALL;
	}
	
	private static final Predicate<?, ?> NONE = new Predicate<Object, Exception>() {
		public boolean evaluate(final Object value) {
			return false;
		}
	};
	
	/**
	 * Build a predicate which evaluates to <code>false</code> for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate<T, X> none() {
		return (Predicate<T, X>) NONE;
	}
	
	/**
	 * Build a predicate corresponding to the inverse of the given predicate.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate<T, X> not(final Predicate<T, X> predicate) {
		assert null != predicate;
		
		// Build.
		return new Predicate<T, X>() {
			public boolean evaluate(final T value)
			throws X {
				return !predicate.evaluate(value);
			}
		};
	}
	
	/**
	 * Build a predicate corresponding to the conjonction of the given predicates.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate<T, X> and(final Predicate<? super T, ? extends X> predicate1, final Predicate<? super T, ? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		// Build.
		return new Predicate<T, X>() {
			public boolean evaluate(final T value)
			throws X {
				return predicate1.evaluate(value) && predicate2.evaluate(value);
			}
		};
	}
	
	/**
	 * Build a predicate corresponding to the disjunction of the given predicates.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate<T, X> or(final Predicate<? super T, ? extends X> predicate1, final Predicate<? super T, ? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		// Build.
		return new Predicate<T, X>() {
			public boolean evaluate(final T value)
			throws X {
				return predicate1.evaluate(value) || predicate2.evaluate(value);
			}
		};
	}
	
	private static final Predicate2<?, ?, ?> ALL2 = new Predicate2<Object, Object, Exception>() {
		public boolean evaluate(final Object value1, final Object value2) {
			return true;
		}
	};
	
	/**
	 * Build a two arguments predicate which evaluates to <code>true</code> for all pairs of values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> all2() {
		return (Predicate2<T1, T2, X>) ALL2;
	}
	
	private static final Predicate2<?, ?, ?> NONE2 = new Predicate2<Object, Object, Exception>() {
		public boolean evaluate(final Object value1, final Object value2) {
			return false;
		}
	};
	
	/**
	 * Build a two arguments predicate which evaluates to <code>false</code> for all pairs of values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> none2() {
		return (Predicate2<T1, T2, X>) NONE2;
	}
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate<T, X> equal(final T value) {
		return new Predicate<T, X>() {
			public boolean evaluate(final T value_) {
				return LangUtils.equals(value, value_);
			}
		};
	}
	
	/**
	 * Build a predicate which evaluates to <code>true</code> for any given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param values Values defining the predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate<T, X> any(final Collection<T> values) {
		assert null != values;
		
		return new Predicate<T, X>() {
			public boolean evaluate(final T value) {
				return values.contains(value);
			}
		};
	}
	
	/**
	 * Build a two arguments predicate which evaluates to <code>true</code> for all pairs whose first value belongs to the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param values First values defining the predicate.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> any1(final Collection<T1> values) {
		assert null != values;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2) {
				return values.contains(value1);
			}
		};
	}
	
	/**
	 * Build a two arguments predicate which evaluates to <code>true</code> for all pairs whose second value belongs to the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param values Second values defining the predicate.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> any2(final Collection<T2> values) {
		assert null != values;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2) {
				return values.contains(value2);
			}
		};
	}
	
	private Predicates() {
		// Prevent instantiation.
	}
}
