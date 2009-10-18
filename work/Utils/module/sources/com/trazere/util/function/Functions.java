/*
 *  Copyright 2006-2009 Julien Dufour
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

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.collection.Multimap;
import com.trazere.util.lang.Factory;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import com.trazere.util.type.Tuple3;
import com.trazere.util.type.Tuple4;
import com.trazere.util.type.Tuple5;
import java.util.Collection;
import java.util.Map;

/**
 * The {@link Functions} class provides various common functions.
 * 
 * @see Function1
 * @see Function2
 */
public class Functions {
	private static final Function1<?, ?, ?> _IDENTITY = new Function1<Object, Object, RuntimeException>() {
		public Object evaluate(final Object value) {
			return value;
		}
	};
	
	/**
	 * Build an identity function.
	 * 
	 * @param <T> Type of the argument and result values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, T, X> identity() {
		return (Function1<T, T, X>) _IDENTITY;
	}
	
	/**
	 * Build a function corresponding to the composition of the given functions (f . g).
	 * 
	 * @param <T1> Type of the argument values of the outer function.
	 * @param <T2> Type of the results.
	 * @param <X> Type of the exceptions.
	 * @param f The outer function.
	 * @param g The inner function.
	 * @return The built function.
	 */
	public static <T1, T2, X extends Exception> Function0<T2, X> compose(final Function1<? super T1, ? extends T2, ? extends X> f, final Function0<? extends T1, ? extends X> g) {
		assert null != f;
		assert null != g;
		
		// Build.
		return new Function0<T2, X>() {
			public T2 evaluate()
			throws X {
				return f.evaluate(g.evaluate());
			}
		};
	}
	
	/**
	 * Build a function corresponding to the composition of the given functions (f . g).
	 * 
	 * @param <T1> Type of the argument values of the inner function.
	 * @param <T2> Type of the argument values of the outer function.
	 * @param <T3> Type of the results.
	 * @param <X> Type of the exceptions.
	 * @param f The outer function.
	 * @param g The inner function.
	 * @return The built function.
	 */
	public static <T1, T2, T3, X extends Exception> Function1<T1, T3, X> compose(final Function1<? super T2, ? extends T3, ? extends X> f, final Function1<T1, ? extends T2, ? extends X> g) {
		assert null != f;
		assert null != g;
		
		// Build.
		return new Function1<T1, T3, X>() {
			public T3 evaluate(final T1 value)
			throws X {
				return f.evaluate(g.evaluate(value));
			}
		};
	}
	
	/**
	 * Build a zero argument function which always evaluates to the given value.
	 * 
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param result Result value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <R, X extends Exception> Function0<R, X> constant0(final R result) {
		return new Function0<R, X>() {
			public R evaluate() {
				return result;
			}
		};
	}
	
	/**
	 * Build a one argument function which always evaluates to the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param result Result value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<T, R, X> constant1(final R result) {
		return new Function1<T, R, X>() {
			public R evaluate(final T value) {
				return result;
			}
		};
	}
	
	/**
	 * Build a function corresponding to the given factory.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param factory The predicate.
	 * @return The built function.
	 */
	public static final <T, X extends Exception> Function0<T, X> lift0(final Factory<? extends T, ? extends X> factory) {
		assert null != factory;
		
		return new Function0<T, X>() {
			public T evaluate()
			throws X {
				return factory.build();
			}
		};
	}
	
	/**
	 * Build a one argument function which lifts the given zero arguments function.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The wrapped function.
	 * @return
	 */
	public static <T, R, X extends Exception> Function1<T, R, X> lift1(final Function0<? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function1<T, R, X>() {
			public R evaluate(final T value)
			throws X {
				return function.evaluate();
			}
		};
	}
	
	/**
	 * Build a one argument function which lifts the given zero arguments function.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param factory The wrapped factory.
	 * @return
	 */
	public static <T, R, X extends Exception> Function1<T, R, X> lift1(final Factory<? extends R, ? extends X> factory) {
		assert null != factory;
		
		return new Function1<T, R, X>() {
			public R evaluate(final T value)
			throws X {
				return factory.build();
			}
		};
	}
	
