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
 * The {@link Tuple5} class implements a 5-tuple (quintuple) data type which represents sequences of 5 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 * @param <E3> Type of the third element.
 * @param <E4> Type of the fourth element.
 * @param <E5> Type of the fifth element.
 */
public class Tuple5<E1, E2, E3, E4, E5>
extends Tuple4<E1, E2, E3, E4> {
	/**
	 * Builds a 5-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @return The built tuple.
	 */
	public static <E1, E2, E3, E4, E5> Tuple5<E1, E2, E3, E4, E5> build(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5) {
		return new Tuple5<E1, E2, E3, E4, E5>(e1, e2, e3, e4, e5);
	}
	
	/**
	 * Instantiates a new 5-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 */
	public Tuple5(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5) {
		super(e1, e2, e3, e4);
		
		// Initialization.
		_e5 = e5;
	}
	
	// First element.
	
	@Override
	public <NE1> Tuple5<NE1, E2, E3, E4, E5> with1(final NE1 ne1) {
		return new Tuple5<NE1, E2, E3, E4, E5>(ne1, _e2, _e3, _e4, _e5);
	}
	
	@Override
	public <NE1> Tuple5<NE1, E2, E3, E4, E5> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple5<NE1, E2, E3, E4, E5>(function.evaluate(_e1), _e2, _e3, _e4, _e5);
	}
	
	// Second element.
	
	@Override
	public <NE2> Tuple5<E1, NE2, E3, E4, E5> with2(final NE2 ne2) {
		return new Tuple5<E1, NE2, E3, E4, E5>(_e1, ne2, _e3, _e4, _e5);
	}
	
	@Override
	public <NE2> Tuple5<E1, NE2, E3, E4, E5> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple5<E1, NE2, E3, E4, E5>(_e1, function.evaluate(_e2), _e3, _e4, _e5);
	}
	
	// Third element.
	
	@Override
	public <NE3> Tuple5<E1, E2, NE3, E4, E5> with3(final NE3 ne3) {
		return new Tuple5<E1, E2, NE3, E4, E5>(_e1, _e2, ne3, _e4, _e5);
	}
	
	@Override
	public <NE3> Tuple5<E1, E2, NE3, E4, E5> map3(final Function<? super E3, ? extends NE3> function) {
		return new Tuple5<E1, E2, NE3, E4, E5>(_e1, _e2, function.evaluate(_e3), _e4, _e5);
	}
	
	// Fourth element.
	
	@Override
	public <NE4> Tuple5<E1, E2, E3, NE4, E5> with4(final NE4 ne4) {
		return new Tuple5<E1, E2, E3, NE4, E5>(_e1, _e2, _e3, ne4, _e5);
	}
	
	@Override
	public <NE4> Tuple5<E1, E2, E3, NE4, E5> map4(final Function<? super E4, ? extends NE4> function) {
		return new Tuple5<E1, E2, E3, NE4, E5>(_e1, _e2, _e3, function.evaluate(_e4), _e5);
	}
	
	// Fifth element.
	
	/** Fifth element. */
	protected final E5 _e5;
	
	/**
	 * Gets the fifth element of the receiver tuple.
	 * 
	 * @return The fifth element.
	 */
	public E5 get5() {
		return _e5;
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by replacing the fifth element.
	 * 
	 * @param <NE5> Type of the new fifth element.
	 * @param ne5 New fifth element.
	 * @return The derived tuple.
	 */
	public <NE5> Tuple5<E1, E2, E3, E4, NE5> with5(final NE5 ne5) {
		return new Tuple5<E1, E2, E3, E4, NE5>(_e1, _e2, _e3, _e4, ne5);
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by mapping the fifth element using the given function.
	 * 
	 * @param <NE5> Type of the new fifth element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE5> Tuple5<E1, E2, E3, E4, NE5> map5(final Function<? super E5, ? extends NE5> function) {
		return new Tuple5<E1, E2, E3, E4, NE5>(_e1, _e2, _e3, _e4, function.evaluate(_e5));
	}
	
	// Object.
	
	@Override
	public int hashCode() {
		final HashCode result = new HashCode(this);
		result.append(_e1);
		result.append(_e2);
		result.append(_e3);
		result.append(_e4);
		result.append(_e5);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple5<?, ?, ?, ?, ?> tuple = (Tuple5<?, ?, ?, ?, ?>) object;
			return ObjectUtils.safeEquals(_e1, tuple._e1) && ObjectUtils.safeEquals(_e2, tuple._e2) && ObjectUtils.safeEquals(_e3, tuple._e3) && ObjectUtils.safeEquals(_e4, tuple._e4) && ObjectUtils.safeEquals(_e5, tuple._e5);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + "," + _e3 + "," + _e4 + "," + _e5 + ")";
	}
}
