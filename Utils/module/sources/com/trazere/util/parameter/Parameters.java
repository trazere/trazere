/*
 *  Copyright 2006 Julien Dufour
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.trazere.util.Assert;
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.text.TextUtils;

/**
 * The <code>Parameters</code> class represents collections of parameter values identified by unique names.
 * <p>
 * Parameter set are not mutable. Parameter values may not be <code>null</code>.
 * 
 * @param <T> Type of the parameter values.
 */
public class Parameters<T> {
	/**
	 * The <code>Builder</code> class provides a tool to build of parameter sets.
	 * 
	 * @param <T> Type of the parameter values.
	 */
	public static class Builder<T> {
		/** Values of the parameters identified by their names. */
		final Map<String, T> _parameters;

		/**
		 * Instanciate a new builder.
		 */
		public Builder() {
			// Initialization.
			_parameters = new HashMap<String, T>();
		}

		/**
		 * Instanciate a new builder populated with the given parameter set.
		 * 
		 * @param parameters Parameter set initially populating the builder.
		 */
		public Builder(final Parameters<? extends T> parameters) {
			Assert.notNull(parameters);

			// Initialization.
			_parameters = new HashMap<String, T>(parameters._parameters);
		}

		/**
		 * Test wether the receiver builder is empty or not. A builder is empty when parameters have been added.
		 * 
		 * @return <code>true</code> if the receiver builder is empty, <code>false</code> otherwise.
		 */
		public boolean isEmpty() {
			return _parameters.isEmpty();
		}

		/**
		 * Test wether the receiver builder contains a parameter with the given name.
		 * 
		 * @param name Name of the searched parameter.
		 * @return <code>true</code> if a parameter with the given name has been added, <code>false</code> otherwise.
		 */
		public boolean contains(final String name) {
			Assert.notNull(name);

			// Test.
			return _parameters.containsKey(name);
		}

		/**
		 * Get the value of the parameter with the given name contained in the receiver builder.
		 * 
		 * @param name Name of the parameter.
		 * @return The value of the parameter, or <code>null</code> if the builder contains no parameters with the given name.
		 */
		public T get(final String name) {
			Assert.notNull(name);

			// Get the parameter value.
			return _parameters.get(name);
		}

		/**
		 * Get the typed value of the parameter with the given name contained in the receiver builder.
		 * 
		 * @param <C> Type of the value.
		 * @param name Name of the parameter.
		 * @param type Class of the value.
		 * @return The value of the parameter, or <code>null</code> if the builder contains no parameters with the given name.
		 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the given type.
		 */
		@SuppressWarnings("unchecked")
		public <C extends T> C getTyped(final String name, final Class<C> type)
		throws IncompatibleParameterException {
			Assert.notNull(name);
			Assert.notNull(type);

			// Get the parameter value.
			final T value = _parameters.get(name);
			if (null == value || type.isInstance(value)) {
				return (C) value;
			} else {
				throw new IncompatibleParameterException("Value " + value + " of parameter " + name + " is not compatible with type " + type);
			}
		}

		/**
		 * Add a new parameter with the given key and value to the receiver builder.
		 * <p>
		 * The receiver builder must not already contain a parameter with the given name.
		 * 
		 * @param name Name of the parameter to add.
		 * @param value Value of the parameter to add. May be <code>null</code>.
		 * @throws ParameterAlreadyExistsException When a parameter with the given name has already been added.
		 */
		public void add(final String name, final T value)
		throws ParameterAlreadyExistsException {
			Assert.notNull(name);

			// Check that the parameter does not exist.
			if (_parameters.containsKey(name)) {
				throw new ParameterAlreadyExistsException("Parameter \"" + name + "\" alread exists in parameters " + _parameters);
			}

			// Add the parameter.
			_parameters.put(name, value);
		}

