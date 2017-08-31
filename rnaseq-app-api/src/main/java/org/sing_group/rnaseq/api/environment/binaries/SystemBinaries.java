package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines system binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemBinaries extends Binaries {
	public final static String SYSTEM_PREFIX = "system.";
	public final static String BASE_DIRECTORY_PROP = SYSTEM_PREFIX + "binDir";
	public final static String BASE_DIRECTORY_2_PROP = SYSTEM_PREFIX + "binDir2";

	/**
	 * Returns a string with the full path to the join command.
	 *  
	 * @return a string with the full path to the join command
	 */
	public abstract String getJoin();

	/**
	 * Returns a string with the full path to the sed command.
	 *  
	 * @return a string with the full path to the sed command
	 */
	public abstract String getSed();

	/**
	 * Returns a string with the full path to the awk command.
	 *  
	 * @return a string with the full path to the awk command
	 */
	public abstract String getAwk();
}
