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

import java.util.Set;

public class ParameterUtils {
	/**
	 * Build a new parameter set containing the union of the given parameter sets.
	 * <p>
	 * The names of the parameters of the given sets should not overlap.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param parameters1 First set of parameters.
	 * @param parameters2 Second set of parameters.
	 * @return The union parameter set.
	 * @throws DuplicateParameterException When the given sets both contain parameters identified by the name.
	 * @throws ParameterException When the union parameter cannot be built.
	 */
	public static <T> ParameterSet<T> union(final ParameterSet<? extends T> parameters1, final ParameterSet<? extends T> parameters2)
	throws ParameterException {
		final SimpleParameterSetBuilder<T> builder = new SimpleParameterSetBuilder<T>(parameters1);
		builder.addAll(parameters2);
		return builder.build();
	}
	
	/**
	 * Fill the given builder with the parameters of the given parameter set identified by the given names.
	 * <p>
	 * The given parameter set must contains parameters identified by all the given names and the given builder must not contain parameters identified by the
	 * given names.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param parameters Parameters to read.
	 * @param names Names of the parameter to keep.
	 * @param builder Builder to fill.
	 * @throws MissingParameterException When the parameter set does not contains some parameters identified by the given names.
	 * @throws DuplicateParameterException When the builder contains parameters identified by the given names.
	 * @throws ParameterException When the sub set cannot be built.
	 */
	public static <T> void subSet(final ParameterSet<? extends T> parameters, final Set<String> names, final ParameterSetBuilder<? super T, ?> builder)
	throws ParameterException {
		assert null != parameters;
		assert null != names;
		assert null != builder;
		
		// Fill the builder.
		for (final String key : names) {
			builder.add(key, parameters.get(key));
		}
	}
	
	private ParameterUtils() {
		// Prevent instantiation.
	}
}
