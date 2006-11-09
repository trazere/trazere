package com.trazere.util;

/**
 * The <code>Assert</code> class provides various methods to perform assertion tests.
 * <p>
 * <code>AssertException</code> are thrown when the assertion tests fail.
 * 
 * @see AssertException
 */
public class Assert {
	/**
	 * Check that the given condition is fulfilled (is <code>true</code>).
	 * 
	 * @param condition Condition to test.
	 * @throws AssertException
	 */
	public static void expression(final boolean condition)
	throws AssertException {
		if (!condition) {
			throw new AssertException("Condition not fulfilled");
		}
	}

	/**
	 * Check that the given condition is fulfilled (is <code>true</code>).
	 * 
	 * @param condition Condition to test.
	 * @param message Failure message.
	 * @throws AssertException
	 */
	public static void expression(final boolean condition, final String message) {
		if (!condition) {
			throw new AssertException(message);
		}
	}

	/**
	 * Check that the given value is not <code>null</code>.
	 * 
	 * @param value Value to test. May be <code>null</code>.
	 * @throws AssertException
	 */
	public static void notNull(final Object value) {
		if (null == value) {
			throw new AssertException("Should not be null");
		}
	}

	/**
	 * Check that the given value is <code>null</code>.
	 * 
	 * @param value Value to test. May be <code>null</code>.
	 * @throws AssertException
	 */
	public static void isNull(final Object value) {
		if (null != value) {
			throw new AssertException("Should be null (" + value + ")");
		}
	}

	/**
	 * Check that the given <code>boolean</code> values are equal.
	 * 
	 * @param value1 First value to test.
	 * @param value2 Second value to test.
	 * @throws AssertException
	 */
	public static void equal(final boolean value1, final boolean value2) {
		if (value1 != value2) {
			throw new AssertException("Should be equal (" + value1 + " != " + value2 + ")");
		}
	}

	/**
	 * Check that the given <code>int</code> values are equal.
	 * 
	 * @param value1 First value to test.
	 * @param value2 Second value to test.
	 * @throws AssertException
	 */
	public static void equal(final int value1, final int value2) {
		if (value1 != value2) {
			throw new AssertException("Should be equal (" + value1 + " != " + value2 + ")");
		}
	}

	/**
	 * Check that the given <code>long</code> values are equal.
	 * 
	 * @param value1 First value to test.
	 * @param value2 Second value to test.
	 * @throws AssertException
	 */
	public static void equal(final long value1, final long value2) {
		if (value1 != value2) {
			throw new AssertException("Should be equal (" + value1 + " != " + value2 + ")");
		}
	}

	/**
	 * Check that the given object values are equal.
	 * <p>
	 * Values are considered equal when they are both <code>null</code> or both not <code>null</code> and equal according to the
	 * {@link Object#equals(Object)} method.
	 * 
	 * @param value1 First value to test. May be <code>null</code>.
	 * @param value2 Second value to test. May be <code>null</code>.
	 * @throws AssertException
	 */
	public static void equal(final Object value1, final Object value2) {
		if ((null != value1 && (null == value2 || !value1.equals(value2))) || (null == value1 && null != value2)) {
			throw new AssertException("Should be equal (" + value1 + " <> " + value2 + ")");
		}
	}

	/**
	 * Check that the given object values are equal.
	 * <p>
	 * Values are considered equal when they are both <code>null</code> or both not <code>null</code> and physically equal according <code>==</code>
	 * operator.
	 * 
	 * @param value1 First value to test. May be <code>null</code>.
	 * @param value2 Second value to test. May be <code>null</code>.
	 * @throws AssertException
	 */
	public static void same(final Object value1, final Object value2) {
		if (value1 != value2) {
			throw new AssertException("Should be same (" + value1 + " != " + value2 + ")");
		}
	}

	private Assert() {
		// Prevent instanciation.
	}
}
