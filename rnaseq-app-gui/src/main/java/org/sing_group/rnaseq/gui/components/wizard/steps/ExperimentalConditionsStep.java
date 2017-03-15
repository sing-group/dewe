package org.sing_group.rnaseq.gui.components.wizard.steps;

import static org.sing_group.rnaseq.gui.components.wizard.steps.StepUtils.configureStepComponent;

import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingx.JXLabel;
import org.sing_group.rnaseq.gui.components.wizard.components.ExperimentalConditionsSelectionComponent;
import org.sing_group.rnaseq.gui.components.wizard.components.MultipleConditionsSelectionPanel;
import org.sing_group.rnaseq.gui.components.wizard.components.TwoConditionsSelectionPanel;
import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;

import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class ExperimentalConditionsStep extends WizardStep {

	private JPanel stepComponent;
	private int minConditions;
	private int maxConditions;
	private ExperimentalConditionsSelectionComponent conditionsSelectionComponent;

	public ExperimentalConditionsStep(int minConditions) {
		this(minConditions, Integer.MAX_VALUE);
	}

	public ExperimentalConditionsStep(int minConditions, int maxConditions) {
		checkConditions(minConditions, maxConditions);
		this.minConditions = minConditions;
		this.maxConditions = maxConditions;
	}

	private void checkConditions(int minConditions, int maxConditions) {
		if (minConditions <= 0) {
			throw new IllegalArgumentException(
				"minConditions must be greater than 0");
		} else if (minConditions > maxConditions) {
			throw new IllegalArgumentException(
				"maxConditions must be greater or equal than minConditions");
		}
	}

	@Override
	public String getStepTitle() {
		return "Experimental conditions";
	}

	@Override
	public JComponent getStepComponent() {
		if(this.stepComponent == null) {
			this.stepComponent = new CenteredJPanel(getSelectionPanel());
			configureStepComponent(stepComponent);
		}
		return this.stepComponent;
	}

	private JComponent getSelectionPanel() {
		JPanel selectionPanel = new JPanel();
		selectionPanel.setLayout(
			new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
		selectionPanel.setOpaque(false);
		JXLabel descriptionLabel = new JXLabel(
			"Introduce the experimental conditions:");
		selectionPanel.add(Box.createHorizontalGlue());
		selectionPanel.add(descriptionLabel);
		selectionPanel.add(getExperimentalConditionsPanel());
		selectionPanel.add(Box.createHorizontalGlue());
		return selectionPanel;
	}

	private JComponent getExperimentalConditionsPanel() {
		if (minConditions == 2 && maxConditions == 2) {
			conditionsSelectionComponent = new TwoConditionsSelectionPanel();
		} else {
			conditionsSelectionComponent = new MultipleConditionsSelectionPanel(
				minConditions, maxConditions);
		}
		((JComponent) conditionsSelectionComponent).setOpaque(false);
		conditionsSelectionComponent.addExperimentalConditionsEditorListener(
			new ExperimentalConditionsEditorListener() {
				@Override
				public void experimentalConditionsChanged(ChangeEvent event) {
					conditionsListChanged();
				}
			}
		);

		return (JComponent) conditionsSelectionComponent;
	}

	private void conditionsListChanged() {
		notifyWizardStepStatus();
	}

	@Override
	public boolean isStepCompleted() {
		return conditionsSelectionComponent.isValidSelection();
	}

	@Override
	public void stepEntered() {
	}

	public List<String> getExperimentalConditions() {
		return new LinkedList<>(
			conditionsSelectionComponent.getSelectedConditions());
	}
}
