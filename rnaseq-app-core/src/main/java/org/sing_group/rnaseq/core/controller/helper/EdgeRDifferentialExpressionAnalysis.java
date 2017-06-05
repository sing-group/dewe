package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisSubDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getBamFile;

import java.io.File;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSample;
import org.sing_group.rnaseq.core.entities.edgeR.DefaultEdgeRSamples;

public class EdgeRDifferentialExpressionAnalysis {

	public static void edgeRDifferentialExpressionAnalysis(
		FastqReadsSamples reads,
		File referenceAnnotationFile, 
		File workingDirectory
	) throws ExecutionException, InterruptedException {
		File edgeRWorkingDir = getEdgeRWorkingDir(workingDirectory);
		EdgeRSamples edgeRSamples = getEdgeRSamples(reads, workingDirectory);
		
		DefaultAppController.getInstance().getEdgeRController()
			.differentialExpression(edgeRSamples, referenceAnnotationFile, edgeRWorkingDir);
	}

	private static EdgeRSamples getEdgeRSamples(FastqReadsSamples reads,
		File workingDirectory
	) {
		return new DefaultEdgeRSamples(
			reads.stream().map(r -> {
				String name = r.getName();
				String type = r.getCondition();
				File bam = getBamFile(r, workingDirectory);
				return new DefaultEdgeRSample(name, type, bam);
			}).collect(Collectors.toList())
		);
	}

	public static File getEdgeRWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "edger");
	}
}
