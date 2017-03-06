package org.sing_group.rnaseq.gui.components.wizard.steps;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;

import es.uvigo.ei.sing.hlfernandez.event.ListDataAdapter;
import es.uvigo.ei.sing.hlfernandez.input.JInputList;
import es.uvigo.ei.sing.hlfernandez.ui.CenteredJPanel;
import es.uvigo.ei.sing.hlfernandez.wizard.WizardStep;

public class ExperimentalConditionsStep extends WizardStep {

	private JPanel stepComponent;
	private JInputList conditionsInputList;
	
	private int minConditions;
	private int maxConditions;

	public ExperimentalConditionsStep(int minConditions) {
		this(minConditions, Integer.MAX_VALUE);
	}

	public ExperimentalConditionsStep(int minConditions, int maxConditions) {
		this.minConditions = minConditions;
		this.maxConditions = maxConditions;
	}

	@Override
	public String getStepTitle() {
		return "Experimental conditions";
	}

	@Override
	public JComponent getStepComponent() {
		if(this.stepComponent == null) {
			this.stepComponent = new JPanel();
			this.stepComponent.setLayout(new GridLayout(0, 1));
			this.stepComponent.add(new JLabel("Introduce the experimental conditions:"));
			conditionsInputList = new JInputList(true, false, false);
			conditionsInputList.addListDataListener(new ListDataAdapter() {
				@Override
				public void intervalAdded(ListDataEvent e) {
					conditionsListChanged();
				}

				@Override
				public void intervalRemoved(ListDataEvent e) {
					conditionsListChanged();
				}
			});
			this.stepComponent.add(conditionsInputList);
		}
		return new CenteredJPanel(this.stepComponent);
	}

	private void conditionsListChanged() {
		notifyWizardStepStatus();
	}

	@Override
	public boolean isStepCompleted() {
		int conditionsCount = this.conditionsInputList.getInputItems().size();

		return 	conditionsCount >= this.minConditions &&
				conditionsCount <= this.maxConditions;
	}

	@Override
	public void stepEntered() {
	}

	public List<String> getExperimentalConditions() {
		return this.conditionsInputList.getInputItems();
	}
}
