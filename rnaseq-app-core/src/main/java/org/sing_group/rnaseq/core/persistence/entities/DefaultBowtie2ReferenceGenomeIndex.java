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
package org.sing_group.rnaseq.core.persistence.entities;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.Optional;

import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenomeIndex;

/**
 * The default {@code Bowtie2ReferenceGenomeIndex} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBowtie2ReferenceGenomeIndex
	implements Bowtie2ReferenceGenomeIndex {
	private static final long serialVersionUID = 1L;
	private static final String[] INDEXES = {
		".1.bt2", ".2.bt2", 	".3.bt2",
		".4.bt2", ".rev.1.bt2", ".rev.2.bt2"
	};

	private String name;
	private File referenceGenome;
	private String referenceGenomeIndex;
	
	/**
	 * Creates a new {@code DefaultBowtie2ReferenceGenomeIndex} with the
	 * specified {@code name} and {@code referenceGenome}. This constructor
	 * looks for index files in {@code indexFolder}.
	 * 
	 * @param name the genome index name
	 * @param referenceGenome the reference genome file
	 * @param indexDirectory the directory where indexes must be found 
	 */
	public DefaultBowtie2ReferenceGenomeIndex(String name, File referenceGenome,
		File indexDirectory
	) {
		this(name, referenceGenome, lookForIndex(indexDirectory));
	}
	
	/**
	/**
	 * Creates a new {@code DefaultBowtie2ReferenceGenomeIndex} with the
	 * specified {@code name}, {@code referenceGenome} and {@code index}. This
	 * index is a string containing the directory where the indexes are and the
	 * base name of the index (e.g. {@code /data/genome/index}).
	 * 
	 * @param name the genome index name
	 * @param referenceGenome the reference genome file
	 * @param index the name of the index
	 */
	public DefaultBowtie2ReferenceGenomeIndex(String name, File referenceGenome,
		String index
	) {
		this.name = name;
		this.referenceGenome = referenceGenome;
		this.referenceGenomeIndex = index;
	}

	private static String lookForIndex(File indexFolder) {
		if (indexFolder.isDirectory()) {
			Optional<File> firstIndexFile = asList(indexFolder.listFiles())
				.stream().filter(f -> f.getName().endsWith(INDEXES[5]))
				.findAny();

			if (firstIndexFile.isPresent()) {
				String absolutePath = firstIndexFile.get().getAbsolutePath();
				return 	absolutePath.substring(
							0, absolutePath.indexOf(INDEXES[5]));
			}
			throw new IllegalArgumentException("indexFolder must contain Bowtie2 indexes");
		}
		throw new IllegalArgumentException("indexFolder must be a directory");
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Optional<File> getReferenceGenome() {
		return Optional.ofNullable(referenceGenome);
	}

	@Override
	public String getReferenceGenomeIndex() {
		return referenceGenomeIndex;
	}

	@Override
	public boolean isValidIndex() {
		if (getReferenceGenomeIndex() == null) {
			return false;
		}

		return directoryContainsIndex(getReferenceGenomeIndex());
	}

	/**
	 * Returns {@code true} if {@code directory} contains all the Bowtie2 index
	 * files and {@code false} otherwise.
	 *
	 * @param directory the directory where Bowtie2 index files must be located
	 *
	 * @return {@code true} if {@code directory} contains all the Bowtie2 index
	 *         files and {@code false} otherwise
	 */
	public static boolean directoryContainsBowtie2Indexes(File directory) {
		try {
			String index = lookForIndex(directory);
			return directoryContainsIndex(index);
		} catch (IllegalArgumentException ex) {
			return false;
		}
	}

	private static boolean directoryContainsIndex(String index) {
		boolean valid = true;
		for (String extension : INDEXES) {
			valid = valid && new File(index + extension).exists();
		}
		return valid;
	}
}
