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

import com.trazere.util.lang.Factory;
import java.util.Collection;

/**
 * The {@link CollectionFactory} interface defines factories which build collections.
 * 
 * @param <T> Type of the elements.
 * @param <C> Type of the collections.
 */
public interface CollectionFactory<T, C extends Collection<? super T>>
extends Factory<C, RuntimeException> {
	public C build();
	
	public C build(final int capacity);
	
	public C build(final Collection<? extends T> values);
}
