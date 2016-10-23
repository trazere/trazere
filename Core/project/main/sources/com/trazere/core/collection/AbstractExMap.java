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
package com.trazere.core.collection;

import java.util.AbstractMap;

/**
 * The {@link AbstractExMap} class provides a skeleton implementation of {@link ExMap extended maps}.
 * 
 * @param <K> Type of keys.
 * @param <V> Type of values.
 * @since 2.0
 */
public abstract class AbstractExMap<K, V>
extends AbstractMap<K, V>
implements ExMap<K, V> {
	/**
	 * Instantiates a new map.
	 * 
	 * @since 2.0
	 */
	protected AbstractExMap() {
		super();
	}
	
	// Map.
	
	@Override
	public ExSet<K> keySet() {
		return ExSet.build(super.keySet());
	}
	
	@Override
	public ExCollection<V> values() {
		return ExCollection.build(super.values());
	}
}
