package com.trazere.util.lang;

import com.trazere.util.function.Function1;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@link LangFunctions} class provides various factories of functions related to the language.
 */
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
	 */
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
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<Object, Class<?>, X> getClass_() {
		return (Function1<Object, Class<?>, X>) _GET_CLASS;
	}
	
	private static final Function1<Object, Class<?>, ?> _GET_CLASS = new Function1<Object, Class<?>, RuntimeException>() {
		@Override
		public Class<?> evaluate(final Object value) {
			return value.getClass();
		}
	};
	
	/**
	 * Builds a function returning the string representation of the values.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <T, X extends Exception> Function1<T, String, X> toString_() {
		return (Function1<T, String, X>) _TO_STRING;
	}
	
	private static final Function1<?, String, ?> _TO_STRING = new Function1<Object, String, RuntimeException>() {
		@Override
		public String evaluate(final Object value) {
			return value.toString();
		}
	};
	
	// Booleans.
	
	/**
	 * Builds a functions which negates boolean values.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<Boolean, Boolean, X> not() {
		return (Function1<Boolean, Boolean, X>) _NOT;
	}
	
	private static final Function1<Boolean, Boolean, ?> _NOT = new Function1<Boolean, Boolean, RuntimeException>() {
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
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Short, X> byteValue() {
		return (Function1<N, Short, X>) _BYTE_VALUE;
	}
	
	private static final Function1<? extends Number, Short, ?> _BYTE_VALUE = new Function1<Number, Short, RuntimeException>() {
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
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Short, X> shortValue() {
		return (Function1<N, Short, X>) _SHORT_VALUE;
	}
	
	private static final Function1<? extends Number, Short, ?> _SHORT_VALUE = new Function1<Number, Short, RuntimeException>() {
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
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Integer, X> intValue() {
		return (Function1<N, Integer, X>) _INT_VALUE;
	}
	
	private static final Function1<? extends Number, Integer, ?> _INT_VALUE = new Function1<Number, Integer, RuntimeException>() {
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
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Long, X> longValue() {
		return (Function1<N, Long, X>) _LONG_VALUE;
	}
	
	private static final Function1<? extends Number, Long, ?> _LONG_VALUE = new Function1<Number, Long, RuntimeException>() {
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
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Float, X> floatValue() {
		return (Function1<N, Float, X>) _FLOAT_VALUE;
	}
	
	private static final Function1<? extends Number, Float, ?> _FLOAT_VALUE = new Function1<Number, Float, RuntimeException>() {
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
	 */
	@SuppressWarnings("unchecked")
	public static <N extends Number, X extends Exception> Function1<N, Double, X> doubleValue() {
		return (Function1<N, Double, X>) _DOUBLE_VALUE;
	}
	
	private static final Function1<? extends Number, Double, ?> _DOUBLE_VALUE = new Function1<Number, Double, RuntimeException>() {
		@Override
		public Double evaluate(final Number value) {
			return value.doubleValue();
		}
	};
	
	// Dates.
	
	/**
	 * Builds a function which gets the time of calendars.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built function.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<Calendar, Date, X> getTime() {
		return (Function1<Calendar, Date, X>) _GET_TIME;
	}
	
	private static final Function1<Calendar, Date, RuntimeException> _GET_TIME = new Function1<Calendar, Date, RuntimeException>() {
		@Override
		public Date evaluate(final Calendar calendar) {
			return calendar.getTime();
		}
	};
	
	private LangFunctions() {
		// Prevents instantiation.
	}
}
