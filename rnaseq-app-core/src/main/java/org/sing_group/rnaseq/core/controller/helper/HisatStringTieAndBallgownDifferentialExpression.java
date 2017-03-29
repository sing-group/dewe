package org.sing_group.rnaseq.core.controller.helper;

import java.io.File;

import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

public class HisatStringTieAndBallgownDifferentialExpression
	extends AbstractDifferentialExpressionWorkflow {

	private Hisat2Controller hisat2Controller;
	
	public HisatStringTieAndBallgownDifferentialExpression(
		Hisat2ReferenceGenome referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory) {
		super(referenceGenome, reads, referenceAnnotationFile,
			workingDirectory);
		this.hisat2Controller =
			DefaultAppController.getInstance().getHisat2Controller();

	}

	@Override
	protected void alignReads(File readsFile1, File readsFile2, File output)
		throws ExecutionException, InterruptedException {
		hisat2Controller.alignReads(
			(Hisat2ReferenceGenome) referenceGenome, 
			readsFile1, readsFile2, output
		);
	}

	@Override
	protected void performDifferentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setSubtaskProgress(0f);
		BallgownDifferentialExpressionAnalysis
			.ballgownDifferentialExpressionAnalysis(reads,
				referenceAnnotationFile, workingDirectory, status);
	}
}