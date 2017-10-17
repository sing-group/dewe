/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.FastQcBinariesExecutor;

/**
 * The interface for controlling FastQC commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface FastQcController {
	/**
	 * Sets the {@code FastQcBinariesExecutor} that should be used to execute
	 * FastQC commands.
	 * 
	 * @param executor the {@code FastQcBinariesExecutor}
	 */	
	public abstract void setFastQcBinariesExecutor(
		FastQcBinariesExecutor executor);

	/**
	 * Produces a FastQC report for each input file in the specified output 
	 * directory.
	 * 
	 * @param reads the input sequence files
	 * @param outputDir the output directory to generate the reports
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract void fastqc(File[] reads, File outputDir)
		throws ExecutionException, InterruptedException;

	/**
	 * Produces a FastQC report for each input file. Each report file is 
	 * created in the same directory as the sequence file which was processed.
	 * 
	 * @param reads the input sequence files
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract void fastqc(File[] reads)
		throws ExecutionException, InterruptedException;
}
