package org.sing_group.rnaseq.core.environment.execution;

import org.sing_group.rnaseq.api.environment.execution.ExecutionResult;

public class DefaultExecutionResult implements ExecutionResult {
	private final int exitStatus;
	private final String output;
	private final String error;
	
	public DefaultExecutionResult(int exitStatus) {
		this(exitStatus, null, null);
	}
	
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