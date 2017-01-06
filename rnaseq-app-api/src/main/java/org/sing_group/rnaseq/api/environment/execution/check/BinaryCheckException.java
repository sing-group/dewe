package org.sing_group.rnaseq.api.environment.execution.check;

public class BinaryCheckException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final String command;
	
	public BinaryCheckException(String command) {
		super();
		this.command = command;
	}

	public BinaryCheckException(String message, String command) {
		super(message);
		this.command = command;
	}

	public BinaryCheckException(Throwable cause, String command) {
		super(cause);
		this.command = command;
	}

	public BinaryCheckException(String message, Throwable cause, String command) {
		super(message, cause);
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
}
