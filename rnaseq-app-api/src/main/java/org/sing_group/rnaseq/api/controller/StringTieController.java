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
import java.util.List;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.StringTieBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.SystemBinariesExecutor;

/**
 * The interface for controlling StringTie commands.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 * @see <a href=
 *      "https://ccb.jhu.edu/software/stringtie/index.shtml?t=manual">StringTie
 *      reference manual</a>
 */
public interface StringTieController {
	/**
	 * Sets the {@code StringTieBinariesExecutor} that should be used to execute
	 * StringTie commands.
	 * 
	 * @param executor the {@code StringTieBinariesExecutor}
	 */
	public abstract void setStringTieBinariesExecutor(
		StringTieBinariesExecutor executor);

	/**
	 * Sets the {@code SystemBinariesExecutor} that should be used to execute
	 * system commands.
	 * 
	 * @param executor the {@code SystemBinariesExecutor}
	 */	
	public abstract void setSystemBinariesExecutor(
		SystemBinariesExecutor executor);

	/**
	 * Runs StringTie to obtain the labeled transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file containing the alignments
	 * @param outputTranscripts the output file
	 * @param label the prefix for the name of the output transcripts (StringTie
	 *        uses {@code STRG} as default)
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void obtainLabeledTranscripts(File referenceAnnotationFile,
		File inputBam, File outputTranscripts, String label)
		throws ExecutionException, InterruptedException;

	/**
	 * Runs StringTie to obtain the transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file containing the alignments
	 * @param outputTranscripts the output file
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void obtainTranscripts(File referenceAnnotationFile,
		File inputBam, File outputTranscripts)
		throws ExecutionException, InterruptedException;

	/**
	 * Takes a input a list of GTF/GFF files ({@code annotationFilesToMerge})
	 * and merges/assembles these transcripts into a non-redundant set of
	 * transcripts.
	 * 
	 * By providing a reference annotation file, StringTie will assemble the
	 * transfrags from the input GTF files with the reference transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param annotationFilesToMerge the GTF/GFF files to be merged
	 * @param mergedAnnotationFile the file to store the result
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract void mergeTranscripts(File referenceAnnotationFile, 
		List<File> annotationFilesToMerge, File mergedAnnotationFile)
		throws ExecutionException, InterruptedException;
}
