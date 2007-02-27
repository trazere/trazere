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
 * The <code>RemoveParameterTransformer</code> class represents parameter transformers which remove single parameters.
 */
public class RemoveParameterTransformer
implements ParameterTransformer {
	/** Name of the parameter to remove. */
	protected final String _name;

	/** Flag indicating wether the deletion is strict or not. Strict deletion require the removed parameters to exist. */
	protected final boolean _strict;

	/**
	 * Build a new transformer using the given parameters.
	 * 
	 * @param name Name of the parameter to remove.
	 * @param strict Flag indicating wether the deletion is strict or not.
	 */
	public RemoveParameterTransformer(final String name, final boolean strict) {
		// Checks.
		Assert.notNull(name);

		// Initialization.
		_name = name;
		_strict = strict;
	}

	public void apply(final Parameters<Object> parameters, final Parameters.Builder<Object> builder)
	throws ParameterException {
		// Remove.
		if (_strict) {
			builder.remove(_name);
		} else {
			builder.clear(_name);
		}
	}
}
