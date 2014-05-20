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
package com.trazere.util.text;

import com.trazere.util.lang.MutableBoolean;
import com.trazere.util.type.Maybe;

/**
 * The {@link Description} class helps to compute descriptions of objects.
 * <p>
 * This class works as an accumulator of named properties, each instance can only be used to compute a single description.
 * 
 * @deprecated Use {@link com.trazere.core.text.Description}.
 */
@Deprecated
public class Description {
	public static Description buildObjectDescription(final Object object) {
		assert null != object;
		
		return new Description(TextUtils.computeClassName(object.getClass()));
	}
	
	private final StringBuilder _builder;
	private final MutableBoolean _first;
	
	public Description() {
		// Initialization.
		_builder = new StringBuilder("[");
		_first = new MutableBoolean(true);
	}
	
	public Description(final String head) {
		assert null != head;
		
		// Initialization.
		_builder = new StringBuilder("[");
		_builder.append(head);
		_first = new MutableBoolean(false);
	}
	
	public Description(final StringBuilder builder, final boolean first) {
		assert null != builder;
		
		// Initialization.
		_builder = builder;
		_first = new MutableBoolean(first);
	}
	
	public Description append(final String name) {
		assert null != name;
		
		if (_first.get()) {
			_first.set(false);
		} else {
			_builder.append(" - ");
		}
		_builder.append(name);
		
		return this;
	}
	
	public Description append(final String name, final Object value) {
		append(name);
		_builder.append(" = ").append(value);
		
		return this;
	}
	
	public Description append(final String name, final boolean value) {
		return append(name, Boolean.valueOf(value));
	}
	
	public Description append(final String name, final byte value) {
		return append(name, Byte.valueOf(value));
	}
	
	public Description append(final String name, final int value) {
		return append(name, Integer.valueOf(value));
	}
	
	public Description append(final String name, final long value) {
		return append(name, Long.valueOf(value));
	}
	
	public Description append(final String name, final float value) {
		return append(name, Float.valueOf(value));
	}
	
	public Description append(final String name, final double value) {
		return append(name, Double.valueOf(value));
	}
	
	public Description append(final String name, final char value) {
		return append(name, Character.valueOf(value));
	}
	
	public Description append(final String name, final Maybe<?> value) {
		assert null != name;
		assert null != value;
		
		if (value.isSome()) {
			append(name, value.asSome().getValue());
		}
		return this;
	}
	
	@Override
	public String toString() {
		return _builder.toString() + "]";
	}
}
