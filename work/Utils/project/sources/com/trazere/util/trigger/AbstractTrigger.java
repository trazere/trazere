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
package com.trazere.util.trigger;

import com.trazere.util.lang.MutableBoolean;

/**
 * The {@link AbstractTrigger} abstract class provides skeletons for writing triggers.
 */
public abstract class AbstractTrigger
implements Trigger {
	/** State of the trigger. */
	private final MutableBoolean _state = new MutableBoolean(false);
	
	@Override
	public boolean isSet() {
		return _state.get();
	}
	
	@Override
	public boolean begin() {
		final boolean set = doBegin();
		if (set && !_state.get()) {
			_state.set(true);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Begin a session.
	 * 
	 * @return <code>true</code> when the trigger is set, <code>false</code> otherwise.
	 */
	protected abstract boolean doBegin();
	
	@Override
	public boolean end() {
		final boolean set = doEnd();
		if (!set && _state.get()) {
			_state.set(false);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * End a session.
	 * 
	 * @return <code>true</code> when the trigger is set, <code>false</code> otherwise.
	 */
	protected abstract boolean doEnd();
}
