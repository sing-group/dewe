package org.sing_group.rnaseq.api.environment;

/**
 * The interface that defines the StringTie environment.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface StringTieEnvironment {
	/**
	 * Returns the name of the default StringTie command.
	 * 
	 * @return the name of the default StringTie command
	 */
	public abstract String getDefaultStringTie();
}
