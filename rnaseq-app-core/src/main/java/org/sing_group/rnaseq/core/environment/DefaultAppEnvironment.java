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
package org.sing_group.rnaseq.core.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import org.sing_group.rnaseq.api.environment.AppEnvironment;
import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.FastQcBinaries;
import org.sing_group.rnaseq.api.environment.binaries.Hisat2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.HtseqBinaries;
import org.sing_group.rnaseq.api.environment.binaries.RBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SamtoolsBinaries;
import org.sing_group.rnaseq.api.environment.binaries.StringTieBinaries;
import org.sing_group.rnaseq.api.environment.binaries.SystemBinaries;
import org.sing_group.rnaseq.api.environment.binaries.TrimmomaticBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultBowtie2Binaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultFastQcBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultHisat2Binaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultHtseqBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultRBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultSamtoolsBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultStringTieBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultSystemBinaries;
import org.sing_group.rnaseq.core.environment.binaries.DefaultTrimmomaticBinaries;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;

/**
 * The default {@code AppEnvironment} implementation.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultAppEnvironment implements AppEnvironment {

	public static final String PROP_NUM_THREADS = "threads";
	public static final String PROP_DATABASES_DIR = "databases.directory";
	public static final String PROP_REFERENCE_GENOME_FILE = "genomes.db";

	private File propertiesFile;
	private Properties defaultProperties;
	private int cores;
	private DefaultBowtie2Binaries bowtie2Binaries;
	private DefaultSamtoolsBinaries samtoolsBinaries;
	private DefaultStringTieBinaries stringTieBinaries;
	private DefaultHtseqBinaries htseqBinaries;
	private DefaultRBinaries rBinaries;
	private DefaultSystemBinaries systemBinaries;
	private DefaultHisat2Binaries hisat2Binaries;
	private DefaultFastQcBinaries fastQcBinaries;
	private DefaultTrimmomaticBinaries trimmomaticBinaries;
	private DefaultReferenceGenomeIndexDatabaseManager referenceGenomeDatabaseManager;

	/**
	 * Creates a new {@code DefaultAppEnvironment} that is configured using the
	 * specified properties file.
	 *
	 * @param propertiesFile the properties file with the environment
	 *        configuration
	 * @throws FileNotFoundException if the file does not exist, is a directory
	 *        rather than a regular file, or for some other reason cannot be
	 *        opened for reading.
	 * @throws IOException if an error occurred when reading from the input
	 *        stream
	 */
	public DefaultAppEnvironment(File propertiesFile)
		throws FileNotFoundException, IOException {
		this.propertiesFile = propertiesFile;
		this.defaultProperties = new Properties();

		if (this.propertiesFile != null) {
			this.defaultProperties.load(new FileReader(this.propertiesFile));
		}

		for (String property : new String[] {
				Bowtie2Binaries.BASE_DIRECTORY_PROP,
				SamtoolsBinaries.BASE_DIRECTORY_PROP,
				StringTieBinaries.BASE_DIRECTORY_PROP,
				HtseqBinaries.BASE_DIRECTORY_PROP,
				RBinaries.BASE_DIRECTORY_PROP,
				SystemBinaries.BASE_DIRECTORY_PROP,
				SystemBinaries.BASE_DIRECTORY_2_PROP,
				Hisat2Binaries.BASE_DIRECTORY_PROP,
				FastQcBinaries.BASE_DIRECTORY_PROP,
				TrimmomaticBinaries.BASE_DIRECTORY_PROP,
				PROP_DATABASES_DIR,
				PROP_NUM_THREADS
		}) {
			if (!this.hasProperty(property)) {
				throw new IllegalStateException(
					"Missing property in configuration file: " + property);
			}
		}

		this.cores = Integer.valueOf(_getProperty(PROP_NUM_THREADS));

		this.bowtie2Binaries = new DefaultBowtie2Binaries(
			this._getProperty(Bowtie2Binaries.BASE_DIRECTORY_PROP)
		);
		this.samtoolsBinaries = new DefaultSamtoolsBinaries(
			this._getProperty(SamtoolsBinaries.BASE_DIRECTORY_PROP)
		);
		this.stringTieBinaries = new DefaultStringTieBinaries(
			this._getProperty(StringTieBinaries.BASE_DIRECTORY_PROP)
		);
		this.htseqBinaries = new DefaultHtseqBinaries(
			this._getProperty(HtseqBinaries.BASE_DIRECTORY_PROP)
		);
		this.rBinaries = new DefaultRBinaries(
			this._getProperty(RBinaries.BASE_DIRECTORY_PROP)
		);
		this.systemBinaries = new DefaultSystemBinaries(
			this._getProperty(SystemBinaries.BASE_DIRECTORY_PROP),
			this._getProperty(SystemBinaries.BASE_DIRECTORY_2_PROP)
		);
		this.hisat2Binaries = new DefaultHisat2Binaries(
			this._getProperty(Hisat2Binaries.BASE_DIRECTORY_PROP)
		);
		this.fastQcBinaries = new DefaultFastQcBinaries(
			this._getProperty(FastQcBinaries.BASE_DIRECTORY_PROP)
		);
		this.trimmomaticBinaries = new DefaultTrimmomaticBinaries(
			this._getProperty(TrimmomaticBinaries.BASE_DIRECTORY_PROP)
		);

		try {
			this.initReferenceGenomeDatabaseManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initReferenceGenomeDatabaseManager() throws IOException, ClassNotFoundException {
		referenceGenomeDatabaseManager = DefaultReferenceGenomeIndexDatabaseManager.getInstance();

		referenceGenomeDatabaseManager.setPersistenceStorageFileProvider(this::getReferenceGenomeDatabaseFile);
		if(!this.getReferenceGenomeDatabaseFile().exists()) {
			referenceGenomeDatabaseManager.persistDatabase();
		}
		referenceGenomeDatabaseManager.loadDatabase();
	}

	private File getReferenceGenomeDatabaseFile() {
		File databasesDir = getDatabasesDirectory();
		return new File(databasesDir, PROP_REFERENCE_GENOME_FILE);
	}

	private File getDatabasesDirectory() {
		File databasesDirectory = new File(_getProperty(PROP_DATABASES_DIR));
		if (!databasesDirectory.exists()) {
			databasesDirectory.mkdirs();
		}
		return databasesDirectory;
	}

	private String _getProperty(String propertyName) {
		return getProperty(propertyName).get();
	}

	@Override
	public Optional<String> getProperty(String propertyName) {
		String propertyValue = System.getProperty(propertyName);

		if (propertyValue == null) {
			propertyValue = this.defaultProperties.getProperty(propertyName);
		}

		return Optional.ofNullable(propertyValue);
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
	public SamtoolsBinaries getSamtoolsBinaries() {
		return this.samtoolsBinaries;
	}

	@Override
	public StringTieBinaries getStringTieBinaries() {
		return this.stringTieBinaries;
	}

	@Override
	public HtseqBinaries getHtseqBinaries() {
		return this.htseqBinaries;
	}

	@Override
	public RBinaries getRBinaries() {
		return this.rBinaries;
	}

	@Override
	public SystemBinaries getSystemBinaries() {
		return this.systemBinaries;
	}

	@Override
	public Hisat2Binaries getHisat2Binaries() {
		return hisat2Binaries;
	}

	@Override
	public FastQcBinaries getFastQcBinaries() {
		return fastQcBinaries;
	}

	@Override
	public TrimmomaticBinaries getTrimmomaticBinaries() {
		return trimmomaticBinaries;
	}

	@Override
	public DefaultReferenceGenomeIndexDatabaseManager getReferenceGenomeDatabaseManager() {
		return referenceGenomeDatabaseManager;
	}

	@Override
	public int getThreads() {
		return this.cores;
	}
}
