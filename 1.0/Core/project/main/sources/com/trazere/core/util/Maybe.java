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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.functional.Function2;
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Iterators;
import com.trazere.core.imperative.Procedure;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;
import java.io.Serializable;
import java.util.Iterator;

/**
 * The {@link Maybe} class encodes a tagged union data type which represents an optional value.
 * <ul>
 * <li>Instances built using the {@link None} constructor encode absent values.
 * <li>Instances built using the {@link Some} constructor encode available values.
 * <p>
 * This class aims to improve the reliability of the code by avoiding uses of <code>null</code>.
 * 
 * @param <T> Type of the value.
 */
public abstract class Maybe<T>
implements Iterable<T>, Serializable, Describable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a {@link Maybe} instance using the {@link None} constructor.
	 * 
	 * @param <T> Type of the value.
	 * @return The built instance.
	 * @see None
	 */
	@SuppressWarnings("unchecked")
	public static <T> Maybe<T> none() {
		return (None<T>) NONE;
	}
	
	private static final None<?> NONE = new None<>();
	
	/**
	 * Builds a {@link Maybe} instance using the {@link Some} constructor.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Some
	 */
	public static <T> Maybe<T> some(final T value) {
		return new Some<>(value);
	}
	
	// None.
	
	/**
	 * The {@link Maybe.None} class represents instances of {@link Maybe} that encode absent values.
	 * 
	 * @param <T> Type of the value.
	 */
	public static final class None<T>
	extends Maybe<T> {
		private static final long serialVersionUID = 1L;
		
		// None.
		
		@Override
		public boolean isNone() {
			return true;
		}
		
		@Override
		public None<T> asNone() {
			return this;
		}
		
		// Value.
		
		@Override
		public T get(final T defaultValue) {
			return defaultValue;
		}
		
		@Override
		public T get(final Thunk<? extends T> defaultValue) {
			return defaultValue.evaluate();
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T, ? extends R> matcher) {
			return matcher.none(this);
		}
		
		// Imperative.
		
		@Override
		public void foreach(final Procedure<? super T> procedure) {
			// Nothing to do.
		}
		
		// Functional.
		
		@Override
		public <S> S fold(final Function2<? super S, ? super T, ? extends S> operator, final S initialState) {
			return initialState;
		}
		
		@Override
		public boolean isAny(final Predicate<? super T> filter) {
			return false;
		}
		
		@Override
		public boolean areAll(final Predicate<? super T> filter) {
			return true;
		}
		
		@Override
		public int count(final Predicate<? super T> filter) {
			return 0;
		}
		
		@Override
		public Maybe<T> filter(final Predicate<? super T> filter) {
			return none();
		}
		
		@Override
		public <R> Maybe<R> map(final Function<? super T, ? extends R> function) {
			return none();
		}
		
		@Override
		public <R> Maybe<R> flatMap(final Function<? super T, ? extends Maybe<? extends R>> extractor) {
			return none();
		}
		
		// Iterable.
		
		@Override
		public Iterator<T> iterator() {
			return Iterators.empty();
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public void appendDescription(final Description description) {
			// Nothing to do.
		}
	}
	
	/**
	 * Tests whether the receiver instance has been built using the {@link None} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built using the {@link None} constructor, <code>false</code> otherwise.
	 */
	public boolean isNone() {
		return false;
	}
	
	/**
	 * Gets a view of the receiver instance as a {@link None} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when the receiver instance has not been built using the {@link None} constructor.
	 */
	public None<T> asNone()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, None.class);
	}
	
	// Some.
	
	/**
	 * The {@link Maybe.Some} class represents instances of {@link Maybe} that encode available values.
	 * 
	 * @param <T> Type of the value.
	 */
	public static final class Some<T>
	extends Maybe<T> {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Instantiates a new {@link Maybe} instance wrapping the given value.
		 * 
		 * @param value Value to wrap.
		 */
		public Some(final T value) {
			_value = value;
		}
		
		// Some.
		
		@Override
		public boolean isSome() {
			return true;
		}
		
		@Override
		public Some<T> asSome() {
			return this;
		}
		
		/** Wrapped value. */
		private final T _value;
		
		/**
		 * Gets the value wrapped in the receiver instance.
		 * 
		 * @return The wrapped value.
		 */
		public T getValue() {
			return _value;
		}
		
		// Getting.
		
		@Override
		public T get(final T defaultValue) {
			return _value;
		}
		
		@Override
		public T get(final Thunk<? extends T> defaultValue) {
			return _value;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T, ? extends R> matcher) {
			return matcher.some(this);
		}
		
		// Imperative.
		
		@Override
		public void foreach(final Procedure<? super T> procedure) {
			procedure.execute(_value);
		}
		
		// Functional.
		
		@Override
		public <S> S fold(final Function2<? super S, ? super T, ? extends S> operator, final S initialState) {
			return operator.evaluate(initialState, _value);
		}
		
		@Override
		public boolean isAny(final Predicate<? super T> filter) {
			return filter.evaluate(_value);
		}
		
		@Override
		public boolean areAll(final Predicate<? super T> filter) {
			return filter.evaluate(_value);
		}
		
		@Override
		public int count(final Predicate<? super T> filter) {
			return filter.evaluate(_value) ? 1 : 0;
		}
		
		@Override
		public Maybe<T> filter(final Predicate<? super T> predicate) {
			return predicate.evaluate(_value) ? this : Maybe.<T>none();
		}
		
		@Override
		public <R> Maybe<R> map(final Function<? super T, ? extends R> function) {
			return Maybe.<R>some(function.evaluate(_value));
		}
		
		@Override
		public <R> Maybe<R> flatMap(final Function<? super T, ? extends Maybe<? extends R>> function) {
			return function.evaluate(_value).map(Functions.<R>identity()); // HACK: explicit type arguments to work around a bug of javac
		}
		
		// Iterable.
		
		@Override
		public Iterator<T> iterator() {
			return Iterators.fromElement(_value);
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_value);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final Some<?> maybe = (Some<?>) object;
				return ObjectUtils.safeEquals(_value, maybe._value);
			} else {
				return false;
			}
		}
		
		@Override
		public void appendDescription(final Description description) {
			description.append("Value", _value);
		}
	}
	
	/**
	 * Tests whether the receiver instance has been built using the {@link Some} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built using the {@link Some} constructor, <code>false</code> otherwise.
	 */
	public boolean isSome() {
		return false;
	}
	
	/**
	 * Gets a view of the receiver instance as a {@link Some} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when the receiver instance has not been built using the {@link Some} constructor.
	 */
	public Some<T> asSome()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Some.class);
	}
	
	// Value.
	
	/**
	 * Gets the value of the receiver {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 * 
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public abstract T get(final T defaultValue);
	
	/**
	 * Gets the value of the receiver {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 * 
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public abstract T get(final Thunk<? extends T> defaultValue);
	
	// Matching.
	
	/**
	 * The {@link Maybe.Matcher} interface defines matching functions of {@link Maybe} instances.
	 * 
	 * @param <T> Type of the value.
	 * @param <R> Type of the result.
	 * @see Maybe#match(Matcher)
	 */
	public interface Matcher<T, R> {
		/**
		 * Matches the given {@link Maybe} instances built using the {@link Maybe.None} constructor.
		 * 
		 * @param none {@link Maybe} instance to match.
		 * @return The result of the matching function evaluation.
		 */
		R none(None<? extends T> none);
		
		/**
		 * Matches the given {@link Maybe} instances built using the {@link Maybe.Some} constructor.
		 * 
		 * @param some {@link Maybe} instance to match.
		 * @return The result of the matching function evaluation.
		 */
		R some(Some<? extends T> some);
	}
	
	/**
	 * Matches the receiver {@link Maybe} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <R> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 */
	public abstract <R> R match(final Matcher<? super T, ? extends R> matcher);
	
	// Imperative.
	
	/**
	 * Executes the given procedure with the value wrapped in the receiver {@link Maybe} instance.
	 * 
	 * @param procedure Procedure to execute.
	 */
	public abstract void foreach(final Procedure<? super T> procedure);
	
	// Functional.
	
	/**
	 * Left folds over the value wrapped in the receiver {@link Maybe} instance using the given binary operator and initial state.
	 * 
	 * @param <S> Type of the state.
	 * @param operator Binary operator to use.
	 * @param initialState Initial state.
	 * @return The folded state.
	 */
	public abstract <S> S fold(final Function2<? super S, ? super T, ? extends S> operator, final S initialState);
	
	/**
	 * Tests whether the value wrapped in the receiver {@link Maybe} instance is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the value.
	 * @return <code>true</code> when the wrapped value is accepted, <code>false</code> when no value is wrapped and when the wrapped value is rejected.
	 */
	public abstract boolean isAny(final Predicate<? super T> filter);
	
	/**
	 * Tests whether the value wrapped in the receiver {@link Maybe} instance is accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the value.
	 * @return <code>true</code> when no value is wrapped and when the wrapped value is accepted, <code>false</code> when the wrapped value is rejected.
	 */
	public abstract boolean areAll(final Predicate<? super T> filter);
	
	/**
	 * Counts the value wrapped in the receiver {@link Maybe} instance accepted by the given filter.
	 * 
	 * @param filter Predicate to use to filter the value.
	 * @return <tt>1</tt> when the wrapped value is accepted by the filter, <tt>0</tt> when no value is wrapped and when the wrapped value is rejected.
	 */
	public abstract int count(final Predicate<? super T> filter);
	
	/**
	 * Filters the value wrapped by the receiver {@link Maybe} instance using the given filter.
	 * 
	 * @param filter Predicate to use to filter the value.
	 * @return The {@link Maybe} instance wrapping the filtered value.
	 */
	public abstract Maybe<T> filter(final Predicate<? super T> filter);
	
	/**
	 * Maps the value wrapped by the receiver {@link Maybe} instance using the given function.
	 * 
	 * @param <R> Type of the mapped value.
	 * @param function Function to use to transform the value.
	 * @return The {@link Maybe} instance wrapping the mapped value.
	 */
	public abstract <R> Maybe<R> map(final Function<? super T, ? extends R> function);
	
	/**
	 * Transforms and flattens the value wrapped by the receiver {@link Maybe} instance using the given function.
	 * 
	 * @param <R> Type of the extracted value.
	 * @param function Function to use to transform the value.
	 * @return The {@link Maybe} instance wrapping the extracted value.
	 */
	public abstract <R> Maybe<R> flatMap(final Function<? super T, ? extends Maybe<? extends R>> function);
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.description(this);
	}
}
