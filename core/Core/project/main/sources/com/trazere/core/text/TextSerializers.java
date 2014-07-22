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
package com.trazere.core.text;

import com.trazere.core.functional.Function;
import com.trazere.core.lang.ThrowableFactory;
import com.trazere.core.util.Maybe;
import com.trazere.core.util.Serializer;
import com.trazere.core.util.Serializers;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The {@link TextSerializers} class provides various factories of serializers related to text.
 * 
 * @see Serializer
 */
public class TextSerializers {
	/**
	 * Builds a serializer of strings to text.
	 * 
	 * @return The built serializer.
	 */
	public static Serializer<String, String> string() {
		return STRING;
	}
	
	private static final Serializer<String, String> STRING = Serializers.identity();
	
	/**
	 * Builds a serializer of strings to text with length constraints.
	 * 
	 * @param minLength Min length of the strings.
	 * @param maxLength Max length of the strings, or <code>0</code> for unlimited length.
	 * @param failureFactory Factory of failures.
	 * @return The built serializer.
	 */
	public static Serializer<String, String> string(final int minLength, final int maxLength, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert minLength >= 0;
		assert maxLength >= 0;
		assert minLength <= maxLength;
		assert null != failureFactory;
		
		return new Serializer<String, String>() {
			@Override
			public String serialize(final String value) {
				final int length = value.length();
				if (length < minLength) {
					throw failureFactory.build("Value \"" + value + "\" is too short (" + minLength + " min)");
				} else if (maxLength > 0 && length > maxLength) {
					throw failureFactory.build("Value \"" + value + "\" is too long (" + minLength + " max)");
				} else {
					return value;
				}
			}
			
			@Override
			public String deserialize(final String representation) {
				final int length = representation.length();
				if (length < minLength) {
					throw failureFactory.build("Representation \"" + representation + "\" is too short (" + minLength + " min)");
				} else if (maxLength > 0 && length > maxLength) {
					throw failureFactory.build("Representation \"" + representation + "\" is too long (" + minLength + " max)");
				} else {
					return representation;
				}
			}
		};
	}
	
	/**
	 * Builds a serializer of booleans to text.
	 * 
	 * @return The built serializer.
	 */
	public static Serializer<Boolean, String> boolean_() {
		return BOOLEAN;
	}
	
	private static final Serializer<Boolean, String> BOOLEAN = new Serializer<Boolean, String>() {
		@Override
		public String serialize(final Boolean value) {
			return value.toString();
		}
		
		@Override
		public Boolean deserialize(final String representation) {
			return Boolean.valueOf(representation);
		}
	};
	
	/**
	 * Builds a serializer of numbers to text.
	 * 
	 * @param <N> Type of the numbers.
	 * @param format Format of the numbers.
	 * @param converter Function to use to convert the parsed numbers to the excepted type.
	 * @param failureFactory Factory of the failures.
	 * @return The built serializer.
	 */
	public static <N extends Number> Serializer<N, String> number(final NumberFormat format, final Function<Number, N> converter, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != converter;
		assert null != format;
		assert null != failureFactory;
		
		return new Serializer<N, String>() {
			@Override
			public String serialize(final N value) {
				return TextUtils.formatNumber(format, value);
			}
			
			@Override
			public N deserialize(final String representation) {
				final Maybe<N> value = TextUtils.parseNumber(format, converter, representation);
				if (value.isSome()) {
					return value.asSome().getValue();
				} else {
					throw failureFactory.build("Invalid number \"" + representation + "\" (" + TextUtils.formatNumber(format, 123.456) + ").");
				}
			}
		};
	}
	
	/**
	 * Builds a serializer of dates to text.
	 * 
	 * @param format Format of the dates.
	 * @param failureFactory Factory of the failures.
	 * @return The built serializer.
	 */
	public static Serializer<Date, String> date(final DateFormat format, final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != format;
		assert null != failureFactory;
		
		return new Serializer<Date, String>() {
			@Override
			public String serialize(final Date value) {
				return TextUtils.formatDate(format, value);
			}
			
			@Override
			public Date deserialize(final String representation) {
				final Maybe<Date> value = TextUtils.parseDate(format, representation);
				if (value.isSome()) {
					return value.asSome().getValue();
				} else {
					throw failureFactory.build("Invalid date \"" + representation + "\" (" + TextUtils.formatDate(format, new Date()) + ").");
				}
			}
		};
	}
	
	/**
	 * Builds a serializer of UUIDs to text.
	 * 
	 * @param failureFactory Factory of the failures.
	 * @return The built serializer.
	 */
	public static Serializer<UUID, String> uuid(final ThrowableFactory<? extends RuntimeException> failureFactory) {
		assert null != failureFactory;
		
		return new Serializer<UUID, String>() {
			@Override
			public String serialize(final UUID value) {
				return TextUtils.formatUuid(value);
			}
			
			@Override
			public UUID deserialize(final String representation) {
				final Maybe<UUID> value = TextUtils.parseUuid(representation);
				if (value.isSome()) {
					return value.asSome().getValue();
				} else {
					throw failureFactory.build("Invalid UUID \"" + representation + "\" (" + TextUtils.formatUuid(new UUID(0L, 0L)) + ").");
				}
			}
		};
	}
	
	private TextSerializers() {
		// Prevents instantiation.
	}
}
