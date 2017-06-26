package org.sing_group.rnaseq.aibench.operations.workflow;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getBallgownWorkingDir;

import java.io.File;

import org.sing_group.rnaseq.aibench.datatypes.BallgownWorkingDirectory;
import org.sing_group.rnaseq.aibench.gui.util.AIBenchOperationStatus;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.environment.execution.parameters.DefaultImageConfigurationParameter;
import org.slf4j.LoggerFactory;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;

@Operation(
	name = "Gisat2, StringTie and Ballgown differential expression",
	description = "Runs the differential expression workflow using Hisat2, StringTie and Ballgown."
)
public class HisatStringTieAndBallgownDifferentialExpressionOperation {

	private AIBenchOperationStatus status = new AIBenchOperationStatus();
	private Hisat2ReferenceGenomeIndex referenceGenome;
	private FastqReadsSamples samples;
	private File referenceAnnotationFile;
	private File workingDirectory;

	@Port(
		direction = Direction.INPUT,
		name = "Hisat2 reference genome",
		description = "Hisat2 reference genome",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Hisat2ReferenceGenomeIndex referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	@Port(
		direction = Direction.INPUT,
		name = "FastQ samples",
		description = "FastQ samples",
		allowNull = false,
		order = 2
	)
	public void setSamples(FastqReadsSamples samples) {
		this.samples = samples;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Reference annotation file",
		description = "Reference annotation file",
		allowNull = false,
		order = 3,
		extras="selectionMode=files"
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Working directory",
		description = "Working directory",
		allowNull = false,
		order = 4,
		extras = "selectionMode=directories"
	)
	public void setWorkingDirectory(File workingDirectory) {
		this.workingDirectory = workingDirectory;

		this.runAnalysis();
	}

	private void runAnalysis() {
		try {
			//TODO: Get ImageConfigurationParameter from GUI
			DefaultImageConfigurationParameter imageConfiguration = 
					new DefaultImageConfigurationParameter(ImageConfigurationParameter.Format.JPEG, 1000, 1000, false);
			DefaultAppController.getInstance().getWorkflowController()
				.runHisatStringTieAndBallgownDifferentialExpression(
					this.referenceGenome, this.samples,
					this.referenceAnnotationFile, this.workingDirectory,
					imageConfiguration, this.status);
			processOutputs();
		} catch (ExecutionException | InterruptedException e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage());
		}
	}

	private void processOutputs() {
		BallgownWorkingDirectory ballgownDirectory =
			new BallgownWorkingDirectory(
				getBallgownWorkingDir(this.workingDirectory)
			);
		Core.getInstance().getClipboard()
			.putItem(ballgownDirectory, ballgownDirectory.getName());
	}

	@Progress(
		workingLabel = "Running workflow",
		progressDialogTitle = "Execution progress",
		modal = true
	)
	public OperationStatus getStatus(){
	      return this.status;
	}
}
