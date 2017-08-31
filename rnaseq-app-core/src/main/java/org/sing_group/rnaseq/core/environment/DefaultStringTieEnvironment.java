package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.StringTieEnvironment;

/**
 * The default {@code StringTieEnvironment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultStringTieEnvironment implements StringTieEnvironment {

	private static DefaultStringTieEnvironment INSTANCE;

	/**
	 * Returns the {@code DefaultStringTieEnvironment} singleton instance.
	 * 
	 * @return the {@code DefaultStringTieEnvironment} singleton instance
	 */
	public static DefaultStringTieEnvironment getInstance() {
		if
		(INSTANCE == null) {
			INSTANCE = new DefaultStringTieEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultStringTie() {
		return "stringtie";
	}
}
