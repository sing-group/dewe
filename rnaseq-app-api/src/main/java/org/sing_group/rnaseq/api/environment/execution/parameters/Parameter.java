package org.sing_group.rnaseq.api.environment.execution.parameters;

/**
 * The interface that defines a parameter.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Parameter {

	/**
	 * Returns the parameter name.
	 * 
	 * @return the parameter name
	 */
	abstract String getParameter();

	/**
	 * Returns the parameter value.
	 * 
	 * @return the parameter value
	 */
	abstract String getValue();

	/**
	 * Returns the parameter description.
	 * 
	 * @return the parameter description
	 */
	abstract String getDescription();

	/**
	 * Returns {@code true} if the parameter value is valid and {@code false}
	 * otherwise.
	 * 
	 * @return {@code true} if the parameter value is valid and {@code false}
	 *         otherwise
	 */
	abstract boolean isValidValue();
}
