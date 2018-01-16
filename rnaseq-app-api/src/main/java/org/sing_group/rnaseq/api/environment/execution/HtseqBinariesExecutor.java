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
package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;

import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;

/**
 * The interface for running HTSeq binaries. 
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface HtseqBinariesExecutor extends BinariesExecutor<HtseqBinaries> {
	/**
	 * Counts how many reads in {@code inputBam} map to each feature or region 
	 * defined in the {@code referenceAnnotationFile}. Results are written to
	 * {@code output}.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file
	 * @param output the output file
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult countBamReverseExon(
		File referenceAnnotationFile, File inputBam, File output)
		throws ExecutionException, InterruptedException;
}
