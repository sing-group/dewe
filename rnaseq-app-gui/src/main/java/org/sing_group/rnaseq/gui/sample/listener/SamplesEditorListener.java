package org.sing_group.rnaseq.gui.sample.listener;

import java.util.EventListener;

import javax.swing.event.ChangeEvent;

public interface SamplesEditorListener extends EventListener {
	void onSampleEdited(ChangeEvent event);

	void onSampleAdded(ChangeEvent event);

	void onSampleRemoved(ChangeEvent event);
}
