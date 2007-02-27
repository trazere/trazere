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

/**
 * The <code>ParameterTransformer</code> interface defines transformations of parameters.
 */
public interface ParameterTransformer {
	/**
	 * Apply the transformation to the given parameter builder.
	 * 
	 * @param parameters Source parameter set being transformed as reference.
	 * @param builder Parameter builder to transform.
	 * @throws ParameterException When some error occurs.
	 */
	public void apply(final Parameters<Object> parameters, final Parameters.Builder<Object> builder)
	throws ParameterException;
}
