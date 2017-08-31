package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.SamtoolsBinariesExecutor;

/**
 * The interface for controlling samtools commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamtoolsController {
	/**
	 * Sets the {@code SamtoolsBinariesExecutor} that should be used to execute
	 * samtools commands.
	 * 
	 * @param executor the {@code SamtoolsBinariesExecutor}
	 */	
	public abstract void setSamtoolsBinariesExecutor(
		SamtoolsBinariesExecutor executor);

	/**
	 * Converts a file in SAM format into a file in BAM format.
	 * 
	 * @param sam the input file in SAM format
	 * @param bam the output file in BAM format
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void samToBam(File sam, File bam)
		throws ExecutionException, InterruptedException;
}
