/*
 * #%L
 * DEWE Core
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
package org.sing_group.rnaseq.core.environment.execution.parameters.stringtie;

import org.sing_group.rnaseq.api.environment.execution.parameters.Parameter;

public class StringTieBallgownParameter implements Parameter {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_VALUE = "-B";

	private static StringTieBallgownParameter instance;

	public static StringTieBallgownParameter getInstance() {
		if (instance == null) {
			instance = new StringTieBallgownParameter();
		}
		return instance;
	}

	@Override
	public String getParameter() {
		return DEFAULT_VALUE;
	}

	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String getDescription() {
		return "Enables the output of Ballgown input table files (*.ctab) containing coverage data for the reference transcripts given with the -G option";
	}

	@Override
	public boolean isValidValue() {
		return true;
	}
}
