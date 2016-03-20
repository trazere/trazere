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

import com.trazere.core.functional.Function;
import java.util.function.Consumer;

/**
 * The {@link Procedure} interface defines procedures (functions that return no results).
 * 
 * @param <A> Type of the arguments.
 * @since 2.0
 */
@FunctionalInterface
public interface Procedure<A> {
	/**
	 * Executes this procedure with the given argument.
	 * 
	 * @param arg Argument to execute the procedure with.
	 * @since 2.0
	 */
	void execute(A arg);
	
	/**
	 * Executes this procedure in a thread safe way.
	 * 
	 * @param arg Argument to execute the procedure with.
	 * @since 2.0
	 */
	default void synchronizedExecute(final A arg) {
		synchronized (this) {
			execute(arg);
		}
	}
	
	/**
	 * Composes this procedure with the given function.
	 * <p>
	 * TODO: detail function composition
	 *
	 * @param <CA> Type of the composition arguments.
	 * @param function Function to use to transform the arguments.
	 * @return The composition procedure.
	 * @since 2.0
	 */
	default <CA> Procedure<CA> compose(final Function<? super CA, ? extends A> function) {
		assert null != function;
		
		final Procedure<A> self = this;
		return arg -> self.execute(function.evaluate(arg));
	}
	
	/**
	 * Builds a synchronized view of this procedure.
	 * 
	 * @return The built procedure.
	 * @see #synchronizedExecute(Object)
	 * @since 2.0
	 */
	default Procedure<A> synchronized_() {
		return this::synchronizedExecute;
	}
	
	/**
	 * Lifts this procedure as a Java 8 consumer.
	 * 
	 * @return The built Java 8 consumer.
	 * @since 2.0
	 */
	default Consumer<A> toConsumer() {
		return this::execute;
	}
}
