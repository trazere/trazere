/*
 *  Copyright 2008 Julien Dufour
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

import com.trazere.util.Assert;
import java.util.Collections;
import java.util.List;

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
		Assert.notNull(name);
		
		// Initialize instance.
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public void performTask()
	throws TaskException {
		performTask(Collections.<TaskListener>emptyList());
	}
	
	public void performTask(final List<TaskListener> listeners)
	throws TaskException {
		Assert.notNull(listeners);
		
		// Start.
		taskStarted(listeners);
		
		try {
			// Perform the task.
			performAbstractTask();
			
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
	
	public boolean unsafePerformTask() {
		return unsafePerformTask(Collections.<TaskListener>emptyList());
	}
	
	public boolean unsafePerformTask(final List<TaskListener> listeners) {
		try {
			performTask(listeners);
			return true;
		} catch (final TaskException exception) {
			return false;
		}
	}
	
	protected abstract void performAbstractTask()
	throws TaskException;
	
	protected void taskStarted(final List<TaskListener> listeners) {
		for (final TaskListener listener : listeners) {
			listener.taskStarted();
		}
	}
	
	protected void taskSucceeded(final List<TaskListener> listeners) {
		for (final TaskListener listener : listeners) {
			listener.taskSucceeded();
		}
	}
	
	protected void taskFailed(final List<TaskListener> listeners, final TaskException exception) {
		for (final TaskListener listener : listeners) {
			listener.taskFailed(exception);
		}
	}
	
	protected void taskFailed(final List<TaskListener> listeners, final RuntimeException exception) {
		for (final TaskListener listener : listeners) {
			listener.taskFailed(exception);
		}
	}
	
	protected void taskEnded(final List<TaskListener> listeners) {
		for (final TaskListener listener : listeners) {
			listener.taskEnded();
		}
	}
}
