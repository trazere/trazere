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
import com.trazere.core.functional.Functions;
import com.trazere.core.functional.Predicate;
import com.trazere.core.functional.Thunk;
import com.trazere.core.imperative.Iterators;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.LangUtils;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;
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
implements Iterable<T>, Describable {
	/**
	 * Builds a {@link Maybe} instance using the {@link None} constructor.
	 * 
	 * @param <T> Type of the value.
	 * @return The built instance.
	 * @see None
	 */
	@SuppressWarnings("unchecked")
	public static <T> None<T> none() {
		return (None<T>) NONE;
	}
	
	private static final None<?> NONE = new None<Object>();
	
	/**
	 * Builds a {@link Maybe} instance using the {@link Some} constructor.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Some
	 */
	public static <T> Some<T> some(final T value) {
		return new Some<T>(value);
	}
	
	/**
	 * Builds a function that builds {@link Maybe} instances using the {@link Maybe.Some} constructor.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see #some(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Maybe<T>> someFunction() {
		return (Function<T, Maybe<T>>) SOME_FUNCTION;
	}
	
	private static final Function<?, ?> SOME_FUNCTION = new Function<Object, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Object value) {
			return some(value);
		}
	};
	
	/**
	 * Builds an instance of {@link Maybe} from the given value according to the following rules:
	 * <ul>
	 * <li><code>null</code> are translated to an absent value ({@link None}),
	 * <li>non-<code>null</code> values are wrapped in available values ({@link Some}).
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param value Value to wrap.
	 * @return The built instance.
	 */
	public static <T> Maybe<T> fromValue(final T value) {
		if (null != value) {
			return some(value);
		} else {
			return none();
		}
	}
	
	/**
	 * Builds a function that builds instances of {@link Maybe} from values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see #fromValue(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<T, Maybe<T>> fromValueFunction() {
		return (Function<T, Maybe<T>>) FROM_VALUE_FUNCTION;
	}
	
	private static final Function<?, ?> FROM_VALUE_FUNCTION = new Function<Object, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Object value) {
			return fromValue(value);
		}
	};
	
	/**
	 * Convert the given {@link Maybe} instance to a value according to the following rules:
	 * <ul>
	 * <li>absents values ({@link None}) are translated to <code>null</code>,
	 * <li>available values ({@link Some}) are unwrapped.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe Instance to convert.
	 * @return The resulting value.
	 */
	public static <T> T toValue(final Maybe<T> maybe) {
		return maybe.get((T) null);
	}
	
	/**
	 * Builds a function that convert {@link Maybe} instances to values.
	 * 
	 * @param <T> Type of the values.
	 * @return The built function.
	 * @see #toValue(Maybe)
	 */
	@SuppressWarnings("unchecked")
	public static <T> Function<Maybe<T>, T> toValueFunction() {
		return (Function<Maybe<T>, T>) TO_VALUE_FUNCTION;
	}
	
	private static final Function<?, ?> TO_VALUE_FUNCTION = new Function<Maybe<Object>, Object>() {
		@Override
		public Object evaluate(final Maybe<Object> instance) {
			return toValue(instance);
		}
	};
	
	// None.
	
	/**
	 * The {@link Maybe.None} class represents instances of {@link Maybe} that encode absent values.
	 * 
	 * @param <T> Type of the value.
	 */
	public static final class None<T>
	extends Maybe<T> {
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
		public <R> R match(final Matcher<? super T, R> matcher) {
			return matcher.none(this);
		}
		
		// Functional.
		
		@Override
		public <R> Maybe<R> map(final Function<? super T, ? extends R> function) {
			return none();
		}
		
		@Override
		public Maybe<T> filter(final Predicate<? super T> filter) {
			return none();
		}
		
		@Override
		public <R> Maybe<R> extract(final Function<? super T, ? extends Maybe<? extends R>> extractor) {
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
		public <R> R match(final Matcher<? super T, R> matcher) {
			return matcher.some(this);
		}
		
		// Functional.
		
		@Override
		public <R> Maybe<R> map(final Function<? super T, ? extends R> function) {
			return Maybe.<R>some(function.evaluate(_value));
		}
		
		@Override
		public Maybe<T> filter(final Predicate<? super T> predicate) {
			return predicate.evaluate(_value) ? this : Maybe.<T>none();
		}
		
		@Override
		public <R> Maybe<R> extract(final Function<? super T, ? extends Maybe<? extends R>> function) {
			return function.evaluate(_value).map(Functions.<R>identity());
		}
		
		// Iterable.
		
		@Override
		public Iterator<T> iterator() {
			return Iterators.fromValue(_value);
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
				return LangUtils.safeEquals(_value, maybe._value);
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
	 * Gets the value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 * <p>
	 * This method is slightly more general than {@link Maybe#get(Object)}.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe {@link Maybe} instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static <T> T get(final Maybe<? extends T> maybe, final T defaultValue) {
		return maybe.isSome() ? maybe.asSome().getValue() : defaultValue;
	}
	
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
	
	/**
	 * Gets the value of the given {@link Maybe} instance.
	 * <p>
	 * This method returns the wrapped value for the instances built using the {@link Some} constructor and the given default value for instances built using
	 * the {@link Maybe.None} constructor.
	 * <p>
	 * This method is slightly more general than {@link Maybe#get(Thunk)}.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe {@link Maybe} instance to read.
	 * @param defaultValue Default value.
	 * @return The value.
	 */
	public static <T> T get(final Maybe<? extends T> maybe, final Thunk<T> defaultValue) {
		return maybe.isSome() ? maybe.asSome().getValue() : defaultValue.evaluate();
	}
	
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
	public abstract <R> R match(final Matcher<? super T, R> matcher);
	
	// Functional.
	
	/**
	 * Maps the value wrapped by the receiver {@link Maybe} instance using the given function.
	 * 
	 * @param <R> Type of the mapped value.
	 * @param function Mapping function to use.
	 * @return The {@link Maybe} instance wrapping the mapped value.
	 */
	public abstract <R> Maybe<R> map(final Function<? super T, ? extends R> function);
	
	/**
	 * Builds a function that maps the value wrapped in {@link Maybe} instances using the given function.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the mapped values.
	 * @param function Mapping function to use.
	 * @return The built function.
	 * @see #map(Function)
	 */
	public static <T, R> Function<Maybe<? extends T>, Maybe<R>> mapFunction(final Function<? super T, ? extends R> function) {
		assert null != function;
		
		return new Function<Maybe<? extends T>, Maybe<R>>() {
			@Override
			public Maybe<R> evaluate(final Maybe<? extends T> maybe) {
				return maybe.map(function);
			}
		};
	}
	
	/**
	 * Filters the value wrapped by the receiver {@link Maybe} instance using the given filter.
	 * 
	 * @param filter Filter to use.
	 * @return The {@link Maybe} instance wrapping the filtered value.
	 */
	public abstract Maybe<T> filter(final Predicate<? super T> filter);
	
	/**
	 * Builds a function that filters the value wrapped in {@link Maybe} instances using the given filter.
	 * 
	 * @param <T> Type of the values.
	 * @param filter Filter to use.
	 * @return The built function.
	 * @see #filter(Predicate)
	 */
	public static <T> Function<Maybe<? extends T>, Maybe<T>> filterFunction(final Predicate<? super T> filter) {
		assert null != filter;
		
		return new Function<Maybe<? extends T>, Maybe<T>>() {
			@Override
			public Maybe<T> evaluate(final Maybe<? extends T> maybe) {
				return maybe.filter(filter).map(Functions.<T>identity());
			}
		};
	}
	
	/**
	 * Extracts the value wrapped by the receiver {@link Maybe} instance using the given extractor.
	 * 
	 * @param <R> Type of the extracted value.
	 * @param extractor Extractor to use.
	 * @return The {@link Maybe} instance wrapping the extracted value.
	 */
	public abstract <R> Maybe<R> extract(final Function<? super T, ? extends Maybe<? extends R>> extractor);
	
	/**
	 * Builds a function that extracts the value wrapped in {@link Maybe} instances using the given extractor.
	 * 
	 * @param <T> Type of the values.
	 * @param <R> Type of the extracted values.
	 * @param extractor Extractor to use.
	 * @return The built function.
	 */
	public static <T, R> Function<Maybe<? extends T>, Maybe<R>> extractFunction(final Function<? super T, ? extends Maybe<? extends R>> extractor) {
		assert null != extractor;
		
		return new Function<Maybe<? extends T>, Maybe<R>>() {
			@Override
			public Maybe<R> evaluate(final Maybe<? extends T> maybe) {
				return maybe.extract(extractor);
			}
		};
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
