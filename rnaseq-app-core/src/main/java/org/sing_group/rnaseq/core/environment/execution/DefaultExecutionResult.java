package org.sing_group.rnaseq.core.environment.execution;

import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;

/**
 * The default {@code ExecutionResult} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultExecutionResult implements ExecutionResult {
	private final int exitStatus;
	private final String output;
	private final String error;
	
	/**
	 * Creates a new {@code DefaultExecutionResult} instance with the specified
	 * exit status.
	 * 
	 * @param exitStatus the exit status
	 */
	public DefaultExecutionResult(int exitStatus) {
		this(exitStatus, null, null);
	}
	
	/**
	 * Creates a new {@code DefaultExecutionResult} instance with the specified
	 * exit status and the output and error results.
	 * 
	 * @param exitStatus the exit status
	 * @param output the output string
	 * @param error the error string
	 */	
	public DefaultExecutionResult(int exitStatus, String output, String error) {
		this.exitStatus = exitStatus;
		this.output = output;
		this.error = error;
	}
	
	@Override
	public int getExitStatus() {
		return exitStatus;
	}
	
	@Override
	public String getOutput() {
		return output;
	}
	
	@Override
	public String getError() {
		return error;
	}
}