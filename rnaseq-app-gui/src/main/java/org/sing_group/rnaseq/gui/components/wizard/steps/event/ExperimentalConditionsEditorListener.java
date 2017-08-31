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
