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
package com.trazere.util.math;

import com.trazere.util.function.Function1;
import com.trazere.util.type.Maybe;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The {@link MathExtractors} class provides various factories of extractors related to maths.
 * 
 * @see Function1
 * @deprecated Use {@link com.trazere.core}.
 */
@Deprecated
public class MathExtractors {
	/**
	 * Builds an extractor that converts big integers into bytes.
	 * <p>
	 * These extractor controls that the given values fit in bytes.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigIntegerExtractors#byteValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigInteger, Maybe<Byte>, X> byteFromBigInteger() {
		return (Function1<BigInteger, Maybe<Byte>, X>) _BYTE_FROM_BIG_INTEGER;
	}
	
	private static final Function1<BigInteger, Maybe<Byte>, ?> _BYTE_FROM_BIG_INTEGER = new Function1<BigInteger, Maybe<Byte>, RuntimeException>() {
		@Override
		public Maybe<Byte> evaluate(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Byte.SIZE) {
				return Maybe.some(value.byteValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big integers into short integers.
	 * <p>
	 * These extractor controls that the given values fit in short integers.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigIntegerExtractors#shortValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigInteger, Maybe<Short>, X> shortFromBigInteger() {
		return (Function1<BigInteger, Maybe<Short>, X>) _SHORT_FROM_BIG_INTEGER;
	}
	
	private static final Function1<BigInteger, Maybe<Short>, ?> _SHORT_FROM_BIG_INTEGER = new Function1<BigInteger, Maybe<Short>, RuntimeException>() {
		@Override
		public Maybe<Short> evaluate(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Short.SIZE) {
				return Maybe.some(value.shortValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big integers into integers.
	 * <p>
	 * These extractor controls that the given values fit in integers.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigIntegerExtractors#integerValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigInteger, Maybe<Integer>, X> integerFromBigInteger() {
		return (Function1<BigInteger, Maybe<Integer>, X>) _INTEGER_FROM_BIG_INTEGER;
	}
	
	private static final Function1<BigInteger, Maybe<Integer>, ?> _INTEGER_FROM_BIG_INTEGER = new Function1<BigInteger, Maybe<Integer>, RuntimeException>() {
		@Override
		public Maybe<Integer> evaluate(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Integer.SIZE) {
				return Maybe.some(value.intValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big integers into long integers.
	 * <p>
	 * These extractor controls that the given values fit in long integers.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigIntegerExtractors#longValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigInteger, Maybe<Long>, X> longFromBigInteger() {
		return (Function1<BigInteger, Maybe<Long>, X>) _LONG_FROM_BIG_INTEGER;
	}
	
	private static final Function1<BigInteger, Maybe<Long>, ?> _LONG_FROM_BIG_INTEGER = new Function1<BigInteger, Maybe<Long>, RuntimeException>() {
		@Override
		public Maybe<Long> evaluate(final BigInteger value) {
			assert null != value;
			
			if (value.bitLength() < Long.SIZE) {
				return Maybe.some(value.longValue());
			} else {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big decimals into bytes.
	 * <p>
	 * These extractor controls that the given values fit in bytes without information loss.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigDecimalExtractors#byteValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigDecimal, Maybe<Byte>, X> byteFromBigDecimal() {
		return (Function1<BigDecimal, Maybe<Byte>, X>) _BYTE_FROM_BIG_DECIMAL;
	}
	
	private static final Function1<BigDecimal, Maybe<Byte>, ?> _BYTE_FROM_BIG_DECIMAL = new Function1<BigDecimal, Maybe<Byte>, RuntimeException>() {
		@Override
		public Maybe<Byte> evaluate(final BigDecimal value) {
			assert null != value;
			
			try {
				return Maybe.some(value.byteValueExact());
			} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big decimals into short integers.
	 * <p>
	 * These extractor controls that the given values fit in short integers without information loss.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigDecimalExtractors#shortValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigDecimal, Maybe<Short>, X> shortFromBigDecimal() {
		return (Function1<BigDecimal, Maybe<Short>, X>) _SHORT_FROM_BIG_DECIMAL;
	}
	
	private static final Function1<BigDecimal, Maybe<Short>, ?> _SHORT_FROM_BIG_DECIMAL = new Function1<BigDecimal, Maybe<Short>, RuntimeException>() {
		@Override
		public Maybe<Short> evaluate(final BigDecimal value) {
			assert null != value;
			
			try {
				return Maybe.some(value.shortValueExact());
			} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big decimals into integers.
	 * <p>
	 * These extractor controls that the given values fit in integers without information loss.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigDecimalExtractors#integerValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigDecimal, Maybe<Integer>, X> integerFromBigDecimal() {
		return (Function1<BigDecimal, Maybe<Integer>, X>) _INTEGER_FROM_BIG_DECIMAL;
	}
	
	private static final Function1<BigDecimal, Maybe<Integer>, ?> _INTEGER_FROM_BIG_DECIMAL = new Function1<BigDecimal, Maybe<Integer>, RuntimeException>() {
		@Override
		public Maybe<Integer> evaluate(final BigDecimal value) {
			assert null != value;
			
			try {
				return Maybe.some(value.intValueExact());
			} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big decimals into long integers.
	 * <p>
	 * These extractor controls that the given values fit in long integers without information loss.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigDecimalExtractors#longValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigDecimal, Maybe<Long>, X> longFromBigDecimal() {
		return (Function1<BigDecimal, Maybe<Long>, X>) _LONG_FROM_BIG_DECIMAL;
	}
	
	private static final Function1<BigDecimal, Maybe<Long>, ?> _LONG_FROM_BIG_DECIMAL = new Function1<BigDecimal, Maybe<Long>, RuntimeException>() {
		@Override
		public Maybe<Long> evaluate(final BigDecimal value) {
			assert null != value;
			
			try {
				return Maybe.some(value.longValueExact());
			} catch (@SuppressWarnings("unused") final ArithmeticException exception) {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big decimals into floats.
	 * <p>
	 * These extractor controls that the given values fit in floats without information loss.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigDecimalExtractors#floatValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigDecimal, Maybe<Float>, X> floatFromBigDecimal() {
		return (Function1<BigDecimal, Maybe<Float>, X>) _FLOAT_FROM_BIG_DECIMAL;
	}
	
	private static final Function1<BigDecimal, Maybe<Float>, ?> _FLOAT_FROM_BIG_DECIMAL = new Function1<BigDecimal, Maybe<Float>, RuntimeException>() {
		@Override
		public Maybe<Float> evaluate(final BigDecimal value) {
			assert null != value;
			
			try {
				return Maybe.some(value.floatValue());
			} catch (@SuppressWarnings("unused") final NumberFormatException exception) {
				return Maybe.none();
			}
		}
	};
	
	/**
	 * Builds an extractor that converts big decimals into doubles.
	 * <p>
	 * These extractor controls that the given values fit in doubles without information loss.
	 * 
	 * @param <X> Type of the exception.
	 * @return The built extractor.
	 * @deprecated Use {@link com.trazere.core.math.BigDecimalExtractors#doubleValue()}.
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public static <X extends Exception> Function1<BigDecimal, Maybe<Double>, X> doubleFromBigDecimal() {
		return (Function1<BigDecimal, Maybe<Double>, X>) _DOUBLE_FROM_BIG_DECIMAL;
	}
	
	private static final Function1<BigDecimal, Maybe<Double>, ?> _DOUBLE_FROM_BIG_DECIMAL = new Function1<BigDecimal, Maybe<Double>, RuntimeException>() {
		@Override
		public Maybe<Double> evaluate(final BigDecimal value) {
			assert null != value;
			
			try {
				return Maybe.some(value.doubleValue());
			} catch (@SuppressWarnings("unused") final NumberFormatException exception) {
				return Maybe.none();
			}
		}
	};
	
	private MathExtractors() {
		// Prevents instantiation.
	}
}
