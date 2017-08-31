package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

/**
 * The interface for controlling R commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface RController {
	/**
	 * Sets the {@code RBinariesExecutor} that should be used to execute R
	 * commands.
	 * 
	 * @param executor the {@code RBinariesExecutor}
	 */
	public abstract void setRBinariesExecutor(RBinariesExecutor executor);

	/**
	 * Runs the script using RScript with the specified parameters.
	 *  
	 * @param script the file that contains the script
	 * @param args the parameters to be passed to Rscript
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void runScript(File script, String...args)
		throws ExecutionException, InterruptedException;
}
