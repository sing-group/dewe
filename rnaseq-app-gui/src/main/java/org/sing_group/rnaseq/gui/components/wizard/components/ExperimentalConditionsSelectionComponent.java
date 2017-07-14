package org.sing_group.rnaseq.gui.components.wizard.components;

import java.util.Set;

import org.sing_group.rnaseq.gui.components.wizard.steps.event.ExperimentalConditionsEditorListener;

/**
 * The interface that defines a component to select the experimental conditions.
 *
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ExperimentalConditionsSelectionComponent {

	/**
	 * Returns {@code true} if the current selection is valid and {@code false}
	 * otherwise.
	 *
	 * @return {@code true} if the current selection is valid and {@code false}
	 *         otherwise
	 */
	public boolean isValidSelection();

	/**
	 * Returns a set containing the selected conditions.
	 *
	 * @return a set containing the selected conditions
	 */
	public Set<String> getSelectedConditions();

	/**
	 * Sets the selected conditions that the component must show.
	 *
	 * @param selectedConditions the selected conditions that the component
	 *        must show
	 */
	public void setSelectedConditions(Set<String> selectedConditions);

	/**
	 * Adds the specified {@code ExperimentalConditionsEditorListener} to the
	 * listener list.
	 *
	 * @param l a {@code ExperimentalConditionsEditorListener}
	 */
	public void addExperimentalConditionsEditorListener(ExperimentalConditionsEditorListener l);
}
