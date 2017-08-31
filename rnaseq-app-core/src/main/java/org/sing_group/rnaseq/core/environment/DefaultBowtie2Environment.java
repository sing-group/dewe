package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.Bowtie2Environment;

/**
 * The default {@code Bowtie2Environment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultBowtie2Environment implements Bowtie2Environment {

	private static DefaultBowtie2Environment INSTANCE;

	/**
	 * Returns the {@code DefaultBowtie2Environment} singleton instance.
	 * 
	 * @return the {@code DefaultBowtie2Environment} singleton instance
	 */
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
