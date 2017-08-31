package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines samtools binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamtoolsBinaries extends Binaries {
	public final static String SAMTOOLS_PREFIX = "samtools.";
	public final static String BASE_DIRECTORY_PROP = SAMTOOLS_PREFIX + "binDir";

	/**
	 * Returns a string with the full path to the convert command.
	 *  
	 * @return a string with the full path to the convert command
	 */
	public abstract String getSamToBam();
}
