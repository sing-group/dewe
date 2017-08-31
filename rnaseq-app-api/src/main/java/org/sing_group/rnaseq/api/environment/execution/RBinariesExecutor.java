package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.RBinaries;

/**
 * The interface for running R binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface RBinariesExecutor extends BinariesExecutor<RBinaries> {

	/**
	 * Runs the script using RScript with the specified parameters.
	 *  
	 * @param script the file that contains the script
	 * @param args the parameters to be passed to Rscript
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult runScript(File script, String... args)
		throws ExecutionException, InterruptedException;
}
