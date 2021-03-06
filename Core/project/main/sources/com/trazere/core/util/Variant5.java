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
 * The {@link Variant5} class implements a generic tagged union data type that supports 4 cases.
 * 
 * @param <T1> Type of the value wrapped in the first case.
 * @param <T2> Type of the value wrapped in the second case.
 * @param <T3> Type of the value wrapped in the third case.
 * @param <T4> Type of the value wrapped in the fourth case.
 * @param <T5> Type of the value wrapped in the fifth case.
 * @since 2.0
 */
public abstract class Variant5<T1, T2, T3, T4, T5>
implements Tag1<Variant5.Case1<T1, T2, T3, T4, T5>>, Tag2<Variant5.Case2<T1, T2, T3, T4, T5>>, Tag3<Variant5.Case3<T1, T2, T3, T4, T5>>, Tag4<Variant5.Case4<T1, T2, T3, T4, T5>>, Tag5<Variant5.Case5<T1, T2, T3, T4, T5>> {
	private Variant5() {
		// Prevents external subclassing.
	}
	
	/**
	 * Builds an instance of the first case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case1
	 * @since 2.0
	 */
	public static <T1, T2, T3, T4, T5> Variant5<T1, T2, T3, T4, T5> case1(final T1 value) {
		return new Case1<>(value);
	}
	
	/**
	 * Builds an instance of the second case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case2
	 * @since 2.0
	 */
	public static <T1, T2, T3, T4, T5> Variant5<T1, T2, T3, T4, T5> case2(final T2 value) {
		return new Case2<>(value);
	}
	
	/**
	 * Builds an instance of the third case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case3
	 * @since 2.0
	 */
	public static <T1, T2, T3, T4, T5> Variant5<T1, T2, T3, T4, T5> case3(final T3 value) {
		return new Case3<>(value);
	}
	
	/**
	 * Builds an instance of the fourth case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case4
	 * @since 2.0
	 */
	public static <T1, T2, T3, T4, T5> Variant5<T1, T2, T3, T4, T5> case4(final T4 value) {
		return new Case4<>(value);
	}
	
	/**
	 * Builds an instance of the fourth case of the variant.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param value Value to wrap.
	 * @return The built instance.
	 * @see Case5
	 * @since 2.0
	 */
	public static <T1, T2, T3, T4, T5> Variant5<T1, T2, T3, T4, T5> case5(final T5 value) {
		return new Case5<>(value);
	}
	
	/**
	 * The {@link Variant5.BaseCase} class provides a skeleton implementation of {@link Variant5}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param <T> Type of the wrapped value.
	 * @since 2.0
	 */
	private static abstract class BaseCase<T1, T2, T3, T4, T5, T>
	extends Variant5<T1, T2, T3, T4, T5>
	implements Field<T> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 2.0
		 */
		public BaseCase(final T value) {
			_value = value;
		}
		
		// Value.
		
		/**
		 * Wrapped value.
		 * 
		 * @since 2.0
		 */
		protected final T _value;
		
		/**
		 * Gets the wrapped value.
		 * 
		 * @return The wrapped value.
		 * @since 2.0
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
				final BaseCase<?, ?, ?, ?, ?, ?> case_ = (BaseCase<?, ?, ?, ?, ?, ?>) object;
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
	 * The {@link Variant5.Case1} class implements the first case of {@link Variant5}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @since 2.0
	 */
	public static final class Case1<T1, T2, T3, T4, T5>
	extends BaseCase<T1, T2, T3, T4, T5, T1> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 2.0
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
		public Case1<T1, T2, T3, T4, T5> as1() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> matcher) {
			return matcher.case1(this);
		}
		
		// Functional.
		
		@Override
		public <R1> Case1<R1, T2, T3, T4, T5> map1(final Function<? super T1, ? extends R1> function) {
			return new Case1<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case1<T1, R2, T3, T4, T5> map2(final Function<? super T2, ? extends R2> function) {
			return (Case1<T1, R2, T3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R3> Case1<T1, T2, R3, T4, T5> map3(final Function<? super T3, ? extends R3> function) {
			return (Case1<T1, T2, R3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R4> Case1<T1, T2, T3, R4, T5> map4(final Function<? super T4, ? extends R4> function) {
			return (Case1<T1, T2, T3, R4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R5> Case1<T1, T2, T3, T4, R5> map5(final Function<? super T5, ? extends R5> function) {
			return (Case1<T1, T2, T3, T4, R5>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the first case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the first case, <code>false</code> otherwise.
	 * @since 2.0
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
	 * @since 2.0
	 */
	@Override
	public Case1<T1, T2, T3, T4, T5> as1()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case1.class);
	}
	
	// Case 2.
	
	/**
	 * The {@link Variant5.Case2} class implements the second case of {@link Variant5}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @since 2.0
	 */
	public static final class Case2<T1, T2, T3, T4, T5>
	extends BaseCase<T1, T2, T3, T4, T5, T2> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 2.0
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
		public Case2<T1, T2, T3, T4, T5> as2() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> matcher) {
			return matcher.case2(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case2<R1, T2, T3, T4, T5> map1(final Function<? super T1, ? extends R1> function) {
			return (Case2<R1, T2, T3, T4, T5>) this;
		}
		
		@Override
		public <R2> Case2<T1, R2, T3, T4, T5> map2(final Function<? super T2, ? extends R2> function) {
			return new Case2<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R3> Case2<T1, T2, R3, T4, T5> map3(final Function<? super T3, ? extends R3> function) {
			return (Case2<T1, T2, R3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R4> Case2<T1, T2, T3, R4, T5> map4(final Function<? super T4, ? extends R4> function) {
			return (Case2<T1, T2, T3, R4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R5> Case2<T1, T2, T3, T4, R5> map5(final Function<? super T5, ? extends R5> function) {
			return (Case2<T1, T2, T3, T4, R5>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the second case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the second case, <code>false</code> otherwise.
	 * @since 2.0
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
	 * @since 2.0
	 */
	@Override
	public Case2<T1, T2, T3, T4, T5> as2()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Case 3.
	
	/**
	 * The {@link Variant5.Case3} class implements the third case of {@link Variant5}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @since 2.0
	 */
	public static final class Case3<T1, T2, T3, T4, T5>
	extends BaseCase<T1, T2, T3, T4, T5, T3> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 2.0
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
		public Case3<T1, T2, T3, T4, T5> as3() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> matcher) {
			return matcher.case3(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case3<R1, T2, T3, T4, T5> map1(final Function<? super T1, ? extends R1> function) {
			return (Case3<R1, T2, T3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case3<T1, R2, T3, T4, T5> map2(final Function<? super T2, ? extends R2> function) {
			return (Case3<T1, R2, T3, T4, T5>) this;
		}
		
		@Override
		public <R3> Case3<T1, T2, R3, T4, T5> map3(final Function<? super T3, ? extends R3> function) {
			return new Case3<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R4> Case3<T1, T2, T3, R4, T5> map4(final Function<? super T4, ? extends R4> function) {
			return (Case3<T1, T2, T3, R4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R5> Case3<T1, T2, T3, T4, R5> map5(final Function<? super T5, ? extends R5> function) {
			return (Case3<T1, T2, T3, T4, R5>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the third case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the third case, <code>false</code> otherwise.
	 * @since 2.0
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
	 * @since 2.0
	 */
	@Override
	public Case3<T1, T2, T3, T4, T5> as3()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Case 4.
	
	/**
	 * The {@link Variant5.Case4} class implements the fourth case of {@link Variant5}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @since 2.0
	 */
	public static final class Case4<T1, T2, T3, T4, T5>
	extends BaseCase<T1, T2, T3, T4, T5, T4> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 2.0
		 */
		public Case4(final T4 value) {
			super(value);
		}
		
		// Case 4.
		
		@Override
		public boolean is4() {
			return true;
		}
		
		@Override
		public Case4<T1, T2, T3, T4, T5> as4() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> matcher) {
			return matcher.case4(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case4<R1, T2, T3, T4, T5> map1(final Function<? super T1, ? extends R1> function) {
			return (Case4<R1, T2, T3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case4<T1, R2, T3, T4, T5> map2(final Function<? super T2, ? extends R2> function) {
			return (Case4<T1, R2, T3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R3> Case4<T1, T2, R3, T4, T5> map3(final Function<? super T3, ? extends R3> function) {
			return (Case4<T1, T2, R3, T4, T5>) this;
		}
		
		@Override
		public <R4> Case4<T1, T2, T3, R4, T5> map4(final Function<? super T4, ? extends R4> function) {
			return new Case4<>(function.evaluate(_value));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R5> Case4<T1, T2, T3, T4, R5> map5(final Function<? super T5, ? extends R5> function) {
			return (Case4<T1, T2, T3, T4, R5>) this;
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the fourth case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the fourth case, <code>false</code> otherwise.
	 * @since 2.0
	 */
	@Override
	public boolean is4() {
		return false;
	}
	
	/**
	 * Gets a view of this variant as an instance of the fourth case.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the variant is not an instance of the fourth case.
	 * @since 2.0
	 */
	@Override
	public Case4<T1, T2, T3, T4, T5> as4()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Case 5.
	
	/**
	 * The {@link Variant5.Case5} class implements the fifth case of {@link Variant5}.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @since 2.0
	 */
	public static final class Case5<T1, T2, T3, T4, T5>
	extends BaseCase<T1, T2, T3, T4, T5, T5> {
		/**
		 * Instantiates a new variant.
		 * 
		 * @param value Value to wrap.
		 * @since 2.0
		 */
		public Case5(final T5 value) {
			super(value);
		}
		
		// Case 5.
		
		@Override
		public boolean is5() {
			return true;
		}
		
		@Override
		public Case5<T1, T2, T3, T4, T5> as5() {
			return this;
		}
		
		// Matching.
		
		@Override
		public <R> R match(final Matcher<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> matcher) {
			return matcher.case5(this);
		}
		
		// Functional.
		
		@Override
		@SuppressWarnings("unchecked")
		public <R1> Case5<R1, T2, T3, T4, T5> map1(final Function<? super T1, ? extends R1> function) {
			return (Case5<R1, T2, T3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R2> Case5<T1, R2, T3, T4, T5> map2(final Function<? super T2, ? extends R2> function) {
			return (Case5<T1, R2, T3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R3> Case5<T1, T2, R3, T4, T5> map3(final Function<? super T3, ? extends R3> function) {
			return (Case5<T1, T2, R3, T4, T5>) this;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <R4> Case5<T1, T2, T3, R4, T5> map4(final Function<? super T4, ? extends R4> function) {
			return (Case5<T1, T2, T3, R4, T5>) this;
		}
		
		@Override
		public <R5> Case5<T1, T2, T3, T4, R5> map5(final Function<? super T5, ? extends R5> function) {
			return new Case5<>(function.evaluate(_value));
		}
	}
	
	/**
	 * Indicates whether this variant is an instance of the fifth case.
	 * 
	 * @return <code>true</code> when the variant is an instance of the fifth case, <code>false</code> otherwise.
	 * @since 2.0
	 */
	@Override
	public boolean is5() {
		return false;
	}
	
	/**
	 * Gets a view of this variant as an instance of the fifth case.
	 * 
	 * @return The view.
	 * @throws InvalidConstructorException When the variant is not an instance of the fifth case.
	 * @since 2.0
	 */
	@Override
	public Case5<T1, T2, T3, T4, T5> as5()
	throws InvalidConstructorException {
		throw InvalidConstructorException.build(this, Case2.class);
	}
	
	// Matching.
	
	/**
	 * The {@link Variant5.Matcher} interface defines matching functions of {@link Variant5} instances.
	 * 
	 * @param <T1> Type of the value wrapped in the first case.
	 * @param <T2> Type of the value wrapped in the second case.
	 * @param <T3> Type of the value wrapped in the third case.
	 * @param <T4> Type of the value wrapped in the fourth case.
	 * @param <T5> Type of the value wrapped in the fifth case.
	 * @param <R> Type of the result.
	 * @see Variant5#match(Matcher)
	 * @since 2.0
	 */
	public interface Matcher<T1, T2, T3, T4, T5, R> {
		/**
		 * Matches the given first case instance of {@link Variant5}.
		 * 
		 * @param case1 First case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R case1(Case1<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> case1);
		
		/**
		 * Matches the given second case instance of {@link Variant5}.
		 * 
		 * @param case2 Second case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R case2(Case2<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> case2);
		
		/**
		 * Matches the given third case instance of {@link Variant5}.
		 * 
		 * @param case3 Third case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R case3(Case3<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> case3);
		
		/**
		 * Matches the given fourth case instance of {@link Variant5}.
		 * 
		 * @param case4 Fourth case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R case4(Case4<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> case4);
		
		/**
		 * Matches the given fifth case instance of {@link Variant5}.
		 * 
		 * @param case5 Fifth case instance to match.
		 * @return The result of the matching function evaluation.
		 * @since 2.0
		 */
		R case5(Case5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5> case5);
	}
	
	/**
	 * Matches this {@link Variant5} instance using the given matching function.
	 * <p>
	 * This method implements some kind of basic pattern matching.
	 * 
	 * @param <R> Type of the result.
	 * @param matcher Matching function to apply.
	 * @return The result of the matching function evaluation.
	 * @since 2.0
	 */
	public abstract <R> R match(Matcher<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> matcher);
	
	// Functional.
	
	/**
	 * Maps the first case of this {@link Variant5} instance using the given function.
	 * 
	 * @param <R1> Type of the mapped value wrapped in the first case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant5} instance.
	 * @since 2.0
	 */
	public abstract <R1> Variant5<R1, T2, T3, T4, T5> map1(Function<? super T1, ? extends R1> function);
	
	/**
	 * Maps the second case of this {@link Variant5} instance using the given function.
	 * 
	 * @param <R2> Type of the mapped value wrapped in the second case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant5} instance.
	 * @since 2.0
	 */
	public abstract <R2> Variant5<T1, R2, T3, T4, T5> map2(Function<? super T2, ? extends R2> function);
	
	/**
	 * Maps the third case of this {@link Variant5} instance using the given function.
	 * 
	 * @param <R3> Type of the mapped value wrapped in the third case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant5} instance.
	 * @since 2.0
	 */
	public abstract <R3> Variant5<T1, T2, R3, T4, T5> map3(Function<? super T3, ? extends R3> function);
	
	/**
	 * Maps the fourth case of this {@link Variant5} instance using the given function.
	 * 
	 * @param <R4> Type of the mapped value wrapped in the fourth case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant5} instance.
	 * @since 2.0
	 */
	public abstract <R4> Variant5<T1, T2, T3, R4, T5> map4(Function<? super T4, ? extends R4> function);
	
	/**
	 * Maps the fifth case of this {@link Variant5} instance using the given function.
	 * 
	 * @param <R5> Type of the mapped value wrapped in the fifth case.
	 * @param function Function to use to map the wrapped value.
	 * @return The mapped {@link Variant5} instance.
	 * @since 2.0
	 */
	public abstract <R5> Variant5<T1, T2, T3, T4, R5> map5(Function<? super T5, ? extends R5> function);
}
