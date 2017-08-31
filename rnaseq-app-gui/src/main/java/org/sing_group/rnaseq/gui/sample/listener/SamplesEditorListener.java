package org.sing_group.rnaseq.gui.sample.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

/**
 * The interface for receiving samples editor events.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SamplesEditorListener extends EventListener {
	/**
	 * Invoked when a sample is edited.
	 * 
	 * @param event the associated event
	 */
	void onSampleEdited(ChangeEvent event);

	/**
	 * Invoked when a sample is added.
	 * 
	 * @param event the associated event
	 */
	void onSampleAdded(ChangeEvent event);

	/**
	 * Invoked when a sample is removed.
	 * 
	 * @param event the associated event
	 */
	void onSampleRemoved(ChangeEvent event);
}
