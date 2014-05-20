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
 * The {@link Tuple8} class implements a 8-tuple (octuple) data type which represents sequences of 8 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 * @param <E3> Type of the third element.
 * @param <E4> Type of the fourth element.
 * @param <E5> Type of the fifth element.
 * @param <E6> Type of the sixth element.
 * @param <E7> Type of the seventh element.
 * @param <E8> Type of the eighth element.
 */
public class Tuple8<E1, E2, E3, E4, E5, E6, E7, E8>
extends Tuple7<E1, E2, E3, E4, E5, E6, E7> {
	/**
	 * Builds a 8-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 * @return The built tuple.
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8> Tuple8<E1, E2, E3, E4, E5, E6, E7, E8> build(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8) {
		return new Tuple8<E1, E2, E3, E4, E5, E6, E7, E8>(e1, e2, e3, e4, e5, e6, e7, e8);
	}
	
	/**
	 * Instantiates a new 8-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 */
	public Tuple8(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8) {
		super(e1, e2, e3, e4, e5, e6, e7);
		
		// Initialization.
		_e8 = e8;
	}
	
	// First element.
	
	@Override
	public <NE1> Tuple8<NE1, E2, E3, E4, E5, E6, E7, E8> with1(final NE1 ne1) {
		return new Tuple8<NE1, E2, E3, E4, E5, E6, E7, E8>(ne1, _e2, _e3, _e4, _e5, _e6, _e7, _e8);
	}
	
	@Override
	public <NE1> Tuple8<NE1, E2, E3, E4, E5, E6, E7, E8> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple8<NE1, E2, E3, E4, E5, E6, E7, E8>(function.evaluate(_e1), _e2, _e3, _e4, _e5, _e6, _e7, _e8);
	}
	
	// Second element.
	
	@Override
	public <NE2> Tuple8<E1, NE2, E3, E4, E5, E6, E7, E8> with2(final NE2 ne2) {
		return new Tuple8<E1, NE2, E3, E4, E5, E6, E7, E8>(_e1, ne2, _e3, _e4, _e5, _e6, _e7, _e8);
	}
	
	@Override
	public <NE2> Tuple8<E1, NE2, E3, E4, E5, E6, E7, E8> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple8<E1, NE2, E3, E4, E5, E6, E7, E8>(_e1, function.evaluate(_e2), _e3, _e4, _e5, _e6, _e7, _e8);
	}
	
	// Third element.
	
	@Override
	public <NE3> Tuple8<E1, E2, NE3, E4, E5, E6, E7, E8> with3(final NE3 ne3) {
		return new Tuple8<E1, E2, NE3, E4, E5, E6, E7, E8>(_e1, _e2, ne3, _e4, _e5, _e6, _e7, _e8);
	}
	
	@Override
	public <NE3> Tuple8<E1, E2, NE3, E4, E5, E6, E7, E8> map3(final Function<? super E3, ? extends NE3> function) {
		return new Tuple8<E1, E2, NE3, E4, E5, E6, E7, E8>(_e1, _e2, function.evaluate(_e3), _e4, _e5, _e6, _e7, _e8);
	}
	
	// Fourth element.
	
	@Override
	public <NE4> Tuple8<E1, E2, E3, NE4, E5, E6, E7, E8> with4(final NE4 ne4) {
		return new Tuple8<E1, E2, E3, NE4, E5, E6, E7, E8>(_e1, _e2, _e3, ne4, _e5, _e6, _e7, _e8);
	}
	
	@Override
	public <NE4> Tuple8<E1, E2, E3, NE4, E5, E6, E7, E8> map4(final Function<? super E4, ? extends NE4> function) {
		return new Tuple8<E1, E2, E3, NE4, E5, E6, E7, E8>(_e1, _e2, _e3, function.evaluate(_e4), _e5, _e6, _e7, _e8);
	}
	
	// Fifth element.
	
	@Override
	public <NE5> Tuple8<E1, E2, E3, E4, NE5, E6, E7, E8> with5(final NE5 ne5) {
		return new Tuple8<E1, E2, E3, E4, NE5, E6, E7, E8>(_e1, _e2, _e3, _e4, ne5, _e6, _e7, _e8);
	}
	
	@Override
	public <NE5> Tuple8<E1, E2, E3, E4, NE5, E6, E7, E8> map5(final Function<? super E5, ? extends NE5> function) {
		return new Tuple8<E1, E2, E3, E4, NE5, E6, E7, E8>(_e1, _e2, _e3, _e4, function.evaluate(_e5), _e6, _e7, _e8);
	}
	
	// Sixth element.
	
	@Override
	public <NE6> Tuple8<E1, E2, E3, E4, E5, NE6, E7, E8> with6(final NE6 ne6) {
		return new Tuple8<E1, E2, E3, E4, E5, NE6, E7, E8>(_e1, _e2, _e3, _e4, _e5, ne6, _e7, _e8);
	}
	
	@Override
	public <NE6> Tuple8<E1, E2, E3, E4, E5, NE6, E7, E8> map6(final Function<? super E6, ? extends NE6> function) {
		return new Tuple8<E1, E2, E3, E4, E5, NE6, E7, E8>(_e1, _e2, _e3, _e4, _e5, function.evaluate(_e6), _e7, _e8);
	}
	
	// Seventh element.
	
	@Override
	public <NE7> Tuple8<E1, E2, E3, E4, E5, E6, NE7, E8> with7(final NE7 ne7) {
		return new Tuple8<E1, E2, E3, E4, E5, E6, NE7, E8>(_e1, _e2, _e3, _e4, _e5, _e6, ne7, _e8);
	}
	
	@Override
	public <NE7> Tuple8<E1, E2, E3, E4, E5, E6, NE7, E8> map7(final Function<? super E7, ? extends NE7> function) {
		return new Tuple8<E1, E2, E3, E4, E5, E6, NE7, E8>(_e1, _e2, _e3, _e4, _e5, _e6, function.evaluate(_e7), _e8);
	}
	
	// Eighth element.
	
	/** Eighth element. */
	protected final E8 _e8;
	
	/**
	 * Gets the eighth element of the receiver tuple.
	 * 
	 * @return The eighth element.
	 */
	public E8 get8() {
		return _e8;
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by replacing the eighth element.
	 * 
	 * @param <NE8> Type of the new eighth element.
	 * @param ne8 New eighth element.
	 * @return The derived tuple.
	 */
	public <NE8> Tuple8<E1, E2, E3, E4, E5, E6, E7, NE8> with8(final NE8 ne8) {
		return new Tuple8<E1, E2, E3, E4, E5, E6, E7, NE8>(_e1, _e2, _e3, _e4, _e5, _e6, _e7, ne8);
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by mapping the eighth element using the given function.
	 * 
	 * @param <NE8> Type of the new eighth element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE8> Tuple8<E1, E2, E3, E4, E5, E6, E7, NE8> map8(final Function<? super E8, ? extends NE8> function) {
		return new Tuple8<E1, E2, E3, E4, E5, E6, E7, NE8>(_e1, _e2, _e3, _e4, _e5, _e6, _e7, function.evaluate(_e8));
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
		result.append(_e6);
		result.append(_e7);
		result.append(_e8);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple8<?, ?, ?, ?, ?, ?, ?, ?> tuple = (Tuple8<?, ?, ?, ?, ?, ?, ?, ?>) object;
			return LangUtils.safeEquals(_e1, tuple._e1) && LangUtils.safeEquals(_e2, tuple._e2) && LangUtils.safeEquals(_e3, tuple._e3) && LangUtils.safeEquals(_e4, tuple._e4) && LangUtils.safeEquals(_e5, tuple._e5) && LangUtils.safeEquals(_e6, tuple._e6) && LangUtils.safeEquals(_e7, tuple._e7) && LangUtils.safeEquals(_e8, tuple._e8);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + "," + _e3 + "," + _e4 + "," + _e5 + "," + _e6 + "," + _e7 + "," + _e8 + ")";
	}
}
