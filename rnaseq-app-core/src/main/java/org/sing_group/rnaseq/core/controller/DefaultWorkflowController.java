package org.sing_group.rnaseq.core.controller;

import java.io.File;

import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.helper.BowtieStringTieAndRDifferentialExpression;

public class DefaultWorkflowController implements WorkflowController {

	@Override
	public void runBowtieStringTieAndRDifferentialExpression(
		Bowtie2ReferenceGenome referenceGenome,
		FastqReadsSamples reads, 
		File referenceAnnotationFile,
		File workingDirectory, 
		OperationStatus status
	) throws ExecutionException, InterruptedException {
		BowtieStringTieAndRDifferentialExpression.runAnalysis(referenceGenome,
			reads, referenceAnnotationFile, workingDirectory, status);
	}

}
