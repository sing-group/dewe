package org.sing_group.rnaseq.api.environment.execution;

/**
 * The result of the executing binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ExecutionResult {
	/**
	 * Returns the exit status.
	 * 
	 * @return the exit status
	 */
	public abstract int getExitStatus();

	/**
	 * The execution output ({@code stdout}).
	 * 
	 * @return execution output ({@code stdout})
	 */
	public abstract String getOutput();

	/**
	 * The execution output ({@code stderr}).
	 * 
	 * @return execution output ({@code stderr})
	 */
	public abstract String getError();
}
