/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.lang;

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link Closure} class represents closures of values over context records.
 * 
 * @param <T> Type of the value.
 * @param <C> Type of the context.
 */
public class Closure<T, C>
implements Describable {
	/**
	 * Build a new closure with the given value and context.
	 * 
	 * @param <T> Type of the value.
	 * @param <C> Type of the context.
	 * @param value Value of the closure.
	 * @param context Context of the closure.
	 * @return The built closure.
	 */
	public static <T, C> Closure<T, C> build(final T value, final C context) {
		return new Closure<T, C>(value, context);
	}
	
	/** Value of the closure. */
	private final T _value;
	
	/** Context of the closure. */
	private final C _context;
	
	/**
	 * Instantiate a new closure with the given value and parameter set.
	 * 
	 * @param value Value of the closure.
	 * @param context Context of the closure.
	 */
	public Closure(final T value, final C context) {
		assert null != value;
		assert null != context;
		
		// Initialization.
		_value = value;
		_context = context;
	}
	
	/**
	 * Get the value of the receiver closure.
	 * 
	 * @return The value.
	 */
	public T getValue() {
		return _value;
	}
	
	/**
	 * Get the context record of the receiver closure.
	 * 
	 * @return The context.
	 */
	public C getContext() {
		return _context;
	}
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_value);
		result.append(_context);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Closure<?, ?> closure = (Closure<?, ?>) object;
			return _value.equals(closure._value) && _context.equals(closure._context);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Value = ").append(_value);
		builder.append(" - Context = ").append(_context);
	}
}
