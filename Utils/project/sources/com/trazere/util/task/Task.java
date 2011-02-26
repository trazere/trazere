/*
 *  Copyright 2006-2011 Julien Dufour
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

/**
 * The {@link Task} interface defines executable, named tasks.
 */
public interface Task {
	/**
	 * Get the name of the task.
	 * 
	 * @return The name.
	 */
	public String getName();
	
	/**
	 * Run the receiver task.
	 * 
	 * @throws TaskException When an error occur during the execution.
	 */
	public void run()
	throws TaskException;
	
	/**
	 * Run the receiver task.
	 * 
	 * @param listeners Task listeners.
	 * @throws TaskException When an error occur during the execution.
	 */
	public void run(final Collection<? extends TaskListener> listeners)
	throws TaskException;
	
	/**
	 * Run the receiver task.
	 * <p>
	 * This method silently ignore errors.
	 * 
	 * @return <code>true</code> if the task has executed correctly, <code>false</code> if some error occured.
	 */
	public boolean unsafeRun();
	
	/**
	 * Execute the receiver task.
	 * <p>
	 * This method silently ignore errors.
	 * 
	 * @param listeners Task listeners.
	 * @return <code>true</code> if the task has executed correctly, <code>false</code> if some error occured.
	 */
	public boolean unsafeRun(final Collection<? extends TaskListener> listeners);
}
