/*
 * #%L
 * DEWE API
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.sing_group.rnaseq.api.environment.execution.parameters;

import java.io.Serializable;

/**
 * The interface that defines a parameter.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface Parameter extends Serializable {

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
