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
package com.trazere.util.function;

/**
 * The {@link RetryFunction0} interface defines zero arguments functions that are attempted multiple times in case of failure.
 * 
 * @param <S> Type of the success values.
 * @param <F> Type of the failure values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.util.failure.RetryFunction0}. (since 1.0)
 */
@Deprecated
public abstract class RetryFunction0<S, F, X extends Exception>
extends com.trazere.util.failure.RetryFunction0<S, F, X> {
	// Nothing to do.
}
