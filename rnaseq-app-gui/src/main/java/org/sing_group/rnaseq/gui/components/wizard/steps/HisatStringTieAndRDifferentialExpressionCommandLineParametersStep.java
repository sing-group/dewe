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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.rnaseq.api.controller.WorkflowController;
import org.sing_group.rnaseq.core.environment.execution.parameters.hisat2.Hisat2ParametersChecker;
import org.sing_group.rnaseq.gui.components.parameters.CommandLineParameter;
import org.sing_group.rnaseq.gui.components.parameters.CommandLineParametersPanel;

/**
 * The step for selecting command-line applications parameters.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class HisatStringTieAndRDifferentialExpressionCommandLineParametersStep extends WizardStep {

	private final CommandLineParameter hisat2 = new CommandLineParameter(
		"HISAT2 parameters:",
		"HISAT22 command-line parameters.",
		"",
		s -> Hisat2ParametersChecker.validateAlignReadsParameters(s)
	);

	private CommandLineParametersPanel commandLineApplicationsParameters;

	/**
	 * Creates a new {@code HisatStringTieAndRDifferentialExpressionCommandLineParametersStep}.
	 */
	public HisatStringTieAndRDifferentialExpressionCommandLineParametersStep() {
		this.commandLineApplicationsParameters =
			new CommandLineParametersPanel(getParameters());
		this.commandLineApplicationsParameters
			.addCommandLineParametersPanelListener(p -> {
				notifyWizardStepStatus();
			});
	}

	private List<CommandLineParameter> getParameters() {
		return Arrays.asList(hisat2);
	}

	@Override
	public JComponent getStepComponent() {
		return this.commandLineApplicationsParameters;
	}
	@Override
	public String getStepTitle() {
		return "Parameter configuration";
	}

	@Override
	public boolean isStepCompleted() {
		return this.commandLineApplicationsParameters.areAllParametersValid();
	}

	@Override
	public void stepEntered() {
	}

	public Map<WorkflowController.Parameters, String> getParametersMap() {
		Map<WorkflowController.Parameters, String> toret = new HashMap<>();

		toret.put(
			WorkflowController.Parameters.HISAT2,
			this.commandLineApplicationsParameters.getParameterValue(hisat2).get()
		);

		return toret;
	}

	public void setParametersMap(Map<WorkflowController.Parameters, String> parameters) {
		if (parameters.containsKey(WorkflowController.Parameters.HISAT2)) {
			this.commandLineApplicationsParameters.setParameterValue(hisat2,
				parameters.get(WorkflowController.Parameters.HISAT2));
		}
	}
}
