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
package com.trazere.util.parameter;

import com.trazere.util.lang.HashCode;
import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleParameterSetSignature} class represents signatures of parameter sets.
 * 
 * @param <T> Type of the parameter values.
 */
public class SimpleParameterSetSignature<T>
implements ParameterSetSignature<T>, Describable {
	/** Empty parameter set signature. */
	private static final SimpleParameterSetSignature<?> EMPTY = new SimpleParameterSetSignature<Object>(Collections.<String, ParameterSignature<Object>>emptyMap());
	
	/**
	 * Build an empty parameter set signature.
	 * <p>
	 * This method actually returns a singleton instead of building a new objet.
	 * 
	 * @param <T> Type of the parameter values.
	 * @return The parameter set signature.
	 * @see #EMPTY
	 */
	@SuppressWarnings("unchecked")
	public static <T> SimpleParameterSetSignature<T> build() {
		return (SimpleParameterSetSignature<T>) EMPTY;
	}
	
	/**
	 * Build a parameter set signature with the given parameter signatures.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param signatures Signatures of the parameters.
	 * @return The parameter set signature.
	 */
	public static <T> SimpleParameterSetSignature<T> build(final Collection<? extends ParameterSignature<? extends T>> signatures) {
		assert null != signatures;
		
		// Build.
		final Map<String, ParameterSignature<? extends T>> signaturesByNames = new HashMap<String, ParameterSignature<? extends T>>();
		for (final ParameterSignature<? extends T> signature : signatures) {
			signaturesByNames.put(signature.getName(), signature);
		}
		return new SimpleParameterSetSignature<T>(signaturesByNames);
	}
	
	/**
	 * Build a parameter set signature with the given parameter signatures.
	 * 
	 * @param <T> Type of the parameter values.
	 * @param signatures Signatures of the parameters.
	 * @return The parameter set signature.
	 */
	public static <T> SimpleParameterSetSignature<T> build(final ParameterSignature<? extends T>... signatures) {
		assert null != signatures;
		
		// Build.
		final Map<String, ParameterSignature<? extends T>> signaturesByNames = new HashMap<String, ParameterSignature<? extends T>>();
		for (final ParameterSignature<? extends T> signature : signatures) {
			signaturesByNames.put(signature.getName(), signature);
		}
		return new SimpleParameterSetSignature<T>(signaturesByNames);
	}
	
	/** Signatures of the parameters identified by their names. */
	protected final Map<String, ParameterSignature<? extends T>> _signatures;
	
	/**
	 * Instantiate a new parameter set signature backed by the given map.
	 * 
	 * @param signatures Signatures of the parameters identified by their names.
	 */
	protected SimpleParameterSetSignature(final Map<String, ? extends ParameterSignature<? extends T>> signatures) {
		assert null != signatures;
		
		// Initialization.
		_signatures = Collections.unmodifiableMap(signatures);
	}
	
	public boolean isEmpty() {
		return _signatures.isEmpty();
	}
	
	public boolean contains(final String name) {
		assert null != name;
		
		// Test.
		return _signatures.containsKey(name);
	}
	
	public Set<String> getNames() {
		return _signatures.keySet();
	}
	
	public ParameterSignature<? extends T> get(final String name)
	throws MissingParameterException {
		assert null != name;
		
		// Get.
		if (_signatures.containsKey(name)) {
			return _signatures.get(name);
		} else {
			throw new MissingParameterException("Missing \"" + name + "\" parameter signature from set " + this);
		}
	}
	
	public ParameterSignature<? extends T> get(final String name, final ParameterSignature<? extends T> defaultSignature) {
		assert null != name;
		
		// Get.
		if (_signatures.containsKey(name)) {
			return _signatures.get(name);
		} else {
			return defaultSignature;
		}
	}
	
	public Map<String, ParameterSignature<? extends T>> asMap() {
		return _signatures;
	}
	
	public boolean accepts(final ParameterSet<? extends T> parameters)
	throws ParameterException {
		assert null != parameters;
		
		for (final Map.Entry<String, ParameterSignature<? extends T>> signatureEntry : _signatures.entrySet()) {
			final String name = signatureEntry.getKey();
			if (!parameters.contains(name) || !signatureEntry.getValue().getType().isInstance(parameters.get(signatureEntry.getKey()))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final HashCode hashCode = new HashCode(this);
		hashCode.append(_signatures);
		return hashCode.get();
	}
	
	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		} else if (null != object && getClass().equals(object.getClass())) {
			final SimpleParameterSet<?> signatures = (SimpleParameterSet<?>) object;
			return _signatures.equals(signatures._parameters);
		} else {
			return false;
		}
	}
	
	@Override
	public final String toString() {
		return TextUtils.computeDescription(this);
	}
	
	public void fillDescription(final StringBuilder builder) {
		for (final Map.Entry<String, ParameterSignature<? extends T>> entry : _signatures.entrySet()) {
			builder.append(" - ").append(entry.getKey()).append(" = ").append(entry.getValue());
		}
	}
}
