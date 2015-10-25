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

import com.trazere.util.accumulator.Accumulator1;
import com.trazere.util.accumulator.Accumulator2;
import com.trazere.util.collection.CheckedIterator;
import com.trazere.util.collection.CheckedIterators;
import com.trazere.util.collection.CollectionAccumulators;
import com.trazere.util.collection.Multimap;
import com.trazere.util.feed.Feed;
import com.trazere.util.lang.Counter;
import com.trazere.util.lang.InternalException;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

// TODO: dispatch in CollectionUtils, IteratorUtils and FeedUtils ?

/**
 * The {@link FunctionUtils} provides various helpers regarding predicates, functions and procedures.
 * 
 * @see Predicate1
 * @see Predicate2
 * @see Function1
 * @see Function2
 * @see Procedure1
 * @see Procedure2
 */
public class FunctionUtils {
	/**
	 * Tests whether any given value is accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> boolean isAny(final Predicate1<? super V, ? extends X> predicate, final Iterable<? extends V> values)
	throws X {
		return isAny(predicate, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Tests whether any value provided by the given iterator is accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> boolean isAny(final Predicate1<? super V, ? extends X> predicate, final Iterator<? extends V> values)
	throws X {
		return isAny(predicate, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Tests whether any value provided by the given iterator is accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> boolean isAny(final Predicate1<? super V, ? extends X> predicate, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != predicate;
		assert null != values;
		
		while (values.hasNext()) {
			if (predicate.evaluate(values.next())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether any value provided by the given feed is accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The feed providing the values to test.
	 * @return <code>true</code> if any value is accepted, <code>false</code> if all values are rejected.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> boolean isAny(final Predicate1<? super V, ? extends X> predicate, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		return isAny(predicate, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Tests whether any given binding is accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to test.
	 * @return <code>true</code> if any binding is accepted, <code>false</code> if all bindings are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> boolean isAny(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (predicate.evaluate(binding.getKey(), binding.getValue())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tests whether any given binding is accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to test.
	 * @return <code>true</code> if any binding is accepted, <code>false</code> if all bindings are rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> boolean isAny(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<? extends K, ? extends V, ?> bindings)
	throws X {
		return isAny_(predicate, bindings);
	}
	
	private static <K, V, X extends Exception> boolean isAny_(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<K, ? extends V, ?> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				if (predicate.evaluate(key, value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Tests whether all given values are accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> boolean areAll(final Predicate1<? super V, ? extends X> predicate, final Iterable<? extends V> values)
	throws X {
		assert null != values;
		
		return areAll(predicate, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Tests whether all values provided by the given iterator are accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> boolean areAll(final Predicate1<? super V, ? extends X> predicate, final Iterator<? extends V> values)
	throws X {
		return areAll(predicate, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Tests whether all values provided by the given iterator are accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> boolean areAll(final Predicate1<? super V, ? extends X> predicate, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != predicate;
		assert null != values;
		
		while (values.hasNext()) {
			if (!predicate.evaluate(values.next())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Tests whether all values provided by the given feed are accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The feed providing the values to test.
	 * @return <code>true</code> if all values are accepted, <code>false</code> if any value is rejected.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> boolean areAll(final Predicate1<? super V, ? extends X> predicate, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		return areAll(predicate, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Tests whether all given bindings are accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to test.
	 * @return <code>true</code> if all bindings are accepted, <code>false</code> if any binding is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> boolean areAll(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (!predicate.evaluate(binding.getKey(), binding.getValue())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Tests whether all given bindings are accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to test.
	 * @return <code>true</code> if all bindings are accepted, <code>false</code> if any binding is rejected.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> boolean areAll(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<? extends K, ? extends V, ?> bindings)
	throws X {
		return areAll_(predicate, bindings);
	}
	
	private static <K, V, X extends Exception> boolean areAll_(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<K, ? extends V, ?> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				if (!predicate.evaluate(key, value)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Gets the first given values accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> Maybe<V> first(final Predicate1<? super V, ? extends X> predicate, final Iterable<? extends V> values)
	throws X {
		assert null != values;
		
		return first(predicate, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Gets the first value provided by the given iterator accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> Maybe<V> first(final Predicate1<? super V, ? extends X> predicate, final Iterator<? extends V> values)
	throws X {
		return first(predicate, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Gets the first value provided by the given iterator accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> Maybe<V> first(final Predicate1<? super V, ? extends X> predicate, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != predicate;
		assert null != values;
		
		while (values.hasNext()) {
			final V value = values.next();
			if (predicate.evaluate(value)) {
				return Maybe.some(value);
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first value provided by the given feed accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The feed providing the values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> Maybe<V> first(final Predicate1<? super V, ? extends X> predicate, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		return first(predicate, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Gets the first given binding accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings.
	 * @return The first accepted binding.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> Maybe<Tuple2<K, V>> first(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			final V value = binding.getValue();
			if (predicate.evaluate(key, value)) {
				return Maybe.some(Tuple2.build(key, value));
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first given binding accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings.
	 * @return The first accepted binding.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> Maybe<Tuple2<K, V>> first(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<? extends K, ? extends V, ?> bindings)
	throws X {
		return first_(predicate, bindings).map(new Function1<Tuple2<? extends K, V>, Tuple2<K, V>, InternalException>() {
			@Override
			public Tuple2<K, V> evaluate(final Tuple2<? extends K, V> value) {
				return new Tuple2<K, V>(value.getFirst(), value.getSecond());
			}
		});
	}
	
	private static <K, V, X extends Exception> Maybe<Tuple2<K, V>> first_(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<K, ? extends V, ?> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				if (predicate.evaluate(key, value)) {
					return Maybe.some(Tuple2.build(key, value));
				}
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first value extracted by the given function from the given values.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result value.
	 * @param <X> Type of the exceptions.
	 * @param function The extraction function.
	 * @param values The values.
	 * @return The first extracted value.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, RV, X extends Exception> Maybe<RV> first(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends X> function, final Iterable<? extends V> values)
	throws X {
		assert null != values;
		
		return first(function, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Gets the first value extracted by the given function from the values provided by the given iterator.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result value.
	 * @param <X> Type of the exceptions.
	 * @param function The extraction function.
	 * @param values The iterator providing the values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, RV, X extends Exception> Maybe<RV> first(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends X> function, final Iterator<? extends V> values)
	throws X {
		return first(function, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Gets the first value extracted by the given function from the values provided by the given iterator.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result value.
	 * @param <X> Type of the function exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param function The extraction function.
	 * @param values The iterator providing the values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some iteration fails.
	 */
	public static <V, RV, X extends Exception, VX extends Exception> Maybe<RV> first(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends X> function, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != function;
		assert null != values;
		
		while (values.hasNext()) {
			final Maybe<? extends RV> value = function.evaluate(values.next());
			if (value.isSome()) {
				return Maybe.<RV>some(value.asSome().getValue());
			}
		}
		return Maybe.none();
	}
	
