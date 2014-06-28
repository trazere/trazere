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
 * The {@link Tuple1} class implements a 1-tuple data type (single) which represents sequences of 1 element.
 * 
 * @param <E1> Type of the first element.
 */
public class Tuple1<E1> {
	/**
	 * Builds a 1-tuple with the given element.
	 * 
	 * @param <E1> Type of the first element.
	 * @param e1 First element.
	 * @return The built tuple.
	 */
	public static <E1> Tuple1<E1> build(final E1 e1) {
		return new Tuple1<E1>(e1);
	}
	
	/**
	 * Instantiates a new 1-tuple.
	 * 
	 * @param e1 First element.
	 */
	public Tuple1(final E1 e1) {
		_e1 = e1;
	}
	
	// First element.
	
	/** First element. */
	protected final E1 _e1;
	
	/**
	 * Gets the first element of the receiver tuple.
	 * 
	 * @return The first element.
	 */
	public E1 get1() {
		return _e1;
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by replacing the first element.
	 * 
	 * @param <NE1> Type of the new first element.
	 * @param ne1 New first element.
	 * @return The derived tuple.
	 */
	public <NE1> Tuple1<NE1> with1(final NE1 ne1) {
		return new Tuple1<NE1>(ne1);
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by mapping the first element using the given function.
	 * 
	 * @param <NE1> Type of the new first element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE1> Tuple1<NE1> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple1<NE1>(function.evaluate(_e1));
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_e1);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple1<?> tuple = (Tuple1<?>) object;
			return ObjectUtils.safeEquals(_e1, tuple._e1);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + ")";
	}
}
