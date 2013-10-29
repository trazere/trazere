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
package com.trazere.util.value;

import com.trazere.util.function.Function1;
import com.trazere.util.lang.ThrowableFactory;
import com.trazere.util.text.Description;
import com.trazere.util.text.TextUtils;
import com.trazere.util.type.Maybe;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The {@link ValueSerializers} class provides common value serializers.
 */
public class ValueSerializers {
	/**
	 * Builds an identity serializer.
	 * 
	 * @param <T> Type of the values.
	 * @param <X> Type of the exceptions.
	 * @param valueClass Java class of the values.
	 * @return The built serializer.
	 */
	public static <T, X extends Exception> ValueSerializer<T, T, X> identity(final Class<T> valueClass) {
		assert null != valueClass;
		
		return new BaseValueSerializer<T, T, X>(valueClass, valueClass) {
			@Override
			public T serialize(final T value) {
				assert null != value;
				
				return value;
			}
			
			@Override
			public T deserialize(final T representation) {
				assert null != representation;
				
				return representation;
			}
		};
	}
	
	/**
	 * Builds a serializer of booleans to strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built serializer.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> ValueSerializer<Boolean, String, X> booleanToString() {
		return (ValueSerializer<Boolean, String, X>) _BOOLEAN;
	}
	
	private static final ValueSerializer<Boolean, String, ?> _BOOLEAN = new BaseValueSerializer<Boolean, String, RuntimeException>(Boolean.class, String.class) {
		@Override
		public String serialize(final Boolean value) {
			assert null != value;
			
			return value.toString();
		}
		
		@Override
		public Boolean deserialize(final String representation) {
			assert null != representation;
			
			return Boolean.valueOf(representation);
		}
	};
	
	/**
	 * Builds a serializer of dates to strings according to the given format.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param format The format of the dates.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<Date, String, X> dateToString(final DateFormat format, final ThrowableFactory<X> exceptionFactory) {
		assert null != format;
		assert null != exceptionFactory;
		
		return new BaseValueSerializer<Date, String, X>(Date.class, String.class) {
			@Override
			public String serialize(final Date value) {
				return TextUtils.formatDate(format, value);
			}
			
			@Override
			public Date deserialize(final String representation)
			throws X {
				final Maybe<Date> date = TextUtils.parseDate(format, representation);
				if (date.isSome()) {
					return date.asSome().getValue();
				} else {
					throw exceptionFactory.build("Invalid date \"" + representation + "\" (" + TextUtils.formatDate(format, new Date()) + ").");
				}
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Format", format);
			}
		};
	}
	
	/**
	 * Builds a serializer of numbers to strings according to the given converter and format.
	 * 
	 * @param <T> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @param type The Java type of the values.
	 * @param converter The converter.
	 * @param format The format of the dates.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <T extends Number, X extends Exception> ValueSerializer<T, String, X> numberToString(final Class<T> type, final Function1<Number, T, RuntimeException> converter, final NumberFormat format, final ThrowableFactory<X> exceptionFactory) {
		assert null != converter;
		assert null != format;
		assert null != exceptionFactory;
		
		return new BaseValueSerializer<T, String, X>(type, String.class) {
			@Override
			public String serialize(final T value) {
				return TextUtils.formatNumber(format, value);
			}
			
			@Override
			public T deserialize(final String representation)
			throws X {
				final Maybe<T> value = TextUtils.parseNumber(format, converter, representation);
				if (value.isSome()) {
					return value.asSome().getValue();
				} else {
					throw exceptionFactory.build("Invalid number \"" + representation + "\" (" + TextUtils.formatNumber(format, 123.456) + ").");
				}
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Format", format);
			}
		};
	}
	
	/**
	 * Builds a serializer of strings to strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @return The built serializer.
	 */
	@SuppressWarnings("unchecked")
	public static <X extends Exception> ValueSerializer<String, String, X> stringToString() {
		return (ValueSerializer<String, String, X>) _STRING;
	}
	
	private static final ValueSerializer<String, String, ?> _STRING = ValueSerializers.<String, RuntimeException>identity(String.class);
	
	/**
	 * Builds a serializer of strings to strings with length constraints.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param minLength The min length of the strings.
	 * @param maxLength The max length of the strings or <code>0</code> when unlimited.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<String, String, X> stringToString(final int minLength, final int maxLength, final ThrowableFactory<X> exceptionFactory) {
		assert minLength >= 0;
		assert maxLength >= 0;
		assert minLength <= maxLength;
		assert null != exceptionFactory;
		
		return new BaseValueSerializer<String, String, X>(String.class, String.class) {
			@Override
			public String serialize(final String value)
			throws X {
				assert null != value;
				
				final int length = value.length();
				if (length < minLength) {
					throw exceptionFactory.build("Value \"" + value + "\" is too short (" + minLength + " min)");
				} else if (maxLength > 0 && length > maxLength) {
					throw exceptionFactory.build("Value \"" + value + "\" is too long (" + minLength + " max)");
				} else {
					return value;
				}
			}
			
			@Override
			public String deserialize(final String representation)
			throws X {
				assert null != representation;
				
				final int length = representation.length();
				if (length < minLength) {
					throw exceptionFactory.build("Representation \"" + representation + "\" is too short (" + minLength + " min)");
				} else if (maxLength > 0 && length > maxLength) {
					throw exceptionFactory.build("Representation \"" + representation + "\" is too long (" + minLength + " max)");
				} else {
					return representation;
				}
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Min length", minLength);
				description.append("Max length", maxLength);
			}
		};
	}
	
	/**
	 * Builds a serializer of UUID.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<UUID, String, X> uuidToString(final ThrowableFactory<X> exceptionFactory) {
		assert null != exceptionFactory;
		
		return new BaseValueSerializer<UUID, String, X>(UUID.class, String.class) {
			@Override
			public String serialize(final UUID value) {
				assert null != value;
				
				return value.toString();
			}
			
			@Override
			public UUID deserialize(final String representation)
			throws X {
				assert null != representation;
				
				try {
					return UUID.fromString(representation);
				} catch (final IllegalArgumentException exception) {
					throw exceptionFactory.build("Invalid UUID \"" + representation + "\"", exception);
				}
			}
		};
	}
	
	private ValueSerializers() {
		// Prevents instantiation.
	}
}
