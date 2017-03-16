package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GTF_FILES;

import java.io.File;
import java.util.Arrays;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Reconstruct transcripts using StringTie", 
	description = "Reconstructs transcripts using StringTie."
)
public class StringTieMerge {
	private File referenceAnnotationFile;
	private File[] inputAnnotationFiles;
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
		name = "Input annotation files",
		description = "Input annotation files.",
		allowNull = false,
		order = 2,
		extras = EXTRAS_GTF_FILES
	)	
	public void setInputAnnotationFiles(File[] inputAnnotationFiles) {
		this.inputAnnotationFiles = inputAnnotationFiles;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output transcripts file",
		description = "Optionally, an output transcripts file (.gtf)." + 
			"If not provided, it will be created in the same directory than the reference annotation file",
		allowNull = true,
		order = 3,
		extras = "selectionMode=files"
	)
	public void setOutputTranscripts(File outputTranscripts) {
		this.outputTranscripts = outputTranscripts != null ? outputTranscripts :
			getOutputTranscriptsFile();

		this.runOperation();
	}

	private File getOutputTranscriptsFile() {
		return new File(this.referenceAnnotationFile.getParentFile(), "mergedAnnotation.gtf");
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getStringTieController()
				.mergeTranscripts(referenceAnnotationFile,
					Arrays.asList(this.inputAnnotationFiles),
					outputTranscripts);
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info("Transcript files successfully merged into " + outputTranscripts.getName() + ".");
	}
}

