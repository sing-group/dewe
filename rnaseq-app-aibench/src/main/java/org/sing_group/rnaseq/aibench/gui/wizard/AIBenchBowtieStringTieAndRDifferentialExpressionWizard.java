package org.sing_group.rnaseq.aibench.gui.wizard;

import java.awt.Dimension;
import java.awt.Window;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sing_group.rnaseq.gui.components.wizard.BowtieStringTieAndRDifferentialExpressionWizard;

import es.uvigo.ei.aibench.workbench.Workbench;
import es.uvigo.ei.sing.hlfernandez.utilities.ExtendedAbstractAction;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class AIBenchBowtieStringTieAndRDifferentialExpressionWizard extends BowtieStringTieAndRDifferentialExpressionWizard {
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
	
	private static void showWizard() {
		AIBenchBowtieStringTieAndRDifferentialExpressionWizard
			.getWizard(Workbench.getInstance().getMainFrame())
			.setVisible(true);
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
	public void setVisible(boolean b) {
		this.setResizable(true);
		int height 	= (int) (this.getSize().getHeight() + 1);
		int width 	= (int) (this.getSize().getWidth() 	+ 1);
		this.setSize(new Dimension(width, height));
		this.setResizable(false);
		super.setVisible(b);
	}
}
