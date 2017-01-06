package org.sing_group.rnaseq.api.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;
import org.sing_group.rnaseq.api.environment.binaries.DefaultBowtie2Binaries;

public class DefaultAppEnvironment implements AppEnvironment {

	private File propertiesFile;
	private Properties defaultProperties;
	private DefaultBowtie2Binaries bowtie2Binaries;

	public DefaultAppEnvironment(File propertiesFile) 
		throws FileNotFoundException, IOException {
		this.propertiesFile = propertiesFile;
		this.defaultProperties = new Properties();
		
		if (this.propertiesFile != null) {
			this.defaultProperties.load(new FileReader(this.propertiesFile));
		}

		for (String property : new String[] {
				Bowtie2Binaries.BASE_DIRECTORY_PROP }) {
			if (!this.hasProperty(property)) {
				throw new IllegalStateException(
					"Missing property in configuration file: " + property);
			}
		}

		this.bowtie2Binaries = new DefaultBowtie2Binaries(
			this.getProperty(Bowtie2Binaries.BASE_DIRECTORY_PROP)
		);
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
}
