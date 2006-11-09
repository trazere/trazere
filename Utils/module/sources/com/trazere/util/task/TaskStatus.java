package com.trazere.util.task;

public enum TaskStatus {
	SUCCEEDED("Task succeeded"),
	FAILED("Task failed");
	
	protected final String _message;
	
	TaskStatus(final String message) {
		_message = message;
	}
	
	public String getMessage() {
		return _message;
	}
}
