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

import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple2;
import java.util.Collection;

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
	// Accumulator.
	
	@Override
	public void add(final Tuple2<? extends T1, ? extends T2> value)
	throws X {
		assert null != value;
		
		add(value.getFirst(), value.getSecond());
	}
	
	@Override
	public void add(final Maybe<? extends Tuple2<? extends T1, ? extends T2>> value)
	throws X {
		assert null != value;
		
		if (value.isSome()) {
			add(value.asSome().getValue());
		}
	}
	
	@Override
	public void addAll(final Collection<? extends Tuple2<? extends T1, ? extends T2>> values)
	throws X {
		assert null != values;
		
		for (final Tuple2<? extends T1, ? extends T2> value : values) {
			add(value);
		}
	}
	
	// Procedure.
	
	@Override
	public void execute(final T1 value1, final T2 value2)
	throws X {
		add(value1, value2);
	}
}
