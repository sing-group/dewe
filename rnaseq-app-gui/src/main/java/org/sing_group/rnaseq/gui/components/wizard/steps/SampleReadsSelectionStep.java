package org.sing_group.rnaseq.gui.components.wizard.steps;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.sing_group.gc4s.wizard.WizardStep;
import org.sing_group.rnaseq.api.entities.FastqReadsSample;
import org.sing_group.rnaseq.api.entities.FastqReadsSamples;
import org.sing_group.rnaseq.gui.sample.FastqSamplesEditor;
import org.sing_group.rnaseq.gui.sample.listener.SamplesEditorListener;

public class SampleReadsSelectionStep extends WizardStep
	implements SamplesEditorListener {

	private ExperimentalConditionsStep experimentalConditionsStep;
	private FastqSamplesEditor fastqSamplesEditor;
	private int minSamplesPerCondition;
	private int initialNumSamples;

	public SampleReadsSelectionStep(
		ExperimentalConditionsStep experimentalConditionsStep,
		int minSamplesPerCondition,
		int initialNumSamples
	) {
		this.experimentalConditionsStep = experimentalConditionsStep;
		this.minSamplesPerCondition = minSamplesPerCondition;
		this.initialNumSamples = initialNumSamples;
	}

	@Override
	public String getStepTitle() {
		return "Samples selection";
	}

	@Override
	public JComponent getStepComponent() {
		this.fastqSamplesEditor = new FastqSamplesEditor(initialNumSamples);
		this.fastqSamplesEditor.addSamplesEditorListener(this);
		configureStepComponent(fastqSamplesEditor);
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

		Optional<Map<String, FastqReadsSamples>> conditionSamples =
			this.experimentalConditionsStep.getExperimentalConditionsAndSamples();
		if (conditionSamples.isPresent()) {
			this.setSamples(conditionSamples.get());
		}
	}

	private void setSamples(
		Map<String, FastqReadsSamples> conditionSamples) {
		if (conditionSamples != null) {
			this.fastqSamplesEditor.setSamples(conditionSamples.values()
				.stream().flatMap(List::stream).collect(Collectors.toList()));
			samplesListEdited();
		}
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
