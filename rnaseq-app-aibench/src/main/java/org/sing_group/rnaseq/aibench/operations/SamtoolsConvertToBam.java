package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.util.FileUtils;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Convert sam to sorted bam", 
	description = "Converts a sam file into a sorted bam file using samtools."
)
public class SamtoolsConvertToBam {
	private File input;
	private File output;

	@Port(
		direction = Direction.INPUT, 
		name = "Input sam file",
		description = "Input sam file",
		allowNull = false,
		order = 1,
		extras="selectionMode=files"
	)
	public void setInputFile(File file) {
		this.input = file;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output bam file",
		description = "Optionally, an output bam file. "
			+ "If not provided, a file with the same name that the input sam file will be used",
		allowNull = true,
		order = 2,
		extras="selectionMode=files"
	)
	public void setOutputFile(File file) {
		this.output = file == null ? getOutputFile() : file;

		this.runOperation();
	}

	private File getOutputFile() {
		return new File(input.getParentFile(), FileUtils.removeExtension(input) + ".bam");
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getSamtoolsController().samToBam(input, output);
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info(input.getName() + " successfully converted an sorted to " + output.getName() + ".");
	}
}
