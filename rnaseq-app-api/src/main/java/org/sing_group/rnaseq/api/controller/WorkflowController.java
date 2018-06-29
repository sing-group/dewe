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
import java.util.Map;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;

/**
 * The interface for controlling the execution of entire workflows.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface WorkflowController {

	public enum Parameters {
		BOWTIE2("Bowtie2"),
		HISAT2("Hisat2"),
		STRINGTIE_OBTAIN_LABELED("StringTie obtain labeled transcripts"),
		STRINGTIE_MERGE("StringTie merge transcripts"),
		STRINGTIE_OBTAIN("StringTie obtain transcripts");

		private String name;

		Parameters(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Runs the differential expression workflow using bowtie2, StringTie and R
	 * (Ballgown/edgeR).
	 *
	 * @param referenceGenome a {@code Bowtie2ReferenceGenomeIndex} index
	 * @param reads the {@code FastqReadsSamples} list
	 * @param referenceAnnotationFile the path to the reference annotation file
	 * @param commandLineApplicationsParameters a map containing additional
	 *        command-line parameters for the underlying software
	 * @param workingDirectory the path to the working directory where the
	 *        results are stored
	 * @param status an {@code OperationStatus} object to monitor the analysis
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void runBowtieStringTieAndRDifferentialExpression(
		Bowtie2ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		Map<Parameters, String> commandLineApplicationsParameters,
		File workingDirectory,
		OperationStatus status
	)
		throws ExecutionException, InterruptedException;

	/**
	 * Runs the differential expression workflow using HISAT2, StringTie and R
	 * (Ballgown/edgeR).
	 *
	 * @param referenceGenome a {@code Hisat2ReferenceGenomeIndex} index
	 * @param reads the {@code FastqReadsSamples} list
	 * @param referenceAnnotationFile the path to the reference annotation file
	 * @param commandLineApplicationsParameters a map containing additional
	 *        command-line parameters for the underlying software
	 * @param workingDirectory the path to the working directory where the
	 *        results are stored
	 * @param status an {@code OperationStatus} object to monitor the analysis
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void runHisatStringTieAndRDifferentialExpression(
		Hisat2ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		Map<Parameters, String> commandLineApplicationsParameters,
		File workingDirectory,
		OperationStatus status
	)
		throws ExecutionException, InterruptedException;
}
