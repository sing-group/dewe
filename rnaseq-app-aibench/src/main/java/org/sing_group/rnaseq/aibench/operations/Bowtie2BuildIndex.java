package org.sing_group.rnaseq.aibench.operations;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.core.util.FileUtils.removeExtension;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenome;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Build bowtie2 index", 
	description = "Builds a genome index using bowtie2."
)
public class Bowtie2BuildIndex {
	private File file;
	private File outputDir;

	@Port(
		direction = Direction.INPUT, 
		name = "Genome",
		description = "Reference genome file.",
		allowNull = false,
		order = 1,
		extras="selectionMode=files"
	)
	public void setFile(File file) {
		this.file = file;
	}

	@Port(
		direction = Direction.INPUT, 
		name = "Output folder",
		description = "Output folder.",
		allowNull = true,
		order = 2,
		extras="selectionMode=directories",
		advanced=false
	)
	public void setOutputFolder(File outputDir) {
		this.outputDir = outputDir == null ? this.file.getParentFile() : outputDir;

		this.runOperation();
	}

	private void runOperation() {
		try {
			String name = removeExtension(file) + "index";
			DefaultAppController.getInstance().getBowtie2Controller().buildIndex(this.file, this.outputDir, name);
			DefaultAppController.getInstance().getReferenceGenomeDatabaseManager().addReferenceGenome(
				new DefaultBowtie2ReferenceGenome(this.file, new File(outputDir, name).getAbsolutePath())
			);
			DefaultReferenceGenomeDatabaseManager.getInstance().persistDatabase();
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}
	
	private void succeed() {
		Workbench.getInstance().info("bowtie2 index successfully created.");
	}
}