	/**
	 * Build a one argument function which lifts the given one argument predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built function.
	 */
	public static final <T, X extends Exception> Function1<T, Boolean, X> lift1(final Predicate1<? super T, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function1<T, Boolean, X>() {
			public Boolean evaluate(final T value)
			throws X {
				return predicate.evaluate(value);
			}
		};
	}
	
	/**
	 * Build a two argument function which lifts the given zero arguments function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The wrapped function.
	 * @return
	 */
	public static <T1, T2, R, X extends Exception> Function2<T1, T2, R, X> lift2(final Function0<? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function2<T1, T2, R, X>() {
			public R evaluate(final T1 value1, final T2 value2)
			throws X {
				return function.evaluate();
			}
		};
	}
	
	/**
	 * Build a two argument function which lifts the given zero arguments function.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The wrapped function.
	 * @return
	 */
	public static <T1, T2, R, X extends Exception> Function2<T1, T2, R, X> lift2(final Function1<? super T1, ? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function2<T1, T2, R, X>() {
			public R evaluate(final T1 value1, final T2 value2)
			throws X {
				return function.evaluate(value1);
			}
		};
	}
	
	/**
	 * Build a two arguments function which lifts the given one arguments predicate.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built function.
	 */
	public static final <T1, T2, X extends Exception> Function2<T1, T2, Boolean, X> lift2(final Predicate2<? super T1, ? super T2, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function2<T1, T2, Boolean, X>() {
			public Boolean evaluate(final T1 value1, final T2 value2)
			throws X {
				return predicate.evaluate(value1, value2);
			}
		};
	}
	
	/**
	 * Build a function returning the string representation of the values.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, String, X> toString() {
		return (Function1<T, String, X>) _TO_STRING;
	}
	
	private static final Function1<?, String, ?> _TO_STRING = new Function1<Object, String, RuntimeException>() {
		public String evaluate(final Object value) {
			return value.toString();
		}
	};
	
	/**
	 * Build a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map and to <code>null</code> for the other keys.
	 * 
	 * @param <K> Type of the keys of the map (the argument values).
	 * @param <V> Type of the values of the map (the result values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function1<K, V, X> map(final Map<? super K, ? extends V> map) {
		assert null != map;
		
		// Build the function.
		return new Function1<K, V, X>() {
			public V evaluate(final K key) {
				return map.get(key);
			}
		};
	}
	
	/**
	 * Build a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map and to the default value for the other keys.
	 * 
	 * @param <K> Type of the keys of the map (the argument values).
	 * @param <V> Type of the values of the map (the result values).
	 * @param <X> Type of the exceptions.
	 * @param map The map.
	 * @param defaultValue The default value.
	 * @return The built function.
	 */
	public static <K, V, X extends Exception> Function1<K, V, X> map(final Map<? super K, ? extends V> map, final V defaultValue) {
		assert null != map;
		
		// Build the function.
		return new Function1<K, V, X>() {
			public V evaluate(final K key) {
				return CollectionUtils.get(map, key, defaultValue);
			}
		};
	}
	
	/**
	 * Build a function corresponding to the given map.
	 * <p>
	 * The built function evaluates to the values associated to the keys in the map wrapped into a {@link Maybe maybe} instance to reflect the domain of the
	 * map.
	 * 
	 * @param <T> Type of the keys of the map (the argument values).
	 * @param <R> Type of the values of the map (the result values).
	 * @param <X> Type of the exceptions.
	 * @param map Map defining the function to build.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<T, Maybe<R>, X> maybeMap(final Map<? super T, ? extends R> map) {
		assert null != map;
		
		// Build the function.
		return new Function1<T, Maybe<R>, X>() {
			public Maybe<R> evaluate(final T key) {
				if (map.containsKey(key)) {
					return Maybe.<R>some(map.get(key));
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Build a function corresponding to the given multimap.
	 * 
	 * @param <K> Type of the keys (the argument values).
	 * @param <C> Type of the collections of values (the result values).
	 * @param <X> Type of the exceptions.
	 * @param map The multimap.
	 * @return The built function.
	 */
	public static <K, C extends Collection<?>, X extends Exception> Function1<K, C, X> multimap(final Multimap<? super K, ?, ? extends C> map) {
		assert null != map;
		
		// Build the function.
		return new Function1<K, C, X>() {
			public C evaluate(final K key) {
				return map.get(key);
			}
		};
	}
	
