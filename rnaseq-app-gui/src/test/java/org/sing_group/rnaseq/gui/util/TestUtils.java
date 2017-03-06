package org.sing_group.rnaseq.gui.util;

import java.io.File;
import java.util.Optional;

import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

public class TestUtils {
	
	public static DefaultReferenceGenomeDatabaseManager createReferenceGenomeDatabaseManager() {
		DefaultReferenceGenomeDatabaseManager dbManager = 
			DefaultReferenceGenomeDatabaseManager.getInstance();
	
		dbManager.addReferenceGenome(new Bowtie2ReferenceGenome() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public boolean isValid() {
				return false;
			}
	
			@Override
			public String getType() {
				return "bowtie2";
			}
	
			@Override
			public Optional<String> getReferenceGenomeIndex() {
				return Optional.of("index");
			}
	
			@Override
			public File getReferenceGenome() {
				return new File("/home/users/dataRNA/data/genome.fa");
			}
		});
		dbManager.addReferenceGenome(new Bowtie2ReferenceGenome() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public boolean isValid() {
				return true;
			}
	
			@Override
			public String getType() {
				return "bowtie2";
			}
	
			@Override
			public Optional<String> getReferenceGenomeIndex() {
				return Optional.of("index");
			}
	
			@Override
			public File getReferenceGenome() {
				return new File("/data/genome-2.fa");
			}
		});
		return dbManager;
	}
}
