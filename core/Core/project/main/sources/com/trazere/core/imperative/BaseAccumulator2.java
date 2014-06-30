/*
 *  Copyright 2006-2013 Julien Dufour
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
 * The {@link BaseAccumulator2} class provides a skeleton implementation of {@link Accumulator2 pair accumulators}.
 * 
 * @param <A1> Type of the first value of the pairs.
 * @param <A2> Type of the second value of the pairs.
 * @param <S> Type of the states.
 */
public abstract class BaseAccumulator2<A1, A2, S>
implements Accumulator2<A1, A2, S> {
	// Accumulator.
	
	@Override
	public void add(final Tuple2<? extends A1, ? extends A2> value) {
		add(value.get1(), value.get2());
	}
	
	@Override
	public void addAll(final Iterable<? extends Tuple2<? extends A1, ? extends A2>> values) {
		for (final Tuple2<? extends A1, ? extends A2> value : values) {
			add(value);
		}
	}
	
	// Procedure.
	
	@Override
	public void execute(final A1 value1, final A2 value2) {
		add(value1, value2);
	}
}
