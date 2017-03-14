package org.sing_group.rnaseq.gui.components.wizard.components;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.ListDataListener;

import es.uvigo.ei.sing.hlfernandez.input.JInputList;

public class MultipleConditionsSelectionPanel extends JPanel
	implements ExperimentalConditionsSelectionComponent {
	private static final long serialVersionUID = 1L;

	private JInputList conditionsInputList;
	private int minConditions;
	private int maxConditions;

	public MultipleConditionsSelectionPanel(int minConditions,
		int maxConditions
	) {
		checkConditions(minConditions, maxConditions);
		this.minConditions = minConditions;
		this.maxConditions = maxConditions;
		this.init();
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

	private void init() {
		this.add(getInputConditionsList());
	}

	private Component getInputConditionsList() {
		conditionsInputList = new JInputList(true, false, false);
		return conditionsInputList;
	}

	@Override
	public boolean isValidSelection() {
		int conditionsCount = this.conditionsInputList.getInputItems().size();

		return 	conditionsCount >= this.minConditions &&
				conditionsCount <= this.maxConditions;
	}

	@Override
	public Set<String> getSelectedConditions() {
		return new HashSet<>(this.conditionsInputList.getInputItems());
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		this.conditionsInputList.addListDataListener(l);
	}
}
