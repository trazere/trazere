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
package com.trazere.core.functional;

import com.trazere.core.text.DescriptionBuilder;
import com.trazere.core.util.Either;
import com.trazere.core.util.Maybe;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The {@link Thunk} interface defines abstract computations of some value.
 * <p>
 * Thunks represent no-arguments functions whose evaluation solely relies on the captured context. They provide an abstraction for lazy evaluation.
 * 
 * @param <T> Type of the values.
 * @since 2.0
 */
@FunctionalInterface
public interface Thunk<T> {
	/**
	 * Evaluates this thunk.
	 * 
	 * @return The value of the thunk.
	 * @since 2.0
	 */
	T evaluate();
	
	/**
	 * Evaluates this thunk in a thread safe way.
	 * 
	 * @return The value of the thunk.
	 * @see #evaluate()
	 * @since 2.0
	 */
	default T synchronizedEvaluate() {
		synchronized (this) {
			return evaluate();
		}
	}
	
	/**
	 * Transforms the value of this thunk using the given function.
	 * 
	 * @param <R> Type of the transformed value.
	 * @param function Function to use to transform the value.
	 * @return A thunk of the transformed value.
	 * @since 2.0
	 */
	default <R> Thunk<R> map(final Function<? super T, ? extends R> function) {
		assert null != function;
		
		final Thunk<T> self = this;
		return () -> function.evaluate(self.evaluate());
	}
	
	/**
	 * Builds a memoized view of this thunk.
	 * 
	 * @return A memoized view of this thunk, or this thunk when it is already memoized.
	 * @see MemoizedThunk
	 * @since 2.0
	 */
	default MemoizedThunk<T> memoized() {
		if (this instanceof MemoizedThunk) {
			return (MemoizedThunk<T>) this;
		} else {
			final Thunk<T> self = this;
			return new MemoizedThunk<T>() {
				/** Unevaluated thunk or memoized value. */
				protected Either<Thunk<? extends T>, T> _value = Either.<Thunk<? extends T>, T>left(self);
				
				@Override
				public T evaluate() {
					if (_value.isRight()) {
						return _value.asRight().getValue();
					} else {
						final T value = _value.asLeft().getValue().evaluate();
						_value = Either.right(value);
						return value;
					}
				}
				
				@Override
				public boolean isMemoized() {
					return _value.isRight();
				}
				
				@Override
				public Maybe<T> probe() {
					if (_value.isRight()) {
						return Maybe.some(_value.asRight().getValue());
					} else {
						return Maybe.<T>none();
					}
				}
				
				// Object.
				
				@Override
				public String toString() {
					if (_value.isLeft()) {
						return "=> " + _value.asLeft().getLeft();
					} else {
						return String.valueOf(_value.asRight().getRight());
					}
				}
			};
		}
	}
	
	/**
	 * Builds a memoized, resettable view of this thunk.
	 * 
	 * @return A resettable view of this thunk, or this thunk when it is resettable.
	 * @see ResettableThunk
	 * @since 2.0
	 */
	default ResettableThunk<T> resettable() {
		if (this instanceof ResettableThunk) {
			return (ResettableThunk<T>) this;
		} else {
			final Thunk<T> self = this;
			return new BaseResettableThunk<T>() {
				@Override
				protected T compute() {
					return self.evaluate();
				}
				
				@Override
				public void appendDescription(final DescriptionBuilder description) {
					super.appendDescription(description);
					description.append("Thunk", self);
				}
			};
		}
	}
	
	/**
	 * Builds a synchronized view of this thunk.
	 * 
	 * @return A synchronized view of this thunk.
	 * @see #synchronizedEvaluate()
	 * @since 2.0
	 */
	default Thunk<T> synchronized_() {
		return this::synchronizedEvaluate;
	}
	
	/**
	 * Lifts this thunk as a Java 8 callable.
	 * 
	 * @return The built Java 8 callable.
	 * @since 2.0
	 */
	default Callable<T> toCallable() {
		return this::evaluate;
	}
	
	/**
	 * Lifts this thunk as a Java 8 supplier.
	 * 
	 * @return The built Java 8 supplier.
	 * @since 2.0
	 */
	default Supplier<T> toSupplier() {
		return this::evaluate;
	}
}
