/*
 *  Copyright 2006 Julien Dufour
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

import com.trazere.util.Assert;
import com.trazere.util.CannotComputeValueException;
import com.trazere.util.ObjectUtils;
import com.trazere.util.function.ApplicationException;
import com.trazere.util.function.Function;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link Maybe} class represents an algebraic data type which wraps an optional value.
 * <ul>
 * <li>The <code>None</code> constructor builds instances which wrap absent values.
 * <li>The <code>Some</code> constructor builds instances which wrap available values.
 * <p>
 * This class aims to improve the reliability of the code by avoiding uses of <code>null</code> values.
 * 
 * @param <Value> Type of the value.
 */
public abstract class Maybe<Value>
implements Describable {
	/**
	 * The {@link Constructor} enumeration represents the constructors of the algebraic data type.
	 */
	public static enum Constructor {
		NONE,
		SOME,
	}
	
	/**
	 * The {@link Matcher} interface defines functions on unwrapped {@link Maybe} instances.
	 * 
	 * @param <Value> Type of the value.
	 * @param <Result> Type of the result.
	 * @see Maybe#match(Matcher)
	 */
	public static interface Matcher<Value, Result> {
		/**
		 * Apply the receiver function to the given <code>None</code> instance.
		 * 
		 * @param none Argument instance of the function.
		 * @return The result of the function application.
		 * @throws CannotComputeValueException When the computation fails.
		 */
		public Result none(final None<Value> none)
		throws CannotComputeValueException;
		
		/**
		 * Apply the receiver function to the given <code>Some</code> instance.
		 * 
		 * @param some Argument instance of the function.
		 * @return The result of the function application.
		 * @throws CannotComputeValueException When the computation fails.
		 */
		public Result some(final Some<Value> some)
		throws CannotComputeValueException;
	}
	
	/**
	 * The {@link None} class represents the instances built using the <code>None</code> constructor.
	 * 
	 * @param <Value> Type of the value.
	 */
	public static final class None<Value>
	extends Maybe<Value> {
		@Override
		public boolean isNone() {
			return true;
		}
		
		@Override
		public None<Value> asNone() {
			return this;
		}
		
		@Override
		public boolean isSome() {
			return false;
		}
		
		@Override
		public Some<Value> asSome()
		throws InvalidConstructorException {
			throw new InvalidConstructorException("Cannot cast instance " + this);
		}
		
		@Override
		public Constructor getConstructor() {
			return Constructor.NONE;
		}
		
		@Override
		public <Result> Result match(final Matcher<Value, Result> matcher) {
			return matcher.none(this);
		}
		
		@Override
		public <Result> Maybe<Result> lift(final Function<? super Value, Result> function) {
			Assert.notNull(function);
			
			// Lift.
			return none();
		}
		
		public void fillDescription(final StringBuilder builder) {
			builder.append(" - None");
		}
		
		@Override
		public int hashCode() {
			return getClass().hashCode();
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
	}
	
	/**
	 * The {@link Some} class represents the instances built using the <code>Some</code> constructor.
	 * 
	 * @param <Value> Type of the value.
	 */
	public static final class Some<Value>
	extends Maybe<Value> {
		protected final Value _value;
		
		/**
		 * Build a new instance wrapping the given value.
		 * 
		 * @param value Value to wrap. May be <code>null</code>.
		 */
		public Some(final Value value) {
			// Initialization.
			_value = value;
		}
		
		@Override
		public boolean isNone() {
			return false;
		}
		
		@Override
		public None<Value> asNone()
		throws InvalidConstructorException {
			throw new InvalidConstructorException("Cannot cast instance " + this);
		}
		
		@Override
		public boolean isSome() {
			return true;
		}
		
		@Override
		public Some<Value> asSome() {
			return this;
		}
		
		@Override
		public Constructor getConstructor() {
			return Constructor.SOME;
		}
		
		/**
		 * Get the value wrapped in the receiver instance.
		 * 
		 * @return The wrapped value. May be <code>null</code>.
		 */
		public Value getValue() {
			return _value;
		}
		
		@Override
		public <Result> Result match(final Matcher<Value, Result> matcher) {
			return matcher.some(this);
		}
		
		@Override
		public <Result> Maybe<Result> lift(final Function<? super Value, Result> function)
		throws ApplicationException {
			Assert.notNull(function);
			
			// Lift.
			return some(function.apply(_value));
		}
		
		@Override
		public int hashCode() {
			int result = getClass().hashCode();
			if (null != _value) {
				result = result * 31 + _value.hashCode();
			}
			return result;
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final Some<?> maybe = (Some<?>) object;
				return ObjectUtils.equals(_value, maybe._value);
			} else {
				return false;
			}
		}
		
		public void fillDescription(final StringBuilder builder) {
			builder.append(" - Some = ").append(_value);
		}
	}
	
	private static final None<?> _NONE = new None<Object>();
	
	/**
	 * Build an instance using the <code>None</code> constructor.
	 * 
	 * @param <Value> Type of the value.
	 * @return The instance.
	 */
	@SuppressWarnings("unchecked")
	public static <Value> None<Value> none() {
		return (None<Value>) _NONE;
	}
	
	/**
	 * Build an instance using the <code>Some</code> constructor wrapping the given value.
	 * 
	 * @param <Value> Type of the value.
	 * @param value Value to wrap. May be <code>null</code>.
	 * @return The instance.
	 */
	public static <Value> Some<Value> some(final Value value) {
		return new Some<Value>(value);
	}
	
	/**
	 * Wrap the given value into a {@link Maybe} instance.
	 * <p>
	 * This method wraps <code>null</code> values using the <code>None</code> constructor and non <code>null</code> values using the <code>Some</code>
	 * constructor.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <Value> Type of the value.
	 * @param value Value to wrap. May be <code>null</code>.
	 * @return The instance.
	 */
	public static <Value> Maybe<Value> fromValue(final Value value) {
		if (null != value) {
			return some(value);
		} else {
			return none();
		}
	}
	
	private static final Matcher<?, ?> TO_MATCHER = new Matcher<Object, Object>() {
		public Object none(final None<Object> none) {
			return null;
		}
		
		public Object some(final Some<Object> some) {
			return some.getValue();
		}
	};
	
	/**
	 * Unwrap the value from the given {@link Maybe} instance.
	 * <p>
	 * This method returns <code>null</code> for the <code>None</code> instances and the wrapped value for the <code>Some</code> instances.
	 * <p>
	 * This method aims to simplify the interoperability with legacy Java code.
	 * 
	 * @param <Value> Type of the value.
	 * @param maybe The instance to unwrap.
	 * @return The wrapped value or <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public static <Value> Value toValue(final Maybe<Value> maybe) {
		return maybe.match((Matcher<Value, Value>) TO_MATCHER);
	}
	
	/**
	 * Test wether the receiver instance has been built using the <code>None</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>None</code> constructor, <code>false</code> otherwise.
	 */
	public abstract boolean isNone();
	
	/**
	 * Cast the receiver instance as a {@link None} instance.
	 * 
	 * @return The instance.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>None</code> constructor.
	 */
	public abstract None<Value> asNone()
	throws InvalidConstructorException;
	
	/**
	 * Test wether the receiver instance has been built using the <code>Some</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>Some</code> constructor, <code>false</code> otherwise.
	 */
	public abstract boolean isSome();
	
	/**
	 * Cast the receiver instance as a {@link Some} instance.
	 * 
	 * @return The instance.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>Some</code> constructor.
	 */
	public abstract Some<Value> asSome()
	throws InvalidConstructorException;
	
	/**
	 * Get the constructor of the receiver instance.
	 * 
	 * @return The constructor.
	 */
	public abstract Constructor getConstructor();
	
	/**
	 * Apply the given matcher function to the receiver instance.
	 * <p>
	 * This method implements some kind of simple pattern matching.
	 * 
	 * @param <Result> Type of the result.
	 * @param matcher Matcher to use.
	 * @return The result of the function application.
	 */
	public abstract <Result> Result match(final Matcher<Value, Result> matcher);
	
	public abstract <Result> Maybe<Result> lift(final Function<? super Value, Result> function)
	throws ApplicationException;
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
