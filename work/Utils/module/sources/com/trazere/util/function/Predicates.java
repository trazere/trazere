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
package com.trazere.util.function;

import com.trazere.util.lang.LangUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * The {@link Predicates} class provides various common predicate functions.
 * 
 * @see Predicate1
 * @see Predicate2
 */
public class Predicates {
	/**
	 * Builds a predicate which evaluates to <code>true</code> for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate1<T, X> all() {
		return (Predicate1<T, X>) _ALL;
	}
	
	private static final Predicate1<?, ?> _ALL = new Predicate1<Object, Exception>() {
		public boolean evaluate(final Object value) {
			return true;
		}
	};
	
	/**
	 * Builds a predicate which evaluates to <code>false</code> for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate1<T, X> none() {
		return (Predicate1<T, X>) _NONE;
	}
	
	private static final Predicate1<?, ?> _NONE = new Predicate1<Object, Exception>() {
		public boolean evaluate(final Object value) {
			return false;
		}
	};
	
	/**
	 * Builds a predicate corresponding to the inverse of the given predicate.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> not(final Predicate1<? super T, ? extends X> predicate) {
		assert null != predicate;
		
		return new Predicate1<T, X>() {
			public boolean evaluate(final T value)
			throws X {
				return !predicate.evaluate(value);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the conjonction of the given predicates.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> and(final Predicate1<? super T, ? extends X> predicate1, final Predicate1<? super T, ? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return new Predicate1<T, X>() {
			public boolean evaluate(final T value)
			throws X {
				return predicate1.evaluate(value) && predicate2.evaluate(value);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the disjunction of the given predicates.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> or(final Predicate1<? super T, ? extends X> predicate1, final Predicate1<? super T, ? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return new Predicate1<T, X>() {
			public boolean evaluate(final T value)
			throws X {
				return predicate1.evaluate(value) || predicate2.evaluate(value);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the composition of the given predicate and function (f . g).
	 * 
	 * @param <T1> Type of the argument values of the (outer) predicate.
	 * @param <T2> Type of the argument values of the (inner) function.
	 * @param <X> Type of the exceptions.
	 * @param g The (outer) predicate.
	 * @param f The (inner) function.
	 * @return The built function.
	 */
	public static <T1, T2, X extends Exception> Predicate1<T1, X> compose(final Predicate1<? super T2, ? extends X> g, final Function1<T1, ? extends T2, ? extends X> f) {
		assert null != g;
		assert null != f;
		
		return new Predicate1<T1, X>() {
			public boolean evaluate(final T1 value)
			throws X {
				return g.evaluate(f.evaluate(value));
			}
		};
	}
	
	/**
	 * Builds a predicate which evaluates to <code>true</code> for the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> equal(final T value) {
		return new Predicate1<T, X>() {
			public boolean evaluate(final T value_) {
				return LangUtils.equals(value, value_);
			}
		};
	}
	
	/**
	 * Builds a predicate which evaluates to <code>true</code> for any given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> any(final T... values) {
		assert null != values;
		
		return any(new HashSet<T>(Arrays.asList(values)));
	}
	
	/**
	 * Builds a predicate which evaluates to <code>true</code> for any given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> any(final Collection<? extends T> values) {
		assert null != values;
		
		return new Predicate1<T, X>() {
			public boolean evaluate(final T value) {
				return values.contains(value);
			}
		};
	}
	
	/**
	 * Builds a predicate which evaluates to <code>true</code> for empty strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Predicate1<String, X> emptyString() {
		return (Predicate1<String, X>) _EMPTY_STRING;
	}
	
	private static Predicate1<String, ?> _EMPTY_STRING = new Predicate1<String, RuntimeException>() {
		public boolean evaluate(final String value) {
			assert null != value;
			
			return 0 == value.length();
		}
	};
	
	/**
	 * Builds a predicate which evaluates to <code>true</code> for empty collections.
	 * 
	 * @param <C> Type of the collections.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <C extends Collection<?>, X extends Exception> Predicate1<C, X> empty() {
		return (Predicate1<C, X>) _EMPTY;
	}
	
	private static Predicate1<Collection<?>, ?> _EMPTY = new Predicate1<Collection<?>, RuntimeException>() {
		public boolean evaluate(final Collection<?> collection) {
			assert null != collection;
			
			return collection.isEmpty();
		}
	};
	
	/**
	 * Builds a two arguments predicate which evaluates to <code>true</code> for all pairs of values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> all2() {
		return (Predicate2<T1, T2, X>) _ALL2;
	}
	
	private static final Predicate2<?, ?, ?> _ALL2 = new Predicate2<Object, Object, Exception>() {
		public boolean evaluate(final Object value1, final Object value2) {
			return true;
		}
	};
	
	/**
	 * Builds a two arguments predicate which evaluates to <code>false</code> for all pairs of values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> none2() {
		return (Predicate2<T1, T2, X>) _NONE2;
	}
	
	private static final Predicate2<?, ?, ?> _NONE2 = new Predicate2<Object, Object, Exception>() {
		public boolean evaluate(final Object value1, final Object value2) {
			return false;
		}
	};
	
	/**
	 * Builds a predicate corresponding to the inverse of the given predicate.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> not(final Predicate2<? super T1, ? super T2, ? extends X> predicate) {
		assert null != predicate;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return !predicate.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the conjonction of the given predicates.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> and(final Predicate2<? super T1, ? super T2, ? extends X> predicate1, final Predicate2<? super T1, ? super T2, ? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate1.evaluate(value1, value2) && predicate2.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a predicate corresponding to the disjunction of the given predicates.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate1 The first predicate.
	 * @param predicate2 The second predicate.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> or(final Predicate2<? super T1, ? super T2, ? extends X> predicate1, final Predicate2<? super T1, ? super T2, ? extends X> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate1.evaluate(value1, value2) || predicate2.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate which evaluates to <code>true</code> for all pairs whose first value belongs to the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> any2First(final Collection<? extends T1> values) {
		assert null != values;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2) {
				return values.contains(value1);
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate which evaluates to <code>true</code> for all pairs whose second value belongs to the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> any2Second(final Collection<? extends T2> values) {
		assert null != values;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2) {
				return values.contains(value2);
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate which lifts the given one argument predicate.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The lifted predicate.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> lift2(final Predicate1<? super T1, ? extends X> predicate) {
		assert null != predicate;
		
		return new Predicate2<T1, T2, X>() {
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate.evaluate(value1);
			}
		};
	}
	
	private Predicates() {
		// Prevent instantiation.
	}
}
