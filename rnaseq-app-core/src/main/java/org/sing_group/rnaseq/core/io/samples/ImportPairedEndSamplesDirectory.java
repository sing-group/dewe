/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
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
public class ImportPairedEndSamplesDirectory extends ImportSamplesDirectory {
	public static final String READS_FILE_1_REGEX = ".*_1\\.(fastq|fastq.gz|fq)";

	/**
	 * Imports the list of paired samples contained the specified directory. The
	 * condition of each sample is the name of {@code dataDir}.
	 * 
	 * @param dataDir the directory where sample files are stored
	 *
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
	 * 
	 * @return the list of {@code FastqReadsSamples} in the specified directory
	 */
	public static FastqReadsSamples importDirectory(File dataDir,
		String condition) {
		FastqReadsSamples toret = new DefaultFastqReadsSamples();
		for (File readsFile1 : listReadsFile1(dataDir)) {
			Optional<File> readsFile2Opt = lookForReadsFile2(readsFile1);
			if (readsFile2Opt.isPresent()) {
				toret.add(new DefaultFastqReadsSample(
					extractSampleNameFromReadsFile(readsFile1), condition,
					readsFile1, readsFile2Opt.get()));
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
}
