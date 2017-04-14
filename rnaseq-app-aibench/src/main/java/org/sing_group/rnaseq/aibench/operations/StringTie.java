package org.sing_group.rnaseq.aibench.operations;

import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_BAM_FILES;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GTF_FILES;

import static javax.swing.SwingUtilities.invokeLater;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.util.FileOperationStatus;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.util.FileUtils;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Reconstruct transcripts using StringTie", 
	description = "Reconstructs transcripts using StringTie."
)
public class StringTie {
	private FileOperationStatus status = new FileOperationStatus();	
	private File referenceAnnotationFile;
	private File inputBam;
	private File outputTranscripts;

	@Port(
		direction = Direction.INPUT, 
		name = "Reference annotation file",
		description = "Reference annotation file (.gtf)",
		allowNull = false,
		order = 1,
		extras = EXTRAS_GTF_FILES
	)
	public void setReferenceAnnotationFile(File referenceAnnotationFile) {
		this.referenceAnnotationFile = referenceAnnotationFile;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Input bam file",
		description = "Input bam file.",
		allowNull = false,
		order = 2,
		extras = EXTRAS_BAM_FILES
	)
	public void setInputBamFile(File inputBam) {
		this.inputBam = inputBam;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output transcripts file",
		description = "Optionally, an output transcripts file (.gtf)." + 
			"If not provided, it will be created in the same directory than "
			+ "the input bam file",
		allowNull = true,
		order = 3,
		extras="selectionMode=files"
	)
	public void setOutputTranscripts(File outputTranscripts) {
		this.outputTranscripts = outputTranscripts != null ? outputTranscripts :
			getOutputTranscriptsFile();

		this.runOperation();
	}

	private File getOutputTranscriptsFile() {
		return new File(this.inputBam.getParentFile(),
			FileUtils.removeExtension(this.inputBam) + ".gtf");
	}

	private void runOperation() {
		try {
			this.status.setStage(inputBam.getName());
			DefaultAppController.getInstance().getStringTieController()
				.obtainTranscripts(referenceAnnotationFile, inputBam,
					outputTranscripts);
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info(inputBam.getName() + " successfully transcripted to " + outputTranscripts.getName() + ".");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Obtaining transcripts",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public FileOperationStatus progress() {
		return this.status;
	}
}
