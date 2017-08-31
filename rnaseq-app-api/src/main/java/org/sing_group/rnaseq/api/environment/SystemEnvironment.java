package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the system environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SystemEnvironment {
	/**
	 * Returns the name of the default join command.
	 * 
	 * @return the name of the default join command
	 */
	public abstract String getDefaultJoin();

	/**
	 * Returns the name of the default sed command.
	 * 
	 * @return the name of the default sed command
	 */
	public abstract String getDefaultSed();

	/**
	 * Returns the name of the default awk command.
	 * 
	 * @return the name of the default awk command
	 */
	public abstract String getDefaultAwk();
}
