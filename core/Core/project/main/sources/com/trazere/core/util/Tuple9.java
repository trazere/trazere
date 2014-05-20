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
 * The {@link Tuple9} class implements a 9-tuple (nonuple) data type which represents sequences of 9 elements.
 * 
 * @param <E1> Type of the first element.
 * @param <E2> Type of the second element.
 * @param <E3> Type of the third element.
 * @param <E4> Type of the fourth element.
 * @param <E5> Type of the fifth element.
 * @param <E6> Type of the sixth element.
 * @param <E7> Type of the seventh element.
 * @param <E8> Type of the eighth element.
 * @param <E9> Type of the ninth element.
 */
public class Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, E9>
extends Tuple8<E1, E2, E3, E4, E5, E6, E7, E8> {
	/**
	 * Builds a 9-tuple with the given elements.
	 * 
	 * @param <E1> Type of the first element.
	 * @param <E2> Type of the second element.
	 * @param <E3> Type of the third element.
	 * @param <E4> Type of the fourth element.
	 * @param <E5> Type of the fifth element.
	 * @param <E6> Type of the sixth element.
	 * @param <E7> Type of the seventh element.
	 * @param <E8> Type of the eighth element.
	 * @param <E9> Type of the ninth element.
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 * @param e9 Ninth element.
	 * @return The built tuple.
	 */
	public static <E1, E2, E3, E4, E5, E6, E7, E8, E9> Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, E9> build(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8, final E9 e9) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, E9>(e1, e2, e3, e4, e5, e6, e7, e8, e9);
	}
	
	/**
	 * Instantiates a new 9-tuple with the given elements.
	 * 
	 * @param e1 First element.
	 * @param e2 Second element.
	 * @param e3 Third element.
	 * @param e4 Fourth element.
	 * @param e5 Fifth element.
	 * @param e6 Sixth element.
	 * @param e7 Seventh element.
	 * @param e8 Eighth element.
	 * @param e9 Ninth element.
	 */
	public Tuple9(final E1 e1, final E2 e2, final E3 e3, final E4 e4, final E5 e5, final E6 e6, final E7 e7, final E8 e8, final E9 e9) {
		super(e1, e2, e3, e4, e5, e6, e7, e8);
		
		// Initialization.
		_e9 = e9;
	}
	
	// First element.
	
	@Override
	public <NE1> Tuple9<NE1, E2, E3, E4, E5, E6, E7, E8, E9> with1(final NE1 ne1) {
		return new Tuple9<NE1, E2, E3, E4, E5, E6, E7, E8, E9>(ne1, _e2, _e3, _e4, _e5, _e6, _e7, _e8, _e9);
	}
	
	@Override
	public <NE1> Tuple9<NE1, E2, E3, E4, E5, E6, E7, E8, E9> map1(final Function<? super E1, ? extends NE1> function) {
		return new Tuple9<NE1, E2, E3, E4, E5, E6, E7, E8, E9>(function.evaluate(_e1), _e2, _e3, _e4, _e5, _e6, _e7, _e8, _e9);
	}
	
	// Second element.
	
	@Override
	public <NE2> Tuple9<E1, NE2, E3, E4, E5, E6, E7, E8, E9> with2(final NE2 ne2) {
		return new Tuple9<E1, NE2, E3, E4, E5, E6, E7, E8, E9>(_e1, ne2, _e3, _e4, _e5, _e6, _e7, _e8, _e9);
	}
	
	@Override
	public <NE2> Tuple9<E1, NE2, E3, E4, E5, E6, E7, E8, E9> map2(final Function<? super E2, ? extends NE2> function) {
		return new Tuple9<E1, NE2, E3, E4, E5, E6, E7, E8, E9>(_e1, function.evaluate(_e2), _e3, _e4, _e5, _e6, _e7, _e8, _e9);
	}
	
	// Third element.
	
	@Override
	public <NE3> Tuple9<E1, E2, NE3, E4, E5, E6, E7, E8, E9> with3(final NE3 ne3) {
		return new Tuple9<E1, E2, NE3, E4, E5, E6, E7, E8, E9>(_e1, _e2, ne3, _e4, _e5, _e6, _e7, _e8, _e9);
	}
	
	@Override
	public <NE3> Tuple9<E1, E2, NE3, E4, E5, E6, E7, E8, E9> map3(final Function<? super E3, ? extends NE3> function) {
		return new Tuple9<E1, E2, NE3, E4, E5, E6, E7, E8, E9>(_e1, _e2, function.evaluate(_e3), _e4, _e5, _e6, _e7, _e8, _e9);
	}
	
	// Fourth element.
	
	@Override
	public <NE4> Tuple9<E1, E2, E3, NE4, E5, E6, E7, E8, E9> with4(final NE4 ne4) {
		return new Tuple9<E1, E2, E3, NE4, E5, E6, E7, E8, E9>(_e1, _e2, _e3, ne4, _e5, _e6, _e7, _e8, _e9);
	}
	
	@Override
	public <NE4> Tuple9<E1, E2, E3, NE4, E5, E6, E7, E8, E9> map4(final Function<? super E4, ? extends NE4> function) {
		return new Tuple9<E1, E2, E3, NE4, E5, E6, E7, E8, E9>(_e1, _e2, _e3, function.evaluate(_e4), _e5, _e6, _e7, _e8, _e9);
	}
	
	// Fifth element.
	
	@Override
	public <NE5> Tuple9<E1, E2, E3, E4, NE5, E6, E7, E8, E9> with5(final NE5 ne5) {
		return new Tuple9<E1, E2, E3, E4, NE5, E6, E7, E8, E9>(_e1, _e2, _e3, _e4, ne5, _e6, _e7, _e8, _e9);
	}
	
	@Override
	public <NE5> Tuple9<E1, E2, E3, E4, NE5, E6, E7, E8, E9> map5(final Function<? super E5, ? extends NE5> function) {
		return new Tuple9<E1, E2, E3, E4, NE5, E6, E7, E8, E9>(_e1, _e2, _e3, _e4, function.evaluate(_e5), _e6, _e7, _e8, _e9);
	}
	
	// Sixth element.
	
	@Override
	public <NE6> Tuple9<E1, E2, E3, E4, E5, NE6, E7, E8, E9> with6(final NE6 ne6) {
		return new Tuple9<E1, E2, E3, E4, E5, NE6, E7, E8, E9>(_e1, _e2, _e3, _e4, _e5, ne6, _e7, _e8, _e9);
	}
	
	@Override
	public <NE6> Tuple9<E1, E2, E3, E4, E5, NE6, E7, E8, E9> map6(final Function<? super E6, ? extends NE6> function) {
		return new Tuple9<E1, E2, E3, E4, E5, NE6, E7, E8, E9>(_e1, _e2, _e3, _e4, _e5, function.evaluate(_e6), _e7, _e8, _e9);
	}
	
	// Seventh element.
	
	@Override
	public <NE7> Tuple9<E1, E2, E3, E4, E5, E6, NE7, E8, E9> with7(final NE7 ne7) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, NE7, E8, E9>(_e1, _e2, _e3, _e4, _e5, _e6, ne7, _e8, _e9);
	}
	
	@Override
	public <NE7> Tuple9<E1, E2, E3, E4, E5, E6, NE7, E8, E9> map7(final Function<? super E7, ? extends NE7> function) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, NE7, E8, E9>(_e1, _e2, _e3, _e4, _e5, _e6, function.evaluate(_e7), _e8, _e9);
	}
	
	// Eighth element.
	
	@Override
	public <NE8> Tuple9<E1, E2, E3, E4, E5, E6, E7, NE8, E9> with8(final NE8 ne8) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, E7, NE8, E9>(_e1, _e2, _e3, _e4, _e5, _e6, _e7, ne8, _e9);
	}
	
	@Override
	public <NE8> Tuple9<E1, E2, E3, E4, E5, E6, E7, NE8, E9> map8(final Function<? super E8, ? extends NE8> function) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, E7, NE8, E9>(_e1, _e2, _e3, _e4, _e5, _e6, _e7, function.evaluate(_e8), _e9);
	}
	
	// Ninth element.
	
	/** Ninth element. */
	protected final E9 _e9;
	
	/**
	 * Gets the ninth element of the receiver tuple.
	 * 
	 * @return The ninth element.
	 */
	public E9 get9() {
		return _e9;
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by replacing the ninth element.
	 * 
	 * @param <NE9> Type of the new ninth element.
	 * @param ne9 New ninth element.
	 * @return The derived tuple.
	 */
	public <NE9> Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, NE9> with9(final NE9 ne9) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, NE9>(_e1, _e2, _e3, _e4, _e5, _e6, _e7, _e8, ne9);
	}
	
	/**
	 * Derives a new tuple from the receiver tuple by mapping the ninth element using the given function.
	 * 
	 * @param <NE9> Type of the new ninth element.
	 * @param function Mapping function to use.
	 * @return The derived tuple.
	 */
	public <NE9> Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, NE9> map9(final Function<? super E9, ? extends NE9> function) {
		return new Tuple9<E1, E2, E3, E4, E5, E6, E7, E8, NE9>(_e1, _e2, _e3, _e4, _e5, _e6, _e7, _e8, function.evaluate(_e9));
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
		result.append(_e9);
		return result.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?> tuple = (Tuple9<?, ?, ?, ?, ?, ?, ?, ?, ?>) object;
			return LangUtils.safeEquals(_e1, tuple._e1) && LangUtils.safeEquals(_e2, tuple._e2) && LangUtils.safeEquals(_e3, tuple._e3) && LangUtils.safeEquals(_e4, tuple._e4) && LangUtils.safeEquals(_e5, tuple._e5) && LangUtils.safeEquals(_e6, tuple._e6) && LangUtils.safeEquals(_e7, tuple._e7) && LangUtils.safeEquals(_e8, tuple._e8) && LangUtils.safeEquals(_e9, tuple._e9);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "(" + _e1 + "," + _e2 + "," + _e3 + "," + _e4 + "," + _e5 + "," + _e6 + "," + _e7 + "," + _e8 + "," + _e9 + ")";
	}
}