	/**
	 * Build a function wrapping its argument in a {@link Maybe} instance according to the given predicate.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built function.
	 */
	public static final <T, X extends Exception> Function1<T, Maybe<T>, X> makeMaybe(final Predicate1<? super T, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function1<T, Maybe<T>, X>() {
			public Maybe<T> evaluate(final T value)
			throws X {
				if (predicate.evaluate(value)) {
					return Maybe.some(value);
				} else {
					return Maybe.none();
				}
			}
		};
	}
	
	/**
	 * Build a function wrapping its argument in a {@link Maybe.Some} instance.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, Maybe<T>, X> makeSome() {
		return (Function1<T, Maybe<T>, X>) _MAKE_SOME;
	}
	
	private static final Function1<?, ?, ?> _MAKE_SOME = new Function1<Object, Maybe<Object>, RuntimeException>() {
		public Maybe<Object> evaluate(final Object value) {
			return Maybe.some(value);
		}
	};
	
	/**
	 * Build a function wrapping its argument in a {@link Either.Left} instance.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <L, R, X extends Exception> Function1<L, Either<L, R>, X> makeLeft() {
		return (Function1<L, Either<L, R>, X>) _MAKE_LEFT;
	}
	
	private static final Function1<?, ?, ?> _MAKE_LEFT = new Function1<Object, Either<?, ?>, RuntimeException>() {
		public Either<?, ?> evaluate(final Object value) {
			return Either.left(value);
		}
	};
	
	/**
	 * Build a function wrapping its argument in a {@link Either.Left} instance.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <L, R, X extends Exception> Function1<R, Either<L, R>, X> makeRight() {
		return (Function1<R, Either<L, R>, X>) _MAKE_RIGHT;
	}
	
	private static final Function1<?, ?, ?> _MAKE_RIGHT = new Function1<Object, Either<?, ?>, RuntimeException>() {
		public Either<?, ?> evaluate(final Object value) {
			return Either.right(value);
		}
	};
	
	/**
	 * Build a function wrapping its arguments in a {@link Tuple2} instance.
	 * 
	 * @param <T1> Type of the first value.
	 * @param <T2> Type of the second value.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function2<T1, T2, Tuple2<T1, T2>, X> makeTuple2() {
		return (Function2<T1, T2, Tuple2<T1, T2>, X>) _MAKE_TUPLE2;
	}
	
	private static final Function2<?, ?, ?, ?> _MAKE_TUPLE2 = new Function2<Object, Object, Tuple2<Object, Object>, RuntimeException>() {
		public Tuple2<Object, Object> evaluate(final Object value1, final Object value2) {
			return Tuple2.build(value1, value2);
		}
	};
	
	/**
	 * Build a function getting the first value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function1<Tuple2<? extends T1, ? extends T2>, T1, X> first() {
		return (Function1<Tuple2<? extends T1, ? extends T2>, T1, X>) _FIRST;
	}
	
	private static final Function1<?, ?, ?> _FIRST = new Function1<Tuple2<Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple2<Object, Object> value) {
			return value.getFirst();
		}
	};
	
	/**
	 * Build a function getting the second value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, X extends Exception> Function1<Tuple2<? extends T1, ? extends T2>, T2, X> second() {
		return (Function1<Tuple2<? extends T1, ? extends T2>, T2, X>) _SECOND;
	}
	
	private static final Function1<?, ?, ?> _SECOND = new Function1<Tuple2<Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple2<Object, Object> value) {
			return value.getSecond();
		}
	};
	
	/**
	 * Build a function getting the third value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <T3> Type of the third values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, T3, X extends Exception> Function1<Tuple3<? extends T1, ? extends T2, ? extends T3>, T3, X> third() {
		return (Function1<Tuple3<? extends T1, ? extends T2, ? extends T3>, T3, X>) _THIRD;
	}
	
	private static final Function1<?, ?, ?> _THIRD = new Function1<Tuple3<Object, Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple3<Object, Object, Object> value) {
			return value.getThird();
		}
	};
	
	/**
	 * Build a function getting the fourth value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <T3> Type of the third values of the tuples.
	 * @param <T4> Type of the fourth values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, T3, T4, X extends Exception> Function1<Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, T4, X> fourth() {
		return (Function1<Tuple4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, T4, X>) _FOURTH;
	}
	
	private static final Function1<?, ?, ?> _FOURTH = new Function1<Tuple4<Object, Object, Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple4<Object, Object, Object, Object> value) {
			return value.getFourth();
		}
	};
	
	/**
	 * Build a function getting the fifth value of a tuple.
	 * 
	 * @param <T1> Type of the first values of the tuples.
	 * @param <T2> Type of the second values of the tuples.
	 * @param <T3> Type of the third values of the tuples.
	 * @param <T4> Type of the fourth values of the tuples.
	 * @param <T5> Type of the fifth values of the tuples.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T1, T2, T3, T4, T5, X extends Exception> Function1<Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, T5, X> fifth() {
		return (Function1<Tuple5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, T5, X>) _FIFTH;
	}
	
	private static final Function1<?, ?, ?> _FIFTH = new Function1<Tuple5<Object, Object, Object, Object, Object>, Object, RuntimeException>() {
		public Object evaluate(final Tuple5<Object, Object, Object, Object, Object> value) {
			return value.getFifth();
		}
	};
	
	private static final Function1<?, ?, ?> _EVALUATE0 = new Function1<Function0<Object, RuntimeException>, Object, RuntimeException>() {
		public Object evaluate(final Function0<Object, RuntimeException> function) {
			return function.evaluate();
		}
	};
	
	/**
	 * Build a function which takes a zero arguments function as argument and evalutes to its result.
	 * 
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <R, X extends Exception> Function1<Function0<? extends R, ? extends X>, R, X> evaluate0() {
		return (Function1<Function0<? extends R, ? extends X>, R, X>) _EVALUATE0;
	}
	
	/**
	 * Build a function which takes a one argument function as argument and evalutes to its result for the given value.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param value The argument value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<Function1<? super T, ? extends R, ? extends X>, R, X> evaluate1(final T value) {
		return new Function1<Function1<? super T, ? extends R, ? extends X>, R, X>() {
			public R evaluate(final Function1<? super T, ? extends R, ? extends X> function)
			throws X {
				return function.evaluate(value);
			}
		};
	}
	
	/**
	 * Build a function which takes a two arguments function as argument and evalutes to its result for the given values.
	 * 
	 * @param <T1> Type of the first argument values.
	 * @param <T2> Type of the second argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param value1 The first argument value. May be <code>null</code>.
	 * @param value2 The second argument value. May be <code>null</code>.
	 * @return The built function.
	 */
	public static <T1, T2, R, X extends Exception> Function1<Function2<? super T1, ? super T2, ? extends R, ? extends X>, R, X> evaluate2(final T1 value1, final T2 value2) {
		return new Function1<Function2<? super T1, ? super T2, ? extends R, ? extends X>, R, X>() {
			public R evaluate(final Function2<? super T1, ? super T2, ? extends R, ? extends X> function)
			throws X {
				return function.evaluate(value1, value2);
			}
		};
	}
	
	private Functions() {
		// Prevent instantiation.
	}
}