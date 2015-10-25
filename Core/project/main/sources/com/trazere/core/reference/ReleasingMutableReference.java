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

/**
 * The {@link ReleasingMutableReference} class implements mutable references that release the referenced value on disposal.
 * 
 * @param <T> Type of the referenced value.
 * @since 2.0
 */
public class ReleasingMutableReference<T extends Releasable>
extends MutableReference<T> {
	@Override
	protected void dispose(final T value) {
		// Release.
		value.release();
		
		// Dispose.
		super.dispose(value);
	}
}
