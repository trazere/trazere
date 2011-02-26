/*
 *  Copyright 2006-2011 Julien Dufour
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
package com.trazere.util.collection;

import com.trazere.util.lang.AbstractFactory;
import java.util.Map;

/**
 * The {@link AbstractMapFactory} class implements skeletons of {@link MapFactory map factories}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <M> Type of the maps.
 */
public abstract class AbstractMapFactory<K, V, M extends Map<? super K, ? super V>>
extends AbstractFactory<M, RuntimeException>
implements MapFactory<K, V, M> {
	// Nothing to do.
}
