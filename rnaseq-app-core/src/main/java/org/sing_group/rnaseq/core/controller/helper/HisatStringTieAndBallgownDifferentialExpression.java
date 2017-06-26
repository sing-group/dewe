package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.BallgownDifferentialExpressionAnalysis.ballgownDifferentialExpressionAnalysis;
import java.io.File;

import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.execution.parameters.DefaultImageConfigurationParameter;

public class HisatStringTieAndBallgownDifferentialExpression
	extends AbstractDifferentialExpressionWorkflow {

	private Hisat2Controller hisat2Controller;
	
	public HisatStringTieAndBallgownDifferentialExpression(
		Hisat2ReferenceGenomeIndex referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory, ImageConfigurationParameter imageConfiguration
	) {
		super(referenceGenome, reads, referenceAnnotationFile,
			workingDirectory, imageConfiguration);
		this.hisat2Controller =
			DefaultAppController.getInstance().getHisat2Controller();
	}

	@Override
	protected void alignReads(File readsFile1, File readsFile2, File output)
		throws ExecutionException, InterruptedException {
		hisat2Controller.alignReads(
			(Hisat2ReferenceGenomeIndex) referenceGenome, 
			readsFile1, readsFile2, true, output, true
		);
	}

	@Override
	protected void performDifferentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setStageProgress(0f);
		status.setSubStage("Ballgown");
		ballgownDifferentialExpressionAnalysis(
			reads, referenceAnnotationFile,	workingDirectory, imageConfiguration);
		status.setStageProgress(1f);
	}
}
