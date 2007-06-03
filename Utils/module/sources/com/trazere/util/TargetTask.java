package com.trazere.util;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

/**
 * DOCME
 */
public class TargetTask
extends Task
implements TaskContainer {
	protected final Target _target = new Target();
	
	public void setIf(final String if_) {
		_target.setIf(if_);
	}
	
	public void setUnless(final String unless) {
		_target.setUnless(unless);
	}
	
	//	public void setDepends(final String depends) {
	//		_target.setDepends(depends);
	//	}
	
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
