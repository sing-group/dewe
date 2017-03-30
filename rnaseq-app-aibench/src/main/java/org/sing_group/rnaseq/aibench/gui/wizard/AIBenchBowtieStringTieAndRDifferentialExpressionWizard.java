package org.sing_group.rnaseq.aibench.gui.wizard;

import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.askUserImportOrBuild;
import static org.sing_group.rnaseq.aibench.gui.wizard.AIBenchWizardUtil.fixDialogSize;

import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sing_group.rnaseq.api.persistence.entities.Bowtie2ReferenceGenome;
import org.sing_group.rnaseq.core.persistence.DefaultReferenceGenomeDatabaseManager;
import org.sing_group.rnaseq.gui.components.wizard.BowtieStringTieAndRDifferentialExpressionWizard;

import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class AIBenchBowtieStringTieAndRDifferentialExpressionWizard
	extends BowtieStringTieAndRDifferentialExpressionWizard {
	private static final long serialVersionUID = 1L;

	protected AIBenchBowtieStringTieAndRDifferentialExpressionWizard(Window parent,
		String wizardTitle, List<WizardStep> steps
	) {
		super(parent, wizardTitle, steps);
	}

	public static ExtendedAbstractAction SHOW_WIZARD = 
		new ExtendedAbstractAction("Differential expression wizard", 
			AIBenchBowtieStringTieAndRDifferentialExpressionWizard::showWizard);
	
	public static AIBenchBowtieStringTieAndRDifferentialExpressionWizard getWizard(
		Window parent
	) {
		return new AIBenchBowtieStringTieAndRDifferentialExpressionWizard(
			parent, TITLE, getWizardSteps());
	}
	
	public static void showWizard() {
		while (shouldCreateBowtie2Index()) {
			if (!askUserImportOrBuild("bowtie2",
				"operations.bowtie2importindex",
				"operations.bowtie2buildindex")) {
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
				"operations.bowtiestringtiedifferentialexpression", null,
				Arrays.asList(getReferenceGenome(), getSamples(),
						getReferenceAnnotationFile(), getWorkingDirectory()));
	}

	@Override
	public void setVisible(boolean visible) {
		fixDialogSize(visible, this);
		super.setVisible(visible);
	}
}
