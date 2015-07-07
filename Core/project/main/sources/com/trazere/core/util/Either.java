/*
 *  Copyright 2006-2015 Julien Dufour
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
import com.trazere.core.lang.ObjectUtils;
import com.trazere.core.text.Describable;
import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.text.TextUtils;
import java.io.Serializable;

// TODO: replace by Variant2 ?

/**
 * The {@link Either} class implements a tagged union data type that represents a binary disjonction.
 * <ul>
 * <li>Instances built using the {@link Left} constructor encode the first alternative (left alternative).
 * <li>Instances built using the {@link Right} constructor encode the second alternative (right alternative).
 * 
 * @param <L> Type of the left value.
 * @param <R> Type of the right value.
 * @since 1.0
 */
public abstract class Either<L, R>
implements Serializable, Describable {
	private static final long serialVersionUID = 1L;
	
	// TODO: move to Eithers ?
	/**
	 * Builds a {@link Either} instance using the {@link Left} constructor.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param left Left value to wrap.
	 * @return The built instance.
	 * @see Left
	 * @since 1.0
	 */
	public static <L, R> Either<L, R> left(final L left) {
		return new Left<>(left);
	}
	
	// TODO: move to Eithers ?
	/**
	 * Builds a {@link Either} instance using the {@link Right} constructor.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param right Right value to wrap.
	 * @return The built instance.
	 * @see Right
	 * @since 1.0
	 */
	public static <L, R> Either<L, R> right(final R right) {
		return new Right<>(right);
	}
	
	// Left.
	
	/**
	 * The {@link Either.Left} class represents the instances of {@link Either} that encode the first alternative (left alternative).
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @since 1.0
	 */
	public static final class Left<L, R>
	extends Either<L, R> {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Instantiates a new {@link Either} instance wrapping the given left value.
		 * 
		 * @param value Left value to wrap.
		 * @since 1.0
		 */
		public Left(final L value) {
			_value = value;
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
		
		@Override
		public Maybe<L> getLeft() {
			return Maybe.some(_value);
		}
		
		// Value.
		
		/** Wrapped left value. */
		private final L _value;
		
		/**
		 * Gets the left value wrapped in this instance.
		 * 
		 * @return The wrapped left value.
		 * @since 1.0
		 */
		public L getValue() {
			return _value;
		}
		
		// Matching.
		
		@Override
		public <RT> RT match(final Matcher<? super L, ? super R, ? extends RT> matcher) {
			return matcher.left(this);
		}
		
		// Functional.
		
		@Override
		public <RL> Either<RL, R> mapLeft(final Function<? super L, ? extends RL> function) {
			return Either.<RL, R>left(function.evaluate(_value));
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
			result.append(_value);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final Left<?, ?> either = (Left<?, ?>) object;
				return ObjectUtils.safeEquals(_value, either._value);
			} else {
				return false;
			}
		}
		
		@Override
		public void appendDescription(final DescriptionBuilder description) {
			description.append("Left", _value);
		}
	}
	
	/**
	 * Tests whether this instance has been built using the {@link Left} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built with the Left</code> constructor, <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean isLeft() {
		return false;
	}
	
	/**
	 * Gets a view of this instance as a {@link Left} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when this instance has not been built using the {@link Left} constructor.
	 * @since 1.0
	 */
	public Left<L, R> asLeft()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Left.class);
	}
	
	/**
	 * Extracts the left value wrapped in this {@link Either} instance.
	 * 
	 * @return The wrapped left value, or nothing when the instance has not been built using the {@link Left} constructor.
	 * @since 1.0
	 */
	public Maybe<L> getLeft() {
		return Maybe.none();
	}
	
	// Right.
	
	/**
	 * The {@link Either.Right} class represents instances of {@link Either} that encode the second alternative (right alternative).
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @since 1.0
	 */
	public static final class Right<L, R>
	extends Either<L, R> {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Instantiates a new {@link Either} instance wrapping the given right value.
		 * 
		 * @param value Right value to wrap.
		 * @since 1.0
		 */
		public Right(final R value) {
			_value = value;
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
		
		@Override
		public Maybe<R> getRight() {
			return Maybe.some(_value);
		}
		
		// Value.
		
		/** Wrapped right value. */
		private final R _value;
		
		/**
		 * Gets the right value wrapped in this instance.
		 * 
		 * @return The wrapped right value.
		 * @since 1.0
		 */
		public R getValue() {
			return _value;
		}
		
		// Matching.
		
		@Override
		public <RT> RT match(final Matcher<? super L, ? super R, ? extends RT> matcher) {
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
			return Either.<L, RR>right(function.evaluate(_value));
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
				final Right<?, ?> either = (Right<?, ?>) object;
				return ObjectUtils.safeEquals(_value, either._value);
			} else {
				return false;
			}
		}
		
		@Override
		public void appendDescription(final DescriptionBuilder description) {
			description.append("Right", _value);
		}
	}
	
	/**
	 * Tests whether this instance has been built using the {@link Right} constructor.
	 * 
	 * @return <code>true</code> when the instance has been built using the {@link Right} constructor, <code>false</code> otherwise.
	 * @since 1.0
	 */
	public boolean isRight() {
		return false;
	}
	
	/**
	 * Gets a view of this instance as a {@link Right} instance.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException when this instance has not been built using the {@link Right} constructor.
	 * @since 1.0
	 */
	public Right<L, R> asRight()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Right.class);
	}
	
	/**
	 * Extracts the right value wrapped in this {@link Either} instance.
	 * 
	 * @return The wrapped right value, or nothing when the instance has not been built using the {@link Right} constructor.
	 * @since 1.0
	 */
	public Maybe<R> getRight() {
		return Maybe.none();
	}
	
	// Matching.
	
	/**
	 * The {@link Either.Matcher} interface defines matching functions of {@link Either} instances.
	 * 
	 * @param <L> Type of the left value.
	 * @param <R> Type of the right value.
	 * @param <RT> Type of the result.
	 * @see Either#match(Matcher)
	 * @since 1.0
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
	 * Matches this {@link Either} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <RT> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 * @since 1.0
	 */
	public abstract <RT> RT match(final Matcher<? super L, ? super R, ? extends RT> matcher);
	
	// Functional.
	
	/**
	 * Maps the left value wrapped by this {@link Either} instance using the given function.
	 * 
	 * @param <RL> Type of the mapped left value.
	 * @param function Mapping function to use.
	 * @return The {@link Either} instance wrapping the mapped left value.
	 * @since 1.0
	 */
	public abstract <RL> Either<RL, R> mapLeft(final Function<? super L, ? extends RL> function);
	
	/**
	 * Maps the right value wrapped by this {@link Either} instance using the given function.
	 * 
	 * @param <RR> Type of the mapped right value.
	 * @param function Mapping function to use.
	 * @return The {@link Either} instance wrapping the mapped right value.
	 * @since 1.0
	 */
	public abstract <RR> Either<L, RR> mapRight(final Function<? super R, ? extends RR> function);
	
	// Object.
	
	@Override
	public final String toString() {
		return TextUtils.description(this);
	}
}
