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
package org.sing_group.rnaseq.gui.components.wizard.steps.event;

import java.util.EventListener;
import java.util.Map;

import javax.swing.event.ChangeEvent;

/**
 * The listener interface for receiving events from an experimental condition
 * editor.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface ExperimentalConditionsEditorListener extends EventListener {

	/**
	 * Produced when the experimental conditions are changed.
	 * 
	 * @param event the event
	 */
	public void experimentalConditionsChanged(ChangeEvent event);

	/**
	 * Produced when the experimental conditions are renamed. 
	 * 
	 * @param event the event
	 * @param renameMap a map from the old names to the new names
	 */
	public void experimentalConditionsRenamed(ChangeEvent event,
		Map<String, String> renameMap);
}
