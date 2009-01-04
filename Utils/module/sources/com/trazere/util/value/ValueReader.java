/*
 *  Copyright 2006-2008 Julien Dufour
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
package com.trazere.util.value;

import com.trazere.util.function.ParameterFunction;
import com.trazere.util.record.Record;

/**
 * The {@link ValueReader} interface defines value reading functions.
 * 
 * @param <T> Type of the values.
 */
public interface ValueReader<T>
extends ParameterFunction<String, Object, T, ValueException> {
	/**
	 * Get the type of the values produced by the receiver reader.
	 * 
	 * @return The Java type of the values.
	 */
	public Class<T> getType();
	
	/**
	 * Read the value defined by the receiver reader using the given parameters.
	 * 
	 * @param parameters Parameters to use.
	 * @return The produced value. May be <code>null</code>.
	 * @throws ValueException When the value cannot be read.
	 */
	public T read(final Record<String, Object> parameters)
	throws ValueException;
}
