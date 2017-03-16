package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GENOME_FA_FILES;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenome;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Import bowtie2 index", 
	description = "Imports a reference genome indexed using bowtie2."
)
public class Bowtie2ImportIndex {
	private File file;
	private File indexDir;

	@Port(
		direction = Direction.INPUT, 
		name = "Genome",
		description = "Reference genome file.",
		allowNull = false,
		order = 1,
		extras = EXTRAS_GENOME_FA_FILES
	)
	public void setFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Index folder",
		description = "Folder containing the bowtie2 index.",
		allowNull = true,
		order = 2,
		extras = "selectionMode=directories",
		advanced = true
	)
	public void setOutputFolder(File outputDir) {
		this.indexDir = outputDir == null ? this.file.getParentFile() : outputDir;

		this.runOperation();
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance().getReferenceGenomeDatabaseManager().addReferenceGenome(
				new DefaultBowtie2ReferenceGenome(this.file, this.indexDir)
			);
			DefaultReferenceGenomeDatabaseManager.getInstance().persistDatabase();
			invokeLater(this::succeed);
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void succeed() {
		Workbench.getInstance().info("bowtie2 index successfully imported.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Importing bowtie2 index",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {};	
}
