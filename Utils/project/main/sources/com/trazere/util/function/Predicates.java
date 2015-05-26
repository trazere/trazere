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
package com.trazere.util.function;

import com.trazere.core.lang.ObjectUtils;
import com.trazere.util.collection.CollectionUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link Predicates} class provides various factories of predicates.
 * 
 * @see Predicate1
 * @see Predicate2
 */
public class Predicates {
	/**
	 * Builds a one argument predicate that evaluates to the given result for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> constant(final boolean result) {
		return new Predicate1<T, X>() {
			@Override
			public boolean evaluate(final T value) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate that evaluates to the given result for all values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param result The result.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> constant2(final boolean result) {
		return new Predicate2<T1, T2, X>() {
			@Override
			public boolean evaluate(final T1 value1, final T2 value2) {
				return result;
			}
		};
	}
	
	/**
	 * Builds a one argument predicate that evaluates to <code>true</code> for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate1<T, X> all() {
		return (Predicate1<T, X>) _ALL;
	}
	
	private static final Predicate1<?, ?> _ALL = Predicates.<Object, RuntimeException>constant(true);
	
	/**
	 * Builds a two arguments predicate that evaluates to <code>true</code> for all pairs of values.
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
	
	private static final Predicate2<?, ?, ?> _ALL2 = Predicates.<Object, Object, RuntimeException>constant2(true);
	
	/**
	 * Builds a one argument predicate that evaluates to <code>false</code> for all values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate1<T, X> none() {
		return (Predicate1<T, X>) _NONE;
	}
	
	private static final Predicate1<?, ?> _NONE = Predicates.<Object, RuntimeException>constant(false);
	
	/**
	 * Builds a two arguments predicate that evaluates to <code>false</code> for all pairs of values.
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
	
	private static final Predicate2<?, ?, ?> _NONE2 = Predicates.<Object, Object, RuntimeException>constant2(false);
	
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
			@Override
			public boolean evaluate(final T value)
			throws X {
				return !predicate.evaluate(value);
			}
		};
	}
	
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
			@Override
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return !predicate.evaluate(value1, value2);
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
			@Override
			public boolean evaluate(final T value)
			throws X {
				return predicate1.evaluate(value) && predicate2.evaluate(value);
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
			@Override
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate1.evaluate(value1, value2) && predicate2.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a one argument predicate corresponding to the conjonction of the given one argument predicates.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicates The predicates.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate1<T, X> and(final Collection<? extends Predicate1<? super T, ? extends X>> predicates) {
		assert null != predicates;
		
		if (predicates.size() < 2) {
			return (Predicate1<T, X>) CollectionUtils.any(predicates).get(Predicates.<T, X>all());
		} else {
			return new Predicate1<T, X>() {
				@Override
				public boolean evaluate(final T value)
				throws X {
					for (final Predicate1<? super T, ? extends X> predicate : predicates) {
						if (!predicate.evaluate(value)) {
							return false;
						}
					}
					return true;
				}
			};
		}
	}
	
	/**
	 * Builds a two arguments predicate corresponding to the conjonction of the given two arguments predicates.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicates The predicates.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> and2(final Collection<? extends Predicate2<? super T1, ? super T2, ? extends X>> predicates) {
		assert null != predicates;
		
		if (predicates.size() < 2) {
			return (Predicate2<T1, T2, X>) CollectionUtils.any(predicates).get(Predicates.<T1, T2, X>none2());
		} else {
			return new Predicate2<T1, T2, X>() {
				@Override
				public boolean evaluate(final T1 value1, final T2 value2)
				throws X {
					for (final Predicate2<? super T1, ? super T2, ? extends X> predicate : predicates) {
						if (!predicate.evaluate(value1, value2)) {
							return false;
						}
					}
					return true;
				}
			};
		}
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
			@Override
			public boolean evaluate(final T value)
			throws X {
				return predicate1.evaluate(value) || predicate2.evaluate(value);
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
			@Override
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate1.evaluate(value1, value2) || predicate2.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Builds a one argument predicate corresponding to the disjunction of the given one argument predicates.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicates The predicates.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Predicate1<T, X> or(final Collection<? extends Predicate1<? super T, ? extends X>> predicates) {
		assert null != predicates;
		
		if (predicates.size() < 2) {
			return (Predicate1<T, X>) CollectionUtils.any(predicates).get(Predicates.<T, X>none());
		} else {
			return new Predicate1<T, X>() {
				@Override
				public boolean evaluate(final T value)
				throws X {
					for (final Predicate1<? super T, ? extends X> predicate : predicates) {
						if (predicate.evaluate(value)) {
							return true;
						}
					}
					return false;
				}
			};
		}
	}
	
	/**
	 * Builds a two arguments predicate corresponding to the disjunction of the given two arguments predicates.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicates The predicates.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> or2(final Collection<? extends Predicate2<? super T1, ? super T2, ? extends X>> predicates) {
		assert null != predicates;
		
		if (predicates.size() < 2) {
			return (Predicate2<T1, T2, X>) CollectionUtils.any(predicates).get(Predicates.<T1, T2, X>none2());
		} else {
			return new Predicate2<T1, T2, X>() {
				@Override
				public boolean evaluate(final T1 value1, final T2 value2)
				throws X {
					for (final Predicate2<? super T1, ? super T2, ? extends X> predicate : predicates) {
						if (predicate.evaluate(value1, value2)) {
							return true;
						}
					}
					return false;
				}
			};
		}
	}
	
	/**
	 * Transforms the given predicate using the given function.
	 * 
	 * @param <T1> Type of the values.
	 * @param <T2> Type of the mapped values.
	 * @param <X> Type of the exceptions.
	 * @param function The (inner) function.
	 * @param predicate The (outer) predicate.
	 * @return The built function.
	 */
	public static <T1, T2, X extends Exception> Predicate1<T1, X> map(final Function1<? super T1, ? extends T2, ? extends X> function, final Predicate1<? super T2, ? extends X> predicate) {
		assert null != predicate;
		assert null != function;
		
		return new Predicate1<T1, X>() {
			@Override
			public boolean evaluate(final T1 value)
			throws X {
				return predicate.evaluate(function.evaluate(value));
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param value The value. May be <code>null</code>.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> value(final T value) {
		return new Predicate1<T, X>() {
			@Override
			public boolean evaluate(final T value_) {
				return ObjectUtils.safeEquals(value, value_);
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for any given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> values(final T... values) {
		assert null != values;
		
		return values(new HashSet<T>(Arrays.asList(values)));
	}
	
	/**
	 * Builds a predicate that evaluates to <code>true</code> for any given values.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param values The values.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> values(final Collection<? extends T> values) {
		assert null != values;
		
		return new Predicate1<T, X>() {
			@Override
			public boolean evaluate(final T value) {
				return values.contains(value);
			}
		};
	}
	
	/**
	 * Builds a one argument predicates that accepts each value only once.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	public static <T, X extends Exception> Predicate1<T, X> normalizer() {
		return new Predicate1<T, X>() {
			private final Set<T> _visitedValues = new HashSet<T>();
			
			@Override
			public boolean evaluate(final T value)
			throws X {
				return _visitedValues.add(value);
			}
		};
	}
	
	/**
	 * Builds a predicate that evaluates to the given value.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Predicate1<Boolean, X> identity() {
		return (Predicate1<Boolean, X>) _IDENTITY;
	}
	
	private static final Predicate1<Boolean, ?> _IDENTITY = new Predicate1<Boolean, RuntimeException>() {
		@Override
		public boolean evaluate(final Boolean value) {
			assert null != value;
			
			return value.booleanValue();
		}
	};
	
	/**
	 * Builds a one argument predicate from the given one argument function.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built predicate.
	 */
	public static final <T, X extends Exception> Predicate1<T, X> fromFunction(final Function1<? super T, Boolean, ? extends X> function) {
		assert null != function;
		
		return new Predicate1<T, X>() {
			@Override
			public boolean evaluate(final T value)
			throws X {
				return function.evaluate(value).booleanValue();
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate from the given two arguments function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param function The lifted function.
	 * @return The built predicate.
	 */
	public static final <T1, T2, X extends Exception> Predicate2<T1, T2, X> fromFunction(final Function2<? super T1, ? super T2, Boolean, ? extends X> function) {
		assert null != function;
		
		return new Predicate2<T1, T2, X>() {
			@Override
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return function.evaluate(value1, value2).booleanValue();
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate that lifts the given one argument predicate on the first value.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The lifted predicate for the first values.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> lift2(final Predicate1<? super T1, ? extends X> predicate) {
		assert null != predicate;
		
		return new Predicate2<T1, T2, X>() {
			@Override
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate.evaluate(value1);
			}
		};
	}
	
	/**
	 * Builds a two arguments predicate that lifts the given one argument predicate on the second value.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The lifted predicate for the second values.
	 * @return The built predicate.
	 */
	public static <T1, T2, X extends Exception> Predicate2<T1, T2, X> lift2Second(final Predicate1<? super T2, ? extends X> predicate) {
		assert null != predicate;
		
		return new Predicate2<T1, T2, X>() {
			@Override
			public boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate.evaluate(value2);
			}
		};
	}
	
	private Predicates() {
		// Prevents instantiation.
	}
}
