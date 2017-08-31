package org.sing_group.rnaseq.core.environment;

import org.sing_group.rnaseq.api.environment.SystemEnvironment;

/**
 * The default {@code SystemEnvironment} implementation.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class DefaultSystemEnvironment implements SystemEnvironment {

	private static DefaultSystemEnvironment INSTANCE;

	/**
	 * Returns the {@code DefaultSystemEnvironment} singleton instance.
	 * 
	 * @return the {@code DefaultSystemEnvironment} singleton instance
	 */
	public static DefaultSystemEnvironment getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DefaultSystemEnvironment();
		}
		return INSTANCE;
	}

	@Override
	public String getDefaultJoin() {
		return "join";
	}

	@Override
	public String getDefaultSed() {
		return "sed";
	}

	@Override
	public String getDefaultAwk(){
		return "awk";
	}
}
