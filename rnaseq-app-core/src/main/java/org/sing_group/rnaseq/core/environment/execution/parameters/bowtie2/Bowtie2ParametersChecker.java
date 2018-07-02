/*
 * #%L
 * DEWE Core
 * %%
 * Copyright (C) 2016 - 2018 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola,
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
package org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2;

/**
 * A class that validates Bowtie2 command-line parameters.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class Bowtie2ParametersChecker {
	public static final String ALIGN_PARAMS = "Please, note "
		+ "that the usage of -x, -S, -1, -2 and -U is not allowed.";
	public static final String ALIGN_ERROR = "Bowtie2 command "
		+ "parameters not valid. " + ALIGN_PARAMS;

	/**
	 * Validates the list of command-line parameters.
	 *
	 * @param params the list of command-line parameters
	 * @return {@code true} if the list of parameters can be used to run Bowtie2
	 * 		   and {@code false} otherwise
	 */
	public static boolean validateAlignReadsParameters(String params) {
		return !params.contains("-x ") && !params.contains("-S ")
			&& !params.contains("-1 ") && !params.contains("-2 ")
			&& !params.contains("-U ");
	}
}
