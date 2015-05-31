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
package com.trazere.util.collection;

import com.trazere.util.lang.BaseFactory;
import java.util.Map;

/**
 * The {@link BaseMapFactory} abstract class provides a skeleton implementation of {@link MapFactory map factories}.
 * 
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @param <M> Type of the maps.
 * @deprecated Use {@link com.trazere.core.collection.MapFactory}.
 */
@Deprecated
public abstract class BaseMapFactory<K, V, M extends Map<? super K, ? super V>>
extends BaseFactory<M, RuntimeException>
implements MapFactory<K, V, M> {
	// Nothing to do.
}
