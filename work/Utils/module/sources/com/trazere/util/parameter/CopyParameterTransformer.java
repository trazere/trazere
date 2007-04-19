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

import com.trazere.util.Assert;

/**
 * The <code>CopyParameterTransformer</code> class represents parameter transformers which copy single parameters.
 * 
 * @param <T> Type of the parameter values.
 */
public class CopyParameterTransformer<T>
implements ParameterTransformer<T> {
	/** Name of the parameter to set. */
	protected final String _name;

	/** Flag indicating wether the copy is strict or not. Strict copies may not overwrite existing parameters. */
	protected final boolean _strict;

	/** Name of the parameter to copy. */
	protected final String _source;

	/**
	 * Build a new transformer using the given parameters.
	 * 
	 * @param name Name of the parameter to set.
	 * @param strict Flag indicating wether the copy is strict or not.
	 * @param source Name of the parameter to copy.
	 */
	public CopyParameterTransformer(final String name, final boolean strict, final String source) {
		// Checks.
		Assert.notNull(name);
		Assert.notNull(source);

		// Initialization.
		_name = name;
		_strict = strict;
		_source = source;
	}

	public void apply(final Parameters<T> parameters, final Parameters.Builder<T> builder)
	throws ParameterException {
		// Copy.
		final T value = builder.get(_source);
		if (_strict) {
			builder.add(_name, value);
		} else {
			builder.set(_name, value);
		}
	}
}
