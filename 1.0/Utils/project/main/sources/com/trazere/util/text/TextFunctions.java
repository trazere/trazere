package com.trazere.util.text;

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The {@link TextFunctions} class provides various factories of functions related to text.
 * 
 * @see Function1
 * @deprecated Use core.
 */
@Deprecated
public class TextFunctions {
	// Cases.
	
	/**
	 * Builds a function which converts strings to lower case.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see String#toLowerCase()
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#toLowerCase()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, String, X> toLowerCase() {
		return (Function1<String, String, X>) _TO_LOWER_CASE;
	}
	
	private static final Function1<String, String, ?> _TO_LOWER_CASE = new Function1<String, String, RuntimeException>() {
		@Override
		public String evaluate(final String value) {
			assert null != value;
			
			return value.toLowerCase();
		}
	};
	
	/**
	 * Builds a function which converts strings to upper case.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see String#toUpperCase()
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#toUpperCase()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, String, X> toUpperCase() {
		return (Function1<String, String, X>) _TO_UPPER_CASE;
	}
	
	private static final Function1<String, String, ?> _TO_UPPER_CASE = new Function1<String, String, RuntimeException>() {
		@Override
		public String evaluate(final String value) {
			assert null != value;
			
			return value.toUpperCase();
		}
	};
	
	/**
	 * Builds a function which capitalizes strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see TextUtils#capitalize(String)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#capitalize()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, String, X> capitalize() {
		return (Function1<String, String, X>) _CAPITALIZE;
	}
	
	private static final Function1<String, String, ?> _CAPITALIZE = new Function1<String, String, RuntimeException>() {
		@Override
		public String evaluate(final String s) {
			return TextUtils.capitalize(s);
		}
	};
	
	// Numbers.
	
	/**
	 * Builds a number formatting function.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param <T> Type of the numbers.
	 * @param <X> Type of the exception.
	 * @param format The number format.
	 * @return The built function.
	 * @see TextUtils#formatNumber(NumberFormat, Number)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#formatNumber(NumberFormat)}.
	 */
	@Deprecated
	public static <T extends Number, X extends Exception> Function1<T, String, X> formatNumber(final NumberFormat format) {
		assert null != format;
		
		return new Function1<T, String, X>() {
			@Override
			public String evaluate(final T number) {
				return TextUtils.formatNumber(format, number);
			}
		};
	}
	
	/**
	 * Builds a number parsing function.
	 * 
	 * @param <T> Type of the numbers to parse.
	 * @param <X> Type of the exceptions.
	 * @param format The number format.
	 * @param converter The converter of the {@link Number} instance to the excepted type.
	 * @return The built function.
	 * @see TextUtils#parseNumber(NumberFormat, Function1, String)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#parseNumber(NumberFormat, com.trazere.core.functional.Function)}.
	 */
	@Deprecated
	public static <T extends Number, X extends Exception> Function1<String, Maybe<T>, X> parseNumber(final NumberFormat format, final Function1<Number, T, RuntimeException> converter) {
		assert null != format;
		assert null != converter;
		
		return new Function1<String, Maybe<T>, X>() {
			@Override
			public Maybe<T> evaluate(final String representation) {
				return TextUtils.parseNumber(format, converter, representation);
			}
		};
	}
	
	// Dates.
	
	/**
	 * Builds a date formatting function.
	 * <p>
	 * This method synchronizes the format.
	 * 
	 * @param <T> Type of the dates.
	 * @param <X> Type of the exception.
	 * @param format The date format.
	 * @return The built function.
	 * @see TextUtils#formatDate(DateFormat, Date)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#formatDate(DateFormat)}.
	 */
	@Deprecated
	public static <T extends Date, X extends Exception> Function1<T, String, X> formatDate(final DateFormat format) {
		assert null != format;
		
		return new Function1<T, String, X>() {
			@Override
			public String evaluate(final T date) {
				return TextUtils.formatDate(format, date);
			}
		};
	}
	
	/**
	 * Builds a date parsing function.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param format The date format.
	 * @return The built function.
	 * @see TextUtils#parseDate(DateFormat, String)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#parseDate(DateFormat)}.
	 */
	@Deprecated
	public static <X extends Exception> Function1<String, Maybe<Date>, X> parseDate(final DateFormat format) {
		assert null != format;
		
		return new Function1<String, Maybe<Date>, X>() {
			@Override
			public Maybe<Date> evaluate(final String representation) {
				return TextUtils.parseDate(format, representation);
			}
		};
	}
	
	/**
	 * Builds an UUID formatting function.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built function.
	 * @see TextUtils#formatUuid(UUID)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#formatUuid()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<UUID, String, X> formatUuid() {
		return (Function1<UUID, String, X>) _FORMAT_UUID;
	}
	
	private static final Function1<UUID, String, ?> _FORMAT_UUID = new Function1<UUID, String, RuntimeException>() {
		@Override
		public String evaluate(final UUID uuid) {
			return TextUtils.formatUuid(uuid);
		}
	};
	
	/**
	 * Builds an UUID parsing function.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built function.
	 * @see TextUtils#parseUuid(String)
	 * @deprecated Use {@link com.trazere.core.text.TextFunctions#parseUuid()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<String, Maybe<UUID>, X> parseUuid() {
		return (Function1<String, Maybe<UUID>, X>) _PARSE_UUID;
	}
	
	private static final Function1<String, Maybe<UUID>, ?> _PARSE_UUID = new Function1<String, Maybe<UUID>, RuntimeException>() {
		@Override
		public Maybe<UUID> evaluate(final String representation) {
			return TextUtils.parseUuid(representation);
		}
	};
	
	private TextFunctions() {
		// Prevents instantiation.
	}
}
