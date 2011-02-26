/*
 *  Copyright 2006-2010 Julien Dufour
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
package com.trazere.util.task;

import java.util.Collection;
import java.util.Collections;

/**
 * The {@link AbstractTask} abstract class provide a task basic implementation which supports notification about the executions.
 * 
 * @see TaskListener
 */
public abstract class AbstractTask
implements Task {
	/** Name of the task. */
	protected final String _name;
	
	/**
	 * Instantiate a new task with the given name and report listener.
	 * 
	 * @param name Name of the task.
	 */
	public AbstractTask(final String name) {
		assert null != name;
		
		// Initialize instance.
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public void run()
	throws TaskException {
		run(Collections.<TaskListener>emptyList());
	}
	
	public void run(final Collection<? extends TaskListener> listeners)
	throws TaskException {
		assert null != listeners;
		
		// Start.
		taskStarted(listeners);
		
		try {
			// Perform the task.
			doRun();
			
			// Success.
			taskSucceeded(listeners);
		} catch (final TaskException exception) {
			// Failure.
			taskFailed(listeners, exception);
			throw exception;
		} catch (final RuntimeException exception) {
			// Failure.
			taskFailed(listeners, exception);
			throw exception;
		} finally {
			// End.
			taskEnded(listeners);
		}
	}
	
	public boolean unsafeRun() {
		return unsafeRun(Collections.<TaskListener>emptyList());
	}
	
	public boolean unsafeRun(final Collection<? extends TaskListener> listeners) {
		try {
			run(listeners);
			return true;
		} catch (final TaskException exception) {
			return false;
		}
	}
	
	/**
	 * Run the receiver task.
	 * 
	 * @throws TaskException When an error occur during the execution.
	 */
	protected abstract void doRun()
	throws TaskException;
	
	protected void taskStarted(final Collection<? extends TaskListener> listeners) {
		assert null != listeners;
		
		for (final TaskListener listener : listeners) {
			listener.taskStarted(this);
		}
	}
	
	protected void taskSucceeded(final Collection<? extends TaskListener> listeners) {
		assert null != listeners;
		
		for (final TaskListener listener : listeners) {
			listener.taskSucceeded(this);
		}
	}
	
	protected void taskFailed(final Collection<? extends TaskListener> listeners, final TaskException exception) {
		assert null != listeners;
		assert null != exception;
		
		for (final TaskListener listener : listeners) {
			listener.taskFailed(this, exception);
		}
	}
	
	protected void taskFailed(final Collection<? extends TaskListener> listeners, final RuntimeException exception) {
		assert null != listeners;
		assert null != exception;
		
		for (final TaskListener listener : listeners) {
			listener.taskFailed(this, exception);
		}
	}
	
	protected void taskEnded(final Collection<? extends TaskListener> listeners) {
		assert null != listeners;
		
		for (final TaskListener listener : listeners) {
			listener.taskEnded(this);
		}
	}
}
