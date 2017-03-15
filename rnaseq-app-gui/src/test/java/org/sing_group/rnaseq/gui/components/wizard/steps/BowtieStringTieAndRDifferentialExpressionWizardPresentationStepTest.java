package org.sing_group.rnaseq.gui.components.wizard.steps;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class BowtieStringTieAndRDifferentialExpressionWizardPresentationStepTest {

	public static void main(String[] args) {
		BowtieStringTieAndRDifferentialExpressionWizardPresentationStep step = 
			new BowtieStringTieAndRDifferentialExpressionWizardPresentationStep();
		TestUtils.showStepComponent(step);
	}
}
