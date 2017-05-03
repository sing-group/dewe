package org.sing_group.rnaseq.aibench.gui.wizard;

import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.fixDialogSize;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.askUserImportOrBuild;

import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sing_group.rnaseq.aibench.gui.wizard.steps.AIBenchHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider;
import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.HisatStringTieAndBallgownDifferentialExpressionWizard;
import org.sing_group.rnaseq.gui.components.wizard.steps.HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider;

import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

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

	public static AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
			parent, TITLE, getWizardSteps(getStepProvider()));
	}

	private static HisatStringTieAndBallgownDifferentialExpressionWizardStepProvider getStepProvider() {
		return new AIBenchHisatStringTieAndBallgownDifferentialExpressionWizardStepProvider();
	}

	public static void showWizard() {
		while (shouldCreateHisat2Index()) {
			if (!askUserImportOrBuild("hisat2", IMPORT_INDEX, BUILD_INDEX)) {
				return;
			}
		}
		AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard
			.getWizard(Workbench.getInstance().getMainFrame()).setVisible(true);
	}

	private static boolean shouldCreateHisat2Index() {
		return 	DefaultReferenceGenomeDatabaseManager.getInstance()
					.listValidReferenceGenomes(Hisat2ReferenceGenome.class)
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
