package org.sing_group.rnaseq.api.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.entities.ballgown.BallgownGenes;
import org.sing_group.rnaseq.api.entities.ballgown.BallgownTranscripts;
import org.sing_group.rnaseq.api.environment.execution.ExecutionException;
import org.sing_group.rnaseq.api.environment.execution.parameters.ImageConfigurationParameter;

/**
 * The interface for controlling the Ballgown R package working directory where
 * results generated by {@link BallgownController} are stored.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface BallgownWorkingDirectoryController {

	/**
	 * Returns {@code true} if the Ballgown structure is present and
	 * {@code false} otherwise.
	 *
	 * @return {@code true} if the Ballgown structure is present and
	 *         {@code false} otherwise
	 */
	public abstract boolean isBallgownStructurePresent();

	/**
	 * Returns {@code true} if the phenotype data file is present and
	 * {@code false} otherwise.
	 *
	 * @return {@code true} if the phenotype data file is present and
	 *         {@code false} otherwise
	 */
	public abstract boolean isPhenotypeFilePresent();

	/**
	 * If {@code isPhenotypeFilePresent} returns {@code true}, then returns the
	 * list of sample names. Otherwise it returns an empty {@code Optional}.
	 *
	 * @return the list of sample names wrapped in an {@code Optional}
	 */
	public abstract Optional<List<String>> getSampleNames();

	/**
	 * If the genes table is present, then it returns the {@code BallgownGenes}
	 * list. Otherwise it returns an empty {@code Optional}.
	 *
	 * @return the {@code BallgownGenes} list wrapped in an {@code Optional}
	 */
	public abstract Optional<BallgownGenes> getGenes();

	/**
	 * If the filtered genes table is present, then it returns the
	 * {@code BallgownGenes} list. Otherwise it returns an empty
	 * {@code Optional}.
	 *
	 * @return the {@code BallgownGenes} filtered list wrapped in an
	 *         {@code Optional}
	 */
	public abstract Optional<BallgownGenes> getFilteredGenes();

	/**
	 * If the significant filtered genes table is present, then it returns the
	 * {@code BallgownGenes} list. Otherwise it returns an empty
	 * {@code Optional}.
	 *
	 * @return the {@code BallgownGenes} significant filtered list wrapped in an
	 *         {@code Optional}
	 */
	public abstract Optional<BallgownGenes> getSignificantFilteredGenes();

	/**
	 * If the genes transcripts is present, then it returns the
	 * {@code BallgownTranscripts} list. Otherwise it returns an empty
	 * {@code Optional}.
	 *
	 * @return the {@code BallgownTranscripts} list wrapped in an
	 *         {@code Optional}
	 */
	public abstract Optional<BallgownTranscripts> getTranscripts();

	/**
	 * If the filtered transcripts table is present, then it returns the
	 * {@code BallgownTranscripts} list. Otherwise it returns an empty
	 * {@code Optional}.
	 *
	 * @return the {@code BallgownTranscripts} filtered list wrapped in an
	 *         {@code Optional}
	 */
	public abstract Optional<BallgownTranscripts> getFilteredTranscripts();

	/**
	 * If the significant filtered transcripts table is present, then it returns
	 * the {@code BallgownTranscripts} list. Otherwise it returns an empty
	 * {@code Optional}.
	 *
	 * @return the {@code BallgownTranscripts} significant filtered list wrapped
	 *         in an {@code Optional}
	 */
	public abstract Optional<BallgownTranscripts> getSignificantFilteredTranscripts();

	/**
	 * Creates the figure of the FPKM distribution in conditions for the
	 * specified transcript id. Please, note that the working directory must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param transcriptId the transcript id
	 * @param configuration the image configuration
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createFpkmDistributionFigureForTranscript(
		String transcriptId, ImageConfigurationParameter configuration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the structure and expression levels of a given
	 * transcript in a sample. Please, note that the working directory must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param transcriptId the transcript id
	 * @param sampleName the name of the sample
	 * @param configuration the image configuration
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createExpressionLevelsFigure(String transcriptId,
		String sampleName, ImageConfigurationParameter configuration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the FPKM distribution across the samples.
	 * Please, note that the working directory must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createFpkmDistributionAcrossSamplesFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the overall distribution of differential expression
	 * P values for genes.
	 * Please, note that the working directory must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createGenesDEpValuesFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Creates the figure of the overall distribution of differential expression
	 * P values for transcripts.
	 * Please, note that the working directory must
	 * contain the Ballgown data structure file created when
	 * {@code differentialExpression} is used.
	 *
	 * @param imageConfiguration the {@code ImageConfigurationParameter}
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract void createTranscriptsDEpValuesFigure(
		ImageConfigurationParameter imageConfiguration)
		throws ExecutionException, InterruptedException;

	/**
	 * Exports the filtered genes table removing those with a pValue over the
	 * specified threshold.
	 *
	 * @param pValue the maximum pValue for genes to be exported
	 * @return the file where the table has been exported
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract File exportFilteredGenesTable(double pValue)
		throws ExecutionException, InterruptedException;

	/**
	 * Exports the filtered transcripts table removing those with a pValue over
	 * the specified threshold.
	 *
	 * @param pValue the maximum pValue for transcripts to be exported
	 * @return the file where the table has been exported
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system
	 *         binary
	 */
	public abstract File exportFilteredTranscriptsTable(double pValue)
		throws ExecutionException, InterruptedException;

	/**
	 * Returns a list containing the names of the working directory files that
	 * have a view associated to them.
	 *
	 * @return a list containing the names of the working directory files that
	 *         have a view associated to them
	 */
	public abstract List<String> getMissingWorkingDirectoryFiles();
}
