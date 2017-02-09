package org.sing_group.rnaseq.api.controller;

import java.io.File;
import java.util.List;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

public interface BallgownController {
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	public abstract void differentialExpression(List<BallgownSample> samples,
		File outputFolder)
		throws ExecutionException, InterruptedException;
}
