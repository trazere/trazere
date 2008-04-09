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

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link SimpleParameterSetBuilder} class implements builders of {@link SimpleParameterSet simple parameter sets}.
 * 
 * @param <T> Type of the parameter values.
 */
public class SimpleParameterSetBuilder<T>
extends AbstractParameterSetBuilder<T, SimpleParameterSet<T>> {
	/**
	 * Instantiate a new empty builder.
	 */
	public SimpleParameterSetBuilder() {
		super();
	}
	
	/**
	 * Instantiate a new builder populated with the parameters of the given set.
	 * 
	 * @param parameters Parameter set containing the initial parameter the new builder.
	 * @throws ParameterException When the parameters cannot be read.
	 */
	public SimpleParameterSetBuilder(final ParameterSet<? extends T> parameters)
	throws ParameterException {
		super(parameters);
	}
	
	/**
	 * Instantiate a new builder populated with the parameters of the given builder.
	 * 
	 * @param builder Builder containing the initial parameters of the new builder.
	 * @throws ParameterException When the builder cannot be read.
	 */
	public SimpleParameterSetBuilder(final ParameterSetBuilder<? extends T, ?> builder)
	throws ParameterException {
		super(builder);
	}
	
	/**
	 * Instantiate a new builder populated with the given parameters.
	 * 
	 * @param parameters Values of the initial parameters identified by their names.
	 */
	public SimpleParameterSetBuilder(final Map<String, ? extends T> parameters) {
		super(parameters);
	}
	
	public SimpleParameterSet<T> build() {
		return new SimpleParameterSet<T>(new HashMap<String, T>(_parameters));
	}
}
