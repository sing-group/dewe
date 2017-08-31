package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;

/**
 * The interface for running system binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemBinariesExecutor
	extends BinariesExecutor<SystemBinaries> {
	
	/**
	 * Joins files {@code a} and {@code b} and writes the result into
	 * {@code result}.
	 * 
	 * @param a the first file to be joined
	 * @param b the second file to be joined
	 * @param result the file to store the result
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract ExecutionResult join(File a, File b, File result)
		throws ExecutionException, InterruptedException;

	/**
	 * Joins all files in {@code files} and writes the result into
	 * {@code result}.
	 * 
	 * @param files the array of files to be joined
	 * @param result the file to store the result
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */		
	public abstract ExecutionResult join(File[] files, File result)
		throws ExecutionException, InterruptedException;

	/**
	 * Executes sed using the specified parameters. 
	 * 
	 * @param params the parameters to be passed to sed
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult sed(String... params)
		throws ExecutionException, InterruptedException;

	/**
	 * Executes awk using the specified parameters. 
	 * 
	 * @param output the file to store the result
	 * @param params the parameters to be passed to awk
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult awk(File output, String... params)
		throws ExecutionException, InterruptedException;
}
