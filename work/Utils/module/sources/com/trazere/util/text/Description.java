/*
 *  Copyright 2006-2008 Julien Dufour
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

import com.trazere.util.type.Maybe;

public class Description {
	private final StringBuilder _builder = new StringBuilder();
	
	public Description(final Object object) {
		assert null != object;
		
		// Compute.
		_builder.append("[").append(TextUtils.computeClassName(object.getClass()));
	}
	
	public Description append(final String name) {
		assert null != name;
		
		_builder.append(" - ").append(name);
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
	
	public Description append(final String name, final Object value) {
		append(name);
		_builder.append(" = ").append(value);
		return this;
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
