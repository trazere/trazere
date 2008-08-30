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
package com.trazere.util.type;

import com.trazere.util.lang.CannotComputeValueException;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link Either} class represents an algebraic data type which wraps a binary disjonction.
 * <ul>
 * <li>The <code>Left</code> constructor builds instances which encode the first alternative and wrap the corresponding values.
 * <li>The <code>Right</code> constructor builds instances which encode the second alternative and wrap the corresponding values.
 * 
 * @param <LeftValue> Type of the left value.
 * @param <RightValue> Type of the right value.
 */
public abstract class Either<LeftValue, RightValue>
implements Describable {
	/**
	 * The {@link Constructor} enumeration represents the constructors of the algebraic data type.
	 */
	public static enum Constructor {
		LEFT,
		RIGHT,
	}
	
	/**
	 * The {@link Either.Matcher} interface defines functions on unwrapped {@link Either} instances.
	 * 
	 * @param <LeftValue> Type of the left value.
	 * @param <RightValue> Type of the right value.
	 * @param <Result> Type of the result.
	 * @see Either#match(Matcher)
	 */
	public static interface Matcher<LeftValue, RightValue, Result> {
		/**
		 * Apply the receiver function to the given <code>Left</code> instance.
		 * 
		 * @param left Argument instance of the function.
		 * @return The result of the function application.
		 * @throws CannotComputeValueException When the computation fails.
		 */
		public Result left(final Left<LeftValue, RightValue> left)
		throws CannotComputeValueException;
		
		/**
		 * Apply the receiver function to the given <code>Right</code> instance.
		 * 
		 * @param right Argument instance of the function.
		 * @return The result of the function application.
		 * @throws CannotComputeValueException When the computation fails.
		 */
		public Result right(final Right<LeftValue, RightValue> right)
		throws CannotComputeValueException;
	}
	
	/**
	 * The {@link Either.Left} class represents the instances built using the <code>Left</code> constructor.
	 * 
	 * @param <LeftValue> Type of the left value.
	 * @param <RightValue> Type of the right value.
	 */
	public final static class Left<LeftValue, RightValue>
	extends Either<LeftValue, RightValue> {
		protected LeftValue _left;
		
		/**
		 * Build a new instance wrapping the given left value.
		 * 
		 * @param left Left value to wrap. May be <code>null</code>.
		 */
		public Left(final LeftValue left) {
			// Initialization.
			_left = left;
		}
		
		@Override
		public boolean isLeft() {
			return true;
		}
		
		@Override
		public Left<LeftValue, RightValue> asLeft() {
			return this;
		}
		
		@Override
		public boolean isRight() {
			return false;
		}
		
		@Override
		public Right<LeftValue, RightValue> asRight()
		throws InvalidConstructorException {
			throw new InvalidConstructorException("Cannot cast instance " + this);
		}
		
		@Override
		public Constructor getConstructor() {
			return Constructor.LEFT;
		}
		
		/**
		 * Get the left value wrapped in the receiver instance.
		 * 
		 * @return The wrapped value. May be <code>null</code>.
		 */
		public LeftValue getLeft() {
			return _left;
		}
		
		@Override
		public <Result> Result match(final Matcher<LeftValue, RightValue, Result> matcher) {
			return matcher.left(this);
		}
		
		@Override
		public int hashCode() {
			final HashCode hashCode = new HashCode(this);
			hashCode.append(_left);
			return hashCode.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final Left<?, ?> either = (Left<?, ?>) object;
				return LangUtils.equals(_left, either._left);
			} else {
				return false;
			}
		}
		
		public void fillDescription(final StringBuilder builder) {
			builder.append(" - Left = ").append(_left);
		}
	}
	
	/**
	 * The {@link Either.Right} class represents the instances built using the <code>Right</code> constructor.
	 * 
	 * @param <LeftValue> Type of the left value.
	 * @param <RightValue> Type of the right value.
	 */
	public final static class Right<LeftValue, RightValue>
	extends Either<LeftValue, RightValue> {
		protected final RightValue _right;
		
		/**
		 * Build a new instance wrapping the given right value.
		 * 
		 * @param right Right value to wrap. May be <code>null</code>.
		 */
		public Right(final RightValue right) {
			// Initialization.
			_right = right;
		}
		
		@Override
		public boolean isLeft() {
			return false;
		}
		
		@Override
		public Left<LeftValue, RightValue> asLeft()
		throws InvalidConstructorException {
			throw new InvalidConstructorException("Cannot cast instance " + this);
		}
		
		@Override
		public boolean isRight() {
			return true;
		}
		
		@Override
		public Right<LeftValue, RightValue> asRight() {
			return this;
		}
		
		@Override
		public Constructor getConstructor() {
			return Constructor.RIGHT;
		}
		
		/**
		 * Get the right value wrapped in the receiver instance.
		 * 
		 * @return The wrapped value. May be <code>null</code>.
		 */
		public RightValue getRight() {
			return _right;
		}
		
		@Override
		public <Result> Result match(final Matcher<LeftValue, RightValue, Result> matcher) {
			return matcher.right(this);
		}
		
		@Override
		public int hashCode() {
			final HashCode hashCode = new HashCode(this);
			hashCode.append(_right);
			return hashCode.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final Right<?, ?> either = (Right<?, ?>) object;
				return LangUtils.equals(_right, either._right);
			} else {
				return false;
			}
		}
		
		public void fillDescription(final StringBuilder builder) {
			builder.append(" - Right = ").append(_right);
		}
	}
	
	/**
	 * Build an instance using the <code>Left</code> constructor wrapping the given value.
	 * 
	 * @param <LeftValue> Type of the left value.
	 * @param <RightValue> Type of the right value.
	 * @param left Left value to wrap. May be <code>null</code>.
	 * @return The instance.
	 */
	public static <LeftValue, RightValue> Either<LeftValue, RightValue> left(final LeftValue left) {
		return new Left<LeftValue, RightValue>(left);
	}
	
	/**
	 * Build an instance using the <code>Left</code> constructor wrapping the given value.
	 * 
	 * @param <LeftValue> Type of the left value.
	 * @param <RightValue> Type of the right value.
	 * @param right Right value to wrap. May be <code>null</code>.
	 * @return The instance.
	 */
	public static <LeftValue, RightValue> Either<LeftValue, RightValue> right(final RightValue right) {
		return new Right<LeftValue, RightValue>(right);
	}
	
	/**
	 * Test wether the receiver instance has been built using the <code>Left</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>Left</code> constructor, <code>false</code> otherwise.
	 */
	public abstract boolean isLeft();
	
	/**
	 * Cast the receiver instance as a {@link Left} instance.
	 * 
	 * @return The instance.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>Left</code> constructor.
	 */
	public abstract Left<LeftValue, RightValue> asLeft()
	throws InvalidConstructorException;
	
	/**
	 * Test wether the receiver instance has been built using the <code>Right</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>Right</code> constructor, <code>false</code> otherwise.
	 */
	public abstract boolean isRight();
	
	/**
	 * Cast the receiver instance as a {@link Right} instance.
	 * 
	 * @return The instance.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>Right</code> constructor.
	 */
	public abstract Right<LeftValue, RightValue> asRight()
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
	public abstract <Result> Result match(final Matcher<LeftValue, RightValue, Result> matcher);
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