		/**
		 * Add the parameters of the given set to the receiver builder.
		 * <p>
		 * The receiver builder must not already contain any parameter with the names of the given parameter set.
		 * 
		 * @param parameters Parameter set to add.
		 * @throws ParameterAlreadyExistsException When a parameter with a name of the given parameter set has already been added.
		 */
		public void add(final Parameters<? extends T> parameters)
		throws ParameterAlreadyExistsException {
			Assert.notNull(parameters);

			// Check that the parameters do not exist.
			if (CollectionUtils.intersects(_parameters.keySet(), parameters._parameters.keySet())) {
				throw new ParameterAlreadyExistsException("Some parameter of " + parameters + " alread exists in parameters " + _parameters);
			}

			// Add the parameters.
			_parameters.putAll(parameters._parameters);
		}

		/**
		 * Set the parameter with the given key and value in the receiver builder.
		 * <p>
		 * A new parameter is added when the receiver builder contains no parameters with the given name. The value of the existing parameter is replaced
		 * otherwise.
		 * 
		 * @param name Name of the parameter to set.
		 * @param value Value of the parameter to set. May be <code>null</code>.
		 */
		public void set(final String name, final T value) {
			Assert.notNull(name);

			// Set the parameter.
			_parameters.put(name, value);
		}

		/**
		 * Set the parameters of the given set in the receiver builder.
		 * <p>
		 * New parameters are added when the receiver builder contains no parameters with the corresponding names. The values of the existing parameters are
		 * replaced otherwise.
		 * 
		 * @param parameters Parameter set to set.
		 */
		public void set(final Parameters<? extends T> parameters) {
			Assert.notNull(parameters);

			// Set the parameters.
			_parameters.putAll(parameters._parameters);
		}

		/**
		 * Remove the parameter with the given name from the receiver builder.
		 * <p>
		 * The receiver builder must contain a parameter with the given name.
		 * 
		 * @param name Name of the parameter to remove.
		 * @return The value of the removed parameter. May be <code>null</code>.
		 * @throws MissingParameterException When the receiver builder contains no parameters with the given name.
		 */
		public T remove(final String name)
		throws MissingParameterException {
			// Checks.
			Assert.notNull(name);

			// Check that the parameter do exist.
			if (!_parameters.containsKey(name)) {
				throw new MissingParameterException("Parameter \"" + name + "\" alread does not exist in parameters " + _parameters);
			}

			// Remove the parameter.
			return _parameters.remove(name);
		}

		/**
		 * Remove the parameter with the given name from the receiver builder.
		 * 
		 * @param name Name of the parameter to remove.
		 * @return The value of the removed parameters, or <code>null</code> if the builder contained no parameters with the given name.
		 */
		public T clear(final String name) {
			// Checks.
			Assert.notNull(name);

			// Remove the parameter.
			return _parameters.remove(name);
		}

		/**
		 * Remove the parameters with the given names from the receiver builder.
		 * 
		 * @param names Names of the parameters to remove.
		 */
		public void clear(final Collection<String> names) {
			// Checks.
			Assert.notNull(names);

			// Remove the parameters.
			for (final String name : names) {
				_parameters.remove(name);
			}
		}

		/**
		 * Remove all paramaters from the receiver builder.
		 */
		public void clear() {
			// Clear the parameters.
			_parameters.clear();
		}

		/**
		 * Build a new parameter set with the parameters of the receiver builder.
		 * 
		 * @return The built parameter set.
		 */
		public Parameters<T> build() {
			return new Parameters<T>(new HashMap<String, T>(_parameters));
		}
	}

	/**
	 * Empty parameter set.
	 */
	@SuppressWarnings("unchecked")
	protected static final Parameters EMPTY = new Parameters<Object>(Collections.EMPTY_MAP);

	/**
	 * Build an empty parameter set.
	 * <p>
	 * This method actually does not build a new objet, it returns a singleton instead.
	 * 
	 * @param <T> Type of the parameter values.
	 * @return The built parameter set.
	 * @see #EMPTY
	 */
	@SuppressWarnings("unchecked")
	public static <T> Parameters<T> build() {
		return EMPTY;
	}

