/*
 * #%L
 * DEWE
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
package org.sing_group.rnaseq.aibench.gui.wizard;

import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.askUserImportOrBuild;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.fixDialogSize;

import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.gc4s.wizard.WizardStep;
import org.sing_group.rnaseq.aibench.gui.wizard.steps.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenomeIndex;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeIndexDatabaseManager;
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
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard::showWizard);

	protected AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
		Window parent, String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	/**
	 * Creates a new
	 * {@code AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard}.
	 * 
	 * @param parent the parent component of the wizard dialog.
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
	 * Shows the wizard. Note that the wizard requires at least one HISAT2
	 * index. This method checks this prerequisite and asks user to import or
	 * build an index if no one is available.
	 */
	public static void showWizard() {
		while (shouldCreateHisat2Index()) {
			if (!askUserImportOrBuild("HISAT2", IMPORT_INDEX, BUILD_INDEX)) {
				return;
			}
		}
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard
			.getWizard(Workbench.getInstance().getMainFrame()).setVisible(true);
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
