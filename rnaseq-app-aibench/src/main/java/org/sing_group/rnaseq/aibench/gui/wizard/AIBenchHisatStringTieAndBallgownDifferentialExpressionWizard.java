/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.wizard;

import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.DEWE_EXTENSION;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.askUserImportOrBuild;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.fixDialogSize;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.getFile;

import java.awt.Window;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.sing_group.gc4s.dialog.wizard.WizardStep;
import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.rnaseq.aibench.gui.wizard.steps.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.api.persistence.entities.DifferentialExpressionWorkflowConfiguration;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
import org.sing_group.rnaseq.core.persistence.entities.DefaultDifferentialExpressionWorkflowConfiguration;
import org.sing_group.rnaseq.gui.components.wizard.HisatStringTieAndBallgownDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.components.wizard.steps.HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider;

import es.uvigo.ei.aibench.workbench.Workbench;

/**
 * An AIBench extension of the {@code HisatStringTieAndBallgownDifferentialExpressionWizard}.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard
	extends HisatStringTieAndBallgownDifferentialExpressionWizard {
	private static final long serialVersionUID = 1L;

	public static final String IMPORT_INDEX = "operations.genome.hisat2importindex";
	public static final String BUILD_INDEX = "operations.genome.hisat2buildindex";
	public static final String DIFFERENTIAL_EXPRESSION = "operations.hisatstringtiedifferentialexpression";

	public static final ExtendedAbstractAction SHOW_WIZARD = new ExtendedAbstractAction(
		"Differential expression wizard",
		() -> AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard.showWizard(false));

	protected AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
		Window parent, String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	protected AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
		Window parent, String wizardTitle, List<WizardStep> steps,
		DifferentialExpressionWorkflowConfiguration configuration
	) {
		super(parent, wizardTitle, steps, configuration);
	}

	/**
	 * Creates a new
	 * {@code AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard}
	 * using the specified workflow configuration.
	 *
	 * @param parent the parent component of the wizard dialog
	 * @param configuration the {@code DifferentialExpressionWorkflowConfiguration}
	 *
	 * @return a new wizard dialog
	 */
	public static AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard getWizard(
		Window parent, DifferentialExpressionWorkflowConfiguration configuration
	) {
		return new AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
			parent, TITLE, getWizardSteps(getStepProvider()), configuration);
	}

	/**
	 * Creates a new
	 * {@code AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard}.
	 *
	 * @param parent the parent component of the wizard dialog
	 *
	 * @return a new wizard dialog
	 */
	public static AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
			parent, TITLE, getWizardSteps(getStepProvider()));
	}

	private static HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider getStepProvider() {
		return new AIBenchHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider();
	}

	/**
	 * Shows the wizard. Note that the wizard requires at least one Bowtie2
	 * index. This method checks this prerequisite and asks user to import or
	 * build an index if no one is available.
	 *
	 * @param importWorkflow whether a workflow file must be required to user
	 * 		  before showing the wizard or not
	 */
	public static void showWizard(boolean importWorkflow) {
		if(importWorkflow) {
			importWorkflowAndShowWizard();
		} else {
			while (shouldCreateHisat2Index()) {
				if (!askUserImportOrBuild("HISAT2", IMPORT_INDEX, BUILD_INDEX)) {
					return;
				}
			}
			AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard
				.getWizard(getParentForDialog()).setVisible(true);
		}
	}

	private static Window getParentForDialog() {
		return Workbench.getInstance().getMainFrame();
	}

	private static void importWorkflowAndShowWizard() {
		Window parent = getParentForDialog();

		Optional<File> configurationFile = getFile(parent, DEWE_EXTENSION);
		if(configurationFile.isPresent()) {
			try {
			DifferentialExpressionWorkflowConfiguration config =
				DefaultDifferentialExpressionWorkflowConfiguration
					.loadWorkflowConfiguration(configurationFile.get());
				if (config.getReferenceGenome().getType()
					.equals(Hisat2ReferenceGenomeIndex.TYPE)
				) {
					AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard
						.getWizard(parent, config).setVisible(true);
					return;
				}
			} catch(RuntimeException ex) { }

			JOptionPane.showMessageDialog(parent,
				"The selected file does not contain the required information for this workflow.",
				"Invalid workflow file", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static boolean shouldCreateHisat2Index() {
		return 	DefaultReferenceGenomeIndexDatabaseManager.getInstance()
					.listValidIndexes(Hisat2ReferenceGenomeIndex.class)
					.isEmpty();
	}

	@Override
	protected void wizardFinished() {
		super.wizardFinished();
		SwingUtilities.invokeLater(this::launchWorkflow);
	}

	private void launchWorkflow() {
		Workbench.getInstance().executeOperation(
				DIFFERENTIAL_EXPRESSION, null,
				Arrays.asList(getReferenceGenome(), getSamples(),
						getReferenceAnnotationFile(), getWorkingDirectory()));
	}

	@Override
	public void setVisible(boolean visible) {
		fixDialogSize(visible, this);
		super.setVisible(visible);
	}
}
