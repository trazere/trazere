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
package com.trazere.core.math;

/**
 * The {@link MathUtils} class provides various utilities regarding math.
 * 
 * @since 2.0
 */
public class MathUtils {
	/**
	 * Computes the greatest common denomitor of the given integers.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The greatest common denominator.
	 * @since 2.0
	 */
	public static int gcd(final int a, final int b) {
		return 0 == b ? a : gcd(b, a % b);
	}
	
	/**
	 * Computes the greatest common denomitor of the given long integers.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The greatest common denominator.
	 * @since 2.0
	 */
	public static long gcd(final long a, final long b) {
		return 0 == b ? a : gcd(b, a % b);
	}
	
	/**
	 * Computes the least common multiple of the given integers.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The least common multiple.
	 * @since 2.0
	 */
	public static int lcm(final int a, final int b) {
		return 0 == a ? 0 : (a / gcd(a, b)) * b;
	}
	
	/**
	 * Computes the least common multiple of the given long integers.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return The least common multiple.
	 * @since 2.0
	 */
	public static long lcm(final long a, final long b) {
		return 0 == a ? 0 : (a / gcd(a, b)) * b;
	}
	
	private MathUtils() {
		// Prevents instantiation.
	}
}
