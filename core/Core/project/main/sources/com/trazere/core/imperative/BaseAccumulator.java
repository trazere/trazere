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

/**
 * The {@link BaseAccumulator} class provides a skeleton implementation of {@link Accumulator accumulators}.
 * 
 * @param <A> Type of the values.
 * @param <S> Type of the states.
 */
public abstract class BaseAccumulator<A, S>
implements Accumulator<A, S> {
	// Accumulator.
	
	@Override
	public void addAll(final Iterable<? extends A> values) {
		for (final A value : values) {
			add(value);
		}
	}
	
	// Procedure.
	
	@Override
	public void execute(final A value) {
		add(value);
	}
}
