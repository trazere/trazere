package com.trazere.util.type;

import com.trazere.util.Assert;
import com.trazere.util.ObjectUtils;

/**
 * The <code>Tuple2</code> class represents the 2-tuple (pair) data type which store a sequence of 2 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 */
public class Tuple2<T1, T2> {
	/**
	 * Compare the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> int compare(final Tuple2<T1, T2> tuple1, final Tuple2<T1, T2> tuple2) {
		Assert.notNull(tuple1);
		Assert.notNull(tuple2);

		// Compare.
		final int comp1 = ObjectUtils.compare(tuple1._first, tuple2._first);
		if (0 != comp1) {
			return comp1;
		}

		return ObjectUtils.compare(tuple1._second, tuple2._second);
	}

	/** First value. May be <code>null</code>. */
	protected final T1 _first;

	/** Second value. May be <code>null</code>. */
	protected final T2 _second;

	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 */
	public Tuple2(final T1 first, final T2 second) {
		// Initialization.
		_first = first;
		_second = second;
	}

	/**
	 * Get the first value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T1 getFirst() {
		return _first;
	}

	/**
	 * Get the second value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T2 getSecond() {
		return _second;
	}

	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		if (null != _first) {
			result = result * 31 + _first.hashCode();
		}
		if (null != _second) {
			result = result * 31 + _second.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple2<?, ?> tuple = (Tuple2<?, ?>) object;
			return ObjectUtils.equals(_first, tuple._first) && ObjectUtils.equals(_second, tuple._second);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "(" + _first + "," + _second + ")";
	}
}
