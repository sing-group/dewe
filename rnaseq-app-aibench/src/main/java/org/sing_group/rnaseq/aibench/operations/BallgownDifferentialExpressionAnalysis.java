package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getBallgownWorkingDir;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;
import org.sing_group.rnaseq.aibench.gui.dialogs.BallgownDifferentialExpressionAnalysisParamsWindow;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Differential expression analysis with Ballgown",
	description = "Performs a differential expression analysis using Ballgown."
)
public class BallgownDifferentialExpressionAnalysis {
	private BallgownSamples samples;
	private File directory;

	@Port(
		direction = Direction.INPUT,
		name = BallgownDifferentialExpressionAnalysisParamsWindow.SAMPLES,
		description = "List of samples to analyze",
		allowNull = false,
		order = 1
	)
	public void setSamples(BallgownSamples samples) {
		this.samples = samples;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Directory",
		description = "Directory to store analysis results",
		allowNull = false,
		order = 2,
		extras="selectionMode=directories"
	)
	public void setDirectory(File directory) {
		this.directory = directory;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getBallgownController()
				.differentialExpression(samples, this.directory);
			invokeLater(this::succeed);
			processOutputs();
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void processOutputs() {
		BallgownWorkingDirectory ballgownDirectory =
			new BallgownWorkingDirectory(
				getBallgownWorkingDir(this.directory)
			);
		Core.getInstance().getClipboard()
			.putItem(ballgownDirectory, ballgownDirectory.getName());
	}

	private void succeed() {
		Workbench.getInstance().info(
			"Succeed: ballgown analysis results can be found at " +
			this.directory.getAbsolutePath() + "."
		);
	}
}
