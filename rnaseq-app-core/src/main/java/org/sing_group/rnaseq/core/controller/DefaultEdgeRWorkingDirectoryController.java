/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.core.controller;

import static java.util.Arrays.asList;
import static org.sing_group.rnaseq.core.controller.DefaultEdgeRController.OUTPUT_FILE_DE_GENES;
import static org.sing_group.rnaseq.core.controller.DefaultEdgeRController.OUTPUT_FILE_DE_SIGNIFICANT_GENES;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.controller.EdgeRWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.edger.EdgeRGenes;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
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
	private static final List<String> VIEWABLE_WORKING_DIR_FILES = asList(
		OUTPUT_FILE_DE_GENES, OUTPUT_FILE_DE_SIGNIFICANT_GENES);

	private File workingDirectory;

	/**
	 * Creates a new {@code DefaultEdgeRWorkingDirectoryController} for the
	 * specified directory.
	 *
	 * @param workingDirectory the working directory to be controlled
	 */
	public DefaultEdgeRWorkingDirectoryController(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Optional<EdgeRGenes> getGenes() {
		return loadGenes(getWorkingDirFile(OUTPUT_FILE_DE_GENES));
	}

	@Override
	public Optional<EdgeRGenes> getSignificantGenes() {
		return loadGenes(getWorkingDirFile(OUTPUT_FILE_DE_SIGNIFICANT_GENES));
	}

	private Optional<EdgeRGenes> loadGenes(File genesFile) {
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

	@Override
	public List<String> getMissingWorkingDirectoryFiles() {
		List<String> missingFiles = new LinkedList<>();

		for (String fileName : VIEWABLE_WORKING_DIR_FILES) {
			File file = getWorkingDirFile(fileName);
			if (!file.exists()) {
				missingFiles.add(fileName);
			}
		}

		return missingFiles;
	}

	@Override
	public void createDEpValuesDistributionFigure(
		ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getEdgeRController()
			.createDEpValuesDistributionFigure(workingDirectory,
				imageConfiguration);

	}

	@Override
	public void createDEfoldChangeValuesDistributionFigure(
		ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getEdgeRController()
			.createDEfoldChangeValuesDistributionFigure(workingDirectory,
				imageConfiguration);
	}

	@Override
	public void createVolcanoFigure(
		ImageConfigurationParameter imageConfiguration
	) throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getEdgeRController()
			.createVolcanoFigure(workingDirectory, imageConfiguration);
	}
}
