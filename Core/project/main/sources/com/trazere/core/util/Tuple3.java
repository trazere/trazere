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
import com.trazere.core.lang.LangUtils;

/**
 * The {@link Tuple3} class implements a 3-tuple (triple) data type which represents sequences of 3 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 * @param <E3> Type of the third element.
 */
public class Tuple3<E1, E2, E3>
extends Tuple2<E1, E2> {
	/**
	 * Builds a 3-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @return The built tuple.
	 */
	public static <E1, E2, E3> Tuple3<E1, E2, E3> build(final E1 e1, final E2 e2, final E3 e3) {
		return new Tuple3<E1, E2, E3>(e1, e2, e3);
	}
	
	/**
	 * Instantiates a new 3-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 */
	public Tuple3(final E1 e1, final E2 e2, final E3 e3) {
		super(e1, e2);
		
		// Initialization.
		_e3 = e3;
	}
	
	// First element.
	
	@Override
	public <NE1> Tuple3<NE1, E2, E3> with1(final NE1 ne1) {
		return new Tuple3<NE1, E2, E3>(ne1, _e2, _e3);
	}
	
	@Override
	public <NE1> Tuple3<NE1, E2, E3> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple3<NE1, E2, E3>(function.evaluate(_e1), _e2, _e3);
	}
	
	// Second element.
	
	@Override
	public <NE2> Tuple3<E1, NE2, E3> with2(final NE2 ne2) {
		return new Tuple3<E1, NE2, E3>(_e1, ne2, _e3);
	}
	
	@Override
	public <NE2> Tuple3<E1, NE2, E3> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple3<E1, NE2, E3>(_e1, function.evaluate(_e2), _e3);
	}
	
	// Third element.
	
	/** Third element. */
	protected final E3 _e3;
	
	/**
	 * Gets the third element of the receiver tuple.
	 * 
	 * @return The third element.
	 */
	public E3 get3() {
		return _e3;
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by replacing the third element.
	 * 
	 * @param <NE3> Type of the new third element.
	 * @param ne3 New third element.
	 * @return The derived tuple.
	 */
	public <NE3> Tuple3<E1, E2, NE3> with3(final NE3 ne3) {
		return new Tuple3<E1, E2, NE3>(_e1, _e2, ne3);
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by mapping the third element using the given function.
	 * 
	 * @param <NE3> Type of the new third element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE3> Tuple3<E1, E2, NE3> map3(final Function<? super E3, ? extends NE3> function) {
		return new Tuple3<E1, E2, NE3>(_e1, _e2, function.evaluate(_e3));
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_e1);
		result.append(_e2);
		result.append(_e3);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple3<?, ?, ?> tuple = (Tuple3<?, ?, ?>) object;
			return LangUtils.safeEquals(_e1, tuple._e1) && LangUtils.safeEquals(_e2, tuple._e2) && LangUtils.safeEquals(_e3, tuple._e3);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + "," + _e3 + ")";
	}
}
