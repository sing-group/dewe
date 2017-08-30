package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.BallgownDifferentialExpressionAnalysis.ballgownDifferentialExpressionAnalysis;

import java.io.File;

import org.sing_group.rnaseq.api.controller.Hisat2Controller;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

/**
 * A concrete {@code HisatStringTieAndBallgownDifferentialExpression}
 * implementation to run a complete differential expression analysis using
 * HISAT2, StringTie and the Ballgown R package.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class HisatStringTieAndBallgownDifferentialExpression
	extends AbstractDifferentialExpressionWorkflow {

	private Hisat2Controller hisat2Controller;

	/**
	 * Creates a new {@code HisatStringTieAndBallgownDifferentialExpression}
	 * instance in order to perform a differential expression analysis.
	 *
	 * @param referenceGenome the reference genome to use in the analysis
	 * @param reads the {@code FastqReadsSamples} to analyze
	 * @param referenceAnnotationFile the reference annotation file
	 * @param workingDirectory the working directory to store the analysis
	 * 		  results
	 */
	public HisatStringTieAndBallgownDifferentialExpression(
		Hisat2ReferenceGenomeIndex referenceGenome, FastqReadsSamples reads,
		File referenceAnnotationFile, File workingDirectory
	) {
		super(referenceGenome, reads, referenceAnnotationFile,
			workingDirectory);
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
			reads, workingDirectory, imageConfiguration);
		status.setStageProgress(1f);
	}
}
