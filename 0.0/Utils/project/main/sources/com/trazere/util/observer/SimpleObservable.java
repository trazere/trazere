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
package com.trazere.util.observer;

/**
 * The {@link SimpleObservable} class implements observable sources whose events can be raised externally.
 * 
 * @param <T> Type of the event values.
 */
// TODO: implement Observer and rename to Broadcaster or something else
public class SimpleObservable<T>
extends BaseObservable<T> {
	@Override
	public boolean isObserved() {
		return super.isObserved();
	}
	
	@Override
	public void unsubscribeAll() {
		super.unsubscribeAll();
	}
	
	@Override
	public void notify(final T value) {
		super.notify(value);
	}
}