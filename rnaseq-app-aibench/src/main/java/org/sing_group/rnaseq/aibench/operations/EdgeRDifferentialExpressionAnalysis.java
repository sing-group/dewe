package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.EdgeRWorkingDirectory;
import org.sing_group.rnaseq.aibench.gui.dialogs.EdgeRDifferentialExpressionAnalysisParamsWindow;
import org.sing_group.rnaseq.api.entities.edger.EdgeRSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Differential expression analysis with EdgeR",
	description = "Performs a differential expression analysis using EdgeR."
)
public class EdgeRDifferentialExpressionAnalysis {
	private File workingDir;
	private File referenceAnnotationFile;
	private EdgeRSamples samples;

	@Port(
		direction = Direction.INPUT,
		name = "Working directory",
		description = "Working directory.",
		allowNull = false,
		order = 1,
		extras="selectionMode=directories"
	)
	public void setWorkingDir(File workingDir) {
		this.workingDir = workingDir;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Reference annotation file",
		description = "Reference annotation file (.gtf)",
		allowNull = false,
		order = 2,
		extras="selectionMode=files"
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	@Port(
		direction = Direction.INPUT,
		name = EdgeRDifferentialExpressionAnalysisParamsWindow.SAMPLES,
		description = "Samples",
		allowNull = false,
		order = 3
	)
	public void setEdgeRSamples(EdgeRSamples samples) {
		this.samples = samples;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getEdgeRController()
				.differentialExpression(this.samples,
					this.referenceAnnotationFile, this.workingDir);
			invokeLater(this::succeed);
			processOutputs();
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void processOutputs() {
		EdgeRWorkingDirectory edgeRDirectory =
			new EdgeRWorkingDirectory(this.workingDir);
		Core.getInstance().getClipboard()
			.putItem(edgeRDirectory, edgeRDirectory.getName());
	}

	private void succeed() {
		Workbench.getInstance().info(
			"Succeed: edgeR analysis results can be found at " +
			this.workingDir.getAbsolutePath() + "."
		);
	}
}
