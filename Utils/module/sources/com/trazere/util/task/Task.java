package com.trazere.util.task;

/**
 * The <code>Task</code> interface defines executable, named tasks.
 */
public interface Task {
	/**
	 * Get the name of the task.
	 * 
	 * @return The name.
	 */
	public String getName();

	/**
	 * Execute the receiver task.
	 * 
	 * @throws TaskException When an error occur during the execution.
	 */
	public void performTask()
	throws TaskException;

	/**
	 * Execute the receiver task.
	 * <p>
	 * This method silently ignore errors.
	 * 
	 * @return <code>true</code> if the task has executed correctly, <code>false</code> if some error occured.
	 */
	public boolean unsafePerformTask();
}
