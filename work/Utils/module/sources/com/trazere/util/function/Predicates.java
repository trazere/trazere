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
	 * Build a predicate which evaluates to <code>true</code> for the given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param values Values defining the predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate<T, X> values(final Collection<T> values) {
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
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> values1(final Collection<T1> values) {
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
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> values2(final Collection<T2> values) {
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
