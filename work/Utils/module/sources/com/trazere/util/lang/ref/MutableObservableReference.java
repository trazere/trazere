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
	/** Listeners of the reference. */
	protected final List<ReferenceListener<? super T>> _listeners = new ArrayList<ReferenceListener<? super T>>();
	
	/**
	 * Instantiate an unset reference.
	 */
	public MutableObservableReference() {
		super();
	}
	
	/**
	 * Instantiate a reference set with the given value.
	 * 
	 * @param value Value to set. May be <code>null</code>.
	 */
	public MutableObservableReference(final T value) {
		super(value);
	}
	
	/**
	 * Instantiate a reference with the given value.
	 * 
	 * @param value Initial value.
	 */
	public MutableObservableReference(final Maybe<T> value) {
		super(value);
	}
	
	@Override
	public void set(final T value)
	throws ReferenceAlreadySetException {
		// Set.
		super.set(value);
		
		// Notify.
		if (!_listeners.isEmpty()) {
			notifyUpdated();
		}
	}
	
	@Override
	public void set(final Maybe<T> value)
	throws ReferenceAlreadySetException {
		// Set.
		super.set(value);
		
		// Notify.
		if (!_listeners.isEmpty() && value.isSome()) {
			notifyUpdated();
		}
	}
	
	@Override
	public void reset() {
		// Reset.
		final Maybe<T> oldValue = _value;
		super.reset();
		
		// Notify.
		if (!_listeners.isEmpty() && oldValue.isSome()) {
			notifyUpdated();
		}
	}
	
	@Override
	public void update(final T value) {
		// Update.
		final Maybe<T> oldValue = _value;
		super.update(value);
		
		// Notify.
		if (!_listeners.isEmpty() && (oldValue.isNone() || !LangUtils.equals(oldValue.asSome().getValue(), value))) {
			notifyUpdated();
		}
	}
	
	@Override
	public void update(final Maybe<T> value) {
		// Update.
		final Maybe<T> oldValue = _value;
		super.update(value);
		
		// Notify.
		if (!_listeners.isEmpty() && !oldValue.equals(value)) {
			notifyUpdated();
		}
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
