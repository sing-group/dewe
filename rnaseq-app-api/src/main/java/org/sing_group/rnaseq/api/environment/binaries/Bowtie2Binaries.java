package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines Bowtie2 binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Bowtie2Binaries extends Binaries {
	public final static String BOWTIE2_PREFIX = "bowtie2.";
	public final static String BASE_DIRECTORY_PROP = BOWTIE2_PREFIX + "binDir";

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
