package org.sing_group.rnaseq.core.io.samples;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSample;
import org.sing_group.rnaseq.core.entities.DefaultFastqReadsSamples;

/**
 * Imports the list of paired samples contained in a given directory. For
 * example, in the following directory: 
 * +---my-data-dir 
 * |   +---sample1_1.fastq
 * |   +---sample1_2.fastq
 * |   +---sample2_1.fastq
 * |   +---sample2_2.fastq
 * 
 * Two samples with names <i>sample1</i> and <i>sample2</i> can be found. 
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class ImportPairedSamplesDirectory {
	public static final String[] FASTQ_EXTENSIONS =
		{ ".fq", ".fastq", ".fastq.gz" };
	public static final String READS_FILE_1_REGEX = ".*_1\\.(fastq|fastq.gz|fq)";

	/**
	 * Imports the list of paired samples contained the specified directory. The
	 * condition of each sample is the name of {@code dataDir}.
	 * 
	 * @param dataDir the directory where sample files are stored
	 * @return the list of {@code FastqReadsSamples} in the specified directory
	 */
	public static FastqReadsSamples importDirectory(File dataDir) {
		return importDirectory(dataDir, dataDir.getName());
	}

	/**
	 * Imports the list of paired samples contained the specified directory.
	 * 
	 * @param dataDir the directory where sample files are stored
	 * @param condition the condition to which samples are associated
	 * @return the list of {@code FastqReadsSamples} in the specified directory
	 */
	public static FastqReadsSamples importDirectory(File dataDir, String condition) {
		FastqReadsSamples toret = new DefaultFastqReadsSamples();
		for (File readsFile1 : listReadsFile1(dataDir)) {
			Optional<File> readsFile2Opt = lookForReadsFile2(readsFile1);
			if (readsFile2Opt.isPresent()) {
				toret.add(new DefaultFastqReadsSample(
					extractSampleNameFromReadsFile(readsFile1),
					condition, readsFile1, readsFile2Opt.get())
				);
			}
		}
		return toret;
	}

	private static List<File> listReadsFile1(File dataDir) {
		return Arrays.asList(dataDir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.matches(READS_FILE_1_REGEX);
			}
		}));
	}

	/**
	 * Given a reads file 1 (with "_1" in its name), it looks for its 2 mate.
	 * 
	 * @param readsFile1 the reads file 1
	 * @return the reads file 2 wrapped as an {@code Optional}
	 */
	public static Optional<File> lookForReadsFile2(File readsFile1) {
		for(String extension : FASTQ_EXTENSIONS) {
			File readsFile2 = new File(
				readsFile1.getAbsolutePath()
				.replace("_1" + extension, "_2" + extension)
			);
			if (readsFile2.exists() && !readsFile1.equals(readsFile2)) {
				return Optional.of(readsFile2);
			}
		}
		return Optional.empty();
	}

	private static final String extractSampleNameFromReadsFile(File readsFile) {
		String fileName = readsFile.getName();
		if (fileName.contains(".")) {
			return fileName.substring(0, fileName.indexOf("_1."));
		} else {
			return fileName;
		}
	}
}