	/**
	 * Build a new single parameter set with the given name and value.
	 * <p>
	 * <code>null</code> values are ignored.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param name Name of the parameter.
	 * @param value Value of the parameter. May be <code>null</code>.
	 * @return The built parameter set.
	 */
	public static <T> Parameters<T> build(final String name, final T value) {
		Assert.notNull(name);

		// Build.
		final Map<String, T> parameters = new HashMap<String, T>();
		if (null != value) {
			parameters.put(name, value);
		}
		return new Parameters<T>(parameters);
	}

	/**
	 * Build a new two parameter set with the given names and values.
	 * <p>
	 * <code>null</code> values are ignored.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param name1 Name of the first parameter.
	 * @param value1 Value of the first parameter. May be <code>null</code>.
	 * @param name2 Name of the second parameter.
	 * @param value2 Value of the second parameter. May be <code>null</code>.
	 * @return The built parameter set.
	 */
	public static <T> Parameters<T> build(final String name1, final T value1, final String name2, final T value2) {
		Assert.notNull(name1);
		Assert.notNull(name2);

		// Build.
		final Map<String, T> parameters = new HashMap<String, T>();
		if (null != value1) {
			parameters.put(name1, value1);
		}
		if (null != value2) {
			parameters.put(name2, value2);
		}
		return new Parameters<T>(parameters);
	}

	/**
	 * Build a new parameter set with the given names and values.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param values Value of the parameters identified by their names.
	 * @return The built parameter set.
	 */
	public static <T> Parameters<T> build(final Map<String, T> values) {
		return new Parameters<T>(new HashMap<String, T>(values));
	}

	/**
	 * Build a new parameter set containing the union of the parameters of the given parameter sets.
	 * <p>
	 * The parameters of the first set have precedence over the parameters of the second set when some parameters have the same names.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param parameters1 First set of parameters.
	 * @param parameters2 Second set of parameters.
	 * @return The union parameter set.
	 */
	public static <T> Parameters<T> union(final Parameters<? extends T> parameters1, final Parameters<? extends T> parameters2) {
		Assert.notNull(parameters1);
		Assert.notNull(parameters2);

		// Build.
		return new Parameters<T>(CollectionUtils.unionMap(parameters1._parameters, parameters2._parameters));
	}

	/** Values of the parameters identified by their names. */
	protected final Map<String, T> _parameters;

	protected Parameters(final Map<String, T> parameters) {
		// Initialization.
		_parameters = Collections.unmodifiableMap(parameters);
	}

	/**
	 * Test wether the receiver set contains a parameter with the given name.
	 * 
	 * @param name Name of the searched parameter.
	 * @return <code>true</code> if the set contains a parameter with the given name, <code>false</code> otherwise.
	 */
	public boolean contains(final String name) {
		Assert.notNull(name);

		// Test.
		return _parameters.containsKey(name);
	}

	/**
	 * Get the parameters of the given set as a map.
	 * 
	 * @return A unmodifiable map of the values of the paramters identified by their names.
	 */
	public Map<String, T> getParameters() {
		return _parameters;
	}

	/**
	 * Get the value of the parameter with the given name of the receiver set.
	 * 
	 * @param name Name of the parameter.
	 * @return The value of the parameter, or <code>null</code> if the set contains no parameters with the given name.
	 */
	public T getParameter(final String name) {
		Assert.notNull(name);

		// Get the parameter value.
		return _parameters.get(name);
	}

	/**
	 * Get the value of the parameter with the given name of the receiver set.
	 * 
	 * @param name Name of the parameter.
	 * @param defaultValue Default value of the parameter.
	 * @return The value of the parameter, or the given default value if the set contains no parameters with the given name.
	 */
	public T getParameter(final String name, final T defaultValue) {
		Assert.notNull(name);

		// Get the parameter value.
		final T value = _parameters.get(name);
		return null != value ? value : defaultValue;
	}

