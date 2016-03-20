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

import com.trazere.core.util.Tuple2;
import java.util.function.BiConsumer;

/**
 * The {@link Procedure2} interface defines uncurried procedures (functions which return no results) that take two arguments.
 * 
 * @param <A1> Type of the first arguments.
 * @param <A2> Type of the second arguments.
 * @since 2.0
 */
@FunctionalInterface
public interface Procedure2<A1, A2> {
	/**
	 * Executes this procedure with the given arguments.
	 * 
	 * @param arg1 First argument to execute the procedure with.
	 * @param arg2 Second argument to execute the procedure with.
	 * @since 2.0
	 */
	void execute(A1 arg1, A2 arg2);
	
	/**
	 * Executes this procedure in a thread safe way.
	 * 
	 * @param arg1 First argument to execute the procedure with.
	 * @param arg2 Second argument to execute the procedure with.
	 * @since 2.0
	 */
	default void synchronizedExecute(final A1 arg1, final A2 arg2) {
		synchronized (this) {
			execute(arg1, arg2);
		}
	}
	
	/**
	 * Builds a synchronized view of this procedure.
	 * 
	 * @return The built procedure.
	 * @see #synchronizedExecute(Object, Object)
	 * @since 2.0
	 */
	default Procedure2<A1, A2> synchronized_() {
		return this::synchronizedExecute;
	}
	
	/**
	 * Gets an uncurried view of this procedure (as a procedure that takes pairs of elements).
	 *
	 * @return The built procedure.
	 * @since 2.0
	 */
	default Procedure<Tuple2<A1, A2>> uncurried() {
		final Procedure2<A1, A2> self = this;
		return arg -> self.execute(arg.get1(), arg.get2());
	}
	
	/**
	 * Lifts this procedure as a Java 8 bi-consumer.
	 * 
	 * @return The built Java 8 bi-consumer.
	 * @since 2.0
	 */
	default BiConsumer<A1, A2> toBiConsumer() {
		return this::execute;
	}
}
