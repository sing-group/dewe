package org.sing_group.rnaseq.api.environment;

public class DefaultBowtie2Environment implements Bowtie2Environment {

	private static DefaultBowtie2Environment INSTANCE;

	public static DefaultBowtie2Environment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultBowtie2Environment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultBuildIndex() {
		return "bowtie2-build";
	}

	@Override
	public String getDefaultAlign() {
		return "bowtie2";
	}
}
