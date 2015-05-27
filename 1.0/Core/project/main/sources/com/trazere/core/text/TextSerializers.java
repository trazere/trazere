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
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
	 * <p>
	 * This serializer is an identity.
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
	 * @see Boolean#toString()
	 * @see Boolean#valueOf(String)
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
	 * Builds a serializer of bytes to text.
	 * 
	 * @return The built serializer.
	 * @see Byte#toString()
	 * @see Byte#parseByte(String)
	 */
	public static Serializer<Byte, String> byte_() {
		return BYTE;
	}
	
	private static final Serializer<Byte, String> BYTE = new Serializer<Byte, String>() {
		@Override
		public String serialize(final Byte value) {
			return value.toString();
		}
		
		@Override
		public Byte deserialize(final String representation) {
			return Byte.parseByte(representation);
		}
	};
	
	/**
	 * Builds a serializer of short integers to text.
	 * 
	 * @return The built serializer.
	 * @see Short#toString()
	 * @see Short#parseShort(String)
	 */
	public static Serializer<Short, String> short_() {
		return SHORT;
	}
	
	private static final Serializer<Short, String> SHORT = new Serializer<Short, String>() {
		@Override
		public String serialize(final Short value) {
			return value.toString();
		}
		
		@Override
		public Short deserialize(final String representation) {
			return Short.parseShort(representation);
		}
	};
	
	/**
	 * Builds a serializer of integers to text.
	 * 
	 * @return The built serializer.
	 * @see Integer#toString()
	 * @see Integer#parseInt(String)
	 */
	public static Serializer<Integer, String> integer() {
		return INTEGER;
	}
	
	private static final Serializer<Integer, String> INTEGER = new Serializer<Integer, String>() {
		@Override
		public String serialize(final Integer value) {
			return value.toString();
		}
		
		@Override
		public Integer deserialize(final String representation) {
			return Integer.parseInt(representation);
		}
	};
	
	/**
	 * Builds a serializer of unsigned integers to text.
	 * 
	 * @return The built serializer.
	 * @see Integer#toString()
	 * @see Integer#parseUnsignedInt(String)
	 */
	public static Serializer<Integer, String> unsignedInteger() {
		return UNSIGNED_INTEGER;
	}
	
	private static final Serializer<Integer, String> UNSIGNED_INTEGER = new Serializer<Integer, String>() {
		@Override
		public String serialize(final Integer value) {
			return value.toString();
		}
		
		@Override
		public Integer deserialize(final String representation) {
			return Integer.parseUnsignedInt(representation);
		}
	};
	
	/**
	 * Builds a serializer of long integers to text.
	 * 
	 * @return The built serializer.
	 * @see Long#toString()
	 * @see Long#parseLong(String)
	 */
	public static Serializer<Long, String> long_() {
		return LONG;
	}
	
	private static final Serializer<Long, String> LONG = new Serializer<Long, String>() {
		@Override
		public String serialize(final Long value) {
			return value.toString();
		}
		
		@Override
		public Long deserialize(final String representation) {
			return Long.parseLong(representation);
		}
	};
	
	/**
	 * Builds a serializer of unsigned long integers to text.
	 * 
	 * @return The built serializer.
	 * @see Long#toString()
	 * @see Long#parseUnsignedLong(String)
	 */
	public static Serializer<Long, String> unsignedLong() {
		return UNSIGNED_LONG;
	}
	
	private static final Serializer<Long, String> UNSIGNED_LONG = new Serializer<Long, String>() {
		@Override
		public String serialize(final Long value) {
			return value.toString();
		}
		
		@Override
		public Long deserialize(final String representation) {
			return Long.parseUnsignedLong(representation);
		}
	};
	
	/**
	 * Builds a serializer of floats to text.
	 * 
	 * @return The built serializer.
	 * @see Float#toString()
	 * @see Float#parseFloat(String)
	 */
	public static Serializer<Float, String> float_() {
		return FLOAT;
	}
	
	private static final Serializer<Float, String> FLOAT = new Serializer<Float, String>() {
		@Override
		public String serialize(final Float value) {
			return value.toString();
		}
		
		@Override
		public Float deserialize(final String representation) {
			return Float.parseFloat(representation);
		}
	};
	
	/**
	 * Builds a serializer of doubles to text.
	 * 
	 * @return The built serializer.
	 * @see Double#toString()
	 * @see Double#parseDouble(String)
	 */
	public static Serializer<Double, String> double_() {
		return DOUBLE;
	}
	
	private static final Serializer<Double, String> DOUBLE = new Serializer<Double, String>() {
		@Override
		public String serialize(final Double value) {
			return value.toString();
		}
		
		@Override
		public Double deserialize(final String representation) {
			return Double.parseDouble(representation);
		}
	};
	
	/**
	 * Builds a serializer of numbers to text.
	 * 
	 * @param <N> Type of the numbers.
	 * @param format Format of the numbers.
	 * @param converter Function to use to convert the parsed numbers to the excepted type.
	 * @return The built serializer.
	 * @see TextUtils#formatNumber(NumberFormat, Number)
	 * @see TextUtils#parseNumber(NumberFormat, Function, String)
	 */
	public static <N extends Number> Serializer<N, String> number(final NumberFormat format, final Function<Number, N> converter) {
		assert null != converter;
		assert null != format;
		
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
					throw new NumberFormatException("Invalid number representation \"" + representation + "\" (" + TextUtils.formatNumber(format, 123.456) + ").");
				}
			}
		};
	}
	
	/**
	 * Builds a serializer of dates to text.
	 * 
	 * @param format Format of the dates.
	 * @return The built serializer.
	 * @see TextUtils#formatDate(DateFormat, Date)
	 * @see TextUtils#parseDate(DateFormat, String)
	 */
	public static Serializer<Date, String> date(final DateFormat format) {
		assert null != format;
		
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
					throw new IllegalArgumentException("Invalid date representation \"" + representation + "\" (" + TextUtils.formatDate(format, new Date()) + ").");
				}
			}
		};
	}
	
	/**
	 * Builds a serializer of UUIDs to text.
	 * 
	 * @return The built serializer.
	 * @see UUID#toString()
	 * @see UUID#fromString(String)
	 */
	public static Serializer<UUID, String> uuid() {
		return UUID;
	}
	
	private static final Serializer<UUID, String> UUID = new Serializer<UUID, String>() {
		@Override
		public String serialize(final UUID value) {
			return value.toString();
		}
		
		@Override
		public UUID deserialize(final String representation) {
			return java.util.UUID.fromString(representation);
		}
	};
	
	/**
	 * Builds a serializer of files to text.
	 * 
	 * @return The built serializer.
	 * @see File#toString()
	 * @see File#File(String)
	 */
	public static Serializer<File, String> file() {
		return FILE;
	}
	
	private static final Serializer<File, String> FILE = new Serializer<File, String>() {
		@Override
		public String serialize(final File value) {
			return value.toString();
		}
		
		@Override
		public File deserialize(final String representation) {
			return new File(representation);
		}
	};
	
	/**
	 * Builds a serializer of URIs to text.
	 * 
	 * @return The built serializer.
	 * @see URI#toString()
	 * @see URI#URI(String)
	 */
	public static Serializer<URI, String> uri() {
		return URI;
	}
	
	private static final Serializer<URI, String> URI = new Serializer<URI, String>() {
		@Override
		public String serialize(final URI value) {
			return value.toString();
		}
		
		@Override
		public URI deserialize(final String representation) {
			try {
				return new URI(representation);
			} catch (final URISyntaxException exception) {
				throw new IllegalArgumentException(exception);
			}
		}
	};
	
	/**
	 * Builds a serializer of URLs to text.
	 * 
	 * @return The built serializer.
	 * @see URL#toString()
	 * @see URL#URL(String)
	 */
	public static Serializer<URL, String> url() {
		return URL;
	}
	
	private static final Serializer<URL, String> URL = new Serializer<URL, String>() {
		@Override
		public String serialize(final URL value) {
			return value.toString();
		}
		
		@Override
		public URL deserialize(final String representation) {
			try {
				return new URL(representation);
			} catch (final MalformedURLException exception) {
				throw new IllegalArgumentException(exception);
			}
		}
	};
	
	private TextSerializers() {
		// Prevents instantiation.
	}
}
