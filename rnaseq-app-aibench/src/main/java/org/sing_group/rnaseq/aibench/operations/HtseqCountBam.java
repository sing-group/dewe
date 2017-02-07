package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Calculate reads counts using htseq-count", 
	description = "Takes an alignment file in SAM/BAM format and a feature "
		+ "file in GFF format and calculates for each feature the number of "
		+ "reads mapping to it."
)
public class HtseqCountBam {
	private File referenceAnnotationFile;
	private File inputBam;
	private File outputFile;

	@Port(
		direction = Direction.INPUT, 
		name = "Reference annotation file",
		description = "Reference annotation file (.gtf)",
		allowNull = false,
		order = 1,
		extras="selectionMode=files"
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
		extras="selectionMode=files"
	)
	public void setInputBamFile(File inputBam) {
		this.inputBam = inputBam;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output file",
		description = "The output file",
		allowNull = false,
		order = 3,
		extras="selectionMode=files"
	)
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getHtseqController().countBamReverseExon(referenceAnnotationFile, inputBam, outputFile);
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info(inputBam.getName() + " successfully analyzed to " + outputFile.getName() + ".");
	}
}
