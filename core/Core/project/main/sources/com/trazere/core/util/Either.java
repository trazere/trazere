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
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.LangUtils;
import com.trazere.core.text.Describable;
import com.trazere.core.text.Description;
import com.trazere.core.text.TextUtils;

/**
 * The {@link Either} class encodes a tagged union data type which represents a binary disjonction.
 * <ul>
 * <li>Instances built using the {@link Left} constructor encode the first alternative (left alternative).
 * <li>Instances built using the {@link Right} constructor encode the second alternative (right alternative).
 * 
 * @param <L> Type of the left value.
 * @param <R> Type of the right value.
 */
public abstract class Either<L, R>
implements Describable {
	/**
	 * Builds a {@link Either} instance using the {@link Left} constructor.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param left Left value to wrap.
	 * @return The built instance.
	 * @see Left
	 */
	public static <L, R> Either<L, R> left(final L left) {
		return new Left<L, R>(left);
	}
	
	/**
	 * Builds a function that builds {@link Either} instances using the {@link Left} constructor.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @return The built function.
	 * @see #left(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <L, R> Function<L, Either<L, R>> leftFunction() {
		return (Function<L, Either<L, R>>) LEFT_FUNCTION;
	}
	
	private static final Function<?, ?> LEFT_FUNCTION = new Function<Object, Either<?, ?>>() {
		@Override
		public Either<?, ?> evaluate(final Object value) {
			return Either.left(value);
		}
	};
	
	/**
	 * Builds a {@link Either} instance using the {@link Right} constructor.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param right Right value to wrap.
	 * @return The built instance.
	 * @see Right
	 */
	public static <L, R> Either<L, R> right(final R right) {
		return new Right<L, R>(right);
	}
	
	/**
	 * Builds a function that builds {@link Either} instances using the {@link Right} constructor.
	 * 
	 * @param <L> Type of the left values.
	 * @param <R> Type of the right values.
	 * @return The built function.
	 * @see #right(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <L, R> Function<R, Either<L, R>> rightFunction() {
		return (Function<R, Either<L, R>>) RIGHT_FUNCTION;
	}
	
	private static final Function<?, ?> RIGHT_FUNCTION = new Function<Object, Either<?, ?>>() {
		@Override
		public Either<?, ?> evaluate(final Object value) {
			return Either.right(value);
		}
	};
	
	// Left.
	
	/**
	 * The {@link Either.Left} class represents instances of {@link Either} that encode the first alternative (left alternative).
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 */
	public static final class Left<L, R>
	extends Either<L, R> {
		/**
		 * Instantiates a new {@link Either} instance wrapping the given left value.
		 * 
		 * @param left Left value to wrap.
		 */
		public Left(final L left) {
			_left = left;
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
		
		/** Wrapped left value. */
		private final L _left;
		
		/**
		 * Gets the left value wrapped in the receiver instance.
		 * 
		 * @return The wrapped left value.
		 */
		public L getLeft() {
			return _left;
		}
		
		// Matching.
		
		@Override
		public <RT> RT match(final Matcher<? super L, ? super R, RT> matcher) {
			return matcher.left(this);
		}
		
		// Functional.
		
		@Override
		public <RL> Either<RL, R> mapLeft(final Function<? super L, ? extends RL> function) {
			return Either.<RL, R>left(function.evaluate(_left));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <RR> Either<L, RR> mapRight(final Function<? super R, ? extends RR> function) {
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
				return LangUtils.safeEquals(_left, either._left);
			} else {
				return false;
			}
		}
		
		@Override
		public void appendDescription(final Description description) {
			description.append("Left", _left);
		}
	}
	
	/**
	 * Tests whether the receiver instance has been built using the {@link Left} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the Left</code> constructor, <code>false</code> otherwise.
	 */
	public boolean isLeft() {
		return false;
	}
	
	/**
	 * Gets a view of the receiver instance as a {@link Left} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when the receiver instance has not been built using the {@link Left} constructor.
	 */
	public Left<L, R> asLeft()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Left.class);
	}
	
	/**
	 * Builds an extractor of left values.
	 * 
	 * @param <L> Type of the left value.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <L> Function<Either<? extends L, ?>, Maybe<L>> getLeftExtractor() {
		return (Function<Either<? extends L, ?>, Maybe<L>>) GET_LEFT_EXTRACTOR;
	}
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_LEFT_EXTRACTOR = new Function<Either<Object, Object>, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Either<Object, Object> instance) {
			return instance.isLeft() ? Maybe.some(instance.asLeft().getLeft()) : Maybe.<Object>none();
		}
	};
	
	// Right.
	
	/**
	 * The {@link Either.Right} class represents instances of {@link Either} that encode the second alternative (right alternative).
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 */
	public static final class Right<L, R>
	extends Either<L, R> {
		/**
		 * Instantiates a new {@link Either} instance wrapping the given right value.
		 * 
		 * @param right Right value to wrap.
		 */
		public Right(final R right) {
			_right = right;
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
		
		/** Wrapped right value. */
		private final R _right;
		
		/**
		 * Gets the right value wrapped in the receiver instance.
		 * 
		 * @return The wrapped right value.
		 */
		public R getRight() {
			return _right;
		}
		
		// Matching.
		
		@Override
		public <RT> RT match(final Matcher<? super L, ? super R, RT> matcher) {
			return matcher.right(this);
		}
		
		// Functional.
		
		@SuppressWarnings("unchecked")
		@Override
		public <RL> Either<RL, R> mapLeft(final Function<? super L, ? extends RL> function) {
			return (Either<RL, R>) this;
		}
		
		@Override
		public <RR> Either<L, RR> mapRight(final Function<? super R, ? extends RR> function) {
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
				return LangUtils.safeEquals(_right, either._right);
			} else {
				return false;
			}
		}
		
		@Override
		public void appendDescription(final Description description) {
			description.append("Right", _right);
		}
	}
	
	/**
	 * Tests whether the receiver instance has been built using the {@link Right} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built using the {@link Right} constructor, <code>false</code> otherwise.
	 */
	public boolean isRight() {
		return false;
	}
	
	/**
	 * Gets a view of the receiver instance as a {@link Right} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when the receiver instance has not been built using the {@link Right} constructor.
	 */
	public Right<L, R> asRight()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Right.class);
	}
	
	/**
	 * Builds an extractor of right values.
	 * 
	 * @param <R> Type of the right value.
	 * @return The built extractor.
	 */
	@SuppressWarnings("unchecked")
	public static <R> Function<Either<?, ? extends R>, Maybe<R>> getRightExtractor() {
		return (Function<Either<?, ? extends R>, Maybe<R>>) GET_RIGHT_EXTRACTOR;
	}
	
	private static final Function<? extends Either<?, ?>, ? extends Maybe<?>> GET_RIGHT_EXTRACTOR = new Function<Either<Object, Object>, Maybe<Object>>() {
		@Override
		public Maybe<Object> evaluate(final Either<Object, Object> instance) {
			return instance.isRight() ? Maybe.some(instance.asRight().getRight()) : Maybe.<Object>none();
		}
	};
	
	// Matching.
	
	/**
	 * The {@link Either.Matcher} interface defines matching functions of {@link Either} instances.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RT> Type of the result.
	 * @see Either#match(Matcher)
	 */
	public interface Matcher<L, R, RT> {
		/**
		 * Matches the given {@link Either} instance built using the {@link Either.Left} constructor.
		 * 
		 * @param left {@link Either} instance to match.
		 * @return The result of the matching function evaluation.
		 */
		RT left(Left<? extends L, ? extends R> left);
		
		/**
		 * Matches the given {@link Either} instance built using the {@link Either.Right} constructor.
		 * 
		 * @param right {@link Either} instance to match.
		 * @return The result of the matching function evaluation.
		 */
		RT right(Right<? extends L, ? extends R> right);
	}
	
	/**
	 * Matches the receiver {@link Either} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <RT> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 */
	public abstract <RT> RT match(final Matcher<? super L, ? super R, RT> matcher);
	
	// Functional.
	
	/**
	 * Maps the left value wrapped by the receiver {@link Either} instance using the given function.
	 * 
	 * @param <RL> Type of the mapped left value.
	 * @param function Mapping function to use.
	 * @return The {@link Either} instance wrapping the mapped left value.
	 */
	public abstract <RL> Either<RL, R> mapLeft(final Function<? super L, ? extends RL> function);
	
	/**
	 * Builds a function that maps the left value wrapped in {@link Either} instances using the given function.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RL> Type of the mapped left value.
	 * @param function Mapping function to use.
	 * @return The built function.
	 * @see #mapLeft(Function)
	 */
	public static <L, R, RL> Function<Either<? extends L, R>, Either<RL, R>> mapLeftFunction(final Function<? super L, ? extends RL> function) {
		assert null != function;
		
		return new Function<Either<? extends L, R>, Either<RL, R>>() {
			@Override
			public Either<RL, R> evaluate(final Either<? extends L, R> either) {
				return either.mapLeft(function);
			}
		};
	}
	
	/**
	 * Maps the right value wrapped by the receiver {@link Either} instance using the given function.
	 * 
	 * @param <RR> Type of the mapped right value.
	 * @param function Mapping function to use.
	 * @return The {@link Either} instance wrapping the mapped right value.
	 */
	public abstract <RR> Either<L, RR> mapRight(final Function<? super R, ? extends RR> function);
	
	/**
	 * Builds a function that maps the right value wrapped in {@link Either} instances using the given function.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RR> Type of the mapped right value.
	 * @param function Mapping function to use.
	 * @return The built function.
	 * @see #mapRight(Function)
	 */
	public static <L, R, RR> Function<Either<L, ? extends R>, Either<L, RR>> mapRightFunction(final Function<? super R, ? extends RR> function) {
		assert null != function;
		
		return new Function<Either<L, ? extends R>, Either<L, RR>>() {
			@Override
			public Either<L, RR> evaluate(final Either<L, ? extends R> either) {
				return either.mapRight(function);
			}
		};
	}
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
}
