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
package com.trazere.util.lang;

/**
 * The {@link MathUtils} class provides various math related helpers.
 * 
 * @deprecated Use {@link com.trazere.util.math.MathUtils}. (since 1.0)
 */
@Deprecated
public class MathUtils {
	/**
	 * Computes the greatest common denomitor of the given values.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The greatest common denominator.
	 * @deprecated Use {@link com.trazere.util.math.MathUtils#gcd(int, int)}. (since 1.0)
	 */
	@Deprecated
	public static int gcd(final int a, final int b) {
		return com.trazere.util.math.MathUtils.gcd(a, b);
	}
	
	/**
	 * Computes the greatest common denomitor of the given values.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The greatest common denominator.
	 * @deprecated Use {@link com.trazere.util.math.MathUtils#gcd(long, long)}. (since 1.0)
	 */
	@Deprecated
	public static long gcd(final long a, final long b) {
		return com.trazere.util.math.MathUtils.gcd(a, b);
	}
	
	/**
	 * Computes the least common multiple of the given values.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The least common multiple.
	 * @deprecated Use {@link com.trazere.util.math.MathUtils#lcm(int, int)}. (since 1.0)
	 */
	@Deprecated
	public static int lcm(final int a, final int b) {
		return com.trazere.util.math.MathUtils.lcm(a, b);
	}
	
	/**
	 * Computes the least common multiple of the given values.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The least common multiple.
	 * @deprecated Use {@link com.trazere.util.math.MathUtils#lcm(long, long)}. (since 1.0)
	 */
	@Deprecated
	public static long lcm(final long a, final long b) {
		return com.trazere.util.math.MathUtils.lcm(a, b);
	}
	
	private MathUtils() {
		// Prevents instantiation.
	}
}
