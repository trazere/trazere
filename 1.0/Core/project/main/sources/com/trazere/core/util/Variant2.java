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
import com.trazere.core.lang.ObjectUtils;

/**
 * The {@link Variant2} class implements a generic tagged union data type that supports 2 cases.
 * 
 * @param <T1> Type of the value wrapped in the first case.
 * @param <T2> Type of the value wrapped in the second case.
 */
public abstract class Variant2<T1, T2>
implements Tag1<Variant2.Case1<T1, T2>>, Tag2<Variant2.Case2<T1, T2>> {
	private Variant2() {
		// Prevents external subclassing.
	}
	
	/**
	 * Builds an instance of the first case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case1
	 */
	public static <T1, T2> Variant2<T1, T2> case1(final T1 value) {
		return new Case1<>(value);
	}
	
	/**
	 * Builds an instance of the second case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case2
	 */
	public static <T1, T2> Variant2<T1, T2> case2(final T2 value) {
		return new Case2<>(value);
	}
	
	/**
	 * The {@link Variant2.BaseCase} class provides a skeleton implementation of {@link Variant2}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T> Type of the wrapped value.
	 */
	private static abstract class BaseCase<T1, T2, T>
	extends Variant2<T1, T2>
	implements Field<T> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 */
		public BaseCase(final T value) {
			_value = value;
		}
		
		// Value.
		
		/** Wrapped value. */
		protected final T _value;
		
		/**
		 * Gets the wrapped value.
		 * 
		 * @return The wrapped value.
		 */
		@Override
		public T get() {
			return _value;
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
				final BaseCase<?, ?, ?> case_ = (BaseCase<?, ?, ?>) object;
				return ObjectUtils.safeEquals(_value, case_._value);
			} else {
				return false;
			}
		}
		
		@Override
		public String toString() {
			return String.valueOf(_value);
		}
	}
	
	// Case 1.
	
	/**
	 * The {@link Variant2.Case1} class implements the first case of {@link Variant2}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 */
	public static final class Case1<T1, T2>
	extends BaseCase<T1, T2, T1> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 */
		public Case1(final T1 value) {
			super(value);
		}
		
		// Case 1.
		
		@Override
		public boolean is1() {
			return true;
		}
		
		@Override
		public Case1<T1, T2> as1() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? extends R> matcher) {
			return matcher.case1(this);
		}
		
		// Functional.
		
		@Override
		public <R1> Case1<R1, T2> map1(final Function<? super T1, ? extends R1> function) {
			return new Case1<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case1<T1, R2> map2(final Function<? super T2, ? extends R2> function) {
			return (Case1<T1, R2>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the first case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the first case, <code>false</code> otherwise.
	 */
	@Override
	public boolean is1() {
		return false;
	}
	
	/**
	 * Gets a view of this variant as an instance of the first case.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the variant is not an instance of the first case.
	 */
	@Override
	public Case1<T1, T2> as1()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case1.class);
	}
	
	// Case 2.
	
	/**
	 * The {@link Variant2.Case2} class implements the second case of {@link Variant2}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 */
	public static final class Case2<T1, T2>
	extends BaseCase<T1, T2, T2> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 */
		public Case2(final T2 value) {
			super(value);
		}
		
		// Case 2.
		
		@Override
		public boolean is2() {
			return true;
		}
		
		@Override
		public Case2<T1, T2> as2() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? extends R> matcher) {
			return matcher.case2(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case2<R1, T2> map1(final Function<? super T1, ? extends R1> function) {
			return (Case2<R1, T2>) this;
		}
		
		@Override
		public <R2> Case2<T1, R2> map2(final Function<? super T2, ? extends R2> function) {
			return new Case2<>(function.evaluate(_value));
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the second case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the second case, <code>false</code> otherwise.
	 */
	@Override
	public boolean is2() {
		return false;
	}
	
	/**
	 * Gets a view of this variant as an instance of the second case.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the variant is not an instance of the second case.
	 */
	@Override
	public Case2<T1, T2> as2()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Matching.
	
	/**
	 * The {@link Variant2.Matcher} interface defines matching functions of {@link Variant2} instances.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <R> Type of the result.
	 * @see Variant2#match(Matcher)
	 */
	public interface Matcher<T1, T2, R> {
		/**
		 * Matches the given first case instance of {@link Variant2}.
		 * 
		 * @param case1 First case instance to match.
		 * @return The result of the matching function evaluation.
		 */
		R case1(Case1<? extends T1, ? extends T2> case1);
		
		/**
		 * Matches the given second case instance of {@link Variant2}.
		 * 
		 * @param case2 Second case instance to match.
		 * @return The result of the matching function evaluation.
		 */
		R case2(Case2<? extends T1, ? extends T2> case2);
	}
	
	/**
	 * Matches this {@link Variant2} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <R> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 */
	public abstract <R> R match(Matcher<? super T1, ? super T2, ? extends R> matcher);
	
	// Functional.
	
	/**
	 * Maps the first case of this {@link Variant2} instance using the given function.
	 * 
	 * @param <R1> Type of the mapped value wrapped in the first case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant2} instance.
	 */
	public abstract <R1> Variant2<R1, T2> map1(Function<? super T1, ? extends R1> function);
	
	/**
	 * Maps the second case of this {@link Variant2} instance using the given function.
	 * 
	 * @param <R2> Type of the mapped value wrapped in the second case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant2} instance.
	 */
	public abstract <R2> Variant2<T1, R2> map2(Function<? super T2, ? extends R2> function);
}
