package org.sing_group.rnaseq.gui.components.wizard.components;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;

import org.sing_group.gc4s.input.JInputList;

/**
 * A component that allows the introduction of several condition labels using 
 * a list.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public class MultipleConditionsSelectionPanel extends JPanel
	implements ExperimentalConditionsSelectionComponent {
	private static final long serialVersionUID = 1L;

	private JInputList conditionsInputList;
	private int minConditions;
	private int maxConditions;

	/**
	 * Creates a new {@code MultipleConditionsSelectionPanel} for the
	 * introduction of a number of conditions in the specified range.
	 * 
	 * @param minConditions the minimum number of conditions
	 * @param maxConditions the maximum number of conditions
	 */
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
		conditionsInputList.setOpaque(false);
		conditionsInputList.addListDataListener(new ListDataListener() {

			@Override
			public void intervalRemoved(ListDataEvent e) {
				conditionsChanged();
			}

			@Override
			public void intervalAdded(ListDataEvent e) {
				conditionsChanged();
			}

			@Override
			public void contentsChanged(ListDataEvent e) {
				conditionsChanged();
			}
		});
		return conditionsInputList;
	}

	private void conditionsChanged() {
		for (ExperimentalConditionsEditorListener l : this
			.getListeners(ExperimentalConditionsEditorListener.class)
		) {
			l.experimentalConditionsChanged(new ChangeEvent(this));
		}
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
	public void addExperimentalConditionsEditorListener(
		ExperimentalConditionsEditorListener l
	) {
		this.listenerList.add(ExperimentalConditionsEditorListener.class, l);
	}

	@Override
	public void setSelectedConditions(Set<String> selectedConditions) {
		conditionsInputList.addElements(
			selectedConditions.toArray(new String[selectedConditions.size()]));
	}
}
