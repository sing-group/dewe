package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class BowtieStringTieAndRDifferentialExpressionWizardPresentationStep 
	extends WizardStep {

	private JPanel stepComponent;

	@Override
	public boolean isStepCompleted() {
		return true;
	}

	@Override
	public String getStepTitle() {
		return "Differential expression analysis pipeline";
	}

	@Override
	public JComponent getStepComponent() {
		this.stepComponent = new CenteredJPanel(new JLabel("Welcome to the wizard!"));
		configureStepComponent(stepComponent);
		return this.stepComponent;
	}

	@Override
	public void stepEntered() {
	}
}
