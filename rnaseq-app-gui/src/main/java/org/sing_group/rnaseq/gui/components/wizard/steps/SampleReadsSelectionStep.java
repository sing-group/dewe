package org.sing_group.rnaseq.gui.components.wizard.steps;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.gui.sample.FastqSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class SampleReadsSelectionStep extends WizardStep
	implements SamplesEditorListener {
	
	private ExperimentalConditionsStep experimentalConditionsStep;
	private FastqSamplesEditor fastqSamplesEditor;
	private int minSamplesPerCondition;
	
	public SampleReadsSelectionStep(
		ExperimentalConditionsStep experimentalConditionsStep,
		int minSamplesPerCondition
	) {
		this.experimentalConditionsStep = experimentalConditionsStep;
		this.minSamplesPerCondition = minSamplesPerCondition;
	}

	@Override
	public String getStepTitle() {
		return "Samples selection";
	}

	@Override
	public JComponent getStepComponent() {
		this.fastqSamplesEditor = new FastqSamplesEditor();
		this.fastqSamplesEditor.addSamplesEditorListener(this);
		return fastqSamplesEditor;
	}

	@Override
	public boolean isStepCompleted() {
		return this.fastqSamplesEditor.isValidSelection()
			&& checkSamplesPerCondition();
	}

	private boolean checkSamplesPerCondition() {
		Map<String, Long> counts = this.fastqSamplesEditor.getSamples().stream()
			.map(FastqReadsSample::getCondition)
			.collect(groupingBy(e -> e, counting()));

		return allConditionsSelected(counts)
			&& eachConditionContainsMinimumSamples(counts);
	}

	private boolean eachConditionContainsMinimumSamples(
		Map<String, Long> counts) {
		return !counts.values().stream()
				.filter(numSamples -> numSamples < minSamplesPerCondition)
				.findAny().isPresent();
	}

	private boolean allConditionsSelected(Map<String, Long> counts) {
		return counts.keySet().containsAll(
			this.experimentalConditionsStep.getExperimentalConditions());
	}

	@Override
	public void stepEntered() {
		List<String> experimentalConditions =
			this.experimentalConditionsStep.getExperimentalConditions();
		this.fastqSamplesEditor.setSelectableConditions(experimentalConditions);
	}

	public FastqReadsSamples getSamples() {
		return this.fastqSamplesEditor.getSamples();
	}

	@Override
	public void onSampleEdited(ChangeEvent event) {
		samplesListEdited();
	}

	@Override
	public void onSampleAdded(ChangeEvent event) {
		samplesListEdited();
	}

	@Override
	public void onSampleRemoved(ChangeEvent event) {
		samplesListEdited();		
	}

	private void samplesListEdited() {
		this.notifyWizardStepStatus();
	}
}
