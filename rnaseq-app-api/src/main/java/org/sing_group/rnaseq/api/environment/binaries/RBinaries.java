package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines R binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface RBinaries extends Binaries {
	public final static String R_PREFIX = "r.";
	public final static String BASE_DIRECTORY_PROP = R_PREFIX + "binDir";

	/**
	 * Returns a string with the full path to the Rscript command.
	 *  
	 * @return a string with the full path to the Rscript command
	 */
	public abstract String getRscript();
}
