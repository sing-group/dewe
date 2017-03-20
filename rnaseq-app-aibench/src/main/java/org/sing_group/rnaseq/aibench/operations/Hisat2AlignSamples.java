package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.Hisat2AlignSamplesParamsWindow.REFERENCE_GENOME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_FASTQ_FILES;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Align reads using hisat2", 
	description = "Aligns paired-end reads using hisat2."
)
public class Hisat2AlignSamples {
	private Hisat2ReferenceGenome referenceGenome;
	private File readsFile1;
	private File readsFile2;
	private File outputFile;

	@Port(
		direction = Direction.INPUT, 
		name = REFERENCE_GENOME,
		description = "Reference genome index.",
		allowNull = false,
		order = 1
	)
	public void setReferenceGenome(Hisat2ReferenceGenome referenceGenome) {
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
			DefaultAppController.getInstance().getHisat2Controller()
				.alignReads(referenceGenome, readsFile1, readsFile2, outputFile);
			invokeLater(this::succeed);
		} catch (ExecutionException | InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void succeed() {
		Workbench.getInstance().info("Reads successfully aligned.");
	}
}
