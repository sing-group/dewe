package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.REnvironment;

/**
 * The default {@code REnvironment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultREnvironment implements REnvironment {

	private static DefaultREnvironment INSTANCE;

	/**
	 * Returns the {@code DefaultREnvironment} singleton instance.
	 * 
	 * @return the {@code DefaultREnvironment} singleton instance
	 */
	public static DefaultREnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultREnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultRscript() {
		return "Rscript";
	}
}
