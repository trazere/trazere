/*
 *  Copyright 2006-2012 Julien Dufour
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
import java.util.Collection;

/**
 * The {@link BaseCollectionFactory} abstract class provides a skeleton implementation of {@link CollectionFactory collection factories}.
 * 
 * @param <T> Type of the elements.
 * @param <C> Type of the collections.
 */
public abstract class BaseCollectionFactory<T, C extends Collection<? super T>>
extends BaseFactory<C, RuntimeException>
implements CollectionFactory<T, C> {
	// Nothing to do.
}
