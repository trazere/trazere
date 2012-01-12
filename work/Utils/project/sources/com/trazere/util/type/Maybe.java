/*
 *  Copyright 2006-2012 Julien Dufour
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

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Functions;
import com.trazere.util.function.Predicate1;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import java.util.Iterator;

/**
 * The {@link Maybe} class represents an algebraic data type which wraps an optional value.
 * <ul>
 * <li>The <code>None</code> constructor builds instances which wrap absent values.
 * <li>The <code>Some</code> constructor builds instances which wrap available values.
 * <p>
 * This class aims to improve the reliability of the code by avoiding uses of <code>null</code> values.
 * 
 * @param <T> Type of the value.
 */
public abstract class Maybe<T>
implements Iterable<T>, Describable {
	/**
	 * The {@link Constructor} enumeration represents the constructors of the algebraic data type.
	 */
	public static enum Constructor {
		NONE,
		SOME,
	}
	
	/**
	 * The {@link Maybe.Matcher} interface defines matching functions.
	 * 
	 * @param <T> Type of the value.
	 * @param <R> Type of the result.
	 * @param <X> Type of the exceptions.
	 * @see Maybe#match(Matcher)
	 */
	public interface Matcher<T, R, X extends Exception> {
		/**
		 * Matches the given <code>None</code> instance.
		 * 
		 * @param none The instance.
		 * @return The result of the function evaluation.
		 * @throws X When the evaluation fails.
		 */
		public R none(final None<? extends T> none)
		throws X;
		
		/**
		 * Matches the given <code>Some</code> instance.
		 * 
		 * @param some The instance.
		 * @return The result of the function evaluation.
		 * @throws X When the evaluation fails.
		 */
		public R some(final Some<? extends T> some)
		throws X;
	}
	
	/**
	 * The {@link Maybe.None} class represents the instances built using the <code>None</code> constructor.
	 * 
	 * @param <T> Type of the value.
	 */
	public static final class None<T>
	extends Maybe<T> {
		@Override
		public Constructor getConstructor() {
			return Constructor.NONE;
		}
		
		@Override
		public boolean isNone() {
			return true;
		}
		
		@Override
		public None<T> asNone() {
			return this;
		}
		
		@Override
		public <R, X extends Exception> R match(final Matcher<? super T, R, X> matcher)
		throws X {
			assert null != matcher;
			
			return matcher.none(this);
		}
		
		@Override
		public <R, X extends Exception> Maybe<R> map(final Function1<? super T, ? extends R, X> function) {
			return none();
		}
		
		@Override
		public <X extends Exception> Maybe<T> filter(final Predicate1<? super T, X> predicate) {
			return none();
		}
		
		@Override
		public <R, X extends Exception> Maybe<R> mapFilter(final Function1<? super T, ? extends Maybe<? extends R>, X> function) {
			return none();
		}
		
		public Iterator<T> iterator() {
			return CollectionUtils.iterator();
		}
		
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
		
		public void fillDescription(final Description description) {
			// Nothing to do.
		}
	}
	
	/**
	 * The {@link Maybe.Some} class represents the instances built using the <code>Some</code> constructor.
	 * 
	 * @param <T> Type of the value.
	 */
	public static final class Some<T>
	extends Maybe<T> {
		/**
		 * Instantiates a new instance wrapping the given value.
		 * 
		 * @param value The value. May be <code>null</code>.
		 */
		public Some(final T value) {
			_value = value;
		}
		
		@Override
		public Constructor getConstructor() {
			return Constructor.SOME;
		}
		
		@Override
		public boolean isSome() {
			return true;
		}
		
		@Override
		public Some<T> asSome() {
			return this;
		}
		
		/** The wrapped value. May be <code>null</code>. */
		private final T _value;
		
		/**
		 * Gets the value wrapped in the receiver instance.
		 * 
		 * @return The wrapped value. May be <code>null</code>.
		 */
		public T getValue() {
			return _value;
		}
		
		@Override
		public <R, X extends Exception> R match(final Matcher<? super T, R, X> matcher)
		throws X {
			return matcher.some(this);
		}
		
		@Override
		public <R, X extends Exception> Maybe<R> map(final Function1<? super T, ? extends R, X> function)
		throws X {
			assert null != function;
			
			return Maybe.<R>some(function.evaluate(_value));
		}
		
		@Override
		public <X extends Exception> Maybe<T> filter(final Predicate1<? super T, X> predicate)
		throws X {
			assert null != predicate;
			
			return predicate.evaluate(_value) ? this : Maybe.<T>none();
		}
		
		@Override
		public <R, X extends Exception> Maybe<R> mapFilter(final Function1<? super T, ? extends Maybe<? extends R>, X> function)
		throws X {
			return function.evaluate(_value).map(Functions.<R, RuntimeException>identity());
		}
		
		@SuppressWarnings("unchecked")
		public Iterator<T> iterator() {
			return CollectionUtils.iterator(_value);
		}
		
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
				return LangUtils.equals(_value, maybe._value);
			} else {
				return false;
			}
		}
		
		public void fillDescription(final Description description) {
			description.append("Value", _value);
		}
	}
	
	/**
	 * Builds an instance using the <code>None</code> constructor.
	 * 
	 * @param <Value> Type of the value.
	 * @return The built instance.
	 */
	@SuppressWarnings("unchecked")
	public static <Value> None<Value> none() {
		return (None<Value>) _NONE;
	}
	
	private static final None<?> _NONE = new None<Object>();
	
	/**
	 * Builds an instance using the <code>Some</code> constructor wrapping the given value.
	 * 
	 * @param <Value> Type of the value.
	 * @param value The value to wrap. May be <code>null</code>.
	 * @return The built instance.
	 */
	public static <Value> Some<Value> some(final Value value) {
		return new Some<Value>(value);
	}
	
	/**
	 * Builds a function which builds {@link Maybe.Some} instances.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, Maybe<T>, X> someFunction() {
		return (Function1<T, Maybe<T>, X>) _SOME_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _SOME_FUNCTION = new Function1<Object, Maybe<Object>, RuntimeException>() {
		public Maybe<Object> evaluate(final Object value) {
			return some(value);
		}
	};
	
	/**
	 * Wraps the given value into an instance.
	 * <p>
	 * This method wraps <code>null</code> values using the <code>None</code> constructor and non <code>null</code> values using the <code>Some</code>
	 * constructor.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param value The value to wrap. May be <code>null</code>.
	 * @return The instance.
	 */
	public static <T> Maybe<T> fromValue(final T value) {
		if (null != value) {
			return some(value);
		} else {
			return none();
		}
	}
	
	/**
	 * Builds a function which wraps values to instances.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see #fromValue(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, Maybe<T>, X> fromValueFunction() {
		return (Function1<T, Maybe<T>, X>) _FROM_VALUE_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _FROM_VALUE_FUNCTION = new Function1<Object, Maybe<Object>, RuntimeException>() {
		public Maybe<Object> evaluate(final Object value) {
			return fromValue(value);
		}
	};
	
	/**
	 * Unwraps the value contained in an instance.
	 * <p>
	 * This method returns <code>null</code> for the <code>None</code> instances and the wrapped value for the <code>Some</code> instances.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <T> Type of the value.
	 * @param maybe The instance to unwrap.
	 * @return The wrapped value or <code>null</code>.
	 */
	public static <T> T toValue(final Maybe<T> maybe) {
		assert null != maybe;
		
		return TypeUtils.get(maybe, null);
	}
	
	/**
	 * Builds a function which unwraps values from instances.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see #toValue(Maybe)
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<Maybe<T>, T, X> toValueFunction() {
		return (Function1<Maybe<T>, T, X>) _TO_VALUE_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _TO_VALUE_FUNCTION = new Function1<Maybe<Object>, Object, RuntimeException>() {
		public Object evaluate(final Maybe<Object> instance) {
			return toValue(instance);
		}
	};
	
	/**
	 * Gets the constructor of the receiver instance.
	 * 
	 * @return The constructor.
	 */
	public abstract Constructor getConstructor();
	
	/**
	 * Tests whether the receiver instance has been built using the {@link None} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>None</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isNone() {
		return false;
	}
	
	/**
	 * Get a view of the receiver instance as a {@link None} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>None</code> constructor.
	 */
	public None<T> asNone()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, None.class);
	}
	
	/**
	 * Tests whether the receiver instance has been built using the <code>Some</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>Some</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isSome() {
		return false;
	}
	
	/**
	 * Get a view of the receiver instance as a {@link Some} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>Some</code> constructor.
	 */
	public Some<T> asSome()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Some.class);
	}
	
	/**
	 * Matches the receiver instance according to the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <R> Type of the result.
	 * @param <X> Type of the exceptions.
	 * @param matcher The matching function.
	 * @return The result of the match.
	 * @throws X When the match fails.
	 */
	public abstract <R, X extends Exception> R match(final Matcher<? super T, R, X> matcher)
	throws X;
	
	/**
	 * Maps the value wrapped by the receiver instance using the given function.
	 * 
	 * @param <R> Type of the result value.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return An instance containing the mapped value.
	 * @throws X When the mapping fails.
	 */
	public abstract <R, X extends Exception> Maybe<R> map(final Function1<? super T, ? extends R, X> function)
	throws X;
	
	/**
	 * Builds a function which maps the values wrapped in the argument instances using the given function.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<Maybe<? extends T>, Maybe<R>, X> mapFunction(final Function1<? super T, ? extends R, ? extends X> function) {
		assert null != function;
		
		return new Function1<Maybe<? extends T>, Maybe<R>, X>() {
			public Maybe<R> evaluate(final Maybe<? extends T> value)
			throws X {
				assert null != value;
				
				return value.map(function);
			}
		};
	}
	
	/**
	 * Filters the value wrapped by the receiver instance using the given predicate.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return An instance containing the filtered value.
	 * @throws X When the filtering fails.
	 */
	public abstract <X extends Exception> Maybe<T> filter(final Predicate1<? super T, X> predicate)
	throws X;
	
	/**
	 * Builds a function which filters the values wrapped in the argument instances using the given predicate.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <X> Type of the exceptions.
	 * @param predicate The predicate.
	 * @return The built function.
	 */
	public static <T, X extends Exception> Function1<Maybe<? extends T>, Maybe<? extends T>, X> filterFunction(final Predicate1<? super T, ? extends X> predicate) {
		assert null != predicate;
		
		return new Function1<Maybe<? extends T>, Maybe<? extends T>, X>() {
			public Maybe<? extends T> evaluate(final Maybe<? extends T> value)
			throws X {
				assert null != value;
				
				return value.filter(predicate);
			}
		};
	}
	
	/**
	 * Maps and filters the value wrapped by the receiver instance.
	 * 
	 * @param <R> Type of the mapped value.
	 * @param <X> Type of the exceptions.
	 * @param function The mapping function.
	 * @return An instance containing the mapped value.
	 * @throws X When the mapping fails.
	 */
	public abstract <R, X extends Exception> Maybe<R> mapFilter(final Function1<? super T, ? extends Maybe<? extends R>, X> function)
	throws X;
	
	/**
	 * Builds a function which maps and filters the values wrapped in the argument instances using the given function.
	 * 
	 * @param <T> Type of the argument values.
	 * @param <R> Type of the result values.
	 * @param <X> Type of the exceptions.
	 * @param function The function.
	 * @return The built function.
	 */
	public static <T, R, X extends Exception> Function1<Maybe<? extends T>, Maybe<R>, X> mapFilterFunction(final Function1<? super T, ? extends Maybe<? extends R>, ? extends X> function) {
		assert null != function;
		
		return new Function1<Maybe<? extends T>, Maybe<R>, X>() {
			public Maybe<R> evaluate(final Maybe<? extends T> value)
			throws X {
				assert null != value;
				
				return value.mapFilter(function);
			}
		};
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
