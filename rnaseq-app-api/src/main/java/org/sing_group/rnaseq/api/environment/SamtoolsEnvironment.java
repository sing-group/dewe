package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the samtools environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamtoolsEnvironment {
	/**
	 * Returns the default name of the convert command.
	 * 
	 * @return the default name of the convert command
	 */
	public abstract String getDefaultSamToBam();
}
