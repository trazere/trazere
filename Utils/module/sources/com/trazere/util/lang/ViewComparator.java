package com.trazere.util.lang;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// TODO: add an option to disable memoization
// TODO: add a argument for the comparator to use
// TODO: getValue should return a Maybe

/**
 * The {@link ViewComparator} abstract class is a comparator working according some value computed from the compared objects. It applies some kind of view over
 * the compared objects.
 * <p>
 * This class can cache the computed values in order to improved the performances. The computed values as well as the compared objects must be immutable in
 * order to use this cache.
 * 
 * @param <T> Type of the compared objets.
 * @param <V> Type of the values to compore.
 */
public abstract class ViewComparator<T, V extends Comparable<V>>
implements Comparator<T> {
	/** Flag indicating wether the cache if enabled or not. */
	protected final boolean _cache;
	
	/** Cached values identified by the objects they where computed from. */
	protected final Map<T, V> _values = new HashMap<T, V>();
	
	/**
	 * Instantiate a new comparator.
	 * 
	 * @param cache Flag indicating wether the cache if enabled or not.
	 */
	public ViewComparator(final boolean cache) {
		// Initialization.
		_cache = cache;
	}
	
	public int compare(final T object1, final T object2) {
		final V value1 = _cache ? getValue(object1) : computeValue(object1);
		final V value2 = _cache ? getValue(object2) : computeValue(object2);
		return LangUtils.compare(value1, value2);
	}
	
	/**
	 * Get the value to use for comparison for the given object. This methods uses and populates the cache.
	 * 
	 * @param object Compared object whose comparison is retrieved. May be <code>null</code>.
	 * @return The comparison value. May be <code>null</code>.
	 * @throws CannotComputeValueException When the value could not be computed.
	 */
	protected V getValue(final T object)
	throws CannotComputeValueException {
		// Read the cache.
		final V cachedValue = _values.get(object);
		if (null != cachedValue) {
			return cachedValue;
		}
		
		// Compute the value.
		final V value = computeValue(object);
		
		// Fill the cache.
		_values.put(object, value);
		
		return value;
	}
	
	/**
	 * Compute the value to use for comparison for the given object.
	 * 
	 * @param object Compared object whose comparison is retrieved. May be <code>null</code>.
	 * @return The comparison value. May be <code>null</code>.
	 * @throws CannotComputeValueException When the value could not be computed.
	 */
	protected abstract V computeValue(final T object)
	throws CannotComputeValueException;
	
	public void flushCache() {
		_values.clear();
	}
}
