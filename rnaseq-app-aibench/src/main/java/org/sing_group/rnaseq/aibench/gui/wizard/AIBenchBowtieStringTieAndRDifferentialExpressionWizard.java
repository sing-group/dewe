package org.sing_group.rnaseq.aibench.gui.wizard;

import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.askUserImportOrBuild;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.fixDialogSize;

import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sing_group.gc4s.utilities.ExtendedAbstractAction;
import org.sing_group.gc4s.wizard.WizardStep;
import org.sing_group.rnaseq.aibench.gui.wizard.steps.AIBenchBowtieStringTieAndRDifferentialExpressionStepProvider;
import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.BowtieStringTieAndRDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.components.wizard.steps.BowtieStringTieAndRDifferentialExpressionWizardStepProvider;

import es.uvigo.ei.aibench.workbench.Workbench;

public class AIBenchBowtieStringTieAndRDifferentialExpressionWizard
	extends BowtieStringTieAndRDifferentialExpressionWizard {
	private static final long serialVersionUID = 1L;

	public static final String IMPORT_INDEX = "operations.genome.bowtie2importindex";
	public static final String BUILD_INDEX = "operations.genome.bowtie2buildindex";
	public static final String DIFFERENTIAL_EXPRESSION = "operations.bowtiestringtiedifferentialexpression";

	public static final ExtendedAbstractAction SHOW_WIZARD = new ExtendedAbstractAction(
		"Differential expression wizard",
		AIBenchBowtieStringTieAndRDifferentialExpressionWizard::showWizard);

	protected AIBenchBowtieStringTieAndRDifferentialExpressionWizard(
		Window parent, String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	public static AIBenchBowtieStringTieAndRDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new AIBenchBowtieStringTieAndRDifferentialExpressionWizard(
			parent, TITLE, getWizardSteps(getStepProvider()));
	}

	private static BowtieStringTieAndRDifferentialExpressionWizardStepProvider getStepProvider() {
		return new AIBenchBowtieStringTieAndRDifferentialExpressionStepProvider();
	}

	public static void showWizard() {
		while (shouldCreateBowtie2Index()) {
			if (!askUserImportOrBuild("bowtie2", IMPORT_INDEX, BUILD_INDEX)) {
				return;
			}
		}

		AIBenchBowtieStringTieAndRDifferentialExpressionWizard
			.getWizard(Workbench.getInstance().getMainFrame()).setVisible(true);
	}

	private static boolean shouldCreateBowtie2Index() {
		return 	DefaultReferenceGenomeDatabaseManager.getInstance()
					.listValidReferenceGenomes(Bowtie2ReferenceGenome.class)
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
