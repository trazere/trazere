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
package com.trazere.core.functional;

import com.trazere.core.collection.Sets;
import com.trazere.core.lang.ObjectUtils;
import java.util.Collection;

/**
 * The {@link Predicates} class provides various factories of predicates.
 */
public class Predicates {
	/**
	 * Builds a predicate that evaluates to <code>true</code> for all arguments.
	 * 
	 * @param <A> Type of the arguments.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A> Predicate<A> all() {
		return (Predicate<A>) ALL;
	}
	
	private static final Predicate<?> ALL = arg -> true;
	
	/**
	 * Builds a predicate that evaluates to <code>false</code> for all arguments.
	 * 
	 * @param <A> Type of the arguments.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A> Predicate<A> none() {
		return (Predicate<A>) NONE;
	}
	
	private static final Predicate<?> NONE = arg -> false;
	
	/**
	 * Builds a predicate that evaluates to the given result for all arguments.
	 * 
	 * @param <A> Type of the arguments.
	 * @param result Result of the predicate.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> constant(final boolean result) {
		return result ? all() : none();
	}
	
	/**
	 * Builds a predicate corresponding to the logical negation of the given predicate.
	 * 
	 * @param <A> Type of the arguments.
	 * @param predicate Predicate to inverse.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> not(final Predicate<? super A> predicate) {
		assert null != predicate;
		
		return arg -> !predicate.evaluate(arg);
	}
	
	/**
	 * Builds a predicate corresponding to the logical conjunction of the given predicates.
	 * 
	 * @param <A> Type of the arguments.
	 * @param predicate1 First predicate to combine.
	 * @param predicate2 Second predicate to combine.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> and(final Predicate<? super A> predicate1, final Predicate<? super A> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return arg -> predicate1.evaluate(arg) && predicate2.evaluate(arg);
	}
	
	/**
	 * Builds a predicate corresponding to the logical conjunction of the given predicates.
	 * 
	 * @param <A> Type of the arguments.
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A> Predicate<A> and(final Collection<? extends Predicate<? super A>> predicates) {
		if (predicates.isEmpty()) {
			return all();
		} else if (1 == predicates.size()) {
			return (Predicate<A>) predicates.iterator().next();
		} else {
			return (final A arg) -> {
				for (final Predicate<? super A> predicate : predicates) {
					if (!predicate.evaluate(arg)) {
						return false;
					}
				}
				return true;
			};
		}
	}
	
	/**
	 * Builds a predicate corresponding to the logical disjunction of the given predicates.
	 * 
	 * @param <A> Type of the arguments.
	 * @param predicate1 First predicate to combine.
	 * @param predicate2 Second predicate to combine.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> or(final Predicate<? super A> predicate1, final Predicate<? super A> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return arg -> predicate1.evaluate(arg) || predicate2.evaluate(arg);
	}
	
	/**
	 * Builds a predicate corresponding to the logical disjunction of the given predicates.
	 * 
	 * @param <A> Type of the arguments.
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A> Predicate<A> or(final Collection<? extends Predicate<? super A>> predicates) {
		if (predicates.isEmpty()) {
			return none();
		} else if (1 == predicates.size()) {
			return (Predicate<A>) predicates.iterator().next();
		} else {
			return (final A arg) -> {
				for (final Predicate<? super A> predicate : predicates) {
					if (predicate.evaluate(arg)) {
						return true;
					}
				}
				return false;
			};
		}
	}
	
	// TODO: fromValue ?
	/**
	 * Builds a predicate that evaluates to <code>true</code> for the given value.
	 * 
	 * @param <A> Type of the arguments.
	 * @param value Value to accept.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> value(final A value) {
		return arg -> ObjectUtils.safeEquals(arg, value);
	}
	
	// TODO: fromValues ?
	/**
	 * Builds a predicate that evaluates to <code>true</code> for the given values.
	 * 
	 * @param <A> Type of the arguments.
	 * @param values Values to accept.
	 * @return The built predicate.
	 */
	@SafeVarargs
	public static <A> Predicate<A> values(final A... values) {
		assert null != values;
		
		return values(Sets.fromElements(values));
	}
	
	// TODO: fromValues or fromCollection ?
	/**
	 * Builds a predicate that evaluates to <code>true</code> for the given values.
	 * 
	 * @param <A> Type of the arguments.
	 * @param values Values to accept.
	 * @return The built predicate.
	 */
	public static <A> Predicate<A> values(final Collection<? extends A> values) {
		assert null != values;
		
		return arg -> values.contains(arg);
	}
	
	/**
	 * Builds a predicate that lifts the given function.
	 * 
	 * @param <A> Type of the arguments.
	 * @param function Function to lift.
	 * @return The built predicate.
	 */
	public static final <A> Predicate<A> fromFunction(final Function<? super A, Boolean> function) {
		assert null != function;
		
		return arg -> function.evaluate(arg).booleanValue();
	}
	
