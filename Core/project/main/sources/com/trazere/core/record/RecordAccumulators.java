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
package com.trazere.core.record;

import com.trazere.core.imperative.Accumulator;

/**
 * The {@link RecordAccumulators} class provides various factories of {@link Accumulator accumulators} related to {@link Record records}.
 * 
 * @see Accumulator
 * @see Record
 * @since 2.0
 */
public class RecordAccumulators {
	/**
	 * Builds an accumulator that adds fields to the given record builder.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <B> Type of the record builder.
	 * @param builder Record builder to populate.
	 * @return The built accumulator.
	 * @see RecordBuilder#add(Field)
	 * @see RecordBuilder#addAll(Iterable)
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, B extends RecordBuilder<K, ?>> Accumulator<Field<K, ?>, B> add(final B builder) {
		assert null != builder;
		
		return new Accumulator<Field<K, ?>, B>() {
			@Override
			public void add(final Field<K, ?> field) {
				builder.add(field);
			}
			
			@Override
			public void addAll(final Iterable<? extends Field<K, ?>> fields) {
				builder.addAll(fields);
			}
			
			@Override
			public B get() {
				return builder;
			}
		};
	}
	
	/**
	 * Builds an accumulator that completes the fields of the given record builder.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <B> Type of the record builder.
	 * @param builder Record builder to populate.
	 * @return The built accumulator.
	 * @see RecordBuilder#complete(Field)
	 * @see RecordBuilder#completeAll(Iterable)
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, B extends RecordBuilder<K, ?>> Accumulator<Field<K, ?>, B> complete(final B builder) {
		assert null != builder;
		
		return new Accumulator<Field<K, ?>, B>() {
			@Override
			public void add(final Field<K, ?> field) {
				builder.complete(field);
			}
			
			@Override
			public void addAll(final Iterable<? extends Field<K, ?>> fields) {
				builder.completeAll(fields);
			}
			
			@Override
			public B get() {
				return builder;
			}
		};
	}
	
	/**
	 * Builds an accumulator that sets fields in the given record builder.
	 * 
	 * @param <K> Type of the field keys.
	 * @param <B> Type of the record builder.
	 * @param builder Record builder to populate.
	 * @return The built accumulator.
	 * @see RecordBuilder#set(Field)
	 * @since 2.0
	 */
	public static <K extends FieldKey<K, ?>, B extends RecordBuilder<K, ?>> Accumulator<Field<K, ?>, B> set(final B builder) {
		assert null != builder;
		
		return new Accumulator<Field<K, ?>, B>() {
			@Override
			public void add(final Field<K, ?> field) {
				builder.set(field);
			}
			
			@Override
			public void addAll(final Iterable<? extends Field<K, ?>> fields) {
				builder.setAll(fields);
			}
			
			@Override
			public B get() {
				return builder;
			}
		};
	}
	
	private RecordAccumulators() {
		// Prevent instantiation.
	}
}
