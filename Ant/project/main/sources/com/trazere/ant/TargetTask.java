/*
 *  Copyright 2006-2015 Julien Dufour
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
package com.trazere.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

/**
 * The {@link TargetTask} class implements an Ant task container that executes tasks conditionnally.
 * 
 * @since 1.0
 */
public class TargetTask
extends Task
implements TaskContainer {
	/** Target to execute. */
	protected final Target _target = new Target();
	
	/**
	 * Sets the name of the property that must be set for the exection to happen.
	 * 
	 * @param if_ Name of the property.
	 * @since 1.0
	 */
	public void setIf(final String if_) {
		_target.setIf(if_);
	}
	
	/**
	 * Sets the name of the property that must not be set for the exection to happen.
	 * 
	 * @param unless Name of the property.
	 * @since 1.0
	 */
	public void setUnless(final String unless) {
		_target.setUnless(unless);
	}
	
	// public void setDepends(final String depends) {
	// _target.setDepends(depends);
	// }
	
	@Override
	public void addTask(final Task task) {
		_target.addTask(task);
	}
	
	@Override
	public void execute()
	throws BuildException {
		_target.setProject(getProject());
		_target.execute();
	}
}
