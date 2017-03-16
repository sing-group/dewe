package org.sing_group.rnaseq.aibench.operations.batch;

import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_BAM_FILES;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GTF_FILES;

import java.io.File;
import java.util.Arrays;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;

@Operation(
	name = "Reconstruct transcripts using StringTie", 
	description = "Reconstructs transcripts using StringTie."
)
public class StringTieBatch {
	private File referenceAnnotationFile;
	private File[] inputBamFiles;

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
		name = "Input bam files",
		description = "Input bam files.",
		allowNull = false,
		order = 2,
		extras = EXTRAS_BAM_FILES
	)
	public void setInputBamFile(File[] inputBamFiles) {
		this.inputBamFiles = inputBamFiles;

		this.runOperation();
	}

	private void runOperation() {
		for(final File f : inputBamFiles) {
			Core.getInstance().executeOperation(
				"operations.stringtie", null, 
				Arrays.asList(new Object[]{this.referenceAnnotationFile, f, null})
			);
		}
	}
}

