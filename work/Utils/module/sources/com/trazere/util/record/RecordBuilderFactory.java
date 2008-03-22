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
package com.trazere.util.record;

/**
 * The {@link RecordBuilderFactory} defines factories of record builders.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <R> Type of the records.
 * @param <B> Type of the record builders.
 */
public interface RecordBuilderFactory<K, V, R extends Record<K, V>, B extends RecordBuilder<K, V, R>> {
	/**
	 * Build a new empty record builder.
	 * 
	 * @return The built record builder.
	 * @throws RecordException
	 */
	public B build()
	throws RecordException;
}
