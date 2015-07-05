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
package com.trazere.core.reference;

import com.trazere.core.lang.Releasable;
import com.trazere.core.util.Maybe;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link ReleasableReferenceUmbrella} class implements reference hubs to an underlying releasable value.
 * <p>
 * This class implements some kind of reference counting mecanism over the releasable value. The value is released when all references to it have been released.
 * 
 * @param <T> Type of the referenced values.
 * @since 1.0
 */
public class ReleasableReferenceUmbrella<T>
implements Releasable {
	/**
	 * Value to reference.
	 * 
	 * @since 1.0
	 */
	protected final ReleasableReference<? extends T> _value;
	
	/**
	 * Instantiates a new reference umbrella over the given releasable value.
	 * <p>
	 * The new umbrella implicitely references the given value. It will not be released until that implicit reference is released using the {@link #release()}
	 * method.
	 * 
	 * @param value Releasable value to reference.
	 * @since 1.0
	 */
	public ReleasableReferenceUmbrella(final ReleasableReference<? extends T> value) {
		assert null != value;
		
		// Initialization.
		_value = value;
		_references.add(_value);
	}
	
	// References.
	
	/**
	 * References.
	 * 
	 * @since 1.0
	 */
	protected final Set<Releasable> _references = new HashSet<>();
	
	/**
	 * Gets a new reference to the value of this umbrella.
	 * <p>
	 * The value of the umbrella will not be release until the returned reference is released.
	 * 
	 * @return The reference.
	 * @throws ReferenceNotSetException When the value has already been release.
	 * @since 1.0
	 */
	public ReleasableReference<T> getReference()
	throws ReferenceNotSetException {
		final T value = _value.get(); // Note: capture the value.
		final ReleasableReference<T> reference = new ReleasableReference<T>() {
			@Override
			public T get()
			throws ReferenceNotSetException {
				if (isSet()) {
					return value;
				} else {
					throw new ReferenceNotSetException();
				}
			}
			
			@Override
			public boolean isSet() {
				return _references.contains(this);
			}
			
			@Override
			public Maybe<T> asMaybe() {
				if (isSet()) {
					return Maybe.some(value);
				} else {
					return Maybe.none();
				}
			}
			
			@Override
			public void release() {
				removeReference(this);
			}
		};
		_references.add(reference);
		
		return reference;
	}
	
	/**
	 * Removes the given reference to the value and releases the value if needed.
	 * 
	 * @param reference Reference to remove.
	 * @since 1.0
	 */
	protected void removeReference(final ReleasableReference<? extends T> reference) {
		if (_references.remove(reference)) {
			if (_references.isEmpty()) {
				_value.release();
			}
		}
	}
	
	// Release.
	
	/**
	 * Releases the implicit reference to the value of this umbrella.
	 * 
	 * @since 1.0
	 */
	@Override
	public void release() {
		removeReference(_value);
	}
}
