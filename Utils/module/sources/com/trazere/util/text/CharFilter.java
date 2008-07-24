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
package com.trazere.util.text;

/**
 * The <code>CharFilter</code> interface defines character filters.
 */
public interface CharFilter {
	/**
	 * Filter the given character.
	 * 
	 * @param c Character to filter.
	 * @return <code>true</code> is the character is accepted, <code>false</code> otherwise.
	 */
	public boolean filter(final char c);
}
