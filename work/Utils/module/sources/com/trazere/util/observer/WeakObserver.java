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
package com.trazere.util.observer;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Observable;
import java.util.Observer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DOCME
 * 
 * @param <T>
 */
public abstract class WeakObserver<T>
extends WeakReference<T>
implements Observer {
	private static final Log LOG = LogFactory.getLog(WeakObserver.class);
	
	public WeakObserver(final T object) {
		super(object);
	}
	
	public WeakObserver(final T object, final ReferenceQueue<T> queue) {
		super(object, queue);
	}
	
	public final void update(final Observable observable, final Object argument) {
		if (null != get()) {
			weakUpdate(observable, argument);
		} else {
			// Remove observer.
			observable.deleteObserver(this);
			
			// Warning.
			LOG.warn("Deleted weakened observer " + this);
		}
	}
	
	public abstract void weakUpdate(final Observable observable, final Object argument);
}
