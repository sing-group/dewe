package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;
import org.sing_group.rnaseq.api.progress.OperationStatus;

public interface WorkflowController {
	
	public abstract void runBowtieStringTieAndRDifferentialExpression(
		Bowtie2ReferenceGenome referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory,
		OperationStatus status
	)
		throws ExecutionException, InterruptedException;

	public abstract void runHisatStringTieAndBallgownDifferentialExpression(
		Hisat2ReferenceGenome referenceGenome,
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory,
		OperationStatus status
	)
		throws ExecutionException, InterruptedException;	
}
