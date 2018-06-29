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
package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2.Bowtie2ParametersChecker;
import org.sing_group.rnaseq.core.environment.execution.parameters.bowtie2.DefaultBowtie2EndToEndConfiguration;
import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieBallgownParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieLimitToTranscriptsParameter;
import org.sing_group.rnaseq.core.environment.execution.parameters.stringtie.StringTieParametersChecker;
import org.sing_group.rnaseq.gui.components.parameters.CommandLineParameter;
import org.sing_group.rnaseq.gui.components.parameters.CommandLineParametersPanel;

/**
 * The step for selecting command-line applications parameters.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep extends AbstractCommandLineParametersStep {
	private final CommandLineParameter bowtie2 = new CommandLineParameter(
		"Bowtie2:",
		"Bowtie2 command-line parameters.",
		DefaultBowtie2EndToEndConfiguration.DEFAULT_VALUE.getParameter(),
		s -> Bowtie2ParametersChecker.validateAlignReadsParameters(s)
	);
	private final CommandLineParameter stringTieObtainLabeled = new CommandLineParameter(
		"StringTie obtain labeled transcripts:",
		"StringTie obtain labeled transcripts parameters. " + StringTieParametersChecker.OBTAIN_LABELED_TRANSCRIPTS_PARAMS,
		"",
		StringTieParametersChecker::validateObtainLabeledTranscriptsParameters
	);
	private final CommandLineParameter stringTieMerge = new CommandLineParameter(
		"StringTie merge transcripts:",
		"StringTie merge transcripts parameters. " + StringTieParametersChecker.MERGE_TRANSCRIPTS_PARAMS,
		"",
		StringTieParametersChecker::validateMergeTranscriptsParameters
	);
	private final CommandLineParameter stringTieObtain = new CommandLineParameter(
		"StringTie obtain transcripts:",
		"StringTie obtain transcripts parameters. " + StringTieParametersChecker.OBTAIN_TRANSCRIPTS_PARAMS,
		StringTieBallgownParameter.DEFAULT_VALUE + " " + StringTieLimitToTranscriptsParameter.DEFAULT_VALUE,
		StringTieParametersChecker::validateObtainTranscriptsParameters
	);

	private CommandLineParametersPanel commandLineApplicationsParameters;
	private JPanel stepComponent;

	/**
	 * Creates a new {@code BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep}.
	 */
	public BowtieStringTieAndRDifferentialExpressionCommandLineParametersStep() {
		this.commandLineApplicationsParameters =
			new CommandLineParametersPanel(getParameters());
		this.commandLineApplicationsParameters
			.addCommandLineParametersPanelListener(p -> {
				notifyWizardStepStatus();
			});
	}

	private List<CommandLineParameter> getParameters() {
		return Arrays.asList(bowtie2, stringTieObtainLabeled, stringTieMerge,
			stringTieObtain);
	}

	@Override
	public JPanel getStepComponent() {
		if (this.stepComponent == null) {
			stepComponent = super.getStepComponent();
			stepComponent.setOpaque(false);
			stepComponent.add(this.commandLineApplicationsParameters,
				BorderLayout.CENTER);
		}
		return this.stepComponent;
	}

	@Override
	public boolean isStepCompleted() {
		return this.commandLineApplicationsParameters.areAllParametersValid();
	}

	public Map<WorkflowController.Parameters, String> getParametersMap() {
		Map<WorkflowController.Parameters, String> toret = new HashMap<>();

		toret.put(
			WorkflowController.Parameters.BOWTIE2,
			this.commandLineApplicationsParameters.getParameterValue(bowtie2).get()
		);
		toret.put(
			WorkflowController.Parameters.STRINGTIE_OBTAIN_LABELED,
			this.commandLineApplicationsParameters.getParameterValue(stringTieObtainLabeled).get()
		);
		toret.put(
			WorkflowController.Parameters.STRINGTIE_MERGE,
			this.commandLineApplicationsParameters.getParameterValue(stringTieMerge).get()
		);
		toret.put(
			WorkflowController.Parameters.STRINGTIE_OBTAIN,
			this.commandLineApplicationsParameters.getParameterValue(stringTieObtain).get()
		);

		return toret;
	}

	public void setParametersMap(Map<WorkflowController.Parameters, String> parameters) {
		if (parameters.containsKey(WorkflowController.Parameters.BOWTIE2)) {
			this.commandLineApplicationsParameters.setParameterValue(bowtie2,
				parameters.get(WorkflowController.Parameters.BOWTIE2));
		}
		if (parameters.containsKey(WorkflowController.Parameters.STRINGTIE_OBTAIN_LABELED)) {
			this.commandLineApplicationsParameters.setParameterValue(
				stringTieObtainLabeled,
				parameters.get(WorkflowController.Parameters.STRINGTIE_OBTAIN_LABELED)
			);
		}
		if (parameters.containsKey(WorkflowController.Parameters.STRINGTIE_MERGE)) {
			this.commandLineApplicationsParameters.setParameterValue(
				stringTieMerge,
				parameters.get(WorkflowController.Parameters.STRINGTIE_MERGE)
			);
		}
		if (parameters.containsKey(WorkflowController.Parameters.STRINGTIE_OBTAIN)) {
			this.commandLineApplicationsParameters.setParameterValue(
				stringTieObtain,
				parameters.get(WorkflowController.Parameters.STRINGTIE_OBTAIN)
			);
		}
	}
}
