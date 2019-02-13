/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.TrimmomaticBinaries;
import org.sing_group.rnaseq.api.environment.execution.parameters.trimmomatic.TrimmomaticParameter;

/**
 * The interface for running Trimmomatic binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface TrimmomaticBinariesExecutor
	extends BinariesExecutor<TrimmomaticBinaries> {
	
	/**
	 * Runs Trimmomatic in a single-end sample.
	 * 
	 * @param inputFile the input file
	 * @param outputFile the output file
	 * @param parameters parameters for Trimmomatic
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract ExecutionResult filterSingleEndReads(File inputFile,
		File outputFile, TrimmomaticParameter... parameters)
		throws ExecutionException, InterruptedException;
	
	/**
	 * Runs Trimmomatic in a paired-end sample.
	 * 
	 * @param reads1 the first pair of reads
	 * @param reads2 the second pair of reads
	 * @param outputBase the base for the output files
	 * @param parameters parameters for Trimmomatic
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract ExecutionResult filterPairedEndReads(File reads1,
		File reads2, File outputBase, TrimmomaticParameter... parameters)
		throws ExecutionException, InterruptedException;
}