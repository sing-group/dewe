package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.BallgownDifferentialExpressionAnalysis.ballgownDifferentialExpressionAnalysis;
import static org.sing_group.rnaseq.core.controller.helper.EdgeRDifferentialExpressionAnalysis.edgeRDifferentialExpressionAnalysis;

import java.io.File;

import org.sing_group.rnaseq.api.controller.Bowtie2Controller;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2.DefaultBowtie2EndToEndConfiguration;

public class BowtieStringTieAndRDifferentialExpression
	extends AbstractDifferentialExpressionWorkflow {

	private Bowtie2Controller bowtie2Controller;

	public BowtieStringTieAndRDifferentialExpression(
		Bowtie2ReferenceGenomeIndex referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory
	) {
		super(referenceGenome, reads, referenceAnnotationFile,
			workingDirectory);
		this.bowtie2Controller =
			DefaultAppController.getInstance().getBowtie2Controller();
	}

	protected void alignReads(File readsFile1, File readsFile2, File output)
		throws ExecutionException, InterruptedException {
		bowtie2Controller.alignReads(
			(Bowtie2ReferenceGenomeIndex) referenceGenome,
			readsFile1, readsFile2,
			DefaultBowtie2EndToEndConfiguration.VERY_SENSITIVE,
			output, true);
	}

	@Override
	protected void performDifferentialExpressionAnalysis(OperationStatus status)
		throws ExecutionException, InterruptedException {
		status.setStageProgress(0f);
		status.setSubStage("Ballgown");
		ballgownDifferentialExpressionAnalysis(
			reads,referenceAnnotationFile, workingDirectory, imageConfiguration);
		status.setStageProgress(0.5f);
		status.setSubStage("EdgeR");
		edgeRDifferentialExpressionAnalysis(
			reads, referenceAnnotationFile, workingDirectory);
		status.setStageProgress(1f);
	}
}
