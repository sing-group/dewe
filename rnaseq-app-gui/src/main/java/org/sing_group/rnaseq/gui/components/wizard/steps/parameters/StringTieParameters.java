/*
 * #%L
 * DEWE GUI
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
package org.sing_group.rnaseq.gui.components.wizard.steps.parameters;

import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieBallgownParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieLimitToTranscriptsParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieParametersChecker;
import org.sing_group.rnaseq.gui.components.parameters.CommandLineParameter;

public enum StringTieParameters {
	OBTAIN_LABELED_TRANSCRIPTS(new CommandLineParameter(
		"StringTie obtain labeled transcripts:",
		"StringTie obtain labeled transcripts parameters. " + StringTieParametersChecker.OBTAIN_LABELED_TRANSCRIPTS_PARAMS,
		"",
		StringTieParametersChecker::validateObtainLabeledTranscriptsParameters
	)), MERGE_TRANSCRIPTS(new CommandLineParameter(
		"StringTie merge transcripts:",
		"StringTie merge transcripts parameters. " + StringTieParametersChecker.MERGE_TRANSCRIPTS_PARAMS,
		"",
		StringTieParametersChecker::validateMergeTranscriptsParameters
	)), OBTAIN_TRANSCRIPTS(new CommandLineParameter(
		"StringTie obtain transcripts:",
		"StringTie obtain transcripts parameters. " + StringTieParametersChecker.OBTAIN_TRANSCRIPTS_PARAMS,
		StringTieBallgownParameter.DEFAULT_VALUE + " " + StringTieLimitToTranscriptsParameter.DEFAULT_VALUE,
		StringTieParametersChecker::validateObtainTranscriptsParameters
	));

	private CommandLineParameter parameter;

	StringTieParameters(CommandLineParameter parameter) {
		this.parameter = parameter;
	}

	public CommandLineParameter getParameter() {
		return parameter;
	}
}
