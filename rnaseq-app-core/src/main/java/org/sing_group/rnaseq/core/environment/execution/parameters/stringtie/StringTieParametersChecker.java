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
package org.sing_group.rnaseq.core.environment.execution.parameters.stringtie;

/**
 * A class that validates StringTie command-line parameters.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class StringTieParametersChecker {

	public static final String OBTAIN_LABELED_TRANSCRIPTS_PARAMS = "Please, note "
		+ "that the usage of -p, -l, -o and -G is not allowed.";
	public static final String OBTAIN_LABELED_TRANSCRIPTS_ERROR = "StringTie command "
		+ "parameters not valid. " + OBTAIN_LABELED_TRANSCRIPTS_PARAMS;

	public static final String OBTAIN_TRANSCRIPTS_PARAMS = "Please, note "
		+ "that the usage of -p, -o and -G is not allowed.";
	public static final String OBTAIN_TRANSCRIPTS_ERROR = "StringTie command "
		+ "parameters not valid. " + OBTAIN_TRANSCRIPTS_PARAMS;

	public static final String MERGE_TRANSCRIPTS_PARAMS = "Please, note "
		+ "that the usage of --merge, -p, -o and -G is not allowed.";
	public static final String MERGE_TRANSCRIPTS_ERROR = "StringTie command "
		+ "parameters not valid. " + MERGE_TRANSCRIPTS_PARAMS;

	/**
	 * Validates the list of command-line parameters for the obtain labeled
	 * transcripts operation.
	 *
	 * @param params the list of command-line parameters
	 * @return {@code true} if the list of parameters can be used to run
	 * 		   StringTie and {@code false} otherwise
	 */
	public static boolean validateObtainLabeledTranscriptsParameters(
		String params
	) {
		return validateGeneralParameters(params) && !params.contains("-l");
	}

	/**
	 * Validates the list of command-line parameters for the obtain transcripts
	 * operation.
	 *
	 * @param params the list of command-line parameters
	 * @return {@code true} if the list of parameters can be used to run
	 * 		   StringTie and {@code false} otherwise
	 */
	public static boolean validateObtainTranscriptsParameters(String params) {
		return validateGeneralParameters(params);
	}

	/**
	 * Validates the list of command-line parameters for the merge transcripts
	 * operation.
	 *
	 * @param params the list of command-line parameters
	 * @return {@code true} if the list of parameters can be used to run
	 * 		   StringTie and {@code false} otherwise
	 */
	public static boolean validateMergeTranscriptsParameters(String params) {
		return validateGeneralParameters(params) && !params.contains("--merge");
	}

	private static boolean validateGeneralParameters(String params) {
		return !params.contains("-p ") && !params.contains("-G ")
			&& !params.contains("-o ");
	}
}
