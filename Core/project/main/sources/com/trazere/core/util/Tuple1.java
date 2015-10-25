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
package com.trazere.core.util;

import com.trazere.core.functional.Function;
import com.trazere.core.lang.HashCode;
import com.trazere.core.lang.ObjectUtils;
import java.io.Serializable;

/**
 * The {@link Tuple1} class implements a 1-tuple data type (single) that represents sequences of 1 element.
 * 
 * @param <E1> Type of the first element.
 * @since 2.0
 */
public class Tuple1<E1>
implements Field1<E1>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new 1-tuple.
	 * 
	 * @param e1 First element.
	 * @since 2.0
	 */
	public Tuple1(final E1 e1) {
		_e1 = e1;
	}
	
	// First element.
	
	/**
	 * First element.
	 * 
	 * @since 2.0
	 */
	protected final E1 _e1;
	
	/**
	 * Gets the first element of this tuple.
	 * 
	 * @return The first element.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public <NE1> Tuple1<NE1> with1(final NE1 ne1) {
		return new Tuple1<>(ne1);
	}
	
	/**
	 * Derives a new tuple from this tuple by mapping the first element using the given function.
	 * 
	 * @param <NE1> Type of the new first element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 * @since 2.0
	 */
	public <NE1> Tuple1<NE1> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple1<>(function.evaluate(_e1));
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
