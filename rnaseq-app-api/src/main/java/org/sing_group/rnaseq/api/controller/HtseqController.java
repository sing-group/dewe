/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
import org.sing_group.rnaseq.api.environment.execution.HtseqBinariesExecutor;

/**
 * The interface that defines the HTSeq controller.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqController {
	/**
	 * Sets the {@code HtseqBinariesExecutor} that should be used to execute
	 * HTSeq commands.
	 * 
	 * @param executor the {@code HtseqBinariesExecutor}
	 */	
	public abstract void setHtseqBinariesExecutor(
		HtseqBinariesExecutor executor);

	/**
	 * Counts how many reads in {@code inputBam} map to each feature or region 
	 * defined in the {@code referenceAnnotationFile}. Results are written to
	 * {@code output}.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file
	 * @param output the output file
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void countBamReverseExon(File referenceAnnotationFile,
		File inputBam, File output)
		throws ExecutionException, InterruptedException;

	/**
	 * Counts how many reads in each input file map to each feature or region 
	 * defined in the {@code referenceAnnotationFile}. Results are generated 
	 * in {@code outputDir} and joined into a unique file at {@code joinFile}.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBams the input BAM files
	 * @param outputDir the directory to store the results
	 * @param joinFile the results file
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void countBamReverseExon(File referenceAnnotationFile,
		File[] inputBams, File outputDir, File joinFile)
		throws ExecutionException, InterruptedException;
}
