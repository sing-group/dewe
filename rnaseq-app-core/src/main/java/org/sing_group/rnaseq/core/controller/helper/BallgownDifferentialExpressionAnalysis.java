package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisSubDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getSampleWorkingDir;

import java.io.File;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSample;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSamples;

public class BallgownDifferentialExpressionAnalysis {

	private static File getBallgownWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "ballgown");
	}

	public static void ballgownDifferentialExpressionAnalysis(
		FastqReadsSamples reads,
		File referenceAnnotationFile,
		File workingDirectory,
		ImageConfigurationParameter configuration
	) throws ExecutionException, InterruptedException {
		File ballgownWorkingDir = getBallgownWorkingDir(workingDirectory);
		BallgownSamples samples = getBallgownSamples(reads, workingDirectory);
		DefaultAppController.getInstance()
			.getBallgownController()
			.differentialExpression(samples, ballgownWorkingDir);
	}

	private static BallgownSamples getBallgownSamples(FastqReadsSamples reads,
		File workingDirectory
	) {
		return new DefaultBallgownSamples(
				reads.stream().map(r -> {
					String name = r.getName();
					String type = r.getCondition();
					File bam = getSampleWorkingDir(r, workingDirectory);
					return new DefaultBallgownSample(name, type, bam);
				}).collect(Collectors.toList())
			);
	}
}
