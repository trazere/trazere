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
package com.trazere.util.lang;

/**
 * The {@link ObservableReference} interface defines references whose value can be observed.
 * 
 * @param <T> Type of the referenced value.
 */
public interface ObservableReference<T>
extends Reference<T> {
	/**
	 * Add the given listener to the receiver reference.
	 * 
	 * @param listener The listener.
	 */
	public void addListener(final ReferenceListener<? super T> listener);
	
	/**
	 * Remove the given listener from the receiver reference.
	 * 
	 * @param listener The listener.
	 */
	public void removeListener(final ReferenceListener<? super T> listener);
}
