/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2019 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.gui.components.parameters;

import java.util.function.Function;

/**
 * A POJO to store the information about a command-line parameter along with a 
 * function to validate it.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class CommandLineParameter {

	private String name;
	private String value;
	private String description;
	private Function<String, Boolean> validateMethod;

	/**
	 * Creates a new {@code CommandLineParameter} with the specified values.
	 * 
	 * @param name the parameter name
	 * @param description the parameter description
	 * @param value the parameter value
	 * @param validateMethod the method to validate the parameter
	 */
	public CommandLineParameter(String name,
		String description,
		String value,
		Function<String, Boolean> validateMethod
	) {
		this.name = name;
		this.value = value;
		this.description = description;
		this.validateMethod = validateMethod;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public String getDescripion() {
		return description;
	}

	public boolean validate(String params) {
		return validateMethod.apply(params);
	}
}
