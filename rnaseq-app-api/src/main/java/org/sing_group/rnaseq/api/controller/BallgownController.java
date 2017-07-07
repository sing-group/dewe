package org.sing_group.rnaseq.api.controller;

import java.io.File;
import java.util.List;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownSample;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.RBinariesExecutor;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;

/**
 * The interface for controlling the Ballgown R package.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BallgownController {
	/**
	 * Sets the {@code RBinariesExecutor} to use.
	 *
	 * @param executor the {@code RBinariesExecutor} to use
	 */
	public abstract void setRBinariesExecutor(
		RBinariesExecutor executor);

	/**
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code outputFolder}. Note
	 * that there must be only two conditions and at least two samples in each
	 * one.
	 *
	 * @param samples the list of input {@code BallgownSample}s
	 * @param outputFolder the directory where results must be stored
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void differentialExpression(List<BallgownSample> samples,
		File outputFolder) throws ExecutionException, InterruptedException;

	/**
	 * Performs the differential expression analysis between the groups of the
	 * samples in the list and stores the results in {@code outputFolder}. Note
	 * that there must be only two conditions and at least two samples in each
	 * one.
	 *
	 * @param samples the list of input {@code BallgownSample}s
	 * @param outputFolder the directory where results must be stored
	 * @param imageConfiguration the {@code ImageConfigurationParameter} to
	 *        create the images
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void differentialExpression(List<BallgownSample> samples,
		File outputFolder, ImageConfigurationParameter imageConfiguration)
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
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createFpkmDistributionFigureForTranscript(
		File workingDirectory, String transcriptId,
		ImageConfigurationParameter imageConfiguration)
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
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createExpressionLevelsFigure(File workingDirectory,
		String transcriptId, String sampleName,
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the FPKM distribution across the samples.
	 * Please, note that {@code workingDirectory} must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createFpkmDistributionAcrossSamplesFigure(
		File workingDirectory, ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the overall distribution of differential expression
	 * P values for genes.
	 * Please, note that {@code workingDirectory} must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createGenesDEpValuesFigure(File workingDirectory,
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the overall distribution of differential expression
	 * P values for transcripts.
	 * Please, note that {@code workingDirectory} must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createTranscriptsDEpValuesFigure(File workingDirectory,
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Exports the filtered genes table removing those with a pValue over the
	 * specified threshold.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param pValue the maximum pValue for genes to be exported
	 * @return the file where the table has been exported
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract File exportFilteredGenesTable(File workingDirectory,
		double pValue)
		throws ExecutionException, InterruptedException;

	/**
	 * Exports the filtered transcripts table removing those with a pValue over
	 * the specified threshold.
	 *
	 * @param workingDirectory directory that contains the Ballgown data
	 *        structure
	 * @param pValue the maximum pValue for transcripts to be exported
	 * @return the file where the table has been exported
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract File exportFilteredTranscriptsTable(File workingDirectory,
		double pValue)
		throws ExecutionException, InterruptedException;
}
