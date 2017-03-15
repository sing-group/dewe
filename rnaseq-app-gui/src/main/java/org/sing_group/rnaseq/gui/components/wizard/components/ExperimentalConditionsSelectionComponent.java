package org.sing_group.rnaseq.gui.components.wizard.components;

import java.util.Set;

import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;

public interface ExperimentalConditionsSelectionComponent {
	public boolean isValidSelection();

	public Set<String> getSelectedConditions();

	public void addExperimentalConditionsEditorListener(ExperimentalConditionsEditorListener l);
}
