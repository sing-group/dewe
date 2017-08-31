package org.sing_group.rnaseq.api.environment.execution;

/**
 * A class that represents an exception occurred during binaries execution.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ExecutionException extends Exception {
	private static final long serialVersionUID = 1L;

	private final int exitStatus;
	private final String command;

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, String command) {
		super();
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status with an informative message.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param message the exception message
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, String message, String command) {
		super(message);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status with a {@code Throwable} cause.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param cause a {@code Throwable} object.
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, Throwable cause, String command) {
		super(cause);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Creates a new {@code ExecutionException} for the specified command and
	 * status with an informative message and a {@code Throwable} cause.
	 * 
	 * @param exitStatus the exit status of the command
	 * @param message the exception message
	 * @param cause a {@code Throwable} object. 
	 * @param command the command being executed
	 */
	public ExecutionException(int exitStatus, String message, Throwable cause, 
		String command
	) {
		super(message, cause);
		this.exitStatus = exitStatus;
		this.command = command;
	}

	/**
	 * Returns the command being executed.
	 * 
	 * @return the command being executed
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * Returns the exit status of the command.
	 * 
	 * @return the exit status of the command
	 */
	public int getExitStatus() {
		return exitStatus;
	}
}
