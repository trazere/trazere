/*
 *  Copyright 2006-2012 Julien Dufour
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
package com.trazere.util.cache;

import java.util.Collection;

/**
 * The {@link FIFOCachePolicy} class provides cache policies which keeps all entries.
 * 
 * @param <K> Type of the keys.
 */
public class AllCachePolicy<K>
implements CachePolicy<K> {
	public <C extends Collection<? super K>> C accessedEntry(final K key, final C dirtyEntries) {
		return dirtyEntries;
	}
	
	public <C extends Collection<? super K>> C updatedEntry(final K key, final C dirtyEntries) {
		return dirtyEntries;
	}
	
	public void removedEntry(final K key) {
		// Nothing to do.
	}
	
	public void removedAllEntries() {
		// Nothing to do.
	}
}