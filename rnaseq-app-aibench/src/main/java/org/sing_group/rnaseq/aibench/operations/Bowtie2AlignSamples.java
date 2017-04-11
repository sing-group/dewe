package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.Bowtie2AlignSamplesParamsWindow.REFERENCE_GENOME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_FASTQ_FILES;

import java.io.File;

import org.sing_group.rnaseq.aibench.gui.util.FileOperationStatus;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Align reads using bowtie2", 
	description = "Aligns paired-end reads using bowtie2."
)
public class Bowtie2AlignSamples {
	private Bowtie2ReferenceGenome referenceGenome;
	private File readsFile1;
	private File readsFile2;
	private File outputFile;
	private boolean saveAlignmentLog;
	private FileOperationStatus status = new FileOperationStatus();

	@Port(
		direction = Direction.INPUT, 
		name = REFERENCE_GENOME,
		description = "Reference genome index.",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Bowtie2ReferenceGenome referenceGenome) {
		this.referenceGenome = referenceGenome;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Reads file 1",
		description = "Reads file 1.",
		allowNull = false,
		order = 2,
		extras = EXTRAS_FASTQ_FILES, 
		advanced = false
	)
	public void setReadsFile1(File readsFile1) {
		this.readsFile1 = readsFile1;
	}
	
	@Port(
		direction = Direction.INPUT, 
		name = "Reads file 2",
		description = "Reads file 2.",
		allowNull = false,
		order = 3,
		extras = EXTRAS_FASTQ_FILES,
		advanced = false
	)
	public void setReadsFile2(File readsFile2) {
		this.readsFile2 = readsFile2;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Save alignment log",
		description = "Whether the alignment log must be saved or not.",
		allowNull = false,
		order = 4,
		defaultValue = "true",
		advanced = false
	)
	public void setSaveAlignmentLog(boolean saveAlignmentLog) {
		this.saveAlignmentLog = saveAlignmentLog;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output file",
		description = "Output file.",
		allowNull = true,
		order = 5,
		extras = "selectionMode=files",
		advanced = false
	)
	public void setOutputFolder(File outputFile) {
		this.outputFile = outputFile.getName().endsWith(".sam") ? outputFile
				: new File(outputFile.getAbsolutePath() + ".sam");

		this.runOperation();
	}

	private void runOperation() {
		try {
			this.status.setStage(outputFile.getName());
			DefaultAppController.getInstance().getBowtie2Controller()
				.alignReads(referenceGenome, readsFile1, readsFile2, outputFile,
					saveAlignmentLog);
			invokeLater(this::succeed);
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void succeed() {
		Workbench.getInstance().info("Reads successfully aligned.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Bowtie2 reads alignment",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public FileOperationStatus progress() {
		return this.status;
	};
}
