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
package com.trazere.util.lang;

import com.trazere.util.function.Function1;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@link LangFunctions} class provides various factories of functions related to the language.
 * 
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class LangFunctions {
	// Objects.
	
	/**
	 * Builds a function which matches objects according to the given type.
	 * 
	 * @param <L> Type of the arguments.
	 * @param <T> Type of the match.
	 * @param <X> Type of the exceptions.
	 * @param type The type.
	 * @param throwableFactory The throwable factory to use.
	 * @return The built extractor.
	 * @see LangUtils#match(Object, Class, ThrowableFactory)
	 * @deprecated Use {@link com.trazere.core.lang.ObjectFunctions#match(Class, com.trazere.core.lang.ThrowableFactory)}.
	 */
	@Deprecated
	public static <L, T extends L, X extends Exception> Function1<L, T, X> match(final Class<T> type, final ThrowableFactory<? extends X> throwableFactory) {
		assert null != type;
		assert null != throwableFactory;
		
		return new Function1<L, T, X>() {
			@Override
			public T evaluate(final L value)
			throws X {
				return LangUtils.match(value, type, throwableFactory);
			}
		};
	}
	
	/**
	 * Builds a function returning the Java class of the values.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.lang.ObjectFunctions#getClass_()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<Object, Class<?>, X> getClass_() {
		return (Function1<Object, Class<?>, X>) GET_CLASS;
	}
	
	private static final Function1<Object, Class<?>, ?> GET_CLASS = new Function1<Object, Class<?>, RuntimeException>() {
		@Override
		public Class<?> evaluate(final Object value) {
			return value.getClass();
		}
	};
	
	/**
	 * Builds a function computes the hash code of the values.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.lang.ObjectFunctions#hashCode_()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, Integer, X> hashCode_() {
		return (Function1<T, Integer, X>) HASH_CODE;
	}
	
	private static final Function1<?, Integer, ?> HASH_CODE = new Function1<Object, Integer, RuntimeException>() {
		@Override
		public Integer evaluate(final Object value) {
			return value.hashCode();
		}
	};
	
	/**
	 * Builds a function that computes the string representation of the values.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.lang.ObjectFunctions#toString_()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, String, X> toString_() {
		return (Function1<T, String, X>) TO_STRING;
	}
	
	private static final Function1<?, String, ?> TO_STRING = new Function1<Object, String, RuntimeException>() {
		@Override
		public String evaluate(final Object value) {
			return value.toString();
		}
	};
	
	// Booleans.
	
	@Deprecated
	/**
	 * Builds a functions which negates boolean values.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#not()}.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<Boolean, Boolean, X> not() {
		return (Function1<Boolean, Boolean, X>) NOT;
	}
	
	private static final Function1<Boolean, Boolean, ?> NOT = new Function1<Boolean, Boolean, RuntimeException>() {
		@Override
		public Boolean evaluate(final Boolean value) {
			assert null != value;
			
			return !value.booleanValue();
		}
	};
	
	// Numbers.
	
	/**
	 * Builds a function which extracts the value of numbers as bytes.
	 * 
	 * @param <N> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see Number#byteValue()
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#byteValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Short, X> byteValue() {
		return (Function1<N, Short, X>) BYTE_VALUE;
	}
	
	private static final Function1<? extends Number, Short, ?> BYTE_VALUE = new Function1<Number, Short, RuntimeException>() {
		@Override
		public Short evaluate(final Number value) {
			return value.shortValue();
		}
	};
	
	/**
	 * Builds a function which extracts the value of numbers as shorts.
	 * 
	 * @param <N> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see Number#shortValue()
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#shortValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Short, X> shortValue() {
		return (Function1<N, Short, X>) SHORT_VALUE;
	}
	
	private static final Function1<? extends Number, Short, ?> SHORT_VALUE = new Function1<Number, Short, RuntimeException>() {
		@Override
		public Short evaluate(final Number value) {
			return value.shortValue();
		}
	};
	
	/**
	 * Builds a function which extracts the value of numbers as integers.
	 * 
	 * @param <N> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see Number#intValue()
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#intValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Integer, X> intValue() {
		return (Function1<N, Integer, X>) INT_VALUE;
	}
	
	private static final Function1<? extends Number, Integer, ?> INT_VALUE = new Function1<Number, Integer, RuntimeException>() {
		@Override
		public Integer evaluate(final Number value) {
			return value.intValue();
		}
	};
	
	/**
	 * Builds a function which extracts the value of numbers as long integers.
	 * 
	 * @param <N> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see Number#longValue()
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#longValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Long, X> longValue() {
		return (Function1<N, Long, X>) LONG_VALUE;
	}
	
	private static final Function1<? extends Number, Long, ?> LONG_VALUE = new Function1<Number, Long, RuntimeException>() {
		@Override
		public Long evaluate(final Number value) {
			return value.longValue();
		}
	};
	
	/**
	 * Builds a function which extracts the value of numbers as floats.
	 * 
	 * @param <N> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see Number#floatValue()
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#floatValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Float, X> floatValue() {
		return (Function1<N, Float, X>) FLOAT_VALUE;
	}
	
	private static final Function1<? extends Number, Float, ?> FLOAT_VALUE = new Function1<Number, Float, RuntimeException>() {
		@Override
		public Float evaluate(final Number value) {
			return value.floatValue();
		}
	};
	
	/**
	 * Builds a function which extracts the value of numbers as doubles.
	 * 
	 * @param <N> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see Number#doubleValue()
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#doubleValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Double, X> doubleValue() {
		return (Function1<N, Double, X>) DOUBLE_VALUE;
	}
	
	private static final Function1<? extends Number, Double, ?> DOUBLE_VALUE = new Function1<Number, Double, RuntimeException>() {
		@Override
		public Double evaluate(final Number value) {
			return value.doubleValue();
		}
	};
	
	// Enums.
	
	/**
	 * Builds a function that gets the name of enumeration entries.
	 * 
	 * @param <E> Type of the enumeration.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.lang.LangFunctions#name()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>, X extends Exception> Function1<E, String, X> name() {
		return (Function1<E, String, X>) NAME;
	}
	
	private static final Function1<? extends Enum<?>, String, ?> NAME = new Function1<Enum<?>, String, RuntimeException>() {
		@Override
		public String evaluate(final Enum<?> value) {
			return value.name();
		}
	};
	
	// Dates.
	
	// TODO: move away, Date is not in lang
	/**
	 * Builds a function which gets the time of calendars.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built function.
	 * @deprecated Use {@link com.trazere.core.util.CalendarFunctions#getTime()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<Calendar, Date, X> getTime() {
		return (Function1<Calendar, Date, X>) GET_TIME;
	}
	
	private static final Function1<Calendar, Date, RuntimeException> GET_TIME = new Function1<Calendar, Date, RuntimeException>() {
		@Override
		public Date evaluate(final Calendar calendar) {
			return calendar.getTime();
		}
	};
	
	private LangFunctions() {
		// Prevents instantiation.
	}
}
