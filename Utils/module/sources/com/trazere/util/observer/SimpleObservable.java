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

import java.util.Observable;

/**
 * The <code>SimpleObservable</code> class represents observables which provide public control of their <em>changed</em> status.
 */
public class SimpleObservable
extends Observable {
	@Override
	public void clearChanged() {
		super.clearChanged();
	}
	
	@Override
	public void setChanged() {
		super.setChanged();
	}
	
	/**
	 * Mark the receiver observable as changed and immediately notify its observers.
	 */
	public void setChangedAndNotifyObservers() {
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Mark the receiver observable as changed and immediately notify its observers with the given argument.
	 * 
	 * @param argument Argument to pass to the observers.
	 */
	public void setChangedAndNotifyObservers(final Object argument) {
		setChanged();
		notifyObservers(argument);
	}
}
