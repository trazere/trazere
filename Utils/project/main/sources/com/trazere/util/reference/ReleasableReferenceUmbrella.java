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
package com.trazere.util.reference;

import com.trazere.util.lang.Releasable;
import com.trazere.util.type.Maybe;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@link ReleasableReferenceUmbrella} class provides sub references to an underlying releasable reference.
 * <p>
 * This class implements some kind of reference counting mecanism over a releable value. The value is released when all references to it have been released.
 * 
 * @param <T> Type of the referenced values.
 * @param <X> Type of the exceptions.
 * @deprecated Use {@link com.trazere.core.reference.ReleasableReferenceUmbrella}.
 */
@Deprecated
public class ReleasableReferenceUmbrella<T, X extends Exception>
implements Releasable<X> {
	/** The value. */
	protected final ReleasableReference<T, X> _value;
	
	/**
	 * Instantiates a new reference umbrella with the given value.
	 * <p>
	 * The new umbrella implicitely references the value. The value will not be release until this implicit reference is released using the {@link #release()}
	 * method.
	 * 
	 * @param value The value.
	 */
	public ReleasableReferenceUmbrella(final ReleasableReference<T, X> value) {
		assert null != value;
		
		// Initialization.
		_value = value;
		_references.add(_value);
	}
	
	// References.
	
	/** The references. */
	protected final Set<Releasable<X>> _references = new HashSet<Releasable<X>>();
	
	/**
	 * Gets a new reference to the value of the receiver umbrella.
	 * <p>
	 * The value of the umbrella will not be release until the returned reference is released.
	 * 
	 * @return The reference.
	 * @throws ReferenceNotSetException When the value has already been release.
	 */
	public ReleasableReference<T, X> getReference()
	throws ReferenceNotSetException {
		// Get the service instance.
		final T value = _value.get();
		
		// Build the reference.
		final class Reference
		extends BaseReference<T>
		implements ReleasableReference<T, X> {
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
			public void release()
			throws X {
				removeReference(this);
			}
		}
		final ReleasableReference<T, X> reference = new Reference();
		_references.add(reference);
		
		return reference;
	}
	
	/**
	 * Removes the given reference to the value and releases the value if needed.
	 * 
	 * @param reference The reference to remove.
	 * @throws X When the release of the value fails.
	 */
	protected void removeReference(final ReleasableReference<T, X> reference)
	throws X {
		if (_references.remove(reference)) {
			if (_references.isEmpty()) {
				_value.release();
			}
		}
	}
	
	// Release.
	
	/**
	 * Releases the implicit reference to the value of the receiver umbrella.
	 * 
	 * @throws X When the release fails.
	 */
	@Override
	public void release()
	throws X {
		removeReference(_value);
	}
}
