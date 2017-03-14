package org.sing_group.rnaseq.gui.components.wizard.components;

import java.util.Set;

import javax.swing.event.ListDataListener;

public interface ExperimentalConditionsSelectionComponent {
	public boolean isValidSelection();

	public Set<String> getSelectedConditions();

	public void addListDataListener(ListDataListener l);
}
