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
 * The <code>RemoveParameterTransformer</code> class represents parameter transformers which remove single parameters.
 * 
 * @param <T> Type of the parameter values.
 */
public class RemoveParameterTransformer<T>
implements ParameterTransformer<T> {
	/** Name of the parameter to remove. */
	protected final String _name;
	
	/**
	 * Build a new transformer using the given parameters.
	 * 
	 * @param name Name of the parameter to remove.
	 */
	public RemoveParameterTransformer(final String name) {
		// Checks.
		Assert.notNull(name);
		
		// Initialization.
		_name = name;
	}
	
	public void apply(final Parameters<T> parameters, final Parameters.Builder<T> builder)
	throws ParameterException {
		// Remove.
		builder.remove(_name);
	}
}
