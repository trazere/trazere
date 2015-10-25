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
import com.trazere.core.functional.Functions;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import java.io.Serializable;

/**
 * The {@link Result} class implements a tagged union data type that represents success or failure.
 * <ul>
 * <li>Instances built using the {@link Success} constructor encode successes.
 * <li>Instances built using the {@link Failure} constrcutor encode failures.
 * 
 * @param <T> Type of the success value.
 * @since 2.0
 */
public abstract class Result<T>
implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builds a success.
	 * 
	 * @param <T> Type of the success value.
	 * @param value Success value.
	 * @return The built instance.
	 * @since 2.0
	 */
	public static <T> Result<T> success(final T value) {
		return new Success<>(value);
	}
	
	/**
	 * Builds a failure.
	 * 
	 * @param <T> Type of the sucess value.
	 * @param cause Cause of the failure.
	 * @return The built instance.
	 * @since 2.0
	 */
	public static <T> Result<T> failure(final Throwable cause) {
		return new Failure<>(cause);
	}
	
	// Success.
	
	/**
	 * The {@link Result.Success} class represents the instances of {@link Result} that encode the successes.
	 * 
	 * @param <T> Type of the success value.
	 * @since 2.0
	 */
	public static final class Success<T>
	extends Result<T> {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Instantiates a new success.
		 * 
		 * @param value Success value.
		 * @since 2.0
		 */
		public Success(final T value) {
			_value = value;
		}
		
		// Success.
		
		@Override
		public boolean isSuccess() {
			return true;
		}
		
		@Override
		public Success<T> asSuccess() {
			return this;
		}
		
		@Override
		public Maybe<T> getSuccess() {
			return Maybe.some(_value);
		}
		
		// Value.
		
		/** Success value. */
		protected final T _value;
		
		/**
		 * Gets the value of this success.
		 * 
		 * @return The success value.
		 * @since 2.0
		 */
		public T getValue() {
			return _value;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T, ? extends R> matcher) {
			return matcher.success(this);
		}
		
		// Functional.
		
		@Override
		public <R> Result<R> map(final Function<? super T, ? extends R> function) {
			return new Success<>(function.evaluate(_value));
		}
		
		@Override
		public <R> Result<R> flatMap(final Function<? super T, ? extends Result<? extends R>> function) {
			return function.evaluate(_value).map(Functions.<R>identity()); // HACK: explicit type arguments to work around a bug of javac
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
				final Success<?> success = (Success<?>) object;
				return ObjectUtils.safeEquals(_value, success._value);
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return String.valueOf(_value);
		}
	}
	
	/**
	 * Indicates whether this {@link Result} instance is a success.
	 * 
	 * @return <code>true</code> when the instance is a success, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public boolean isSuccess() {
		return false;
	}
	
	/**
	 * Gets a view of this {@link Result} instance as a success.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the instance is not a success.
	 * @since 2.0
	 */
	public Success<T> asSuccess()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Success.class);
	}
	
	/**
	 * Extracts the success value of this {@link Result} instance.
	 * 
	 * @return The success value, or nothing when the instance is not a success.
	 * @since 2.0
	 */
	public Maybe<T> getSuccess() {
		return Maybe.none();
	}
	
	// Failure.
	
	/**
	 * The {@link Result.Failure} class represents the instances of {@link Result} that encode the failures.
	 * 
	 * @param <T> Type of the success value.
	 * @since 2.0
	 */
	public static final class Failure<T>
	extends Result<T> {
		private static final long serialVersionUID = 1L;
		
		/**
		 * Instantiates a new failure.
		 * 
		 * @param cause Cause of the failure.
		 * @since 2.0
		 */
		public Failure(final Throwable cause) {
			assert null != cause;
			
			// Initialization.
			_cause = cause;
		}
		
		// Failure.
		
		@Override
		public boolean isFailure() {
			return true;
		}
		
		@Override
		public Failure<T> asFailure() {
			return this;
		}
		
		@Override
		public Maybe<Throwable> getFailure() {
			return Maybe.some(_cause);
		}
		
		// Cause.
		
		/** Cause of the failure. */
		protected final Throwable _cause;
		
		/**
		 * Gets the cause of this failure.
		 * 
		 * @return The cause.
		 * @since 2.0
		 */
		public Throwable getCause() {
			return _cause;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T, ? extends R> matcher) {
			return matcher.failure(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R> Result<R> map(final Function<? super T, ? extends R> function) {
			return (Result<R>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R> Result<R> flatMap(final Function<? super T, ? extends Result<? extends R>> function) {
			return (Result<R>) this;
		}
		
		// Object.
		
		@Override
		public int hashCode() {
			final HashCode result = new HashCode(this);
			result.append(_cause);
			return result.get();
		}
		
		@Override
		public boolean equals(final Object object) {
			if (this == object) {
				return true;
			} else if (null != object && getClass().equals(object.getClass())) {
				final Failure<?> failure = (Failure<?>) object;
				return _cause.equals(failure._cause);
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return _cause.toString();
		}
	}
	
	/**
	 * Indicates whether this {@link Result} instance is a failure.
	 * 
	 * @return <code>true</code> when the instance is a failure, <code>false</code> otherwise.
	 * @since 2.0
	 */
	public boolean isFailure() {
		return false;
	}
	
	/**
	 * Gets a view of this {@link Result} instance as a failure.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the instance is not a failure.
	 * @since 2.0
	 */
	public Failure<T> asFailure()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Failure.class);
	}
	
	/**
	 * Gets the cause of this {@link Result} instance.
	 * 
	 * @return The cause of the failure, or nothing when the instance is not a failure.
	 * @since 2.0
	 */
	public Maybe<Throwable> getFailure() {
		return Maybe.none();
	}
	
	// Matching.
	
	/**
	 * The {@link Result.Matcher} interface defines matching functions of {@link Result} instances.
	 * 
	 * @param <T> Type of the success value.
	 * @param <R> Type of the result.
	 * @since 2.0
	 */
	public interface Matcher<T, R> {
		/**
		 * Matches the given success.
		 * 
		 * @param success Success to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R success(Success<? extends T> success);
		
		/**
		 * Matches the given failure.
		 * 
		 * @param failure Failure to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R failure(Failure<? extends T> failure);
	}
	
	/**
	 * Matches this {@link Result} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <R> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 * @since 2.0
	 */
	public abstract <R> R match(Matcher<? super T, ? extends R> matcher);
	
	// Functional.
	
	/**
	 * Maps the success value of this {@link Result} instance using the given function.
	 * 
	 * @param <R> Type of the mapped success value.
	 * @param function Function to use to transform the success value.
	 * @return The {@link Result} instance containing the mapped success value.
	 * @since 2.0
	 */
	public abstract <R> Result<R> map(final Function<? super T, ? extends R> function);
	
	/**
	 * Transforms and flattens the success value of this {@link Result} instance using the given function.
	 * 
	 * @param <R> Type of the extracted success value.
	 * @param function Function to use to transform the success value.
	 * @return The {@link Result} instance containing the extracted success value.
	 * @since 2.0
	 */
	public abstract <R> Result<R> flatMap(final Function<? super T, ? extends Result<? extends R>> function);
}
