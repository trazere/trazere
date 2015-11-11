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
package com.trazere.core.imperative;

import com.trazere.core.util.Tuple2;

/**
 * The {@link LookAheadPairIterator} class implements iterators that look ahead the next pair of elements.
 * 
 * @param <E1> Type of the first element of the pairs.
 * @param <E2> Type of the second element of the pairs.
 * @since 2.0
 */
public abstract class LookAheadPairIterator<E1, E2>
extends LookAheadIterator<Tuple2<E1, E2>>
implements PairIterator<E1, E2> {
	// Nothing to do.
}
