/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.lang.ref;

import com.trazere.util.lang.LangUtils;
import com.trazere.util.type.Maybe;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link MutableReference} class represents mutable, observable refererences.
 * 
 * @param <T> Type of the referenced values.
 */
public class MutableObservableReference<T>
extends MutableReference<T>
implements ObservableReference<T> {
	/** The listeners of the reference. */
	protected final List<ReferenceListener<? super T>> _listeners = new ArrayList<ReferenceListener<? super T>>();
	
	/**
	 * Instantiates an unset reference.
	 */
	public MutableObservableReference() {
		super();
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value. May be <code>null</code>.
	 */
	public MutableObservableReference(final T value) {
		super(value);
	}
	
	/**
	 * Instantiates a reference set to the given value.
	 * 
	 * @param value The value.
	 */
	public MutableObservableReference(final Maybe<T> value) {
		super(value);
	}
	
	@Override
	public <V extends T> V set(final V value)
	throws ReferenceAlreadySetException {
		// Set.
		final V result = super.set(value);
		
		// Notify.
		if (!_listeners.isEmpty()) {
			notifyUpdated();
		}
		
		return result;
	}
	
	@Override
	public <V extends T> Maybe<V> set(final Maybe<V> value)
	throws ReferenceAlreadySetException {
		// Set.
		final Maybe<V> result = super.set(value);
		
		// Notify.
		if (!_listeners.isEmpty() && value.isSome()) {
			notifyUpdated();
		}
		
		return result;
	}
	
	@Override
	public void reset() {
		// Reset.
		final Maybe<T> currentValue = _value;
		super.reset();
		
		// Notify.
		if (!_listeners.isEmpty() && currentValue.isSome()) {
			notifyUpdated();
		}
	}
	
	@Override
	public <V extends T> V update(final V value) {
		// Update.
		final Maybe<T> currentValue = _value;
		final V result = super.update(value);
		
		// Notify.
		if (!_listeners.isEmpty() && (currentValue.isNone() || !LangUtils.equals(currentValue.asSome().getValue(), value))) {
			notifyUpdated();
		}
		
		return result;
	}
	
	@Override
	public <V extends T> Maybe<V> update(final Maybe<V> value) {
		// Update.
		final Maybe<T> currentValue = _value;
		final Maybe<V> result = super.update(value);
		
		// Notify.
		if (!_listeners.isEmpty() && !currentValue.equals(value)) {
			notifyUpdated();
		}
		
		return result;
	}
	
	public void addListener(final ReferenceListener<? super T> observer) {
		assert null != observer;
		
		_listeners.add(observer);
	}
	
	public void removeListener(final ReferenceListener<? super T> observer) {
		assert null != observer;
		
		_listeners.remove(observer);
	}
	
	protected void notifyUpdated() {
		for (final ReferenceListener<? super T> listener : new ArrayList<ReferenceListener<? super T>>(_listeners)) {
			listener.updatedReference(_value);
		}
	}
}
