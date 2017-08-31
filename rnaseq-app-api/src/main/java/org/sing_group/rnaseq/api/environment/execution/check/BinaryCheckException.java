package org.sing_group.rnaseq.api.environment.execution.check;

/**
 * A class that represents an exception occurred during binaries checking.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BinaryCheckException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final String command;

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command.
	 * 
	 * @param command the command being checked
	 */
	public BinaryCheckException(String command) {
		super();
		this.command = command;
	}

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command with
	 * an informative message.
	 * 
	 * @param message the exception message
	 * @param command the command being checked
	 */
	public BinaryCheckException(String message, String command) {
		super(message);
		this.command = command;
	}

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command with
	 * a {@code Throwable} cause.
	 * 
	 * @param cause a {@code Throwable} object.
	 * @param command the command being executed
	 */
	public BinaryCheckException(Throwable cause, String command) {
		super(cause);
		this.command = command;
	}

	/**
	 * Creates a new {@code BinaryCheckException} for the specified command with
	 * an informative message and a {@code Throwable} cause.
	 * 
	 * @param message the exception message
	 * @param cause a {@code Throwable} object.
	 * @param command the command being executed
	 */
	public BinaryCheckException(String message, Throwable cause, String command) {
		super(message, cause);
		this.command = command;
	}
	
	/**
	 * Returns the command being checked.
	 * 
	 * @return the command being checked
	 */
	public String getCommand() {
		return command;
	}
}
