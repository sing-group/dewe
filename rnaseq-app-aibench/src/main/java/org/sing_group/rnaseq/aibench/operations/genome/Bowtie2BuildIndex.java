package org.sing_group.rnaseq.aibench.operations.genome;

import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.GENOME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.NAME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GENOME_FA_FILES;
import static org.sing_group.rnaseq.core.util.FileUtils.removeExtension;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.aibench.operations.util.OperationsUtils;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultBowtie2ReferenceGenomeIndex;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Build Bowtie2 index",
	description = "Builds a genome index using Bowtie2."
)
public class Bowtie2BuildIndex {
	private String name;
	private File file;
	private File outputDir;

	@Port(
		direction = Direction.INPUT,
		name = GENOME,
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
		name = NAME,
		description = "Reference genome name.",
		allowNull = false,
		order = 2,
		validateMethod = "validateName"
	)
	public void setName(String name) {
		this.name = name;
	}

	public void validateName(String name) {
		OperationsUtils.validateName(name);
	}

	@Port(
		direction = Direction.INPUT,
		name = "Output folder",
		description = "Output folder.",
		allowNull = true,
		order = 3,
		extras = "selectionMode=directories",
		advanced = true
	)
	public void setOutputFolder(File outputDir) {
		this.outputDir = outputDir == null ? this.file.getParentFile() : outputDir;

		this.runOperation();
	}

	private void runOperation() {
		try {
			createIndex();
			addIndexToDatabase();
			invokeLater(this::succeed);
		} catch (ExecutionException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (InterruptedException e) {
			Workbench.getInstance().error(e, e.getMessage());
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private void createIndex() throws ExecutionException, InterruptedException {
		String name = removeExtension(file) + "index";
		DefaultAppController.getInstance().getBowtie2Controller()
			.buildIndex(this.file, this.outputDir, name);
	}

	private void addIndexToDatabase() throws IOException {
		DefaultAppController.getInstance()
		.getReferenceGenomeDatabaseManager().addIndex(
			new DefaultBowtie2ReferenceGenomeIndex(this.name, this.file,
				new File(outputDir, name).getAbsolutePath())
		);

		DefaultReferenceGenomeIndexDatabaseManager.getInstance().persistDatabase();
	}

	private void succeed() {
		Workbench.getInstance().info("bowtie2 index successfully created.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Creating bowtie2 index",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {};
}
