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
 * The {@link AbstractParameterSetBuilder} abstract class implements skeleton of builders of {@link ParameterSet parameter sets}.
 * 
 * @param <T> Type of the parameter values.
 * @param <S> Type of the parameter sets.
 */
public abstract class AbstractParameterSetBuilder<T, S extends ParameterSet<T>>
implements ParameterSetBuilder<T, S>, Describable {
	/** Values of the parameters identified by their names. */
	final Map<String, T> _parameters;
	
	/**
	 * Instantiate a new empty builder.
	 */
	public AbstractParameterSetBuilder() {
		// Initialization.
		_parameters = new HashMap<String, T>();
	}
	
	/**
	 * Instantiate a new builder populated with the parameters of the given set.
	 * 
	 * @param parameters Parameter set containing the initial parameter the new builder.
	 * @throws ParameterException When the parameters cannot be read.
	 */
	public AbstractParameterSetBuilder(final ParameterSet<? extends T> parameters)
	throws ParameterException {
		// Initialization.
		_parameters = new HashMap<String, T>(parameters.asMap());
	}
	
	/**
	 * Instantiate a new builder populated with the parameters of the given builder.
	 * 
	 * @param builder Builder containing the initial parameters of the new builder.
	 * @throws ParameterException When the builder cannot be read.
	 */
	public AbstractParameterSetBuilder(final ParameterSetBuilder<? extends T, ?> builder)
	throws ParameterException {
		Assert.notNull(builder);
		
		// Initialization.
		_parameters = new HashMap<String, T>();
		builder.populate(this);
	}
	
	/**
	 * Instantiate a new builder populated with the given parameters.
	 * 
	 * @param parameters Values of the initial parameters identified by their names.
	 */
	public AbstractParameterSetBuilder(final Map<String, ? extends T> parameters) {
		Assert.notNull(parameters);
		
		// Initialization.
		_parameters = new HashMap<String, T>(parameters);
	}
	
	public void add(final String name, final T value)
	throws DuplicateParameterException {
		Assert.notNull(name);
		
		// Add the parameter.
		if (!_parameters.containsKey(name)) {
			_parameters.put(name, value);
		} else {
			throw new DuplicateParameterException("Parameter \"" + name + "\" already exists in builder " + this);
		}
	}
	
	public void addAll(final ParameterSet<? extends T> parameters)
	throws ParameterException {
		Assert.notNull(parameters);
		
		// Add the parameters.
		addAll(parameters.asMap());
	}
	
	public void addAll(final Map<String, ? extends T> parameters)
	throws ParameterException {
		Assert.notNull(parameters);
		
		// Add the fields.
		for (final Map.Entry<String, ? extends T> entry : parameters.entrySet()) {
			final String name = entry.getKey();
			if (!_parameters.containsKey(name)) {
				_parameters.put(name, entry.getValue());
			} else {
				throw new DuplicateParameterException("Parameter \"" + name + "\" already exists in builder " + this);
			}
		}
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
		return Collections.unmodifiableSet(_parameters.keySet());
	}
	
	public void remove(final String name)
	throws ParameterException {
		Assert.notNull(name);
		
		// Remove the parameter.
		if (_parameters.containsKey(name)) {
			_parameters.remove(name);
		} else {
			throw new MissingParameterException("Parameter \"" + name + "\" does not exist in builder " + this);
		}
	}
	
	public void clear() {
		// Remove all parameters.
		_parameters.clear();
	}
	
	public <B extends ParameterSetBuilder<? super T, ?>> B populate(final B builder)
	throws ParameterException {
		Assert.notNull(builder);
		
		// Fill.
		builder.addAll(Collections.unmodifiableMap(_parameters));
		
		return builder;
	}
	
	public <B extends ParameterSetBuilder<? super T, ?>> B populate(final B builder, final Set<String> names)
	throws ParameterException {
		Assert.notNull(builder);
		Assert.notNull(names);
		
		// Fill.
		for (final String name : names) {
			if (_parameters.containsKey(name)) {
				builder.add(name, _parameters.get(name));
			} else {
				throw new MissingParameterException("Parameter \"" + name + "\" does not exist in builder " + this);
			}
		}
		
		return builder;
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
