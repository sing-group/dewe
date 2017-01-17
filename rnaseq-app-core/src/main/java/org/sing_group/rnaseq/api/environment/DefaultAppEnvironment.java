package org.sing_group.rnaseq.api.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.DefaultBowtie2Binaries;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;

public class DefaultAppEnvironment implements AppEnvironment {
	
	public static final String PROP_DATABASES_DIR = "databases.directory";
	public static final String PROP_REFERENCE_GENOME_FILE = "genomes.db";
	
	private File propertiesFile;
	private Properties defaultProperties;
	private DefaultBowtie2Binaries bowtie2Binaries;
	private DefaultReferenceGenomeDatabaseManager referenceGenomeDatabaseManager;

	public DefaultAppEnvironment(File propertiesFile) 
		throws FileNotFoundException, IOException {
		this.propertiesFile = propertiesFile;
		this.defaultProperties = new Properties();
		
		if (this.propertiesFile != null) {
			this.defaultProperties.load(new FileReader(this.propertiesFile));
		}

		for (String property : new String[] {
				Bowtie2Binaries.BASE_DIRECTORY_PROP,
				PROP_DATABASES_DIR 
		}) {
			if (!this.hasProperty(property)) {
				throw new IllegalStateException(
					"Missing property in configuration file: " + property);
			}
		}

		this.bowtie2Binaries = new DefaultBowtie2Binaries(
			this.getProperty(Bowtie2Binaries.BASE_DIRECTORY_PROP)
		);
		
		try {
			this.initReferenceGenomeDatabaseManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initReferenceGenomeDatabaseManager() throws IOException, ClassNotFoundException {
		referenceGenomeDatabaseManager = DefaultReferenceGenomeDatabaseManager.getInstance();
		
		File referenceGenomeDatabasefile = getReferenceGenomeDatabaseFile();
		referenceGenomeDatabaseManager.setPersistenceStorageFile(referenceGenomeDatabasefile);
		if(!referenceGenomeDatabasefile.exists()) {
			referenceGenomeDatabaseManager.persistDatabase();
		}
		referenceGenomeDatabaseManager.loadDatabase();
	}

	private File getReferenceGenomeDatabaseFile() {
		File databasesDir = getDatabasesDirectory();
		return new File(databasesDir, PROP_REFERENCE_GENOME_FILE);
	}

	private File getDatabasesDirectory() {
		File databasesDirectory = new File(getProperty(PROP_DATABASES_DIR));
		if (!databasesDirectory.exists()) {
			databasesDirectory.mkdirs();
		}
		return databasesDirectory;
	}

	@Override
	public String getProperty(String propertyName) {
		String propertyValue = System.getProperty(propertyName);
		
		if (propertyValue == null)
			propertyValue = this.defaultProperties.getProperty(propertyName);
		
		return propertyValue;
	}
	
	@Override
	public boolean hasProperty(String propertyName) {
		return System.getProperty(propertyName) != null ||
			this.defaultProperties.containsKey(propertyName);
	}

	@Override
	public Bowtie2Binaries getBowtie2Binaries() {
		return this.bowtie2Binaries;
	}

	@Override
	public DefaultReferenceGenomeDatabaseManager getReferenceGenomeDatabaseManager() {
		return referenceGenomeDatabaseManager;
	}
}
