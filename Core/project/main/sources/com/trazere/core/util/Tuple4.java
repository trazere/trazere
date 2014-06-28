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
 * The {@link Tuple4} class implements a 4-tuple (quadruple) data type which represents sequences of 4 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 * @param <E3> Type of the third element.
 * @param <E4> Type of the fourth element.
 */
public class Tuple4<E1, E2, E3, E4>
extends Tuple3<E1, E2, E3> {
	/**
	 * Builds a 4-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @return The built tuple.
	 */
	public static <E1, E2, E3, E4> Tuple4<E1, E2, E3, E4> build(final E1 e1, final E2 e2, final E3 e3, final E4 e4) {
		return new Tuple4<E1, E2, E3, E4>(e1, e2, e3, e4);
	}
	
	/**
	 * Instantiates a new 4-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 */
	public Tuple4(final E1 e1, final E2 e2, final E3 e3, final E4 e4) {
		super(e1, e2, e3);
		
		// Initialization.
		_e4 = e4;
	}
	
	// First element.
	
	@Override
	public <NE1> Tuple4<NE1, E2, E3, E4> with1(final NE1 ne1) {
		return new Tuple4<NE1, E2, E3, E4>(ne1, _e2, _e3, _e4);
	}
	
	@Override
	public <NE1> Tuple4<NE1, E2, E3, E4> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple4<NE1, E2, E3, E4>(function.evaluate(_e1), _e2, _e3, _e4);
	}
	
	// Second element.
	
	@Override
	public <NE2> Tuple4<E1, NE2, E3, E4> with2(final NE2 ne2) {
		return new Tuple4<E1, NE2, E3, E4>(_e1, ne2, _e3, _e4);
	}
	
	@Override
	public <NE2> Tuple4<E1, NE2, E3, E4> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple4<E1, NE2, E3, E4>(_e1, function.evaluate(_e2), _e3, _e4);
	}
	
	// Third element.
	
	@Override
	public <NE3> Tuple4<E1, E2, NE3, E4> with3(final NE3 ne3) {
		return new Tuple4<E1, E2, NE3, E4>(_e1, _e2, ne3, _e4);
	}
	
	@Override
	public <NE3> Tuple4<E1, E2, NE3, E4> map3(final Function<? super E3, ? extends NE3> function) {
		return new Tuple4<E1, E2, NE3, E4>(_e1, _e2, function.evaluate(_e3), _e4);
	}
	
	// Fourth element.
	
	/** Fourth element. */
	protected final E4 _e4;
	
	/**
	 * Gets the fourth element of the receiver tuple.
	 * 
	 * @return The fourth element.
	 */
	public E4 get4() {
		return _e4;
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by replacing the fourth element.
	 * 
	 * @param <NE4> Type of the new fourth element.
	 * @param ne4 New fourth element.
	 * @return The derived tuple.
	 */
	public <NE4> Tuple4<E1, E2, E3, NE4> with4(final NE4 ne4) {
		return new Tuple4<E1, E2, E3, NE4>(_e1, _e2, _e3, ne4);
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by mapping the fourth element using the given function.
	 * 
	 * @param <NE4> Type of the new fourth element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE4> Tuple4<E1, E2, E3, NE4> map4(final Function<? super E4, ? extends NE4> function) {
		return new Tuple4<E1, E2, E3, NE4>(_e1, _e2, _e3, function.evaluate(_e4));
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_e1);
		result.append(_e2);
		result.append(_e3);
		result.append(_e4);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple4<?, ?, ?, ?> tuple = (Tuple4<?, ?, ?, ?>) object;
			return ObjectUtils.safeEquals(_e1, tuple._e1) && ObjectUtils.safeEquals(_e2, tuple._e2) && ObjectUtils.safeEquals(_e3, tuple._e3) && ObjectUtils.safeEquals(_e4, tuple._e4);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + "," + _e3 + "," + _e4 + ")";
	}
}