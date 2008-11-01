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
package com.trazere.util.trigger;

/**
 * The {@link CountingTrigger} class represents simple triggers based on counting.
 */
public class CountingTrigger
extends AbstractTrigger {
	/** Value. */
	protected int _value = 0;
	
	/** Set bound. */
	protected final int _setBound;
	
	/** Reset bound. */
	protected final int _resetBound;
	
	/**
	 * Instantiate a new trigger with <code>0</code> bounds.
	 */
	public CountingTrigger() {
		this(0, 0);
	}
	
	/**
	 * Instantiate a new trigger with the given bounds.
	 * 
	 * @param setBound Set bound.
	 * @param resetBound Reset bound.
	 */
	public CountingTrigger(final int setBound, final int resetBound) {
		assert setBound >= 0;
		assert resetBound >= 0;
		assert setBound >= resetBound;
		
		// Initialization.
		_setBound = setBound;
		_resetBound = resetBound;
	}
	
	@Override
	protected boolean doBegin() {
		_value += 1;
		return _value > _setBound;
	}
	
	@Override
	protected boolean doEnd() {
		assert _value > 0;
		
		_value -= 1;
		return _value > _resetBound;
	}
}
