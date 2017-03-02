package org.sing_group.rnaseq.core.persistence.entities;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.Optional;

import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;

public class DefaultBowtie2ReferenceGenome implements Bowtie2ReferenceGenome {
	private static final long serialVersionUID = 1L;
	private static final String[] INDEXES = { 
		".1.bt2", ".2.bt2", 	".3.bt2",
		".4.bt2", ".rev.1.bt2", ".rev.2.bt2" 
	};

	private File referenceGenome;
	private String referenceGenomeIndex;

	public DefaultBowtie2ReferenceGenome(File file, String index) {
		this.referenceGenome = file;
		this.referenceGenomeIndex = index;
	}

	public DefaultBowtie2ReferenceGenome(File file, File indexFolder) {
		this.referenceGenome = file;
		this.referenceGenomeIndex = lookForIndex(indexFolder);
	}

	private String lookForIndex(File indexFolder) {
		if (indexFolder.isDirectory()) {
			Optional<File> firstIndexFile = asList(indexFolder.listFiles())
				.stream().filter(f -> f.getName().endsWith(INDEXES[5]))
				.findAny();

			if (firstIndexFile.isPresent()) {
				String absolutePath = firstIndexFile.get().getAbsolutePath();
				return 	absolutePath.substring(
							0, absolutePath.indexOf(INDEXES[5]));
			}
			throw new IllegalArgumentException("indexFolder must contain bowtie2 indexes");
		}
		throw new IllegalArgumentException("indexFolder must be a directory");
	}

	@Override
	public String getType() {
		return "bowtie2";
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
		}

		String index = getReferenceGenomeIndex().get();
		boolean valid = true;
		for (String extension : INDEXES) {
			valid = valid && new File(index + extension).exists();
		}
		return valid;
	}
}
