package com.trazere.util.type;

import com.trazere.util.Assert;
import com.trazere.util.ObjectUtils;

/**
 * The <code>Tuple3</code> class represents the 3-tuple (triplet) data type which store a sequence of 3 values.
 * 
 * @param <T1> Type of the first value.
 * @param <T2> Type of the second value.
 * @param <T3> Type of the third value.
 */
public class Tuple3<T1, T2, T3> {
	/**
	 * Compare the given tuples.
	 * <p>
	 * The comparison is performed by comparing the respective values of the tuples in sequence.
	 * 
	 * @param <T1> Type of the first values.
	 * @param <T2> Type of the second values.
	 * @param <T3> Type of the third values.
	 * @param tuple1 First tuple.
	 * @param tuple2 Second tuple.
	 * @param tuple3 Third tuple.
	 * @return The result of the comparison as defined by the {@link Comparable#compareTo(Object)} method.
	 * @see Comparable#compareTo(Object)
	 */
	public static <T1 extends Comparable, T2 extends Comparable, T3 extends Comparable> int compare(final Tuple3<T1, T2, T3> tuple1, final Tuple3<T1, T2, T3> tuple2, final Tuple3<T1, T2, T3> tuple3) {
		Assert.notNull(tuple1);
		Assert.notNull(tuple2);
		Assert.notNull(tuple3);

		// Compare.
		final int comp1 = ObjectUtils.compare(tuple1._first, tuple2._first);
		if (0 != comp1) {
			return comp1;
		}

		final int comp2 = ObjectUtils.compare(tuple1._second, tuple2._second);
		if (0 != comp2) {
			return comp2;
		}

		return ObjectUtils.compare(tuple1._third, tuple2._third);
	}

	/** First value. May be <code>null</code>. */
	protected final T1 _first;

	/** Second value. May be <code>null</code>. */
	protected final T2 _second;

	/** Third value. May be <code>null</code>. */
	protected final T3 _third;

	/**
	 * Build a new instance with the given values.
	 * 
	 * @param first First value. May be <code>null</code>.
	 * @param second Second value. May be <code>null</code>.
	 * @param third Third value. May be <code>null</code>.
	 */
	public Tuple3(final T1 first, final T2 second, final T3 third) {
		// Initialization.
		_first = first;
		_second = second;
		_third = third;
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

	/**
	 * Get the third value of the receiver tuple.
	 * 
	 * @return The value. May be <code>null</code>.
	 */
	public T3 getThird() {
		return _third;
	}

	public int hashCode() {
		int result = getClass().hashCode();
		if (null != _first) {
			result = result * 31 + _first.hashCode();
		}
		if (null != _second) {
			result = result * 31 + _second.hashCode();
		}
		if (null != _third) {
			result = result * 31 + _third.hashCode();
		}
		return result;
	}

	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple3<?, ?, ?> tuple = (Tuple3<?, ?, ?>) object;
			return ObjectUtils.equals(_first, tuple._first) && ObjectUtils.equals(_second, tuple._second) && ObjectUtils.equals(_third, tuple._third);
		} else {
			return false;
		}
	}

	public String toString() {
		return "(" + _first + "," + _second + "," + _third + ")";
	}
}
