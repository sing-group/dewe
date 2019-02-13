/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
import static org.sing_group.rnaseq.core.controller.DefaultDEOverlapsController.OUTPUT_FILE_BALLGOWN_EDGER_OVERLAPS;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.controller.BallgownEdgeROverlapsWorkingDirectoryController;
import org.sing_group.rnaseq.api.entities.ballgownedgeroverlaps.BallgownEdgeROverlaps;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.VennDiagramConfigurationParameter;
import org.sing_group.rnaseq.core.io.ballgownedgeroverlaps.BallgownEdgeROverlapsTSVFileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default {@code BallgownEdgeROverlapsWorkingDirectoryController} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBallgownEdgeROverlapsWorkingDirectoryController
	implements BallgownEdgeROverlapsWorkingDirectoryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(
		DefaultBallgownEdgeROverlapsWorkingDirectoryController.class);
	private static final List<String> VIEWABLE_WORKING_DIR_FILES = asList(
		OUTPUT_FILE_BALLGOWN_EDGER_OVERLAPS);

	private File workingDirectory;

	/**
	 * Creates a new {@code DefaultPathfindRWorkingDirectoryController} for the
	 * specified directory.
	 *
	 * @param workingDirectory the working directory to be controlled
	 */
	public DefaultBallgownEdgeROverlapsWorkingDirectoryController(File workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	@Override
	public Optional<BallgownEdgeROverlaps> getOverlaps() {
		return loadOverlaps(getWorkingDirFile(OUTPUT_FILE_BALLGOWN_EDGER_OVERLAPS));
	}

	
	private Optional<BallgownEdgeROverlaps> loadOverlaps(File overlapsFile) {
		BallgownEdgeROverlaps overlaps = null;
		if (overlapsFile.exists()) {
			try {
				overlaps = BallgownEdgeROverlapsTSVFileLoader.loadFile(overlapsFile);
			} catch (IOException e) {
				LOGGER.warn("I/O error loading genes file "
					+ overlapsFile.getAbsolutePath());
			} 
		}
		return Optional.ofNullable(overlaps);
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
	public void createVennDiagram(VennDiagramConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException {
		DefaultAppController.getInstance().getDEOverlapsController()
			.createBallgownEdgerRVennDiagram(workingDirectory, imageConfiguration);
	}
}
