package org.sing_group.rnaseq.aibench.operations.genome;

import static org.sing_group.rnaseq.core.persistence.entities.DefaultHisat2ReferenceGenome.directoryContainsHisat2Indexes;
import static javax.swing.SwingUtilities.invokeLater;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.NAME;
import static org.sing_group.rnaseq.aibench.gui.dialogs.ReferenceGenomeOperationParamsWindow.GENOME;
import static org.sing_group.rnaseq.aibench.gui.util.PortConfiguration.EXTRAS_GENOME_FA_FILES;

import java.io.File;
import java.io.IOException;

import org.sing_group.rnaseq.api.persistence.entities.ReferenceGenome;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultHisat2ReferenceGenome;

import es.uvigo.ei.aibench.core.operation.annotation.Direction;
import es.uvigo.ei.aibench.core.operation.annotation.Operation;
import es.uvigo.ei.aibench.core.operation.annotation.Port;
import es.uvigo.ei.aibench.core.operation.annotation.Progress;
import es.uvigo.ei.aibench.workbench.Workbench;

@Operation(
	name = "Import HISAT2 index",
	description = "Imports a reference genome indexed using HISAT2."
)
public class Hisat2ImportIndex {
	private String name;
	private File file;
	private File indexDir;

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
		order = 2
	)
	public void setName(String name) {
		this.name = name;
	}

	@Port(
		direction = Direction.INPUT,
		name = "Index folder",
		description = "Folder containing the hisat2 index.",
		allowNull = true,
		order = 3,
		extras = "selectionMode=directories",
		advanced = true,
		validateMethod = "validateIndexDirectory"
	)
	public void setIndexDirectory(File indexDir) {
		this.indexDir = indexDir == null ? this.file.getParentFile() : indexDir;

		this.runOperation();
	}

	public void validateIndexDirectory(File indexDir) {
		if(!directoryContainsHisat2Indexes(indexDir)) {
			throw new IllegalArgumentException(
				"Index directory must contain the hisat2 indexes files");
		}
	}

	private void runOperation() {
		try {
			DefaultAppController.getInstance()
				.getReferenceGenomeDatabaseManager()
				.addReferenceGenome(createReferengeGenome()
			);
			DefaultReferenceGenomeDatabaseManager.getInstance().persistDatabase();
			invokeLater(this::succeed);
		} catch (IOException e) {
			Workbench.getInstance().error(e, e.getMessage());
		}
	}

	private ReferenceGenome createReferengeGenome() {
		return new DefaultHisat2ReferenceGenome(this.name, this.file,
			this.indexDir);
	}

	private void succeed() {
		Workbench.getInstance().info("hisat2 index successfully imported.");
	}

	@Progress(
		progressDialogTitle = "Progress",
		workingLabel = "Importing hisat2 index",
		preferredHeight = 200,
		preferredWidth = 300
	)
	public void progress() {};
}