	/**
	 * Get the value of the mandatory parameter with the given name of the receiver set.
	 * 
	 * @param name Name of the parameter.
	 * @return The value of the parameter.
	 * @throws MissingParameterException When the receiver set contains no parameters with the given name.
	 */
	public T getMandatoryParameter(final String name)
	throws MissingParameterException {
		Assert.notNull(name);

		// Get the parameter value.
		final T value = _parameters.get(name);
		if (null != value) {
			return value;
		} else {
			throw new MissingParameterException("Missing \"" + name + "\" parameter from parameters " + this);
		}
	}

	/**
	 * Get the typed value of the parameter with the given name of the receiver set.
	 * 
	 * @param <C> Type of the value.
	 * @param name Name of the parameter.
	 * @param type Class of the value.
	 * @return The value of the parameter, or <code>null</code> if the set contains no parameters with the given name.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the given type.
	 */
	@SuppressWarnings("unchecked")
	public <C extends T> C getTypedParameter(final String name, final Class<C> type)
	throws IncompatibleParameterException {
		Assert.notNull(name);
		Assert.notNull(type);

		// Get the parameter value.
		final T value = _parameters.get(name);
		if (null == value || type.isInstance(value)) {
			return (C) value;
		} else {
			throw new IncompatibleParameterException("Value " + value + " of parameter " + name + " is not compatible with type " + type);
		}
	}

	/**
	 * Get the typed value of the parameter with the given name of the receiver set.
	 * 
	 * @param <C> Type of the value.
	 * @param name Name of the parameter.
	 * @param defaultValue Default value of the parameter.
	 * @param type Class of the value.
	 * @return The value of the parameter, or the given default value if the set contains no parameters with the given name.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the given type.
	 */
	@SuppressWarnings("unchecked")
	public <C extends T> C getTypedParameter(final String name, final C defaultValue, final Class<C> type)
	throws IncompatibleParameterException {
		Assert.notNull(name);

		// Get the parameter value.
		final T value = _parameters.get(name);
		if (null == value) {
			return defaultValue;
		} else if (type.isInstance(value)) {
			return (C) value;
		} else {
			throw new IncompatibleParameterException("Value " + value + " of parameter " + name + " is not compatible with type " + type);
		}
	}

	/**
	 * Get the typed value of the mandatory parameter with the given name of the receiver set.
	 * 
	 * @param <C> Type of the value.
	 * @param name Name of the parameter.
	 * @param type Class of the value.
	 * @return The value of the parameter.
	 * @throws MissingParameterException When the receiver set contains no parameters with the given name.
	 * @throws IncompatibleParameterException When the value of the parameter is not compatible with the given type.
	 */
	@SuppressWarnings("unchecked")
	public <C extends T> C getTypedMandatoryParameter(final String name, final Class<C> type)
	throws MissingParameterException, IncompatibleParameterException {
		Assert.notNull(name);

		// Get the parameter value.
		final T value = _parameters.get(name);
		if (null == value) {
			throw new MissingParameterException("Missing " + name + " parameter from parameters " + this);
		} else if (type.isInstance(value)) {
			return (C) value;
		} else {
			throw new IncompatibleParameterException("Value " + value + " of parameter " + name + " is not compatible with type " + type);
		}
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
			final Parameters<?> parameters = (Parameters<?>) object;
			return _parameters.equals(parameters._parameters);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(CollectionUtils.last(TextUtils.split(getClass().getName(), ".", true, false)));
		completeDescription(builder);
		builder.append("]");
		return builder.toString();
	}

	protected void completeDescription(final StringBuilder builder) {
		for (final Map.Entry<String, T> entry : _parameters.entrySet()) {
			builder.append(" - ");
			builder.append(entry.getKey());
			builder.append(" = ");
			builder.append(entry.getValue());
		}
	}
}