	/**
	 * Gets the first value extracted by the given function from the values provided by the given feed.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result value.
	 * @param <X> Type of the function exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param function The extraction function.
	 * @param values The feed providing the values.
	 * @return The first accepted value.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <V, RV, X extends Exception, VX extends Exception> Maybe<RV> first(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends X> function, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		return first(function, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Left folds the given values using the given operator and initial argument.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param operator The operator.
	 * @param initialAccumulator The initial argument. May be <code>null</code>.
	 * @param values The values.
	 * @return The result of the fold. May be <code>null</code>.
	 * @throws X When some operator evaluation fails.
	 */
	public static <R, V, X extends Exception> R fold(final Function2<? super R, ? super V, ? extends R, ? extends X> operator, final R initialAccumulator, final Iterable<? extends V> values)
	throws X {
		assert null != values;
		
		return fold(operator, initialAccumulator, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Left folds the values provided by the given iterator using the given operator and initial argument.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param operator The operator.
	 * @param initialAccumulator The initial argument. May be <code>null</code>.
	 * @param values The iterator providing the values.
	 * @return The result of the fold. May be <code>null</code>.
	 * @throws X When some operator evaluation fails.
	 */
	public static <R, V, X extends Exception> R fold(final Function2<? super R, ? super V, ? extends R, ? extends X> operator, final R initialAccumulator, final Iterator<? extends V> values)
	throws X {
		return fold(operator, initialAccumulator, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Left folds the values provided by the given iterator using the given operator and initial argument.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the operator exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param operator The operator.
	 * @param initialAccumulator The initial argument. May be <code>null</code>.
	 * @param values The iterator providing the values.
	 * @return The result of the fold. May be <code>null</code>.
	 * @throws X When some operator evaluation fails.
	 * @throws VX When some iteration fails.
	 */
	public static <R, V, X extends Exception, VX extends Exception> R fold(final Function2<? super R, ? super V, ? extends R, ? extends X> operator, final R initialAccumulator, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != operator;
		assert null != values;
		
		final Accumulator1<V, R, X> accumulator = FunctionAccumulators.fold(operator, initialAccumulator);
		while (values.hasNext()) {
			accumulator.add(values.next());
		}
		return accumulator.get();
	}
	
	/**
	 * Left folds the values provided by the given feed using the given operator and initial argument.
	 * 
	 * @param <R> Type of the result.
	 * @param <V> Type of the values.
	 * @param <X> Type of the operator exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param operator The operator.
	 * @param initialAccumulator The initial argument. May be <code>null</code>.
	 * @param values The feed providing the values.
	 * @return The result of the fold. May be <code>null</code>.
	 * @throws X When some operator evaluation fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <R, V, X extends Exception, VX extends Exception> R fold(final Function2<? super R, ? super V, ? extends R, ? extends X> operator, final R initialAccumulator, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		return fold(operator, initialAccumulator, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Counts the given values accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The values to count.
	 * @return The number of accepted values.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> int count(final Predicate1<? super V, ? extends X> predicate, final Iterable<? extends V> values)
	throws X {
		assert null != values;
		
		return count(predicate, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Counts the values provided by the given iterator accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values to count.
	 * @return The number of accepted values.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, X extends Exception> int count(final Predicate1<? super V, ? extends X> predicate, final Iterator<? extends V> values)
	throws X {
		return count(predicate, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Counts the values provided by the given iterator accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The iterator providing the values to count.
	 * @return The number of accepted values.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> int count(final Predicate1<? super V, ? extends X> predicate, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != predicate;
		assert null != values;
		
		final Counter count = new Counter();
		while (values.hasNext()) {
			if (predicate.evaluate(values.next())) {
				count.inc();
			}
		}
		return count.get();
	}
	
	/**
	 * Counts the values provided by the given feed accepted by the given predicate.
	 * 
	 * @param <V> Type of the values.
	 * @param <X> Type of the predicate exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param predicate The predicate.
	 * @param values The feed providing the values to count.
	 * @return The number of accepted values.
	 * @throws X When some predicate evaluation fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> int count(final Predicate1<? super V, ? extends X> predicate, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		return count(predicate, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Counts the given bindings accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to count.
	 * @return The number of accepted bindings.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> int count(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		final Counter count = new Counter();
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			if (predicate.evaluate(binding.getKey(), binding.getValue())) {
				count.inc();
			}
		}
		return count.get();
	}
	
	/**
	 * Counts the given bindings accepted by the given predicate.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to count.
	 * @return The number of accepted bindings.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, X extends Exception> int count(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<? extends K, ? extends V, ?> bindings)
	throws X {
		return count_(predicate, bindings);
	}
	
	private static <K, V, X extends Exception> int count_(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<K, ? extends V, ?> bindings)
	throws X {
		assert null != predicate;
		assert null != bindings;
		
		final Counter count = new Counter();
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				if (predicate.evaluate(key, value)) {
					count.inc();
				}
			}
		}
		return count.get();
	}
	
	/**
	 * Filters the given values using the given predicate and populates the given result collection with the accepted values.
	 * 
	 * @param <V> Type of the values to filter.
	 * @param <C> Type of the collection to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param values The values to filter.
	 * @param results The collection to populate with the accepted values.
	 * @return The given result collection.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, C extends Collection<? super V>, X extends Exception> C filter(final Predicate1<? super V, ? extends X> predicate, final Iterable<? extends V> values, final C results)
	throws X {
		return FunctionUtils.<V, Accumulator1<V, C, InternalException>, X, InternalException>filter(predicate, values, CollectionAccumulators.<V, C, InternalException>add(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Filters the given values using the given predicate and populates the given accumulator with the accepted values.
	 * 
	 * @param <V> Type of the values to filter.
	 * @param <A> Type of the accumulator to populate.
	 * @param <PX> Type of the predicate exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param predicate The predicate.
	 * @param values The values to filter.
	 * @param results The accumulator to populate with the accepted values.
	 * @return The given result accumulator.
	 * @throws PX When some predicate evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <V, A extends Accumulator1<? super V, ?, ? extends AX>, PX extends Exception, AX extends Exception> A filter(final Predicate1<? super V, ? extends PX> predicate, final Iterable<? extends V> values, final A results)
	throws PX, AX {
		assert null != values;
		assert null != results;
		
		for (final V value : values) {
			if (predicate.evaluate(value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	/**
	 * Filters the given bindings using the given predicate and populates the given result map with accepted values.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to filter.
	 * @param results The map to populate with the accepted bindings.
	 * @return The given result map.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M filter(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		return FunctionUtils.<K, V, Accumulator2<K, V, M, InternalException>, X, InternalException>filter(predicate, bindings, CollectionAccumulators.<K, V, M, InternalException>put(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Filters the given bindings using the given predicate and populates the given accumulator with accepted values.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate.
	 * @param <PX> Type of the predicate exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to filter.
	 * @param results The map to populate with the accepted bindings.
	 * @return The given accumulator.
	 * @throws PX When some predicate evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <K, V, A extends Accumulator2<? super K, ? super V, ?, ? extends AX>, PX extends Exception, AX extends Exception> A filter(final Predicate2<? super K, ? super V, ? extends PX> predicate, final Map<? extends K, ? extends V> bindings, final A results)
	throws PX, AX {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (predicate.evaluate(key, value)) {
				results.add(key, value);
			}
		}
		return results;
	}
	
	/**
	 * Filters the given bindings using the given predicate and populates the given result map with accepted values.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to filter.
	 * @param results The map to populate with the accepted bindings.
	 * @return The given result map.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, M extends Multimap<? super K, ? super V, ?>, X extends Exception> M filter(final Predicate2<? super K, ? super V, ? extends X> predicate, final Multimap<? extends K, ? extends V, ?> bindings, final M results)
	throws X {
		return FunctionUtils.<K, V, Accumulator2<K, V, M, InternalException>, X, InternalException>filter(predicate, bindings, CollectionAccumulators.<K, V, M, InternalException>put(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Filters the given bindings using the given predicate and populates the given accumulator with accepted values.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <A> Type of the accumulator to populate.
	 * @param <PX> Type of the predicate exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to filter.
	 * @param results The map to populate with the accepted bindings.
	 * @return The given accumulator.
	 * @throws PX When some predicate evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <K, V, A extends Accumulator2<? super K, ? super V, ?, ? extends AX>, PX extends Exception, AX extends Exception> A filter(final Predicate2<? super K, ? super V, ? extends PX> predicate, final Multimap<? extends K, ? extends V, ?> bindings, final A results)
	throws PX, AX {
		return filter_(predicate, bindings, results);
	}
	
	private static <K, V, A extends Accumulator2<? super K, ? super V, ?, ? extends AX>, PX extends Exception, AX extends Exception> A filter_(final Predicate2<? super K, ? super V, ? extends PX> predicate, final Multimap<K, ? extends V, ?> bindings, final A results)
	throws PX, AX {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				if (predicate.evaluate(key, value)) {
					results.add(key, value);
				}
			}
		}
		return results;
	}
	
	/**
	 * Filters the given bindings using the given predicate and populates the given result collection with the accepted keys.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the set to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to filter.
	 * @param results The set to populate with the keys of the accepted bindings.
	 * @return The given result set.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, C extends Collection<? super K>, X extends Exception> C filterKeys(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings, final C results)
	throws X {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			if (predicate.evaluate(key, entry.getValue())) {
				results.add(key);
			}
		}
		return results;
	}
	
	// TODO: filterKeys with accumulator
	
	/**
	 * Filters the given bindings using the given predicate and populates the given result collection with the accepted values.
	 * <p>
	 * This method evaluates the predicate by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <C> Type of the collection to populate.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param bindings The bindings to filter.
	 * @param results The collection to populate with the results.
	 * @return The given result collection.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, C extends Collection<? super V>, X extends Exception> C filterValues(final Predicate2<? super K, ? super V, ? extends X> predicate, final Map<? extends K, ? extends V> bindings, final C results)
	throws X {
		assert null != predicate;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final K key = entry.getKey();
			final V value = entry.getValue();
			if (predicate.evaluate(key, value)) {
				results.add(value);
			}
		}
		return results;
	}
	
	// TODO: filterValues with accumulator
	
	/**
	 * Filters the content of the given collection using the given predicate.
	 * <p>
	 * This method does modify the given collection.
	 * 
	 * @param <V> Type of the elements.
	 * @param <C> Type of the collection.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param collection The collection.
	 * @return The given collection.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <V, C extends Collection<V>, X extends Exception> C retain(final Predicate1<? super V, ? extends X> predicate, final C collection)
	throws X {
		assert null != predicate;
		assert null != collection;
		
		final Iterator<V> values_ = collection.iterator();
		while (values_.hasNext()) {
			final V value = values_.next();
			if (!predicate.evaluate(value)) {
				values_.remove();
			}
		}
		return collection;
	}
	
	/**
	 * Filters the content of the given map using the given predicate.
	 * <p>
	 * This method does modify the given map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @param map The map.
	 * @return The given map.
	 * @throws X When some predicate evaluation fails.
	 */
	public static <K, V, M extends Map<K, V>, X extends Exception> M retain(final Predicate2<? super K, ? super V, ? extends X> predicate, final M map)
	throws X {
		assert null != predicate;
		assert null != map;
		
		final Iterator<Map.Entry<K, V>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<K, V> entry = entries.next();
			if (!predicate.evaluate(entry.getKey(), entry.getValue())) {
				entries.remove();
			}
		}
		return map;
	}
	
	/**
	 * Transforms the given values using the given function and populates the given collection with the result values.
	 * 
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <V, RV, C extends Collection<? super RV>, X extends Exception> C map(final Function1<? super V, ? extends RV, ? extends X> function, final Iterable<? extends V> values, final C results)
	throws X {
		return FunctionUtils.<V, RV, Accumulator1<RV, C, InternalException>, X, InternalException>map(function, values, CollectionAccumulators.<RV, C, InternalException>add(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Transforms the given values using the given function and populates the given accumulator with the result values.
	 * 
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result values.
	 * @param <FX> Type of the function exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param function The function.
	 * @param values The values.
	 * @param results The accumulator to populate with the result values.
	 * @return The given result accumulator.
	 * @throws FX When some predicate evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <V, RV, A extends Accumulator1<? super RV, ?, ? extends AX>, FX extends Exception, AX extends Exception> A map(final Function1<? super V, ? extends RV, ? extends FX> function, final Iterable<? extends V> values, final A results)
	throws FX, AX {
		assert null != values;
		
		for (final V value : values) {
			results.add(function.evaluate(value));
		}
		return results;
	}
	
	/**
	 * Transforms the given bindings using the given function and populates the given map with the bindings of the argument keys and the corresponding result
	 * values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, RV, M extends Map<? super K, ? super RV>, X extends Exception> M map(final Function2<? super K, ? super V, ? extends RV, ? extends X> function, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		return FunctionUtils.<K, V, RV, Accumulator2<K, RV, M, InternalException>, X, InternalException>map(function, bindings, CollectionAccumulators.<K, RV, M, InternalException>put(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Transforms the given bindings using the given function and populates the given accumulator with the bindings of the argument keys and the corresponding
	 * result values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the map to populate with the result bindings.
	 * @param <FX> Type of the function exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param function The function.
	 * @param bindings The bindings.
	 * @param results The accumulator to populate with the result bindings.
	 * @return The given accumulator.
	 * @throws FX When some function evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, FX extends Exception, AX extends Exception> A map(final Function2<? super K, ? super V, ? extends RV, ? extends FX> function, final Map<? extends K, ? extends V> bindings, final A results)
	throws FX, AX {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			results.add(key, function.evaluate(key, binding.getValue()));
		}
		return results;
	}
	
	/**
	 * Transforms the given bindings using the given function and populates the given multi-map with the bindings of the argument keys and the corresponding
	 * result values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, RV, M extends Multimap<? super K, ? super RV, ?>, X extends Exception> M map(final Function2<? super K, ? super V, ? extends RV, ? extends X> function, final Multimap<? extends K, ? extends V, ?> bindings, final M results)
	throws X {
		return FunctionUtils.<K, V, RV, Accumulator2<K, RV, M, InternalException>, X, InternalException>map(function, bindings, CollectionAccumulators.<K, RV, M, InternalException>put(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Transforms the given bindings using the given function and populates the given accumulator with the bindings of the argument keys and the corresponding
	 * result values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result bindings.
	 * @param <FX> Type of the function exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param function The function.
	 * @param bindings The bindings.
	 * @param results The accumulator to populate with the result bindings.
	 * @return The given accumulator.
	 * @throws FX When some function evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, FX extends Exception, AX extends Exception> A map(final Function2<? super K, ? super V, ? extends RV, ? extends FX> function, final Multimap<? extends K, ? extends V, ?> bindings, final A results)
	throws FX, AX {
		return map_(function, bindings, results);
	}
	
	private static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, FX extends Exception, AX extends Exception> A map_(final Function2<? super K, ? super V, ? extends RV, ? extends FX> function, final Multimap<K, ? extends V, ?> bindings, final A results)
	throws FX, AX {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				results.add(key, function.evaluate(key, value));
			}
		}
		return results;
	}
	
	/**
	 * Filters and transforms the given values using the given extractor and populates the given collection with the accepted results.
	 * 
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param values The values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some extractor evaluation fails.
	 * @deprecated Use {@link #extract(Function1, Iterable, Collection)}.
	 */
	@Deprecated
	public static <V, RV, C extends Collection<? super RV>, X extends Exception> C mapFilter(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends X> extractor, final Iterable<? extends V> values, final C results)
	throws X {
		return extract(extractor, values, results);
	}
	
	/**
	 * Extracts the given values using the given extractor and populates the given collection with the accepted results.
	 * 
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param values The values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some extractor evaluation fails.
	 */
	public static <V, RV, C extends Collection<? super RV>, X extends Exception> C extract(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends X> extractor, final Iterable<? extends V> values, final C results)
	throws X {
		return FunctionUtils.<V, RV, Accumulator1<RV, C, InternalException>, X, InternalException>extract(extractor, values, CollectionAccumulators.<RV, C, InternalException>add(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Filters and transforms the given values using the given extractor and populates the given accumulator with the accepted results.
	 * 
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result values.
	 * @param <EX> Type of the extractor exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param extractor The extractor.
	 * @param values The values.
	 * @param results The accumulator to populate with the result values.
	 * @return The given accumulator.
	 * @throws EX When some extractor evaluation fails.
	 * @throws AX When some accumulation fails.
	 * @deprecated Use {@link #extract(Function1, Iterable, Accumulator1)}.
	 */
	@Deprecated
	public static <V, RV, A extends Accumulator1<? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A mapFilter(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Iterable<? extends V> values, final A results)
	throws EX, AX {
		return FunctionUtils.<V, RV, A, EX, AX>extract(extractor, values, results); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Extracts the given values using the given extractor and populates the given accumulator with the accepted results.
	 * 
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result values.
	 * @param <EX> Type of the extractor exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param extractor The extractor.
	 * @param values The values.
	 * @param results The accumulator to populate with the result values.
	 * @return The given accumulator.
	 * @throws EX When some extractor evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <V, RV, A extends Accumulator1<? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A extract(final Function1<? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Iterable<? extends V> values, final A results)
	throws EX, AX {
		assert null != extractor;
		assert null != values;
		assert null != results;
		
		for (final V value : values) {
			final Maybe<? extends RV> result = extractor.evaluate(value);
			if (result.isSome()) {
				results.add(result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filters and transforms the given bindings using the given extractor and populates the given map with the bindings of the argument keys and the
	 * corresponding accepted results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some extractor evaluation fails.
	 * @deprecated Use {@link #extract(Function2, Map, Map)}.
	 */
	@Deprecated
	public static <K, V, RV, M extends Map<? super K, ? super RV>, X extends Exception> M mapFilter(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends X> extractor, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		return extract(extractor, bindings, results);
	}
	
	/**
	 * Extracts the given bindings using the given extractor and populates the given map with the bindings of the argument keys and the corresponding accepted
	 * results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some extractor evaluation fails.
	 */
	public static <K, V, RV, M extends Map<? super K, ? super RV>, X extends Exception> M extract(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends X> extractor, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		return FunctionUtils.<K, V, RV, Accumulator2<K, RV, M, InternalException>, X, InternalException>extract(extractor, bindings, CollectionAccumulators.<K, RV, M, InternalException>put(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Filters and transforms the given bindings using the given extractor and populates the given accumulator with the bindings of the argument keys and the
	 * corresponding accepted results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result bindings.
	 * @param <EX> Type of the extractor exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given accumulator.
	 * @throws EX When some extractor evaluation fails.
	 * @throws AX When some accumulation fails.
	 * @deprecated Use {@link #extract(Function2, Map, Accumulator2)}.
	 */
	@Deprecated
	public static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A mapFilter(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Map<? extends K, ? extends V> bindings, final A results)
	throws EX, AX {
		return FunctionUtils.<K, V, RV, A, EX, AX>extract(extractor, bindings, results); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Extracts the given bindings using the given extractor and populates the given accumulator with the bindings of the argument keys and the corresponding
	 * accepted results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result bindings.
	 * @param <EX> Type of the extractor exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given accumulator.
	 * @throws EX When some extractor evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A extract(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Map<? extends K, ? extends V> bindings, final A results)
	throws EX, AX {
		assert null != extractor;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			final K key = binding.getKey();
			final Maybe<? extends RV> result = extractor.evaluate(key, binding.getValue());
			if (result.isSome()) {
				results.add(key, result.asSome().getValue());
			}
		}
		return results;
	}
	
	/**
	 * Filters and transforms the given bindings using the given extractor and populates the given map with the bindings of the argument keys and the
	 * corresponding accepted results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some extractor evaluation fails.
	 * @deprecated {@link #extract(Function2, Multimap, Multimap)}.
	 */
	@Deprecated
	public static <K, V, RV, M extends Multimap<? super K, ? super RV, ?>, X extends Exception> M mapFilter(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends X> extractor, final Multimap<? extends K, ? extends V, ?> bindings, final M results)
	throws X {
		return extract(extractor, bindings, results);
	}
	
	/**
	 * Extracts the given bindings using the given extractor and populates the given map with the bindings of the argument keys and the corresponding accepted
	 * results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some extractor evaluation fails.
	 */
	public static <K, V, RV, M extends Multimap<? super K, ? super RV, ?>, X extends Exception> M extract(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends X> extractor, final Multimap<? extends K, ? extends V, ?> bindings, final M results)
	throws X {
		return FunctionUtils.<K, V, RV, Accumulator2<K, RV, M, InternalException>, X, InternalException>extract(extractor, bindings, CollectionAccumulators.<K, RV, M, InternalException>put(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Filters and transforms the given bindings using the given extractor and populates the given accumulator with the bindings of the argument keys and the
	 * corresponding accepted results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result bindings.
	 * @param <EX> Type of the extractor exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given accumulator.
	 * @throws EX When some extractor evaluation fails.
	 * @throws AX When some accumulation fails.
	 * @deprecated Use {@link #extract(Function2, Multimap, Accumulator2)}.
	 */
	@Deprecated
	public static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A mapFilter(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Multimap<? extends K, ? extends V, ?> bindings, final A results)
	throws EX, AX {
		return FunctionUtils.<K, V, RV, A, EX, AX>extract(extractor, bindings, results); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Extracts the given bindings using the given extractor and populates the given accumulator with the bindings of the argument keys and the corresponding
	 * accepted results.
	 * <p>
	 * This method evaluates the extractor by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result bindings.
	 * @param <EX> Type of the extractor exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param extractor The extractor.
	 * @param bindings The bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given accumulator.
	 * @throws EX When some extractor evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A extract(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Multimap<? extends K, ? extends V, ?> bindings, final A results)
	throws EX, AX {
		return extract_(extractor, bindings, results);
	}
	
	private static <K, V, RV, A extends Accumulator2<? super K, ? super RV, ?, ? extends AX>, EX extends Exception, AX extends Exception> A extract_(final Function2<? super K, ? super V, ? extends Maybe<? extends RV>, ? extends EX> extractor, final Multimap<K, ? extends V, ?> bindings, final A results)
	throws EX, AX {
		assert null != extractor;
		assert null != bindings;
		assert null != results;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				final Maybe<? extends RV> result = extractor.evaluate(key, value);
				if (result.isSome()) {
					results.add(key, result.asSome().getValue());
				}
			}
		}
		return results;
	}
	
	/**
	 * Transforms the given values using the given function, flattens them and populates the given collection with the result values.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <V, RV, C extends Collection<? super RV>, X extends Exception> C flatMap(final Function1<? super V, ? extends Collection<? extends RV>, ? extends X> function, final Iterable<? extends V> values, final C results)
	throws X {
		assert null != values;
		
		return FunctionUtils.<V, RV, Accumulator1<RV, C, InternalException>, X, InternalException>flatMap(function, values, CollectionAccumulators.<RV, C, InternalException>add(results)).get(); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Transforms the values provided by the given iterator using the given function, flattens them and populates the given collection with the result values.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The iterator providing the argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 */
	public static <V, RV, C extends Collection<? super RV>, X extends Exception> C flatMap(final Function1<? super V, ? extends Collection<? extends RV>, ? extends X> function, final Iterator<? extends V> values, final C results)
	throws X {
		return FunctionUtils.<V, RV, C, X, InternalException>flatMap(function, CheckedIterators.<V, InternalException>fromIterator(values), results); // HACK: explicit type argments to work around a bug of javac
	}
	
	/**
	 * Transforms the values provided by the given iterator using the given function, flattens them and populates the given collection with the result values.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result values.
	 * @param <C> Type of the collection to populate with the result values.
	 * @param <X> Type of the function exceptions.
	 * @param <TX> Type of the value exceptions.
	 * @param function The function.
	 * @param values The iterator providing the argument values.
	 * @param results The collection to populate with the result values.
	 * @return The given result collection.
	 * @throws X When some function evaluation fails.
	 * @throws TX When some iteration fails.
	 */
	public static <V, RV, C extends Collection<? super RV>, X extends Exception, TX extends Exception> C flatMap(final Function1<? super V, ? extends Collection<? extends RV>, ? extends X> function, final CheckedIterator<? extends V, ? extends TX> values, final C results)
	throws X, TX {
		assert null != function;
		assert null != values;
		assert null != results;
		
		while (values.hasNext()) {
			results.addAll(function.evaluate(values.next()));
		}
		return results;
	}
	
	/**
	 * Transforms the given values using the given function, flattens them and populates the given collection with the result values.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <RV> Type of the result values.
	 * @param <A> Type of the accumulator to populate with the result values.
	 * @param <FX> Type of the function exceptions.
	 * @param <AX> Type of the accumulator exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The accumulator to populate with the result values.
	 * @return The given accumulator.
	 * @throws FX When some function evaluation fails.
	 * @throws AX When some accumulation fails.
	 */
	public static <V, RV, A extends Accumulator1<? super RV, ?, ? extends AX>, FX extends Exception, AX extends Exception> A flatMap(final Function1<? super V, ? extends Collection<? extends RV>, ? extends FX> function, final Iterable<? extends V> values, final A results)
	throws FX, AX {
		assert null != function;
		assert null != values;
		assert null != results;
		
		for (final V value : values) {
			results.addAll(function.evaluate(value));
		}
		return results;
	}
	
	/**
	 * Projects the given keys using the given function and populates the given map with the bindings of the keys and the projected values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param keys The argument keys.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M projectKeys(final Function1<? super K, ? extends V, ? extends X> function, final Iterable<? extends K> keys, final M results)
	throws X {
		assert null != function;
		assert null != keys;
		assert null != results;
		
		for (final K key : keys) {
			results.put(key, function.evaluate(key));
		}
		return results;
	}
	
	// TODO: projectKeys with accumulator
	
	/**
	 * Projects the given values using the given function and populates the given map with the bindings of the projected keys and the values.
	 * <p>
	 * When the function evaluates to the same result key for different argument values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @param values The argument values.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M projectValues(final Function1<? super V, ? extends K, ? extends X> function, final Iterable<? extends V> values, final M results)
	throws X {
		assert null != function;
		assert null != values;
		assert null != results;
		
		for (final V value : values) {
			results.put(function.evaluate(value), value);
		}
		return results;
	}
	
	// TODO: projectValues with accumulator
	
	/**
	 * Filters and projects the given keys using the given extractor and populates the given map with the bindings of the accepted keys and the projected
	 * values.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param keys The argument keys.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M projectFilterKeys(final Function1<? super K, ? extends Maybe<? extends V>, ? extends X> extractor, final Iterable<? extends K> keys, final M results)
	throws X {
		assert null != extractor;
		assert null != keys;
		assert null != results;
		
		for (final K key : keys) {
			final Maybe<? extends V> value = extractor.evaluate(key);
			if (value.isSome()) {
				results.put(key, value.asSome().getValue());
			}
		}
		return results;
	}
	
	// TODO: projectFilterKeys with accumulator
	
	/**
	 * Filters and projects the given values using the given extractor and populates the given map with the bindings of the projected keys and the accepted
	 * values.
	 * <p>
	 * When the function evaluates to the same result key for different argument values, the last value is associated to the key in the result map.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param extractor The extractor.
	 * @param values The argument values.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, V, M extends Map<? super K, ? super V>, X extends Exception> M projectFilterValues(final Function1<? super V, ? extends Maybe<? extends K>, ? extends X> extractor, final Iterable<? extends V> values, final M results)
	throws X {
		assert null != extractor;
		assert null != values;
		assert null != results;
		
		for (final V value : values) {
			final Maybe<? extends K> key = extractor.evaluate(value);
			if (key.isSome()) {
				results.put(key.asSome().getValue(), value);
			}
		}
		return results;
	}
	
	// TODO: projectFilterValues with accumulator
	
	/**
	 * Transforms the keys of the given bindings using the given function and populates the given map with the bindings of the result keys and the values
	 * associated to the arguments keys.
	 * 
	 * @param <K> Type of the argument keys.
	 * @param <RK> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function .
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, RK, V, M extends Map<? super RK, ? super V>, X extends Exception> M remap(final Function1<? super K, RK, ? extends X> function, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			results.put(function.evaluate(entry.getKey()), entry.getValue());
		}
		return results;
	}
	
	// TODO: remap with accumulator
	
	/**
	 * Transforms the given bindings using the given function and populates the given map with the bindings of the result keys and the argument values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the argument keys.
	 * @param <RK> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, RK, V, M extends Map<? super RK, ? super V>, X extends Exception> M remap(final Function2<? super K, ? super V, RK, ? extends X> function, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final V value = entry.getValue();
			results.put(function.evaluate(entry.getKey(), value), value);
		}
		return results;
	}
	
	// TODO: remap for multimap
	
	/**
	 * Filters and transforms the keys of the given bindings using the given function and populates the given map with the bindings of the accepted result keys
	 * and the values associated to the arguments keys.
	 * 
	 * @param <K> Type of the argument keys.
	 * @param <RK> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate with the result bindings.
	 * @param <X> Type of the exceptions.
	 * @param function The function .
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the result bindings.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, RK, V, M extends Map<? super RK, ? super V>, X extends Exception> M remapFilter(final Function1<? super K, ? extends Maybe<? extends RK>, ? extends X> function, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final Maybe<? extends RK> newKey = function.evaluate(entry.getKey());
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), entry.getValue());
			}
		}
		return results;
	}
	
	// TODO: remapFilter with accumulator
	
	/**
	 * Filters and transforms the given bindings using the given function and populates the given map with the bindings of the accepted result keys and the
	 * argument values.
	 * <p>
	 * This method evaluates the function by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the argument keys.
	 * @param <RK> Type of the result keys.
	 * @param <V> Type of the values.
	 * @param <M> Type of the map to populate.
	 * @param <X> Type of the exceptions.
	 * @param function Function to use.
	 * @param bindings The argument bindings.
	 * @param results The map to populate with the results.
	 * @return The given result map.
	 * @throws X When some function evaluation fails.
	 */
	public static <K, RK, V, M extends Map<? super RK, ? super V>, X extends Exception> M remapFilter(final Function2<? super K, ? super V, ? extends Maybe<? extends RK>, ? extends X> function, final Map<? extends K, ? extends V> bindings, final M results)
	throws X {
		assert null != function;
		assert null != bindings;
		assert null != results;
		
		for (final Map.Entry<? extends K, ? extends V> entry : bindings.entrySet()) {
			final V value = entry.getValue();
			final Maybe<? extends RK> newKey = function.evaluate(entry.getKey(), value);
			if (newKey.isSome()) {
				results.put(newKey.asSome().getValue(), value);
			}
		}
		return results;
	}
	
	// TODO: remapFilter for multimap
	
	/**
	 * Executes the given procedure with the given values.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param procedure The procedure.
	 * @param values The argument values.
	 * @throws X When some procedure execution fails.
	 */
	public static <V, X extends Exception> void execute(final Procedure1<? super V, ? extends X> procedure, final Iterable<? extends V> values)
	throws X {
		assert null != values;
		
		execute(procedure, CheckedIterators.<V, InternalException>fromIterable(values));
	}
	
	/**
	 * Executes the given procedure with the values provided by the given iterator.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param procedure The procedure.
	 * @param values The iterator providing the argument values.
	 * @throws X When some procedure execution fails.
	 */
	public static <V, X extends Exception> void execute(final Procedure1<? super V, ? extends X> procedure, final Iterator<? extends V> values)
	throws X {
		execute(procedure, CheckedIterators.<V, InternalException>fromIterator(values));
	}
	
	/**
	 * Executes the given procedure with the values provided by the given iterator.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <X> Type of the procedure exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param procedure The procedure.
	 * @param values The iterator providing the argument values.
	 * @throws X When some procedure execution fails.
	 * @throws VX When some iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> void execute(final Procedure1<? super V, ? extends X> procedure, final CheckedIterator<? extends V, ? extends VX> values)
	throws X, VX {
		assert null != procedure;
		assert null != values;
		
		while (values.hasNext()) {
			procedure.execute(values.next());
		}
	}
	
	/**
	 * Executes the given procedure with the values provided by the given feed.
	 * 
	 * @param <V> Type of the argument values.
	 * @param <X> Type of the procedure exceptions.
	 * @param <VX> Type of the value exceptions.
	 * @param procedure The procedure.
	 * @param values The feed providing the argument values.
	 * @throws X When some procedure execution fails.
	 * @throws VX When some feed iteration fails.
	 */
	public static <V, X extends Exception, VX extends Exception> void execute(final Procedure1<? super V, ? extends X> procedure, final Feed<? extends V, ? extends VX> values)
	throws X, VX {
		execute(procedure, CheckedIterators.fromFeed(values));
	}
	
	/**
	 * Executes the given procedure with the given bindings.
	 * <p>
	 * This method executes the procedure by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param procedure The procedure.
	 * @param bindings The argument bindings.
	 * @throws X When some procedure execution fails.
	 */
	public static <K, V, X extends Exception> void execute(final Procedure2<? super K, ? super V, ? extends X> procedure, final Map<? extends K, ? extends V> bindings)
	throws X {
		assert null != procedure;
		assert null != bindings;
		
		for (final Map.Entry<? extends K, ? extends V> binding : bindings.entrySet()) {
			procedure.execute(binding.getKey(), binding.getValue());
		}
	}
	
	/**
	 * Executes the given procedure with the given bindings.
	 * <p>
	 * This method executes the procedure by passing the keys and values of the bindings respectively as first and second arguments.
	 * 
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param procedure The procedure.
	 * @param bindings The argument bindings.
	 * @throws X When some procedure execution fails.
	 */
	public static <K, V, X extends Exception> void execute(final Procedure2<? super K, ? super V, ? extends X> procedure, final Multimap<? extends K, ? extends V, ?> bindings)
	throws X {
		execute_(procedure, bindings);
	}
	
	private static <K, V, X extends Exception> void execute_(final Procedure2<? super K, ? super V, ? extends X> procedure, final Multimap<K, ? extends V, ?> bindings)
	throws X {
		assert null != procedure;
		assert null != bindings;
		
		for (final K key : bindings.keySet()) {
			for (final V value : bindings.get(key)) {
				procedure.execute(key, value);
			}
		}
	}
	
	private FunctionUtils() {
		// Prevents instantiation.
	}
}
