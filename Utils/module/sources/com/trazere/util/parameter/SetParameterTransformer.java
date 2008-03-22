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

/**
 * The <code>SetParameterTransformer</code> class represents parameter transformers which set single parameters.
 * 
 * @param <T> Type of the parameter values.
 */
public class SetParameterTransformer<T>
implements ParameterTransformer<T> {
	/** Name of the parameter to set. */
	protected final String _name;
	
	/** Flag indicating wether the affectation is strict or not. Strict affectations may not overwrite existing parameters. */
	protected final boolean _strict;
	
	/** Value of the parameter to set. May be <code>null</code>. */
	protected final T _value;
	
	/**
	 * Build a new transformer using the given parameters.
	 * 
	 * @param name Name of the parameter to set.
	 * @param strict Flag indicating wether the affectation is strict or not.
	 * @param value Value of the parameter to set. May be <code>null</code>.
	 */
	public SetParameterTransformer(final String name, final boolean strict, final T value) {
		// Checks.
		Assert.notNull(name);
		
		// Initialization.
		_name = name;
		_value = value;
		_strict = strict;
	}
	
	public void apply(final Parameters<T> parameters, final Parameters.Builder<T> builder)
	throws ParameterException {
		// Set.
		if (_strict) {
			builder.add(_name, _value);
		} else {
			builder.set(_name, _value);
		}
	}
}
