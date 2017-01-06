package org.sing_group.rnaseq.api.environment.execution;

public class ExecutionException extends Exception {
	private static final long serialVersionUID = 1L;

	private final int exitStatus;
	private final String command;

	public ExecutionException(int exitStatus, String command) {
		super();
		this.exitStatus = exitStatus;
		this.command = command;
	}

	public ExecutionException(int exitStatus, String message, String command) {
		super(message);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	public ExecutionException(int exitStatus, Throwable cause, String command) {
		super(cause);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	public ExecutionException(int exitStatus, String message, Throwable cause, 
		String command
	) {
		super(message, cause);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	public String getCommand() {
		return command;
	}
	
	public int getExitStatus() {
		return exitStatus;
	}
}
