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
package org.sing_group.rnaseq.core.controller.helper;

import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisDir;
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getAnalysisSubDir;

import java.io.File;

import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.core.controller.DefaultAppController;

/**
 * A class to encapsulate the execution of the overlaps between Ballgown and
 * edgeR DE analysis.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownEdgeROverlapsAnalysis {

	/**
	 * <p>
	 * Detects overlaps between Ballgown and edgeR differential expression 
	 * analysis  and stores the results in {@code workingDirectory}.
	 * </p>
	 *
	 * @param workingDirectory the directory where results must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public static void ballgownEdgerROverlapAnalysis(
		File workingDirectory
	) throws ExecutionException, InterruptedException {
		File overlapsWorkingDir = getOverlapsWorkingDir(workingDirectory);

		DefaultAppController.getInstance().getDEOverlapsController()
			.ballgownEdgerROverlaps(getAnalysisDir(workingDirectory),
				overlapsWorkingDir);
	}	

	/**
	 * Returns the DE overlaps location in a given working directory.
	 *
	 * @param workingDirectory the working directory where the DE overlaps 
	 *        directory must be located.
	 * @return the DE overlaps location in a given working directory.
	 */
	public static File getOverlapsWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "overlaps");
	}
}
