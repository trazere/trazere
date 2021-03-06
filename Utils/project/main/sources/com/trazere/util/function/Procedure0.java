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
package com.trazere.util.function;

/**
 * The {@link Procedure0} interface defines zero arguments procedures (zero arguments functions which return no results).
 * 
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.imperative.Effect}.
 */
@Deprecated
public interface Procedure0<X extends Exception> {
	/**
	 * Executes the receiver procedure.
	 * 
	 * @throws X When the procedure execution fails.
	 * @deprecated Use {@link com.trazere.core.imperative.Effect#execute()}.
	 */
	@Deprecated
	public void execute()
	throws X;
}
