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
import static org.sing_group.rnaseq.core.controller.DefaultPathfindRBallgownController.OUTPUT_FILE_PE;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.controller.PathfindRWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.pathfindr.PathfindRPathways;
import org.sing_group.rnaseq.core.io.pathfindr.PathfindRPathwaysTSVFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code PathfindRWorkingDirectoryController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultPathfindRWorkingDirectoryController
	implements PathfindRWorkingDirectoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(
		DefaultPathfindRWorkingDirectoryController.class);
	private static final List<String> VIEWABLE_WORKING_DIR_FILES = asList(
		OUTPUT_FILE_PE);

	private File workingDirectory;

	/**
	 * Creates a new {@code DefaultPathfindRWorkingDirectoryController} for the
	 * specified directory.
	 *
	 * @param workingDirectory the working directory to be controlled
	 */
	public DefaultPathfindRWorkingDirectoryController(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Optional<PathfindRPathways> getPathways() {
		return loadPathways(getWorkingDirFile(OUTPUT_FILE_PE));
	}

	
	private Optional<PathfindRPathways> loadPathways(File pathwaysFile) {
		PathfindRPathways pathways = null;
		if (pathwaysFile.exists()) {
			try {
				pathways = PathfindRPathwaysTSVFileLoader.loadFile(pathwaysFile);
			} catch (IOException e) {
				LOGGER.warn("I/O error loading genes file "
					+ pathwaysFile.getAbsolutePath());
			} 
		}

		return Optional.ofNullable(pathways);
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
}
