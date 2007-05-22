package com.trazere.util;

import java.util.Comparator;
import java.util.List;

/**
 * The {@link MultipleComparator} class is a comparator combinator which compares according to multiple criterion.
 * <p>
 * The comparator applies the given comparators in order until one of them orders the given objects.
 * 
 * @param <T> Type of the compared objects.
 */
public class MultipleComparator<T>
implements Comparator<T> {
	/** Comparators to apply ordered by priority. */
	protected final Comparator<T>[] _comparators;
	
	/**
	 * Instantiate a new multiple criterion comparator with the given comparators.
	 * 
	 * @param comparators Comparators to apply ordered by priority.
	 */
	public MultipleComparator(final List<? extends Comparator<T>> comparators) {
		// Checks.
		Assert.notNull(comparators);
		
		// Initialization.
		@SuppressWarnings("unchecked")
		final Comparator<T>[] comparators_ = new Comparator[comparators.size()];
		comparators.toArray(comparators_);
		_comparators = comparators_;
	}
	
	public int compare(final T object1, final T object2) {
		for (final Comparator<T> comparator : _comparators) {
			final int result = comparator.compare(object1, object2);
			if (0 != result) {
				return result;
			}
		}
		return 0;
	}
	
	@Override
	public int hashCode() {
		int result = getClass().hashCode();
		result = result * 31 + _comparators.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final MultipleComparator<?> comparator = (MultipleComparator<?>) object;
			return _comparators.equals(comparator._comparators);
		} else {
			return false;
		}
	}
}
