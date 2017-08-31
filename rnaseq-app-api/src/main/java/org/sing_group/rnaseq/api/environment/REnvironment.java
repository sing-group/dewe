package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the R environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface REnvironment {
	/**
	 * Returns the name of the default Rscript command.
	 * 
	 * @return the name of the default Rscript command
	 */
	public abstract String getDefaultRscript();
}
