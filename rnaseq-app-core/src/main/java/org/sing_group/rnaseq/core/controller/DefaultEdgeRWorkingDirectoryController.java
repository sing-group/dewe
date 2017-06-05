package org.sing_group.rnaseq.core.controller;

import static org.sing_group.rnaseq.core.controller.DefaultEdgeRController.OUTPUT_FILE_DE_GENES;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.sing_group.rnaseq.api.controller.EdgeRWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.core.io.edger.EdgeRGenesTxtFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code EdgeRWorkingDirectoryController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultEdgeRWorkingDirectoryController
	implements EdgeRWorkingDirectoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(
		DefaultEdgeRWorkingDirectoryController.class);

	private File workingDirectory;

	public DefaultEdgeRWorkingDirectoryController(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Optional<EdgeRGenes> getGenes() {
		return loadGenes(OUTPUT_FILE_DE_GENES);
	}

	private Optional<EdgeRGenes> loadGenes(String genesFileName) {
		File genesFile = getWorkingDirFile(genesFileName);
		EdgeRGenes genes = null;
		if (genesFile.exists()) {
			try {
				genes = EdgeRGenesTxtFileLoader.loadFile(genesFile);
			} catch (IOException e) {
				LOGGER.warn("I/O error loading genes file "
					+ genesFile.getAbsolutePath());
			}
		}

		return Optional.ofNullable(genes);
	}

	protected File getWorkingDirFile(String file) {
		return new File(workingDirectory, file);
	}
}
