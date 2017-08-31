package org.sing_group.rnaseq.api.environment.execution;

import java.io.File;
import java.util.List;

import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;

/**
 * The interface for running StringTie binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface StringTieBinariesExecutor
	extends BinariesExecutor<StringTieBinaries> {
	
	/**
	 * Runs StringTie to obtain the labeled transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file containing the alignments
	 * @param outputTranscripts the output file
	 * @param label the prefix for the name of the output transcripts (StringTie
	 *        uses {@code STRG} as default)
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */	
	public abstract ExecutionResult obtainLabeledTranscripts(
		File referenceAnnotationFile, File inputBam, File outputTranscripts,
		String label) throws ExecutionException, InterruptedException;

	/**
	 * Runs StringTie to obtain the transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param inputBam the input BAM file containing the alignments
	 * @param outputTranscripts the output file
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult obtainTranscripts(
		File referenceAnnotationFile, File inputBam, File outputTranscripts)
		throws ExecutionException, InterruptedException;

	/**
	 * Takes a input a list of GTF/GFF files (contained in the plain txt file
	 * {@code mergeList}) and merges/assembles these transcripts into a 
	 * non-redundant set of transcripts.
	 * 
	 * By providing a reference annotation file, StringTie will assemble the
	 * transfrags from the input GTF files with the reference transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param mergeList the GTF/GFF files to be merged
	 * @param mergedAnnotationFile the file to store the result
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult mergeTranscripts(
		File referenceAnnotationFile, File mergeList, File mergedAnnotationFile)
		throws ExecutionException, InterruptedException;

	/**
	 * Takes a input a list of GTF/GFF files ({@code annotationFilesToMerge})
	 * and merges/assembles these transcripts into a non-redundant set of
	 * transcripts.
	 * 
	 * By providing a reference annotation file, StringTie will assemble the
	 * transfrags from the input GTF files with the reference transcripts.
	 * 
	 * @param referenceAnnotationFile the reference annotation file
	 * @param annotationFilesToMerge the GTF/GFF files to be merged
	 * @param mergedAnnotationFile the file to store the result
	 * 
	 * @return the {@code ExecutionResult}
	 * 
	 * @throws ExecutionException if an error occurs during the execution
	 * @throws InterruptedException if an error occurs executing the system 
	 *         binary
	 */
	public abstract ExecutionResult mergeTranscripts(
		File referenceAnnotationFile, List<File> annotationFilesToMerge,
		File mergedAnnotationFile)
		throws ExecutionException, InterruptedException;
}
