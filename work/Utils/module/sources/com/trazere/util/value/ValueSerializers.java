/*
 *  Copyright 2006-2010 Julien Dufour
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
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@link ValueSerializers} class provides common value serializers.
 */
public class ValueSerializers {
	/**
	 * Build a serializer of booleans.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<Boolean, X> buildBoolean(final ThrowableFactory<X> exceptionFactory) {
		assert null != exceptionFactory;
		
		return new AbstractValueSerializer<Boolean, X>(Boolean.class) {
			public String serialize(final Boolean value) {
				assert null != value;
				
				return value.toString();
			}
			
			public Boolean deserialize(final String representation) {
				assert null != representation;
				
				return Boolean.valueOf(representation);
			}
		};
	}
	
	/**
	 * Build a serializer of dates according to the given format.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param format The format of the dates.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<Date, X> buildDate(final SimpleDateFormat format, final ThrowableFactory<X> exceptionFactory) {
		assert null != format;
		assert null != exceptionFactory;
		
		return new AbstractValueSerializer<Date, X>(Date.class) {
			public String serialize(final Date value) {
				assert null != value;
				
				return format.format(value);
			}
			
			public Date deserialize(final String representation)
			throws X {
				assert null != representation;
				
				synchronized (format) {
					final ParsePosition position = new ParsePosition(0);
					final Date date = format.parse(representation, position);
					if (position.getIndex() == representation.length()) {
						return date;
					} else {
						throw exceptionFactory.build("Invalid date \"" + representation + "\" (" + format.format(new Date()) + ").");
					}
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
	 * Build a serializer of numbers according to the given converter and format.
	 * 
	 * @param <T> Type of the numbers.
	 * @param <X> Type of the exceptions.
	 * @param type The Java type of the values.
	 * @param converter The converter.
	 * @param format The format of the dates.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <T extends Number, X extends Exception> ValueSerializer<T, X> buildNumber(final Class<T> type, final Function1<Number, T, RuntimeException> converter, final NumberFormat format, final ThrowableFactory<X> exceptionFactory) {
		assert null != converter;
		assert null != format;
		assert null != exceptionFactory;
		
		return new AbstractValueSerializer<T, X>(type) {
			public String serialize(final T value) {
				assert null != value;
				
				synchronized (format) {
					return format.format(value);
				}
			}
			
			public T deserialize(final String representation)
			throws X {
				assert null != representation;
				
				synchronized (format) {
					final ParsePosition position = new ParsePosition(0);
					final Number number = format.parse(representation, position);
					if (position.getIndex() == representation.length()) {
						return converter.evaluate(number);
					} else {
						throw exceptionFactory.build("Invalid number \"" + representation + "\" (" + format.format(123.456) + ").");
					}
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
	 * Build a serializer of strings.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<String, X> buildString(final ThrowableFactory<X> exceptionFactory) {
		assert null != exceptionFactory;
		
		return new AbstractValueSerializer<String, X>(String.class) {
			public String serialize(final String value) {
				assert null != value;
				
				return value;
			}
			
			public String deserialize(final String representation) {
				assert null != representation;
				
				return representation;
			}
		};
	}
	
	/**
	 * Build a serializer of strings with the given length constraints.
	 * 
	 * @param <X> Type of the exceptions.
	 * @param minLength The min length of the strings.
	 * @param maxLength The max length of the strings or <code>0</code> when unlimited.
	 * @param exceptionFactory The exception factory to use.
	 * @return The built serializer.
	 */
	public static <X extends Exception> ValueSerializer<String, X> buildString(final int minLength, final int maxLength, final ThrowableFactory<X> exceptionFactory) {
		assert minLength >= 0;
		assert maxLength >= 0;
		assert minLength <= maxLength;
		assert null != exceptionFactory;
		
		return new AbstractValueSerializer<String, X>(String.class) {
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
			
			public String deserialize(final String representation) {
				assert null != representation;
				
				return representation;
			}
			
			@Override
			public void fillDescription(final Description description) {
				super.fillDescription(description);
				description.append("Min length", minLength);
				description.append("Max length", maxLength);
			}
		};
	}
	
	private ValueSerializers() {
		// Prevent instantiation.
	}
}
