package org.sing_group.rnaseq.api.environment;

import org.sing_group.rnaseq.api.environment.binaries.Bowtie2Binaries;

public interface AppEnvironment {
	public abstract Bowtie2Binaries getBowtie2Binaries();

	public abstract String getProperty(String propertyName);

	public abstract boolean hasProperty(String propertyName);
}
