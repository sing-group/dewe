package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;

/**
 * The interface for controlling system commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemController {
	/**
	 * Sets the {@code SystemBinariesExecutor} that should be used to execute
	 * system commands.
	 * 
	 * @param executor the {@code SystemBinariesExecutor}
	 */
	public abstract void setSystemBinariesExecutor(
		SystemBinariesExecutor executor);

	/**
	 * Joins files {@code a} and {@code b} and writes the result into
	 * {@code result}.
	 * 
	 * @param a the first file to be joined
	 * @param b the second file to be joined
	 * @param result the file to store the result
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void join(File a, File b, File result)
		throws ExecutionException, InterruptedException;

	/**
	 * Joins all files in {@code files} and writes the result into
	 * {@code result}.
	 * 
	 * @param files the array of files to be joined
	 * @param result the file to store the result
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract void join(File[] files, File result)
		throws ExecutionException, InterruptedException;

	/**
	 * Executes sed using the specified parameters. 
	 * 
	 * @param params the parameters to be passed to sed
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void sed(String...params)
		throws ExecutionException, InterruptedException;
	
	/**
	 * Executes awk using the specified parameters. 
	 * 
	 * @param result the file to store the result
	 * @param params the parameters to be passed to awk
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void awk(File result, String...params)
		throws ExecutionException, InterruptedException;
}
