/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.record;

import com.trazere.util.lang.Closure;

/**
 * The {@link RecordClosure} class represents closures of values over context records.
 * 
 * @param <T> Type of the value.
 * @param <K> Type of the keys of the context record.
 * @param <V> Type of the values of the context record.
 */
public class RecordClosure<T, K, V>
extends Closure<T, Record<K, V>> {
	/**
	 * Build a new closure with the given value and an empty context.
	 * 
	 * @param <T> Type of the value.
	 * @param <K> Type of the keys of the context record.
	 * @param <V> Type of the values of the context record.
	 * @param value Value of the closure.
	 * @return The built closure.
	 */
	public static <T, K, V> RecordClosure<T, K, V> build(final T value) {
		return new RecordClosure<T, K, V>(value, SimpleRecord.<K, V>build());
	}
	
	/**
	 * Build a new closure with the given value and context.
	 * 
	 * @param <T> Type of the value.
	 * @param <K> Type of the keys of the context record.
	 * @param <V> Type of the values of the context record.
	 * @param value Value of the closure.
	 * @param context Context of the closure.
	 * @return The built closure.
	 */
	public static <T, K, V> RecordClosure<T, K, V> build(final T value, final Record<K, V> context) {
		return new RecordClosure<T, K, V>(value, context);
	}
	
	/**
	 * Instantiate a new closure with the given value and parameter set.
	 * 
	 * @param value Value of the closure.
	 * @param context Context of the closure.
	 */
	public RecordClosure(final T value, final Record<K, V> context) {
		super(value, context);
	}
}
