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
 * The {@link Tuple3} class implements a 3-tuple (triple) data type that represents sequences of 3 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 * @param <E3> Type of the third element.
 * @since 2.0
 */
public class Tuple3<E1, E2, E3>
implements Field1<E1>, Field2<E2>, Field3<E3>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new 3-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @since 2.0
	 */
	public Tuple3(final E1 e1, final E2 e2, final E3 e3) {
		_e1 = e1;
		_e2 = e2;
		_e3 = e3;
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
	public <NE1> Tuple3<NE1, E2, E3> with1(final NE1 ne1) {
		return new Tuple3<>(ne1, _e2, _e3);
	}
	
	/**
	 * Derives a new tuple from this tuple by mapping the first element using the given function.
	 * 
	 * @param <NE1> Type of the new first element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 * @since 2.0
	 */
	public <NE1> Tuple3<NE1, E2, E3> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple3<>(function.evaluate(_e1), _e2, _e3);
	}
	
	// Second element.
	
	/**
	 * Second element.
	 * 
	 * @since 2.0
	 */
	protected final E2 _e2;
	
	/**
	 * Gets the second element of this tuple.
	 * 
	 * @return The second element.
	 * @since 2.0
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
	 * @since 2.0
	 */
	public <NE2> Tuple3<E1, NE2, E3> with2(final NE2 ne2) {
		return new Tuple3<>(_e1, ne2, _e3);
	}
	
	/**
	 * Derives a new tuple from this tuple by mapping the second element using the given function.
	 * 
	 * @param <NE2> Type of the new second element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 * @since 2.0
	 */
	public <NE2> Tuple3<E1, NE2, E3> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple3<>(_e1, function.evaluate(_e2), _e3);
	}
	
	// Third element.
	
	/**
	 * Third element.
	 * 
	 * @since 2.0
	 */
	protected final E3 _e3;
	
	/**
	 * Gets the third element of this tuple.
	 * 
	 * @return The third element.
	 * @since 2.0
	 */
	@Override
	public E3 get3() {
		return _e3;
	}
	
	/**
	 * Derives a new tuple from this tuple by replacing the third element.
	 * 
	 * @param <NE3> Type of the new third element.
	 * @param ne3 New third element.
	 * @return The derived tuple.
	 * @since 2.0
	 */
	public <NE3> Tuple3<E1, E2, NE3> with3(final NE3 ne3) {
		return new Tuple3<>(_e1, _e2, ne3);
	}
	
	/**
	 * Derives a new tuple from this tuple by mapping the third element using the given function.
	 * 
	 * @param <NE3> Type of the new third element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 * @since 2.0
	 */
	public <NE3> Tuple3<E1, E2, NE3> map3(final Function<? super E3, ? extends NE3> function) {
		return new Tuple3<>(_e1, _e2, function.evaluate(_e3));
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
			return ObjectUtils.safeEquals(_e1, tuple._e1) && ObjectUtils.safeEquals(_e2, tuple._e2) && ObjectUtils.safeEquals(_e3, tuple._e3);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + "," + _e3 + ")";
	}
}
