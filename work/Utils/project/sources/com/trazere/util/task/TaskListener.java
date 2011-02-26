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

/**
 * The {@link TaskListener} interface defines listener of task executions.
 */
public interface TaskListener {
	/**
	 * Notify the receiver listener that the execution of the given task has started.
	 * 
	 * @param task The task.
	 */
	public void taskStarted(final Task task);
	
	/**
	 * Notify the receiver listener that the execution of the given task has succeeded.
	 * 
	 * @param task The task.
	 */
	public void taskSucceeded(final Task task);
	
	/**
	 * Notify the receiver listener that the execution of the given task has failed.
	 * 
	 * @param task The task.
	 * @param exception The failure exception.
	 */
	public void taskFailed(final Task task, final TaskException exception);
	
	/**
	 * Notify the receiver listener that the execution of the given task has failed.
	 * 
	 * @param task The task.
	 * @param exception The failure exception.
	 */
	public void taskFailed(final Task task, final RuntimeException exception);
	
	/**
	 * Notify the receiver listener that the execution of the given task has ended.
	 * 
	 * @param task The task.
	 */
	public void taskEnded(final Task task);
}
