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
package com.trazere.util.type;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.text.Describable;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;

/**
 * The {@link Either} class represents an algebraic data type which wraps a binary disjonction.
 * <ul>
 * <li>The <code>Left</code> constructor builds instances which encode the first alternative and wrap the corresponding values.
 * <li>The <code>Right</code> constructor builds instances which encode the second alternative and wrap the corresponding values.
 * 
 * @param <L> Type of the left value.
 * @param <R> Type of the right value.
 */
public abstract class Either<L, R>
implements Describable {
	/**
	 * Builds a {@link Either.Left} instance wrapping the given value.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param left The left value to wrap. May be <code>null</code>.
	 * @return The built instance.
	 */
	public static <L, R> Either<L, R> left(final L left) {
		return new Left<L, R>(left);
	}
	
	/**
	 * Builds a function which builds {@link Either.Left} instances.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <L, R, X extends Exception> Function1<L, Either<L, R>, X> leftFunction() {
		return (Function1<L, Either<L, R>, X>) _LEFT_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _LEFT_FUNCTION = new Function1<Object, Either<?, ?>, RuntimeException>() {
		@Override
		public Either<?, ?> evaluate(final Object value) {
			return Either.left(value);
		}
	};
	
	/**
	 * Builds a {@link Either.Right} instance wrapping the given value.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param right The right value to wrap. May be <code>null</code>.
	 * @return The built instance.
	 */
	public static <L, R> Either<L, R> right(final R right) {
		return new Right<L, R>(right);
	}
	
	/**
	 * Builds a function which builds {@link Either.Right} instances.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @param <X> The of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <L, R, X extends Exception> Function1<R, Either<L, R>, X> rightFunction() {
		return (Function1<R, Either<L, R>, X>) _RIGHT_FUNCTION;
	}
	
	private static final Function1<?, ?, ?> _RIGHT_FUNCTION = new Function1<Object, Either<?, ?>, RuntimeException>() {
		@Override
		public Either<?, ?> evaluate(final Object value) {
			return Either.right(value);
		}
	};
	
	// Constructor.
	
	/**
	 * The {@link Either.Constructor} enumeration represents the constructors of the algebraic data type.
	 */
	public static enum Constructor {
		LEFT, RIGHT,
	}
	
	/**
	 * Gets the constructor of the receiver instance.
	 * 
	 * @return The constructor.
	 */
	public abstract Constructor getConstructor();
	
	// Left.
	
	/**
	 * The {@link Either.Left} class represents the instances built using the <code>Left</code> constructor.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 */
	public final static class Left<L, R>
	extends Either<L, R> {
		/**
		 * Instantiates a new instance wrapping the given left value.
		 * 
		 * @param left The left value. May be <code>null</code>.
		 */
		public Left(final L left) {
			_left = left;
		}
		
		// Constructor.
		
		@Override
		public Constructor getConstructor() {
			return Constructor.LEFT;
		}
		
		// Left.
		
		@Override
		public boolean isLeft() {
			return true;
		}
		
		@Override
		public Left<L, R> asLeft() {
			return this;
		}
		
		/** The wrapped left value. May be <code>null</code>. */
		private final L _left;
		
		/**
		 * Gets the left value wrapped in the receiver instance.
		 * 
		 * @return The wrapped left value. May be <code>null</code>.
		 */
		public L getLeft() {
			return _left;
		}
		
		// Matching.
		
		@Override
		public <RT, X extends Exception> RT match(final Matcher<? super L, ? super R, RT, X> matcher)
		throws X {
			assert null != matcher;
			
			return matcher.left(this);
		}
		
		// Functions.
		
		@Override
		public <RL, X extends Exception> Either<RL, R> mapLeft(final Function1<? super L, ? extends RL, X> function)
		throws X {
			assert null != function;
			
			return Either.<RL, R>left(function.evaluate(_left));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <RR, X extends Exception> Either<L, RR> mapRight(final Function1<? super R, ? extends RR, X> function) {
			return (Either<L, RR>) this;
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_left);
			return result.get();
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
		
		@Override
		public void fillDescription(final Description description) {
			description.append("Left", _left);
		}
	}
	
	/**
	 * Tests whether the receiver instance has been built using the <code>Left</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>Left</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isLeft() {
		return false;
	}
	
	/**
	 * Gets a view of the receiver instance as a {@link Left} instance.
	 * 
	 * @return The instance.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>Left</code> constructor.
	 */
	public Left<L, R> asLeft()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Left.class);
	}
	
	/**
	 * Builds an extractor of left values.
	 * 
	 * @param <L> Type of the left value.
	 * @param <X> Type of the exceptions.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <L, X extends Exception> Function1<Either<? extends L, ?>, Maybe<L>, X> getLeftExtractor() {
		return (Function1<Either<? extends L, ?>, Maybe<L>, X>) _GET_LEFT_EXTRACTOR;
	}
	
	private static final Function1<? extends Either<?, ?>, ? extends Maybe<?>, ?> _GET_LEFT_EXTRACTOR = new Function1<Either<Object, Object>, Maybe<Object>, RuntimeException>() {
		@Override
		public Maybe<Object> evaluate(final Either<Object, Object> instance) {
			assert null != instance;
			
			return instance.isLeft() ? Maybe.some(instance.asLeft().getLeft()) : Maybe.<Object>none();
		}
	};
	
	// Right.
	
	/**
	 * The {@link Either.Right} class represents the instances built using the <code>Right</code> constructor.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 */
	public final static class Right<L, R>
	extends Either<L, R> {
		/**
		 * Instantiates a new instance wrapping the given right value.
		 * 
		 * @param right The value to wrap. May be <code>null</code>.
		 */
		public Right(final R right) {
			_right = right;
		}
		
		// Constructor.
		
		@Override
		public Constructor getConstructor() {
			return Constructor.RIGHT;
		}
		
		// Right.
		
		@Override
		public boolean isRight() {
			return true;
		}
		
		@Override
		public Right<L, R> asRight() {
			return this;
		}
		
		/** The wrapped right value. May be <code>null</code>. */
		private final R _right;
		
		/**
		 * Gets the right value wrapped in the receiver instance.
		 * 
		 * @return The wrapped value. May be <code>null</code>.
		 */
		public R getRight() {
			return _right;
		}
		
		// Matching.
		
		@Override
		public <Result, X extends Exception> Result match(final Matcher<? super L, ? super R, Result, X> matcher)
		throws X {
			assert null != matcher;
			
			return matcher.right(this);
		}
		
		// Functions.
		
		@SuppressWarnings("unchecked")
		@Override
		public <RL, X extends Exception> Either<RL, R> mapLeft(final Function1<? super L, ? extends RL, X> function) {
			return (Either<RL, R>) this;
		}
		
		@Override
		public <RR, X extends Exception> Either<L, RR> mapRight(final Function1<? super R, ? extends RR, X> function)
		throws X {
			assert null != function;
			
			return Either.<L, RR>right(function.evaluate(_right));
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_right);
			return result.get();
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
		
		@Override
		public void fillDescription(final Description description) {
			description.append("Right", _right);
		}
	}
	
	/**
	 * Tests whether the receiver instance has been built using the <code>Right</code> constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the <code>Right</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isRight() {
		return false;
	}
	
	/**
	 * Gets a view of the receiver instance as a {@link Right} instance.
	 * 
	 * @return The instance.
	 * @throws InvalidConstructorException when the receiver instance has not been built with the <code>Right</code> constructor.
	 */
	public Right<L, R> asRight()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Right.class);
	}
	
	/**
	 * Builds an extractor of right values.
	 * 
	 * @param <R> Type of the right value.
	 * @param <X> Type of the exceptions.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <R, X extends Exception> Function1<Either<?, ? extends R>, Maybe<R>, X> getRightExtractor() {
		return (Function1<Either<?, ? extends R>, Maybe<R>, X>) _GET_RIGHT_EXTRACTOR;
	}
	
	private static final Function1<? extends Either<?, ?>, ? extends Maybe<?>, ?> _GET_RIGHT_EXTRACTOR = new Function1<Either<Object, Object>, Maybe<Object>, RuntimeException>() {
		@Override
		public Maybe<Object> evaluate(final Either<Object, Object> instance) {
			assert null != instance;
			
			return instance.isRight() ? Maybe.some(instance.asRight().getRight()) : Maybe.<Object>none();
		}
	};
	
	// Matching.
	
	/**
	 * The {@link Either.Matcher} interface defines matching functions.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RT> Type of the result.
	 * @param <X> Type of the exceptions.
	 * @see Either#match(Matcher)
	 */
	public interface Matcher<L, R, RT, X extends Exception> {
		/**
		 * Matches the given <code>Left</code> instance.
		 * 
		 * @param left The instance.
		 * @return The result of the function evaluation.
		 * @throws X When the evaluation fails.
		 */
		public RT left(final Left<? extends L, ? extends R> left)
		throws X;
		
		/**
		 * Matches the given <code>Right</code> instance.
		 * 
		 * @param right The instance.
		 * @return The result of the function evaluation.
		 * @throws X When the evaluation fails.
		 */
		public RT right(final Right<? extends L, ? extends R> right)
		throws X;
	}
	
	/**
	 * Matches the receiver instance according to the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <RT> Type of the result.
	 * @param <X> Type of the exceptions.
	 * @param matcher The matching function.
	 * @return The result of the match.
	 * @throws X When the match fails.
	 */
	public abstract <RT, X extends Exception> RT match(final Matcher<? super L, ? super R, RT, X> matcher)
	throws X;
	
	// Functions.
	
	/**
	 * Maps the left value wrapped by the receiver instance.
	 * 
	 * @param <RL> Type of the mapped left value.
	 * @param <X> Type of the exceptions.
	 * @param function The mapping function.
	 * @return An instance containing the mapped value.
	 * @throws X When the mapping fails.
	 */
	public abstract <RL, X extends Exception> Either<RL, R> mapLeft(final Function1<? super L, ? extends RL, X> function)
	throws X;
	
	/**
	 * Maps the left value wrapped by the receiver instance.
	 * 
	 * @param <RR> Type of the mapped right value.
	 * @param <X> Type of the exceptions.
	 * @param function The mapping function.
	 * @return An instance containing the mapped value.
	 * @throws X When the mapping fails.
	 */
	public abstract <RR, X extends Exception> Either<L, RR> mapRight(final Function1<? super R, ? extends RR, X> function)
	throws X;
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
