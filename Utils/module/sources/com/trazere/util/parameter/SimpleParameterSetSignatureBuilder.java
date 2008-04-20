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

import com.trazere.util.text.Describable;
import com.trazere.util.text.TextUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The {@link SimpleParameterSetSignatureBuilder} class represents builders of {@link ParameterSetSignature parameter set signatures}.
 * 
 * @param <T> Type of the parameter values.
 */
public class SimpleParameterSetSignatureBuilder<T>
implements ParameterSetSignatureBuilder<T>, Describable {
	/** Signatures of the parameters identified by their names. */
	final Map<String, ParameterSignature<? extends T>> _signatures;
	
	/**
	 * Instantiate a new empty builder.
	 */
	public SimpleParameterSetSignatureBuilder() {
		// Initialization.
		_signatures = new HashMap<String, ParameterSignature<? extends T>>();
	}
	
	/**
	 * Instantiate a new builder populated with the given parameter signatures.
	 * 
	 * @param signatures Initial parameter signatures identified by their names.
	 */
	public SimpleParameterSetSignatureBuilder(final Map<String, ? extends ParameterSignature<? extends T>> signatures) {
		assert null != signatures;
		
		// Initialization.
		_signatures = new HashMap<String, ParameterSignature<? extends T>>(signatures);
	}
	
	/**
	 * Instantiate a new builder populated with the parameter signatures of the given parameter set signature.
	 * 
	 * @param signature Parameter set signature containing the initial parameter signatures the new builder.
	 * @throws ParameterException When the parameter signatures cannot be read.
	 */
	public SimpleParameterSetSignatureBuilder(final ParameterSetSignature<? extends T> signature)
	throws ParameterException {
		assert null != signature;
		
		// Initialization.
		_signatures = new HashMap<String, ParameterSignature<? extends T>>(signature.asMap());
	}
	
	public void add(final ParameterSignature<? extends T> signature)
	throws DuplicateParameterException {
		assert null != signature;
		
		// Add the parameter signature.
		final String name = signature.getName();
		if (!_signatures.containsKey(name)) {
			_signatures.put(name, signature);
		} else {
			throw new DuplicateParameterException("Parameter signature \"" + name + "\" already exists in builder " + this);
		}
	}
	
	public void addAll(final Collection<? extends ParameterSignature<? extends T>> signatures)
	throws DuplicateParameterException {
		assert null != signatures;
		
		// Add the parameter signatures.
		for (final ParameterSignature<? extends T> signature : signatures) {
			final String name = signature.getName();
			if (!_signatures.containsKey(name)) {
				_signatures.put(name, signature);
			} else {
				throw new DuplicateParameterException("Parameter signature \"" + name + "\" already exists in builder " + this);
			}
		}
	}
	
	public void addAll(final ParameterSetSignature<? extends T> signature)
	throws ParameterException {
		assert null != signature;
		
		// Add the parameter signatures.
		addAll(signature.asMap().values());
	}
	
	public void unify(final ParameterSignature<? extends T> signature)
	throws ParameterException {
		assert null != signature;
		
		// Unify the parameter signature.
		final String name = signature.getName();
		if (_signatures.containsKey(name)) {
			final Class<? extends T> type = signature.getType();
			final Class<? extends T> currentType = _signatures.get(name).getType();
			if (!type.isAssignableFrom(currentType)) {
				if (currentType.isAssignableFrom(type)) {
					_signatures.put(name, signature);
				} else {
					throw new IncompatibleParameterException("Cannot unify signature " + signature + " with type " + currentType + " in builder " + this);
				}
			}
		} else {
			_signatures.put(name, signature);
		}
	}
	
	public void unifyAll(final Collection<? extends ParameterSignature<? extends T>> signatures)
	throws ParameterException {
		assert null != signatures;
		
		// Unify the parameter signatures.
		for (final ParameterSignature<? extends T> signature : signatures) {
			final String name = signature.getName();
			if (_signatures.containsKey(name)) {
				final Class<? extends T> type = signature.getType();
				final Class<? extends T> currentType = _signatures.get(name).getType();
				if (!type.isAssignableFrom(currentType)) {
					if (currentType.isAssignableFrom(type)) {
						_signatures.put(name, signature);
					} else {
						throw new IncompatibleParameterException("Cannot unify signature " + signature + " with type " + currentType + " in builder " + this);
					}
				}
			} else {
				_signatures.put(name, signature);
			}
		}
	}
	
	public void unifyAll(final ParameterSetSignature<? extends T> signature)
	throws ParameterException {
		assert null != signature;
		
		// Unify the parameter signatures.
		unifyAll(signature.asMap().values());
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
		return Collections.unmodifiableSet(_signatures.keySet());
	}
	
	public void remove(final String name)
	throws MissingParameterException {
		assert null != name;
		
		// Remove the parameter signature.
		if (_signatures.containsKey(name)) {
			_signatures.remove(name);
		} else {
			throw new MissingParameterException("Parameter signature \"" + name + "\" does not exist in builder " + this);
		}
	}
	
	public void clear() {
		_signatures.clear();
	}
	
	public ParameterSetSignature<T> build() {
		return new SimpleParameterSetSignature<T>(new HashMap<String, ParameterSignature<? extends T>>(_signatures));
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
