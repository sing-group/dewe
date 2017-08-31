package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines HISAT2 binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Hisat2Binaries extends Binaries {
	public final static String HISAT2_PREFIX = "hisat2.";
	public final static String BASE_DIRECTORY_PROP = HISAT2_PREFIX + "binDir";

	/**
	 * Returns a string with the full path to the build index command.
	 *  
	 * @return a string with the full path to the build index command
	 */
	public abstract String getBuildIndex();

	/**
	 * Returns a string with the full path to the align reads command.
	 *  
	 * @return a string with the full path to the align reads command
	 */
	public abstract String getAlignReads();
}
