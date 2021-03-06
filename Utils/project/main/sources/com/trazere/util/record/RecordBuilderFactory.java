/*
 *  Copyright 2006-2015 Julien Dufour
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
package com.trazere.util.record;

import com.trazere.util.lang.Factory;

/**
 * The {@link RecordBuilderFactory} interface defines factories of {@link RecordBuilder record builders}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @param <B> Type of the record builders.
 * @see RecordBuilder
 * @deprecated To be removed.
 */
@Deprecated
public interface RecordBuilderFactory<K, V, R extends Record<K, V>, B extends RecordBuilder<K, V, R>>
extends Factory<B, RecordException> {
	/**
	 * Builds a new empty record builder.
	 * 
	 * @return The built record builder.
	 * @throws RecordException When the record builder cannot be built.
	 * @deprecated To be removed.
	 */
	@Deprecated
	@Override
	public B build()
	throws RecordException;
}
