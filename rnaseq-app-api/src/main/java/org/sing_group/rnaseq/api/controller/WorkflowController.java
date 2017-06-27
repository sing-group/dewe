package org.sing_group.rnaseq.api.controller;

import java.io.File;

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

	/**
	 * Runs the differential expression workflow using bowtie2, StringTie and R
	 * (ballgown/edgeR).
	 *
	 * @param referenceGenome a {@code Bowtie2ReferenceGenomeIndex} index
	 * @param reads the {@code FastqReadsSamples} list
	 * @param referenceAnnotationFile the path to the reference annotation file
	 * @param workingDirectory the path to the working directory where the
	 *        results are stored
	 * @param status an {@code OperationStatus} object to monitor the analysis
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public abstract void runBowtieStringTieAndRDifferentialExpression(
		Bowtie2ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory,
		OperationStatus status
	)
		throws ExecutionException, InterruptedException;

	/**
	 * Runs the differential expression workflow using HISAT2, StringTie and the
	 * ballgown R library.
	 *
	 * @param referenceGenome a {@code Hisat2ReferenceGenomeIndex} index
	 * @param reads the {@code FastqReadsSamples} list
	 * @param referenceAnnotationFile the path to the reference annotation file
	 * @param workingDirectory the path to the working directory where the
	 *        results are stored
	 * @param status an {@code OperationStatus} object to monitor the analysis
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public abstract void runHisatStringTieAndBallgownDifferentialExpression(
		Hisat2ReferenceGenomeIndex referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory,
		OperationStatus status
	)
		throws ExecutionException, InterruptedException;
}
