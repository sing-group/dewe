package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.Hisat2Environment;

/**
 * The default {@code Hisat2Environment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultHisat2Environment implements Hisat2Environment {

	private static DefaultHisat2Environment INSTANCE;

	/**
	 * Returns the {@code DefaultHisat2Environment} singleton instance.
	 * 
	 * @return the {@code DefaultHisat2Environment} singleton instance
	 */
	public static DefaultHisat2Environment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultHisat2Environment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultBuildIndex() {
		return "hisat2-build";
	}

	@Override
	public String getDefaultAlign() {
		return "hisat2";
	}
}
