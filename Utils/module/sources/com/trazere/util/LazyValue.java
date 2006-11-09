package com.trazere.util;

/**
 * The <code>LazyValue</code> class represents values which are computed lazily.
 * <p>
 * This class may be used to simulate call-by-name or call-by-need evaluations.
 * 
 * @param <T> Type of the value.
 */
public abstract class LazyValue<T> {
	/** Flag indicating wether the computed value should be memoized (call-by-need evaluation) or not (call-by-name evaluation). */
	protected final boolean _memoize;

	/** Flag indicating wether the value has been memoized or not. */
	protected boolean _memoized;

	/** The last computed value. May be <code>null</code>. */
	protected T _value;

	/**
	 * Build a new lazy value.
	 * 
	 * @param memoize Flag indicating wether the computed value should be memoized (call-by-need) or not (call-by-name).
	 */
	public LazyValue(final boolean memoize) {
		// Initialization.
		_memoize = memoize;
		_memoized = false;
		_value = null;
	}

	/**
	 * Build a new memoized lazy value.
	 * <p>
	 * This constructor provide an adapter for lazy values since the built instance is not lazy.
	 * 
	 * @param value Value to memoize. May be <code>null</code>.
	 */
	public LazyValue(final T value) {
		// Initialization.
		_memoize = true;
		_memoized = true;
		_value = value;
	}

	/**
	 * Indicate wether the computed value should be memoized (call-by-need evaluation) or not (call-by-name evaluation).
	 * 
	 * @return <code>true</code> if the computed value should be memoized, <code>false</code> otherwise.
	 */
	public boolean shouldMemoize() {
		return _memoize;
	}

	/**
	 * Indicate wether a value has been memoized or not.
	 * 
	 * @return <code>true</code> if a value has been memoized, <code>false</code> otherwise.
	 */
	public boolean isMemoized() {
		return _memoized;
	}

	/**
	 * Get the lazy value.
	 * <p>
	 * The value is computed if needed (if it has not been memoized), and memoized if needed.
	 * 
	 * @return The computed value.
	 * @throws CannotComputeValueException When the computation of the value fails.
	 */
	public T get()
	throws CannotComputeValueException {
		// Compute the value if needed.
		if (!_memoized) {
			_value = computeValue();

			// Memoize the value if needed.
			if (_memoize) {
				_memoized = true;
			}
		}

		return _value;
	}

	/**
	 * Compute the lazy value.
	 * 
	 * @return The computed value.
	 * @throws CannotComputeValueException When the computation fails.
	 */
	protected abstract T computeValue()
	throws CannotComputeValueException;

	public String toString() {
		if (_memoized) {
			return null != _value ? _value.toString() : "null";
		} else {
			return "lazy_value";
		}
	}
}
