/*
 * #%L
 * DEWE GUI
 * %%
 * Copyright (C) 2016 - 2017 Hugo López-Fernández, Aitor Blanco-García, Florentino Fdez-Riverola, 
 * 			Borja Sánchez, and Anália Lourenço
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
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

	/**
	 * Enables or disables the introduction of experimental conditions.
	 * 
	 * @param enabled whether the introduction of experimental conditions must
	 * 		  be enabled or not.
	 */
	public void setExperimentalConditionIntroductionEnabled(boolean enabled);
}
