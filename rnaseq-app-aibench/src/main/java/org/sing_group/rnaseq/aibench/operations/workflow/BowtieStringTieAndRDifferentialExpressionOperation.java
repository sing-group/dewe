package org.sing_group.rnaseq.aibench.operations.workflow;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.util.AIBenchOperationStatus;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.api.progress.OperationStatus;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.slf4j.LoggerFactory;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;

@Operation(
	name = "bowtie2, StringTie and R (ballgown/edgeR) differential expression", 
	description = "Runs the differential expression workflow using bowtie2, StringTie and R (ballgown/edgeR)."
)
public class BowtieStringTieAndRDifferentialExpressionOperation {

	private AIBenchOperationStatus status = new AIBenchOperationStatus();
	private Bowtie2ReferenceGenome referenceGenome;
	private FastqReadsSamples samples;
	private File referenceAnnotationFile;
	private File workingDirectory;

	@Port(
		direction = Direction.INPUT, 
		name = "Bowtie2 reference genome",
		description = "Bowtie2 reference genome",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Bowtie2ReferenceGenome referenceGenome) {
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
			DefaultAppController.getInstance().getWorkflowController()
				.runBowtieStringTieAndRDifferentialExpression(
					this.referenceGenome, this.samples,
					this.referenceAnnotationFile, this.workingDirectory, 
					this.status);
		} catch (ExecutionException | InterruptedException e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage());
		}
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
