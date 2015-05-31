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

/**
 * The {@link Variant3} class implements a generic tagged union data type that supports 3 cases.
 * 
 * @param <T1> Type of the value wrapped in the first case.
 * @param <T2> Type of the value wrapped in the second case.
 * @param <T3> Type of the value wrapped in the third case.
 * @since 1.0
 */
public abstract class Variant3<T1, T2, T3>
implements Tag1<Variant3.Case1<T1, T2, T3>>, Tag2<Variant3.Case2<T1, T2, T3>>, Tag3<Variant3.Case3<T1, T2, T3>> {
	private Variant3() {
		// Prevents external subclassing.
	}
	
	/**
	 * Builds an instance of the first case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case1
	 * @since 1.0
	 */
	public static <T1, T2, T3> Variant3<T1, T2, T3> case1(final T1 value) {
		return new Case1<>(value);
	}
	
	/**
	 * Builds an instance of the second case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case2
	 * @since 1.0
	 */
	public static <T1, T2, T3> Variant3<T1, T2, T3> case2(final T2 value) {
		return new Case2<>(value);
	}
	
	/**
	 * Builds an instance of the third case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case3
	 * @since 1.0
	 */
	public static <T1, T2, T3> Variant3<T1, T2, T3> case3(final T3 value) {
		return new Case3<>(value);
	}
	
	/**
	 * The {@link Variant3.BaseCase} class provides a skeleton implementation of {@link Variant3}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T> Type of the wrapped value.
	 * @since 1.0
	 */
	private static abstract class BaseCase<T1, T2, T3, T>
	extends Variant3<T1, T2, T3>
	implements Field<T> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 1.0
		 */
		public BaseCase(final T value) {
			_value = value;
		}
		
		// Value.
		
		/**
		 * Wrapped value.
		 * 
		 * @since 1.0
		 */
		protected final T _value;
		
		/**
		 * Gets the wrapped value.
		 * 
		 * @return The wrapped value.
		 * @since 1.0
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
				final BaseCase<?, ?, ?, ?> case_ = (BaseCase<?, ?, ?, ?>) object;
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
	 * The {@link Variant3.Case1} class implements the first case of {@link Variant3}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @since 1.0
	 */
	public static final class Case1<T1, T2, T3>
	extends BaseCase<T1, T2, T3, T1> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 1.0
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
		public Case1<T1, T2, T3> as1() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? extends R> matcher) {
			return matcher.case1(this);
		}
		
		// Functional.
		
		@Override
		public <R1> Case1<R1, T2, T3> map1(final Function<? super T1, ? extends R1> function) {
			return new Case1<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case1<T1, R2, T3> map2(final Function<? super T2, ? extends R2> function) {
			return (Case1<T1, R2, T3>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R3> Case1<T1, T2, R3> map3(final Function<? super T3, ? extends R3> function) {
			return (Case1<T1, T2, R3>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the first case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the first case, <code>false</code> otherwise.
	 * @since 1.0
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
	 * @since 1.0
	 */
	@Override
	public Case1<T1, T2, T3> as1()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case1.class);
	}
	
	// Case 2.
	
	/**
	 * The {@link Variant3.Case2} class implements the second case of {@link Variant3}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @since 1.0
	 */
	public static final class Case2<T1, T2, T3>
	extends BaseCase<T1, T2, T3, T2> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 1.0
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
		public Case2<T1, T2, T3> as2() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? extends R> matcher) {
			return matcher.case2(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case2<R1, T2, T3> map1(final Function<? super T1, ? extends R1> function) {
			return (Case2<R1, T2, T3>) this;
		}
		
		@Override
		public <R2> Case2<T1, R2, T3> map2(final Function<? super T2, ? extends R2> function) {
			return new Case2<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R3> Case2<T1, T2, R3> map3(final Function<? super T3, ? extends R3> function) {
			return (Case2<T1, T2, R3>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the second case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the second case, <code>false</code> otherwise.
	 * @since 1.0
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
	 * @since 1.0
	 */
	@Override
	public Case2<T1, T2, T3> as2()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Case 3.
	
	/**
	 * The {@link Variant3.Case3} class implements the third case of {@link Variant3}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @since 1.0
	 */
	public static final class Case3<T1, T2, T3>
	extends BaseCase<T1, T2, T3, T3> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 1.0
		 */
		public Case3(final T3 value) {
			super(value);
		}
		
		// Case 3.
		
		@Override
		public boolean is3() {
			return true;
		}
		
		@Override
		public Case3<T1, T2, T3> as3() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? extends R> matcher) {
			return matcher.case3(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case3<R1, T2, T3> map1(final Function<? super T1, ? extends R1> function) {
			return (Case3<R1, T2, T3>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case3<T1, R2, T3> map2(final Function<? super T2, ? extends R2> function) {
			return (Case3<T1, R2, T3>) this;
		}
		
		@Override
		public <R3> Case3<T1, T2, R3> map3(final Function<? super T3, ? extends R3> function) {
			return new Case3<>(function.evaluate(_value));
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the third case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the third case, <code>false</code> otherwise.
	 * @since 1.0
	 */
	@Override
	public boolean is3() {
		return false;
	}
	
	/**
	 * Gets a view of this variant as an instance of the third case.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the variant is not an instance of the third case.
	 * @since 1.0
	 */
	@Override
	public Case3<T1, T2, T3> as3()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Matching.
	
	/**
	 * The {@link Variant3.Matcher} interface defines matching functions of {@link Variant3} instances.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <R> Type of the result.
	 * @see Variant3#match(Matcher)
	 * @since 1.0
	 */
	public interface Matcher<T1, T2, T3, R> {
		/**
		 * Matches the given first case instance of {@link Variant3}.
		 * 
		 * @param case1 First case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 1.0
		 */
		R case1(Case1<? extends T1, ? extends T2, ? extends T3> case1);
		
		/**
		 * Matches the given second case instance of {@link Variant3}.
		 * 
		 * @param case2 Second case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 1.0
		 */
		R case2(Case2<? extends T1, ? extends T2, ? extends T3> case2);
		
		/**
		 * Matches the given third case instance of {@link Variant3}.
		 * 
		 * @param case3 Third case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 1.0
		 */
		R case3(Case3<? extends T1, ? extends T2, ? extends T3> case3);
	}
	
	/**
	 * Matches this {@link Variant3} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <R> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 * @since 1.0
	 */
	public abstract <R> R match(Matcher<? super T1, ? super T2, ? super T3, ? extends R> matcher);
	
	// Functional.
	
	/**
	 * Maps the first case of this {@link Variant3} instance using the given function.
	 * 
	 * @param <R1> Type of the mapped value wrapped in the first case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant3} instance.
	 * @since 1.0
	 */
	public abstract <R1> Variant3<R1, T2, T3> map1(Function<? super T1, ? extends R1> function);
	
	/**
	 * Maps the second case of this {@link Variant3} instance using the given function.
	 * 
	 * @param <R2> Type of the mapped value wrapped in the second case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant3} instance.
	 * @since 1.0
	 */
	public abstract <R2> Variant3<T1, R2, T3> map2(Function<? super T2, ? extends R2> function);
	
	/**
	 * Maps the third case of this {@link Variant3} instance using the given function.
	 * 
	 * @param <R3> Type of the mapped value wrapped in the third case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant3} instance.
	 * @since 1.0
	 */
	public abstract <R3> Variant3<T1, T2, R3> map3(Function<? super T3, ? extends R3> function);
}
