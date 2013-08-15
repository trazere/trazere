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
package com.trazere.util.accumulator;

/**
 * The {@link BaseAccumulator2} abstract class provides a skeleton implementation of {@link Accumulator2 pair accumulators}.
 * 
 * @param <T1> Type of the first values.
 * @param <T2> Type of the second values.
 * @param <S> Type of the states.
 * @param <X> Type of the exceptions.
 */
public abstract class BaseAccumulator2<T1, T2, S, X extends Exception>
implements Accumulator2<T1, T2, S, X> {
	// Procedure.
	
	@Override
	public void execute(final T1 value1, final T2 value2)
	throws X {
		add(value1, value2);
	}
}
