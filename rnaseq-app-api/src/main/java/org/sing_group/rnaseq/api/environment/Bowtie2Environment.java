package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the Bowtie2 environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Bowtie2Environment {
	/**
	 * Returns the default name of the the build index command.
	 * 
	 * @return the default name of the the build index command
	 */
	public abstract String getDefaultBuildIndex();

	/**
	 * Returns the default name of the align command.
	 * 
	 * @return the default name of the align command
	 */
	public abstract String getDefaultAlign();
}
