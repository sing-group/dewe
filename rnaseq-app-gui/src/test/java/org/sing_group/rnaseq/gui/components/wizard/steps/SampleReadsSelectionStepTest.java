package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.sing_group.rnaseq.gui.util.TestUtils;

public class SampleReadsSelectionStepTest {

	public static void main(String[] args) {
		SampleReadsSelectionStep step = 
			new SampleReadsSelectionStep(new ExperimentalConditionsStep(1) {
				@Override
				public List<String> getExperimentalConditions() {
					return new LinkedList<>(Arrays.asList("A", "B"));
				}
			},
			1);
		step.addWizardStepListener(new DefaultWizardStepListener());
		TestUtils.showStepComponent(step);
	}
}
