package org.sing_group.rnaseq.gui.sample.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

/**
 * The interface for receiving sample editor events.
 * 
 * @author Hugo López-Fernández
 * @author Aitor Blanco-Míguez
 *
 */
public interface SampleEditorListener extends EventListener {
	/**
	 * Invoked when a sample is edited.
	 * 
	 * @param event the associated event
	 */
	void onSampleEdited(ChangeEvent event);
}
