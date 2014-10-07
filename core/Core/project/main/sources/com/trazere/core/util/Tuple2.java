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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;

/**
 * The {@link Tuple2} class implements a 2-tuple (douple or pair) data type that represents sequences of 2 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 */
public class Tuple2<E1, E2>
implements Field1<E1>, Field2<E2> {
	/**
	 * Instantiates a new 2-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 */
	public Tuple2(final E1 e1, final E2 e2) {
		_e1 = e1;
		_e2 = e2;
	}
	
	// First element.
	
	/** First element. */
	protected final E1 _e1;
	
	/**
	 * Gets the first element of this tuple.
	 * 
	 * @return The first element.
	 */
	@Override
	public E1 get1() {
		return _e1;
	}
	
	/**
	 * Derives a new tuple from this tuple by replacing the first element.
	 * 
	 * @param <NE1> Type of the new first element.
	 * @param ne1 New first element.
	 * @return The derived tuple.
	 */
	public <NE1> Tuple2<NE1, E2> with1(final NE1 ne1) {
		return new Tuple2<>(ne1, _e2);
	}
	
	/**
	 * Derives a new tuple from this tuple by mapping the first element using the given function.
	 * 
	 * @param <NE1> Type of the new first element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE1> Tuple2<NE1, E2> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple2<>(function.evaluate(_e1), _e2);
	}
	
	// Second element.
	
	/** Second element. */
	protected final E2 _e2;
	
	/**
	 * Gets the second element of this tuple.
	 * 
	 * @return The second element.
	 */
	@Override
	public E2 get2() {
		return _e2;
	}
	
	/**
	 * Derives a new tuple from this tuple by replacing the second element.
	 * 
	 * @param <NE2> Type of the new second element.
	 * @param ne2 New second element.
	 * @return The derived tuple.
	 */
	public <NE2> Tuple2<E1, NE2> with2(final NE2 ne2) {
		return new Tuple2<>(_e1, ne2);
	}
	
	/**
	 * Derives a new tuple from this tuple by mapping the second element using the given function.
	 * 
	 * @param <NE2> Type of the new second element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE2> Tuple2<E1, NE2> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple2<>(_e1, function.evaluate(_e2));
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_e1);
		result.append(_e2);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple2<?, ?> tuple = (Tuple2<?, ?>) object;
			return ObjectUtils.safeEquals(_e1, tuple._e1) && ObjectUtils.safeEquals(_e2, tuple._e2);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + ")";
	}
}