	//	// TODO: LangPredicates ?
	//	/**
	//	 * Builds a predicate that evaluates to the given value.
	//	 *
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built predicate.
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <X extends Exception> Predicate1<Boolean, X> identity() {
	//		return (Predicate1<Boolean, X>) _IDENTITY;
	//	}
	//
	//	private static final Predicate1<Boolean, ?> _IDENTITY = new Predicate1<Boolean, RuntimeException>() {
	//		@Override
	//		public boolean evaluate(final Boolean value) {
	//			assert null != value;
	//
	//			return value.booleanValue();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a one argument predicates that accepts each value only once.
	//	 *
	//	 * @param <T> Type of the values.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built predicate.
	//	 */
	//	public static <T, X extends Exception> Predicate1<T, X> normalizer() {
	//		return new Predicate1<T, X>() {
	//			private final Set<T> _visitedValues = new HashSet<T>();
	//
	//			@Override
	//			public boolean evaluate(final T value)
	//			throws X {
	//				return _visitedValues.add(value);
	//			}
	//		};
	//	}
	
	/**
	 * Builds a two arguments predicate that evaluates to the given result for all arguments.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param result Result of the predicate.
	 * @return The built predicate.
	 */
	public static <A1, A2> Predicate2<A1, A2> constant2(final boolean result) {
		return result ? all2() : none2();
	}
	
	/**
	 * Builds a two arguments predicate that evaluates to <code>true</code> for all arguments.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A1, A2> Predicate2<A1, A2> all2() {
		return (Predicate2<A1, A2>) ALL2;
	}
	
	private static final Predicate2<?, ?> ALL2 = (arg1, arg2) -> true;
	
	/**
	 * Builds a two arguments predicate that evaluates to <code>false</code> for all arguments.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A1, A2> Predicate2<A1, A2> none2() {
		return (Predicate2<A1, A2>) NONE2;
	}
	
	private static final Predicate2<?, ?> NONE2 = (arg1, arg2) -> false;
	
	/**
	 * Builds a two argument predicate corresponding to the logical inverse of the given two arguments predicate.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicate Predicate to inverse.
	 * @return The built predicate.
	 */
	public static <A1, A2> Predicate2<A1, A2> not2(final Predicate2<? super A1, ? super A2> predicate) {
		assert null != predicate;
		
		return (arg1, arg2) -> !predicate.evaluate(arg1, arg2);
	}
	
	/**
	 * Builds a two arguments predicate corresponding to the logical conjunction of the given two arguments predicates.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicate1 First predicate to combine.
	 * @param predicate2 Second predicate to combine.
	 * @return The built predicate.
	 */
	public static <A1, A2> Predicate2<A1, A2> and2(final Predicate2<? super A1, ? super A2> predicate1, final Predicate2<? super A1, ? super A2> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return (arg1, arg2) -> predicate1.evaluate(arg1, arg2) && predicate2.evaluate(arg1, arg2);
	}
	
	/**
	 * Builds a two arguments predicate corresponding to the logical conjunction of the given two arguments predicates.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A1, A2> Predicate2<A1, A2> and2(final Collection<? extends Predicate2<? super A1, ? super A2>> predicates) {
		if (predicates.isEmpty()) {
			return all2();
		} else if (1 == predicates.size()) {
			return (Predicate2<A1, A2>) predicates.iterator().next();
		} else {
			return (final A1 arg1, final A2 arg2) -> {
				for (final Predicate2<? super A1, ? super A2> predicate : predicates) {
					if (!predicate.evaluate(arg1, arg2)) {
						return false;
					}
				}
				return true;
			};
		}
	}
	
	/**
	 * Builds a two arguments predicate corresponding to the logical disjunction of the given two arguments predicates.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicate1 First predicate to combine.
	 * @param predicate2 Second predicate to combine.
	 * @return The built predicate.
	 */
	public static <A1, A2> Predicate2<A1, A2> or2(final Predicate2<? super A1, ? super A2> predicate1, final Predicate2<? super A1, ? super A2> predicate2) {
		assert null != predicate1;
		assert null != predicate2;
		
		return (arg1, arg2) -> predicate1.evaluate(arg1, arg2) || predicate2.evaluate(arg1, arg2);
	}
	
	/**
	 * Builds a two arguments predicate corresponding to the logical disjunction of the given two arguments predicates.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param predicates Predicates to combine.
	 * @return The built predicate.
	 */
	@SuppressWarnings("unchecked")
	public static <A1, A2> Predicate2<A1, A2> or2(final Collection<? extends Predicate2<? super A1, ? super A2>> predicates) {
		if (predicates.isEmpty()) {
			return none2();
		} else if (1 == predicates.size()) {
			return (Predicate2<A1, A2>) predicates.iterator().next();
		} else {
			return (final A1 arg1, final A2 arg2) -> {
				for (final Predicate2<? super A1, ? super A2> predicate : predicates) {
					if (predicate.evaluate(arg1, arg2)) {
						return true;
					}
				}
				return false;
			};
		}
	}
	
	/**
	 * Builds a two arguments predicate that lifts the given two arguments function.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param function Function to lift.
	 * @return The built predicate.
	 */
	public static final <A1, A2> Predicate2<A1, A2> fromFunction(final Function2<? super A1, ? super A2, Boolean> function) {
		assert null != function;
		
		return (arg1, arg2) -> function.evaluate(arg1, arg2).booleanValue();
	}
	
	private Predicates() {
		// Prevents instantiation.
	}
}
