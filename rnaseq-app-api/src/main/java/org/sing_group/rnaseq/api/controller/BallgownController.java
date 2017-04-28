package org.sing_group.rnaseq.api.controller;

import java.io.File;
import java.util.List;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;

/**
 * The interface for controlling the Ballgown R package.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BallgownController {
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	/**
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code outputFolder}. Note
	 * that there must be only two conditions and at least two samples in each
	 * one.
	 *
	 * @param samples the list of input {@code BallgownSample}s
	 * @param outputFolder the directory where resultst must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void differentialExpression(List<BallgownSample> samples,
		File outputFolder)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the FPKM distribution in conditions for the
	 * specified transcript id. Please, note that {@code workingDirectory} must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param transcriptId the transcript id
	 * @param format the output format of the image which can be {@code jpeg},
	 *        {@code tiff} or {@code png}
	 * @param width the image width
	 * @param height the image height
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createFpkmDistributionFigureForTranscript(
		File workingDirectory, String transcriptId, String format, int width,
		int height)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the structure and expression levels of a given
	 * transcript in a sample. Please, note that {@code workingDirectory} must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param transcriptId the transcript id
	 * @param sampleName the name of the sample
	 * @param format the output format of the image which can be {@code jpeg},
	 *        {@code tiff} or {@code png}
	 * @param width the image width
	 * @param height the image height
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createExpressionLevelsFigure(File workingDirectory,
		String transcriptId, String sampleName, String format, int width,
		int height)
		throws ExecutionException, InterruptedException;
}
