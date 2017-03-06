package org.sing_group.rnaseq.gui.components.wizard.steps;

import javax.swing.JComponent;
import javax.swing.JLabel;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class BowtieStringTieAndRDifferentialExpressionWizardPresentationStep 
	extends WizardStep {

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
		return new CenteredJPanel(new JLabel("Welcome to the wizard!"));
	}

	@Override
	public void stepEntered() {
	}
}
