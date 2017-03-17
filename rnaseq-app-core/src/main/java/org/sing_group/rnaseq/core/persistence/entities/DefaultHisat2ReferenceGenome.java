package org.sing_group.rnaseq.core.persistence.entities;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.Optional;

import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

public class DefaultHisat2ReferenceGenome implements Bowtie2ReferenceGenome {
	private static final long serialVersionUID = 1L;
	private static final String[] INDEXES = { 
		".1.ht2", ".2.ht2", ".3.ht2", ".4.ht2",
		".5.ht2", ".6.ht2", ".7.ht2", ".8.ht2"
	};

	private File referenceGenome;
	private String referenceGenomeIndex;

	public DefaultHisat2ReferenceGenome(File file, String index) {
		this.referenceGenome = file;
		this.referenceGenomeIndex = index;
	}

	public DefaultHisat2ReferenceGenome(File file, File indexFolder) {
		this.referenceGenome = file;
		this.referenceGenomeIndex = lookForIndex(indexFolder);
	}

	private static String lookForIndex(File indexFolder) {
		if (indexFolder.isDirectory()) {
			Optional<File> firstIndexFile = asList(indexFolder.listFiles())
				.stream().filter(f -> f.getName().endsWith(INDEXES[0]))
				.findAny();

			if (firstIndexFile.isPresent()) {
				String absolutePath = firstIndexFile.get().getAbsolutePath();
				return 	absolutePath.substring(
							0, absolutePath.indexOf(INDEXES[0]));
			}
			throw new IllegalArgumentException("indexFolder must contain hisat2 indexes");
		}
		throw new IllegalArgumentException("indexFolder must be a directory");
	}

	@Override
	public String getType() {
		return "hisat2";
	}

	@Override
	public File getReferenceGenome() {
		return referenceGenome;
	}

	@Override
	public Optional<String> getReferenceGenomeIndex() {
		return Optional.ofNullable(referenceGenomeIndex);
	}
	
	@Override
	public boolean isValid() {
		return	this.referenceGenome.exists() && 
				this.isValidIndex();
	}

	private boolean isValidIndex() {
		if (!getReferenceGenomeIndex().isPresent()) {
			return false;
		} else {
			return directoryContainsIndex(getReferenceGenomeIndex().get());
		}
	}

	public static boolean directoryContainsHisat2Indexes(File directory) {
		return directoryContainsIndex(lookForIndex(directory));
	}
	
	private static boolean directoryContainsIndex(String index) {
		boolean valid = true;
		for (String extension : INDEXES) {
			valid = valid && new File(index + extension).exists();
		}
		return valid;
	}
}
