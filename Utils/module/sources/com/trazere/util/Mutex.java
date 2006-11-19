/*
 *  Copyright 2006 Julien Dufour
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
package com.trazere.util;

/**
 * The <code>Mutex</code> class represents mutexes.
 * <p>
 * The class supports embedded locks from the owner thread.
 */
public class Mutex {
	/** Owner thread of the lock. May be <code>null</code>. */
	protected Thread _owner = null;

	/** Lock count. */
	protected int _count = 0;

	/**
	 * Lock the receiver mutex.
	 * <p>
	 * This method blocks if the lock of the mutex is already owned by another thread.
	 */
	public synchronized void lock() {
		while (!doLock()) {
			try {
				wait();
			} catch (final InterruptedException exception) {
				// Nothing to do.
			}
		}
	}

	protected synchronized boolean doLock() {
		final Thread owner = Thread.currentThread();
		if (null == _owner) {
			_owner = owner;
			_count = 1;

			return true;
		} else if (owner == _owner) {
			_count += 1;

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Unlock the receiver mutex.
	 * <p>
	 * This method must not be called by the threads which don't own lock. The mutex must be unlocked the same number of times that it was locked be the owner
	 * thread in order to release the lock.
	 */
	public synchronized void unlock() {
		final Thread owner = Thread.currentThread();
		if (owner == _owner) {
			_count -= 1;

			if (0 == _count) {
				_owner = null;
				notify();
			}
		} else {
			throw new IllegalMonitorStateException("Lock owned by " + _owner + " <> " + owner);
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[Mutex");
		if (null != _owner) {
			builder.append(" - Locked by ");
			builder.append(_owner);
			builder.append(" ");
			builder.append(_count);
			builder.append(" time(s)");
		} else {
			builder.append(" - Unlocked");
		}
		builder.append("]");
		return builder.toString();
	}
}
