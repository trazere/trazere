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
package com.trazere.core.imperative;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The {@link ProcedureUtils} class provides various utilities regarding {@link Procedure procedures}.
 * 
 * @see Procedure
 * @see Procedure2
 * @since 2.0
 */
public class ProcedureUtils {
	/**
	 * Builds a consumer that lifts the given procedure.
	 * 
	 * @param <A> Type of the arguments.
	 * @param procedure Procedure to lift.
	 * @return The built consumer.
	 * @since 2.0
	 */
	public static <A> Consumer<A> toConsumer(final Procedure<? super A> procedure) {
		assert null != procedure;
		
		return t -> {
			procedure.execute(t);
		};
	}
	
	/**
	 * Builds a bi-consumer that lifts the given two arguments procedure.
	 * 
	 * @param <A1> Type of the first arguments.
	 * @param <A2> Type of the second arguments.
	 * @param procedure Procedure to lift.
	 * @return The built bi-consumer.
	 * @since 2.0
	 */
	public static <A1, A2> BiConsumer<A1, A2> toBiConsumer(final Procedure2<? super A1, ? super A2> procedure) {
		assert null != procedure;
		
		return (t, u) -> {
			procedure.execute(t, u);
		};
	}
	
	private ProcedureUtils() {
		// Prevent instantiation.
	}
}
