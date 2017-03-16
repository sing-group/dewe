package org.sing_group.rnaseq.aibench.operations;

import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_SAM_FILES;

import java.io.File;
import java.util.Arrays;

import es.uvigo.ei.aibench.core.Core;
import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;

@Operation(
	name = "Convert several sam files to sorted bam files", 
	description = "Converts several sam files into sorted bam files using samtools."
)
public class SamtoolsBatchConvertToBam {
	private File[] inputFiles;

	@Port(
		direction = Direction.INPUT, 
		name = "Input sam files",
		description = "Input sam files",
		allowNull = false,
		order = 1,
		extras = EXTRAS_SAM_FILES
	)
	public void setInputFile(File[] files) {
		this.inputFiles = files;

		this.runOperation();
	}

	private void runOperation() {
		for(final File f : inputFiles) {
			Core.getInstance().executeOperation(
				"operations.samtoolsconvertsamtobam", null, 
				Arrays.asList(new Object[]{f, null})
			);
		}
	}
}

