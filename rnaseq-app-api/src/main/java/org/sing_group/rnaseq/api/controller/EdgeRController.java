package org.sing_group.rnaseq.api.controller;

import java.io.File;

import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

public interface EdgeRController {
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	public abstract void differentialExpression(File workingDir)
		throws ExecutionException, InterruptedException;

	public abstract void differentialExpression(EdgeRSamples samples, 
		File referenceAnnotationFile, File workingDir)
		throws ExecutionException, InterruptedException;
}
