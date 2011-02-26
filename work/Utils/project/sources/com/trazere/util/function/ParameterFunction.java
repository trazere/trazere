/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.function;

import com.trazere.util.record.Record;

/**
 * The {@link Parametrable} interface defines functions which take a record of parameters as argument.
 * 
 * @param <K> Type of the keys of the parameters.
 * @param <V> Type of the values of the parameters.
 * @param <T> Type of the result values.
 * @param <X> Type of the exceptions.
 */
public interface ParameterFunction<K, V, T, X extends Exception>
extends Function1<Record<K, V>, T, X>, Parametrable<K, V> {
	// Nothing to do.
}
