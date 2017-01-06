package org.sing_group.rnaseq.api.environment;

public interface Bowtie2Environment {
	public abstract String getDefaultBuildIndex();

	public abstract String getDefaultAlign();
}
