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
package com.trazere.util.parameter;

import com.trazere.util.Assert;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleParameterSet} class implement simple parameter sets.
 * 
 * @param <T> Type of the parameter values.
 */
public class SimpleParameterSet<T>
implements ParameterSet<T>, Describable {
	/** Empty parameter set. */
	private static final SimpleParameterSet<?> EMPTY = new SimpleParameterSet<Object>(Collections.<String, Object>emptyMap());
	
	/**
	 * Build an empty parameter set.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <T> Type of the parameter values.
	 * @return The parameter set.
	 * @see #EMPTY
	 */
	@SuppressWarnings("unchecked")
	public static <T> SimpleParameterSet<T> build() {
		return (SimpleParameterSet<T>) EMPTY;
	}
	
	/**
	 * Build a one parameter set with the given name and value.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param name Name of the parameter.
	 * @param value Value of the parameter. May be <code>null</code>.
	 * @return The parameter set.
	 */
	public static <T> SimpleParameterSet<T> build(final String name, final T value) {
		Assert.notNull(name);
		
		// Build.
		final Map<String, T> parameters = new HashMap<String, T>();
		parameters.put(name, value);
		return new SimpleParameterSet<T>(parameters);
	}
	
	/**
	 * Build a two parameter set with the given names and values.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param name1 Name of the first parameter.
	 * @param value1 Value of the first parameter. May be <code>null</code>.
	 * @param name2 Name of the second parameter.
	 * @param value2 Value of the second parameter. May be <code>null</code>.
	 * @return The parameter set.
	 */
	public static <T> SimpleParameterSet<T> build(final String name1, final T value1, final String name2, final T value2) {
		Assert.notNull(name1);
		Assert.notNull(name2);
		
		// Build.
		final Map<String, T> parameters = new HashMap<String, T>();
		parameters.put(name1, value1);
		parameters.put(name2, value2);
		return new SimpleParameterSet<T>(parameters);
	}
	
	/**
	 * Build a parameter set with the given names and values.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param values Value of the parameters identified by their names.
	 * @return The built parameter set.
	 */
	public static <T> SimpleParameterSet<T> build(final Map<String, ? extends T> values) {
		return new SimpleParameterSet<T>(new HashMap<String, T>(values));
	}
	
	/** Values of the parameters identified by their names. */
	protected final Map<String, T> _parameters;
	
	/**
	 * Instantiate a new parameter set backed by the given map.
	 * 
	 * @param parameters Value of the parameters identified by their names.
	 */
	protected SimpleParameterSet(final Map<String, T> parameters) {
		// Initialization.
		_parameters = Collections.unmodifiableMap(parameters);
	}
	
	public boolean isEmpty() {
		return _parameters.isEmpty();
	}
	
	public boolean contains(final String name) {
		Assert.notNull(name);
		
		// Test.
		return _parameters.containsKey(name);
	}
	
	public Set<String> getNames() {
		return _parameters.keySet();
	}
	
	public T get(final String name)
	throws MissingParameterException {
		Assert.notNull(name);
		
		// Get.
		if (_parameters.containsKey(name)) {
			return _parameters.get(name);
		} else {
			throw new MissingParameterException("Missing \"" + name + "\" parameter from set " + this);
		}
	}
	
	public T get(final String name, final T defaultValue) {
		Assert.notNull(name);
		
		// Get.
		if (_parameters.containsKey(name)) {
			return _parameters.get(name);
		} else {
			return defaultValue;
		}
	}
	
	public <C extends T> C getTyped(final String name, final Class<C> type)
	throws MissingParameterException, IncompatibleParameterException {
		Assert.notNull(name);
		Assert.notNull(type);
		
		// Get.
		if (_parameters.containsKey(name)) {
			final T value = _parameters.get(name);
			if (null == value) {
				return null;
			} else if (type.isInstance(value)) {
				return type.cast(value);
			} else {
				throw new IncompatibleParameterException("Value " + value + " of parameter " + name + " is not compatible with type " + type + " from set " + this);
			}
		} else {
			throw new MissingParameterException("Missing \"" + name + "\" parameter from set " + this);
		}
	}
	
	public <C extends T> C getTyped(final String name, final Class<C> type, final C defaultValue)
	throws IncompatibleParameterException {
		Assert.notNull(name);
		Assert.notNull(type);
		
		// Get.
		if (_parameters.containsKey(name)) {
			final T value = _parameters.get(name);
			if (null == value) {
				return null;
			} else if (type.isInstance(value)) {
				return type.cast(value);
			} else {
				throw new IncompatibleParameterException("Value " + value + " of parameter " + name + " is not compatible with type " + type + " from set " + this);
			}
		} else {
			return defaultValue;
		}
	}
	
	public <C extends T> C getTyped(final ParameterSignature<C> signature)
	throws MissingParameterException, IncompatibleParameterException {
		Assert.notNull(signature);
		
		// Get.
		return getTyped(signature.getName(), signature.getType());
	}
	
	public <C extends T> C getTyped(final ParameterSignature<C> signature, final C defaultValue)
	throws IncompatibleParameterException {
		Assert.notNull(signature);
		
		// Get.
		return getTyped(signature.getName(), signature.getType(), defaultValue);
	}
	
	public Map<String, T> asMap() {
		return _parameters;
	}
	
	@Override
	public int hashCode() {
		return _parameters.hashCode();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SimpleParameterSet<?> parameters = (SimpleParameterSet<?>) object;
			return _parameters.equals(parameters._parameters);
		} else {
			return false;
		}
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		for (final Map.Entry<String, T> entry : _parameters.entrySet()) {
			builder.append(" - ").append(entry.getKey()).append(" = ").append(entry.getValue());
		}
	}
}
