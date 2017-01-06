package org.sing_group.rnaseq.api.environment.execution;

public interface ExecutionResult {
	public abstract int getExitStatus();

	public abstract String getOutput();

	public abstract String getError();
}