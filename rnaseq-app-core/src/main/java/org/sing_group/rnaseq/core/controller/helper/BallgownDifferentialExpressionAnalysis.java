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
import static org.sing_group.rnaseq.core.controller.helper.AbstractDifferentialExpressionWorkflow.getSampleWorkingDir;

import java.io.File;
import java.util.stream.Collectors;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownSamples;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;
import org.sing_group.rnaseq.core.controller.DefaultAppController;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSample;
import org.sing_group.rnaseq.core.entities.ballgown.DefaultBallgownSamples;

/**
 * A class to encapsulate the execution of Ballgown differential expression
 * analyses.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BallgownDifferentialExpressionAnalysis {

	private static File getBallgownWorkingDir(File workingDirectory) {
		return getAnalysisSubDir(getAnalysisDir(workingDirectory), "ballgown");
	}

	/**
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code workingDirectory}.
	 * Note that there must be only two conditions and at least two samples in
	 * each one.
	 *
	 * @param reads the list of input {@code FastqReadsSample}s
	 * @param workingDirectory the directory where results must be stored
	 * @param configuration the {@code ImageConfigurationParameter} to
	 *        create the images
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public static void ballgownDifferentialExpressionAnalysis(
		FastqReadsSamples reads,
		File workingDirectory,
		ImageConfigurationParameter configuration
	) throws ExecutionException, InterruptedException {
		File ballgownWorkingDir = getBallgownWorkingDir(workingDirectory);
		BallgownSamples samples = getBallgownSamples(reads, workingDirectory);
		DefaultAppController.getInstance()
			.getBallgownController()
			.differentialExpression(samples, ballgownWorkingDir, configuration);
	}

	private static BallgownSamples getBallgownSamples(FastqReadsSamples reads,
		File workingDirectory
	) {
		return new DefaultBallgownSamples(
				reads.stream().map(r -> {
					String name = r.getName();
					String type = r.getCondition();
					File bam = getSampleWorkingDir(r, workingDirectory);
					return new DefaultBallgownSample(name, type, bam);
				}).collect(Collectors.toList())
			);
	}
}
