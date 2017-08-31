package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.SamtoolsEnvironment;

/**
 * The default {@code SamtoolsEnvironment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSamtoolsEnvironment implements SamtoolsEnvironment {

	private static DefaultSamtoolsEnvironment INSTANCE;

	/**
	 * Returns the {@code DefaultSamtoolsEnvironment} singleton instance.
	 * 
	 * @return the {@code DefaultSamtoolsEnvironment} singleton instance
	 */
	public static DefaultSamtoolsEnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultSamtoolsEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultSamToBam() {
		return "samtools";
	}
}
