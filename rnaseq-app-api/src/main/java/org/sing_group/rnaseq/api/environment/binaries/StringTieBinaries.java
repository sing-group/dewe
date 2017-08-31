package org.sing_group.rnaseq.api.environment.binaries;

/**
 * The interface that defines StringTie binaries.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface StringTieBinaries extends Binaries {
	public final static String STRINGTIE_PREFIX = "stringtie.";
	public final static String BASE_DIRECTORY_PROP = STRINGTIE_PREFIX + "binDir";
	
	/**
	 * Returns a string with the full path to the StringTie command.
	 *  
	 * @return a string with the full path to the StringTie command
	 */
	public abstract String getStringTie();
}
