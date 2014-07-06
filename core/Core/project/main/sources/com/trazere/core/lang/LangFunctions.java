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
package com.trazere.core.lang;

/**
 * The {@link LangFunctions} class provides various factories of functions related to the language.
 */
public class LangFunctions {
	//	// Booleans.
	//
	//	/**
	//	 * Builds a functions which negates boolean values.
	//	 *
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <X extends Exception> Function1<Boolean, Boolean, X> not() {
	//		return (Function1<Boolean, Boolean, X>) NOT;
	//	}
	//
	//	private static final Function1<Boolean, Boolean, ?> NOT = new Function1<Boolean, Boolean, RuntimeException>() {
	//		@Override
	//		public Boolean evaluate(final Boolean value) {
	//			assert null != value;
	//
	//			return !value.booleanValue();
	//		}
	//	};
	//
	//	// Numbers.
	//
	//	/**
	//	 * Builds a function which extracts the value of numbers as bytes.
	//	 *
	//	 * @param <N> Type of the numbers.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see Number#byteValue()
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <N extends Number, X extends Exception> Function1<N, Short, X> byteValue() {
	//		return (Function1<N, Short, X>) BYTE_VALUE;
	//	}
	//
	//	private static final Function1<? extends Number, Short, ?> BYTE_VALUE = new Function1<Number, Short, RuntimeException>() {
	//		@Override
	//		public Short evaluate(final Number value) {
	//			return value.shortValue();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a function which extracts the value of numbers as shorts.
	//	 *
	//	 * @param <N> Type of the numbers.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see Number#shortValue()
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <N extends Number, X extends Exception> Function1<N, Short, X> shortValue() {
	//		return (Function1<N, Short, X>) SHORT_VALUE;
	//	}
	//
	//	private static final Function1<? extends Number, Short, ?> SHORT_VALUE = new Function1<Number, Short, RuntimeException>() {
	//		@Override
	//		public Short evaluate(final Number value) {
	//			return value.shortValue();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a function which extracts the value of numbers as integers.
	//	 *
	//	 * @param <N> Type of the numbers.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see Number#intValue()
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <N extends Number, X extends Exception> Function1<N, Integer, X> intValue() {
	//		return (Function1<N, Integer, X>) INT_VALUE;
	//	}
	//
	//	private static final Function1<? extends Number, Integer, ?> INT_VALUE = new Function1<Number, Integer, RuntimeException>() {
	//		@Override
	//		public Integer evaluate(final Number value) {
	//			return value.intValue();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a function which extracts the value of numbers as long integers.
	//	 *
	//	 * @param <N> Type of the numbers.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see Number#longValue()
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <N extends Number, X extends Exception> Function1<N, Long, X> longValue() {
	//		return (Function1<N, Long, X>) LONG_VALUE;
	//	}
	//
	//	private static final Function1<? extends Number, Long, ?> LONG_VALUE = new Function1<Number, Long, RuntimeException>() {
	//		@Override
	//		public Long evaluate(final Number value) {
	//			return value.longValue();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a function which extracts the value of numbers as floats.
	//	 *
	//	 * @param <N> Type of the numbers.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see Number#floatValue()
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <N extends Number, X extends Exception> Function1<N, Float, X> floatValue() {
	//		return (Function1<N, Float, X>) FLOAT_VALUE;
	//	}
	//
	//	private static final Function1<? extends Number, Float, ?> FLOAT_VALUE = new Function1<Number, Float, RuntimeException>() {
	//		@Override
	//		public Float evaluate(final Number value) {
	//			return value.floatValue();
	//		}
	//	};
	//
	//	/**
	//	 * Builds a function which extracts the value of numbers as doubles.
	//	 *
	//	 * @param <N> Type of the numbers.
	//	 * @param <X> Type of the exceptions.
	//	 * @return The built function.
	//	 * @see Number#doubleValue()
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <N extends Number, X extends Exception> Function1<N, Double, X> doubleValue() {
	//		return (Function1<N, Double, X>) DOUBLE_VALUE;
	//	}
	//
	//	private static final Function1<? extends Number, Double, ?> DOUBLE_VALUE = new Function1<Number, Double, RuntimeException>() {
	//		@Override
	//		public Double evaluate(final Number value) {
	//			return value.doubleValue();
	//		}
	//	};
	//
	//	// Dates.
	//
	//	// TODO: move away, Date is not in lang
	//	/**
	//	 * Builds a function which gets the time of calendars.
	//	 *
	//	 * @param <X> Type of the exception.
	//	 * @return The built function.
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static <X extends Exception> Function1<Calendar, Date, X> getTime() {
	//		return (Function1<Calendar, Date, X>) GET_TIME;
	//	}
	//
	//	private static final Function1<Calendar, Date, RuntimeException> GET_TIME = new Function1<Calendar, Date, RuntimeException>() {
	//		@Override
	//		public Date evaluate(final Calendar calendar) {
	//			return calendar.getTime();
	//		}
	//	};
	
	private LangFunctions() {
		// Prevents instantiation.
	}
}
