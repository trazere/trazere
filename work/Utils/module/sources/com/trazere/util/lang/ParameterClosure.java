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
package com.trazere.util.lang;

import com.trazere.util.Assert;
import com.trazere.util.parameter.ParameterSet;
import com.trazere.util.parameter.SimpleParameterSet;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;

/**
 * The {@link ParameterClosure} class represent closures of values over parameter sets.
 * 
 * @param <Value> Type of the value.
 * @param <Parameter> Type of the parameter values.
 */
public class ParameterClosure<Value, Parameter>
implements Describable {
	/**
	 * Build a new closure with the given value and no parameters.
	 * 
	 * @param <Value>
	 * @param <Parameter>
	 * @param value Value of the closure.
	 * @return The built closure.
	 */
	public static <Value, Parameter> ParameterClosure<Value, Parameter> build(final Value value) {
		return new ParameterClosure<Value, Parameter>(value, SimpleParameterSet.<Parameter>build());
	}
	
	/**
	 * Build a new closure with the given value and parameters.
	 * 
	 * @param <Value>
	 * @param <Parameter>
	 * @param value Value of the closure.
	 * @param parameters Parameters of the closure.
	 * @return The built closure.
	 */
	public static <Value, Parameter> ParameterClosure<Value, Parameter> build(final Value value, final ParameterSet<Parameter> parameters) {
		return new ParameterClosure<Value, Parameter>(value, parameters);
	}
	
	/** Value of the closure. */
	final Value _value;
	
	/** Parameters of the closure. */
	final ParameterSet<Parameter> _parameters;
	
	/**
	 * Instantiate a new closure with the given value and parameter set.
	 * 
	 * @param value Value of the closure.
	 * @param parameters Parameters of the closure.
	 */
	public ParameterClosure(final Value value, final ParameterSet<Parameter> parameters) {
		// Checks.
		Assert.notNull(value);
		Assert.notNull(parameters);
		
		// Initialization.
		_value = value;
		_parameters = parameters;
	}
	
	/**
	 * Get the value of the receiver closure.
	 * 
	 * @return The value.
	 */
	public Value getValue() {
		return _value;
	}
	
	/**
	 * Get the parameters of the receiver closure.
	 * 
	 * @return The parameter set.
	 */
	public ParameterSet<Parameter> getParameters() {
		return _parameters;
	}
	
	@Override
	public String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		builder.append(" - Value = ").append(_value);
		builder.append(" - Parameters = ").append(_parameters);
	}
}
