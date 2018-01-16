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
package org.sing_group.rnaseq.core.io.samples;

import java.io.File;
import java.util.Optional;

import org.sing_group.rnaseq.core.util.FileUtils;

/**
 * An abstract class to import samples from a directory.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public abstract class ImportSamplesDirectory {
	public static final String[] FASTQ_EXTENSIONS =
		{ ".fq", ".fastq", ".fastq.gz" };

	/**
	 * Given a reads file 1 (with "_1" in its name), it looks for its 2 mate.
	 * 
	 * @param readsFile1 the reads file 1
	 * @return the reads file 2 wrapped as an {@code Optional}
	 */
	public static Optional<File> lookForReadsFile2(File readsFile1) {
		for (String extension : FASTQ_EXTENSIONS) {
			File readsFile2 = new File(readsFile1.getAbsolutePath()
				.replace("_1" + extension, "_2" + extension));
			if (
				readsFile2.exists() && 
				!readsFile1.getAbsolutePath().equals(readsFile2.getAbsolutePath())
			) {
				return Optional.of(readsFile2);
			}
		}
		return Optional.empty();
	}

	protected static final String extractSampleNameFromReadsFile(
		File readsFile) {
		String fileName = readsFile.getName();
		if (fileName.contains("_1.")) {
			return fileName.substring(0, fileName.indexOf("_1."));
		} else {
			return FileUtils.removeExtension(readsFile);
		}
	}
}
