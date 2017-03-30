package org.sing_group.rnaseq.aibench.gui.wizard;

import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.fixDialogSize;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.askUserImportOrBuild;

import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sing_group.rnaseq.api.persistence.entities.Hisat2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.HisatStringTieAndBallgownDifferentialExpressionWizard;

import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard
	extends HisatStringTieAndBallgownDifferentialExpressionWizard {
	private static final long serialVersionUID = 1L;

	protected AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
		Window parent, String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	public static ExtendedAbstractAction SHOW_WIZARD = 
		new ExtendedAbstractAction("Differential expression wizard", 
			AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard::showWizard);
	
	public static AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new AIBenchHisatStringTieAndBallgownDifferentialExpressionWizard(
			parent, TITLE, getWizardSteps());
	}

	public static void showWizard() {
		while (shouldCreateHisat2Index()) {
			if (!askUserImportOrBuild("hisat2", "operations.hisat2importindex",
				"operations.hisat2buildindex")) {
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
				"operations.hisatstringtiedifferentialexpression", null,
				Arrays.asList(getReferenceGenome(), getSamples(),
						getReferenceAnnotationFile(), getWorkingDirectory()));
	}

	@Override
	public void setVisible(boolean visible) {
		fixDialogSize(visible, this);
		super.setVisible(visible);
	}
}
